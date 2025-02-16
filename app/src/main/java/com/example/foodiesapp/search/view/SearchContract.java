package com.example.foodiesapp.search.view;

import com.example.foodiesapp.model.category.Category;

import java.util.List;

public interface SearchContract {
    void updateCategoriesRecyclerView(List<Category> categoriesList);
    void updateIngredientsRecyclerView();
    void updateCountriesRecyclerView();
    void showToast(String msg);
}
