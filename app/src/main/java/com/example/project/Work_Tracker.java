package com.example.project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class Work_Tracker extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_tracker);

        // ImageViews
        ImageView image1 = findViewById(R.id.rmi23iviyo7k);
        ImageView image2 = findViewById(R.id.rdm60wwgb709);
        ImageView image3 = findViewById(R.id.rlzzvqokshij);
        ImageView image4 = findViewById(R.id.rtkum3zhxtcq);
        ImageView image5 = findViewById(R.id.rmtfige432zf);
        ImageView image6 = findViewById(R.id.rksfzc3cg6oh);
        ImageView image7 = findViewById(R.id.rccmblxkakjl);
        ImageView image8 = findViewById(R.id.rfr5dt8c02qd);
        ImageView image9 = findViewById(R.id.r5p3awsyp9a4);
        ImageView image10 = findViewById(R.id.r0f7rk3mkiss);
        ImageView image11 = findViewById(R.id.ro4fkwpv1pr9);
        ImageView image12 = findViewById(R.id.r20dtmwwmxcj);
        ImageView image13 = findViewById(R.id.rthjoh1co8kr);
        ImageView image14 = findViewById(R.id.rkzamz0jcus);

        // ShapeableImageView for special shape
        ShapeableImageView shapedImage = findViewById(R.id.rmh0xikeeod);

        // Load images with Glide
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1700ede5-aea7-4e1b-934d-4405ae39eafa").into(image1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6b5ad503-fcc7-47dd-831c-15c2859bcc83").into(image2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/39190eca-3ea4-4da9-887c-735507a9a9f9").into(image3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bcaa960c-f300-497d-8fc2-492da21aa97b").into(image4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/475e6676-039c-43ed-939e-a09c17d31ef1").into(image5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/c65cdc8d-f349-41f9-b9ea-637bec018f78").into(image6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/72f03aa4-096a-44f2-9a69-96bdc574ef81").into(image7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/887f1692-5889-498d-9e7c-816b67840932").into(image8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d16fc04c-0078-4f0e-bd91-9d7d2c5f35a6").into(image9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/75ca2cb5-c780-4b02-b559-60520b458e7b").into(image10);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a4a18f74-672f-4729-bbfa-d78bf2b6951a").into(image11);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bd3aa2ca-be41-4f54-ae99-b128403dcfa6").into(image12);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/cf141f68-fbb6-4bef-aa98-0a2d7fcd244a").into(image13);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fd1b690a-45a2-467d-8bbc-a11b5d461ed1").into(image14);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/34f2312b-80fd-48d9-9917-7e6658aa7530").into(shapedImage);

        // EditText listener
        EditText editText1 = findViewById(R.id.rw9u5susurda);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Button click listener
        View button1 = findViewById(R.id.rycdvuq51ruq);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        });
    }
}
