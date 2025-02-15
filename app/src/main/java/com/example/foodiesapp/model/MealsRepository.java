package com.example.foodiesapp.model;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class MealsRepository {
    MealsRemoteDataSource remoteSource;
    private List<Meal> cachedMeals = new ArrayList<>();
    private List<Meal> recommendedMeals = new ArrayList<>();

    public MealsRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    public void getRandomInspirationMeals(HomeContract callback){
         remoteSource.getInspirationMeals(callback);
    }

    public void getRecommendationMeals(HomeContract callback){
        remoteSource.getRecommendations(callback);
    }

    public List<Meal> getCachedMeals() {
        return cachedMeals;
    }

    public void setCachedMeals(List<Meal> cachedMeals) {
        this.cachedMeals = cachedMeals;
    }

    public List<Meal> getRecommendedMeals() {
        return recommendedMeals;
    }

    public void setRecommendedMeals(List<Meal> recommendedMeals) {
        this.recommendedMeals = recommendedMeals;
    }
}
