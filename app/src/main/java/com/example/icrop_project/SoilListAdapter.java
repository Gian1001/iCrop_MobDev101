package com.example.icrop_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SoilListAdapter extends RecyclerView.Adapter<SoilListAdapter.SoilInfoListViewHolder> {

    Context context;
    ArrayList<SoilData> soilData;
    public SoilListAdapter(Context context, ArrayList<SoilData> soilInfoArrayList){
        this.context = context;
        this.soilData = soilInfoArrayList;

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private SoilListAdapter.OnItemClickListener listener;

    public SoilListAdapter(Context context, ArrayList<SoilData> soilDataArrayList, OnItemClickListener listener){
        this.context = context;
        this.soilData = soilDataArrayList;
        this.listener = listener;
    }



    @NonNull
    @Override
    public SoilListAdapter.SoilInfoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.soil_list_cardview, parent, false);
        return new SoilInfoListViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SoilListAdapter.SoilInfoListViewHolder holder, int position) {
        SoilData list = soilData.get(position);
        holder.name.setText(list.getName());
        holder.best_crops.setText(list.getBest_crops());

        holder.model = list;
    }

    @Override
    public int getItemCount() {
        return soilData.size();
    }

    public class SoilInfoListViewHolder extends  RecyclerView.ViewHolder {
        TextView best_crops, common_issues, description, name, nutrient_content, organic_matter_content, ph_range;
        SoilData model;
        public SoilInfoListViewHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.soilInfoName);
            best_crops = itemView.findViewById(R.id.bestCropInfo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), SoilInfoDetailActivity.class);
                    intent.putExtra("model", model);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
