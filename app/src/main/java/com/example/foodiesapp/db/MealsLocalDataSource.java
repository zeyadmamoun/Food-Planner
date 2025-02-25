package com.example.foodiesapp.db;

import android.content.Context;

import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSource {

    private final MealsDao mealsDao;

    public MealsLocalDataSource(Context context) {
        MealsDatabase db = MealsDatabase.getInstance(context);
        mealsDao = db.mealsDao();
    }

    public Completable addMealToPlan(DatabaseMeal meal){
        return mealsDao.addMealToPlan(meal);
    }

    public Completable addMealToFavorite(Meal meal){
        return mealsDao.addMealToFavorites(meal);
    }

    public Flowable<List<DatabaseMeal>> getMealsByDate(String date){
        return mealsDao.getMealsByDate(date);
    }

    public Flowable<List<Meal>> getFavoriteMeals(){
        return mealsDao.getFavoriteMeals();
    }

    public Flowable<List<DatabaseMeal>> getAllPlanMeals(){
        return mealsDao.getAllPlanMeals();
    }

    public Single<Meal> getMealById(String id){
        return mealsDao.getMealById(id);
    }

    public Completable removeMealFromFavorites(Meal meal){
        return mealsDao.removeMealFromFavorites(meal);
    }

    public Completable removeMealFromPlan(DatabaseMeal meal){
        return mealsDao.removeMealFromPlans(meal);
    }
}
