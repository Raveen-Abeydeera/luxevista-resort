package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.luxevista_resort.R;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Button saveButton = findViewById(R.id.save_payment_button);
        saveButton.setOnClickListener(v -> {
            Toast.makeText(this, "Payment details saved.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

