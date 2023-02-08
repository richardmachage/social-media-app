package com.example.social.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.social.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Friends Wema");
    }

    @Override
    public void onBackPressed() {

    }
}