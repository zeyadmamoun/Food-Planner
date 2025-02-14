package com.example.foodiesapp.model;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.List;

public class MealsRepository {
    MealsRemoteDataSource remoteSource;

    public MealsRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    public void getRandomInspirationMeals(HomeContract callback){
         remoteSource.getInspirationMeals(callback);
    }
}
