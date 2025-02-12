package com.example.foodiesapp;

import android.app.Application;

import com.example.foodiesapp.repositories.AppContainer;

public class MealsApplication extends Application {
    private AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();
        container = new AppContainer();
    }

    public AppContainer getContainer() {
        return container;
    }
}
