package io.webguru.ticketing.Approver;


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
import io.webguru.ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApproverTicketInbox extends Fragment {

    ViewPager mViewPager;
    private SlidingTabLayout slidingTabLayout;
    SectionsPagerAdapter mSectionsPagerAdapter;

    public ApproverTicketInbox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_approver_ticket_inbox,container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(R.color.pink);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        slidingTabLayout.setViewPager(mViewPager);

        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PendingApprovalFragment();
                case 1:
                default:
                    return new ApprovedFragment();

            }
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Pending Approval".toUpperCase(l);
                case 1:
                    return "Approved".toUpperCase(l);
            }
            return null;
        }
    }

}
