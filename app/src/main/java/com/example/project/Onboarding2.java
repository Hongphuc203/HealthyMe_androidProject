package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;

public class Onboarding2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_2); // Đảm bảo tên file XML không có dấu `-`

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/46ddec4d-4f94-4947-b186-4db70218f640")
                .into((ImageView) findViewById(R.id.r2dtzhzdrj7p));

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/736b1866-bb09-42d1-af3c-91a241a29be0")
                .into((ImageView) findViewById(R.id.rim86cklr6g));
    }
}
