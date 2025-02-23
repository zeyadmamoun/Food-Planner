package com.example.foodiesapp.model.meal;

import java.util.List;

public class FilteredMealResponse {

    List<FilteredMeal> meals;

    public List<FilteredMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<FilteredMeal> meals) {
        this.meals = meals;
    }

    public FilteredMealResponse(List<FilteredMeal> meals) {
        this.meals = meals;
    }
}
