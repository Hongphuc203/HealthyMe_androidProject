package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.imageview.ShapeableImageView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Work_Tracker extends AppCompatActivity {
    private LinearLayout btnBackWorkOutTracker, btnCheckWorkOut, btnViewMoreFullbody, btnViewMoreLowebody, btnViewMoreAB;
    private static final String[] DAYS = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    private boolean isExpanded = false;
    private TextView seeMoreText;
    private LinearLayout workoutContainer;
    private List<WorkoutItem> allWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_tracker);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1700ede5-aea7-4e1b-934d-4405ae39eafa").into((ImageView) findViewById(R.id.rmi23iviyo7k));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d16fc04c-0078-4f0e-bd91-9d7d2c5f35a6").into((ImageView) findViewById(R.id.r5p3awsyp9a4));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/75ca2cb5-c780-4b02-b559-60520b458e7b").into((ImageView) findViewById(R.id.r0f7rk3mkiss));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a4a18f74-672f-4729-bbfa-d78bf2b6951a").into((ImageView) findViewById(R.id.ro4fkwpv1pr9));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bd3aa2ca-be41-4f54-ae99-b128403dcfa6").into((ImageView) findViewById(R.id.r20dtmwwmxcj));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/cf141f68-fbb6-4bef-aa98-0a2d7fcd244a").into((ImageView) findViewById(R.id.rthjoh1co8kr));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fd1b690a-45a2-467d-8bbc-a11b5d461ed1").into((ImageView) findViewById(R.id.rkzamz0jcus));

        btnBackWorkOutTracker = findViewById(R.id.btnBackWorkOutTracker);
        btnBackWorkOutTracker.setOnClickListener(v -> startActivity(new Intent(Work_Tracker.this, home.class)));

        btnCheckWorkOut = findViewById(R.id.btnCheckWorkOut);
        btnCheckWorkOut.setOnClickListener(v -> startActivity(new Intent(Work_Tracker.this, WorkoutSchedule.class)));

        btnViewMoreFullbody = findViewById(R.id.btnViewMoreFullbody);
        btnViewMoreFullbody.setOnClickListener(v -> startActivity(new Intent(Work_Tracker.this, WorkoutDetail1.class)));

        btnViewMoreLowebody = findViewById(R.id.btnViewMoreLowebody);
        btnViewMoreLowebody.setOnClickListener(v -> startActivity(new Intent(Work_Tracker.this, WorkoutDetail1.class)));

        btnViewMoreAB = findViewById(R.id.btnViewMoreAB);
        btnViewMoreAB.setOnClickListener(v -> startActivity(new Intent(Work_Tracker.this, WorkoutDetail1.class)));

        seeMoreText = findViewById(R.id.txtSeeMoreWorkout);
        workoutContainer = findViewById(R.id.workoutContainer);
        allWorkouts = loadWorkoutFromSQLite();
        renderWorkoutList(false);

        seeMoreText.setOnClickListener(v -> {
            isExpanded = !isExpanded;
            renderWorkoutList(isExpanded);
            seeMoreText.setText(isExpanded ? "See less" : "See more");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupChart((LineChart) findViewById(R.id.lineChart));
    }

    private void setupChart(LineChart chart) {
        List<Float> valuesList = getWeeklyCompletionFromDB();
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < valuesList.size(); i++) entries.add(new Entry(i, valuesList.get(i)));

        LineDataSet set = new LineDataSet(entries, "Completion %");
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawCircles(true);
        set.setDrawCircleHole(true);
        set.setCircleColor(Color.WHITE);
        set.setCircleRadius(4f);
        set.setLineWidth(2f);
        set.setColor(Color.WHITE);
        set.setDrawFilled(true);
        set.setFillAlpha(50);
        set.setFillColor(Color.WHITE);
        set.setDrawValues(true);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12f);
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getPointLabel(Entry entry) {
                return ((int) entry.getY()) + "%";
            }
        });

        chart.setData(new LineData(set));

        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);
        x.setValueFormatter(new IndexAxisValueFormatter(DAYS));
        x.setTextColor(Color.WHITE);
        x.setTextSize(14f);
        x.setDrawGridLines(false);

        YAxis left = chart.getAxisLeft();
        left.setEnabled(true);
        left.setDrawGridLines(true);
        left.setGridColor(Color.WHITE);
        left.setGridLineWidth(0.5f);
        left.setAxisMinimum(0f);
        left.setAxisMaximum(100f);
        left.setGranularity(20f);
        left.setLabelCount(6, true);
        left.setTextColor(Color.WHITE);
        left.setTextSize(14f);
        left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return value == 0f ? "" : ((int) value) + "%";
            }
        });

        chart.getAxisRight().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.animateX(800);
        chart.invalidate();
    }

    private List<Float> getWeeklyCompletionFromDB() {
        List<Float> result = new ArrayList<>();
        WorkOutTaskDBHelper dbHelper = new WorkOutTaskDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);

        for (int i = 0; i < 7; i++) {
            String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(calendar.getTime());
            Cursor cursor = db.rawQuery("SELECT SUM(is_done)*100.0/COUNT(*) AS percent_done FROM workout_schedule WHERE date = ?", new String[]{dateStr});
            float percent = 0f;
            if (cursor.moveToFirst() && !cursor.isNull(0)) percent = cursor.getFloat(0);
            result.add(percent);
            cursor.close();
            calendar.add(java.util.Calendar.DAY_OF_WEEK, 1);
        }

        db.close();
        return result;
    }

    private void renderWorkoutList(boolean showAll) {
        workoutContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        int count = showAll ? allWorkouts.size() : Math.min(2, allWorkouts.size());

        for (int i = 0; i < count; i++) {
            WorkoutItem item = allWorkouts.get(i);
            View workoutView = inflater.inflate(R.layout.item_upcoming_workout, workoutContainer, false);
            TextView title = workoutView.findViewById(R.id.textTitle);
            TextView time = workoutView.findViewById(R.id.textTime);
            ImageView icon = workoutView.findViewById(R.id.imgIcon);
            title.setText(item.getTitle());
            time.setText(item.getTime());
            icon.setImageResource(item.getImageResId());
            workoutContainer.addView(workoutView);
        }
    }

    private List<WorkoutItem> loadWorkoutFromSQLite() {
        List<WorkoutItem> list = new ArrayList<>();
        WorkOutTaskDBHelper dbHelper = new WorkOutTaskDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT title, date, time FROM workout_schedule WHERE is_done = 0 ORDER BY date ASC, time ASC", null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String time = cursor.getString(cursor.getColumnIndex("time"));

            String timeStr = date + ", " + time;

            // Ánh xạ tiêu đề sang icon
            int iconResId;
            switch (title.toLowerCase()) {
                case "fullbody workout":
                    iconResId = R.drawable.ic_fullbody;
                    break;
                case "upperbody workout":
                    iconResId = R.drawable.ic_yoga;
                    break;
                case "lowebody workout":
                    iconResId = R.drawable.ic_lowebody;
                    break;
                case "ab workout":
                    iconResId = R.drawable.ic_ab;
                    break;
                default:
                    iconResId = R.mipmap.ic_launcher;
            }

            list.add(new WorkoutItem(title, timeStr, iconResId));
        }

        cursor.close();
        db.close();
        return list;
    }
}
