package com.example.project;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;  // Thêm import này

public class Register3_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_3_3);

        // Ép kiểu rõ ràng để tránh lỗi Glide
        ShapeableImageView image1 = findViewById(R.id.royw81pi16t);
        ShapeableImageView image2 = findViewById(R.id.r7ger4iebdwp);

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/05e2062d-2cdb-400a-8230-f296ede2f594")
                .into(image1);

        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6f31d3ed-d9f3-4ab1-9041-9775adc66e12")
                .into(image2);

        View button1 = findViewById(R.id.rdmujg3n6spq);
        button1.setOnClickListener(v -> System.out.println("Pressed"));
    }
}
