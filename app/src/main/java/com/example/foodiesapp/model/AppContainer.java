package com.example.foodiesapp.model;

import com.example.foodiesapp.network.MealsRemoteDataSource;
import com.example.foodiesapp.network.MealsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppContainer {
    MealsRepository repository;
    public AppContainer() {
        MealsRemoteDataSource remoteDataSource = new MealsRemoteDataSource();
        repository = new MealsRepository(remoteDataSource);
    }

    public MealsRepository getRepository() {
        return repository;
    }
}
