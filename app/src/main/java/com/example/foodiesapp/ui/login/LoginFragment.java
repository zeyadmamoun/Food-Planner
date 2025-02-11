package com.example.foodiesapp.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiesapp.databinding.FragmentLoginBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;

import java.util.List;

public class LoginFragment extends Fragment implements LoginContract {
    private FragmentLoginBinding binding;
    private LoginPresenter presenter;
    private static final String TAG = "LoginFragment";
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> presenter.signInUserWithGoogleResult(result)
    );
    List<AuthUI.IdpConfig> providers = List.of(
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
    }

    // here we check if user is already signed in.
    @Override
    public void onStart() {
        super.onStart();
        presenter.isUserSignedIn();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create binding file and Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginBtn.setOnClickListener(view1 -> {
            String email = binding.emailOutlinedTextField.getEditText().getText().toString();
            String password = binding.passwordOutlinedTextField.getEditText().getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Enter your credentials");
                return;
            }
            presenter.signInUser(email, password);
        });

        binding.guestModeBtn.setOnClickListener(view3 -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });

        binding.googleIcon.setOnClickListener(view4 -> {
            // Create and launch sign-in intent
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch(signInIntent);
        });

        binding.switchToSignupTv.setOnClickListener(view2 -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment();
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void navigateToHomeScreen() {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}