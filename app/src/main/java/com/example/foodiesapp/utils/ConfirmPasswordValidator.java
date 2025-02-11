package com.example.foodiesapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmPasswordValidator implements TextWatcher {

    private TextInputLayout passwordEditText;
    private TextInputLayout confirmPasswordEditText;

    public ConfirmPasswordValidator(TextInputLayout passwordEditText, TextInputLayout confirmPasswordEditText) {
        this.passwordEditText = passwordEditText;
        this.confirmPasswordEditText = confirmPasswordEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No implementation needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String passwordConfirmation = s.toString();
        String userPassword = passwordEditText.getEditText().getText().toString();
        if (!passwordConfirmation.equals(userPassword)) {
            confirmPasswordEditText.setError("It's not the same password as above");
        } else {
            confirmPasswordEditText.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // No implementation needed
    }
}
