package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Ép kiểu về ImageView trước khi dùng Glide
        ImageView img1 = findViewById(R.id.r1mz030ntwl3);
        ImageView img2 = findViewById(R.id.rvzvg1jq7tdf);
        ImageView img3 = findViewById(R.id.rr3jwrxmhax);
        ImageView img4 = findViewById(R.id.rtvqno2p3wjg);
        ImageView img5 = findViewById(R.id.rjsm9dls26wk);
        ImageView img6 = findViewById(R.id.r9xlqfegazm9);
        ImageView img7 = findViewById(R.id.r20c3sith3i1);
        ImageView img8 = findViewById(R.id.r05w64bqs7fli);
        ImageView img9 = findViewById(R.id.rrd3k0dq6t5h);
        ImageView img10 = findViewById(R.id.r9g8j3v8urqo);
        ImageView img11 = findViewById(R.id.rihbtdvjoua);
        ImageView img12 = findViewById(R.id.rx0mn812q73d);
        ImageView img13 = findViewById(R.id.rfw2ghs4ollv);
        ImageView img14 = findViewById(R.id.rbypz1cjn5ru);

        // Load ảnh bằng Glide
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7e0944d9-5638-40ed-87d7-ed3934118999").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a60342ff-d44e-46d1-834b-4ef84e4270a9").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0c14dce3-0622-46d9-b319-3c33a99b153c").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/2cf9036b-f25a-4987-842b-a9d131f59b1d").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bbbfa909-12a1-4dab-8d03-62785a1eb9e4").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/95069712-38a1-487d-b532-c1038d9704dd").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/98aefff2-db9a-473b-89ff-29e47b56cff9").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a6cda3bc-5f9e-4840-9fc4-7cfb85f979f5").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/43ffd817-ed69-4c25-88bd-1170f2247fcf").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0b6e49e6-c3f3-46b5-ae7d-a7227e7ee09e").into(img10);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b0561163-47da-4db8-8945-62a198942514").into(img11);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/fec7f8c9-cc2f-45a6-834b-f121c4c92645").into(img12);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/014ee00b-fa87-47e7-8e49-ddd2dd151d41").into(img13);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a10e842c-b58d-434f-a2dc-fb516929473b").into(img14);

        // Các nút
        View button1 = findViewById(R.id.rrr9a8s9vik);
        button1.setOnClickListener(v -> System.out.println("Pressed"));

        View button2 = findViewById(R.id.rww8kbypmor);
        button2.setOnClickListener(v -> System.out.println("Pressed"));
    }
}
