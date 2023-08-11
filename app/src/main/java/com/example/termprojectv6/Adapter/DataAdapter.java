package com.example.termprojectv6.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termprojectv6.Model.DataModel;
import com.example.termprojectv6.R;
import com.example.termprojectv6.SecondActivity;
import com.example.termprojectv6.Utilility.DatabaseHelper;

import java.util.List;

public class DataAdapter  extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<DataModel> dataList;
    private SecondActivity activity;
    private DatabaseHelper db;

    public DataAdapter(DatabaseHelper db, SecondActivity activity) {
        this.activity = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataModel item = dataList.get(position);
        holder.itemID.setText(item.getId());
        holder.itemDate.setText(item.getDate().toString());
        holder.itemWeight.setText(String.valueOf(item.getWeight()));
        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteEntry(item.getId());
            }
        });
    }

    public boolean toBoolean(int num) {
        return num != 0;
    }

    public Context getContext() {
        return activity;
    }

    public void setEntries(List<DataModel> mList) {
        this.dataList = mList;
        notifyDataSetChanged();
    }

    public void deleteEntry(int position) {
        DataModel item = dataList.get(position);
        db.deleteEntry(item.getId());
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {

        DataModel item = dataList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("date", item.getDate().toString());
        bundle.putFloat("weight", item.getWeight());

//        AddNewEntry task = new AddNewEntry();
//        task.setArguments(bundle);
//        task.show(activity.getSupportFragmentManager(), task.getTag());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView itemID;
        TextView itemDate;
        TextView itemWeight;
        ImageView itemDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            itemDate = itemView.findViewById(R.id.itemDate);
            itemWeight = itemView.findViewById(R.id.itemAvgWeight);
            itemDelete = itemView.findViewById(R.id.itemDelete);
        }
    }
}
