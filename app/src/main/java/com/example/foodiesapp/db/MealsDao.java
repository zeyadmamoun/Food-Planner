package com.example.foodiesapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsDao {
    @Query("Select * from meals_plan")
    Flowable<List<DatabaseMeal>>getAllPlanMeals();
    @Query("Select * From meals_plan where mealDate = :Date")
    Flowable<List<DatabaseMeal>>getMealsByDate(String Date);
    @Query("Select * from favorites")
    Flowable<List<Meal>> getFavoriteMeals();
    @Query("Select * from favorites where idMeal = :id")
    Single<Meal> getMealById(String id);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addMealToFavorites(Meal meal);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addMealToPlan(DatabaseMeal meal);
    @Delete
    Completable removeMealFromFavorites(Meal meal);
    @Delete
    Completable removeMealFromPlans(DatabaseMeal databaseMeal);

}
