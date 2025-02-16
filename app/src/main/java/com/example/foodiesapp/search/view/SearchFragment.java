package com.example.foodiesapp.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentSearchBinding;
import com.example.foodiesapp.model.category.Category;
import com.example.foodiesapp.search.presenter.SearchPresenter;
import com.example.foodiesapp.search.view.adapters.CategoryCardsAdapter;

import java.util.List;

public class SearchFragment extends Fragment implements SearchContract {
    FragmentSearchBinding binding;
    CategoryCardsAdapter categoryCardsAdapter;
    SearchPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        presenter = new SearchPresenter(app.getContainer().getCategoryRepository(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.categoriesRv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        categoryCardsAdapter = new CategoryCardsAdapter(getActivity(),this);
        binding.categoriesRv.setAdapter(categoryCardsAdapter);
        presenter.getCategories();
    }

    @Override
    public void updateCategoriesRecyclerView(List<Category> categoriesList) {
        categoryCardsAdapter.setList(categoriesList);
        categoryCardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateIngredientsRecyclerView() {
    }

    @Override
    public void updateCountriesRecyclerView() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}