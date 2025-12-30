package com.example.luxevista_resort.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.ui.HelpSupportActivity;
import com.example.luxevista_resort.ui.LoginActivity;
import com.example.luxevista_resort.ui.PaymentActivity;
import com.example.luxevista_resort.ui.PrivacyPolicyActivity;
import com.example.luxevista_resort.ui.SettingsActivity;
import com.example.luxevista_resort.ui.TermsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private Guest currentGuest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DatabaseHelper(getContext());

        TextView guestNameTextView = view.findViewById(R.id.profile_guest_name);
        TextView guestEmailTextView = view.findViewById(R.id.profile_email);
        TextView guestPhoneTextView = view.findViewById(R.id.profile_phone);
        TextView guestAddressTextView = view.findViewById(R.id.profile_address);

        SharedPreferences prefs = requireActivity().getSharedPreferences("LuxeVistaPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);
        if (userEmail != null) {
            currentGuest = dbHelper.getGuestByEmail(userEmail);
            if (currentGuest != null) {
                guestNameTextView.setText(currentGuest.getFirstName() + " " + currentGuest.getLastName());
                guestEmailTextView.setText(currentGuest.getEmail());
                guestPhoneTextView.setText(currentGuest.getPhone());
                guestAddressTextView.setText(currentGuest.getAddress());
            }
        }

        view.findViewById(R.id.profile_upcoming_bookings).setOnClickListener(v -> navigateToBookings());
        view.findViewById(R.id.profile_past_bookings).setOnClickListener(v -> navigateToBookings());
        view.findViewById(R.id.profile_payment_method).setOnClickListener(v -> startActivity(new Intent(getActivity(), PaymentActivity.class)));
        view.findViewById(R.id.profile_help_support).setOnClickListener(v -> startActivity(new Intent(getActivity(), HelpSupportActivity.class)));
        view.findViewById(R.id.profile_terms).setOnClickListener(v -> startActivity(new Intent(getActivity(), TermsActivity.class)));
        view.findViewById(R.id.profile_privacy).setOnClickListener(v -> startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class)));

        view.findViewById(R.id.logout_button).setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void navigateToBookings() {
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_bookings);
        }
    }
}

