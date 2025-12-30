package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.luxevista_resort.R;

public class BookingConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        TextView bookingIdTextView = findViewById(R.id.booking_ref_text);
        TextView bookingItemNameTextView = findViewById(R.id.booking_conf_item_name_text);
        TextView bookingDateTextView = findViewById(R.id.booking_conf_date_text);
        Button backToHomeButton = findViewById(R.id.back_to_home_button);

        long bookingId = getIntent().getLongExtra("bookingId", -1);
        String itemName = getIntent().getStringExtra("itemName");
        String bookingDate = getIntent().getStringExtra("bookingDate");

        if (bookingId != -1) {
            bookingIdTextView.setText("Booking Reference: #LVR" + bookingId);
            bookingItemNameTextView.setText(itemName);
            bookingDateTextView.setText("Date: " + bookingDate);
        }

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookingConfirmationActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}

