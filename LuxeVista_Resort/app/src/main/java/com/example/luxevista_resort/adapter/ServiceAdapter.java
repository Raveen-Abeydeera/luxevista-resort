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
import com.example.luxevista_resort.model.Service;
import com.example.luxevista_resort.ui.ServiceDetailsActivity;
import java.util.List;
import java.util.Locale;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private final Context context;
    private final List<Service> serviceList;

    public ServiceAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.serviceName.setText(service.getName());
        holder.serviceDescription.setText(service.getDescription());
        holder.servicePrice.setText(String.format(Locale.US, "$%.2f", service.getPrice()));
        holder.serviceDuration.setText(String.format(Locale.US, "%d min", service.getDuration()));


        String imageName = service.getImageUrl();
        if (imageName != null && !imageName.isEmpty()) {
            int imageId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (imageId != 0) {
                holder.serviceImage.setImageResource(imageId);
            } else {

                holder.serviceImage.setImageResource(R.drawable.spa_background);
            }
        } else {

            holder.serviceImage.setImageResource(R.drawable.spa_background);
        }


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceDetailsActivity.class);
            intent.putExtra("service", service);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImage;
        TextView serviceName, serviceDescription, servicePrice, serviceDuration;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceImage = itemView.findViewById(R.id.service_image);
            serviceName = itemView.findViewById(R.id.service_name);
            serviceDescription = itemView.findViewById(R.id.service_description);
            servicePrice = itemView.findViewById(R.id.service_price);
            serviceDuration = itemView.findViewById(R.id.service_duration);
        }
    }
}

