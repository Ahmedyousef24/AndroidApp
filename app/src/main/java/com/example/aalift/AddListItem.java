package com.example.aalift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddListItem extends AppCompatActivity {

    EditText et;
    private Button bt;
    ListView lv;
    ArrayList<String> arrayList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_item);

        et = (EditText) findViewById(R.id.txtinput);
        bt = (Button) findViewById(R.id.Add_food2);
        lv = (ListView) findViewById(R.id.listView_lv);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        lv.setAdapter(adapter);

        onBtnClick();

    }

    public void onBtnClick() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = et.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
