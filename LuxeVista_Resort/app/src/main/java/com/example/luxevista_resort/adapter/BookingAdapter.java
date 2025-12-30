package com.example.luxevista_resort.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luxevista_resort.R;
import com.example.luxevista_resort.model.Booking;
import com.example.luxevista_resort.ui.BookingDetailsActivity;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    protected final List<Booking> bookingList;
    protected final Context context;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.itemName.setText(booking.getItemName());
        holder.bookingType.setText(booking.getBookingType() + " on " + booking.getDate());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingDetailsActivity.class);
            intent.putExtra("booking", booking);
            // We'll handle the result in the Fragment/Activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void updateData(List<Booking> newBookingList) {
        this.bookingList.clear();
        this.bookingList.addAll(newBookingList);
        notifyDataSetChanged();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName, bookingType;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.booking_item_title);
            bookingType = itemView.findViewById(R.id.booking_item_subtitle);
        }
    }
}

