package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HottestNewsRVAdapter extends RecyclerView.Adapter<HottestNewsRVAdapter.ViewHolder> {
    private ArrayList<Article> articlesArrayList;
    private Context context;

    public HottestNewsRVAdapter(ArrayList<Article> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HottestNewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hottest_news, parent,false);
        return new HottestNewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HottestNewsRVAdapter.ViewHolder holder, int position) {
        Article hottestArticle =articlesArrayList.get(position);
        holder.headingTV.setText(hottestArticle.getTitle());
        Picasso.get().load(hottestArticle.getUrlToImage()).into(holder.imageIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, NewsDetailActivity.class);
                i.putExtra("title", hottestArticle.getTitle());
                i.putExtra("content", hottestArticle.getContent());
                i.putExtra("desc", hottestArticle.getDescription());
                i.putExtra("url", hottestArticle.getUrl());
                i.putExtra("image", hottestArticle.getUrlToImage());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView headingTV;
        private ImageView imageIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headingTV=itemView.findViewById(R.id.HotNewsHeadTV);
            imageIV=itemView.findViewById(R.id.HotNewsImgIV);
        }
    }
}
