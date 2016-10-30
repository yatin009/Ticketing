package io.webguru.ticketing.Agent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import io.webguru.ticketing.AndroidSlidingTab.SlidingTabLayout;
import io.webguru.ticketing.Approver.ApprovedFragment;
import io.webguru.ticketing.Approver.ApproverTicketInbox;
import io.webguru.ticketing.Approver.PendingApprovalFragment;
import io.webguru.ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentTicketInbox extends Fragment{

    ViewPager mViewPager;
    private SlidingTabLayout slidingTabLayout;
    AgentTicketInbox.SectionsPagerAdapter mSectionsPagerAdapter;

    public AgentTicketInbox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_agent_ticket_inbox,container, false);

        mSectionsPagerAdapter = new AgentTicketInbox.SectionsPagerAdapter(getFragmentManager());

        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.pink);    //define any color in xml resources and set it here, I have used white
            }
        });
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        slidingTabLayout.setViewPager(mViewPager);

        return rootView;
    }

//    @Override
//    public int getIndicatorColor(int position) {
//        return getResources().getColor(R.color.pink);
//    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PendingTicket();
                case 1:
                    return new ApprovedTicket();
                case 2:
                    return new CanceledTicket();
                case 3:
                    return new AgentApprovedFragment();
                case 4:
                    default:
                    return new AgentCompletedFragment();

            }
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Incoming".toUpperCase(l);
                case 1:
                    return "Dispatched".toUpperCase(l);
                case 2:
                    return "Pending Approval".toUpperCase(l);
                case 3:
                    return "Approved".toUpperCase(l);
                case 4:
                    return "Work Completed".toUpperCase(l);
            }
            return null;
        }
    }


}
