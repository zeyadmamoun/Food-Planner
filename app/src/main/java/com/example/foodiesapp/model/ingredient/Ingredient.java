package com.example.foodiesapp.model.ingredient;

public class Ingredient {
    private String idIngredient;
    private String strIngredient;

    public Ingredient(String idIngredient, String strIngredient) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }
}
