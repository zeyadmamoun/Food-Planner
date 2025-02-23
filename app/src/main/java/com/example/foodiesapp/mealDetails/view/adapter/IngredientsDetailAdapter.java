package com.example.foodiesapp.mealDetails.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.R;
import com.example.foodiesapp.mealDetails.view.MealDetailsContract;

import java.util.ArrayList;
import java.util.List;

public class IngredientsDetailAdapter extends  RecyclerView.Adapter<IngredientViewHolder>{

    private final Context context;
    private List<String> ingredientList = new ArrayList<>();

    public IngredientsDetailAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<String> ingredientList){
        this.ingredientList.clear();
        this.ingredientList.addAll(ingredientList);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient_small_card,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient = ingredientList.get(position);
        holder.ingredientName.setText(ingredient);

        String imgUrl = "https://www.themealdb.com/images/ingredients/"+ingredient+"-Small.png";
        Glide.with(context).load(imgUrl).into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
    ImageView ingredientImage;
    TextView ingredientName;
    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientImage= itemView.findViewById(R.id.small_ingredient_card_image);
        ingredientName= itemView.findViewById(R.id.small_ingredient_card_name);
    }
}
