package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;

public class Onboarding1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_1);

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ecacf8a7-d652-4791-ab38-7c534dd960c7")
                .into((ImageView) findViewById(R.id.r8rl0m4b3a86));

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/abf0515a-6f74-4943-98c4-fde11a5c8979")
                .into((ImageView) findViewById(R.id.r3vmh66ssxqj));
    }
}
