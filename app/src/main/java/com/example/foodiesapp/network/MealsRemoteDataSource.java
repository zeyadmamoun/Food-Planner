package com.example.foodiesapp.network;

import android.util.Log;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.meal.MealsListResponse;
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
                    if(response.errorBody() != null || response.body().getMeals() == null){
                        getInspirationMeals(callback);
                    }
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

    public void getRecommendations(HomeContract callback){
        List<Meal> meals = new ArrayList<>();
        service.getMeals(generateRecommendationLetter()).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MealsListResponse> call, Response<MealsListResponse> response) {
                Log.i(HomePresenter.TAG, call.request().toString());
                if (response.isSuccessful()) {
                    if(response.errorBody() != null || response.body().getMeals() == null){
                        getInspirationMeals(callback);
                    }
                    else{
                        meals.addAll(response.body().getMeals());
                        callback.assignMealsListToRecommendations(meals);
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

    public void getAllCategories(){
        List<Category> categories = new ArrayList<>();
        service.getCategories().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {

            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable throwable) {

            }
        });
    }

    private String generateRandomLetter(){
        Random random = new Random();
        int randomInt = random.nextInt(26);
        return String.valueOf((char) ('a' + randomInt));
    }

    private String generateRecommendationLetter(){
        List<String> letters = new ArrayList<>();
        letters.add("p");
        letters.add("f");
        letters.add("l");
        letters.add("b");
        letters.add("c");
        Random random = new Random();
        int randomInt = random.nextInt(4);
        return letters.get(randomInt);
    }
}
