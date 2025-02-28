package com.example.foodiesapp.mealDetails.view;

import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

public interface MealDetailsContract {
    void assignIngredientsRecyclerView(List<String> ingredients);
    void updateUI(Meal meal);
    void setupVideo(String videoLink);
    void updateFavoriteStateToRemoved();
    void updateFavoriteStateToAdded();
    void showToast(String msg);
}
