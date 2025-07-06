package com.s23010920.studypal;

import android.content.Intent;

import android.os.Bundle;

import android.text.InputType;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.biometric.BiometricManager;

import androidx.biometric.BiometricPrompt;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private boolean passwordVisible = false;

    private DatabaseHelper dbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        EditText emailEditText = findViewById(R.id.emailEditText);

        EditText passwordEditText = findViewById(R.id.passwordEditText);

        ImageView passwordToggle = findViewById(R.id.passwordToggle);

        Button loginButton = findViewById(R.id.loginButton);

        TextView signupText = findViewById(R.id.signupText);

        TextView forgotText = findViewById(R.id.forgotText);

        // Toggle password visibility

        passwordToggle.setOnClickListener(v -> {

            passwordVisible = !passwordVisible;

            if (passwordVisible) {

                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

            } else {

                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            }

            passwordEditText.setSelection(passwordEditText.length());

        });

        // Login button with fingerprint authentication

        loginButton.setOnClickListener(v -> {

            String email = emailEditText.getText().toString().trim();

            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();

                return;

            }

            if (!dbHelper.checkUser(email, password)) {

                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                return;

            }

            BiometricManager biometricManager = BiometricManager.from(this);

            if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

                    != BiometricManager.BIOMETRIC_SUCCESS) {

                Toast.makeText(this, "Biometric authentication not available", Toast.LENGTH_SHORT).show();

                return;

            }

            Executor executor = ContextCompat.getMainExecutor(this);

            BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {

                @Override

                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {

                    super.onAuthenticationSucceeded(result);

                    runOnUiThread(() -> {

                        Toast.makeText(MainActivity.this, "Fingerprint recognized. Login successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                        intent.putExtra("email", email);

                        startActivity(intent);

                        finish();

                    });

                }

                @Override

                public void onAuthenticationError(int errorCode, CharSequence errString) {

                    super.onAuthenticationError(errorCode, errString);

                    runOnUiThread(() ->

                            Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show());

                }

                @Override

                public void onAuthenticationFailed() {

                    super.onAuthenticationFailed();

                    runOnUiThread(() ->

                            Toast.makeText(MainActivity.this, "Fingerprint not recognized", Toast.LENGTH_SHORT).show());

                }

            });

            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()

                    .setTitle("Fingerprint Authentication")

                    .setSubtitle("Place your finger on the sensor")

                    .setNegativeButtonText("Cancel")

                    .build();

            biometricPrompt.authenticate(promptInfo);

        });

        // Sign up link

        signupText.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, SignUpActivity.class));

        });

        // Forgot password link

        forgotText.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ResetPasswordActivity.class);

            intent.putExtra("email", emailEditText.getText().toString().trim());

            startActivity(intent);

        });

    }

}

