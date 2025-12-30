package com.example.luxevista_resort.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Booking;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.ui.BookingDetailsActivity;
import com.example.luxevista_resort.ui.RoomListActivity;
import com.example.luxevista_resort.ui.SearchResultsActivity;
import com.example.luxevista_resort.ui.ServiceListActivity;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView welcomeTextView = view.findViewById(R.id.home_welcome_text);
        EditText searchBar = view.findViewById(R.id.search_bar);
        CardView bookRoomCard = view.findViewById(R.id.quick_access_book_room);
        CardView reserveServiceCard = view.findViewById(R.id.quick_access_reserve_services);
        CardView upcomingBookingCard = view.findViewById(R.id.upcoming_booking_card);
        TextView upcomingBookingTitle = view.findViewById(R.id.upcoming_booking_title);
        TextView upcomingBookingSubtitle = view.findViewById(R.id.upcoming_booking_subtitle);
        ImageView upcomingBookingImage = view.findViewById(R.id.upcoming_booking_image);

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SharedPreferences prefs = requireActivity().getSharedPreferences("LuxeVistaPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", "Guest");

        if (!userEmail.equals("Guest")) {
            Guest guest = dbHelper.getGuestByEmail(userEmail);
            if (guest != null) {
                welcomeTextView.setText("Welcome back, " + guest.getFirstName());

                Booking nextBooking = dbHelper.getUpcomingBooking(guest.getId());
                if (nextBooking != null) {
                    upcomingBookingCard.setVisibility(View.VISIBLE);
                    upcomingBookingTitle.setText(nextBooking.getItemName());
                    upcomingBookingSubtitle.setText(nextBooking.getBookingType() + " - " + nextBooking.getDate());

                    String imageName = nextBooking.getImageUrl();
                    if (imageName != null) {
                        int imageId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
                        if (imageId != 0) {
                            upcomingBookingImage.setImageResource(imageId);
                        }
                    }

                    upcomingBookingCard.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), BookingDetailsActivity.class);
                        intent.putExtra("booking", nextBooking);
                        startActivity(intent);
                    });
                } else {
                    upcomingBookingCard.setVisibility(View.GONE);
                }
            }
        } else {
            welcomeTextView.setText("Welcome to LuxeVista");
            upcomingBookingCard.setVisibility(View.GONE);
        }

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchBar.getText().toString().trim();
                if (!query.isEmpty()) {
                    Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                    intent.putExtra("SEARCH_QUERY", query);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });


        bookRoomCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), RoomListActivity.class)));
        reserveServiceCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), ServiceListActivity.class)));

        return view;
    }
}

