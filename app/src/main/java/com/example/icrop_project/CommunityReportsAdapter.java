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

public class CommunityReportsAdapter extends RecyclerView.Adapter<CommunityReportsAdapter.ViewHolder> {

    Context context;
    ArrayList<CropData> cropReports;

    public CommunityReportsAdapter(Context context, ArrayList<CropData> cropReports) {
        this.context = context;
        this.cropReports = cropReports;
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }

    private CommunityReportsAdapter.OnItemClickListener listener;

    public CommunityReportsAdapter(Context context, ArrayList<CropData> cropReports, CommunityReportsAdapter.OnItemClickListener listener) {
        this.context = context;
        this.cropReports = cropReports;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_reportlist, parent, false);
        return new CommunityReportsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityReportsAdapter.ViewHolder holder, int position) {
        CropData list = cropReports.get(position);
        holder.cropType.setText(list.getCropType());
        holder.reportID.setText(list.getReportID());
        holder.dateSubmitted.setText(list.getDateTimeReported());


        holder.model = list;

    }

    @Override
    public int getItemCount() {
        return cropReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cropType, dateSubmitted, reportID;
        CropData model;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            cropType = itemView.findViewById(R.id.cropListID );
            dateSubmitted = itemView.findViewById(R.id.dateReported);
            reportID = itemView.findViewById(R.id.listReportID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), CommunityReportsDetailActivity.class);
                    intent.putExtra("model", model);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
