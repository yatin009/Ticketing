package io.webguru.ticketing.Welcome;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.DB.UserInfoDB;
import io.webguru.ticketing.Requester.RequesterMainActivity;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.KenBurnsEffect.KenBurnsView;
import io.webguru.ticketing.Agent.AgentMainActivity;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;


public class WelcomeSplash extends AppCompatActivity {

    ViewPager mPager;
    ScreenSlidePagerAdapter myPagerAdapter;
    private static final int NUM_PAGES = 2;
    int maxY;

    @Bind(R.id.loginButton)
    AppCompatButton loginButton;
    @Bind(R.id.signLayout)
    LinearLayout signLayout;
    @Bind(R.id.ken_burns_images)
    KenBurnsView mKenBurns;
    @Bind(R.id.scrollBackgroundImage)
    ScrollView scrollBackgroundImage;

    private String TAG = "WelcomeSplash";
    private boolean disableSwipe = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        ButterKnife.bind(this);
        scrollBackgroundImage.setEnabled(false);
        mKenBurns.setImageResource(R.drawable.factory_inspector);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        maxY=size.y;
        mPager = (ViewPager)findViewById(R.id.welcome_screens);
        myPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(myPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(1, true);
            }
        });
        mPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return disableSwipe;
            }
        });
        new CheckUserInDB().execute();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class CheckUserInDB extends AsyncTask<Void, Void ,Boolean>{

        UserInfo userInfo;
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean dbExist = GlobalFunctions.checkDataBase(WelcomeSplash.this);
            Log.d(TAG, "dbExist >>> "+dbExist);
            if(!dbExist)
                return false;
            userInfo = new UserInfoDB(WelcomeSplash.this).getUserInfo();
            return (userInfo!=null && userInfo.isLoggedin());
        }

        protected void onPostExecute(Boolean result){
            if(!result){
                disableSwipe = false;
                setButton();
            }else {
                disableSwipe = true;
                if ("manager".equals(userInfo.getRole())) {
                    Intent intent = new Intent(WelcomeSplash.this, AgentMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("fieldagent".equals(userInfo.getRole())) {
                    Intent intent = new Intent(WelcomeSplash.this, RequesterMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("contractor".equals(userInfo.getRole())) {
                    startActivity(new Intent(WelcomeSplash.this, ContractorMainActivity.class));
                } else if ("approver".equals(userInfo.getRole())) {
                    startActivity(new Intent(WelcomeSplash.this, ApproverMainActivity.class));
                }
                (WelcomeSplash.this).finish();
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new WelcomeSlide1();
                case 1 :
                    return new LoginFragmetSlide();
                default:
                    return new WelcomeSlide1();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void setButton(){
        if(mPager.getCurrentItem()==0)
            showLoginButton();
        else
            hideLoginButton();
    }

    public void showLoginButton(){
        if(!loginButton.isShown()) {
            loginButton.setVisibility(View.VISIBLE);
            animateSignLayout();
        }
    }

    public void hideLoginButton(){
        loginButton.setVisibility(View.GONE);
        animateSignLayout();
    }

    public void animateSignLayout(){
        ObjectAnimator moveLoginButton = ObjectAnimator.ofFloat(signLayout, "translationY", (float)maxY, 0f);
        moveLoginButton.setDuration(600);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(moveLoginButton);
        animatorSet.start();
    }

}
