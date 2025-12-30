package com.example.luxevista_resort.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.RoomAdapter;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Room;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RoomListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;
    private DatabaseHelper dbHelper;
    private TextView noResultsTextView;
    private TextView dateRangeTextView;
    private String checkInDate, checkOutDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Available Rooms");
        }

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.room_recycler_view);
        noResultsTextView = findViewById(R.id.no_rooms_text);
        dateRangeTextView = findViewById(R.id.date_range_text);
        Button filterButton = findViewById(R.id.filter_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initial load of all rooms
        roomList = dbHelper.getAvailableRooms(null, null, 1);
        roomAdapter = new RoomAdapter(this, roomList);
        recyclerView.setAdapter(roomAdapter);

        filterButton.setOnClickListener(v -> showDateRangePicker());
    }

    private void showDateRangePicker() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.show(getSupportFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            checkInDate = sdf.format(new Date(selection.first));
            checkOutDate = sdf.format(new Date(selection.second));

            dateRangeTextView.setText("Selected: " + checkInDate + " to " + checkOutDate);
            loadRooms(checkInDate, checkOutDate, 1);
        });
    }

    private void loadRooms(String checkIn, String checkOut, int guests) {
        roomList = dbHelper.getAvailableRooms(checkIn, checkOut, guests);

        if (roomList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noResultsTextView.setVisibility(View.GONE);
            roomAdapter = new RoomAdapter(this, roomList);
            roomAdapter.setBookingDates(checkIn, checkOut); // Pass dates to adapter
            recyclerView.setAdapter(roomAdapter);
        }
    }
    private void updateRoomList() {
        // Assuming a default of 1 guest for now. This can be expanded with a guest selector.
        List<Room> filteredRooms = dbHelper.getAvailableRooms(checkInDate, checkOutDate, 1);
        roomAdapter.updateRooms(filteredRooms);
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

