package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Nav pane
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/50b84095-1b43-43f4-9d07-f0191ea8a231")
                .into((ImageView) findViewById(R.id.rowsxmfom8m9));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9fd92ea3-6821-4365-910b-0fdf7136423f")
                .into((ImageView) findViewById(R.id.r6o3g3kmue9p));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/56c717fd-0ebb-4ecd-887e-9e5703a547dc")
                .into((ImageView) findViewById(R.id.rcsf7132kocp));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/19a02c38-fc9e-4c71-9786-3725db697ba5")
                .into((ImageView) findViewById(R.id.r87jxozk86sd));
        // Back button
        View backButton = findViewById(R.id.rc8ozhtr6wg);
        ImageView backIcon = findViewById(R.id.rxqq26ubv85g);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/cb405007-cb92-406c-b6b7-cd65ee898fad").into(backIcon);

        // Menu button
        View menuButton = findViewById(R.id.ru9lw1e90rxh);
        ImageView menuIcon = findViewById(R.id.r79k1xoiwzrb);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/280200f1-b7e0-4763-8c37-639623dccaf3").into(menuIcon);

        // Profile avatar
        ImageView avatar = findViewById(R.id.rbtff2iqiw9o);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/8c135b15-4d39-473d-85bb-2a39222bc2af").into(avatar);

        // Edit icon
        ImageView personalDataArrow = findViewById(R.id.rvb0yeu0irfr);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/c4f8d59d-d608-46fb-bcfc-a69128c88ba2").into(personalDataArrow);

        ImageView achievementIcon = findViewById(R.id.r5p8bkkgk055);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/e7e2964e-0d2c-46bb-988a-8428422d72be").into(achievementIcon);

        ImageView activityHistoryIcon = findViewById(R.id.rstm3k8r6lxr);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bf5d1e6f-5e8a-4c94-adcb-0ad8afc4a511").into(activityHistoryIcon);

        ImageView workoutProgressIcon = findViewById(R.id.r6lyboqsz1ek);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/457abe67-0767-4fc0-b73f-e21b7e2d9708").into(workoutProgressIcon);

        ImageView notificationIcon = findViewById(R.id.r10v9lcor7isr);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0c412dcd-ce1a-4ff5-bda6-204ae2958734").into(notificationIcon);

        ImageView popupToggle = findViewById(R.id.rg6x303crs9v);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/7c6b5ddf-c741-45ea-8740-032cbfd4c6f1").into(popupToggle);

        ImageView contactUsIcon = findViewById(R.id.rguyskybl7ba);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b03900c3-9280-458f-98ff-3a2131f05a1c").into(contactUsIcon);

        ImageView contactUsArrow = findViewById(R.id.r7tjpkiry1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5c99ac1f-c390-40e0-b4e9-117b520322c4").into(contactUsArrow);

        ImageView privacyIcon = findViewById(R.id.rqju03vlerj);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/6acf5bf3-77d2-49e0-9ff0-2af32c29e878").into(privacyIcon);

        ImageView privacyArrow = findViewById(R.id.rrrvls7ob9l);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5112a8d2-159f-4416-9dcc-f95445e08d1a").into(privacyArrow);

        ImageView settingsIcon = findViewById(R.id.rsyho5us7cop);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a08cf753-e08f-452c-9692-e8673fd45774").into(settingsIcon);

        ImageView settingsArrow = findViewById(R.id.rjsyovvos2je);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b8d8288b-82c8-431d-8fcc-7167877e6964").into(settingsArrow);

        // Example: setOnClickListener
        View heightLayout = findViewById(R.id.Height);
        heightLayout.setOnClickListener(v -> {
            // Do something
            System.out.println("Height clicked");
        });

        View weightLayout = findViewById(R.id.Weight);
        weightLayout.setOnClickListener(v -> {
            System.out.println("Weight clicked");
        });

        View ageLayout = findViewById(R.id.Age);
        ageLayout.setOnClickListener(v -> {
            System.out.println("Age clicked");
        });
    }
}
