package com.example.icrop_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.NewsViewHolder> {
    private List<NewsRVModel> newsRVItemList;

    // Constructor to initialize the data source
    public NewsRVAdapter(Context context, List<NewsRVModel> newsItemList) {
        this.newsRVItemList = newsItemList;
    }

    // ViewHolder class
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView;
        public TextView descriptionTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_news_rv, parent, false);
        return new NewsViewHolder(itemView);
    }

    @NonNull


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsRVModel newsItem = newsRVItemList.get(position);

        holder.imageView.setImageResource(newsItem.getImageResource());
        holder.titleTextView.setText(newsItem.getTitle());
        holder.descriptionTextView.setText(newsItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsRVItemList.size();
    }
}

