package com.example.foodiesapp.model.repository;

import com.example.foodiesapp.db.MealsLocalDataSource;
import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.FilteredMealResponse;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepository {
    MealsRemoteDataSource remoteSource;
    MealsLocalDataSource localSource;
    private List<Meal> inspirationMeals = new ArrayList<>();
    private List<Meal> recommendedMeals = new ArrayList<>();

    public MealsRepository(MealsRemoteDataSource remoteSource,MealsLocalDataSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public Single<MealsListResponse> getRandomInspirationMeals(HomeContract callback) {
        return remoteSource.getInspirationMeals();
    }

    public Single<MealsListResponse> getRecommendationMeals() {
        return remoteSource.getRecommendMeals();
    }

    public Single<FilteredMealResponse> getMealsByMainIngredient(String ingredient) {
        return remoteSource.getMealsByMainIngredient(ingredient);
    }

    public Single<FilteredMealResponse> getMealsByCategory(String category) {
        return remoteSource.getMealsByCategory(category);
    }

    public Single<FilteredMealResponse> getMealsByCountry(String country) {
        return remoteSource.getMealsByCountry(country);
    }

    public Single<MealsListResponse> getMealDetails(String id){
        return remoteSource.getMealDetails(id);
    }

    public Single<Meal> getMealById(String id){
        return localSource.getMealById(id);
    }

    public Completable addMealToPlan(DatabaseMeal meal){
        return localSource.addMealToPlan(meal);
    }

    public Completable addMealToFavorite(Meal meal){
        return localSource.addMealToFavorite(meal);
    }

    public Flowable<List<DatabaseMeal>> getMealsByDate(String date){
        return localSource.getMealsByDate(date);
    }

    public Flowable<List<Meal>> getFavoriteMeals(){
        return localSource.getFavoriteMeals();
    }

    public Flowable<List<DatabaseMeal>> getAllPlanMeals(){
        return localSource.getAllPlanMeals();
    }

    public Completable removeMealFromFavorites(Meal meal){
        return localSource.removeMealFromFavorites(meal);
    }

    public Completable removeMealFromPlan(DatabaseMeal meal){
        return localSource.removeMealFromPlan(meal);
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
