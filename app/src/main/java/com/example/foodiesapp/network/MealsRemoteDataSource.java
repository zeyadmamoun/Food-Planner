package com.example.foodiesapp.network;

import android.util.Log;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.home.view.HomeFragment;
import com.example.foodiesapp.model.Meal;
import com.example.foodiesapp.model.MealsListResponse;
import com.example.foodiesapp.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    public final String BASE_URL = "https://www.themealdb.com/";
    private static final String TAG = "MealsRemoteDataSource";
    MealsService service;

    public MealsRemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MealsService.class);
    }

    public void getInspirationMeals(HomeContract callback){
        List<Meal> meals = new ArrayList<>();
        service.getMeals(generateRandomLetter()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MealsListResponse> call, Response<MealsListResponse> response) {
                Log.i(HomePresenter.TAG, call.request().toString());
                if (response.isSuccessful()) {
                    if(response.body() == null)
                        getInspirationMeals(callback);
                    else{
                        meals.addAll(response.body().getMeals());
                        callback.assignMealsListToPager(meals);
                    }
                } else {
                    Log.i(TAG, response.code() + " " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MealsListResponse> call, Throwable throwable) {
                Log.i(TAG, throwable.toString());
            }
        });
    }

    private String generateRandomLetter(){
        Random random = new Random();
        int randomInt = random.nextInt(26);
        return String.valueOf((char) ('a' + randomInt));
    }
}
