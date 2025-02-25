package com.example.foodiesapp.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodiesapp.MainActivity;
import com.example.foodiesapp.MealsApplication;
import com.example.foodiesapp.R;
import com.example.foodiesapp.databinding.FragmentProfileBinding;
import com.example.foodiesapp.profile.presenter.ProfilePresenter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment implements ProfileContract{
    FirebaseAuth mAuth;
    ProfilePresenter presenter;
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        MealsApplication app = (MealsApplication) getActivity().getApplication();
        presenter = new ProfilePresenter(app.getContainer().getMealsRepository(),this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imgUrl = presenter.getUserAvatar();
        if (imgUrl != null){
            Glide.with(requireView()).load(presenter.getUserAvatar()).into(binding.userAvatar);
        }
        binding.usernameTv.setText(presenter.getUsername());
        binding.userEmailTv.setText(presenter.getUserEmail());

        binding.backupDataLl.setOnClickListener(view1 -> {
            MealsApplication app = (MealsApplication) getActivity().getApplication();
            if (app.getGuestModeOn()){
                MainActivity.showDialog(Navigation.findNavController(requireView()), R.id.loginFragment,getActivity());
                return;
            }
            presenter.getUserPlanAndBackItUp();
            presenter.getUserFavouritesAndBackItUp();
        });

        binding.getBackupLl.setOnClickListener(view1 -> {
            MealsApplication app = (MealsApplication) getActivity().getApplication();
            if (app.getGuestModeOn()){
                MainActivity.showDialog(Navigation.findNavController(requireView()), R.id.loginFragment,getActivity());
                return;
            }
            presenter.getFavoritesFromFirestore();
            presenter.getPlanMealsFromFirestore();
        });

        binding.favoritesLl.setOnClickListener(view1 -> {
            MealsApplication app = (MealsApplication) getActivity().getApplication();
            if (app.getGuestModeOn()){
                MainActivity.showDialog(Navigation.findNavController(requireView()),R.id.loginFragment,getActivity());
                return;
            }
            NavDirections action = ProfileFragmentDirections.actionProfileFragmentToFavoritesFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        binding.aboutLl.setOnClickListener(view1 -> {
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Foodies App")
                    .setMessage("Made by: Zeyad Maamoun")
                    .setNeutralButton(R.string.about_dialog,(dialogInterface, i) -> {
                        dialogInterface.cancel();
                    }).show();
        });

        binding.signOutLl.setOnClickListener(view1 -> {
            if (mAuth.getCurrentUser()!= null){
                mAuth.signOut();
            }
            NavDirections action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}