package com.example.foodiesapp.model.ingredient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
   @SerializedName("meals")
    List<Ingredient> ingredients;

    public IngredientResponse(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
