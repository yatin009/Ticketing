package io.webguru.ticketing.Manager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Manager.ManagerMainActivity.userInfo;

public class ApprovedTicket extends Fragment {

    @Bind(R.id.manager_approved_list)
    RecyclerView mRecyclerView;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    private ArrayList<ManagerData> managerApprovedDatas;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private String TAG = "ApprovedTicket";
    public ApprovedTicket() {
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
        View rootView = inflater.inflate(R.layout.fragment_approved_ticket, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
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
        managerApprovedDatas = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("manager_data").child(userInfo.getUserid()).child("approved");
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
                for(ManagerData managerData1 : managerApprovedDatas){
                    if(managerData.getFieldRequestKey().equals(managerData1.getFieldRequestKey())) {
                        managerApprovedDatas.remove(managerData1);
                        break;
                    }
                }
                Log.d("PENDINTICKEFRAGMENT","REMOVING managerPendingDatas.size() >>> "+managerApprovedDatas.size());
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
                managerApprovedDatas.add(0, managerData); // reversing the order, for storing latest at the top
                Log.d("PENDINTICKEFRAGMENT","ADDING managerPendingDatas.size() >>> "+managerApprovedDatas.size());
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
            public void onLongItemClick(View view, final int position) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setCancelable(true);
                ad.setTitle("Assign a contractor");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Contractor 1");
                arrayAdapter.add("Contractor 2");
                arrayAdapter.add("Contractor 3");
                arrayAdapter.add("Contractor 4");
                arrayAdapter.add("Contractor 5");
                ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ad.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String strName = arrayAdapter.getItem(which);
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                        getActivity());
                                builderInner.setTitle("Please Confirm");
                                builderInner.setMessage("You have selected "+ strName +", as a Contractor for this job");
                                builderInner.setPositiveButton(
                                        "Confirm",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                updateContractorInfo(position, strName);
                                                dialog.dismiss();
                                            }
                                        });
                                builderInner.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builderInner.show();
                            }
                        });
                AlertDialog dialog = ad.create();
                dialog.show();
            }
        }));

    }

    private void updateContractorInfo(int position, final String contractorName){
        ManagerData managerData = managerApprovedDatas.get(position);
        managerData.setContractor(contractorName);

        //Updating ticket value
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("manager_data").child(userInfo.getUserid()).child("approved").orderByChild("fieldRequestKey").equalTo(managerData.getFieldRequestKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null)
                    return;
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    childSnapshot.getRef().child("contractor").setValue(contractorName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
