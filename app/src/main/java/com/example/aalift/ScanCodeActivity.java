package com.example.aalift;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    public static final int PERMISSION_REQUEST_CAMERA = 1;
    private  String TAG = "SCAN";
    private ScanData process;
    private  Intent intent;
    private String url = "https://world.openfoodfacts.org/api/v0/product/";
private ScanData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         process = new ScanData();
        intent = new Intent(ScanCodeActivity.this,NutritionActivity.class);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        // Request permission. This does it asynchronously so we have to wait for onRequestPermissionResult before trying to open the camera.
        if (!haveCameraPermission()){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
        viewNutrition();
    }

    private boolean haveCameraPermission()
    {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        // This is because the dialog was cancelled when we recreated the activity.
        if (permissions.length == 0 || grantResults.length == 0){
            return;
        }

        switch (requestCode)
        {
            case PERMISSION_REQUEST_CAMERA:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                }
                else {
                    finish();
                }
            }
            break;
        }
    }

    public void startCamera()
    {
        scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        scannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void handleResult(Result result) {

        url = url + result.getText();
        Log.d(TAG, "handleResult: "+url);

        process.setUrl(url);
        process.execute();
        onBackPressed();
    }

    public void viewNutrition() {
        ScanData.setCustomListener(new ScanData.CallBack(){
            @Override
            public void onDataLoaded(ArrayList<Nutrition> list) {
                intent.putExtra("nutritionList",list);
            }

            @Override
            public void onDataReady(Boolean flag) {
                if(flag){
                    startActivity(intent);
                    finish();
                }
                else{
                    finish();
                }
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();


    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    private void Fetch(String url){
    }

}
