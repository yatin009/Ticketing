package io.webguru.ticketing.Global;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.POJO.Analytics;
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
public class AnalyticFragment extends Fragment {

    @Bind(R.id.chart)
    PieChartView chart;
    @Bind(R.id.chart2)
    PieChartView pieChartView2;
    @Bind(R.id.total_count_value)
    TextView totalCountValue;

    private PieChartData data1, data2;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean hasCenterText1 = false;
    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    private Analytics analytics;

    public AnalyticFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        ButterKnife.bind(this, rootView);

        chart.setOnValueTouchListener(new ValueTouchListener());
//        pieChartView2.setOnValueTouchListener(new ValueTouchListener());


        hasLabels = true;
//        hasCenterCircle = true;
//        hasCenterText1 = true;
//        hasCenterText2 = true;
        generateDataChart1();
        generateDataChart2();
        return rootView;
    }

    public void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchLatestAnalytics();
            }
        }, 1000);


    }

    private void generateDataChart1() {
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

        data1 = new PieChartData(values);
        data1.setHasLabels(hasLabels);
        chart.setPieChartData(data1);
    }

    private void generateDataChart2() {
        int numValues = 3;

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
                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_BLUE);
                values.add(sliceValue);
            }
        }

        data2 = new PieChartData(values);
        data2.setHasLabels(hasLabels);
        pieChartView2.setPieChartData(data2);
    }

//    private void generateData() {
//        int numValues = 4;
//
//        List<SliceValue> values = new ArrayList<SliceValue>();
//        for (int i = 0; i < numValues; ++i) {
//            if(i==0) {
//                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_RED);
//                values.add(sliceValue);
//            }
//            else if(i==1){
//                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_ORANGE);
//                values.add(sliceValue);
//            }
//            else if(i==2){
//                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_GREEN);
//                values.add(sliceValue);
//            }
//            else if(i==3){
//                SliceValue sliceValue = new SliceValue(25, ChartUtils.COLOR_VIOLET);
//                values.add(sliceValue);
//            }
//        }
//
//        data = new PieChartData(values);
//        data.setHasLabels(hasLabels);
//        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
//        data.setHasLabelsOutside(hasLabelsOutside);
//        data.setHasCenterCircle(hasCenterCircle);
//
//        if (isExploded) {
//            data.setSlicesSpacing(24);
//        }
//
//        if (hasCenterText1) {
//            data.setCenterText1("Total");
//
//            // Get roboto-italic font.
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//            data.setCenterText1Typeface(tf);
//
//            // Get font size from dimens.xml and convert it to sp(library uses sp values).
//            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
//        }
//
//        if (hasCenterText2) {
//            data.setCenterText2("Tickets");
//
//            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//
//            data.setCenterText2Typeface(tf);
//            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
//        }
//
//        chart.setPieChartData(data);
////        pieChartView2.setPieChartData(data);
//    }

    private void fetchLatestAnalytics(){
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("analytics").child(GlobalFunctions.getTodaysDateAsFireBaseKey());
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    analytics = dataSnapshot.getValue(Analytics.class);
                    setTotalCount();
                    prepareData1();
                    prepareData2();
                    chart.startDataAnimation();
                    pieChartView2.startDataAnimation();
                } else {
                    System.out.println("No data exists for analyitcs on " + GlobalFunctions.getTodaysDateFormatted());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTotalCount(){
        int totalCount = analytics.getHighCount() + analytics.getMediumCount() + analytics.getLowCount();
        totalCountValue.setText(totalCount+"");
    }

    private void prepareData1() {
        int count=0;
        for (SliceValue value : data1.getValues()) {
            switch (count){
                case 0:
                    value.setTarget(analytics.getIncomingCount());
                    break;
                case 1:
                    value.setTarget(analytics.getDispatchedCount());
                    break;
                case 2:
                    value.setTarget(analytics.getApprovedCount());
                    break;
                case 3:
                    value.setTarget(analytics.getApprovalCount());
                    break;
            }
            count++;
        }
    }

    private void prepareData2() {
        int count=0;
        for (SliceValue value : data2.getValues()) {
            switch (count){
                case 0:
                    value.setTarget(analytics.getHighCount());
                    break;
                case 1:
                    value.setTarget(analytics.getMediumCount());
                    break;
                case 2:
                    value.setTarget(analytics.getLowCount());
                    break;
            }
            count++;
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
