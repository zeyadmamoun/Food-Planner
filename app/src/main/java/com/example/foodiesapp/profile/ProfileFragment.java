package com.example.foodiesapp.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodiesapp.R;
import com.example.foodiesapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button2.setOnClickListener(view1 -> {
            mAuth.signOut();
            NavDirections action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }
}