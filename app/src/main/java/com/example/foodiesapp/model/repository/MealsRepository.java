package com.example.foodiesapp.model.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodiesapp.home.presenter.HomePresenter;
import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.meal.MealsListResponse;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepository {
    MealsRemoteDataSource remoteSource;
    private List<Meal> inspirationMeals = new ArrayList<>();
    private List<Meal> recommendedMeals = new ArrayList<>();

    public MealsRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @SuppressLint("CheckResult")
    public void getRandomInspirationMeals(HomeContract callback) {
        Observable<MealsListResponse> mealsObservable = remoteSource.getInspirationMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mealsObservable.subscribe(
                mealsListResponse -> {
                    inspirationMeals = mealsListResponse.getMeals();
                    Log.i(HomePresenter.TAG, "onNext: " + inspirationMeals.size());
                    callback.assignMealsListToPager(inspirationMeals);
                },
                throwable -> {
                    Log.i(HomePresenter.TAG, "onError: " + throwable.toString());
                    callback.showToast(throwable.toString());
                }
        );
    }

    @SuppressLint("CheckResult")
    public void getRecommendationMeals(HomeContract callback) {
        Log.i(HomePresenter.TAG, "getRecommendationMeals: Im here" );
        Observable<MealsListResponse> recommendationsObservable = remoteSource.getRecommendMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        recommendationsObservable.subscribe(
                mealsListResponse -> {
                    recommendedMeals = mealsListResponse.getMeals();
                    Log.i(HomePresenter.TAG, "getRecommendationMeals: " + recommendedMeals.size());
                    callback.assignMealsListToRecommendations(recommendedMeals);
                },
                throwable -> {
                    callback.showToast(throwable.toString());
                }
        );
    }

    public List<Meal> getInspirationMeals() {
        return inspirationMeals;
    }

    public List<Meal> getRecommendedMeals() {
        return recommendedMeals;
    }
}
