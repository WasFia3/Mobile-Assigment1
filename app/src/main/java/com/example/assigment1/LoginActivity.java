package com.example.assigment1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private CheckedTextView rememberMe;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        rememberMe = findViewById(R.id.checkedTextView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Toggle behavior for CheckedTextView
        rememberMe.setOnClickListener(v -> {
            rememberMe.toggle();
        });

        // Load remembered email and password
        boolean isRemembered = prefs.getBoolean("REMEMBER_ME", false);
        if (isRemembered) {
            emailEditText.setText(prefs.getString("REMEMBERED_EMAIL", ""));
            passwordEditText.setText(prefs.getString("REMEMBERED_PASS", ""));
            rememberMe.setChecked(true);
        }

        loginBtn.setOnClickListener(v -> {
            String inputEmail = emailEditText.getText().toString().trim();
            String inputPassword = passwordEditText.getText().toString().trim();

            String storedEmail = prefs.getString("EMAIL", null);
            String storedPassword = prefs.getString("PASS", null);

            if (storedEmail == null || storedPassword == null) {
                Toast.makeText(LoginActivity.this, "No account found. Please sign in first.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (inputEmail.equals(storedEmail) && inputPassword.equals(storedPassword)) {
                if (rememberMe.isChecked()) {
                    prefs.edit()
                            .putBoolean("REMEMBER_ME", true)
                            .putString("REMEMBERED_EMAIL", inputEmail)
                            .putString("REMEMBERED_PASS", inputPassword)
                            .apply();
                } else {
                    prefs.edit()
                            .putBoolean("REMEMBER_ME", false)
                            .remove("REMEMBERED_EMAIL")
                            .remove("REMEMBERED_PASS")
                            .apply();
                }

                Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
