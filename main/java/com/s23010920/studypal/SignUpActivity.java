package com.s23010920.studypal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText, contactEditText;
    private Button signUpButton;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        contactEditText = findViewById(R.id.contactEditText);
        signUpButton = findViewById(R.id.signUpButton);
        tvLogin = findViewById(R.id.tvLogin);

        signUpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String contact = contactEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailEditText.setError("Email is required");
            } else if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Password is required");
            } else if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordEditText.setError("Confirm your password");
            } else if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
            } else if (TextUtils.isEmpty(contact)) {
                contactEditText.setError("Contact number is required");
            } else if (dbHelper.checkEmailExists(email)) {
                emailEditText.setError("Email already registered");
            } else {
                boolean inserted = dbHelper.registerUser(email, password, contact);
                if (inserted) {
                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        });
    }
}