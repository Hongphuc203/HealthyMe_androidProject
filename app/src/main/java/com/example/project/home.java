package com.example.project;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class home extends AppCompatActivity {

    private int currentTrackIndex = 0;
    private boolean isPlaying = true;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView tvCurrentTime, tvTotalDuration;
    private List<Track> trackList = new ArrayList<>(); //lưu danh sách bài hát

    TextView txtFullName, txtSleepHome;
    LinearLayout btnNotification, btnViewMore, btnCheck;
    PieChart pieChart;
    ImageView iconWorkOut, iconCamera, iconProfile;
    private SleepRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtFullName = findViewById(R.id.txtFullName);
        btnNotification = findViewById(R.id.btnNotification);
        btnViewMore = findViewById(R.id.btnViewMore);
        pieChart = findViewById(R.id.bmiPieChart);
        iconWorkOut = findViewById(R.id.iconWorkOut);
        iconCamera = findViewById(R.id.iconCamera);
        iconProfile = findViewById(R.id.iconProfile);
        btnCheck = findViewById(R.id.btnCheck);


        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/757fbe1d-dbe0-42c5-b235-f05c070e2a29")
                .into((ImageView) findViewById(R.id.iconNotification));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/50b84095-1b43-43f4-9d07-f0191ea8a231")
                .into((ImageView) findViewById(R.id.iconHome));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9fd92ea3-6821-4365-910b-0fdf7136423f")
                .into((ImageView) findViewById(R.id.iconWorkOut));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/56c717fd-0ebb-4ecd-887e-9e5703a547dc")
                .into((ImageView) findViewById(R.id.iconCamera));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/19a02c38-fc9e-4c71-9786-3725db697ba5")
                .into((ImageView) findViewById(R.id.iconProfile));

        // lấy fullname để hiển thị
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("fullName");
                            if (fullName != null) {
                                txtFullName.setText(fullName);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("HOME", "Lỗi khi lấy fullName: " + e.getMessage());
                    });
        }

        PeriodicWorkRequest reminderRequest = new PeriodicWorkRequest.Builder(
                ReminderWorker.class,
                15, TimeUnit.MINUTES // khoảng cách giữa mỗi lần check
        ).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "ReminderCheck",
                ExistingPeriodicWorkPolicy.KEEP,
                reminderRequest
        );
        // chuyển sang Activity Notification
        btnNotification.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, Notification.class);
            startActivity(intent);
        });

        // chuyển sang Activity BMIDetail
        btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, BMIDetailActivity.class);
            startActivity(intent);
        });

        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Number heightNum = documentSnapshot.getDouble("height");
                            Number weightNum = documentSnapshot.getDouble("weight");

                            if (heightNum != null && weightNum != null) {
                                float height = heightNum.floatValue(); // cm
                                float weight = weightNum.floatValue(); // kg

                                float bmi = weight / ((height / 100f) * (height / 100f));
                                updateBMIChart(bmi);
                            } else {
                                Toast.makeText(this, "Thiếu thông tin chiều cao hoặc cân nặng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        //chuyển sang ActivityTracker
        btnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, ActivityTracker.class);
            startActivity(intent);
        });

        //chuyển sang Work_Tracker
        iconWorkOut.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, Work_Tracker.class);
            startActivity(intent);
        });

        //chuyển sang TakePhoto
        iconCamera.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, TakePhoto.class);
            startActivity(intent);
        });

        //chuyển sang Profile
        iconProfile.setOnClickListener(v -> {
            Intent intent = new Intent(home.this, Profile.class);
            startActivity(intent);
        });

        // chuyển sang Sleep Tracker
        CardView ViewSleepTracker = findViewById(R.id.ViewSleepTracker);
        ViewSleepTracker.setOnClickListener(v ->{
            Intent intent = new Intent(home.this, SleepTracker.class);
            startActivity(intent);
        });

        FrameLayout container = findViewById(R.id.musicCardContainer);
        TransitionDrawable transition = (TransitionDrawable) ContextCompat.getDrawable(this, R.drawable.transient_background);
        if (transition != null) {
            container.setBackground(transition);

            int duration = 1500;
            Handler handler = new Handler();
            Runnable colorLoop = new Runnable() {
                boolean reversed = false;

                @Override
                public void run() {
                    if (!reversed) {
                        transition.startTransition(duration);
                    } else {
                        transition.reverseTransition(duration);
                    }
                    reversed = !reversed;
                    handler.postDelayed(this, duration);
                }
            };
            handler.post(colorLoop);
        }
        // Init
        seekBar = findViewById(R.id.seekBar);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalDuration = findViewById(R.id.tvTotalDuration);

        // Media player
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Xóa cái cũ nếu có
        }
        mediaPlayer = new MediaPlayer(); // Tạo mới

        // Nút chọn bài hát
        Button btnChooseSong = findViewById(R.id.btnChooseSong);
        btnChooseSong.setOnClickListener(v -> showPlaylistDialog());
        ImageView btnPlayPause = findViewById(R.id.btnPlayPause);
        ImageView btnNext = findViewById(R.id.btnNext);
        ImageView btnPrev = findViewById(R.id.btnPrev);

        // Nút Play/Pause
        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlayPause.setBackgroundResource(R.drawable.play_song); // icon dạng PNG tròn
                isPlaying = false;
            } else {
                mediaPlayer.start();
                btnPlayPause.setBackgroundResource(R.drawable.pause_song); // trở lại ảnh gốc XML
                isPlaying = true;
            }
        });

        // Nút Next
        btnNext.setOnClickListener(v -> {
            if (trackList != null && !trackList.isEmpty()) {
                currentTrackIndex = (currentTrackIndex + 1) % trackList.size();
                playTrack(trackList.get(currentTrackIndex));
                isPlaying = true;
            }
        });

        // Nút Previous
        btnPrev.setOnClickListener(v -> {
            if (trackList != null && !trackList.isEmpty()) {
                currentTrackIndex = (currentTrackIndex - 1 + trackList.size()) % trackList.size();
                playTrack(trackList.get(currentTrackIndex));
                isPlaying = true;
            }
        });

        // Tải và phát bài đầu tiên
        if (!trackList.isEmpty()) {
            playTrack(trackList.get(currentTrackIndex));
        } else {
            loadSongFromJamendo();
        }

        updateWaterStats();

        txtSleepHome = findViewById(R.id.txtSleepHome);
        repository = new SleepRepository(this);

        // Cập nhật giờ ngủ đêm qua
        updateSleepCard();
    }

    //hàm update BMIChart
    private void updateBMIChart(float bmi) {
        PieChart pieChart = findViewById(R.id.bmiPieChart);
        TextView txtBMI = findViewById(R.id.txtBMI); // text hiển thị cảnh báo BMI

        float remaining = 100f - Math.min(bmi, 100f);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(bmi, ""));
        entries.add(new PieEntry(remaining, ""));

        PieDataSet dataSet = new PieDataSet(entries, "");

        // Màu theo mức độ
        int color;
        if (bmi < 18.5f) {
            color = Color.parseColor("#F4F152"); // vàng
        } else if (bmi < 25f) {
            color = Color.parseColor("#48EF37"); // xanh lá
        } else {
            color = Color.parseColor("#FA0404"); // đỏ
        }
        dataSet.setColors(color, Color.parseColor("#D9A0E2"));

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                if (Math.abs(value - bmi) < 0.01f) {
                    return String.format(Locale.getDefault(), "%.1f", value);
                }
                return ""; // Không hiện label với phần còn lại
            }
        });

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate(); // cập nhật biểu đồ

        // Text BMI: thông báo trạng thái
        String bmiStatus;
        if (bmi < 18.5f) {
            bmiStatus = "Chú ý! Tình trạng Thiếu cân";
        } else if (bmi < 25f) {
            bmiStatus = "Bạn có chiều cao và cân nặng lý tưởng";
        } else if (bmi < 30f) {
            bmiStatus = "Chú ý! Tình trạng Thừa cân";
        } else {
            bmiStatus = "Cảnh báo!! Tình trạng Béo phì";
        }
        txtBMI.setText(bmiStatus);
    }

    private void loadSongFromJamendo() {
        JamendoApi.fetchTracks(new JamendoApi.Callback() {
            @Override
            public void onSuccess(List<Track> tracks) {
                if (tracks == null || tracks.isEmpty()) return;

                trackList.clear();
                trackList.addAll(tracks);

                playTrack(trackList.get(0));
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(home.this, "Không thể tải danh sách bài hát", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPlaylistDialog() {
        if (trackList == null || trackList.isEmpty()) {
            Toast.makeText(this, "Danh sách bài hát đang tải...", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] songTitles = new String[trackList.size()];
        for (int i = 0; i < trackList.size(); i++) {
            songTitles[i] = trackList.get(i).name + " - " + trackList.get(i).artist;
        }

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Chọn bài hát")
                .setItems(songTitles, (dialog, which) -> playTrack(trackList.get(which)))
                .setNegativeButton("Huỷ", null)
                .show();
    }

    private void playTrack(Track track) {
        try {
            ((TextView) findViewById(R.id.tvSongName)).setText(track.name);
            ((TextView) findViewById(R.id.tvSongSinger)).setText(track.artist);
            Glide.with(this).load(track.albumImage).into((ImageView) findViewById(R.id.ivAlbumArt));

            if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
            } else {
                    mediaPlayer.reset();
            }

            mediaPlayer.setDataSource(track.audioUrl);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(mp -> {

                int duration = mp.getDuration();
                seekBar.setMax(duration);
                tvTotalDuration.setText(formatTime(duration));
                updateSeekBar();

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            tvCurrentTime.setText(formatTime(progress));
                        }
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override public void onStopTrackingTouch(SeekBar seekBar) {}
                });
            });


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Không thể phát bài hát", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSeekBar() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int pos = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(pos);
                    tvCurrentTime.setText(formatTime(pos));
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.post(runnable);
    }

    private String formatTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private void updateWaterStats() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        long start = getStartOfTodayTimestamp();
        long end = getEndOfTodayTimestamp();

        FirebaseFirestore.getInstance()
                .collection("users").document(userId).collection("activities")
                .whereEqualTo("type", "WATER")
                .whereGreaterThanOrEqualTo("timestamp", start)
                .whereLessThan("timestamp", end)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null || snapshots == null) return;

                    float total = 0;
                    String timeText = "";
                    String amountText = "";

                    for (int i = 0; i < snapshots.size(); i++) {
                        Long value = snapshots.getDocuments().get(i).getLong("value");
                        Long ts = snapshots.getDocuments().get(i).getLong("timestamp");

                        if (value != null) total += value;

                        if (i == 0 && value != null && ts != null) {
                            timeText = getTimeAgo(new java.util.Date(ts));
                            amountText = value + "ml";
                        }
                    }

                    // Update text
                    ((TextView) findViewById(R.id.txtLiters)).setText(String.format(Locale.getDefault(), "%.1f Liters", total / 1000f));
                    ((TextView) findViewById(R.id.txtTime)).setText(timeText);
                    ((TextView) findViewById(R.id.txtMl)).setText(amountText);

                    // Update chart
                    float percent = Math.min(total / 2000f, 1f) * 100f;
                    BarChart chart = findViewById(R.id.singleBarChart);
                    List<BarEntry> entries = new ArrayList<>();
                    entries.add(new BarEntry(0, percent));

                    BarDataSet dataSet = new BarDataSet(entries, "");
                    dataSet.setColor(Color.parseColor("#C050F6"));

                    BarData barData = new BarData(dataSet);
                    barData.setBarWidth(0.5f);
                    barData.setDrawValues(false);

                    chart.setData(barData);
                    chart.getDescription().setEnabled(false);
                    chart.getLegend().setEnabled(false);
                    chart.getAxisLeft().setAxisMinimum(0);
                    chart.getAxisLeft().setAxisMaximum(100);
                    chart.getAxisLeft().setEnabled(true);
                    chart.getAxisRight().setEnabled(false);
                    chart.getXAxis().setEnabled(false);
                    chart.setTouchEnabled(false);
                    chart.invalidate();
                });
    }

    private String getTimeAgo(java.util.Date date) {
        long diff = new java.util.Date().getTime() - date.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        if (minutes < 1) return "Right now";
        if (minutes < 60) return minutes + " minutes ago";
        long hours = minutes / 60;
        return hours + " hours ago";
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

    private void updateSleepCard() {
        List<SleepRepository.SleepEntry> weekSleep = repository.getThisWeekSleep();

        if (!weekSleep.isEmpty()) {
            SleepRepository.SleepEntry last = weekSleep.get(weekSleep.size() - 1);
            int h = (int) last.duration;
            int m = Math.round((last.duration - h) * 60);
            txtSleepHome.setText(String.format(Locale.getDefault(), "%dh %dm", h, m));
        } else {
            txtSleepHome.setText("0h 0m");
        }
    }


    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
