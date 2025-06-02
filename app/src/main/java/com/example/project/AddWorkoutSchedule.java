package com.example.project;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_schedule);
        // 1) Lấy múi giờ VN và Calendar
        TimeZone tzVN = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calVN = Calendar.getInstance(tzVN);

        // 2) Gắn view
        timePicker = findViewById(R.id.timePicker);
        DateChoose = findViewById(R.id.DateNow);

        // 3) Format và set ngày mặc định
        SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
        fmt.setTimeZone(tzVN);
        DateChoose.setText(fmt.format(calVN.getTime()));

        // 4) Khởi tạo DatePicker với selection là ngày VN hiện tại
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(calVN.getTimeInMillis())
                .build();

        // 5) Show picker khi click
        DateChoose.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "tag"));

        // 6) Khi chọn xong, cập nhật lại TextView
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar cal = Calendar.getInstance(tzVN);
            cal.setTimeInMillis(selection);
            DateChoose.setText(fmt.format(cal.getTime()));
        });

        // 7) Set giờ phút mặc định cho TimePicker (API>=23)
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
        ImageView img5 = findViewById(R.id.r464rjbxczs4);
        ImageView img6 = findViewById(R.id.rguwaci3ae6g);
        ImageView img7 = findViewById(R.id.rkdyy1kyhaq9);
        ImageView img8 = findViewById(R.id.rb5d42e9ya7u);
        ImageView img9 = findViewById(R.id.r4bkzgixg1lp);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/8e6cccaf-659d-4fee-80cc-7694d6186e39").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9f802daf-eb9d-4f79-9ebe-d5d88d210006").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3d2f1bd2-cdbb-4388-92df-17d33b49fc11").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/235d5b60-4d3c-410f-a819-a4611995f2c0").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f770d24a-011d-4599-ad8c-aa103ab3c267").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/12a30d2c-5c0c-4a81-be02-843307944b71").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/387dd415-8490-4f4b-aa11-a1108457e30a").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f9c4b532-d2fa-4eab-86ef-102c2189986c").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6622d123-ea6f-4351-8bd2-ef330d41a3d7").into(img9);


        // Setup buttons with click listeners
        View button1 = findViewById(R.id.rvccip4dzft9);
        View button2 = findViewById(R.id.rpmpnzv6d39);
        View button3 = findViewById(R.id.Repetition);
        View button4 = findViewById(R.id.rv5lc4ki1ty9);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
    }
}
