package com.example.foodiesapp.home.presenter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    MealsRepository repository;
    HomeContract contract;
    private final FirebaseAuth mAuth;
    public static final String TAG = "HomePresenter";

    public HomePresenter(MealsRepository repository, HomeContract contract) {
        this.repository = repository;
        this.contract = contract;
        mAuth = FirebaseAuth.getInstance();
    }

    public String getUsername(){
        if (mAuth.getCurrentUser() != null) {
            Log.i(TAG, "getUsername: " + mAuth.getCurrentUser().getDisplayName());
            return mAuth.getCurrentUser().getDisplayName();
        }
        return "User";
    }

    public String getUserAvatar() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri profileImageUrl = user != null ? user.getPhotoUrl() : null;

        if (profileImageUrl != null) {
            return profileImageUrl.toString();
        } else {
            return null;
        }
    }

    @SuppressLint("CheckResult")
    public void addMealToPlan(Meal currentMeal, String date) {
        if (currentMeal != null){
            DatabaseMeal meal = new DatabaseMeal(currentMeal,date);
            repository.addMealToPlan(meal).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> contract.showToast("meal is now added to your plan")
                    );
        }
    }

    @SuppressLint("CheckResult")
    public void getInspirationMeals(){
        if (repository.getInspirationMeals().isEmpty()){
            repository.getRandomInspirationMeals(contract)
                    .subscribeOn(Schedulers.io())
                    .map(MealsListResponse::getMeals)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                             meals -> {
                                 repository.setInspirationMeals(meals);
                                 Log.i(HomePresenter.TAG, "inspirationMeals size = " + meals.size());
                                 contract.assignMealsListToPager(meals);
                             },
                            throwable -> {
                                Log.i(HomePresenter.TAG, "onError: " + throwable.toString());
                                getInspirationMeals();
                            }
                    );
        } else {
            contract.assignMealsListToPager(repository.getInspirationMeals());
        }
    }

    @SuppressLint("CheckResult")
    public void getRecommendationMeals(){
        if (repository.getRecommendedMeals().isEmpty()){
            repository.getRecommendationMeals().map(MealsListResponse::getMeals)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            meals -> {
                                meals.remove(0);
                                repository.setRecommendedMeals(meals);
                                Log.i(HomePresenter.TAG, "recommendationMeals size = " + meals.size());
                                contract.assignMealsListToRecommendations(meals);
                            },
                            throwable -> {
                                Log.i(HomePresenter.TAG, "onError: " + throwable.toString());
                                contract.showToast(throwable.toString());
                            }
                    );
        } else {
            contract.assignMealsListToRecommendations(repository.getRecommendedMeals());
        }
    }
}
