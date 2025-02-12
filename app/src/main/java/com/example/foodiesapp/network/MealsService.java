package com.example.foodiesapp.network;

import com.example.foodiesapp.model.MealsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("/api/json/v1/1/search.php")
    Call<MealsListResponse> getMeals(@Query("f") String firstLetter);
}
