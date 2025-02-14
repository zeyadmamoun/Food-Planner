package com.example.foodiesapp.authentication.registration;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationPresenter {
    RegistrationContract contract;
    private final FirebaseAuth mAuth;

    public RegistrationPresenter(RegistrationContract contract) {
        this.contract = contract;
        mAuth = FirebaseAuth.getInstance();
    }

    public void signup(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d(RegistrationFragment.TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        updateUsername(user,username);
                        contract.navigateToHomeScreen();
                    } else {
                        Log.d(RegistrationFragment.TAG, "createUserWithEmail:success");
                        contract.showToast("Registration Failed, please try again");
                    }
                });
    }

    // function to update the name in user profile in the firebase
    private void updateUsername(FirebaseUser user, String username){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Profile updated successfully
                        Log.d("TAG", "User profile updated.");
                    } else {
                        // error updating the name
                        Log.e("TAG", "Error updating profile", task.getException());
                    }
                });
    }
}
