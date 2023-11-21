package com.ecoville.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebase_auth;
    private FirebaseFirestore firebase_firestore;


    @Override
    protected void onStart() {
        super.onStart();
        firebase_auth = FirebaseAuth.getInstance();
        firebase_firestore = FirebaseFirestore.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        Button sign_out = (Button) findViewById(R.id.sign_out);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firebase_auth.signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        TextView user_name = (TextView) findViewById(R.id.user_name);
        TextView user_gender = (TextView) findViewById(R.id.user_gender);
        TextView user_bio = (TextView) findViewById(R.id.user_bio);
        TextView user_skill = (TextView) findViewById(R.id.user_skill);

//        user_name.setText(firebase_user.getDisplayName());

        Task<QuerySnapshot> documentRef = firebase_firestore.collection("users").get();

        documentRef.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                } else {
                    Toast.makeText(HomeActivity.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}