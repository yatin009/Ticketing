package io.webguru.ticketing.Manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Global.SignOut;
import io.webguru.ticketing.Global.UserProfile;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class ManagerMainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.requested_ticket_list)
//    RecyclerView mRecyclerView;
    @Bind(R.id.parent_layout)
    CoordinatorLayout parent_layout;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    View.OnClickListener mOnClickListener;
    //RecyclerView objects
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    //Firebase Database refernce
    private DatabaseReference mDatabase;
    public static UserInfo userInfo;
    private ArrayList<ManagerData> managerDatas;

    private String TAG = "ManagerMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        userInfo = (UserInfo) bundle.get("UserInfo");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CanceledTicket(), "Canceled");
        adapter.addFragment(new PendingTicket(), "Pending");
        adapter.addFragment(new ApprovedTicket(), "Approved");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, true);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void updateStatus(final String newstatus, final String currentStatus, boolean showSnakBar, final ManagerData managerData) {

        //Add in new status
        if("pending".equals(newstatus)){
            managerData.setTicketNumber(null);
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String newStatusKey = mDatabase.child("manager_data").child(userInfo.getUserid()).child(newstatus).push().getKey();
        Map<String, Object> childUpdatesNew = new HashMap<>();
        childUpdatesNew.put("/manager_data/" + userInfo.getUserid() + "/" + newstatus + "/" + newStatusKey, managerData.toMap());
        mDatabase.updateChildren(childUpdatesNew);

        //Updating field agent DB
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
        mDatabase1.child("field_agent_data").child("2").child(managerData.getFieldRequestKey()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()==null)
                            return;
                        dataSnapshot.getRef().child("approved").setValue(newstatus);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });

        //Remove in existing status
        mDatabase.child("manager_data").child(userInfo.getUserid()).child(currentStatus).orderByChild("fieldRequestKey").equalTo(managerData.getFieldRequestKey()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()==null)
                            return;
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    managerData.setStatus("pending");
                    updateStatus(currentStatus, newstatus, false, managerData);
                    Log.d("MANAGERMAINACTIVITY", " pending >> ");
            }
        };
        if(showSnakBar) {
            String snackBarMsg = "A Ticket request has been canceled by you.";
            if("approved".equals(newstatus)){
                snackBarMsg = "A Ticket with unique number " + managerData.getTicketNumber() + "has been created.";
            }
            Snackbar snackbar = Snackbar.make(parent_layout, snackBarMsg, Snackbar.LENGTH_LONG).setAction("Undo", mOnClickListener);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.DKGRAY);
            snackbar.show();
        }
    }

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
            Intent intent = new Intent(ManagerMainActivity.this, UserProfile.class);
            intent.putExtra("UserInfo", userInfo);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_signout) {
            Intent intent = new Intent(ManagerMainActivity.this, SignOut.class);
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
