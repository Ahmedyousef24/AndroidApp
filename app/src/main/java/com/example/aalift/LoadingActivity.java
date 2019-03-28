package com.example.aalift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    private Intent intent;
    private ProgressBar progressBar;
    private Handler handler;
    private ScanData scanData;
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        intent = new Intent(LoadingActivity.this,NutritionActivity.class);
        progressBar = findViewById(R.id.progressBar);
        handler = new Handler();
        scanData = new ScanData();
        tag = getIntent().getStringExtra("meal");
        intent.putExtra("meal", tag);
        viewNutrition();
    }

    public void viewNutrition() {

        scanData.setCustomListener(new ScanData.CallBack(){
            @Override
            public void onDataLoaded(ArrayList<Nutrition> list) {
                intent.putExtra("nutritionList",list);
                startActivity(intent);
                finish();
            }

            @Override
            public void onDataReady(Boolean flag) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
