package com.example.project;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class AlarmActivity extends Activity {
    private Ringtone ringtone;
    private String type;
    private float hoursSlept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm); // Đảm bảo trùng với tên layout XML
        if ("WAKEUP".equals(type)) {
            float sleptHours = getIntent().getFloatExtra("HOURS_SLEPT", 0f);
            TextView sleepText = findViewById(R.id.HoursOfSleepLastNight);
            sleepText.setText(String.format(Locale.getDefault(), "%.2f giờ", sleptHours));
        }
        // Lấy extra truyền từ AlarmReceiver
        type = getIntent().getStringExtra("TYPE");
        if ("WAKEUP".equals(type)) {
            hoursSlept = getIntent().getFloatExtra("HOURS_SLEPT", 0f);
        }

        // Tìm các view trong layout
        TextView tvMessage = findViewById(R.id.tv_alarm_message);
        Button btnStop    = findViewById(R.id.btn_stop_alarm);

        // Xác định thông điệp dựa trên TYPE
        if ("BEDTIME".equals(type)) {
            tvMessage.setText("Đã đến giờ đi ngủ! Nhấn Tắt để dừng chuông.");
        } else if ("WAKEUP".equals(type)) {
            tvMessage.setText(String.format(
                    "Bạn đã ngủ %.2f giờ. Nhấn Tắt để dừng chuông.",
                    hoursSlept
            ));
        } else {
            tvMessage.setText("Báo thức!");
        }

        // Lấy URI nhạc chuông ALARM mặc định
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            // Nếu không có nhạc chuông ALARM, fallback sang nhạc Notification
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // Tạo và phát Ringtone
        ringtone = RingtoneManager.getRingtone(this, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }

        // Xử lý nút Tắt báo thức
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ringtone != null && ringtone.isPlaying()) {
                    ringtone.stop();
                }
                finish(); // Đóng AlarmActivity
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Nếu Activity bị hủy mà ringtone vẫn đang chơi, dừng nó
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }
}
