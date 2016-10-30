package io.webguru.ticketing.Agent;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.UserContractor;
import io.webguru.ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContractorListFragment extends Fragment {

    @Bind(R.id.contractor_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private String TAG = "CONTRACTORLIST";

    public ContractorListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contractor_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart() {
        super.onStart();
        mRecyclerView.setHasFixedSize(false);
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Reversing the order of view Latest at top. Firebase keeps Ascending order.
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("contractor_list");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null || dataSnapshot.getValue() == null) {
                    mProgressBar.setVisibility(View.GONE);
                    GlobalFunctions.showToast(getActivity(), "There are no contractors, Create one.", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter = new FirebaseRecyclerAdapter<UserContractor, UserCntractorCardView>(UserContractor.class, R.layout.user_contractor_cardview, UserCntractorCardView.class, mDatabase) {
            @Override
            protected void populateViewHolder(UserCntractorCardView viewHolder, UserContractor userContractor, int position) {
                mProgressBar.setVisibility(View.GONE);
                viewHolder.setViewElements(userContractor, getActivity());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.START) { // Swiped to left
                } else if (direction == ItemTouchHelper.END) { // Swiped to right
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float newHeight = (float) itemView.getBottom() - (float) itemView.getTop();
                    float newWidth = newHeight / 3;
                    Paint p = new Paint();
                    if (dX > 0) {
                        p.setColor(Color.WHITE);
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.cancel_ticket);
                        RectF icon_dest = new RectF((float) itemView.getLeft(), (float) itemView.getTop() + newWidth, (float) itemView.getLeft() + newWidth, (float) itemView.getBottom() - newWidth);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else if (dX < 0) {
                        p.setColor(Color.WHITE);
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.cancel_ticket);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        RectF icon_dest = new RectF((float) itemView.getRight() - newWidth, (float) itemView.getTop() + newWidth + 10, (float) itemView.getRight(), (float) itemView.getBottom() - newWidth + 10);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    @OnClick(R.id.fab)
    public void addContractor() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_contractor, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        builder.setCancelable(true);
        // set the custom dialog components - text, image and button
        final TextInputEditText companyName = (TextInputEditText) view.findViewById(R.id.company_name);
        final TextInputEditText name = (TextInputEditText) view.findViewById(R.id.name);
        final TextInputEditText contactNumber = (TextInputEditText) view.findViewById(R.id.contact_number);
        final TextInputEditText email = (TextInputEditText) view.findViewById(R.id.email);

        AppCompatButton addContractor = (AppCompatButton) view.findViewById(R.id.add_contractor);
        final Dialog dialog = builder.create();
        // if button is clicked, close the custom dialog
        addContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyNameValue = companyName.getText().toString();
                if ("".equals(companyNameValue)) {
                    companyName.setError("Invalid Company Name");
                    return;
                }
                String nameValue = name.getText().toString();
                if ("".equals(companyNameValue)) {
                    name.setError("Invalid Company Name");
                    return;
                }
                String contactNumberValue = contactNumber.getText().toString();
                if ("".equals(companyNameValue)) {
                    contactNumber.setError("Invalid Company Name");
                    return;
                }
                String emailValue = email.getText().toString();
                if ("".equals(companyNameValue)) {
                    email.setError("Invalid Company Name");
                    return;
                }
                UserContractor userContractor = new UserContractor(companyNameValue, nameValue, Integer.parseInt(contactNumberValue), emailValue);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/contractor_list/" + GlobalFunctions.getCurrentDateInMilliseconds(), userContractor.toMap());
                mDatabase.updateChildren(childUpdates);
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    public void strechDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp = null;
        window = null;
    }

}
