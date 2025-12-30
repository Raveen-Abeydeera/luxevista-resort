package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookingSummaryActivity extends AppCompatActivity {

    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        dbHelper = new DatabaseHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Booking Summary");
        }

        // Retrieve data from Intent
        room = (Room) getIntent().getSerializableExtra("room");
        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        // Bind views
        TextView summaryItemName = findViewById(R.id.summary_item_name);
        TextView summaryDates = findViewById(R.id.summary_dates);
        TextView summaryTotalPrice = findViewById(R.id.summary_total_price);
        Button confirmBookingButton = findViewById(R.id.confirm_booking_button);

        if (room != null && checkInDate != null && checkOutDate != null) {
            summaryItemName.setText(room.getRoomType());
            summaryDates.setText("Dates: " + checkInDate + " to " + checkOutDate);

            long nights = getNightCount(checkInDate, checkOutDate);
            double totalPrice = nights * room.getPricePerNight();
            summaryTotalPrice.setText(String.format(Locale.getDefault(), "Total for %d nights: $%.2f", nights, totalPrice));
        }

        confirmBookingButton.setOnClickListener(v -> confirmBooking());
    }

    private void confirmBooking() {
        SharedPreferences prefs = getSharedPreferences("LuxeVistaPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);

        if (userEmail == null) {
            Toast.makeText(this, "Please log in to make a booking.", Toast.LENGTH_SHORT).show();
            return;
        }

        Guest guest = dbHelper.getGuestByEmail(userEmail);
        if (guest != null && room != null) {
            long bookingId = dbHelper.addBooking(guest.getId(), "Room", room.getId(), checkInDate, checkOutDate, "Guests: 2");

            if (bookingId != -1) {
                Intent intent = new Intent(this, BookingConfirmationActivity.class);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("itemName", room.getRoomType());
                intent.putExtra("bookingDate", checkInDate + " to " + checkOutDate);
                startActivity(intent);
                finishAffinity(); // Clear the back stack
            } else {
                Toast.makeText(this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private long getNightCount(String checkIn, String checkOut) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date dateIn = sdf.parse(checkIn);
            Date dateOut = sdf.parse(checkOut);
            long diff = dateOut.getTime() - dateIn.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
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

