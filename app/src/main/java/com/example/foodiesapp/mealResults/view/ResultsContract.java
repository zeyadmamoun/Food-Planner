package com.example.foodiesapp.mealResults.view;

import com.example.foodiesapp.model.meal.FilteredMeal;

import java.util.List;

public interface ResultsContract {
    void assignAdapter(List<FilteredMeal> filteredMeals);
    void navigateToMealDetails(String mealId);
}
