package com.example.foodiesapp.model.country;

public class Country {
    private String strArea;

    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public String toString() {
        return "Country{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}
