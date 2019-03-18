package com.example.aalift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.ViewHolder> {
    private Context context;
    private List<Nutrition> dataList;
    private String tag;
    private clickListener listener = null;

    public NutritionAdapter(Context context, List<Nutrition> dataList, String tag) {
        this.context = context;
        this.dataList = dataList;
        this.tag = tag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nutrition food = dataList.get(position);

        holder.title.setText(food.getName());

        if (tag == "scan") {
            if (position != 1) {
                holder.value.setText(String.format("%.2f", food.getValue()) + context.getString(R.string.gram));
            } else {
                holder.value.setText(String.format("%.2f", food.getValue()) + context.getString(R.string.kcal));
            }
        } else {
            holder.value.setText(String.format("%.2f", food.getValue()) + context.getString(R.string.kcal));
        }
    }

    public void itemClicked(clickListener clickListener) {
        listener = clickListener;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textViewTitle);
            value = itemView.findViewById(R.id.textViewValue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClicked(getAdapterPosition());
            }
        }
    }

    public interface clickListener {
        void onClicked(int position);
    }

}
