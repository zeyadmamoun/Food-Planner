package com.example.foodiesapp.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentFavoritesBinding;
import com.example.foodiesapp.model.meal.Meal;
import com.example.foodiesapp.profile.presenter.FavoritePresenter;
import com.example.foodiesapp.profile.view.adpter.FavoriteMealsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment implements FavoriteContract{
    FavoritePresenter presenter;
    FavoriteMealsAdapter adapter;
    FragmentFavoritesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MealsApplication application = (MealsApplication) getActivity().getApplication();
        presenter = new FavoritePresenter(application.getContainer().getMealsRepository(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.favoriteMealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteMealsAdapter(getActivity(),this);
        binding.favoriteMealsRv.setAdapter(adapter);

        presenter.getFavoriteMeals();
    }

    @Override
    public void assignFavoritesToAdapter(List<Meal> favoriteMeals) {
        binding.favoriteMealsRv.setVisibility(View.VISIBLE);
        binding.emptyPlanLl.setVisibility(View.INVISIBLE);
        adapter.setList(favoriteMeals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMealDetail(String id) {
        NavDirections action = FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailsFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showEmptyListIcon() {
        binding.favoriteMealsRv.setVisibility(View.INVISIBLE);
        binding.emptyPlanLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeMealFromFavorite(Meal meal) {
        presenter.removeMealFromFavorites(meal);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}