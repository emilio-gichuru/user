package com.ecoville.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebase_auth;

    @Override
    protected void onStart() {
        super.onStart();

        firebase_auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebase_auth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
    }
}
