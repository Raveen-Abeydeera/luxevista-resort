package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.NotificationAdapter;
import com.example.luxevista_resort.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        RecyclerView recyclerView = findViewById(R.id.notifications_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Notification> notifications = new ArrayList<>();

        notifications.add(new Notification("Today", true)); // Header
        notifications.add(new Notification("Spa Appointment Reminder", "10:30 AM"));
        notifications.add(new Notification("Lunch Reservation Confirmation", "12:00 PM"));
        notifications.add(new Notification("Yesterday", true)); // Header
        notifications.add(new Notification("Welcome to LuxeVista Resort", "09:00 AM"));
        notifications.add(new Notification("Exclusive Beachfront Dinner Offer", "02:00 PM"));

        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);
    }
}

