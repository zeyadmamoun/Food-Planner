package com.example.foodiesapp.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentSearchBinding;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.model.country.Country;
import com.example.foodiesapp.model.ingredient.Ingredient;
import com.example.foodiesapp.model.searchResults.QueryResult;
import com.example.foodiesapp.search.presenter.SearchPresenter;
import com.example.foodiesapp.search.view.adapters.CategoriesAdapter;
import com.example.foodiesapp.search.view.adapters.CountriesAdapter;
import com.example.foodiesapp.search.view.adapters.IngredientsAdapter;
import com.example.foodiesapp.search.view.adapters.SearchResultsAdapter;
import com.google.android.material.search.SearchView;

import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment implements SearchContract {
    FragmentSearchBinding binding;
    CategoriesAdapter categoryCardsAdapter;
    CountriesAdapter countriesAdapter;
    IngredientsAdapter ingredientsAdapter;
    SearchResultsAdapter searchResultsAdapter;
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // initializing adapters
        categoryCardsAdapter = new CategoriesAdapter(getActivity(), this);
        countriesAdapter = new CountriesAdapter(getActivity(),this);
        ingredientsAdapter = new IngredientsAdapter(getActivity(),this);
        searchResultsAdapter = new SearchResultsAdapter(getActivity(),this);

        // get all remote data.
        presenter.getCategories();
        presenter.getCountries();
        presenter.getIngredients();

        // Adjusting chip group to select category chip by default
        binding.chipGroup.check(binding.categoryChip.getId());
        binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.categoriesRv.setAdapter(categoryCardsAdapter);

        // handling the recyclerview inside the search view
        binding.searchBar.setOnClickListener(v -> binding.searchView.show());
        binding.searchView.addTransitionListener((searchView, previousState, newState) -> {
            if (newState == SearchView.TransitionState.SHOWING) {
                binding.resultsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.resultsRv.setAdapter(searchResultsAdapter);
                searchResultsAdapter.updateList(presenter.getResults());
                searchResultsAdapter.notifyDataSetChanged();
            }
        });

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<QueryResult> userQueryResults = presenter.getResults().stream()
                        .filter(queryResult -> queryResult.getName().toLowerCase()
                                .startsWith(charSequence.toString()))
                        .collect(Collectors.toList());
                searchResultsAdapter.updateList(userQueryResults);
                searchResultsAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // handling chip selection.
        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.get(0) == binding.categoryChip.getId()) {
                binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.categoriesRv.setAdapter(categoryCardsAdapter);
                presenter.updateSearchMode(SearchPresenter.SearchMode.Category);

            } else if (checkedIds.get(0) == binding.countryChip.getId()) {
                binding.categoriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.categoriesRv.setAdapter(countriesAdapter);
                presenter.updateSearchMode(SearchPresenter.SearchMode.Country);
            } else {
                binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                binding.categoriesRv.setAdapter(ingredientsAdapter);
                presenter.updateSearchMode(SearchPresenter.SearchMode.Ingredient);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateCategoriesRecyclerView(List<Category> categoriesList) {
        presenter.updateSearchMode(SearchPresenter.SearchMode.Category);
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
    public void NavigateToFilteredMeals(String query) {
        NavDirections action = SearchFragmentDirections
                .actionSearchFragmentToResultsFragment(presenter.currentSearchMode.toString(),query);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}