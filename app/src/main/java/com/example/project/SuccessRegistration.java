package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SuccessRegistration extends AppCompatActivity {

    LinearLayout btnGoToHome;
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_registration);

        // Load success image with Glide
        ImageView img = findViewById(R.id.rc2vglbhhtb7);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/447b8811-9fee-4674-a954-a05ed1092083")
                .into(img);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnGoToHome = findViewById(R.id.btnGoToHome);

        // Lấy user hiện tại từ FirebaseAuth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("fullName");
                            if (fullName != null && !fullName.isEmpty()) {
                                txtWelcome.setText("Welcome, " + fullName);
                            } else {
                                txtWelcome.setText("Welcome!");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        txtWelcome.setText("Welcome!");
                        Log.e("USER_DATA", "Lỗi khi lấy dữ liệu người dùng: " + e.getMessage());
                    });
        } else {
            txtWelcome.setText("Welcome!");
        }

        btnGoToHome.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessRegistration.this, home.class);
            startActivity(intent);
        });
    }
}
