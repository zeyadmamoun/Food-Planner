package com.example.foodiesapp.ui.registration;

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

import com.example.foodiesapp.databinding.FragmentRegistrationBinding;
import com.example.foodiesapp.utils.ConfirmPasswordValidator;
import com.example.foodiesapp.utils.EmailValidator;
import com.example.foodiesapp.utils.PasswordValidator;

public class RegistrationFragment extends Fragment implements RegistrationContract {

    FragmentRegistrationBinding binding;
    RegistrationPresenter presenter;
    public static final String TAG = "RegistrationFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegistrationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create binding file and Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.emailOutlinedTextField.getEditText().
                addTextChangedListener(new EmailValidator(binding.emailOutlinedTextField));

        binding.passwordOutlinedTextField.getEditText()
                .addTextChangedListener(new PasswordValidator(binding.passwordOutlinedTextField));

        binding.confirmPasswordOutlinedTextField.getEditText()
                .addTextChangedListener(
                        new ConfirmPasswordValidator(binding.passwordOutlinedTextField, binding.confirmPasswordOutlinedTextField)
                );


        binding.registerBtn.setOnClickListener(view1 -> {
            String username = binding.usernameEt.getText().toString();
            String email = binding.emailEt.getText().toString();
            String password = binding.passwordEt.getText().toString();
            String confirmPassword = binding.confirmPasswordEt.getText().toString();

            // validation on email, password,confirm password fields
            if (binding.confirmPasswordOutlinedTextField.getError() != null) {
                showToast("Please fix the confirm password error.");
            } else if (binding.emailOutlinedTextField.getError() != null) {
                showToast("Please fix the email error.");
            } else if (binding.passwordOutlinedTextField.getError() != null) {
                showToast("Please fix the password error.");
            } else {
                // No errors, proceed with signup
                presenter.signup(username, email, password);
            }
        });

        binding.switchToLoginTv.setOnClickListener(view2 -> {
            NavDirections action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment();
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
        NavDirections action = RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}