package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class home extends AppCompatActivity {

    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/757fbe1d-dbe0-42c5-b235-f05c070e2a29")
                .into((ImageView) findViewById(R.id.rcx11dlm7acp));
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png")
                .into((ImageView) findViewById(R.id.rpfxx7lezwc8));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a8073a2b-c92c-4c08-a877-c8a87d82ce51")
                .into((ImageView) findViewById(R.id.rqfh33exgd8));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/736ad19b-f0a2-4439-9f65-e083c8db71f7")
                .into((ImageView) findViewById(R.id.rvh3ymdu7rro));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/50b84095-1b43-43f4-9d07-f0191ea8a231")
                .into((ImageView) findViewById(R.id.rowsxmfom8m9));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9fd92ea3-6821-4365-910b-0fdf7136423f")
                .into((ImageView) findViewById(R.id.r6o3g3kmue9p));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/56c717fd-0ebb-4ecd-887e-9e5703a547dc")
                .into((ImageView) findViewById(R.id.rcsf7132kocp));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/19a02c38-fc9e-4c71-9786-3725db697ba5")
                .into((ImageView) findViewById(R.id.r87jxozk86sd));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ff9399ad-c653-45a1-835d-0df02551bee1")
                .into((ImageView) findViewById(R.id.r8hnwcsf05l8));

        EditText editText1 = findViewById(R.id.rx419djg29l8);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Trước khi thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextValue1 = s.toString();  // Khi thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Sau khi thay đổi
            }
        });

        View button1 = findViewById(R.id.ru9hybzsdm4c);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        });

        View button2 = findViewById(R.id.Viewmore);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        });
    }
}
