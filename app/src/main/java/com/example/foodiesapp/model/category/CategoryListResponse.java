package com.example.foodiesapp.model.category;

import java.util.List;

public class CategoryListResponse {
    List<Category> categories;

    public CategoryListResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "MealsListResponse{" +
                "meals=" + categories +
                '}';
    }
}
