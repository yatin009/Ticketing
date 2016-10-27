package io.webguru.ticketing.Requester;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.Global.SignOut;
import io.webguru.ticketing.Global.UserProfile;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class RequesterMainActivity extends AppCompatActivity {

    //POJO objects
    private static UserInfo userInfo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.requested_ticket_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    int count = 0;
    private Ticket[] ticketsArray = new Ticket[100];
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    //Firebase Database refernce
    private DatabaseReference mDatabase;
    private int userID;
    private String TAG = "REQUESTERMAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //Receving UserInfo Object from Intent
        Bundle bundle = getIntent().getExtras();
        //TODO Handle null case
        if (bundle != null) {
            userInfo = (UserInfo) bundle.get("UserInfo");
        }
        userID = Integer.parseInt(userInfo.getUserid());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        // Reversing the order of view Latest at top
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ticketing");
        Query query = mDatabase.orderByChild("requesterId").equalTo(Integer.parseInt(userInfo.getUserid()));
        mAdapter = new FirebaseRecyclerAdapter<Ticket, RequesterTicketHolder>(Ticket.class, R.layout.requester_ticket_cardview, RequesterTicketHolder.class, query) {
            @Override
            protected void populateViewHolder(RequesterTicketHolder viewHolder, Ticket ticket, int position) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG,"ticket.getRequesterId() >>> "+ticket.getRequesterId());
                if (userID == ticket.getRequesterId()) {
                    ticketsArray[position] = ticket;
                    viewHolder.setViewElements(ticket, true);
                } else {
                    viewHolder.setViewElements(null, false);
                }
//
            }

        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
//                Ticket ticket = ticketsArray[position];
//                if(ticket.isDetailsShown()){
//                    LinearLayout detailsLayout = (LinearLayout) view.findViewById(R.id.detial_layout);
//                    detailsLayout.setVisibility(View.GONE);
//                    ticket.setDetailsShown(false);
//                }else {
//                    LinearLayout detailsLayout = (LinearLayout) view.findViewById(R.id.detial_layout);
//                    detailsLayout.setVisibility(View.VISIBLE);
//
//                    ticket.setDetailsShown(true);
//                }
//                Intent intent = new Intent(RequesterMainActivity.this, ViewEditFieldTicket.class);
//                intent.putExtra("UserInfo", userInfo);
//                intent.putExtra("FieldAgentData", fieldAgentDatas1[position]);
//                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequesterMainActivity.this, AddTicketRequest.class);
                intent.putExtra("UserInfo", userInfo);
                startActivity(intent);
            }
        });
        RecyclerView.ItemAnimator itemAnimator1 = new DefaultItemAnimator();
        itemAnimator1.setAddDuration(200);
        mRecyclerView.setItemAnimator(itemAnimator1);
//        fieldAgentDatas = new ArrayList<>();
//        fieldAgentDatas.add(new FieldAgentData());// Adding blank element
//        mAdapter = new FieldTicketListAdapter(fieldAgentDatas);
//        mRecyclerView.setAdapter(mAdapter);
//        fetchFieldAgentTicketList();
    }

//    private void fetchFieldAgentTicketList(){
//        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getChildrenCount()>0 && !fieldAgentDatas.isEmpty()){
//                    fieldAgentDatas.remove(fieldAgentDatas.size() - 1);//removing blank element
//                }
//                for (DataSnapshot msgSnapshot: dataSnapshot.getChildren()) {
//                    FieldAgentData fieldAgentData = msgSnapshot.getValue(FieldAgentData.class);
//                    Log.d("FieldAgentData", fieldAgentData.getPriority()+": "+fieldAgentData.getProblem());
//                    fieldAgentDatas.add(fieldAgentData);
//                }
//                if(!fieldAgentDatas.isEmpty()) {
//                    fieldAgentDatas.add(new FieldAgentData());// Adding blank element
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                GlobalFunctions.showToast(RequesterMainActivity.this, "We have encountred an error. Please try again after some time.", Toast.LENGTH_LONG);
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contractor_requester, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            Intent intent = new Intent(RequesterMainActivity.this, SignOut.class);
            intent.putExtra("UserInfo", userInfo);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
