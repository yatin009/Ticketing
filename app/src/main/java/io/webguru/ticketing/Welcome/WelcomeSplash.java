package io.webguru.ticketing.Welcome;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.KenBurnsEffect.KenBurnsView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        ButterKnife.bind(this);
        mKenBurns.setImageResource(R.drawable.factory_inspector);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        maxY=size.y;
        mPager = (ViewPager)findViewById(R.id.welcome_screens);
        myPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(myPagerAdapter);
        setButton();
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == LoginFragmetSlide.RC_SIGN_IN) {
//            LoginFragmetSlide fragment = new LoginFragmetSlide();
//            fragment.onActivityResult(requestCode, resultCode, data);
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
