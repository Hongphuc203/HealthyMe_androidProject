package com.example.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.TimePickerDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * SleepTracker Activity: hiển thị biểu đồ 7 ngày, giờ ngủ đêm trước,
 * và cho phép chọn Bedtime / Alarm.
 */
public class SleepTracker extends AppCompatActivity {

    // KHỞI TẠO LABELS NGAY KHI KHAI BÁO: tránh NPE
    public static java.util.List<String> LABELS = new java.util.ArrayList<>();

    private LineChart chartSleepTracker;
    private TextView tvLastNightDuration;
    private TextView tvBedtimeLabel;
    private TextView tvBedtimeCountdown;
    private TextView tvAlarmLabel;
    private TextView tvAlarmCountdown;
    private SwitchMaterial switchBedtime;
    private SwitchMaterial switchAlarm;

    private SharedPreferences prefs;
    private SleepRepository repository;
    private LinearLayout btnBackSleepTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        // 1. Khởi tạo SharedPreferences và repository
        prefs = getSharedPreferences("SleepPrefs", MODE_PRIVATE);
        repository = new SleepRepository(this);

        // 2. Bind ImageViews và load ảnh qua Glide (không đổi)
        ImageView imgBack        = findViewById(R.id.rce5x3iprbul);
        ImageView imgMore        = findViewById(R.id.rnyrczzis2m);
        ImageView imgLastCard    = findViewById(R.id.rk08uxx6kojg);
        ImageView imgIconBed     = findViewById(R.id.rsumk43d3fe);
        ImageView imgToggleBed   = findViewById(R.id.rz2aquca7tfj);
        ImageView imgIconAlarm   = findViewById(R.id.r83wjkwg0gqa);
        ImageView imgToggleAlarm = findViewById(R.id.re10l7j9kboj);

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b1daf17e-5496-4e30-8b63-e70e42fecdbe")
                .into(imgBack);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4a0f9639-1221-4cc2-9b9e-9bd19c7b1963")
                .into(imgMore);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e07ce4f1-56e0-43f0-8513-cf0278646cb2")
                .into(imgLastCard);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a9cec662-5b7e-4f8f-98f3-d48134aa215c")
                .into(imgIconBed);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/749ed497-be8c-46ee-b6fd-8880021c62b6")
                .into(imgToggleBed);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fd167c7d-981c-464f-ab8f-f336acc64b4a")
                .into(imgIconAlarm);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1b414cc4-2f96-43e8-aa37-53ac8939fa03")
                .into(imgToggleAlarm);

        //Button quay lại home
        btnBackSleepTracker = findViewById(R.id.btnBackSleepTracker);
        btnBackSleepTracker.setOnClickListener(v -> {
            Intent intent = new Intent(SleepTracker.this, home.class);
            startActivity(intent);
        });

        // 3. Bind chart và TextView
        chartSleepTracker   = findViewById(R.id.lineChart);
        tvLastNightDuration = findViewById(R.id.HoursOfSleepLastNight);

        tvBedtimeLabel      = findViewById(R.id.TimeToSleep);
        tvBedtimeCountdown  = findViewById(R.id.TimeRemainingToSleep);
        switchBedtime       = findViewById(R.id.switchBedtime);

        tvAlarmLabel        = findViewById(R.id.TimeToGetUp);
        tvAlarmCountdown    = findViewById(R.id.TimeRemainingGetUp);
        switchAlarm         = findViewById(R.id.switchAlarm);

        // 4. Khôi phục trạng thái Bedtime từ SharedPreferences
        int bHour = prefs.getInt("bedtime_hour", -1);
        int bMin  = prefs.getInt("bedtime_min", -1);
        boolean bedtimeEnabled = prefs.getBoolean("bedtime_enabled", false);

