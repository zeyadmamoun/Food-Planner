package com.example.foodiesapp.plan.view;

import com.example.foodiesapp.model.meal.DatabaseMeal;

import java.util.List;

public interface PlanContract {
    void assignPlanAdapter(List<DatabaseMeal> planMeals);
    void showEmptyListIcon();
    void navigateToMealDetail(String id);
    void removeMeal(DatabaseMeal meal);
    void showToast(String msg);
}
