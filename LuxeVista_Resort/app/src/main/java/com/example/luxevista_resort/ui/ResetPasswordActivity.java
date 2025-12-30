package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.luxevista_resort.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Button setPasswordButton = findViewById(R.id.set_new_password_button);
        setPasswordButton.setOnClickListener(v -> {

            Toast.makeText(this, "Password has been reset (mock).", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}

