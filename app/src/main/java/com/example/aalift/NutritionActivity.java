package com.example.aalift;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NutritionActivity extends AppCompatActivity {


    private RecyclerView recyclerView;


    private static NutritionAdapter foodAdapter;
    private Button btn;
    private TextView textViewTitle;
    public static TextView textViewValue;
    private NutritionData data;
    private String tag = "scan";




    private static ArrayList<Nutrition> foodList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView = findViewById(R.id.recyclerViewFood);
        btn = findViewById(R.id.doneButton);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewValue = findViewById(R.id.textViewValue);

        textViewTitle.setText(R.string.serving_size);
        textViewValue.setText(R.string.serving_value);

        data = new NutritionData();
        foodList = (ArrayList<Nutrition>) getIntent().getSerializableExtra("nutritionList");
        foodAdapter = new NutritionAdapter(this, foodList, tag);

        if(foodList.size()== 0){
            onDataNotFound();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.AddFood(foodList, "Lunch");
                finish();
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(foodAdapter);
    }

    public static void updateData(ArrayList<Nutrition> list) {
        foodList.clear();
        foodList.addAll(list);
        foodAdapter.notifyDataSetChanged();

    }

    public static ArrayList<Nutrition> getFoodList() {
        return NutritionActivity.foodList;
    }

    public void onBtnClicked(View view) {
        ServingPicker picker = new ServingPicker();
        picker.show(getSupportFragmentManager(), "dont");

    }

    //handel Data
    private void onDataNotFound() {
        Toast.makeText(NutritionActivity.this, R.string.data_not_found, Toast.LENGTH_LONG).show();
        finish();
    }

}
