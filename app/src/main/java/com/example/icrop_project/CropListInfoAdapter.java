package com.example.icrop_project;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CropListInfoAdapter extends RecyclerView.Adapter<CropListInfoAdapter.MyCropInfoListViewHolder> {


    Context context;

    ArrayList<CropInformationData> cropInformationDataArrayList;

    public CropListInfoAdapter(Context context, ArrayList<CropInformationData> cropInformationArrayList) {
        this.context = context;
        this.cropInformationDataArrayList = cropInformationArrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private CropListInfoAdapter.OnItemClickListener listener;

    public CropListInfoAdapter(Context context, ArrayList<CropInformationData> cropInformationArrayList, OnItemClickListener listener) {
        this.context = context;
        this.cropInformationDataArrayList = cropInformationArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CropListInfoAdapter.MyCropInfoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crop_information_cardview, parent, false);
        return new MyCropInfoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropListInfoAdapter.MyCropInfoListViewHolder holder, int position) {

        CropInformationData list = cropInformationDataArrayList.get(position);
        holder.cropName.setText(list.getName());
        holder.cropSoilType.setText(list.getSoil_type());
        holder.cropType.setText(list.getType());


        holder.model = list;
    }

    @Override
    public int getItemCount() {
        return cropInformationDataArrayList.size();
    }

    public static class MyCropInfoListViewHolder extends RecyclerView.ViewHolder {
        TextView cropName, cropDescription, cropEndDate, cropSoilType, cropStartDate, cropSunlight, cropTemp, cropType, cropWatering;

        CropInformationData model;
        public MyCropInfoListViewHolder(@NonNull View itemView) {
            super(itemView);

            cropName = itemView.findViewById(R.id.cropInformationName);

            cropSoilType = itemView.findViewById(R.id.soilTypeInformation);
            cropType = itemView.findViewById(R.id.cropInformationType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), CropInfoDetailActivity.class);
                    intent.putExtra("model", model);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
