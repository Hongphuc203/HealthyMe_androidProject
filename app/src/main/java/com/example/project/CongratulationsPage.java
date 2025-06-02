package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class CongratulationsPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_page);

        // Load image with Glide
        ImageView img = findViewById(R.id.rs7bhahf413l);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/db6fcf88-87c4-4fe2-b0c6-2ed9323341d5")
                .into(img);

        // Setup button with click listener
        View button1 = findViewById(R.id.BackToHome);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        });
    }
}
