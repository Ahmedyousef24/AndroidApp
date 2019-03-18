package com.example.aalift;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Note> dataList;
    private String tag;
    private clickListener listener = null;

    public HomeAdapter(Context context, List<Note> dataList) {
        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_list, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = dataList.get(position);

        holder.title.setText(note.getTitle());

        holder.value.setText(note.getDescription());
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