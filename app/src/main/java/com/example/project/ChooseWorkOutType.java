package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

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
            int idx = pager.getCurrentItem();
            // idx = 0,1,2 tuỳ bạn xử lý tiếp
        });
    }
}
