package com.example.foodiesapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.loginFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            } else if (navDestination.getId() == R.id.registrationFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            } else if (navDestination.getId() == R.id.resultsFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            } else if (navDestination.getId() == R.id.mealDetailsFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            } else if (navDestination.getId() == R.id.favoritesFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }
}