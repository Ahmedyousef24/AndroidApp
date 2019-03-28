package com.example.aalift;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class PieChartFragment extends Fragment {

    private static PieChart chart;
    private PieData pieData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pie_chart, container, false);

        chart = v.findViewById(R.id.pie);
        SetupPie();
        return v;

    }

    private void SetupPie(){
        List<PieEntry> entries = new ArrayList<>();


        // get these values from database
        entries.add(new PieEntry(20f, "Fat"));
        entries.add(new PieEntry(35f, "Protein"));
        entries.add(new PieEntry(45f, "Carbohydrates"));
        chart.getLegend().setTextColor(Color.WHITE);

        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(new int[]{getContext().getColor(R.color.pink),getContext().getColor(R.color.sun),getContext().getColor(R.color.purple)});

        pieData = new PieData(pieDataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(14f);
        chart.setData(pieData);
        chart.setUsePercentValues(true);
        chart.setHoleRadius(60f);
        chart.setTransparentCircleRadius(70f);
        chart.setTransparentCircleColor(Color.rgb(48,48,48));
        chart.setDrawSliceText(false);
        chart.setBackgroundColor(Color.rgb(48, 48, 48));
        chart.setHoleColor(Color.rgb(48, 48, 48));
       /* Description description = new Description();
        description.setText("Calorie distribution");
        description.setTextSize(14f);
        description.setTextColor(Color.WHITE);
        chart.setDescription(description);*/
       chart.getDescription().setEnabled(false);
        chart.invalidate();

    }

    public static void animate(){
        chart.spin(1000,0,-360,Easing.EaseInSine);
    }
    // getTOTCONSUMPTION REUTNR 3 THINGS PRO FAT KCAL, FOR NOW HARDCODE
}
