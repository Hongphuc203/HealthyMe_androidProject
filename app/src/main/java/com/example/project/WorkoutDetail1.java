package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class WorkoutDetail1 extends AppCompatActivity {

    private LinearLayout btnBackWorkOutDetail1;
    private ImageView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    private LinearLayout btnStartWorkout;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_workout_details1);

            btnBackWorkOutDetail1 = findViewById(R.id.btnBackWorkOutDetail1);
            // Gán view
            btn1 = findViewById(R.id.btn1);
            btn2 = findViewById(R.id.btn2);
            btn3 = findViewById(R.id.btn3);
            btn4 = findViewById(R.id.btn4);
            btn5 = findViewById(R.id.btn5);
            btn6 = findViewById(R.id.btn6);
            btn7 = findViewById(R.id.btn7);
            btn8 = findViewById(R.id.btn8);

            // Load images with Glide
            ImageView img1  = (ImageView) findViewById(R.id.r25sndngjqsg);
            ImageView img2  = (ImageView) findViewById(R.id.rtq70gegp3t);
            ImageView img4  = (ImageView) findViewById(R.id.r49lxmf1onmb);
            ImageView img5  = (ImageView) findViewById(R.id.rmq7ebw8c5ti);
            ImageView img6  = (ImageView) findViewById(R.id.rka1dxowlq9);
            ImageView img8  = (ImageView) findViewById(R.id.rbvjz9j7aikd);
            ImageView img10 = (ImageView) findViewById(R.id.rf7105cm8rts);
            ImageView img11 = (ImageView) findViewById(R.id.riuj13dav5mg);
            ImageView img12 = (ImageView) findViewById(R.id.riw5dnfnfc1c);
            ImageView img13 = (ImageView) findViewById(R.id.rw217gj1payf);
            ImageView img14 = (ImageView) findViewById(R.id.btn1);
            ImageView img15 = (ImageView) findViewById(R.id.r5zxur3v7o9d);
            ImageView img16 = (ImageView) findViewById(R.id.btn2);
            ImageView img17 = (ImageView) findViewById(R.id.ratnc52uz2m);
            ImageView img18 = (ImageView) findViewById(R.id.btn3);
            ImageView img19 = (ImageView) findViewById(R.id.r4vmy03esv59);
            ImageView img20 = (ImageView) findViewById(R.id.btn4);
            ImageView img21 = (ImageView) findViewById(R.id.rinvfbnywvc);
            ImageView img22 = (ImageView) findViewById(R.id.btn5);
            ImageView img23 = (ImageView) findViewById(R.id.re4an36xbud7);
            ImageView img24 = (ImageView) findViewById(R.id.btn6);
            ImageView img25 = (ImageView) findViewById(R.id.rqq830e15ov9);
            ImageView img26 = (ImageView) findViewById(R.id.btn7);
            ImageView img27 = (ImageView) findViewById(R.id.rtp4th0q58ed);
            ImageView img28 = (ImageView) findViewById(R.id.btn8);
            ImageView img29 = (ImageView) findViewById(R.id.rchso1mopveq);
            ImageView img30 = (ImageView) findViewById(R.id.ra9gs8js2aq);

            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/oou4bmia_expires_30_days.png").into(img1);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/7y1btzev_expires_30_days.png").into(img2);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/c936414q_expires_30_days.png").into(img4);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/phjjhan3_expires_30_days.png").into(img5);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/mwdhjjxb_expires_30_days.png").into(img6);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/c8a39njb_expires_30_days.png").into(img8);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/09edccbn_expires_30_days.png").into(img10);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/n4w7pa3v_expires_30_days.png").into(img11);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/a4ly5gad_expires_30_days.png").into(img12);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/ors9hd72_expires_30_days.png").into(img13);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/m3w8bv47_expires_30_days.png").into(img14);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/1vwaj0dr_expires_30_days.png").into(img15);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/rrkxyrsz_expires_30_days.png").into(img16);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/7b8z1ljj_expires_30_days.png").into(img17);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/hehce0hw_expires_30_days.png").into(img18);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/yxrlk6mc_expires_30_days.png").into(img19);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/9ygto3ao_expires_30_days.png").into(img20);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/jmevi01p_expires_30_days.png").into(img21);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/u3xgkogs_expires_30_days.png").into(img22);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/fzixb2if_expires_30_days.png").into(img23);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/0tpdo2x1_expires_30_days.png").into(img24);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/40ix6rwe_expires_30_days.png").into(img25);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/empkbjc7_expires_30_days.png").into(img26);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/2xkwrctl_expires_30_days.png").into(img27);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/0gytr8jy_expires_30_days.png").into(img28);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/sh651ilu_expires_30_days.png").into(img29);
            Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/5hfiEgF7x0/9yr1s7dz_expires_30_days.png").into(img30);

            // Set up buttons with click listeners
            btnBackWorkOutDetail1.setOnClickListener(v -> {
            Intent intent = new Intent(WorkoutDetail1.this, Work_Tracker.class);
            startActivity(intent);
            });

            // Đặt tất cả vào mảng
            ImageView[] buttons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8};

            // Gán sự kiện chung
            for (ImageView btn : buttons) {
                btn.setOnClickListener(v -> {
                    Intent intent = new Intent(this, WorkoutDetail2.class);
                    startActivity(intent);
                });
            }
        }
    }


