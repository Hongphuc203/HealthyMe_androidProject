package com.example.project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddWorkoutSchedule extends AppCompatActivity {
    private TextView DateChoose;
    private TimePicker timePicker;
    private LinearLayout btnBackAddSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_schedule);

        TimeZone tzVN = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calVN = Calendar.getInstance(tzVN);

        timePicker = findViewById(R.id.timePicker);
        DateChoose = findViewById(R.id.DateNow);
        btnBackAddSchedule = findViewById(R.id.btnBackAddSchedule);

        SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
        fmt.setTimeZone(tzVN);
        DateChoose.setText(fmt.format(calVN.getTime()));

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(calVN.getTimeInMillis())
                .build();

        DateChoose.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "tag"));

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar cal = Calendar.getInstance(tzVN);
            cal.setTimeInMillis(selection);
            DateChoose.setText(fmt.format(cal.getTime()));
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(calVN.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calVN.get(Calendar.MINUTE));
        } else {
            timePicker.setCurrentHour(calVN.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calVN.get(Calendar.MINUTE));
        }

        ImageView img1 = findViewById(R.id.rxzpwwu4gzna);
        ImageView img2 = findViewById(R.id.rliefy2nfuja);
        ImageView img3 = findViewById(R.id.rhc3u23lj0xa);
        ImageView img4 = findViewById(R.id.r3imphkoshc1);
        ImageView img6 = findViewById(R.id.rguwaci3ae6g);
        ImageView img8 = findViewById(R.id.rb5d42e9ya7u);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/8e6cccaf-659d-4fee-80cc-7694d6186e39").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9f802daf-eb9d-4f79-9ebe-d5d88d210006").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3d2f1bd2-cdbb-4388-92df-17d33b49fc11").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/235d5b60-4d3c-410f-a819-a4611995f2c0").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/12a30d2c-5c0c-4a81-be02-843307944b71").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f9c4b532-d2fa-4eab-86ef-102c2189986c").into(img8);


        View button3 = findViewById(R.id.Repetition);
        LinearLayout button4 = findViewById(R.id.SaveButton);
        Spinner spinnerWorkout = findViewById(R.id.spinnerWorkout);
        Spinner spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        EditText editRepetition = findViewById(R.id.editRepetition);

        btnBackAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(AddWorkoutSchedule.this, WorkoutSchedule.class);
            startActivity(intent);
        });

        button4.setOnClickListener(v -> {
            String title = spinnerWorkout.getSelectedItem().toString();
            String level = spinnerDifficulty.getSelectedItem().toString();
            String reps = editRepetition.getText().toString();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String timeString = String.format(Locale.getDefault(), "%02d:%02d %s",
                    (hour % 12 == 0) ? 12 : (hour % 12),
                    minute,
                    (hour < 12) ? "AM" : "PM");

            // ⚠️ Kiểm tra selection, nếu null thì dùng ngày mặc định
            long selectedMillis = (datePicker.getSelection() != null)
                    ? datePicker.getSelection()
                    : Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")).getTimeInMillis();

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            cal.setTimeInMillis(selectedMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            String selectedDate = sdf.format(cal.getTime());

            // Tạo và lưu task
            WorkOutTask task = new WorkOutTask(timeString, title, level, reps);
            task.setDate(selectedDate);

            WorkoutRepository repo = new WorkoutRepository(AddWorkoutSchedule.this);
            repo.insert(task);

            Intent result = new Intent();
            result.putExtra("newTask", task);
            setResult(RESULT_OK, result);
            finish();
        });


    }
}
