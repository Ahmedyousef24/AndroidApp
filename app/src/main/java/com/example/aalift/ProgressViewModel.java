package com.example.aalift;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public class ProgressViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // save all ProgressFragment data here

    private static DatabaseReference db;
    private static String TAG = "DATABASE";

    public static void NewWeight(float weight){
       Date date = new Date();
       Weight weightobj = new Weight(weight,date);
        WeightData.AddWeight(weightobj);
    }
}

