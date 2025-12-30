package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.model.Room;

import java.util.Locale;

public class RoomDetailsActivity extends AppCompatActivity {

    private Room room;
    private String checkInDate;
    private String checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Room Details");
        }

        room = (Room) getIntent().getSerializableExtra("room");
        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");


        ImageView roomImageView = findViewById(R.id.room_detail_image);
        TextView roomNameTextView = findViewById(R.id.room_detail_name);
        TextView roomDescriptionTextView = findViewById(R.id.room_detail_description);
        TextView roomPriceTextView = findViewById(R.id.room_detail_price);
        Button bookButton = findViewById(R.id.book_this_room_button);

        if (room != null) {
            int imageResId = getResources().getIdentifier(room.getImageUrl(), "drawable", getPackageName());
            if (imageResId != 0) {
                roomImageView.setImageResource(imageResId);
            } else {
                roomImageView.setImageResource(R.drawable.onboarding1); // Default image
            }

            roomNameTextView.setText(room.getRoomType());
            roomDescriptionTextView.setText(room.getDescription());
            roomPriceTextView.setText(String.format(Locale.getDefault(), "$%.0f/night", room.getPricePerNight()));
        }

        bookButton.setOnClickListener(v -> {
            if (checkInDate == null || checkOutDate == null) {
                Toast.makeText(this, "Please select dates in the previous screen first.", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(RoomDetailsActivity.this, BookingSummaryActivity.class);
            intent.putExtra("room", room);
            intent.putExtra("checkInDate", checkInDate);
            intent.putExtra("checkOutDate", checkOutDate);
            startActivity(intent);
        });
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

