package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.VH> {
    private List<Calendar> days = new ArrayList<>();
    private String selectedDate = "";

    public DayAdapter(List<Calendar> days, OnDayClickListener listener) {
        this.days = days;
        this.listener = listener;
    }

    private final Calendar today = Calendar.getInstance();
    private OnDayClickListener listener;

    interface OnDayClickListener {
        void onDayClick(Calendar date);
    }

    void setDays(List<Calendar> list) {
        days = list;
        notifyDataSetChanged();
    }

    void setListener(OnDayClickListener l) {
        listener = l;
    }

    public void setSelectedDate(String date) {
        selectedDate = date;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Calendar d = days.get(pos);

        // Thứ viết tắt (Mon, Tue, ...)
        String dayName = d.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        h.tvName.setText(dayName);

        // Ngày trong tháng
        h.tvNumber.setText(String.valueOf(d.get(Calendar.DAY_OF_MONTH)));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String thisDate = sdf.format(d.getTime());

        boolean isSelected = thisDate.equals(selectedDate);
        boolean isToday = d.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                d.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                d.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);

        if (isSelected) {
            h.container.setBackgroundResource(R.drawable.cr10_selected_day);
        } else if (isToday) {
            h.container.setBackgroundResource(R.drawable.cr10lr495c050f6eea4ce);
        } else {
            h.container.setBackgroundResource(R.drawable.cr10bf7f8f8);
        }

        int textColor = ContextCompat.getColor(h.itemView.getContext(),
                isSelected || isToday ? R.color.white : R.color.gray_dark);
        h.tvName.setTextColor(textColor);
        h.tvNumber.setTextColor(textColor);

        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDayClick(d);
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView tvName, tvNumber;

        VH(View v) {
            super(v);
            container = v.findViewById(R.id.itemDayContainer);
            tvName = v.findViewById(R.id.tvDayName);
            tvNumber = v.findViewById(R.id.tvDayNumber);
        }
    }
}