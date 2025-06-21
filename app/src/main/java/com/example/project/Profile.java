package com.example.project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    LinearLayout btnBackProfile, btnInfoProfile, btnEdit, btnLogOut;
    EditText edtFullNameProfile, edtHeightProfile, edtWeightProfile;
    TextView txtAgeProfile;
    ImageView iconHome, iconWorkOut, iconCamera;
    Switch switchPopUpNoti;
    Spinner spinnerGoal;
    String[] goals = {"Lose A Fat", "Improve Shape","Lean & Tone"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBackProfile = findViewById(R.id.btnBackProfile);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnEdit = findViewById(R.id.btnEdit);
        edtFullNameProfile = findViewById(R.id.edtFullNameProfile);
        spinnerGoal = findViewById(R.id.spinnerGoal);
        edtHeightProfile = findViewById(R.id.edtHeightProfile);
        edtWeightProfile = findViewById(R.id.edtWeightProfile);
        txtAgeProfile = findViewById(R.id.txtAgeProfile);
        iconHome = findViewById(R.id.iconHome);
        iconWorkOut = findViewById(R.id.iconWorkOut);
        iconCamera = findViewById(R.id.iconCamera);
        switchPopUpNoti = findViewById(R.id.switchPopUpNoti);

        //Nav pane
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/50b84095-1b43-43f4-9d07-f0191ea8a231")
                .into((ImageView) findViewById(R.id.iconHome));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9fd92ea3-6821-4365-910b-0fdf7136423f")
                .into((ImageView) findViewById(R.id.iconWorkOut));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/56c717fd-0ebb-4ecd-887e-9e5703a547dc")
                .into((ImageView) findViewById(R.id.iconCamera));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/19a02c38-fc9e-4c71-9786-3725db697ba5")
                .into((ImageView) findViewById(R.id.iconProfile));
        // Back button
        ImageView backIcon = findViewById(R.id.rxqq26ubv85g);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/cb405007-cb92-406c-b6b7-cd65ee898fad").into(backIcon);

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

        btnBackProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, home.class);
            startActivity(intent);
        });

        btnBackProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, home.class);
            startActivity(intent);
        });

        //Log Out
        btnLogOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();  // Đăng xuất khỏi Firebase
            GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();


            // Quay về trang đăng nhập và xóa backstack
            Intent intent = new Intent(Profile.this, LoginPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Đóng activity hiện tại
        });

        //chuyển sang Profile
        iconHome.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, home.class);
            startActivity(intent);
        });

        //chuyển sang Work_Tracker
        iconWorkOut.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Work_Tracker.class);
            startActivity(intent);
        });

        //chuyển sang TakePhoto
        iconCamera.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, TakePhoto.class);
            startActivity(intent);
        });

        //switch pop up noti
        switchPopUpNoti.setOnClickListener(v -> {
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, goals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(adapter);

        btnEdit.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;

            String uid = user.getUid();

            // Lấy dữ liệu người dùng nhập
            String fullName = edtFullNameProfile.getText().toString().trim();

            long height = parseLongOrZero(edtHeightProfile.getText().toString());
            long weight = parseLongOrZero(edtWeightProfile.getText().toString());

            String goal = spinnerGoal.getSelectedItem().toString();
            if (goal != null) {
                int position = Arrays.asList(goals).indexOf(goal);
                if (position >= 0) {
                    spinnerGoal.setSelection(position);
                }
            }

            // Tạo map để cập nhật Firestore
            Map<String, Object> updates = new HashMap<>();
            updates.put("fullName", fullName);
            updates.put("goal", goal);
            updates.put("height", height);
            updates.put("weight", weight);

            FirebaseFirestore.getInstance().collection("users").document(uid)
                    .update(updates)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        // ✔️ Cập nhật lại giao diện có đơn vị sau khi lưu xong
                        edtHeightProfile.setText(height + "cm");
                        edtWeightProfile.setText(weight + "kg");
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });


        // Lấy thông tin từ Firebase Firestore
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // fullName
                            String fullName = documentSnapshot.getString("fullName");
                            if (fullName != null) {
                                edtFullNameProfile.setText(fullName);
                            }

                            // height (number -> int)
                            if (documentSnapshot.contains("height")) {
                                long height = documentSnapshot.getLong("height");
                                edtHeightProfile.setText(height + "cm");
                            }

                            // weight (number -> int)
                            if (documentSnapshot.contains("weight")) {
                                long weight = documentSnapshot.getLong("weight");
                                edtWeightProfile.setText(weight + "kg");
                            }

                            // birth (string -> tuổi)
                            String birth = documentSnapshot.getString("birth");
                            if (birth != null) {
                                int age = calculateAge(birth);
                                txtAgeProfile.setText(age + "yo");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("PROFILE", "Lỗi khi lấy dữ liệu người dùng: " + e.getMessage());
                    });
        }

    }

    private int calculateAge(String birthDateStr) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
                LocalDate currentDate = LocalDate.now();
                return Period.between(birthDate, currentDate).getYears();
            }
        } catch (Exception e) {
            Log.e("PROFILE", "Lỗi định dạng ngày sinh: " + e.getMessage());
        }
        return 0;
    }

    private long parseLongOrZero(String value) {
        try {
            // Loại bỏ chữ cm, kg, khoảng trắng
            value = value.replaceAll("[^\\d]", ""); // chỉ giữ lại số
            return Long.parseLong(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
