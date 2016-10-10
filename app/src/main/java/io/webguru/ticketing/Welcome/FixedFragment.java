package io.webguru.ticketing.Welcome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixedFragment extends Fragment {


    @Bind(R.id.smallCogWheel)
    ImageView smallCogWheel;
    @Bind(R.id.bigCogWheel)
    ImageView bigCogWheel;

    Animation clockWiseRotation;
    Animation antiClockWiseRotation;

    public FixedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fixed, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart(){
        super.onStart();
        clockWiseRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_clockwise);
        clockWiseRotation.setRepeatCount(Animation.INFINITE);

        antiClockWiseRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anticlockwise);
        antiClockWiseRotation.setRepeatCount(Animation.INFINITE);
        startAnimation();
    }

    private void startAnimation(){
        bigCogWheel.startAnimation(clockWiseRotation);
        smallCogWheel.startAnimation(antiClockWiseRotation);
    }

}
