package com.ecoville.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_register);




        Spinner gender = (Spinner) findViewById(R.id.gender);
        TextView login = (TextView) findViewById(R.id.login);

        String [] items = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        gender.setAdapter(adapter);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
        EditText bio = (EditText) findViewById(R.id.bio);
        EditText skill = (EditText) findViewById(R.id.skill);
        EditText phone_number = (EditText) findViewById(R.id.phone_number);
        EditText user_name = (EditText) findViewById(R.id.name);
        Button register_button = (Button) findViewById(R.id.register_button);


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("registering in...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                firebase_auth.createUserWithEmailAndPassword(String.valueOf(email.getText()), String.valueOf(password.getText())).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            FirebaseUser firebase_user = FirebaseAuth.getInstance().getCurrentUser();
                            Map<String, Object> user = new HashMap<>();
                            user.put("name", String.valueOf(user_name.getText()));
                            user.put("email", String.valueOf(email.getText()));
                            user.put("bio", String.valueOf(bio.getText()));
                            user.put("skill", String.valueOf(skill.getText()));
                            user.put("phone", String.valueOf(phone_number.getText()));


                            firebase_firestore.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(RegisterActivity.this, "User profile has been created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "user profile has not been created", Toast.LENGTH_SHORT).show();
                                }
                            });




                        }else{
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
            }
        });






    }
}