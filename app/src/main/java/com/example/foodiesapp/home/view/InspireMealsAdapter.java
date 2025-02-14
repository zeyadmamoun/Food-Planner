package com.example.foodiesapp.home.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.R;
import com.example.foodiesapp.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class InspireMealsAdapter extends RecyclerView.Adapter<InspireMealViewHolder> {
    Context context;
    HomeContract contract;
    List<Meal> mealsList;

    public InspireMealsAdapter(Context context, HomeContract contract) {
        this.context = context;
        this.contract = contract;
        this.mealsList = new ArrayList<>();
    }

    public void setList(List<Meal> newList){
        mealsList.clear();
        mealsList.addAll(newList);
    }

    @NonNull
    @Override
    public InspireMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_pager_card_item, parent, false);
        return new InspireMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspireMealViewHolder holder, int position) {
        Meal meal = mealsList.get(position);
        holder.mealTitle.setText(meal.getStrMeal());
        holder.category.setText(meal.getStrCategory());
        holder.mealOrigin.setText(meal.getStrArea());
        // loading image
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        holder.addBtn.setOnClickListener(view -> {
            contract.showToast("add button clicked");
        });

        holder.favoriteIcon.setOnClickListener(view -> {
            Drawable currentDrawable = holder.favoriteIcon.getDrawable();
            Drawable targetDrawable = ContextCompat.getDrawable(context, R.drawable.favorite_outlined_ic);
            assert targetDrawable != null;
            if (currentDrawable.getConstantState() == targetDrawable.getConstantState()) {
                holder.favoriteIcon.setImageResource(R.drawable.favorite_filled_ic);
            } else {
                holder.favoriteIcon.setImageResource(R.drawable.favorite_outlined_ic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}

class InspireMealViewHolder extends RecyclerView.ViewHolder {
    CardView mealCard;
    ImageView mealImage;
    ImageView favoriteIcon;
    TextView mealTitle;
    TextView mealOrigin;
    TextView category;
    Button addBtn;

    public InspireMealViewHolder(@NonNull View itemView) {
        super(itemView);
        mealCard = itemView.findViewById(R.id.meal_card);
        mealImage = itemView.findViewById(R.id.recipeImage);
        favoriteIcon = itemView.findViewById(R.id.favorite_icon);
        mealTitle = itemView.findViewById(R.id.recipeTitle_tv);
        mealOrigin = itemView.findViewById(R.id.recipeOrigin_tv);
        category = itemView.findViewById(R.id.card_category_tv);
        addBtn = itemView.findViewById(R.id.addIngredientsButton);
    }
}
