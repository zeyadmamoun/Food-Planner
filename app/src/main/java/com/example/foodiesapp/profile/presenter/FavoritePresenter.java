package com.example.foodiesapp.profile.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.example.foodiesapp.profile.view.FavoriteContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter {
    MealsRepository repository;
    FavoriteContract contract;
    private static final String TAG = "FavoritePresenter";

    public FavoritePresenter(MealsRepository repository, FavoriteContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    @SuppressLint("CheckResult")
    public void getFavoriteMeals() {
        repository.getFavoriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (!meals.isEmpty()) {
                                contract.assignFavoritesToAdapter(meals);
                            } else {
                                Log.i(TAG, "getFavoriteMeals: " + meals.size());
                                contract.showEmptyListIcon();
                            }
                        },
                        throwable -> contract.showEmptyListIcon()
                );
    }

    @SuppressLint("CheckResult")
    public void removeMealFromFavorites(Meal meal) {
        repository.removeMealFromFavorites(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> contract.showToast("meal removed")
                );
    }
}
