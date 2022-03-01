package com.example.digitalmoviesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.digitalmoviesearch.databinding.ActivityAddMoviesBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddMoviesActivity extends AppCompatActivity {

ActivityAddMoviesBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String movieKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoviesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Mynotification","Mynotification",NotificationManager.IMPORTANCE_DEFAULT);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

             auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            storage = FirebaseStorage.getInstance();

            binding.btnAddMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String movieName, movieDescription, movieRating, movieLaunchDate, telegramlink ,movielink;
                    movieName = binding.movieName.getText().toString();
                    movieDescription = binding.movieDescription.getText().toString();
                    movieRating = binding.movieRating.getText().toString();
                    movieLaunchDate = binding.movieLaunchDate.getText().toString();
                    telegramlink = binding.telegramlink.getText().toString();
                    movielink = binding.movielink.getText().toString();

                    if (!movieName.matches("") && !movieDescription.matches("") && !movieRating.matches("")
                            && !movieLaunchDate.matches("") && !telegramlink.matches("") && !movielink.matches("")) {
                        Movies movies = new Movies();
                        movies.setMovieName(movieName);
                        movies.setMovieDescription(movieDescription);
                        movies.setMovieRating(movieRating);
                        movies.setMovieLaunchDate(movieLaunchDate);
                        movies.setMovielink(movielink);
                        movies.setTelegramlink(telegramlink);

                        DatabaseReference ref = database.getReference().child("Movies");
                        movieKey = ref.push().getKey();

                        ref.child(movieKey).setValue(movies).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddMoviesActivity.this, "Movie Added successfully,\nPlease add movie Image now", Toast.LENGTH_SHORT).show();
                                binding.movieImage.setVisibility(View.VISIBLE);
                                binding.btnAddMovie.setText("Add Movie Image");
                            }
                        });
                    }
                }
            });

            binding.movieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 11);
                }
            });
        }




        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 11) {
                if (data.getData() != null) {
//                dialog.show();
                    Uri uri = data.getData();
                    binding.movieImage.setImageURI(uri);

                    binding.btnAddMovie.setOnClickListener(new View.OnClickListener() {
                        final StorageReference reference = storage.getReference().child("movie_images")
                                .child(movieKey);

                        @Override
                        public void onClick(View v) {
                            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        dialog.dismiss();
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            database.getReference()
                                                    .child("Movies")
                                                    .child(movieKey)
                                                    .child("movieImage")
                                                    .setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    startActivity(new Intent(AddMoviesActivity.this, MainActivity.class));
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.logout:
                    auth.signOut();
                    startActivity(new Intent(AddMoviesActivity.this, LoginActivity.class));
                    break;
                case R.id.Addmovie:
                    startActivity(new Intent(AddMoviesActivity.this, AddMoviesActivity.class));
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
            startActivity(new Intent(AddMoviesActivity.this, MainActivity.class));
        }

    }
