package com.example.foodiesapp.network;

import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.country.CountryResponse;
import com.example.foodiesapp.model.ingredient.IngredientResponse;
import com.example.foodiesapp.model.meal.FilteredMealResponse;
import com.example.foodiesapp.model.meal.MealsListResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("/api/json/v1/1/search.php")  // random daily meals
    Single<MealsListResponse> getMeals(@Query("f") String firstLetter);

    @GET("/api/json/v1/1/search.php")  // recommendation meals
    Single<MealsListResponse> getRecommendedMeals(@Query("s") String str);

    @GET("/api/json/v1/1/categories.php")  //get all categories
    Single<CategoryListResponse> getCategories();

    @GET("/api/json/v1/1/list.php?a=list")  // get all countries
    Single<CountryResponse> getCountries();

    @GET("/api/json/v1/1/filter.php")  // Filter by main ingredient
    Single<FilteredMealResponse> getMealsFilteredByMainIngredient(@Query("i") String ingredient);

    @GET("/api/json/v1/1/filter.php")  // Filter by category
    Single<FilteredMealResponse> getMealsFilteredByCategory(@Query("c") String category);

    @GET("/api/json/v1/1/filter.php")  // Filter by Area
    Single<FilteredMealResponse> getMealsFilteredByCountry(@Query("a") String area);

    @GET("/api/json/v1/1/list.php?i=list") // get all ingredients
    Single<IngredientResponse> getIngredients();

    @GET("/api/json/v1/1/lookup.php") // get one meal with details
    Single<MealsListResponse> getMealDetail(@Query("i") String mealId);
}
