package com.example.luxevista_resort.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.BookingAdapter;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Booking;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookingList;
    private DatabaseHelper dbHelper;
    private TextView noBookingsTextView;

    private final ActivityResultLauncher<Intent> bookingDetailsLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    loadBookings();
                }
            }
    );

    public BookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());
        recyclerView = view.findViewById(R.id.bookings_recycler_view);
        noBookingsTextView = view.findViewById(R.id.no_bookings_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingList = new ArrayList<>();

        adapter = new BookingAdapter(getContext(), bookingList) {
            @Override
            public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, com.example.luxevista_resort.ui.BookingDetailsActivity.class);
                    intent.putExtra("booking", bookingList.get(position));
                    bookingDetailsLauncher.launch(intent);
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookings();
    }

    private void loadBookings() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("LuxeVistaPrefs", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);

        if (userEmail != null) {
            Guest guest = dbHelper.getGuestByEmail(userEmail);
            if (guest != null) {
                List<Booking> userBookings = dbHelper.getGuestBookingsWithImage(guest.getId());
                if (userBookings.isEmpty()) {
                    noBookingsTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noBookingsTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.updateData(userBookings);
                }
            }
        } else {
            noBookingsTextView.setText("Please log in to see your bookings.");
            noBookingsTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
