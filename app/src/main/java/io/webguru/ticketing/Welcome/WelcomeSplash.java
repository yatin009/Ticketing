package io.webguru.ticketing.Welcome;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import io.webguru.ticketing.R;


public class WelcomeSplash extends AppCompatActivity {

    private String TAG = "WelcomeSplash";
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);
        ButterKnife.bind(this);
        Button startAnimation = (Button) findViewById(R.id.button2);
        final RelativeLayout LoginBox = (RelativeLayout) findViewById(R.id.LoginBox);
        relativeLayout = (RelativeLayout) findViewById(R.id.ticket_layout);
        startAnimation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LoginBox.setVisibility(View.VISIBLE);
//                Animation animTranslate = AnimationUtils.loadAnimation(WelcomeSplash.this, R.anim.translate);
//                animTranslate.setAnimationListener(new AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation arg0) {
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation arg0) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation arg0) {
//                        LoginBox.setVisibility(View.VISIBLE);
//                        Animation animFade = AnimationUtils.loadAnimation(WelcomeSplash.this, R.anim.fade);
//                        LoginBox.startAnimation(animFade);
//                    }
//                });
//                ImageView imgLogo = (ImageView) findViewById(R.id.imageView1);
//                imgLogo.startAnimation(animTranslate);
            }
        });
        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
//                if (heightDiff > dpToPx(WelcomeSplash.this, 200)) { // if more than 200 dp, it's probably a keyboard...
//                    // ... do something here
//                    relativeLayout.setVisibility(View.GONE);
//                }else{
//                    relativeLayout.setVisibility(View.VISIBLE);
//                }
            }
        });
    }




    @Override
    public void onBackPressed() {

    }
}
