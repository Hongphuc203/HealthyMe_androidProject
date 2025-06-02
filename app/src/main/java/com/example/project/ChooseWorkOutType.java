package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChooseWorkOutType extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_workout_type);

        ViewPager2 pager = findViewById(R.id.goalPager);
        // 1. Gắn adapter đã tạo ở bước trước
        pager.setAdapter(new RegisterPagerAdapter(this));
        pager.setOffscreenPageLimit(3);

        // 2. Cho phép “peeking” card bên cạnh
        pager.setClipToPadding(false);
        pager.setClipChildren(false);

        // 3. Thêm transformer margin + scale
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(30));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        pager.setPageTransformer(transformer);

        // 4. Xử lý nút Confirm
        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            int idx = pager.getCurrentItem(); // 0, 1, 2

            String goal;
            switch (idx) {
                case 0:
                    goal = "Improve Shape";
                    break;
                case 1:
                    goal = "Lean & Tone";
                    break;
                case 2:
                    goal = "Lose Fat";
                    break;
                default:
                    goal = "Unknown";
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("goal", goal);

                db.collection("users")
                        .document(user.getUid())
                        .update(data)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(this, "Lưu mục tiêu thành công", Toast.LENGTH_SHORT).show();
                            // Chuyển sang Home
                            startActivity(new Intent(ChooseWorkOutType.this, SuccessRegistration.class));
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Lỗi khi lưu mục tiêu", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
