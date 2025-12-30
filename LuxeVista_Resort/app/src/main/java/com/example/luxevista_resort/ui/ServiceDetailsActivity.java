package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.luxevista_resort.R;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Guest;
import com.example.luxevista_resort.model.Service;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ServiceDetailsActivity extends AppCompatActivity {

    private Service service;
    private DatabaseHelper dbHelper;
    private String selectedDuration = "";
    private String selectedSpecialist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        dbHelper = new DatabaseHelper(this);
        service = (Service) getIntent().getSerializableExtra("service");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (service != null) {
                getSupportActionBar().setTitle(service.getName());
            }
        }

        ImageView serviceImageView = findViewById(R.id.service_detail_image);
        TextView serviceNameTextView = findViewById(R.id.service_detail_name);
        TextView serviceDescriptionTextView = findViewById(R.id.service_detail_description);
        Button reserveButton = findViewById(R.id.reserve_service_button);

        // Sections to hide/show
        LinearLayout durationSection = findViewById(R.id.duration_section);
        LinearLayout specialistSection = findViewById(R.id.specialist_section);
        TextView fixedDurationText = findViewById(R.id.fixed_duration_text);

        ChipGroup durationGroup = findViewById(R.id.duration_chip_group);
        ChipGroup specialistGroup = findViewById(R.id.specialist_chip_group);

        if (service != null) {
            serviceNameTextView.setText(service.getName());
            serviceDescriptionTextView.setText(service.getDescription());

            String imageName = service.getImageUrl();
            if (imageName != null && !imageName.isEmpty()) {
                int imageId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imageId != 0) {
                    serviceImageView.setImageResource(imageId);
                } else {
                    serviceImageView.setImageResource(R.drawable.spa_background);
                }
            } else {
                serviceImageView.setImageResource(R.drawable.spa_background);
            }

            if ("Spa".equalsIgnoreCase(service.getCategory())) {
                durationSection.setVisibility(View.VISIBLE);
                specialistSection.setVisibility(View.VISIBLE);
                fixedDurationText.setVisibility(View.GONE);

                if (durationGroup.getChildCount() > 0) {
                    Chip firstDurationChip = (Chip) durationGroup.getChildAt(0);
                    firstDurationChip.setChecked(true);
                    selectedDuration = firstDurationChip.getText().toString();
                }

                if (specialistGroup.getChildCount() > 0) {
                    Chip firstSpecialistChip = (Chip) specialistGroup.getChildAt(0);
                    firstSpecialistChip.setChecked(true);
                    selectedSpecialist = firstSpecialistChip.getText().toString();
                }
            } else {
                durationSection.setVisibility(View.GONE);
                specialistSection.setVisibility(View.GONE);
                fixedDurationText.setVisibility(View.VISIBLE);
                fixedDurationText.setText(String.format(Locale.US, "Duration: %d minutes", service.getDuration()));
            }
        }

        durationGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                selectedDuration = chip.getText().toString();
            }
        });

        specialistGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = findViewById(checkedId);
            if (chip != null) {
                selectedSpecialist = chip.getText().toString();
            }
        });

        reserveButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String dateStr = sdf.format(selectedCal.getTime());
            bookService(dateStr);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void bookService(String dateStr) {
        SharedPreferences prefs = getSharedPreferences("LuxeVistaPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", null);

        if (userEmail != null && service != null) {
            Guest guest = dbHelper.getGuestByEmail(userEmail);
            if (guest != null) {
                String details = "Duration: " + selectedDuration + ", Specialist: " + selectedSpecialist;
                // Pass null for check-out date for services
                long bookingId = dbHelper.addBooking(guest.getId(), "Service", service.getId(), dateStr, null, details);

                if (bookingId != -1) {
                    Intent intent = new Intent(this, BookingConfirmationActivity.class);
                    intent.putExtra("bookingId", bookingId);
                    intent.putExtra("itemName", service.getName());
                    intent.putExtra("bookingDate", dateStr);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Booking failed.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "You must be logged in to book.", Toast.LENGTH_SHORT).show();
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

