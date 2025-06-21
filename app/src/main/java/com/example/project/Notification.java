package com.example.project;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Notification extends AppCompatActivity {

    LinearLayout btnNotiBack;
    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notiList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnNotiBack = findViewById(R.id.btnNotiBack);

        // Ép kiểu về ImageView trước khi dùng Glide
        ImageView img1 = findViewById(R.id.r1mz030ntwl3);
        ImageView img2 = findViewById(R.id.rvzvg1jq7tdf);

        // Load ảnh bằng Glide
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7e0944d9-5638-40ed-87d7-ed3934118999").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a60342ff-d44e-46d1-834b-4ef84e4270a9").into(img2);


        recyclerView = findViewById(R.id.recyclerNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(notiList);
        recyclerView.setAdapter(adapter);

        loadNotifications();

        // Các nút
        btnNotiBack.setOnClickListener(v -> {
            Intent intent = new Intent(Notification.this, home.class);
            startActivity(intent);
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void loadNotifications() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("notifications")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null || snapshots == null) return;

                    notiList.clear();
                    for (DocumentSnapshot doc : snapshots) {
                        String title = doc.getString("title");
                        String content = doc.getString("content");
                        Long timestamp = doc.getLong("timestamp");

                        if (title != null && content != null && timestamp != null) {
                            notiList.add(new NotificationItem(title, content, timestamp));
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}
