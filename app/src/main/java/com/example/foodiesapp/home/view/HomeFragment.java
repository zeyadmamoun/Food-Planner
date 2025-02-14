package com.example.foodiesapp.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.databinding.FragmentHomeBinding;
import com.example.foodiesapp.home.presenter.HomePresenter;
import com.example.foodiesapp.model.Meal;
import com.example.foodiesapp.model.MealsRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract{

    private FirebaseAuth mAuth;
    FragmentHomeBinding binding;
    HomePresenter presenter;
    InspireMealsAdapter adapter;
    public static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        MealsRepository repo = app.getContainer().getRepository();
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
        adapter = new InspireMealsAdapter(getActivity(),this);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(3);
        binding.viewpager.setPageTransformer(new SliderTransformer(3));
        presenter.getInspirationMeals();

        binding.signout.setOnClickListener(view1 -> {
            mAuth.signOut();
            Navigation.findNavController(requireView()).navigateUp();
        });
    }

    @Override
    public void assignMealsListToPager(List<Meal> meals) {
        Log.i(TAG, "assignMealsListToPager: meals list size = " + meals.size());
        adapter.setList(meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}