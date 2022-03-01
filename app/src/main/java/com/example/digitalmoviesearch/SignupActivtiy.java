package com.example.digitalmoviesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.digitalmoviesearch.databinding.ActivitySignupActivtiyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivtiy extends AppCompatActivity {

    ActivitySignupActivtiyBinding   binding;

    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupActivtiyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivtiy.this, LoginActivity.class));
            }
        });


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                password = binding.password.getText().toString();
                email = binding.email.getText().toString();

                if (!email.matches(" ") && !password.matches(" ") );{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "onComplete: User Create Task Successful");
                                Users users = new Users();
                                users.setUsername(binding.userName.getText().toString());
                                users.setEmail(email);
                                users.setPassword(password);

                                String id = task.getResult().getUser().getUid();

                                database.getReference()
                                        .child("Users")
                                        .child(id)
                                        .setValue(users);
                                Toast.makeText(SignupActivtiy.this, "User Created Successfully!!!", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignupActivtiy.this, MainActivity.class));

                            }else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithCustomToken:failure", task.getException());
                                Toast.makeText(SignupActivtiy.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                    });

                }
            }
        });

    }


    class  Users {
        String username, password, email;

        public Users(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public Users() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }



}