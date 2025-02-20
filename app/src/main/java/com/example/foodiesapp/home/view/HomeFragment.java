package com.example.foodiesapp.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentHomeBinding;
import com.example.foodiesapp.home.presenter.HomePresenter;
import com.example.foodiesapp.home.view.adapters.InspireMealsAdapter;
import com.example.foodiesapp.home.view.adapters.RecommendationsAdapter;
import com.example.foodiesapp.home.view.adapters.SliderTransformer;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.model.repository.MealsRepository;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract{

    FragmentHomeBinding binding;
    HomePresenter presenter;
    InspireMealsAdapter viewPagerAdapter;
    RecommendationsAdapter recommendationsAdapter;
    public static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        MealsRepository repo = app.getContainer().getMealsRepository();
        presenter = new HomePresenter(repo,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.usernameTv.setText(" " + presenter.getUsername());
        // Initializing viewPager adapter
        viewPagerAdapter = new InspireMealsAdapter(getActivity(),this);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(3);
        binding.viewpager.setPageTransformer(new SliderTransformer(3));

        // Initializing recommendations adapter
        recommendationsAdapter = new RecommendationsAdapter(getActivity(),this);
        binding.mealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.mealsRv.setAdapter(recommendationsAdapter);

        presenter.getInspirationMeals();
        presenter.getRecommendationMeals();
    }

    @Override
    public void assignMealsListToPager(List<Meal> meals) {
        viewPagerAdapter.setList(meals);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void assignMealsListToRecommendations(List<Meal> recommendationMeals) {
        Log.i(TAG, "assignMealsListToPager: meals list size = " + recommendationMeals.size());
        recommendationsAdapter.setList(recommendationMeals);
        recommendationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}