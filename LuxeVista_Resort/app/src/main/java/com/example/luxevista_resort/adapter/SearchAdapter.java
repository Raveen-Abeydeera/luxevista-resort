package com.example.luxevista_resort.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luxevista_resort.R;
import com.example.luxevista_resort.model.Room;
import com.example.luxevista_resort.model.Service;
import com.example.luxevista_resort.ui.RoomDetailsActivity;
import com.example.luxevista_resort.ui.ServiceDetailsActivity;

import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Object> items;

    private static final int TYPE_ROOM = 0;
    private static final int TYPE_SERVICE = 1;

    public SearchAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Room) {
            return TYPE_ROOM;
        } else {
            return TYPE_SERVICE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ROOM) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_room, parent, false);
            return new RoomViewHolder(view);
        } else { // TYPE_SERVICE
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false);
            return new ServiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ROOM) {
            RoomViewHolder roomHolder = (RoomViewHolder) holder;
            roomHolder.bind((Room) items.get(position));
        } else {
            ServiceViewHolder serviceHolder = (ServiceViewHolder) holder;
            serviceHolder.bind((Service) items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder for Room items
    class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView roomTitle, roomPrice, roomDescription;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);
            roomTitle = itemView.findViewById(R.id.room_title);
            roomPrice = itemView.findViewById(R.id.room_price);
            roomDescription = itemView.findViewById(R.id.room_description);
        }

        void bind(Room room) {
            roomTitle.setText(room.getRoomType());
            roomPrice.setText(String.format(Locale.getDefault(), "$%.2f/night", room.getPricePerNight()));
            roomDescription.setText(room.getDescription());

            int imageId = context.getResources().getIdentifier(room.getImageUrl(), "drawable", context.getPackageName());
            if (imageId != 0) {
                roomImage.setImageResource(imageId);
            } else {
                roomImage.setImageResource(R.drawable.oceanfront_grand_suite);
            }

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, RoomDetailsActivity.class);
                intent.putExtra("room", room);
                context.startActivity(intent);
            });
        }
    }

    // ViewHolder for Service items
    class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceImage;
        TextView serviceName, serviceDescription, servicePrice, serviceDuration;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.service_image);
            serviceName = itemView.findViewById(R.id.service_name);
            serviceDescription = itemView.findViewById(R.id.service_description);
            servicePrice = itemView.findViewById(R.id.service_price);
            serviceDuration = itemView.findViewById(R.id.service_duration);
        }

        void bind(Service service) {
            serviceName.setText(service.getName());
            serviceDescription.setText(service.getDescription());
            servicePrice.setText(String.format(Locale.US, "$%.0f", service.getPrice()));
            serviceDuration.setText(String.format(Locale.US, "%d min", service.getDuration()));

            int imageId = context.getResources().getIdentifier(service.getImageUrl(), "drawable", context.getPackageName());
            if (imageId != 0) {
                serviceImage.setImageResource(imageId);
            } else {
                serviceImage.setImageResource(R.drawable.spa_background);
            }

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ServiceDetailsActivity.class);
                intent.putExtra("service", service);
                context.startActivity(intent);
            });
        }
    }
}

