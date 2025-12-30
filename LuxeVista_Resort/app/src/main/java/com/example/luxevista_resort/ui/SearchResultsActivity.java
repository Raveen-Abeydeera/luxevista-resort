package com.example.luxevista_resort.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.adapter.SearchAdapter;
import com.example.luxevista_resort.db.DatabaseHelper;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Search");
        }

        RecyclerView recyclerView = findViewById(R.id.search_results_recycler_view);
        TextView noResultsText = findViewById(R.id.no_search_results_text);
        TextView titleText = findViewById(R.id.search_results_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra("SEARCH_QUERY");
        titleText.setText("Search Results for '" + query + "'");

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Object> searchResults = dbHelper.searchRoomsAndServices(query);

        if (searchResults.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noResultsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            SearchAdapter adapter = new SearchAdapter(this, searchResults);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
