package com.example.foodiesapp.ui.home;

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
import com.example.foodiesapp.repositories.MealsRepository;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements HomeContract{

    private FirebaseAuth mAuth;
    FragmentHomeBinding binding;
    HomePresenter presenter;
    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        MealsApplication app = (MealsApplication) requireActivity().getApplication();
        MealsRepository repo = new MealsRepository(app.getContainer().service);
        presenter = new HomePresenter(repo);
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

        presenter.getInspirationMeals();

        binding.signout.setOnClickListener(view1 -> {
            mAuth.signOut();
            Navigation.findNavController(requireView()).navigateUp();
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}