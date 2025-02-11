package com.example.foodiesapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordValidator implements TextWatcher {

    private TextInputLayout passwordEditText;

    public PasswordValidator(TextInputLayout passwordEditText) {
        this.passwordEditText = passwordEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No implementation needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String password = s.toString();
        if (password.length() < 6) {
            passwordEditText.setError("Password must be more than 6 letters");
        } else {
            passwordEditText.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No implementation needed
    }
}