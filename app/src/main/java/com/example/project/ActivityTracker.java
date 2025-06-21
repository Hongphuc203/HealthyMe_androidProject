package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ActivityTracker extends AppCompatActivity {

    LinearLayout containerLatest;
    FirebaseFirestore db;
    FirebaseAuth auth;
    boolean showAll = false;
    ListenerRegistration activityListener;
    TextView txtSeeMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tracker);

        containerLatest = findViewById(R.id.containerLatestActivities);
        txtSeeMore = findViewById(R.id.txtSeeMore);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        txtSeeMore.setOnClickListener(v -> {
            showAll = !showAll;
            txtSeeMore.setText(showAll ? "See less" : "See more");
            loadActivities(showAll);
        });

        Spinner spinnerFilter = findViewById(R.id.spinnerFilter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: loadWeeklyChartData(); break;
                    case 1: loadMonthlyChartData(); break;
                    case 2: loadYearlyChartData(); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Load chart
        loadWeeklyChartData(); // Gọi hàm để vẽ dữ liệu thực


        // Load danh sách activities trong ngày
        loadActivities(false);

        calculateTodayProgress();


        LinearLayout btnAddActivity = findViewById(R.id.btnAddActivity);
        btnAddActivity.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityTracker.this, AddActivityActivity.class);
            startActivity(intent);
        });

        LinearLayout btnBackActivityTracker = findViewById(R.id.btnBackActivityTracker);
        btnBackActivityTracker.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityTracker.this, home.class);
            startActivity(intent);
        });


        // Load images with Glide
        int[] imageIds = {
                R.id.r9vxk0xpx8bh, R.id.rsvknvh3spf8, R.id.rs953glnnor,
                R.id.r8nibriua228, R.id.ruxljn2fm4zq
        };
        String[] urls = {
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5629039c-4d1b-4556-b92a-8007f0e5a957",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/532f76ab-369f-4bc5-83b2-21a0211afe7b",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9ecf69c3-79ee-4208-8a48-70593f1718c5",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ad0324e4-ea64-4a55-854c-7683f94e6b1c",
                "https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4875b3ce-99d3-433b-a175-93a71514b845"
        };
        for (int i = 0; i < imageIds.length; i++) {
            Glide.with(this).load(urls[i]).into((ImageView) findViewById(imageIds[i]));

        }
    }

    private void loadActivities(boolean showAll) {
        if (activityListener != null) activityListener.remove();

        String userId = auth.getCurrentUser().getUid();
        long start = getStartOfTodayTimestamp();
        long end = getEndOfTodayTimestamp();

        Query query = db.collection("users")
                .document(userId)
                .collection("activities")
                .whereGreaterThanOrEqualTo("timestamp", start)
                .whereLessThan("timestamp", end)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        if (!showAll) query = query.limit(2);

        activityListener = query.addSnapshotListener((snapshots, e) -> {
            if (e != null || snapshots == null) return;

            containerLatest.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);

            for (QueryDocumentSnapshot doc : snapshots) {
                String text = doc.getString("title");
                Long timestampLong = doc.getLong("timestamp");
                Date timestamp = new Date(timestampLong != null ? timestampLong : 0);

                View itemView = inflater.inflate(R.layout.item_activity, containerLatest, false);
                ImageView imgIcon = itemView.findViewById(R.id.imgActivityIcon);
                String type = doc.getString("type"); // đọc type từ Firestore

                if (type != null) {
                    switch (type.toUpperCase()) {
                        case "WALK":
                            imgIcon.setImageResource(R.drawable.ic_walk); break;
                        case "WATER":
                            imgIcon.setImageResource(R.drawable.latest_acti_drink); break;
                        default:
                            imgIcon.setImageResource(R.drawable.ic_default); break;
                    }
                }
                TextView txtTitle = itemView.findViewById(R.id.txtActivityTitle);
                TextView txtTime = itemView.findViewById(R.id.txtActivityTime);

                txtTitle.setText(text);
                txtTime.setText(getTimeAgo(timestamp));

                containerLatest.addView(itemView);
            }

            if (snapshots.isEmpty()) {
                TextView empty = new TextView(this);
                empty.setText("No activity today");
                containerLatest.addView(empty);
            }
        });
    }

    private String getTimeAgo(Date date) {
        long diff = new Date().getTime() - date.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        if (minutes < 1) return "Vừa xong";
        if (minutes < 60) return minutes + " phút trước";
        long hours = minutes / 60;
        if (hours < 24) return hours + " giờ trước";
        return new SimpleDateFormat("HH:mm dd/MM", Locale.getDefault()).format(date);
    }

    private long getStartOfTodayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getEndOfTodayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activityListener != null) activityListener.remove();
    }

    private void loadWeeklyChartData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Calendar calendar = Calendar.getInstance();

        // Ngày đầu tuần (Chủ nhật)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        long startOfWeek = calendar.getTimeInMillis();

        // Ngày cuối tuần (thêm 7 ngày)
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        long endOfWeek = calendar.getTimeInMillis();

        db.collection("users")
                .document(userId)
                .collection("activities")
                .whereGreaterThanOrEqualTo("timestamp", startOfWeek)
                .whereLessThan("timestamp", endOfWeek)
                .orderBy("timestamp")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null || snapshots == null) return;

                    float[] waterArray = new float[7];
                    float[] walkArray = new float[7];

                    for (QueryDocumentSnapshot doc : snapshots) {
                        Long ts = doc.getLong("timestamp");
                        String type = doc.getString("type");
                        Long value = doc.getLong("value");

                        if (ts == null || value == null || type == null) continue;

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(ts);
                        int dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1; // Chủ nhật = 0

                        if ("WATER".equalsIgnoreCase(type)) {
                            waterArray[dayIndex] += value;
                        } else if ("WALK".equalsIgnoreCase(type)) {
                            walkArray[dayIndex] += value;
                        }
                    }

                    List<BarEntry> entries = new ArrayList<>();
                    for (int i = 0; i < 7; i++) {
                        float waterPercent = Math.min(waterArray[i] / 2000f, 1f);
                        float walkPercent = Math.min(walkArray[i] / 8000f, 1f);
                        float progress = (waterPercent + walkPercent) * 50f; // max 100%
                        entries.add(new BarEntry(i, progress));
                    }

                    drawBarChart(entries, new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"});
                });
    }

    private void drawBarChart(List<BarEntry> entries, String[] xLabels) {
        BarChart barChart = findViewById(R.id.barChart);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(12f);
        xAxis.setLabelCount(xLabels.length);

        BarDataSet dataSet = new BarDataSet(entries, "");
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            colors.add(i % 2 == 0 ? Color.parseColor("#00FFB1") : Color.parseColor("#BF72EF"));
        }
        dataSet.setColors(colors);

        BarData barData = new BarData(dataSet);
        barData.setDrawValues(false);
        barData.setBarWidth(0.8f);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setEnabled(true);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMaximum(100f);
        barChart.getAxisLeft().setGranularity(20f); // mỗi mức cách nhau 20%
        barChart.getAxisLeft().setLabelCount(6, true); // hiện 0, 20, 40, 60, 80, 100
        barChart.getAxisLeft().setTextColor(Color.GRAY);
        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.invalidate();
    }

    private void loadMonthlyChartData() {
        String userId = auth.getCurrentUser().getUid();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH); // 0 = Jan

        Map<Integer, Float> weeklyCompletion = new HashMap<>(); // key: week index

        db.collection("users").document(userId).collection("activities")
                .whereGreaterThanOrEqualTo("timestamp", getStartOfMonthTimestamp())
                .whereLessThan("timestamp", getEndOfMonthTimestamp())
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) return;

                    for (DocumentSnapshot doc : snapshots) {
                        Long timestamp = doc.getLong("timestamp");
                        Long water = doc.getLong("water");
                        Long step = doc.getLong("step");
                        if (timestamp == null) continue;

                        cal.setTimeInMillis(timestamp);
                        int week = cal.get(Calendar.WEEK_OF_MONTH);

                        float waterRatio = (water != null ? water : 0) / 2000f;
                        float stepRatio = (step != null ? step : 0) / 8000f;
                        float dayRatio = Math.min(1f, (waterRatio + stepRatio) / 2f);

                        weeklyCompletion.put(week, weeklyCompletion.getOrDefault(week, 0f) + dayRatio);
                    }

                    List<BarEntry> entries = new ArrayList<>();
                    for (int i = 1; i <= 5; i++) {
                        float avg = weeklyCompletion.getOrDefault(i, 0f);
                        entries.add(new BarEntry(i - 1, avg));
                    }

                    drawBarChart(entries, new String[]{"W1", "W2", "W3", "W4", "W5"});
                });
    }

    private void loadYearlyChartData() {
        String userId = auth.getCurrentUser().getUid();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        Map<Integer, Float> monthlyCompletion = new HashMap<>();

        db.collection("users").document(userId).collection("activities")
                .whereGreaterThanOrEqualTo("timestamp", getStartOfYearTimestamp())
                .whereLessThan("timestamp", getEndOfYearTimestamp())
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) return;

                    for (DocumentSnapshot doc : snapshots) {
                        Long timestamp = doc.getLong("timestamp");
                        Long water = doc.getLong("water");
                        Long step = doc.getLong("step");
                        if (timestamp == null) continue;

                        cal.setTimeInMillis(timestamp);
                        int month = cal.get(Calendar.MONTH); // 0 = Jan

                        float waterRatio = (water != null ? water : 0) / 2000f;
                        float stepRatio = (step != null ? step : 0) / 8000f;
                        float dayRatio = Math.min(1f, (waterRatio + stepRatio) / 2f);

                        monthlyCompletion.put(month, monthlyCompletion.getOrDefault(month, 0f) + dayRatio);
                    }

                    List<BarEntry> entries = new ArrayList<>();
                    for (int i = 0; i < 12; i++) {
                        float avg = monthlyCompletion.getOrDefault(i, 0f);
                        entries.add(new BarEntry(i, avg));
                    }

                    drawBarChart(entries, new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
                });
    }

    private long getStartOfMonthTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getEndOfMonthTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    private long getStartOfYearTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getEndOfYearTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    private void calculateTodayProgress() {
        String userId = auth.getCurrentUser().getUid();
        long start = getStartOfTodayTimestamp();
        long end = getEndOfTodayTimestamp();

        db.collection("users").document(userId).collection("activities")
                .whereGreaterThanOrEqualTo("timestamp", start)
                .whereLessThan("timestamp", end)
                .addSnapshotListener((snapshots, error) -> {
                    if (snapshots == null) return;

                    long totalWater = 0, totalSteps = 0;
                    for (DocumentSnapshot doc : snapshots) {
                        String type = doc.getString("type");
                        Long value = doc.getLong("value");
                        if (type == null || value == null) continue;

                        if ("WATER".equalsIgnoreCase(type)) totalWater += value;
                        else if ("WALK".equalsIgnoreCase(type)) totalSteps += value;
                    }

                    updateProgressUI(totalWater, totalSteps);
                });
    }

    private void updateProgressUI(long water, long steps) {
        TextView txtTargetMessage = findViewById(R.id.txtTargetMessage);
        ProgressBar pbWater = findViewById(R.id.progressBarWater);
        ProgressBar pbSteps = findViewById(R.id.progressBarSteps);

        pbWater.setProgress((int) Math.min(water * 100 / 2000, 100));
        pbSteps.setProgress((int) Math.min(steps * 100 / 8000, 100));

        if (water >= 2000 && steps >= 8000) {
            txtTargetMessage.setText("🎉 Chúc mừng! Bạn đã hoàn thành target hôm nay!");
        } else {
            String msg = " Còn thiếu: ";
            if (water < 2000) msg += (2000 - water) + "ml nước ";
            if (steps < 8000) msg += "và " + (8000 - steps) + " bước";
            txtTargetMessage.setText(msg);
        }
    }



}
