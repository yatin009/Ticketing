package io.webguru.ticketing.Approver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Agent.AgentTicketHolder;
import io.webguru.ticketing.Agent.AgentTicketView;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Agent.AgentMainActivity.userInfo;

public class ApproverCompletedFragment extends Fragment {

    @Bind(R.id.manager_work_completed_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private Ticket[] ticketsArray = new Ticket[100];
    private String TAG = "AgentCompletedFragment";

    public ApproverCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_approver_completed, container, false);
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
        Query query = mDatabase.orderByChild("agent_status").equalTo("1_WorkCompleted");
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
