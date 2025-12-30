package com.example.luxevista_resort.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.luxevista_resort.R;
import com.example.luxevista_resort.ui.ServiceListActivity;

public class ServicesFragment extends Fragment {

    public ServicesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Corrected CardView IDs to match fragment_services.xml
        CardView spaCard = view.findViewById(R.id.service_spa_card);
        CardView diningCard = view.findViewById(R.id.service_dining_card);
        CardView cabanasCard = view.findViewById(R.id.service_cabanas_card);
        CardView toursCard = view.findViewById(R.id.service_tours_card);

        spaCard.setOnClickListener(v -> openServiceList("Spa"));
        diningCard.setOnClickListener(v -> openServiceList("Dining"));
        cabanasCard.setOnClickListener(v -> openServiceList("Cabanas"));
        toursCard.setOnClickListener(v -> openServiceList("Tours"));
    }

    private void openServiceList(String category) {
        Intent intent = new Intent(getActivity(), ServiceListActivity.class);

        intent.putExtra("category", category);
        startActivity(intent);
    }
}
