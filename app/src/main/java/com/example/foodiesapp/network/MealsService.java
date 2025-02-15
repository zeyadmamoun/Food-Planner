package com.example.foodiesapp.network;

import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.search.view.adapters.CategoryCardsAdapter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("/api/json/v1/1/search.php")
    Call<MealsListResponse> getMeals(@Query("f") String firstLetter);

    @GET("/api/json/v1/1/categories.php")
    Call<CategoryListResponse> getCategories();
}
