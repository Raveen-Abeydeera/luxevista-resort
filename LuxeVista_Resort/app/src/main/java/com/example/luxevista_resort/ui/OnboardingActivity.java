package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.OnboardingAdapter;
import com.example.luxevista_resort.model.OnboardingItem;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("LuxeVistaPrefs", MODE_PRIVATE);
        if (prefs.getBoolean("onboarding_complete", false)) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_onboarding);

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.onboarding1, "Welcome to Paradise", "Escape to LuxeVista Resort, where luxury meets tranquility."));

        onboardingViewPager.setAdapter(new OnboardingAdapter(onboardingItems));

        Button getStartedButton = findViewById(R.id.buttonGetStarted);
        getStartedButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("onboarding_complete", true);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }
}

