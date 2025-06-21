package com.example.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("TYPE");

        SharedPreferences prefs = context.getSharedPreferences("SleepPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if ("BEDTIME".equals(type)) {
            // 1. Ghi timestamp khi đến giờ đi ngủ
            long nowMillis = System.currentTimeMillis();
            editor.putLong("sleep_start_ts", nowMillis);
            editor.apply();

            // 2. Khởi AlarmActivity để phát chuông Bedtime
            Intent alarmIntent = new Intent(context, AlarmActivity.class);
            alarmIntent.putExtra("TYPE", "BEDTIME");
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(alarmIntent);

        } else if ("WAKEUP".equals(type)) {
            // 1. Lấy timestamp đi ngủ từ SharedPreferences
            long startTs = prefs.getLong("sleep_start_ts", -1);
            if (startTs < 0) {
                // Nếu không có dữ liệu đi ngủ, mở AlarmActivity với HOURS_SLEPT = 0
                Intent alarmIntent = new Intent(context, AlarmActivity.class);
                alarmIntent.putExtra("TYPE", "WAKEUP");
                alarmIntent.putExtra("HOURS_SLEPT", 0f);
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(alarmIntent);
                return;
            }

            // 2. Tính số giờ đã ngủ
            long nowTs = System.currentTimeMillis();
            float hoursSlept = (nowTs - startTs) / (1000f * 60f * 60f);

            // 3. Lưu vào SQLite qua SleepRepository
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(startTs);  // lấy theo timestamp đi ngủ
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String sleepDate = sdf.format(cal.getTime());

            SleepRepository repo = new SleepRepository(context);
            repo.upsertSleep(sleepDate, hoursSlept);

            // 4. Xóa timestamp đi ngủ để không dùng lại
            editor.remove("sleep_start_ts");
            editor.apply();

            // 5. Gửi Intent về SleepTracker để cập nhật ngay UI
            Intent refreshIntent = new Intent(context, SleepTracker.class);
            refreshIntent.putExtra("REFRESH_FROM_WAKEUP", true);
            refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(refreshIntent);

            // 6. Khởi AlarmActivity để phát chuông và hiển thị số giờ đã ngủ
            Intent alarmIntent = new Intent(context, AlarmActivity.class);
            alarmIntent.putExtra("TYPE", "WAKEUP");
            alarmIntent.putExtra("HOURS_SLEPT", hoursSlept);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(alarmIntent);
        }
    }
}
