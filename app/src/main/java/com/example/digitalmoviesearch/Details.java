package com.example.digitalmoviesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.digitalmoviesearch.databinding.ActivityDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        binding.telegramlinkshow.setText(getIntent().getStringExtra("telegramlink"));
        binding.movielinkshow.setText(getIntent().getStringExtra("movielink"));
        binding.movieName.setText(getIntent().getStringExtra("movieName"));
        binding.movieDescription.setText(getIntent().getStringExtra("movieDescription"));
        binding.movieLaunchDate.setText(getIntent().getStringExtra("movieLaunchDate"));
        binding.movieRating.setText(getIntent().getStringExtra("movieRating"));
        Picasso.get().load(getIntent().getStringExtra("movieImage")).placeholder(R.drawable.placeholder).into(binding.movieImage);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(Details.this, MainActivity.class));
    }
}