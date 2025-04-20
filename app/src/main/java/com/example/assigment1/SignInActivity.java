package com.example.assigment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button signInBtn, haveAccountBtn;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // connecting views with their IDs
        edtEmail = findViewById(R.id.editTextEmail);
        edtPassword = findViewById(R.id.editTextPassword);
        signInBtn = findViewById(R.id.signInBtn);
        haveAccountBtn = findViewById(R.id.haveAccountBtn);

        // SharedPreferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        // click on sign in
        signInBtn.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            // check if fields are empty
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(SignInActivity.this, "Please fill fields before", Toast.LENGTH_SHORT).show();
                return;
            }

            // (pattern: something@something.com)
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SignInActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // password should contain numbers and letters
            boolean hasLetter = false;
            boolean hasDigit = false;

            for (char c : password.toCharArray()) {
                if (Character.isLetter(c)) {
                    hasLetter = true;
                } else if (Character.isDigit(c)) {
                    hasDigit = true;
                }
            }

            if (!(hasLetter && hasDigit)) {
                Toast.makeText(SignInActivity.this, "Password must contain both letters and numbers", Toast.LENGTH_SHORT).show();
                return;
            }

            // saving users' info in SharedPreferences
            editor.putString("EMAIL", email);
            editor.putString("PASS", password);
            editor.putBoolean("IS_SIGNED_IN", true); // sign that user signed in
            editor.apply();

            // going to login screen after signing in
            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // quit this activity
        });

        // click on "I have an account" button
        haveAccountBtn.setOnClickListener(v -> {
            // Navigate to LoginActivity
            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
