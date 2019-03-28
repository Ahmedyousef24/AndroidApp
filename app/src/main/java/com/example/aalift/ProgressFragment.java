package com.example.aalift;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.CombinedChart;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {

    private FloatingActionButton addWeightButton;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    private final String TAG = "PARS";
    private static WeightAdapter adapter;
    private boolean flag = false;

    public static ArrayList<Weight> getListItem() {
        return listItem;
    }

    private static ArrayList<Weight> listItem;

    private RecyclerView.LayoutManager layoutManager;
    private Handler handler = new Handler();
    private int progressStatus = 0;
    private ProgressBar progressBar;
    private PieChartFragment pieChartFragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);


     if(savedInstanceState!= null){
         listItem = savedInstanceState.getParcelableArrayList("listItem");
         adapter.notifyItemChanged(0);
         Log.d("savelist","listefter"+listItem.size());
     }
     else{
         listItem = new ArrayList<>();
         GetList();
         animate();
     }
    // animateLoading();
        progressBar = v.findViewById(R.id.progressBar);
        recyclerView = v.findViewById(R.id.recyclerView);
        tabLayout = v.findViewById(R.id.tablayout);
        addWeightButton = v.findViewById(R.id.add_weight);
        pieChartFragment = new PieChartFragment();

        adapter = new WeightAdapter(listItem, getActivity());

        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Log.d("listsize",""+listItem.size());

        mPager = v.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        mPager.setAdapter(pagerAdapter);
        //SetFragment(combinedChartFragment);
        mPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mPager.setCurrentItem(tab.getPosition());
                int piePage = 1;
                if(tab.getPosition() == piePage){
                    Log.d("animate","animate");
                    PieChartFragment.animate();
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }
        });


        addWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightPicker weightPicker = new WeightPicker();
                weightPicker.show(getFragmentManager(), "HEJ");
            }
        });

        recyclerView.setAdapter(adapter);
        return v;
    }
  /*  private void animateLoading(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus <100){
                    progressStatus++;
                    android.os.SystemClock.sleep(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                }
            }
        }).start();
    }*/

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listItem",  listItem);
        Log.d("saveList","savinglist");


    }

    private void SetFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pager, fragment);
        fragmentTransaction.commit();
    }


    // Adding new Weight and add it to the list
    public static void NewWeight(int weightInteger, int WeightDecimal) {

        float weight = FromIntToFloat(weightInteger, WeightDecimal);
        Date date = new Date();

        // pass this to the update chart function
        Weight weightObject = new Weight(weight, date);
        WeightData.AddWeight(weightObject);

        int insertIndex = 0;
        listItem.add(insertIndex, weightObject);

        adapter.notifyItemInserted(insertIndex);
    }

    private static float FromIntToFloat(int integer, int decimal) {
        StringBuilder value = new StringBuilder();

        value.append(integer);
        value.append(".");
        value.append(decimal);

        float weight = Float.parseFloat(value.toString());
        return weight;
    }

    private void animate() {

    }
    // GetLIST FUNCTION
    private void GetList() {

        WeightData.read(new WeightData.FirebaseCallback() {
            @Override
            public void OnCallBack(List<Weight> list) {
                listItem.addAll(list);
                Log.d("listsize",""+listItem.size());
                adapter.notifyItemChanged(0);
                flag = true;
                Log.d("flag",""+flag);
            }
        });
    }

}

