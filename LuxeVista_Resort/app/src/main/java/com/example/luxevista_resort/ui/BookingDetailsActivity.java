package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Booking;

public class BookingDetailsActivity extends AppCompatActivity {

    private Booking booking;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        dbHelper = new DatabaseHelper(this);
        booking = (Booking) getIntent().getSerializableExtra("booking");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Booking Details");
        }


        TextView itemName = findViewById(R.id.details_item_name);
        TextView bookingDate = findViewById(R.id.details_dates);
        TextView bookingExtraDetails = findViewById(R.id.details_specifics);
        Button cancelButton = findViewById(R.id.cancel_booking_button);

        if (booking != null) {
            itemName.setText(booking.getItemName());
            bookingDate.setText("Date: " + booking.getDate());
            bookingExtraDetails.setText("Details: " + booking.getDetails());

            cancelButton.setOnClickListener(v -> showCancelConfirmationDialog());
        }
    }

    private void showCancelConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    cancelBooking();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void cancelBooking() {
        if (booking != null) {
            boolean success = dbHelper.deleteBooking(booking.getId());
            if (success) {
                Toast.makeText(this, "Booking cancelled successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Failed to cancel booking", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
