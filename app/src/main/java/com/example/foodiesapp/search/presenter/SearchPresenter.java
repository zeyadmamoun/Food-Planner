package com.example.foodiesapp.search.presenter;

import com.example.foodiesapp.model.repository.CategoryRepository;
import com.example.foodiesapp.search.view.SearchContract;

public class SearchPresenter {
    CategoryRepository categoryRepository;
    SearchContract contract;

    public SearchPresenter(CategoryRepository categoryRepository, SearchContract contract) {
        this.categoryRepository = categoryRepository;
        this.contract = contract;
    }

    public void getCategories() {
        if (categoryRepository.getCategories().isEmpty()){
            categoryRepository.getCategories(contract);
        } else {
            contract.updateCategoriesRecyclerView(categoryRepository.getCategories());
        }
    }
}
