package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class WorkoutDetail2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details2);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.rib5wn4bvl9i);
        ImageView img2 = findViewById(R.id.rjy7kow70xub);
        ImageView img3 = findViewById(R.id.rpt08y11nchg);
        ImageView img4 = findViewById(R.id.rkjje8q5zqga);
        ImageView img5 = findViewById(R.id.r6ubbqgoomgr);
        ImageView img6 = findViewById(R.id.r7jbwwyfhe2i);
        ImageView img7 = findViewById(R.id.rioqvqnrqnq);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/904646d0-5d22-4097-bcad-c3ae9208e9ca").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/39895482-9a68-4ed2-a38b-592da04d5b73").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7930b7be-ed9e-4854-bfa4-1d2d5a8fad6f").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/afbdaa4c-fc26-4ff5-bc7e-3fea61942091").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/f138261f-ac99-49d5-9204-a38d7667acb5").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ca04dca0-a4fe-4ab7-906e-d4a74c614096").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1a8fe075-67b8-4924-91c2-c7b49b71456c").into(img7);


        // Setup buttons with click listeners
        View button1 = findViewById(R.id.r6tiqjzh897w);
        View button2 = findViewById(R.id.ruv7vdhaqmv);
        View button3 = findViewById(R.id.VidTutorial);
        View button4 = findViewById(R.id.SaveButton);

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
    }
}
