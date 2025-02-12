package com.example.foodiesapp.model;

import java.util.List;

public class MealsListResponse {
    List<Meal> meals;

    public MealsListResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "MealsListResponse{" +
                "meals=" + meals +
                '}';
    }
}
