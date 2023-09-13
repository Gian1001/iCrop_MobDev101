package com.example.icrop_project;

import android.content.Context;
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

    @NonNull
    @Override
    public SoilListAdapter.SoilInfoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.soil_list_cardview, parent, false);
        return new SoilInfoListViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SoilListAdapter.SoilInfoListViewHolder holder, int position) {
        SoilData list = soilData.get(position);
        holder.name.setText(list.getName());
//        holder.best_crops.setText(list.getBest_crops());
    }

    @Override
    public int getItemCount() {
        return soilData.size();
    }

    public class SoilInfoListViewHolder extends  RecyclerView.ViewHolder {
        TextView best_crops, common_issues, description, name, nutrient_content, organic_matter_content, ph_range;

        public SoilInfoListViewHolder(@NonNull View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.soilInfoName);
//            best_crops = itemView.findViewById(R.id.bestCropInfo);


        }
    }
}
