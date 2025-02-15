package com.example.foodiesapp.home.presenter;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.MealsRepository;

import java.util.List;

public class HomePresenter {
    MealsRepository repository;
    HomeContract contract;
    public static final String TAG = "HomePresenter";

    public HomePresenter(MealsRepository repository, HomeContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    public void getInspirationMeals(){
        if (repository.getCachedMeals().isEmpty()){
            repository.getRandomInspirationMeals(contract);
        } else {
            contract.assignMealsListToPager(repository.getCachedMeals());
        }
    }

    public void getRecommendationMeals(){
        if (repository.getRecommendedMeals().isEmpty()){
            repository.getRecommendationMeals(contract);
        } else {
            contract.assignMealsListToRecommendations(repository.getRecommendedMeals());
        }
    }

    public void cacheData(List<Meal> meals) {
        repository.setCachedMeals(meals);
    }
    public void cacheRecommendationData(List<Meal> meals) {
        repository.setRecommendedMeals(meals);
    }
}
