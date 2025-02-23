package com.example.foodiesapp.mealResults.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentResultsBinding;
import com.example.foodiesapp.mealResults.presenter.ResultsPresenter;
import com.example.foodiesapp.mealResults.view.adapter.MealResultsAdapter;
import com.example.foodiesapp.model.meal.FilteredMeal;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsFragment extends Fragment implements ResultsContract {
    FragmentResultsBinding binding;
    ResultsFragmentArgs navArgs;
    ResultsPresenter presenter;
    MealResultsAdapter adapter;
    private static final String TAG = "ResultsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navArgs = ResultsFragmentArgs.fromBundle(getArguments());
        MealsApplication application = (MealsApplication) requireActivity().getApplication();
        presenter = new ResultsPresenter(application.getContainer().getMealsRepository(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String filterType = navArgs.getFilterType();
        String query = navArgs.getQuery();

        adapter = new MealResultsAdapter(getActivity(),this);
        binding.resultsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.resultsRv.setAdapter(adapter);
        presenter.getFilteredMeals(filterType, query);

        binding.textField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<FilteredMeal> filteredMealStream = presenter.filteredMeals.stream()
                        .filter(meals -> meals.getStrMeal().toLowerCase()
                                .startsWith(charSequence.toString()))
                        .collect(Collectors.toList());
                adapter.updateList(filteredMealStream);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.disposeObservables();
    }

    @Override
    public void assignAdapter(List<FilteredMeal> filteredMeals) {
        adapter.updateList(filteredMeals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMealDetails(String mealId) {
        NavDirections action = ResultsFragmentDirections
                .actionResultsFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }
}