package com.example.assigment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView cartIcon;

    private  ImageView profileIcon;

    private ImageView homeIcon;

    private TextView profileEmailTextView, profilePasswordTextView;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        profileEmailTextView = findViewById(R.id.profileEmail);
        profilePasswordTextView = findViewById(R.id.profilePassword);
        cartIcon = findViewById(R.id.cartIcon);
        profileIcon = findViewById(R.id.profileIcon);
        homeIcon = findViewById(R.id.homeIcon);

        // Get SharedPreferences instance
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Retrieve stored user data
        String storedEmail = prefs.getString("EMAIL", "No email found");
        String storedPassword = prefs.getString("PASS", "No password found");

        // Set the data to TextViews
        profileEmailTextView.setText("Email: " + storedEmail);
        profilePasswordTextView.setText("Password: " + storedPassword); // You may want to hide the password for security

        // Checkout action!
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // Checkout action!
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Checkout action!
        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

    }
}
