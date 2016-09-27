package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    //POJO objects
    private UserInfo userInfo;
    private ArrayList<FieldAgentData> fieldAgentDatas;

    //RecyclerView objects
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    //Firebase Database refernce
    private DatabaseReference mDatabase, userInfoDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_agent_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Receving UserInfo Object from Intent
        Bundle bundle = getIntent().getExtras();
        userInfo = (UserInfo) bundle.get("UserInfo");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("field_agent_data").child(userInfo.getUserid());
        mAdapter = new FirebaseRecyclerAdapter<FieldAgentData, TicketHolder>(FieldAgentData.class, R.layout.field_ticket_cardview, TicketHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(TicketHolder viewHolder, FieldAgentData model, int position) {
                viewHolder.setPriority(model.getPriority());
                viewHolder.setProblem(model.getProblem());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime() +" "+model.getPhotoUrl());
            }

        };
        mRecyclerView.setAdapter(mAdapter);
//        fieldAgentDatas = new ArrayList<>();
//        fieldAgentDatas.add(new FieldAgentData());// Adding blank element
//        mAdapter = new FieldTicketListAdapter(fieldAgentDatas);
//        mRecyclerView.setAdapter(mAdapter);
//        fetchFieldAgentTicketList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FieldAgentMainActivity.this, AddTicketRequest.class);
                intent.putExtra("UserInfo", userInfo);
                startActivity(intent);
            }
        });
    }

    private void fetchFieldAgentTicketList(){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0 && !fieldAgentDatas.isEmpty()){
                    fieldAgentDatas.remove(fieldAgentDatas.size() - 1);//removing blank element
                }
                for (DataSnapshot msgSnapshot: dataSnapshot.getChildren()) {
                    FieldAgentData fieldAgentData = msgSnapshot.getValue(FieldAgentData.class);
                    Log.d("FieldAgentData", fieldAgentData.getPriority()+": "+fieldAgentData.getProblem());
                    fieldAgentDatas.add(fieldAgentData);
                }
                if(!fieldAgentDatas.isEmpty()) {
                    fieldAgentDatas.add(new FieldAgentData());// Adding blank element
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                GlobalFunctions.showToast(FieldAgentMainActivity.this, "We have encountred an error. Please try again after some time.", Toast.LENGTH_LONG);
            }
        });
    }

    private void refreshList(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
