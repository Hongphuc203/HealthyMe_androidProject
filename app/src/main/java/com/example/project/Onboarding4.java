package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Onboarding4 extends AppCompatActivity {

    ImageView btnNext4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_4); // Đổi layout XML tương ứng
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f83e3444-043b-4812-abd6-8c7546d1a1a6")
                .into((ImageView) findViewById(R.id.rswpi5fthpf));

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e83bce2a-cf79-42c4-aa09-0ff40d6b5aed")
                .into((ImageView) findViewById(R.id.btnNext4));

        btnNext4 = findViewById(R.id.btnNext4);

        btnNext4.setOnClickListener(v -> {

            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", false);  // Lần sau sẽ không vào welcome nữa
            editor.apply();

            Intent intent = new Intent(Onboarding4.this, LoginPage.class);
            startActivity(intent);
        });

    }
}
