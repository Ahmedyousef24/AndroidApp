package com.example.aalift;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstPage extends AppCompatActivity {

    EditText et;
    private Button btn;
    ListView lv;
    ArrayList<String> arrayList;

    ArrayAdapter<String> adapter;

    private List<Note> listItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.setHasFixedSize(true);

        Note n1 = new Note("BREAKFAST");
        Note n2 = new Note("LUNCH");
        Note n3 = new Note("DINNER");
        Note n4 = new Note("SNACK");

        List listItem = new ArrayList<>();
        listItem.add(n1);
        listItem.add(n2);
        listItem.add(n3);
        listItem.add(n4);

        // istället för this när man använder fragment så skriver man getContext()
        NoteAdapter adapter = new NoteAdapter(this, listItem);
        recyclerView.setAdapter(adapter);

      //  onBtnClick2();

    }
//
//    public void onBtnClick2() {
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String result = et.getText().toString();
//                arrayList.add(result);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
}




