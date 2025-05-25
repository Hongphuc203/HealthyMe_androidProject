package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class LoginPage extends AppCompatActivity {

    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Ép kiểu rõ ràng về ImageView để tránh lỗi ambiguous method call
        ImageView img1 = findViewById(R.id.rm9gx8tna7qr);
        ImageView img2 = findViewById(R.id.ridf6rzh4fx);
        ImageView img3 = findViewById(R.id.rf7w61n68etu);
        ImageView img4 = findViewById(R.id.ri63vs72r6vl);
        ImageView img5 = findViewById(R.id.rdpbxnncp84m);
        ImageView img6 = findViewById(R.id.rlsujbrsp6y);
        ImageView img7 = findViewById(R.id.r82m4wguhbh);
        ImageView img8 = findViewById(R.id.r0093rj4wtn8zb);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5e7098a2-003f-401f-8cc3-b2c50c39829a").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/2b44e21f-a5d5-42d4-a55f-eec1345beec0").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/61962a0c-7ff4-4d8b-9bf4-5b5fee957887").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/45b65fca-0a37-4e7d-a312-12655ba81a23").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0cc02715-9c52-4f58-8a77-6da2dc7e1be0").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a4c74317-aa16-435f-b5c7-e1fdae1315c6").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d14fe48b-26a5-4ea9-9999-18eb0f2c9632").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6bb4f200-217f-47d8-b87f-734803a9c054").into(img8);

        EditText editText1 = findViewById(R.id.ryujd5w5degp);
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

        View button1 = findViewById(R.id.rriou8zz9xb);
        button1.setOnClickListener(v -> System.out.println("Pressed"));

        View button2 = findViewById(R.id.rwt2nem0tk7);
        button2.setOnClickListener(v -> System.out.println("Pressed"));

        View button3 = findViewById(R.id.rmfd4wcx9iu);
        button3.setOnClickListener(v -> System.out.println("Pressed"));
    }
}
