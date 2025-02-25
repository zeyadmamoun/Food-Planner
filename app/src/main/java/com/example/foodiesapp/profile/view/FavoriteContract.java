package com.example.foodiesapp.profile.view;

import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

public interface FavoriteContract {
    void assignFavoritesToAdapter(List<Meal> favoriteMeals);
    void navigateToMealDetail(String id);
    void showEmptyListIcon();
    void removeMealFromFavorite(Meal meal);
    void showToast(String msg);
}
