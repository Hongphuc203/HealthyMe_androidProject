package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WorkoutSchedule extends AppCompatActivity {
    private View dimView;
    private LinearLayout openBox, mainContent;
    private EditText job;
    private RecyclerView rvDays;
    private DayAdapter adapter;

    private final int[] currentYearMonth = {
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1 // Tháng bắt đầu từ 0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_schedule);

        dimView     = findViewById(R.id.dimView);
        openBox     = findViewById(R.id.OpenBox);
        mainContent = findViewById(R.id.main);
        job         = findViewById(R.id.Task);

        dimView.setVisibility(View.GONE);
        openBox.setVisibility(View.GONE);

        job.setOnClickListener(v -> showOverlay());
        findViewById(R.id.ButtonDone).setOnClickListener(v -> hideOverlay());
        findViewById(R.id.CloseButton).setOnClickListener(v -> hideOverlay());
        // Load ảnh
        ImageView img1 = findViewById(R.id.r2jkkf70a1av);
        ImageView img2 = findViewById(R.id.rgexp1sssio);
        ImageView img3 = findViewById(R.id.PreviousMonth);
        ImageView img4 = findViewById(R.id.NextMonth);
        ImageView img6 = findViewById(R.id.rmlhoqy5qx6o);
        Glide.with(this).load("https://...").into((ImageView) findViewById(R.id.r2jkkf70a1av));
        ImageView CloseButton = findViewById(R.id.CloseButton);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0076bbf7-f5e7-43d1-9f88-3b8715b9f5a0")
                .into(CloseButton);

        ImageView image2 = findViewById(R.id.r6ikq5xj2yo7);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3b0f53cd-ab06-4536-90a7-cdf894898bc9")
                .into(image2);

        ImageView image3 = findViewById(R.id.rquci630nf9);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e403aafc-5317-4029-8832-2914a30e4868")
                .into(image3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/83ad3848-6fa0-472b-8460-2e303af8bb40").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a939bd76-db95-4c69-b547-2e1ad84f83e0").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/883216c6-73df-499e-894f-fa662f10a18a").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9499edbd-d39c-4326-9637-a9cf20ae3a1c").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0ffa9055-e6cb-4d03-a58f-453d8b5433c1").into(img6);

        // ... các ảnh khác tương tự

        // RecyclerView ngày
        rvDays = findViewById(R.id.rvDays);
        rvDays.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new DayAdapter(generateDaysForMonth(currentYearMonth[0], currentYearMonth[1]), date -> {
            // xử lý khi chọn ngày
        });
        rvDays.setAdapter(adapter);

        // Prev / Next
        findViewById(R.id.PreviousMonth).setOnClickListener(v -> {
            if (currentYearMonth[1] == 1) {
                currentYearMonth[0]--;
                currentYearMonth[1] = 12;
            } else currentYearMonth[1]--;
            reloadMonth();
        });

        findViewById(R.id.NextMonth).setOnClickListener(v -> {
            if (currentYearMonth[1] == 12) {
                currentYearMonth[0]++;
                currentYearMonth[1] = 1;
            } else currentYearMonth[1]++;
            reloadMonth();
        });

        reloadMonth();
    }

    private void showOverlay() {
        dimView.setVisibility(View.VISIBLE);
        openBox.setVisibility(View.VISIBLE);
        mainContent.setEnabled(false);
    }

    private void hideOverlay() {
        dimView.setVisibility(View.GONE);
        openBox.setVisibility(View.GONE);
        mainContent.setEnabled(true);
    }

    private List<Calendar> generateDaysForMonth(int year, int month) {
        List<Calendar> days = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= maxDay; day++) {
            Calendar c = Calendar.getInstance();
            c.set(year, month - 1, day);
            days.add(c);
        }
        return days;
    }

    private void reloadMonth() {
        TextView tvMonth = findViewById(R.id.MonthChoosen);
        Calendar temp = Calendar.getInstance();
        temp.set(currentYearMonth[0], currentYearMonth[1] - 1, 1);

        String monthName = temp.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String header = monthName + " " + currentYearMonth[0];
        tvMonth.setText(header);

        List<Calendar> days = generateDaysForMonth(currentYearMonth[0], currentYearMonth[1]);
        adapter.setDays(days);
    }
}