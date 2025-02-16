package com.example.foodiesapp.search.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.R;
import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.search.view.SearchContract;

import java.util.ArrayList;
import java.util.List;

public class CategoryCardsAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    Context context;
    SearchContract contract;
    List<Category> mealsList;

    public CategoryCardsAdapter(Context context, SearchContract contract) {
        this.context = context;
        this.contract = contract;
        this.mealsList = new ArrayList<>();
    }

    public void setList(List<Category> newList){
        mealsList.clear();
        mealsList.addAll(newList);
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_card, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        Category category = mealsList.get(position);
        holder.categoryTitle.setText(category.getStrCategory());
        // loading image
        Glide.with(context).load(category.getStrCategoryThumb()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}

class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    CardView categoryCard;
    ImageView categoryImage;
    TextView categoryTitle;

    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryCard = itemView.findViewById(R.id.category_card);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_name);
    }
}
