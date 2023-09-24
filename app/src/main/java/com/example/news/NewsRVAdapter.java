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

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    private ArrayList<Article> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Article> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item, parent,false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Article article=articlesArrayList.get(position);
        holder.newsDesTV.setText(article.getDescription());
        holder.newsHeadTV.setText(article.getTitle());
        Picasso.get().load(article.getUrlToImage()).into(holder.newsImgIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, NewsDetailActivity.class);
                i.putExtra("title",article.getTitle());
                i.putExtra("content",article.getContent());
                i.putExtra("desc",article.getDescription());
                i.putExtra("url",article.getUrl());
                i.putExtra("image",article.getUrlToImage());
                context.startActivity(i);
            }
        });
        holder.favIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag()==null||(int) view.getTag() == R.drawable.baseline_favorite_border_24){
                    view.setTag(R.drawable.baseline_favorite_24);
                    holder.favIV.setImageResource(R.drawable.baseline_favorite_24);
                }
                else {
                    view.setTag(R.drawable.baseline_favorite_border_24);
                    holder.favIV.setImageResource(R.drawable.baseline_favorite_border_24);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView newsHeadTV, newsDesTV;
        private ImageView newsImgIV, favIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsDesTV=itemView.findViewById(R.id.NewsDesTV);
            newsHeadTV=itemView.findViewById(R.id.NewsHeadingTV);
            newsImgIV=itemView.findViewById(R.id.NewsImageIV);
            favIV=itemView.findViewById(R.id.favIV);
        }
    }
}
