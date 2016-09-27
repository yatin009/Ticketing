package io.webguru.ticketing.Welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.webguru.ticketing.R;


public class WelcomeSlide2 extends Fragment {


    public static WelcomeSlide2 newInstance(String param1, String param2) {
        WelcomeSlide2 fragment = new WelcomeSlide2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public WelcomeSlide2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_slide2, container, false);
    }
}
