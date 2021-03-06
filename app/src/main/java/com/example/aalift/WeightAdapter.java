package com.example.aalift;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.ViewHolder>{

    public ArrayList<Weight> getListItem() {
        return new ArrayList<>(listItem);
    }

    private ArrayList<Weight> listItem;
    private Context context;


     // constructer for the Adapter, list and context
    public WeightAdapter(ArrayList<Weight> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;

    }
    // when we create the adapter we get back a view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.list_item,viewGroup,false);

        return new ViewHolder(v);
    }

    // binding the list
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Weight weight = listItem.get(i);
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        viewHolder.textHead.setText(Float.toString(weight.getWeight()) + context.getString(R.string.kg));
        viewHolder.textDesc.setText(dtf.format( weight.getDate()));
    }
    // size of the list
    @Override
    public int getItemCount() {
//        Log.d("ABC", "getItemCount:"+listItem.size());
        return listItem.size();
    }

    // gettting the texthead and the textdesc
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textHead;
        public TextView textDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textDesc = (TextView) itemView.findViewById(R.id.textViewDes);
        }
    }
}
