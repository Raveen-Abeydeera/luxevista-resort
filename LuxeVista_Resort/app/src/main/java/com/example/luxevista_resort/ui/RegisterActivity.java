package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText, addressEditText, passwordEditText, confirmPasswordEditText;
    CheckBox termsCheckBox;
    Button registerButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);
        firstNameEditText = findViewById(R.id.register_first_name);
        lastNameEditText = findViewById(R.id.register_last_name);
        emailEditText = findViewById(R.id.register_email);
        phoneEditText = findViewById(R.id.register_phone);
        addressEditText = findViewById(R.id.register_address);
        passwordEditText = findViewById(R.id.register_password);
        confirmPasswordEditText = findViewById(R.id.register_confirm_password);
        termsCheckBox = findViewById(R.id.terms_checkbox);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (validateInput(firstName, lastName, email, phone, address, password, confirmPassword)) {
                if (!dbHelper.checkEmailExists(email)) {
                    if (dbHelper.addGuest(firstName, lastName, email, password, phone, address)) {
                        Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Email already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String firstName, String lastName, String email, String phone, String address, String password, String confirmPassword) {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Valid email is required");
            return false;
        }
        if (password.length() < 8) {
            passwordEditText.setError("Password must be at least 8 characters");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        if (!termsCheckBox.isChecked()) {
            Toast.makeText(this, "You must agree to the Terms of Service and Privacy Policy", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

