package com.example.project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReminderWorker extends Worker {

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("reminders_templates")
                .get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        String title = doc.getString("title");
                        String content = doc.getString("content");
                        String time = doc.getString("remind_time");
                        Boolean repeat = doc.getBoolean("repeat");

                        if (title == null || content == null || time == null) continue;

                        String[] parts = time.split(":");
                        if (parts.length != 2) continue;

                        int hour = Integer.parseInt(parts[0]);
                        int minute = Integer.parseInt(parts[1]);

                        // Lấy thời gian hiện tại và remind_time
                        long nowMillis = System.currentTimeMillis();
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        long remindMillis = cal.getTimeInMillis();

                        // Nếu chênh lệch thời gian dưới 1 phút
                        if (Math.abs(nowMillis - remindMillis) <= 60000) {
                            Map<String, Object> noti = new HashMap<>();
                            noti.put("title", title);
                            noti.put("content", content);
                            noti.put("timestamp", nowMillis);

                            // Ghi vào Firestore
                            db.collection("users")
                                    .document(userId)
                                    .collection("notifications")
                                    .add(noti);

                            // Hiển thị thông báo cục bộ
                            showNotification(title, content);
                        }
                    }
                });

        return Result.success();
    }

    private void showNotification(String title, String content) {
        Context context = getApplicationContext();
        String channelId = "reminder_channel";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Reminders", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
