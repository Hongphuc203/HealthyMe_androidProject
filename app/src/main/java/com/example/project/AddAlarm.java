package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class AddAlarm extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.rqvsr6hwwyom);
        ImageView img2 = findViewById(R.id.rux5dw8xkxfp);
        ImageView img3 = findViewById(R.id.rlvxxdkwfal9);
        ImageView img4 = findViewById(R.id.r0rf45fm8gc59);
        ImageView img5 = findViewById(R.id.rmg2zexi1jf);
        ImageView img6 = findViewById(R.id.r88txiux8z89);
        ImageView img7 = findViewById(R.id.ri2qk5xn607);
        ImageView img8 = findViewById(R.id.rh4m7qhswoz9);
        ImageView img9 = findViewById(R.id.rla5cm6tja5);
        ImageView img10 = findViewById(R.id.r3faa1kretc9);

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1a273fa0-c516-40e3-8463-7b97f9b4175d")
                .into(img1);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7c657440-ac35-4147-a45b-41bdf7678581")
                .into(img2);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/262c0f98-cab6-4232-8a3f-e2195cc758b3")
                .into(img3);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/1063c0a0-352e-45e8-8a6a-1d97a2b04092")
                .into(img4);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5f71da67-865f-4af5-98f9-95673849b97b")
                .into(img5);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6cb8d19a-a10c-420b-83bf-d4b25d80abab")
                .into(img6);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/84b32262-5158-4796-afa3-8f14aa56eb37")
                .into(img7);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/372583f9-d782-412d-896b-553f76825f45")
                .into(img8);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/27e03b3e-2947-4bcf-bc62-5e674da2d31b")
                .into(img9);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a7c2d90c-5b64-4b5c-a945-efce629e0827")
                .into(img10);

        // TextWatcher for EditText
        EditText editText1 = findViewById(R.id.rvsw7gvo5r9i);
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
        View button1 = findViewById(R.id.rnunfnuq0nfb);
        View button2 = findViewById(R.id.rfg24yacflm4);
        View button3 = findViewById(R.id.AddButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
    }
}
