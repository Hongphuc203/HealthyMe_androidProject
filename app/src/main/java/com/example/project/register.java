package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register); // Đảm bảo file XML tồn tại

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/hc706wbf_expires_30_days.png")
                .into((ImageView) findViewById(R.id.r3nvni4t7804));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/rzn4cjkz_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rk1b0qnhhs7k));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/e7bli6hs_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rbjtcykoidx6));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/4aj87ln8_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rafo10au60y));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/hsrr1t8v_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rokwdk3ruld));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/ppazy1yj_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rwrf2x0luog));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/yvjo6yns_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rb6y5u5242xw));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/pfy8nxdc_expires_30_days.png")
                .into((ImageView) findViewById(R.id.r80xf2xw5aos));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/bwyue0vn_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rbdn5hkuk6q8));

        View button1 = findViewById(R.id.rpya3mb8gcw);
        button1.setOnClickListener(v -> System.out.println("Button 1 Pressed"));

        View button2 = findViewById(R.id.rfvnpbriha17);
        button2.setOnClickListener(v -> System.out.println("Button 2 Pressed"));

        View button3 = findViewById(R.id.r22ulzq039n);
        button3.setOnClickListener(v -> System.out.println("Button 3 Pressed"));
    }
}
