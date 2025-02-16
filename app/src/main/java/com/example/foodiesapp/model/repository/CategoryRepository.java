package com.example.foodiesapp.model.repository;

import android.annotation.SuppressLint;

import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.network.MealsRemoteDataSource;
import com.example.foodiesapp.search.view.SearchContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryRepository {
    private final MealsRemoteDataSource remoteSource;
    private List<Category> categories = new ArrayList<>();

    public CategoryRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @SuppressLint("CheckResult")
    public void getCategories(SearchContract callback) {
        Observable<CategoryListResponse> observable = remoteSource.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(
                categoryListResponse -> {
                    categories = categoryListResponse.getCategories();
                    callback.updateCategoriesRecyclerView(categories);
                },
                throwable -> { callback.showToast(throwable.toString());}
        );
    }

    public List<Category> getCategories() {
        return categories;
    }
}
