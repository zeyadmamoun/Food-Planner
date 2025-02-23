package com.example.foodiesapp.mealDetails.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodiesapp.mealDetails.view.MealDetailsContract;
import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.repository.MealsRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter {
    MealsRepository repository;
    MealDetailsContract contract;
    Meal currentMeal;
    private static final String TAG = "MealDetailsPresenter";

    public MealDetailsPresenter(MealsRepository repository, MealDetailsContract contract) {
        this.repository = repository;
        this.contract = contract;
    }

    public Meal getCurrentMeal() {
        return currentMeal;
    }

    @SuppressLint("CheckResult")
    public void getMealDetails(String id) {repository.getMealDetails(id).subscribeOn(Schedulers.io())
                .map(item -> item.getMeals().get(0))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            currentMeal = meal;
                            List<String> ingredients = createIngredientList(meal);
                            String[] arr = meal.getStrInstructions().split("[.]");
                            List<String> steps = new ArrayList<>(Arrays.asList(arr));
                            contract.updateUI(meal);
                            contract.assignIngredientsRecyclerView(ingredients);
                            contract.assignStepsRecyclerView(steps);
                            contract.setupVideo(extractYouTubeVideoId(meal.getStrYoutube()));
                        },
                        throwable -> Log.i(TAG, throwable.toString())
                );
    }

    @SuppressLint("CheckResult")
    public void addMealToPlan(DatabaseMeal meal){
        repository.addMealToPlan(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> contract.showToast("Added to your plan")
                );
    }

    @SuppressLint("CheckResult")
    public void addMealToFavorites(Meal meal){
        repository.addMealToFavorite(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> contract.showToast("Added to your favorites")
                );
    }

    private String extractYouTubeVideoId(String url) {
        String pattern = "(?:v=|youtu\\.be/|embed/|/v/|/e/|watch\\?v=|watch\\?.+&v=)([^&#?]+)";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1); // Get the first capturing group
        }
        return null; // Return null if no valid ID is found
    }

    private List<String> createIngredientList(Meal meal) {
        List<String> ingredientList = new ArrayList<>();
        if (meal.getStrIngredient1() != null && !meal.getStrIngredient1().isEmpty()) {
            ingredientList.add(meal.getStrIngredient1());
        }
        if (meal.getStrIngredient2() != null && !meal.getStrIngredient2().isEmpty()) {
            ingredientList.add(meal.getStrIngredient2());
        }
        if (meal.getStrIngredient3() != null && !meal.getStrIngredient3().isEmpty()) {
            ingredientList.add(meal.getStrIngredient3());
        }
        if (meal.getStrIngredient4() != null && !meal.getStrIngredient4().isEmpty()) {
            ingredientList.add(meal.getStrIngredient4());
        }
        if (meal.getStrIngredient5() != null && !meal.getStrIngredient5().isEmpty()) {
            ingredientList.add(meal.getStrIngredient5());
        }
        if (meal.getStrIngredient6() != null && !meal.getStrIngredient6().isEmpty()) {
            ingredientList.add(meal.getStrIngredient6());
        }
        if (meal.getStrIngredient7() != null && !meal.getStrIngredient7().isEmpty()) {
            ingredientList.add(meal.getStrIngredient7());
        }
        if (meal.getStrIngredient8() != null && !meal.getStrIngredient8().isEmpty()) {
            ingredientList.add(meal.getStrIngredient8());
        }
        if (meal.getStrIngredient9() != null && !meal.getStrIngredient9().isEmpty()) {
            ingredientList.add(meal.getStrIngredient9());
        }
        if (meal.getStrIngredient10() != null && !meal.getStrIngredient10().isEmpty()) {
            ingredientList.add(meal.getStrIngredient10());
        }
        if (meal.getStrIngredient11() != null && !meal.getStrIngredient11().isEmpty()) {
            ingredientList.add(meal.getStrIngredient11());
        }
        if (meal.getStrIngredient12() != null && !meal.getStrIngredient12().isEmpty()) {
            ingredientList.add(meal.getStrIngredient12());
        }
        if (meal.getStrIngredient13() != null && !meal.getStrIngredient13().isEmpty()) {
            ingredientList.add(meal.getStrIngredient13());
        }
        if (meal.getStrIngredient14() != null && !meal.getStrIngredient14().isEmpty()) {
            ingredientList.add(meal.getStrIngredient14());
        }
        if (meal.getStrIngredient15() != null && !meal.getStrIngredient15().isEmpty()) {
            ingredientList.add(meal.getStrIngredient15());
        }
        return ingredientList;
    }

}
