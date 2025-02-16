package com.example.foodiesapp.home.presenter;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.repository.MealsRepository;

public class HomePresenter {
    MealsRepository repository;
    HomeContract contract;
    public static final String TAG = "HomePresenter";

    public HomePresenter(MealsRepository repository, HomeContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    public void getInspirationMeals(){
        if (repository.getInspirationMeals().isEmpty()){
            repository.getRandomInspirationMeals(contract);
        } else {
            contract.assignMealsListToPager(repository.getInspirationMeals());
        }
    }

    public void getRecommendationMeals(){
        if (repository.getRecommendedMeals().isEmpty()){
            repository.getRecommendationMeals(contract);
        } else {
            contract.assignMealsListToRecommendations(repository.getRecommendedMeals());
        }
    }
}
