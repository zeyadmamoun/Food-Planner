package com.example.foodiesapp.authentication.login;

import static android.app.Activity.RESULT_OK;

import android.util.Log;

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter {
    LoginContract contract;
    private final FirebaseAuth mAuth;
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(LoginContract contract) {
        this.contract = contract;
        mAuth = FirebaseAuth.getInstance();
    }

    // Check if user is signed in (non-null) and update UI accordingly.
    public void isUserSignedIn() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            contract.navigateToHomeScreen();
        }
    }

    public void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        contract.navigateToHomeScreen();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        contract.showToast("Authentication failed");
                    }
                });
    }

    public void signInUserWithGoogleResult(FirebaseAuthUIAuthenticationResult result){
        if (result.getResultCode() == RESULT_OK) {
            // Sign in success, update UI
            contract.navigateToHomeScreen();
        } else {
            // If sign in fails, display a message to the user.
            contract.showToast("Authentication with google account failed");
        }
    }
}
