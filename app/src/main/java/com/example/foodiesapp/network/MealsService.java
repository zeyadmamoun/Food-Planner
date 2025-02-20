package com.example.foodiesapp.network;

import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.country.CountryResponse;
import com.example.foodiesapp.model.ingredient.IngredientResponse;
import com.example.foodiesapp.model.meal.MealsListResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("/api/json/v1/1/search.php")
    Single<MealsListResponse> getMeals(@Query("f") String firstLetter);

    @GET("/api/json/v1/1/search.php")
    Single<MealsListResponse> getRecommendedMeals(@Query("s") String str);

    @GET("/api/json/v1/1/categories.php")
    Single<CategoryListResponse> getCategories();

    @GET("/api/json/v1/1/list.php?a=list")
    Single<CountryResponse> getCountries();

    @GET("/api/json/v1/1/list.php?i=list")
    Single<IngredientResponse> getIngredients();
}
