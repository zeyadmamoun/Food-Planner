package com.example.foodiesapp.home.view;

import com.example.foodiesapp.model.Meal;

import java.util.List;

public interface HomeContract {
    void assignMealsListToPager(List<Meal> meals);
    void showToast(String msg);
}
