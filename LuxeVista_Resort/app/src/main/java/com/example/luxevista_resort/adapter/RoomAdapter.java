package com.example.luxevista_resort.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.model.Room;
import com.example.luxevista_resort.ui.RoomDetailsActivity;
import java.util.List;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private final List<Room> roomList;
    private final Context context;
    private String checkInDate;
    private String checkOutDate;

    public RoomAdapter(Context context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    public void setBookingDates(String checkIn, String checkOut) {
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomTitle.setText(room.getRoomType());
        holder.roomPrice.setText(String.format(Locale.getDefault(), "$%.2f/night", room.getPricePerNight()));
        holder.roomDescription.setText(String.format(Locale.getDefault(), "Sleeps %d adults, %d children", room.getMaxAdults(), room.getMaxChildren()));

        String imageName = room.getImageUrl();
        int imageId = holder.itemView.getContext().getResources()
                .getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

        if (imageId != 0) {
            holder.roomImage.setImageResource(imageId);
        } else {
            holder.roomImage.setImageResource(R.drawable.oceanfront_grand_suite); // Default image
        }

        holder.itemView.setOnClickListener(v -> {
            if (checkInDate == null || checkOutDate == null) {
                Toast.makeText(context, "Please select a date range first.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(context, RoomDetailsActivity.class);
                intent.putExtra("room", room);
                intent.putExtra("checkInDate", checkInDate);
                intent.putExtra("checkOutDate", checkOutDate);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void updateRooms(List<Room> newRoomList) {
        roomList.clear();
        roomList.addAll(newRoomList);
        notifyDataSetChanged();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView roomTitle, roomPrice, roomDescription;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);
            roomTitle = itemView.findViewById(R.id.room_title);
            roomPrice = itemView.findViewById(R.id.room_price);
            roomDescription = itemView.findViewById(R.id.room_description);
        }
    }
}

