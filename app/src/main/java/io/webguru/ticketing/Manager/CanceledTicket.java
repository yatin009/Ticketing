package io.webguru.ticketing.Manager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Manager.ManagerMainActivity.userInfo;

public class CanceledTicket extends Fragment {

    @Bind(R.id.manager_canceled_list)
    RecyclerView mRecyclerView;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    private ArrayList<ManagerData> managerCanceledDatas;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public CanceledTicket() {
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_canceled_ticket, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void onStart(){
        super.onStart();
        super.onStart();
        mRecyclerView.setHasFixedSize(false);
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Reversing the order of view Latest at top. Firebase keeps Ascending order.
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        managerCanceledDatas = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("manager_data").child(userInfo.getUserid()).child("cancel");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ManagerData managerData = dataSnapshot.getValue(ManagerData.class);
                for(ManagerData managerData1 : managerCanceledDatas){
                    if(managerData.getFieldRequestKey().equals(managerData1.getFieldRequestKey())) {
                        managerCanceledDatas.remove(managerData1);
                        break;
                    }
                }
                Log.d("PENDINTICKEFRAGMENT","REMOVING managerPendingDatas.size() >>> "+managerCanceledDatas.size());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter = new FirebaseRecyclerAdapter<ManagerData, ManagerTicketHolder>(ManagerData.class, R.layout.manager_cardview, ManagerTicketHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(ManagerTicketHolder viewHolder, ManagerData managerData, int position) {
                managerCanceledDatas.add(0, managerData); // reversing the order, for storing latest at the top
                Log.d("PENDINTICKEFRAGMENT","ADDING managerPendingDatas.size() >>> "+managerCanceledDatas.size());
                viewHolder.setViewElements(managerData);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            //
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(ManagerMainActivity.this, ViewEditFieldTicket.class);
//                intent.putExtra("UserInfo", userInfo);
//                intent.putExtra("FieldAgentData", fieldAgentDatas.get(position));
//                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
