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
import android.widget.LinearLayout;
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

    private RecyclerView.LayoutManager layoutManager;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View v= inflater.inflate(R.layout.progress_fragment, container, false);

      saveButton = v.findViewById(R.id.save_button);
      weightText = v.findViewById(R.id.weight_text);
      recyclerView = v.findViewById(R.id.recyclerView);


        layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
     WeightData.read(new WeightData.FirebaseCallback() {
            @Override
            public void OnCallBack(List<Weight> list) {
                Log.d("shit",""+list.get(0).getWeight());

                adapter = new WeightAdapter(list,getActivity());
                recyclerView.setAdapter(adapter);

            }
      });


       /* Weight w1 = new Weight(1212 , new Date());
        Weight w2 = new Weight(1212 , new Date());
        Weight w3 = new Weight(1212 , new Date());
        Weight w4 = new Weight(1212 , new Date());
        Weight w5 = new Weight(1212 , new Date());
        Weight w6 = new Weight(1212 , new Date());
        Weight w17= new Weight(1212 , new Date());
        Weight w8 = new Weight(1212 , new Date());
        listItem = new ArrayList<>();
        listItem.add(w1);
        listItem.add(w2);
        listItem.add(w3);
        listItem.add(w4);listItem.add(w5);
        listItem.add(w6);listItem.add(w17);
        listItem.add(w8);*/

       /* adapter = new WeightAdapter(listItem,getActivity());
        recyclerView.setAdapter(adapter);
*/

        saveButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              float weight = Float.valueOf(weightText.getText().toString());
              ProgressViewModel.NewWeight(weight);
              // on click hämta ny list och update
              // skapa en funktion som returner en lista och antal rader förändrade
              // adapter.notifyIteminserste(antal objekt, list.size())


              listItem = new ArrayList<>(WeightData.getNewList());
              Log.d("newLIST", ""+WeightData.getNewList().get(0));
                adapter.notifyItemChanged(WeightData.getNewList().size() - 1);
          }
      });



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       //mViewModel = ViewModelProviders.of(this).get(ProgressViewModel.class);
        // TODO: Use the ViewModel

    }



}

