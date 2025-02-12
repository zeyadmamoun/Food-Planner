package com.example.foodiesapp.ui.home;

import android.util.Log;

import com.example.foodiesapp.model.Meal;
import com.example.foodiesapp.model.MealsListResponse;
import com.example.foodiesapp.repositories.MealsRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    MealsRepository repository;
    HomeContract contract;
    List<Meal> inspirationMeals = new ArrayList<>();
    public static final String TAG = "HomePresenter";

    public HomePresenter(MealsRepository repository) {
        this.repository = repository;
    }

    void getInspirationMeals(){
        repository.getInspirationMeals().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MealsListResponse> call, Response<MealsListResponse> response) {
                Log.i(TAG, call.request().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    inspirationMeals.addAll(response.body().getMeals());
                    Log.i(TAG, inspirationMeals.size() + "");
                } else {
                    Log.i(TAG, response.code() + " " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<MealsListResponse> call, Throwable throwable) {
                contract.showToast("getting list of meals failed");
                Log.i(TAG, throwable.getMessage());
            }
        });
    }
}
