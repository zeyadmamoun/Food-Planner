package com.example.foodiesapp.model.repository;

import com.example.foodiesapp.R;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.category.CategoryListResponse;
import com.example.foodiesapp.model.country.Country;
import com.example.foodiesapp.model.country.CountryResponse;
import com.example.foodiesapp.model.ingredient.Ingredient;
import com.example.foodiesapp.model.ingredient.IngredientResponse;
import com.example.foodiesapp.network.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;

public class CategoryRepository {
    private final MealsRemoteDataSource remoteSource;
    private List<Category> categories = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    public static final Map<String,Integer> flagsMap = new HashMap<>();

    public CategoryRepository(MealsRemoteDataSource remoteSource) {
        this.remoteSource = remoteSource;
        setFlagsMap();
    }

    public Single<CategoryListResponse> getRemoteCategoriesList() {
        return remoteSource.getAllCategories();
    }

    public Single<CountryResponse> getRemoteCountriesList(){
        return remoteSource.getAllCountries();
    }

    public Single<IngredientResponse> getRemoteIngredientsList(){
        return remoteSource.getAllIngredients();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public List<Country> getCountries() {
        return countries;
    }
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    private void setFlagsMap(){
        flagsMap.put("American", R.drawable.america);
        flagsMap.put("British", R.drawable.britain);
        flagsMap.put("Canadian", R.drawable.canada);
        flagsMap.put("Chinese", R.drawable.china);
        flagsMap.put("Croatian", R.drawable.coratia);
        flagsMap.put("Dutch", R.drawable.dutch);
        flagsMap.put("Egyptian", R.drawable.egypt);
        flagsMap.put("Filipino", R.drawable.philippine);
        flagsMap.put("French", R.drawable.france);
        flagsMap.put("Greek", R.drawable.greece);
        flagsMap.put("Indian", R.drawable.india);
        flagsMap.put("Irish", R.drawable.ireland);
        flagsMap.put("Italian", R.drawable.italy);
        flagsMap.put("Jamaican", R.drawable.jamaica);
        flagsMap.put("Japanese", R.drawable.japan);
        flagsMap.put("Kenyan", R.drawable.kenya);
        flagsMap.put("Malaysian", R.drawable.malaysia);
        flagsMap.put("Mexican", R.drawable.mexico);
        flagsMap.put("Moroccan", R.drawable.morocco);
        flagsMap.put("Norwegian", R.drawable.norway);
        flagsMap.put("Polish", R.drawable.poland);
        flagsMap.put("Portuguese", R.drawable.portugal);
        flagsMap.put("Russian", R.drawable.russia);
        flagsMap.put("Spanish", R.drawable.spain);
        flagsMap.put("Thai", R.drawable.thailand);
        flagsMap.put("Tunisian", R.drawable.tunisia);
        flagsMap.put("Turkish", R.drawable.turkey);
        flagsMap.put("Ukrainian", R.drawable.ukraine);
        flagsMap.put("Uruguayan", R.drawable.uruguay);
        flagsMap.put("Vietnamese", R.drawable.vietnam);
    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
