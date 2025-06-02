package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class SleepTracker extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_tracker);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.rce5x3iprbul);
        ImageView img2 = findViewById(R.id.rnyrczzis2m);
        ImageView img3 = findViewById(R.id.rmcunlcse5go);
        ImageView img4 = findViewById(R.id.rhqhjn0fvncn);
        ImageView img5 = findViewById(R.id.rk08uxx6kojg);
        ImageView img6 = findViewById(R.id.rsumk43d3fe);
        ImageView img7 = findViewById(R.id.rz2aquca7tfj);
        ImageView img8 = findViewById(R.id.rcdh7498hbs6);
        ImageView img9 = findViewById(R.id.r83wjkwg0gqa);
        ImageView img10 = findViewById(R.id.re10l7j9kboj);
        ImageView img11 = findViewById(R.id.r32l48hh0uzd);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b1daf17e-5496-4e30-8b63-e70e42fecdbe").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4a0f9639-1221-4cc2-9b9e-9bd19c7b1963").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ec4d0e65-9338-402f-a440-37ef6bf3c504").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5ec8adfb-b0fb-4bf9-ba3d-28cfd6abc9d5").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e07ce4f1-56e0-43f0-8513-cf0278646cb2").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a9cec662-5b7e-4f8f-98f3-d48134aa215c").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/749ed497-be8c-46ee-b6fd-8880021c62b6").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3fd461c8-9847-43e5-a044-57a87a68c7b2").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fd167c7d-981c-464f-ab8f-f336acc64b4a").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1b414cc4-2f96-43e8-aa37-53ac8939fa03").into(img10);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ae1b4256-39b0-43af-b408-ccc25754ff2b").into(img11);

        // TextWatcher for EditText
        EditText editText1 = findViewById(R.id.rpq5eze1imbr);
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
        View button1 = findViewById(R.id.rxj656fkzt2p);
        View button2 = findViewById(R.id.ru9os59brqco);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
    }
}
