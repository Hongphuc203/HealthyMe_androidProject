package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CompareResult1 extends AppCompatActivity {
    private static final String PREFS_NAME = "MyImagePrefs";
    private LinearLayout btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_result1);

        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, home.class);
            startActivity(intent);
        });

        // 1) Lấy reference các ImageView Past & Present cho từng hướng
        ImageView imgPastFront    = findViewById(R.id.Past);
        ImageView imgPresentFront = findViewById(R.id.Present);

        ImageView imgPastLeft    = findViewById(R.id.rgx3uhnv7npv);
        ImageView imgPresentLeft = findViewById(R.id.rmyq1k75gnr);

        ImageView imgPastBack    = findViewById(R.id.r6cwy9sa8drl);
        ImageView imgPresentBack = findViewById(R.id.rvm6gx65yz3);

        ImageView imgPastRight    = findViewById(R.id.r91jzbjkf8dr);
        ImageView imgPresentRight = findViewById(R.id.rkdoaoypv9h);

        // 2) Mở SharedPreferences để đọc URI đã lưu
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // FRONT
        String frontLastStr    = prefs.getString("front_last", null);
        String frontCurrentStr = prefs.getString("front_current", null);
        if (frontLastStr != null) {
            Glide.with(this)
                    .load(Uri.parse(frontLastStr))
                    .into(imgPastFront);
        }
        if (frontCurrentStr != null) {
            Glide.with(this)
                    .load(Uri.parse(frontCurrentStr))
                    .into(imgPresentFront);
        }

        // LEFT
        String leftLastStr    = prefs.getString("left_last", null);
        String leftCurrentStr = prefs.getString("left_current", null);
        if (leftLastStr != null) {
            Glide.with(this)
                    .load(Uri.parse(leftLastStr))
                    .into(imgPastLeft);
        }
        if (leftCurrentStr != null) {
            Glide.with(this)
                    .load(Uri.parse(leftCurrentStr))
                    .into(imgPresentLeft);
        }

        // BACK
        String backLastStr    = prefs.getString("back_last", null);
        String backCurrentStr = prefs.getString("back_current", null);
        if (backLastStr != null) {
            Glide.with(this)
                    .load(Uri.parse(backLastStr))
                    .into(imgPastBack);
        }
        if (backCurrentStr != null) {
            Glide.with(this)
                    .load(Uri.parse(backCurrentStr))
                    .into(imgPresentBack);
        }

        // RIGHT
        String rightLastStr    = prefs.getString("right_last", null);
        String rightCurrentStr = prefs.getString("right_current", null);
        if (rightLastStr != null) {
            Glide.with(this)
                    .load(Uri.parse(rightLastStr))
                    .into(imgPastRight);
        }
        if (rightCurrentStr != null) {
            Glide.with(this)
                    .load(Uri.parse(rightCurrentStr))
                    .into(imgPresentRight);
        }
    }
}
