package com.example.foodiesapp.repositories;

import com.example.foodiesapp.network.MealsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppContainer {
    public MealsService service;
    public final String BASE_URL = "https://www.themealdb.com/";
    public AppContainer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MealsService.class);
    }
}
