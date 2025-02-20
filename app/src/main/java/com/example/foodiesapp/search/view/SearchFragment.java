package com.example.foodiesapp.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentSearchBinding;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.country.Country;
import com.example.foodiesapp.model.ingredient.Ingredient;
import com.example.foodiesapp.search.presenter.SearchPresenter;
import com.example.foodiesapp.search.view.adapters.CategoriesAdapter;
import com.example.foodiesapp.search.view.adapters.CountriesAdapter;
import com.example.foodiesapp.search.view.adapters.IngredientsAdapter;

import java.util.List;

public class SearchFragment extends Fragment implements SearchContract {
    FragmentSearchBinding binding;
    CategoriesAdapter categoryCardsAdapter;
    CountriesAdapter countriesAdapter;
    IngredientsAdapter ingredientsAdapter;
    SearchPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        presenter = new SearchPresenter(app.getContainer().getCategoryRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // initializing adapters
        categoryCardsAdapter = new CategoriesAdapter(getActivity(), this);
        countriesAdapter = new CountriesAdapter(getActivity());
        ingredientsAdapter = new IngredientsAdapter(getActivity());

        // get all remote data.
        presenter.getCategories();
        presenter.getCountries();
        presenter.getIngredients();

        binding.chipGroup.check(binding.categoryChip.getId());
        binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.categoriesRv.setAdapter(categoryCardsAdapter);

        binding.searchBar.setOnClickListener(v -> binding.searchView.show());

        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.get(0) == binding.categoryChip.getId()) {
                binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.categoriesRv.setAdapter(categoryCardsAdapter);
            } else if (checkedIds.get(0) == binding.countryChip.getId()) {
                binding.categoriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.categoriesRv.setAdapter(countriesAdapter);
            } else {
                binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.categoriesRv.setAdapter(ingredientsAdapter);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateCategoriesRecyclerView(List<Category> categoriesList) {
        categoryCardsAdapter.setList(categoriesList);
        categoryCardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateIngredientsRecyclerView(List<Ingredient> ingredientList) {
        ingredientsAdapter.setList(ingredientList);
        ingredientsAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateCountriesRecyclerView(List<Country> countryList) {
        countriesAdapter.setList(countryList);
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}