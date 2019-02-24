package com.example.aalift;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {

    private ProgressViewModel mViewModel;
    private Button saveButton;
    private TextView weightText;
    private RecyclerView recyclerView;

    private final String TAG = "PARS";
    public WeightAdapter adapter;
    private List<Weight>listItem;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View v= inflater.inflate(R.layout.progress_fragment, container, false);

      saveButton = v.findViewById(R.id.save_button);
      weightText = v.findViewById(R.id.weight_text);
      recyclerView = v.findViewById(R.id.recyclerView);


      saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              float weight = Float.valueOf(weightText.getText().toString());
              ProgressViewModel.NewWeight(weight);

          }
      });
      ;
     //   Log.d("shit",""+listItem.size());

      //read();


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       //mViewModel = ViewModelProviders.of(this).get(ProgressViewModel.class);
        // TODO: Use the ViewModel

    }

}

