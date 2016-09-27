package io.webguru.ticketing.Welcome;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.webguru.ticketing.R;


public class WelcomeSlide1 extends Fragment {

    private ImageView mLogo;
    private TextView welcomeText;

    public static WelcomeSlide1 newInstance(String param1, String param2) {
        WelcomeSlide1 fragment = new WelcomeSlide1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WelcomeSlide1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_slide1, container, false);
        mLogo = (ImageView) view.findViewById(R.id.logo);
        welcomeText = (TextView) view.findViewById(R.id.welcome_text);
        return view;
    }

    public void onStart(){
        super.onStart();
        animation1();
    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();

        ObjectAnimator alphaAnimationtext = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimationtext.setStartDelay(1700);
        alphaAnimationtext.setDuration(500);
        alphaAnimationtext.start();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
