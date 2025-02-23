package com.example.foodiesapp.model;

import android.content.Context;

import com.example.foodiesapp.db.MealsLocalDataSource;
import com.example.foodiesapp.model.repository.CategoryRepository;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.example.foodiesapp.network.MealsRemoteDataSource;

public class AppContainer {
    Context context;
    MealsRepository mealsRepository;
    CategoryRepository categoryRepository;

    public AppContainer(Context context) {
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
        MealsLocalDataSource localDataSource = new MealsLocalDataSource(context);
        mealsRepository = new MealsRepository(remoteDataSource,localDataSource);
        categoryRepository = new CategoryRepository(remoteDataSource);
    }

    public MealsRepository getMealsRepository() {
        return mealsRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
}