        if (bedtimeEnabled && bHour >= 0 && bMin >= 0) {
            tvBedtimeLabel.setText(formatTimeLabel("Bedtime", bHour, bMin));
            tvBedtimeCountdown.setText("in " + calculateCountdownTo(bHour, bMin));
            switchBedtime.setChecked(true);
        } else {
            tvBedtimeLabel.setText("Bedtime, –");
            tvBedtimeCountdown.setText("");
            switchBedtime.setChecked(false);
        }

        // 5. Khôi phục trạng thái Alarm (Wake-up)
        int wHour = prefs.getInt("wake_hour", -1);
        int wMin  = prefs.getInt("wake_min", -1);
        boolean wakeEnabled = prefs.getBoolean("wake_enabled", false);

        if (wakeEnabled && wHour >= 0 && wMin >= 0) {
            tvAlarmLabel.setText(formatTimeLabel("Alarm", wHour, wMin));
            tvAlarmCountdown.setText("in " + calculateCountdownTo(wHour, wMin));
            switchAlarm.setChecked(true);
        } else {
            tvAlarmLabel.setText("Alarm, –");
            tvAlarmCountdown.setText("");
            switchAlarm.setChecked(false);
        }

        // 6. Thiết lập listener cho Bedtime switch
        switchBedtime.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                openTimePicker("BEDTIME");
            } else {
                cancelAlarm("BEDTIME");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("bedtime_enabled", false);
                editor.remove("bedtime_hour");
                editor.remove("bedtime_min");
                editor.remove("sleep_start_ts");
                editor.apply();

                tvBedtimeLabel.setText("Bedtime, –");
                tvBedtimeCountdown.setText("");
            }
        });

        // 7. Thiết lập listener cho Alarm (Wake-up) switch
        switchAlarm.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                openTimePicker("WAKEUP");
            } else {
                cancelAlarm("WAKEUP");
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("wake_enabled", false);
                editor.remove("wake_hour");
                editor.remove("wake_min");
                editor.apply();

                tvAlarmLabel.setText("Alarm, –");
                tvAlarmCountdown.setText("");
            }
        });

        // 8. Nếu Intent chứa extra "REFRESH_FROM_WAKEUP", cập nhật UI ngay
        Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.getBooleanExtra("REFRESH_FROM_WAKEUP", false)) {
            loadLastNightSleep();
            drawSleepChart();
        } else {
            // Mặc định, hiển thị dữ liệu hiện có khi mở lần đầu
            loadLastNightSleep();
            drawSleepChart();
        }
    }

    @Override
    protected void onNewIntent(@Nullable Intent intent) {
        super.onNewIntent(intent);
        // Khi Activity nhận thêm Intent mới (clearTop), kiểm tra extra
        if (intent != null && intent.getBooleanExtra("REFRESH_FROM_WAKEUP", false)) {
            loadLastNightSleep();
            drawSleepChart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCountdowns();
        loadLastNightSleep();
        drawSleepChart();
    }

    // Mở TimePickerDialog để chọn giờ
    private void openTimePicker(String type) {
        Calendar now = Calendar.getInstance();
        int hour   = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        new TimePickerDialog(
                this,
                (TimePicker view, int hourOfDay, int minuteOfHour) -> {
                    if (type.equals("BEDTIME")) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("bedtime_hour", hourOfDay);
                        editor.putInt("bedtime_min", minuteOfHour);
                        editor.putBoolean("bedtime_enabled", true);
                        editor.apply();

                        tvBedtimeLabel.setText(formatTimeLabel("Bedtime", hourOfDay, minuteOfHour));
                        tvBedtimeCountdown.setText("in " + calculateCountdownTo(hourOfDay, minuteOfHour));

                        scheduleAlarm("BEDTIME", hourOfDay, minuteOfHour);
                    } else if (type.equals("WAKEUP")) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("wake_hour", hourOfDay);
                        editor.putInt("wake_min", minuteOfHour);
                        editor.putBoolean("wake_enabled", true);
                        editor.apply();

                        tvAlarmLabel.setText(formatTimeLabel("Alarm", hourOfDay, minuteOfHour));
                        tvAlarmCountdown.setText("in " + calculateCountdownTo(hourOfDay, minuteOfHour));

                        scheduleAlarm("WAKEUP", hourOfDay, minuteOfHour);
                    }
                },
                hour, minute, false
        ).show();
    }

    // Đặt AlarmManager (cả Bedtime và Wake-up đều dùng setAlarmClock để kêu đúng ±0 phút)
    private void scheduleAlarm(String type, int hourOfDay, int minuteOfHour) {
        Calendar now     = Calendar.getInstance();
        Calendar trigger = Calendar.getInstance();
        trigger.set(Calendar.HOUR_OF_DAY, hourOfDay);
        trigger.set(Calendar.MINUTE, minuteOfHour);
        trigger.set(Calendar.SECOND, 0);
        trigger.set(Calendar.MILLISECOND, 0);

        if (trigger.before(now)) {
            trigger.add(Calendar.DATE, 1);
        }

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        if (type.equals("BEDTIME")) {
            intent.setAction("com.example.project.ALARM_BEDTIME");
        } else { // "WAKEUP"
            intent.setAction("com.example.project.ALARM_WAKEUP");
        }
        intent.putExtra("TYPE", type);

        PendingIntent pi = PendingIntent.getBroadcast(
                this,
                type.equals("BEDTIME") ? 100 : 200,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // setAlarmClock kêu đúng ±0 phút, không cần permission exact
            AlarmManager.AlarmClockInfo info =
                    new AlarmManager.AlarmClockInfo(trigger.getTimeInMillis(), pi);
            am.setAlarmClock(info, pi);
        } else {
            // Trên Android < 21: fallback dùng setExact / setExactAndAllowWhileIdle
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        trigger.getTimeInMillis(),
                        pi
                );
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(
                        AlarmManager.RTC_WAKEUP,
                        trigger.getTimeInMillis(),
                        pi
                );
            } else {
                am.set(
                        AlarmManager.RTC_WAKEUP,
                        trigger.getTimeInMillis(),
                        pi
                );
            }
        }
    }

    // Hủy Alarm
    private void cancelAlarm(String type) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        if (type.equals("BEDTIME")) {
            intent.setAction("com.example.project.ALARM_BEDTIME");
        } else { // "WAKEUP"
            intent.setAction("com.example.project.ALARM_WAKEUP");
        }

        PendingIntent pi = PendingIntent.getBroadcast(
                this,
                type.equals("BEDTIME") ? 100 : 200,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        am.cancel(pi);
    }

    // Cập nhật countdown nếu switch vẫn bật
    private void updateCountdowns() {
        boolean bedtimeEnabled = prefs.getBoolean("bedtime_enabled", false);
        if (bedtimeEnabled) {
            int bHour = prefs.getInt("bedtime_hour", -1);
            int bMin  = prefs.getInt("bedtime_min", -1);
            if (bHour >= 0 && bMin >= 0) {
                tvBedtimeCountdown.setText("in " + calculateCountdownTo(bHour, bMin));
            }
        }

        boolean wakeEnabled = prefs.getBoolean("wake_enabled", false);
        if (wakeEnabled) {
            int wHour = prefs.getInt("wake_hour", -1);
            int wMin  = prefs.getInt("wake_min", -1);
            if (wHour >= 0 && wMin >= 0) {
                tvAlarmCountdown.setText("in " + calculateCountdownTo(wHour, wMin));
            }
        }
    }

    // Tính “in X hours Y minutes”
    private String calculateCountdownTo(int hourOfDay, int minuteOfHour) {
        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.set(Calendar.HOUR_OF_DAY, hourOfDay);
        target.set(Calendar.MINUTE, minuteOfHour);
        target.set(Calendar.SECOND, 0);
        target.set(Calendar.MILLISECOND, 0);

        if (target.before(now)) {
            target.add(Calendar.DATE, 1);
        }
        long diff = target.getTimeInMillis() - now.getTimeInMillis();
        int hours   = (int) (diff / (1000 * 60 * 60));
        int minutes = (int) ((diff / (1000 * 60)) % 60);
        return hours + " hours " + minutes + " minutes";
    }

    // Format label “Bedtime, 09:00pm” hoặc “Alarm, 05:10am”
    private String formatTimeLabel(String prefix, int hourOfDay, int minuteOfHour) {
        String ampm = (hourOfDay < 12) ? "am" : "pm";
        int dispH = (hourOfDay % 12 == 0) ? 12 : (hourOfDay % 12);
        return String.format(Locale.getDefault(),
                "%s, %02d:%02d%s", prefix, dispH, minuteOfHour, ampm);
    }

    // Hiển “Last Night Sleep”
    private void loadLastNightSleep() {
        List<SleepRepository.SleepEntry> weekSleep = repository.getThisWeekSleep();

        if (!weekSleep.isEmpty()) {
            SleepRepository.SleepEntry lastEntry = weekSleep.get(weekSleep.size() - 1);
            int hours = (int) lastEntry.duration;
            int minutes = Math.round((lastEntry.duration - hours) * 60);
            tvLastNightDuration.setText(hours + "h " + minutes + "m");
        } else {
            tvLastNightDuration.setText("0h 0m");
        }
    }

    private void updateTodaySleepText() {
        List<SleepRepository.SleepEntry> weekSleep = repository.getThisWeekSleep();

        if (!weekSleep.isEmpty()) {
            SleepRepository.SleepEntry entry = weekSleep.get(weekSleep.size() - 1);
            TextView txtSleep = findViewById(R.id.HoursOfSleepLastNight);
            txtSleep.setText(String.format(Locale.getDefault(), "%.2f giờ", entry.duration));
        }
    }


    // Vẽ biểu đồ 7 ngày
    private void drawSleepChart() {
        // 1. Cố định nhãn ngày (Mon → Sun)
        LABELS.clear();
        String[] weekdays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : weekdays) LABELS.add(day);

        // 2. Lấy dữ liệu từ repository
        List<SleepRepository.SleepEntry> sleepList = repository.getThisWeekSleep();

        // 3. Tạo entries cho biểu đồ
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (i < sleepList.size()) {
                entries.add(new Entry(i, sleepList.get(i).duration));
            } else {
                entries.add(new Entry(i, 0f)); // chưa có dữ liệu
            }
        }

        // 4. Cấu hình biểu đồ như cũ
        XAxis xAxis = chartSleepTracker.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7, true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(LABELS));
        xAxis.setTextSize(14f);

        YAxis leftAxis = chartSleepTracker.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(12f);
        leftAxis.setLabelCount(7, true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextColor(android.graphics.Color.parseColor("#B6B4C1"));
        leftAxis.setGridColor(android.graphics.Color.parseColor("#F0F0F0"));
        leftAxis.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Math.round(value) + "h";
            }
        });

        chartSleepTracker.getAxisRight().setEnabled(false);

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(android.graphics.Color.parseColor("#88A0D8FF"));
        dataSet.setDrawCircles(true);
        dataSet.setCircleRadius(4f);
        dataSet.setCircleColor(android.graphics.Color.parseColor("#00FF66"));
        dataSet.setLineWidth(2f);
        dataSet.setColor(android.graphics.Color.parseColor("#00FF66"));
        dataSet.setDrawValues(false);

        chartSleepTracker.setData(new LineData(dataSet));
        chartSleepTracker.setDescription(null);
        chartSleepTracker.animateY(1000);
        chartSleepTracker.getLegend().setEnabled(false);

        MyMarkerView mv = new MyMarkerView(this);
        mv.setChartView(chartSleepTracker);
        chartSleepTracker.setMarker(mv);

        chartSleepTracker.invalidate();
    }

}
