package com.example.icrop_project;

import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return cropInformationDataArrayList.size();
    }

    public static class MyCropInfoListViewHolder extends RecyclerView.ViewHolder {
        TextView cropName, cropDescription, cropEndDate, cropSoilType, cropStartDate, cropSunlight, cropTemp, cropType, cropWatering;

        public MyCropInfoListViewHolder(@NonNull View itemView) {
            super(itemView);

            cropName = itemView.findViewById(R.id.cropInformationName);
//            cropDescription = itemView.findViewById(R.id.);
//            cropEndDate = itemView.findViewById(R.id.cropInformationName);
            cropSoilType = itemView.findViewById(R.id.soilTypeInformation);
//            cropStartDate = itemView.findViewById(R.id.cropInformationName);
//            cropSunlight = itemView.findViewById(R.id.cropInformationName);
//            cropTemp = itemView.findViewById(R.id.cropInformationName);
            cropType = itemView.findViewById(R.id.cropInformationType);
//            cropWatering = itemView.findViewById(R.id.cropInformationName);

        }
    }
}
