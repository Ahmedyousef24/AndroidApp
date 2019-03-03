package com.example.aalift;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {

    private FloatingActionButton addWeightButton;
    private TextView weightText;
    private RecyclerView recyclerView;
    private static CombinedChart chart;

    private final String TAG = "PARS";
    private static WeightAdapter adapter;
    private static List<Weight>listItem;


    private LineData lineData;
    private BarData barData;

    private RecyclerView.LayoutManager layoutManager;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View v= inflater.inflate(R.layout.progress_fragment, container, false);
      chart = v.findViewById(R.id.chart);
      recyclerView = v.findViewById(R.id.recyclerView);
      addWeightButton = v.findViewById(R.id.add_weight);

      listItem = new ArrayList<>();
      adapter = new WeightAdapter(listItem,getActivity());

      layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
      recyclerView.setLayoutManager(layoutManager);

      GetList();


        addWeightButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              WeightPicker weightPicker = new WeightPicker();
              weightPicker.show(getFragmentManager(),"HEJ");
          }
      });

        recyclerView.setAdapter(adapter);
        return v;
    }


    // Adding new Weight and add it to the list
    public static void NewWeight(int weightInteger, int WeightDecimal){

        float weight = FromIntToFloat(weightInteger,WeightDecimal);
        Date date = new Date();

        // pass this to the update chart function
        Weight weightObject = new Weight(weight,date);
        WeightData.AddWeight(weightObject);

        int insertIndex = 0;
        listItem.add(insertIndex,weightObject);

        adapter.notifyItemInserted(insertIndex);
    }

    // GetLIST FUNCTION
    private void GetList(){
        WeightData.read(new WeightData.FirebaseCallback() {
            @Override
            public void OnCallBack(List<Weight> list) {
                listItem.addAll(list);
                adapter.notifyItemChanged(0);
                CombineChart();
            }
        });

    }

    private static float FromIntToFloat(int integer, int decimal){
        StringBuilder value = new StringBuilder();

        value.append(integer);
        value.append(".");
        value.append(decimal);

        float weight = Float.parseFloat(value.toString());
        return  weight;
    }

    private void AddDataToLineChart(){

        List<Entry> entries = new ArrayList<>();
        for (int i=0 ; i<listItem.size(); i++){
            entries.add(new Entry(i,listItem.get(i).getWeight()));
        }

       // move this to another function?

        final String [] date = DateToString();

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return date[(int) value];
            }
        };



        LineDataSet dataSet = new LineDataSet(entries,"Weight(KG)");
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSet.setColor(Color.rgb(130,102,255));
        dataSet.setValueTextColor(Color.rgb(130,102,255));
        dataSet.setCircleColor(Color.rgb(130,102,255));
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCubicIntensity(1f);

        lineData = new LineData(dataSet);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    private void AddDataToBarChart(){
        List<BarEntry> barEntries = new ArrayList<>();

        for(int i =0; i<listItem.size(); i++ ){
            barEntries.add( new BarEntry(i,i*100));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"kcal");
       barDataSet.setColor(Color.rgb(255,103,20));
       barDataSet.setValueTextColor(Color.rgb(255,103,20));
        barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);


    }

    private String[] DateToString(){
        String dateArray [] = new String[listItem.size()];
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString;
           for(int i=0 ; i<listItem.size(); i++){
                dateString = formatter.format(listItem.get(i).getDate());
               dateArray[i]= dateString;
           }
           return dateArray;
    }

    private void CombineChart(){

        AddDataToLineChart();
        AddDataToBarChart();

        CombinedData combinedData = new CombinedData();

        combinedData.setData(barData);
        combinedData.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setSpaceMin(barData.getBarWidth()/2f);
        xAxis.setSpaceMax(barData.getBarWidth() /2f);

        chart.setBackgroundColor(Color.WHITE);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setSpaceBottom(0f);
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawBorders(true);
        chart.setData(combinedData);
        chart.getDescription().setEnabled(false);

        AnimateChart();

        chart.invalidate();
        chart.setVisibleXRangeMaximum(4f);
        chart.moveViewToX(listItem.size());
    }

   private void UpdateChart(){
        /*TODO
        * Add the items to the dataset
        * notifychart
        * update it
        * */
   }

    public static void AnimateChart(){
        chart.animateXY(1000, 2000,Easing.EaseInBounce);
    }
}

