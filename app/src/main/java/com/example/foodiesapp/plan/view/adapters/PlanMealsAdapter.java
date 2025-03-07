package com.example.foodiesapp.plan.view.adapters;

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
import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.plan.view.PlanContract;

import java.util.ArrayList;
import java.util.List;

public class PlanMealsAdapter extends RecyclerView.Adapter<PlanMealViewHolder> {

    Context context;
    PlanContract contract;
    List<DatabaseMeal> mealsList;

    public PlanMealsAdapter(Context context, PlanContract contract) {
        this.context = context;
        this.contract = contract;
        mealsList = new ArrayList<>();
    }

    public void setList(List<DatabaseMeal> newList) {
        mealsList.clear();
        mealsList.addAll(newList);
    }

    @NonNull
    @Override
    public PlanMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plan_meal_card, parent, false);
        return new PlanMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMealViewHolder holder, int position) {
        DatabaseMeal meal = mealsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealOrigin.setText(meal.getStrArea());
        // loading image
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        holder.card.setOnClickListener(view -> {
            contract.navigateToMealDetail(meal.getIdMeal());
        });

        holder.removeBtn.setOnClickListener(view -> {
            contract.removeMeal(meal);
        });
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}

class PlanMealViewHolder extends RecyclerView.ViewHolder {
    CardView card;
    ImageView mealImage;
    TextView mealName;
    TextView mealOrigin;
    Button removeBtn;

    public PlanMealViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.meal_card);
        mealImage = itemView.findViewById(R.id.meal_image);
        mealName = itemView.findViewById(R.id.meal_name);
        mealOrigin = itemView.findViewById(R.id.meal_origin);
        removeBtn = itemView.findViewById(R.id.remove_btn);
    }
}

