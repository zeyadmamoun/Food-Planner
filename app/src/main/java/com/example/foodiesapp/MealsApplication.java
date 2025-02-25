package com.example.foodiesapp;

import android.app.Application;

import com.example.foodiesapp.model.AppContainer;

public class MealsApplication extends Application {
    private AppContainer container;
    private Boolean isGuestModeOn = false;

    @Override
    public void onCreate() {
        super.onCreate();
        container = new AppContainer(this.getApplicationContext());
    }

    public AppContainer getContainer() {
        return container;
    }

    public Boolean getGuestModeOn() {
        return isGuestModeOn;
    }

    public void setGuestModeOn(Boolean guestModeOn) {
        isGuestModeOn = guestModeOn;
    }
}
