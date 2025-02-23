package com.example.foodiesapp.mealResults.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodiesapp.mealResults.view.ResultsContract;
import com.example.foodiesapp.model.meal.FilteredMeal;
import com.example.foodiesapp.model.meal.FilteredMealResponse;
import com.example.foodiesapp.model.repository.MealsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResultsPresenter {
    MealsRepository repository;
    ResultsContract contract;
    public List<FilteredMeal> filteredMeals = new ArrayList<>();
    Disposable disposable;
    private static final String TAG = "ResultsPresenter";

    public ResultsPresenter(MealsRepository repository, ResultsContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    @SuppressLint("CheckResult")
    public void getFilteredMeals(String filterType, String query) {
        Single<FilteredMealResponse> request;
        if (filterType.equals("Category")){
            request = repository.getMealsByCategory(query);
        } else if (filterType.equals("Ingredient")) {
            request = repository.getMealsByMainIngredient(query);
        } else {
            request = repository.getMealsByCountry(query);
        }

        disposable = request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filteredMealResponse -> {
                            contract.assignAdapter(filteredMealResponse.getMeals());
                            filteredMeals.addAll(filteredMealResponse.getMeals());
                        },
                        throwable -> Log.i(TAG, throwable.toString())
                );
    }

    public void disposeObservables(){
        disposable.dispose();
    }
}
