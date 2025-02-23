package com.example.foodiesapp.network;

import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.country.CountryResponse;
import com.example.foodiesapp.model.ingredient.IngredientResponse;
import com.example.foodiesapp.model.meal.FilteredMealResponse;
import com.example.foodiesapp.model.meal.MealsListResponse;

import java.util.Random;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {
    public final String BASE_URL = "https://www.themealdb.com/";
    private static final String TAG = "MealsRemoteDataSource";
    MealsService service;

    public MealsRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        service = retrofit.create(MealsService.class);
    }

    public Single<MealsListResponse> getInspirationMeals() {
        return service.getMeals(generateRandomLetter());
    }

    public Single<MealsListResponse> getRecommendMeals(){
        return service.getRecommendedMeals("");
    }

    public Single<CategoryListResponse> getAllCategories() {
        return service.getCategories();
    }

    public Single<CountryResponse> getAllCountries() {
        return service.getCountries();
    }

    public Single<IngredientResponse> getAllIngredients(){
        return service.getIngredients();
    }

    public Single<FilteredMealResponse> getMealsByMainIngredient(String ingredient){
        return service.getMealsFilteredByMainIngredient(ingredient);
    }

    public Single<FilteredMealResponse> getMealsByCategory(String category){
        return service.getMealsFilteredByCategory(category);
    }

    public Single<FilteredMealResponse> getMealsByCountry(String country){
        return service.getMealsFilteredByCountry(country);
    }

    public Single<MealsListResponse> getMealDetails(String id){
        return service.getMealDetail(id);
    }

    private String generateRandomLetter() {
        Random random = new Random();
        int randomInt = random.nextInt(26);
        return String.valueOf((char) ('a' + randomInt));
    }
}
