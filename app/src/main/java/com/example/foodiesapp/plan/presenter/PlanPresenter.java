package com.example.foodiesapp.plan.presenter;

import android.annotation.SuppressLint;

import com.example.foodiesapp.model.repository.MealsRepository;
import com.example.foodiesapp.plan.view.PlanContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenter {
    MealsRepository repository;
    PlanContract contract;

    public PlanPresenter(MealsRepository repository, PlanContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    @SuppressLint("CheckResult")
    public void getMealsByDate(String date){
        repository.getMealsByDate(date).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        databaseMeals -> {
                            if (!databaseMeals.isEmpty())
                                contract.assignPlanAdapter(databaseMeals);
                            else{
                                contract.showEmptyListIcon();
                            }
                        },
                        throwable ->  contract.showToast(throwable.toString())
                );
    }
}
