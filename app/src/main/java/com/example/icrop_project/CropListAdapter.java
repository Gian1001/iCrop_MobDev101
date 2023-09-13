package com.example.icrop_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CropListAdapter extends RecyclerView.Adapter<CropListAdapter.MyViewHolder> {

    Context context;
    ArrayList<CropData> cropReports;

    public CropListAdapter(Context context, ArrayList<CropData> cropReports) {
        this.context = context;
        this.cropReports = cropReports;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_reportlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CropData list = cropReports.get(position);
        holder.cropType.setText(list.getCropType());
        holder.reportID.setText(list.getDatePlanted());
        holder.dateSubmitted.setText(list.getDateHarvest());

    }

    @Override
    public int getItemCount() {
        return cropReports.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cropType, dateSubmitted, reportID;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            cropType = itemView.findViewById(R.id.cropListID );
            dateSubmitted = itemView.findViewById(R.id.dateReported);
            reportID = itemView.findViewById(R.id.listReportID);
        }
    }
}
