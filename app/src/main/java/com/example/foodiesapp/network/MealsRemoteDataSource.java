package com.example.foodiesapp.network;

import android.util.Log;

import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.home.presenter.HomePresenter;
import com.example.foodiesapp.search.view.SearchContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public Observable<MealsListResponse> getInspirationMeals() {
        return service.getMeals(generateRandomLetter());
    }

    public Observable<MealsListResponse> getRecommendMeals(){
        return service.getRecommendedMeals("");
    }

    public Observable<CategoryListResponse> getAllCategories() {
        return service.getCategories();
    }

    private String generateRandomLetter() {
        Random random = new Random();
        int randomInt = random.nextInt(26);
        return String.valueOf((char) ('a' + randomInt));
    }
}
