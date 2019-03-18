package com.example.aalift;

import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


    private int numTabs;

    public ScreenSlidePagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CombinedChartFragment combinedChartFragment = new CombinedChartFragment();
                return combinedChartFragment;
            case 1:
                PieChartFragment pieChartFragment = new PieChartFragment();

                return pieChartFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
