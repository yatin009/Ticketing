package io.webguru.ticketing.Welcome;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import io.webguru.ticketing.R;

/**
 * Created by yatin on 30/09/16.
 */

public class IntroScreens extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Just set a title, description, background and image. AppIntro will do the rest.
//        addSlide(AppIntroFragment.newInstance("Report", "Report a issue", R.drawable.ic_report, getResources().getColor(R.color.darkBlueGrey)));
//        addSlide(AppIntroFragment.newInstance("Generate & Fix", "Generate a ticket to fix the issue", R.drawable.ic_fixing, getResources().getColor(R.color.darkBlueGrey)));
        addSlide(new FixedFragment());
        addSlide(new LoginFragment());

        // OPTIONAL METHODS
        // Override bar/separator color.
//        setBarColor(getResources().getColor(R.color.primary_dark));
//        setSeparatorColor(getResources().getColor(R.color.primary_darker));

        // Hide Skip/Done button.
        showSkipButton(false);
//        setProgressButtonEnabled(false);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
