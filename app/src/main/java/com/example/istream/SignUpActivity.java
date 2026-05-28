package com.example.istream;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etFullName        = findViewById(R.id.etFullName);
        EditText etUsername        = findViewById(R.id.etUsername);
        EditText etPassword        = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnCreate           = findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> {
            String fullName  = etFullName.getText().toString().trim();
            String username  = etUsername.getText().toString().trim();
            String password  = etPassword.getText().toString().trim();
            String confirm   = etConfirmPassword.getText().toString().trim();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            AppDatabase db = AppDatabase.getInstance(this);

            if (db.userDao().findByUsername(username) != null) {
                Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
                return;
            }

            User user     = new User();
            user.fullName = fullName;
            user.username = username;
            user.password = password;
            db.userDao().insert(user);

            Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}