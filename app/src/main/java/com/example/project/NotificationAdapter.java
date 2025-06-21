package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationItem> notifications;

    public NotificationAdapter(List<NotificationItem> notifications) {
        this.notifications = notifications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtActivityTitle, txtActivityContent, txtActivityTime;
        ImageView imgActivityIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            txtActivityTitle = itemView.findViewById(R.id.txtActivityTitle);
            txtActivityTime = itemView.findViewById(R.id.txtActivityTime);
            txtActivityContent = itemView.findViewById(R.id.txtActivityContent);
            imgActivityIcon = itemView.findViewById(R.id.imgActivityIcon);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationItem item = notifications.get(position);
        holder.txtActivityTitle.setText(item.getTitle());

        holder.txtActivityContent.setText(item.getContent());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM", Locale.getDefault());
        String formattedTime = sdf.format(new Date(item.getTimestamp()));
        holder.txtActivityTime.setText(formattedTime);

        String title = item.getTitle().toLowerCase();

        if (title.contains("sáng") || title.contains("ăn sáng")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_breakfast);
        } else if (title.contains("nước")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_water);
        } else if (title.contains("tập luyện") || title.contains("vận động")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_exercise);
        } else if (title.contains("ăn tối")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_dinner);
        } else if (title.contains("ăn trưa")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_lunch);
        } else if (title.contains("ngủ")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_sleep);
        } else if (title.contains("giãn cơ")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_stretch);
        } else if (title.contains("dậy")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_wake_up);
        } else if (title.contains("đi bộ")) {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_walk);
        } else {
            holder.imgActivityIcon.setImageResource(R.drawable.ic_notification_default);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
