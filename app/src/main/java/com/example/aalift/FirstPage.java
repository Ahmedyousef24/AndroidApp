package com.example.aalift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FirstPage extends AppCompatActivity {


    private Button btn;
    RecyclerView recycleBreakfast, recycleLunch, recycleDinner, recycleSnack;
    ArrayList<Note> breakfastList, lunchList, dinnerList, snackList;
    private HomeAdapter breakfastAdapter, lunchAdpater, dinnerAdapter, snackAdapter;
    private Button breakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        recycleBreakfast = (RecyclerView) findViewById(R.id.recycleBreakfast);
        recycleLunch = (RecyclerView) findViewById(R.id.recycleLunch);
        recycleDinner = (RecyclerView) findViewById(R.id.recycleDinner);
        recycleSnack = (RecyclerView) findViewById(R.id.recycleSnack);
        breakButton = findViewById(R.id.breakfastBtn);


        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();

        breakfastList.add(new Note("this is breakfast","hej"));
        lunchList.add(new Note("this is Lunch","hej"));
        dinnerList.add(new Note("this is dinner","hej"));
        snackList.add(new Note("this is snack","hej"));

        breakfastAdapter = new HomeAdapter(this, breakfastList);
        lunchAdpater = new HomeAdapter(this, lunchList);
        dinnerAdapter = new HomeAdapter(this, dinnerList);
        snackAdapter = new HomeAdapter(this, snackList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        recycleSnack.setLayoutManager(linearLayoutManager);
        recycleDinner.setLayoutManager(linearLayoutManager1);
        recycleLunch.setLayoutManager(linearLayoutManager2);
        recycleBreakfast.setLayoutManager(linearLayoutManager3);

        recycleSnack.setAdapter(snackAdapter);
        recycleDinner.setAdapter(dinnerAdapter);
        recycleLunch.setAdapter(lunchAdpater);
        recycleBreakfast.setAdapter(breakfastAdapter);

        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfastList.add(new Note("this is break ","hej"));
                breakfastAdapter.notifyDataSetChanged();

            }
        });
    }
}






