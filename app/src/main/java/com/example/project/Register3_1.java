package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R; // Đảm bảo đúng package

public class Register3_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_3_1); // Đảm bảo file XML được đổi tên hợp lệ

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d3da200a-12e5-4251-830b-a426824ea305")
                .into((ImageView) findViewById(R.id.rhelbn2nimp));

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/74d21f0a-23df-4311-bdff-4c363e7dcc3a")
                .into((ImageView) findViewById(R.id.r6693ogmqery));

        View button1 = findViewById(R.id.ry5dxvl1ozvi);
        button1.setOnClickListener(v -> System.out.println("Pressed"));
    }
}
