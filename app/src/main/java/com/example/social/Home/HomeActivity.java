package com.example.social.Home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.social.LogIn.LogInActivity;
import com.example.social.R;
import com.example.social.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){ //if true it means no user is logged in
                    Intent goToLogIn_intent = new Intent(HomeActivity.this, LogInActivity.class);
                    goToLogIn_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clears the current activity as well as all others on top of it in the stack so that user wont be able to go back
                    startActivity(goToLogIn_intent);

                }else{
                    Toast.makeText(HomeActivity.this,"logged in as: "+ firebaseAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}