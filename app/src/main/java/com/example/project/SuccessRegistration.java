package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SuccessRegistration extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);

        // Load success image with Glide
        ImageView img = findViewById(R.id.rc2vglbhhtb7);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/447b8811-9fee-4674-a954-a05ed1092083")
                .into(img);

        // Setup button with click listener
        View button1 = findViewById(R.id.rgugbxsfkcd);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add navigation or action here
                System.out.println("Pressed");
            }
        });
    }
}
