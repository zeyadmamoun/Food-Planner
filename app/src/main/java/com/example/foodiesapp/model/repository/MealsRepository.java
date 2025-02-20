package com.example.foodiesapp.model.repository;

import android.annotation.SuppressLint;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealsRepository {
    MealsRemoteDataSource remoteSource;
    private List<Meal> inspirationMeals = new ArrayList<>();
    private List<Meal> recommendedMeals = new ArrayList<>();

    public MealsRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @SuppressLint("CheckResult")
    public Single<MealsListResponse> getRandomInspirationMeals(HomeContract callback) {
        return remoteSource.getInspirationMeals();
    }

    @SuppressLint("CheckResult")
    public Single<MealsListResponse> getRecommendationMeals() {
        return remoteSource.getRecommendMeals();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Meal> getInspirationMeals() {
        return inspirationMeals;
    }
    public void setInspirationMeals(List<Meal> inspirationMeals) {
        this.inspirationMeals = inspirationMeals;
    }
    public List<Meal> getRecommendedMeals() {
        return recommendedMeals;
    }
    public void setRecommendedMeals(List<Meal> recommendedMeals) {
        this.recommendedMeals = recommendedMeals;
    }
}
