package com.example.foodiesapp.home.view;

import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

public interface HomeContract {
    void assignMealsListToPager(List<Meal> meals);
    void assignMealsListToRecommendations(List<Meal> recommendationMeals);
    void onAddToPlanClicked(Meal meal);
    void onCardClicked(String id);
    void showToast(String msg);
}
