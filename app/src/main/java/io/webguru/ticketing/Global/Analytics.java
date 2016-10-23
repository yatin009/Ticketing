package io.webguru.ticketing.Global;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.R;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Analytics extends Fragment {

    @Bind(R.id.chart)
    PieChartView chart;
    @Bind(R.id.chart2)
    PieChartView pieChartView2;

    private PieChartData data;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    public Analytics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        ButterKnife.bind(this, rootView);

        chart.setOnValueTouchListener(new ValueTouchListener());
        pieChartView2.setOnValueTouchListener(new ValueTouchListener());


        hasLabels = true;
        hasCenterCircle = true;
        hasCenterText1 = true;
        hasCenterText2 = true;
        generateData();
        return rootView;
    }

    public void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataAnimation();
                chart.startDataAnimation();
            }
        }, 1000);


    }

    private void generateData() {
        int numValues = 4;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            if(i==0) {
                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_RED);
                values.add(sliceValue);
            }
            else if(i==1){
                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_ORANGE);
                values.add(sliceValue);
            }
            else if(i==2){
                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_GREEN);
                values.add(sliceValue);
            }
            else if(i==3){
                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_VIOLET);
                values.add(sliceValue);
            }
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            data.setCenterText1("Total");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Tickets");

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        chart.setPieChartData(data);
        pieChartView2.setPieChartData(data);
    }

    private void prepareDataAnimation() {
        for (SliceValue value : data.getValues()) {
            value.setTarget((float) Math.random() * 30 + 15);
        }
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}
