package com.example.luxevista_resort.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.ServiceAdapter;
import com.example.luxevista_resort.db.DatabaseHelper;
import com.example.luxevista_resort.model.Service;

import java.util.List;

public class ServiceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        RecyclerView serviceRecyclerView = findViewById(R.id.recycler_view_services);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String category = getIntent().getStringExtra("category");
        List<Service> serviceList;

        if (category != null && !category.isEmpty()) {
            serviceList = dbHelper.getServicesByCategory(category);
        } else {
            serviceList = dbHelper.getAllServices();
        }

        ServiceAdapter serviceAdapter = new ServiceAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(serviceAdapter);
    }
}

