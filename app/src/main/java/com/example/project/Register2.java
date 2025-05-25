package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project.R;

public class Register2 extends AppCompatActivity {

    private String editTextValue1 = "";
    private String editTextValue2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_2);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/703e6c74-f1fd-4837-a6a9-d59ca67998d4")
                .into((ImageView) findViewById(R.id.rhpvc3i0squa));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/10e4bcc6-01ec-4cf0-8eb9-721363e00223")
                .into((ImageView) findViewById(R.id.rep039uiaw7t));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1992b28f-d2c8-4e93-803c-0bb9ed2dda88")
                .into((ImageView) findViewById(R.id.rwdw90ffg7xh));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bcd08335-a640-4810-97df-61613978b40e")
                .into((ImageView) findViewById(R.id.rl6oiditcfvs));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0f501b68-a635-44be-9424-74f5f0f46802")
                .into((ImageView) findViewById(R.id.rgvos9pd44o9));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b94a21b2-b972-467f-bfe6-68b760748bd9")
                .into((ImageView) findViewById(R.id.rqa8xd6ycl4));

        EditText editText1 = findViewById(R.id.rawb2ngyvk3c);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // After text changed
            }
        });

        EditText editText2 = findViewById(R.id.rzbbzu6bxap);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // After text changed
            }
        });

        View button1 = findViewById(R.id.rx10bcvzog3e);
        button1.setOnClickListener(v -> System.out.println("Pressed"));

        View button2 = findViewById(R.id.rml4nh6nmv8);
        button2.setOnClickListener(v -> System.out.println("Pressed"));
    }
}
