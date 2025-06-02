package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class CompareResult1 extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_result1);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.rt9517h4x3aj);
        ImageView img2 = findViewById(R.id.r5vch2a70pgx);
        ImageView img3 = findViewById(R.id.rkno4g2pv63);
        ImageView img4 = findViewById(R.id.rshs43txxdm);
        ImageView img5 = findViewById(R.id.ridk7ea0ze3);
        ImageView img6 = findViewById(R.id.rgx3uhnv7npv);
        ImageView img7 = findViewById(R.id.rmyq1k75gnr);
        ImageView img8 = findViewById(R.id.r6cwy9sa8drl);
        ImageView img9 = findViewById(R.id.rvm6gx65yz3);
        ImageView img10 = findViewById(R.id.r91jzbjkf8dr);
        ImageView img11 = findViewById(R.id.rkdoaoypv9h);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a00fd4bc-2c91-4b03-9dbd-8fc9fa1fa1c0").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9b2ab142-605c-4ece-a7fe-53d98d6f98e4").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1382c5f1-043f-41a1-abda-10b89f658e24").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/851f7087-203d-4b6a-a8e7-07cb607d4e1b").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/175f1873-bc4b-45c5-b1af-723ab4d9d84b").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9c16a51c-0851-498b-a691-97f6002faf2f").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/249751b1-5c50-438d-8ca4-16cad94456dd").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d79b63aa-d960-4eaa-8a58-9d45607b3d60").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/80231a2b-8cf4-479a-a037-7271c0244115").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3102cf5f-a9fa-420a-9266-74b370408a4d").into(img10);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/08140401-3ee7-461e-915c-12edbc9ed95d").into(img11);

        // TextWatcher for EditText
        EditText editText1 = findViewById(R.id.r2kt7rdwcmbz);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // before Text Changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue1 = s.toString();  // on Text Changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // after Text Changed
            }
        });

        // Setup buttons with click listeners
        View button1 = findViewById(R.id.r365xx8ch8ah);
        View button2 = findViewById(R.id.rvr42l12gon);
        View button3 = findViewById(R.id.rzsg5pfze1m);
        View button4 = findViewById(R.id.r6lvqx0zjygn);

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
