package com.example.foodiesapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;

@Database(entities = {Meal.class, DatabaseMeal.class},version = 1)
abstract public class MealsDatabase extends RoomDatabase {
    private static MealsDatabase instance = null;
    public abstract MealsDao mealsDao();

    public static MealsDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MealsDatabase.class,"mealsdb")
                    .build();
        }
        return instance;
    }
}
