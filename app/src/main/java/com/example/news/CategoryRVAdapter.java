package com.example.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private Context context;
    public ArrayList<CategoryRVModal> categoryRVModalArrayList;

    private CategoryOnClickInterface categoryOnClickInterface;

    public CategoryRVAdapter(Context context, ArrayList<CategoryRVModal> categoryRVModalArrayList, CategoryOnClickInterface categoryOnClickInterface) {
        this.context = context;
        this.categoryRVModalArrayList = categoryRVModalArrayList;
        this.categoryOnClickInterface = categoryOnClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int p) {
        int position=holder.getAdapterPosition();
        CategoryRVModal categoryRVModal=categoryRVModalArrayList.get(position);
        holder.categoryTV.setText(categoryRVModal.getCategory());
//        Picasso.get().load(categoryRVModal.getCategoryUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryOnClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModalArrayList.size();
    }

    public interface CategoryOnClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryTV;
        private ImageView categoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV=itemView.findViewById(R.id.CategoryTV);
            categoryIV=itemView.findViewById(R.id.CategoryIV);
        }
    }
}
