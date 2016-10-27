package io.webguru.ticketing.Welcome;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
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
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), "1");
        supportPostponeEnterTransition();
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Title");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public void onBackPressed() {
    }
}
