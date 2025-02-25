package com.example.foodiesapp.profile.view.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.R;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.profile.view.FavoriteContract;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealViewHolder> {
    Context context;
    FavoriteContract contract;
    List<Meal> favoriteMeals = new ArrayList<>();

    public FavoriteMealsAdapter(Context context, FavoriteContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void setList(List<Meal> meals){
        favoriteMeals.clear();
        favoriteMeals.addAll(meals);
    }

    @NonNull
    @Override
    public FavoriteMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plan_meal_card, parent, false);
        return new FavoriteMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealViewHolder holder, int position) {
        Meal meal = favoriteMeals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealOrigin.setText(meal.getStrArea());
        // loading image
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        holder.card.setOnClickListener(view -> contract.navigateToMealDetail(meal.getIdMeal()));
        holder.removeBtn.setOnClickListener(view -> contract.removeMealFromFavorite(meal));
    }

    @Override
    public int getItemCount() {
        return favoriteMeals.size();
    }
}

class FavoriteMealViewHolder extends RecyclerView.ViewHolder{
    CardView card;
    ImageView mealImage;
    TextView mealName;
    TextView mealOrigin;
    Button removeBtn;

    public FavoriteMealViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.meal_card);
        mealImage = itemView.findViewById(R.id.meal_image);
        mealName = itemView.findViewById(R.id.meal_name);
        mealOrigin = itemView.findViewById(R.id.meal_origin);
        removeBtn = itemView.findViewById(R.id.remove_btn);
    }
}
