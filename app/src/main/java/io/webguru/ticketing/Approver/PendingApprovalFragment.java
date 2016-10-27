package io.webguru.ticketing.Approver;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Agent.AgentMainActivity;
import io.webguru.ticketing.Agent.AgentTicketHolder;
import io.webguru.ticketing.Agent.AgentTicketView;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Agent.AgentMainActivity.userInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingApprovalFragment extends Fragment {

    @Bind(R.id.approver_pending_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private Ticket[] ticketsArray = new Ticket[100];
    private String TAG = "PendingApprovalFragment";


    public PendingApprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pending_approval, container, false);
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ticketing");
        Query query = mDatabase.orderByChild("status").equalTo("Approver Assigned");
//        mDatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                ManagerData managerData = dataSnapshot.getValue(ManagerData.class);
//                for(ManagerData managerData1 : managerPendingDatas){
//                    if(managerData.getFieldRequestKey().equals(managerData1.getFieldRequestKey())) {
//                        managerPendingDatas.remove(managerData1);
//                        break;
//                    }
//                }
//                Log.d("PENDINTICKEFRAGMENT","REMOVING managerPendingDatas.size() >>> "+managerPendingDatas.size());
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        mAdapter = new FirebaseRecyclerAdapter<Ticket, AgentTicketHolder>(Ticket.class, R.layout.agent_ticket_cardview, AgentTicketHolder.class, query) {
            @Override
            protected void populateViewHolder(AgentTicketHolder viewHolder, Ticket ticket, int position) {
                mProgressBar.setVisibility(View.GONE);
                ticketsArray[position] = ticket;
                viewHolder.setViewElements(ticket, position, true);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            //
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), AgentTicketView.class);
                intent.putExtra("UserInfo", userInfo);
                intent.putExtra("Ticket", ticketsArray[position]);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

}
