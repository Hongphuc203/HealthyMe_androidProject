package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ProgressPhoto extends AppCompatActivity {
    private String editTextValue1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_photo);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.r9n19nnq87f);
        ImageView img2 = findViewById(R.id.rd50y24s3rp);
        ImageView img3 = findViewById(R.id.rnxftj6yt42q);
        ImageView img4 = findViewById(R.id.rhnyhmxth3eh);
        ImageView img5 = findViewById(R.id.rep83qycc64d);
        ImageView img6 = findViewById(R.id.r9oa52xq8cxt);
        ImageView img7 = findViewById(R.id.rc313tv8ycs);
        ImageView img8 = findViewById(R.id.rmtkq9e3g6bb);
        ImageView img9 = findViewById(R.id.rccnehdxnp8);
        ImageView img10 = findViewById(R.id.r0z2q8pdv2hs);
        ImageView img11 = findViewById(R.id.rggtmc8re3o9);
        ImageView img12 = findViewById(R.id.r57wkc015rk);
        ImageView img13 = findViewById(R.id.rqzb0df191ta);
        ImageView img14 = findViewById(R.id.rgt4w1ah3umm);
        ImageView img15 = findViewById(R.id.r9auxkvrevwm);
        ImageView img16 = findViewById(R.id.rkccdty1mfeo);
        ImageView img17 = findViewById(R.id.rcbyjaefo4y);
        ImageView img18 = findViewById(R.id.reeyl8c3d8db);
        ImageView img19 = findViewById(R.id.rnc39yr0vz4);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9d52c740-f383-4144-aea1-1207a2822661").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4feb9ce3-ec7d-4573-9ddb-e7615fcace57").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5b011d29-0216-40f5-8e58-8cc65aab2b1e").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/31869c58-505b-418c-91d9-8b04a14ec85e").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b6bf53a9-aea5-45a0-b063-2e76e879208b").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/833c0d12-e6cd-47b8-a2b5-a62e6ff289b8").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e130af5f-9ad8-4df3-92bb-02e76c7d3bd6").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/29ff0610-a9b5-4a26-95ed-fccd470657e5").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b63d6893-4fca-4c16-9943-13b6feb8a7c9").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/49db1c09-102f-40ca-a2f9-f876c0905d5c").into(img10);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/8ca119be-bae4-4b2d-aae5-6046422e1d6a").into(img11);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/669da02a-60f2-4249-a97a-115eb2cfa5c8").into(img12);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/581dcac5-1df3-472a-bbec-1656fa7a9654").into(img13);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/3d2553d2-b924-44c0-a09b-94c6925ed8b1").into(img14);
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(img15);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7589ab16-8550-4e24-8c9b-76a02119e46c").into(img16);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/91809cdb-28c2-4f21-ae01-add2e4964bb6").into(img17);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/56a7f8ec-bbd1-4d03-a8d3-98672b65a76e").into(img18);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/850cc33b-96b8-4aeb-a634-4167ef43842b").into(img19);

        // TextWatcher for EditText
        EditText editText1 = findViewById(R.id.rsxy75j64o5);
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
        View button1 = findViewById(R.id.rk47w45y22i);
        View button2 = findViewById(R.id.rmjn87hr7ufk);
        View button3 = findViewById(R.id.rpn6wie909id);

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
