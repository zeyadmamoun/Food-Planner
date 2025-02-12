package com.example.foodiesapp.repositories;

import android.util.Log;

import com.example.foodiesapp.model.MealsListResponse;
import com.example.foodiesapp.network.MealsService;
import com.example.foodiesapp.ui.home.HomePresenter;

import java.util.Random;

import retrofit2.Call;

public class MealsRepository {
    MealsService service;

    public MealsRepository(MealsService retrofitService){
        service = retrofitService;
    }

    public Call<MealsListResponse> getInspirationMeals(){
        String randomChar = generateRandomLetter();
        Log.i(HomePresenter.TAG, randomChar);
        return service.getMeals(randomChar);
    }

    private String generateRandomLetter(){
        Random random = new Random();
        int randomInt = random.nextInt(26);
        return String.valueOf((char) ('a' + randomInt));
    }
}
