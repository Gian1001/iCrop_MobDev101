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

public class PestAdapter extends RecyclerView.Adapter<PestAdapter.PestViewHolder> {


    private Context context;
    ArrayList<PestData> pestData;


    public PestAdapter(Context context, ArrayList<PestData> pestData) {
        this.context = context;
        this.pestData = pestData;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private PestAdapter.OnItemClickListener listener;
    @NonNull
    @Override
    public PestAdapter.PestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pest_diseases_cardview, parent, false);
        return new PestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PestAdapter.PestViewHolder holder, int position) {

        PestData list = pestData.get(position);
        holder.name.setText(list.getName());
//        holder.damageSymptoms.setText(list.getDamage_symptoms());

        holder.model = list;
    }

    @Override
    public int getItemCount() {
        return pestData.size();
    }

    public static class PestViewHolder extends RecyclerView.ViewHolder {

        TextView affectPlants, controlMethods, damageSymptoms, description, name, preventionMethods;
        PestData model;

        public PestViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pestInfoName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), PestInfoDetailActivity.class);
                    intent.putExtra("model", model);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
