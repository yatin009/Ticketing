package io.webguru.ticketing.Global;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import io.webguru.ticketing.POJO.ChatData;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class Chat extends AppCompatActivity {

    public static final String MESSAGES_CHILD = "messages";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = "MainActivity";
    private static final int REQUEST_INVITE = 1;
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private AppCompatButton mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private TextInputEditText mMessageEditText;
    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ChatData, MessageViewHolder>
            mFirebaseAdapter;

    private UserInfo userInfo;
    private FieldAgentData fieldAgentData;
    private ManagerData managerData;
    private String ticketNumber;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            userInfo = (UserInfo) bundle.get("UserInfo");
            fieldAgentData = (FieldAgentData) bundle.get("FieldAgentData");
            managerData = (ManagerData) bundle.get("ManagerData");
        }

        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        // TODO Initialize Firebase Auth
        if (userInfo == null) {
            // Not signed in, launch the Sign In activity
            finish();
            return;
        } else {
            mUsername = userInfo.getFirstname();
            if (userInfo.getPhotoUrl() != null) {
                mPhotoUrl = userInfo.getPhotoUrl();
            }
            if(fieldAgentData!=null){
                ticketNumber = fieldAgentData.getTicketNumber();
            }else if(managerData!=null){
                ticketNumber = managerData.getTicketNumber();
            }
        }
        getSupportActionBar().setTitle(ticketNumber +" Chat");
        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        // Fetch remote config.
        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chat_history").child(ticketNumber);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatData, MessageViewHolder>( ChatData.class, R.layout.item_message, MessageViewHolder.class,
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, ChatData chatData, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                if(chatData.getUserName().equals(userInfo.getUsername())){
                    viewHolder.selfMessageLayout.setVisibility(View.VISIBLE);
                    viewHolder.othersMessageLayout.setVisibility(View.GONE);

                    viewHolder.messageTextView.setText(chatData.getText());
                    viewHolder.messengerTextView.setText(chatData.getName());
                    if (chatData.getPhotoUrl() == null) {
                        viewHolder.messengerImageView.setImageDrawable(ContextCompat.getDrawable(Chat.this,R.drawable.ic_account_circle_black_36dp));
                    } else {
                        Glide.with(Chat.this).load(chatData.getPhotoUrl()).into(viewHolder.messengerImageView);
                    }
                }else{
                    viewHolder.othersMessageLayout.setVisibility(View.VISIBLE);
                    viewHolder.selfMessageLayout.setVisibility(View.GONE);

                    viewHolder.othersMessageTextView.setText(chatData.getText());
                    viewHolder.othersMessengerTextView.setText(chatData.getName());
                    if (chatData.getPhotoUrl() == null) {
                        viewHolder.othersMessengerImageView.setImageDrawable(ContextCompat.getDrawable(Chat.this,R.drawable.ic_account_circle_black_36dp));
                    } else {
                        Glide.with(Chat.this).load(chatData.getPhotoUrl()).into(viewHolder.othersMessengerImageView);
                    }
                }

            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);

        mMessageEditText = (TextInputEditText) findViewById(R.id.messageEditText);
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSendButton = (AppCompatButton) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send messages on click.
                ChatData chatData = new
                        ChatData(mMessageEditText.getText().toString(),
                        mUsername, userInfo.getRole(), userInfo.getUsername(),
                        mPhotoUrl);
                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                        .push().setValue(chatData);
                mMessageEditText.setText("");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;
        public LinearLayout selfMessageLayout;

        public TextView othersMessageTextView;
        public TextView othersMessengerTextView;
        public CircleImageView othersMessengerImageView;
        public LinearLayout othersMessageLayout;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
            selfMessageLayout = (LinearLayout) itemView.findViewById(R.id.self_layout);

            othersMessageTextView = (TextView) itemView.findViewById(R.id.othersMessageTextView);
            othersMessengerTextView = (TextView) itemView.findViewById(R.id.othersMessengerTextView);
            othersMessengerImageView = (CircleImageView) itemView.findViewById(R.id.othersMessengerImageView);
            othersMessageLayout = (LinearLayout) itemView.findViewById(R.id.others_layout);
        }
    }
}
