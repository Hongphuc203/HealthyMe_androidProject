package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.btnByEmail).setOnClickListener(v -> {
            startActivity(new Intent(this, ResetByEmail.class));
        });

        findViewById(R.id.btnByPhone).setOnClickListener(v -> {
            startActivity(new Intent(this, ResetByPhone.class));
        });
    }
}
