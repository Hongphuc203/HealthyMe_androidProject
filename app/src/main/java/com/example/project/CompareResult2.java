package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class CompareResult2 extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_result2);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.r9z6fw2bol8a);
        ImageView img3 = findViewById(R.id.rh7jq4qtngui);
        ImageView img4 = findViewById(R.id.rmuly07xt3re);
        ImageView img5 = findViewById(R.id.reut60viuiwj);
        ImageView img6 = findViewById(R.id.rbs3709cgr6a);
        ImageView img7 = findViewById(R.id.rxiyd9p43che);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/05c4c483-e366-47ea-af5b-e24aa30d3530").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4d8b4324-f0e2-4ad5-a0bd-e7ac3823e207").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6ea0af2f-d8d5-4c1a-93e3-df1359ef5300").into(img4);
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/dcec3177-9aff-4ff9-9e96-fdeb000bdbe2").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/20e63745-80f0-47d1-a862-4827ac20afd9").into(img7);

        // TextWatcher for EditText
        EditText editText1 = findViewById(R.id.rzvo43ox9nj);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // before Text Changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue1 = s.toString(); // on Text Changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // after Text Changed
            }
        });

        // Setup buttons with click listeners
        View button1 = findViewById(R.id.rso1eja7k1ph);
        View button3 = findViewById(R.id.r4azl5psx61v);
        View button4 = findViewById(R.id.rwkrq039ndkq);
        View button5 = findViewById(R.id.rmimk20hgsw);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        };

        button1.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
    }
}