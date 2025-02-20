package com.example.foodiesapp.search.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.country.CountryResponse;
import com.example.foodiesapp.model.ingredient.IngredientResponse;
import com.example.foodiesapp.model.repository.CategoryRepository;
import com.example.foodiesapp.search.view.SearchContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    CategoryRepository categoryRepository;
    SearchContract contract;
    public static final String TAG = "SearchPresenter";

    public SearchPresenter(CategoryRepository categoryRepository, SearchContract contract) {
        this.categoryRepository = categoryRepository;
        this.contract = contract;
    }

    @SuppressLint("CheckResult")
    public void getCategories() {
        if (categoryRepository.getCategories().isEmpty()) {
            categoryRepository.getRemoteCategoriesList()
                    .map(CategoryListResponse::getCategories)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            categories -> {
                                Log.i(TAG, "getCategories: " + categories.size());
                                categoryRepository.setCategories(categories);
                                contract.updateCategoriesRecyclerView(categories);
                            },
                            throwable -> {
                                Log.i(TAG, "getCategories: " + throwable);
                            }
                    );
        } else {
            contract.updateCategoriesRecyclerView(categoryRepository.getCategories());
        }
    }

    @SuppressLint("CheckResult")
    public void getCountries() {
        if (categoryRepository.getCountries().isEmpty()) {
            categoryRepository.getRemoteCountriesList()
                    .map(CountryResponse::getCountries)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            countries -> {
                                contract.updateCountriesRecyclerView(countries);
                                categoryRepository.setCountries(countries);
                                Log.i(TAG, "getCountries: " + countries.size());
                            },
                            throwable -> {
                                Log.i(TAG, "getCountries: " + throwable);
                            }
                    );
        } else {
            contract.updateCountriesRecyclerView(categoryRepository.getCountries());
        }
    }

    @SuppressLint("CheckResult")
    public void getIngredients() {
        if (categoryRepository.getIngredients().isEmpty()) {
            categoryRepository.getRemoteIngredientsList()
                    .map(IngredientResponse::getIngredients)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ingredients -> {
                                contract.updateIngredientsRecyclerView(ingredients);
                                categoryRepository.setIngredients(ingredients);
                                Log.i(TAG, "getIngredients: " + ingredients.size());
                            },
                            throwable -> {
                                Log.i(TAG, "getIngredients: " + throwable);
                            }
                    );
        } else {
            contract.updateIngredientsRecyclerView(categoryRepository.getIngredients());
        }
    }
}
