package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Onboarding3 extends AppCompatActivity {

    ImageView btnNext3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_3); // Đảm bảo bạn đã đổi tên file XML đúng cách

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/089e3e4e-45d8-4af4-be72-a4c0bd381fb1")
                .into((ImageView) findViewById(R.id.ry1xu5k7glkq));

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fcb977c9-59f2-44d3-9a08-a4ff2a4ea147")
                .into((ImageView) findViewById(R.id.btnNext3));

        btnNext3 = findViewById(R.id.btnNext3);

        btnNext3.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding3.this, Onboarding4.class);
            startActivity(intent);
        });

    }
}

