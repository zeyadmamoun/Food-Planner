package com.example.foodiesapp.utils;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements TextWatcher {
    TextInputLayout textField;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public EmailValidator(TextInputLayout emailTextField) {
        textField = emailTextField;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(charSequence);
        if (!matcher.matches()){
            textField.setError("please enter valid email format");
        } else {
            textField.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
