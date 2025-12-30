package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.example.luxevista_resort.R;

public class HelpSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);


        findViewById(R.id.contact_call).setOnClickListener(v -> Toast.makeText(this, "Calling support...", Toast.LENGTH_SHORT).show());
        findViewById(R.id.contact_email).setOnClickListener(v -> Toast.makeText(this, "Opening email client...", Toast.LENGTH_SHORT).show());
        findViewById(R.id.contact_chat).setOnClickListener(v -> Toast.makeText(this, "Starting live chat...", Toast.LENGTH_SHORT).show());
    }
}

