package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.luxevista_resort.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button sendLinkButton = findViewById(R.id.send_reset_link_button);
        sendLinkButton.setOnClickListener(v -> {
            // In a real app, this would trigger an email sending process.
            Toast.makeText(this, "Password reset link sent (mock).", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}

