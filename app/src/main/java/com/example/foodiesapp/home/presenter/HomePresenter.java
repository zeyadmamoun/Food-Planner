package com.example.foodiesapp.home.presenter;

import com.example.foodiesapp.home.view.HomeContract;
import com.example.foodiesapp.model.MealsRepository;

public class HomePresenter {
    MealsRepository repository;
    HomeContract contract;
    public static final String TAG = "HomePresenter";

    public HomePresenter(MealsRepository repository, HomeContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    public void getInspirationMeals(){
        repository.getRandomInspirationMeals(contract);
    }


}
