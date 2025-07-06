package com.s23010920.studypal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etEmail, etNewPassword, etConfirmNewPassword;
    private Button btnResetPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        dbHelper = new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // Pre-fill email if passed from previous activity
        String email = getIntent().getStringExtra("email");
        if (email != null) etEmail.setText(email);

        btnResetPassword.setOnClickListener(v -> {
            String emailInput = etEmail.getText().toString().trim();
            String newPass = etNewPassword.getText().toString();
            String confirmPass = etConfirmNewPassword.getText().toString();

            if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confirmPass)) {
                Toast.makeText(ResetPasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!dbHelper.checkEmailExists(emailInput)) {
                Toast.makeText(ResetPasswordActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
            } else if (!newPass.equals(confirmPass)) {
                Toast.makeText(ResetPasswordActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = dbHelper.updatePassword(emailInput, newPass);
                if (updated) {
                    Toast.makeText(ResetPasswordActivity.this, "Password reset successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Password reset failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}