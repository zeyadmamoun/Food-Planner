package com.example.foodiesapp.profile.presenter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import com.example.foodiesapp.model.meal.DatabaseMeal;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.repository.MealsRepository;
import com.example.foodiesapp.profile.view.ProfileContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    MealsRepository repository;
    ProfileContract contract;
    private static final String TAG = "ProfilePresenter";

    public ProfilePresenter(MealsRepository mealsRepository,ProfileContract contract) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        repository = mealsRepository;
        this.contract = contract;
    }

    public String getUserAvatar() {
        FirebaseUser user = mAuth.getCurrentUser();
        Uri profileImageUrl = user != null ? user.getPhotoUrl() : null;

        if (profileImageUrl != null) {
            return profileImageUrl.toString();
        } else {
            return null;
        }
    }

    public String getUsername() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getDisplayName();
        }
        return "User";
    }

    public String getUserEmail() {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser().getEmail();
        }
        return "";
    }
////////////////////////// these function are specific to backup user plan and favorites////////////////////////////////
    @SuppressLint("CheckResult")
    public void getUserPlanAndBackItUp() {
        repository.getAllPlanMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(databaseMeals -> databaseMeals)
                .subscribe(
                        this::uploadUserPlanToDatabase,
                        throwable -> Log.i(TAG, "getUserPlanAndBackItUp: " + throwable),
                        () -> contract.showToast("plan backup success")
                );
    }
    @SuppressLint("CheckResult")
    public void getUserFavouritesAndBackItUp() {
        repository.getFavoriteMeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(meals -> meals)
                .subscribe(
                        this::uploadUserFavoritesToDatabase,
                        throwable -> Log.i(TAG, "getUserPlanAndBackItUp: " + throwable),
                        () -> contract.showToast("favorite backup success")
                );
    }

    void uploadUserPlanToDatabase(DatabaseMeal userPlanMeal) {
        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> mealMap = new HashMap<>();

        mealMap.put("idMeal", userPlanMeal.getIdMeal());
        mealMap.put("mealDate", userPlanMeal.getMealDate());
        mealMap.put("strMeal", userPlanMeal.getStrMeal());
        mealMap.put("strCategory", userPlanMeal.getStrCategory());
        mealMap.put("strArea", userPlanMeal.getStrArea());
        mealMap.put("strInstructions", userPlanMeal.getStrInstructions());
        mealMap.put("strMealThumb", userPlanMeal.getStrMealThumb());
        mealMap.put("strYoutube", userPlanMeal.getStrYoutube());

        // used reflection to access all ingredients names
        for (int i = 1; i <= 20; i++) {
            try {
                Method method = DatabaseMeal.class.getMethod("getStrIngredient" + i);
                String ingredient = (String) method.invoke(userPlanMeal);
                mealMap.put("strIngredient" + i, ingredient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        db.collection("backups")
                .document(userId)
                .collection("planMeals")
                .add(mealMap)
                .addOnSuccessListener(documentReference -> {
                    Log.i(TAG, "onSuccess: " + documentReference);
                })
                .addOnFailureListener(e -> Log.i(TAG, "onFailure: " + e.getMessage()));
    }
    void uploadUserFavoritesToDatabase(Meal favoriteMeal) {
        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> mealMap = new HashMap<>();

        mealMap.put("idMeal", favoriteMeal.getIdMeal());
        mealMap.put("strMeal", favoriteMeal.getStrMeal());
        mealMap.put("strCategory", favoriteMeal.getStrCategory());
        mealMap.put("strArea", favoriteMeal.getStrArea());
        mealMap.put("strInstructions", favoriteMeal.getStrInstructions());
        mealMap.put("strMealThumb", favoriteMeal.getStrMealThumb());
        mealMap.put("strYoutube", favoriteMeal.getStrYoutube());

        // used reflection to access all ingredients names
        for (int i = 1; i <= 20; i++) {
            try {
                Method method = DatabaseMeal.class.getMethod("getStrIngredient" + i);
                String ingredient = (String) method.invoke(favoriteMeal);
                mealMap.put("strIngredient" + i, ingredient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        db.collection("backups")
                .document(userId)
                .collection("favoriteMeals")
                .add(mealMap)
                .addOnSuccessListener(documentReference -> {
                    Log.i(TAG, "onSuccess: " + documentReference);
                })
                .addOnFailureListener(e -> Log.i(TAG, "onFailure: " + e.getMessage()));
    }
//////////////////////////////////these function are specific to restore user backup //////////////////////////////////////

    public void getFavoritesFromFirestore(){
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("backups")
                .document(userId).collection("favoriteMeals")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            addMealToFavorites(parseMeal(document.getData()));
                        }
                        contract.showToast("your favorites is restored successfully");
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    public void getPlanMealsFromFirestore(){
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("backups")
                .document(userId).collection("planMeals")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            addMealToPlan(parseMeal(document.getData()),(String) document.getData().get("mealDate"));
                        }
                        contract.showToast("your plan is restored successfully");
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void addMealToPlan(Meal currentMeal, String date) {
        if (currentMeal != null){
            DatabaseMeal meal = new DatabaseMeal(currentMeal,date);
            repository.addMealToPlan(meal).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }

    @SuppressLint("CheckResult")
    public void addMealToFavorites(Meal meal) {
        repository.addMealToFavorite(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Meal parseMeal(Map<String, Object> data) {
        return new Meal(
                (String) data.get("idMeal"),
                (String) data.get("strMeal"),
                (String) data.get("strCategory"),
                (String) data.get("strArea"),
                (String) data.get("strInstructions"),
                (String) data.get("strMealThumb"),
                (String) data.get("strYoutube"),
                (String) data.get("strIngredient1"),
                (String) data.get("strIngredient2"),
                (String) data.get("strIngredient3"),
                (String) data.get("strIngredient4"),
                (String) data.get("strIngredient5"),
                (String) data.get("strIngredient6"),
                (String) data.get("strIngredient7"),
                (String) data.get("strIngredient8"),
                (String) data.get("strIngredient9"),
                (String) data.get("strIngredient10"),
                (String) data.get("strIngredient11"),
                (String) data.get("strIngredient12"),
                (String) data.get("strIngredient13"),
                (String) data.get("strIngredient14"),
                (String) data.get("strIngredient15"),
                (String) data.get("strIngredient16"),
                (String) data.get("strIngredient17"),
                (String) data.get("strIngredient18"),
                (String) data.get("strIngredient19"),
                (String) data.get("strIngredient20")
        );
    }
}
