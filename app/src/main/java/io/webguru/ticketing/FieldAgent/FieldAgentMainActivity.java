package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.Global.SignOut;
import io.webguru.ticketing.Global.UserProfile;
import io.webguru.ticketing.Manager.ManagerMainActivity;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import io.webguru.ticketing.Global.GlobalFunctions;

public class FieldAgentMainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.requested_ticket_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    //POJO objects
    private static UserInfo userInfo;
    private ArrayList<FieldAgentData> fieldAgentDatas;
    private FieldAgentData[] fieldAgentDatas1 = new FieldAgentData[100];

    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    //Firebase Database refernce
    private DatabaseReference mDatabase;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_agent_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //Receving UserInfo Object from Intent
        Bundle bundle = getIntent().getExtras();
        //TODO Handle null case
        if(bundle!=null) {
            userInfo = (UserInfo) bundle.get("UserInfo");
        }
        fieldAgentDatas = new ArrayList<>(100);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        // Reversing the order of view Latest at top
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("field_agent_data").child(userInfo.getUserid());
        mAdapter = new FirebaseRecyclerAdapter<FieldAgentData, TicketHolder>(FieldAgentData.class, R.layout.field_ticket_cardview, TicketHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(TicketHolder viewHolder, FieldAgentData fieldAgentData, int position) {
                mProgressBar.setVisibility(View.GONE);
                fieldAgentDatas1[position] = fieldAgentData;
                viewHolder.setViewElements(fieldAgentData);
            }

        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(FieldAgentMainActivity.this, ViewEditFieldTicket.class);
                intent.putExtra("UserInfo", userInfo);
                intent.putExtra("FieldAgentData", fieldAgentDatas1[position]);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FieldAgentMainActivity.this, AddTicketRequest.class);
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
//                GlobalFunctions.showToast(FieldAgentMainActivity.this, "We have encountred an error. Please try again after some time.", Toast.LENGTH_LONG);
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_field_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(FieldAgentMainActivity.this, UserProfile.class);
            intent.putExtra("UserInfo", userInfo);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_signout) {
            Intent intent = new Intent(FieldAgentMainActivity.this, SignOut.class);
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
