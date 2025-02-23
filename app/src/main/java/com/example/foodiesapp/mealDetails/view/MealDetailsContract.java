package com.example.foodiesapp.mealDetails.view;

import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

public interface MealDetailsContract {
    void assignIngredientsRecyclerView(List<String> ingredients);
    void assignStepsRecyclerView(List<String> steps);
    void updateUI(Meal meal);
    void setupVideo(String videoLink);
    void showToast(String msg);
}
