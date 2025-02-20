package com.example.foodiesapp.model.country;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {
    @SerializedName("meals")
    private List<Country> countries;

    public CountryResponse(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
