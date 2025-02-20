package com.example.foodiesapp.search.view.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.foodiesapp.model.ingredient.Ingredient;
import com.example.foodiesapp.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientCardViewHolder> {
    Context context;
    List<Ingredient> ingredientList = new ArrayList<>();

    public IngredientsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_card, parent, false);
        return new IngredientCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientCardViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientName.setText(ingredient.getStrIngredient());

        String imgUrl = "https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png";
        Glide.with(context).load(imgUrl).into(holder.ingredientImage);
    }

    public void setList(List<Ingredient> newList) {
        Log.i(SearchPresenter.TAG, "setList: from ingredientAdapter " + newList.size());
        ingredientList.clear();
        ingredientList.addAll(newList);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}

class IngredientCardViewHolder extends RecyclerView.ViewHolder {
    CardView ingredientCard;
    ImageView ingredientImage;
    TextView ingredientName;

    public IngredientCardViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredientCard = itemView.findViewById(R.id.category_card);
        ingredientImage = itemView.findViewById(R.id.category_image);
        ingredientName = itemView.findViewById(R.id.category_name);
    }
}
