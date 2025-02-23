package com.example.foodiesapp.mealResults.view.adapter;

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
import com.example.foodiesapp.mealResults.view.ResultsContract;
import com.example.foodiesapp.model.meal.FilteredMeal;
import com.example.foodiesapp.model.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealResultsAdapter extends RecyclerView.Adapter<MealResultsViewHolder>{
    Context context;
    ResultsContract contract;
    List<FilteredMeal> resultsList = new ArrayList<>();

    public MealResultsAdapter(Context context,ResultsContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void updateList(List<FilteredMeal> newList){
        resultsList.clear();
        resultsList.addAll(newList);
    }

    @NonNull
    @Override
    public MealResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_meal_card,parent,false);
        return new MealResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealResultsViewHolder holder, int position) {
        FilteredMeal meal = resultsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealOrigin.setText(meal.getStrMeal());
        holder.mealCategory.setText(meal.getStrMeal());
        // loading image
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        holder.card.setOnClickListener(view -> {
            contract.navigateToMealDetails(meal.getIdMeal());
        });
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}

class MealResultsViewHolder extends RecyclerView.ViewHolder {
    CardView card;
    ImageView mealImage;
    TextView mealName;
    TextView mealOrigin;
    TextView mealCategory;
    public MealResultsViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.meal_card);
        mealImage = itemView.findViewById(R.id.meal_image);
        mealName = itemView.findViewById(R.id.meal_name);
        mealOrigin = itemView.findViewById(R.id.meal_origin);
        mealCategory = itemView.findViewById(R.id.meal_category_tv);
    }
}
