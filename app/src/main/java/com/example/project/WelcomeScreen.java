package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {

    LinearLayout btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Kiểm tra SharedPreferences
//        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
//        boolean isFirstTime = prefs.getBoolean("isFirstTime", true);
//
//        if (!isFirstTime) {
//            // Nếu không phải lần đầu mở app → bỏ qua welcome
//            startActivity(new Intent(WelcomeScreen.this, LoginPage.class));
//            finish();
//            return;
//        }

        // Nếu là lần đầu → hiển thị welcome
        setContentView(R.layout.activity_welcome_screen2);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeScreen.this, Onboarding1.class);
            startActivity(intent);
        });
    }
}
