package com.example.foodiesapp.search.view;

import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.country.Country;
import com.example.foodiesapp.model.ingredient.Ingredient;

import java.util.List;

public interface SearchContract {
    void updateCategoriesRecyclerView(List<Category> categoriesList);
    void updateIngredientsRecyclerView(List<Ingredient> ingredientList);
    void updateCountriesRecyclerView(List<Country> countryList);
    void showToast(String msg);
}
