package io.webguru.ticketing.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.Global.RecyclerItemClickListener;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Manager.ManagerMainActivity.userInfo;

public class PendingTicket extends Fragment {

    @Bind(R.id.manager_pending_list)
    RecyclerView mRecyclerView;

    //Firebase Database refernce
    private DatabaseReference mDatabase;
    private ArrayList<ManagerData> managerPendingDatas;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public PendingTicket() {
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
        View rootView = inflater.inflate(R.layout.fragment_pending_ticket, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart(){
        super.onStart();
        mRecyclerView.setHasFixedSize(false);
//        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Reversing the order of view Latest at top. Firebase keeps Ascending order.
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        managerPendingDatas = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("manager_data").child(userInfo.getUserid()).child("pending");
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
                for(ManagerData managerData1 : managerPendingDatas){
                    if(managerData.getFieldRequestKey().equals(managerData1.getFieldRequestKey())) {
                        managerPendingDatas.remove(managerData1);
                        break;
                    }
                }
                Log.d("PENDINTICKEFRAGMENT","REMOVING managerPendingDatas.size() >>> "+managerPendingDatas.size());
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
                managerPendingDatas.add(0, managerData); // reversing the order, for storing latest at the top
                Log.d("PENDINTICKEFRAGMENT","ADDING managerPendingDatas.size() >>> "+managerPendingDatas.size());
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
                ManagerData managerData = managerPendingDatas.get(viewHolder.getAdapterPosition());
                if (direction == ItemTouchHelper.START)
                { // Swiped to left
                    Log.d("MANAGERMAINACTIVITY", " Card swipe START>> ");
                    managerData.setStatus("cancel");
                    managerData.setTicketNumber(GlobalFunctions.getCurrentDateInMilliseconds("-"+userInfo.getUserid()));
                    ((ManagerMainActivity)getActivity()).updateStatus("cancel","pending", true, managerData);
                }
                else if (direction == ItemTouchHelper.END)
                { // Swiped to right
                    Log.d("MANAGERMAINACTIVITY", " Card swipe END>> ");
                    managerData.setStatus("approved");
                    managerData.setTicketNumber(GlobalFunctions.getCurrentDateInMilliseconds("-"+userInfo.getUserid()));
                    ((ManagerMainActivity)getActivity()).updateStatus("approved","pending", true, managerData);

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
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.approve_ticket);
                        RectF icon_dest = new RectF((float) itemView.getLeft(), (float) itemView.getTop() + newWidth, (float) itemView.getLeft() + newWidth, (float) itemView.getBottom() - newWidth);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else if ( dX < 0) {
                        p.setColor(Color.WHITE);
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.cancel_ticket);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        RectF icon_dest = new RectF((float) itemView.getRight() - newWidth, (float) itemView.getTop() + newWidth+10, (float) itemView.getRight(), (float) itemView.getBottom() - newWidth+10);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
