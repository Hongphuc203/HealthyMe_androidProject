package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SleepSchedule extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_schedule);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.r48k0tbp3bxj);
        ImageView img2 = findViewById(R.id.r7s38xcht2cd);
        ImageView img3 = findViewById(R.id.rjisnfamqk7);
        ImageView img4 = findViewById(R.id.rz9m8yzy0fj);
        ImageView img5 = findViewById(R.id.rf7n0lxykoo);
        ImageView img6 = findViewById(R.id.rz1lumfpqjt);
        ImageView img7 = findViewById(R.id.rozvzx59jtbn);
        ImageView img8 = findViewById(R.id.rda9mxvhqp5o);
        ImageView img9 = findViewById(R.id.r4n6a4f6n8ea);
        ImageView img10 = findViewById(R.id.r3zisjb9smoy);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0ee1c687-5eaa-4edf-8f94-a789dc05e553").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/808658ac-a841-4ec5-9053-b6a28e11773e").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/adda31ef-e896-4c2c-8851-6fd9bf91f694").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1bc47ec8-04db-4435-b9cc-2d9ae229c1bd").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f1692cdc-016f-4f6e-88e1-df39f7decc85").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/373e3700-40d4-43fe-9134-70f23e778641").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e23697f2-64d1-400c-afb8-7e092e5b1e3f").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e75fdeeb-4343-4d1e-ba39-3c5054ba4c6e").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/c1a593f6-654d-4c26-a703-3231b4729f0e").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bee1c990-2c49-441d-b0ee-2332e53c2f84").into(img10);

        // Setup buttons with click listeners
        View button1 = findViewById(R.id.rm49uv8encd8);
        View button2 = findViewById(R.id.ro3ymni0h6);
        View button3 = findViewById(R.id.rdd32q4j7ua7);
        View button4 = findViewById(R.id.Wesday);
        View button5 = findViewById(R.id.Thurs);
        View button6 = findViewById(R.id.Friday);
        View button7 = findViewById(R.id.Sartuday);
        View button8 = findViewById(R.id.Sunday);

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
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
    }
}
