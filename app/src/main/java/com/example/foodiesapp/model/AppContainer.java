package com.example.foodiesapp.model;

import com.example.foodiesapp.model.repository.CategoryRepository;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.example.foodiesapp.network.MealsRemoteDataSource;

public class AppContainer {
    MealsRepository mealsRepository;
    CategoryRepository categoryRepository;

    public AppContainer() {
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
        mealsRepository = new MealsRepository(remoteDataSource);
        categoryRepository = new CategoryRepository(remoteDataSource);
    }

    public MealsRepository getMealsRepository() {
        return mealsRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
}
