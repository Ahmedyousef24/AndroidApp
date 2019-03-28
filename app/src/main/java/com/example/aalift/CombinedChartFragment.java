package com.example.aalift;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


public class CombinedChartFragment extends Fragment {

    private static CombinedChart chart;
    private static ArrayList<Weight> listItem;
    private ArrayList<BarEntry> barEntries;
    private ArrayList<Entry> chartEntries;

    private LineData lineData;
    private BarData barData;
    public  static boolean readyFlag = false;


    public CombinedChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_combined_chart, container, false);

        chart = v.findViewById(R.id.chart);
        if (savedInstanceState != null) {
            listItem = savedInstanceState.getParcelableArrayList("listItem");
            barEntries = savedInstanceState.getParcelableArrayList("bar");
            chartEntries = savedInstanceState.getParcelableArrayList("chart");
            Log.d("savedata", "" + listItem.size());
            CombineChart();
        } else {
            listItem = new ArrayList<>();
            barEntries = new ArrayList<>();
            chartEntries = new ArrayList<>();
            GetList();

        }


        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listItem", listItem);
        outState.putParcelableArrayList("bar", barEntries);
        outState.putParcelableArrayList("chart", chartEntries);
    }


    private void GetList() {
        WeightData.read(new WeightData.FirebaseCallback() {
            @Override
            public void OnCallBack(List<Weight> list) {
                listItem.addAll(list);
                Log.d("calling", "getting list...");
                getLineList();
                GetBarData();
                CombineChart();
            }
        });

    }

    private static float FromIntToFloat(int integer, int decimal) {
        StringBuilder value = new StringBuilder();

        value.append(integer);
        value.append(".");
        value.append(decimal);

        float weight = Float.parseFloat(value.toString());
        return weight;
    }

    private void getLineList() {
        for (int i = 0; i < listItem.size(); i++) {
            chartEntries.add(new Entry(i, listItem.get(i).getWeight()));
        }
    }

    private void AddDataToLineChart() {

        final String[] date = DateToString();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return date[(int) value];
            }
        };


        LineDataSet dataSet = new LineDataSet(chartEntries, "Weight(KG)");
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSet.setColor(getContext().getColor(R.color.lightBlue));
        dataSet.setValueTextColor(getContext().getColor(R.color.lightBlue));
        dataSet.setCircleColor(getContext().getColor(R.color.lightBlue));
        dataSet.setValueTextSize(12f);

       // dataSet.setCircleHoleColor(Color.WHITE);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCubicIntensity(1f);

        lineData = new LineData(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    private void GetBarData() {
        for (int i = 0; i < listItem.size(); i++) {
            barEntries.add(new BarEntry(i, i * 100));
        }
    }

    private void AddDataToBarChart() {


        BarDataSet barDataSet = new BarDataSet(barEntries, "kcal");
        barDataSet.setColor(getContext().getColor(R.color.darkBlue));

        barDataSet.setValueTextColor(getContext().getColor(R.color.blue));
        barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);


    }

    private String[] DateToString() {
        String dateArray[] = new String[listItem.size()];
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;
        for (int i = 0; i < listItem.size(); i++) {
            dateString = formatter.format(listItem.get(i).getDate());
            dateArray[i] = dateString;
        }
        return dateArray;
    }

    private void CombineChart() {

        AddDataToLineChart();
        AddDataToBarChart();

        CombinedData combinedData = new CombinedData();

        combinedData.setData(barData);
        combinedData.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setSpaceMin(barData.getBarWidth() / 2f);
        xAxis.setSpaceMax(barData.getBarWidth() / 2f);
        xAxis.setTextColor(getResources().getColor(R.color.white));
        chart.getLegend().setTextColor(getResources().getColor(R.color.white));
        chart.getAxisLeft().setTextColor(getResources().getColor(R.color.white));
        chart.getAxisRight().setTextColor(getResources().getColor(R.color.white));
        chart.setBackgroundColor(Color.rgb(48, 48, 48));
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setSpaceBottom(0f);
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawBorders(true);
        chart.setData(combinedData);
        chart.getDescription().setEnabled(false);

        // AnimateChart();

        chart.invalidate();
        chart.setVisibleXRangeMaximum(4f);
        chart.moveViewToX(listItem.size());
        readyFlag = true;
    }


    public static void AnimateChart() {
        chart.animateXY(1000, 2000, Easing.EaseInBounce);
    }
}



