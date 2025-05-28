package com.example.project;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// 1. Adapter extends FragmentStateAdapter
public class RegisterPagerAdapter extends FragmentStateAdapter {
    // 3 layout con của bạn
    private final int[] layouts = {
            R.layout.improve_shape,
            R.layout.lean_and_tone,
            R.layout.lose_a_fat
    };

    public RegisterPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public androidx.fragment.app.Fragment createFragment(int position) {
        // Tạo ra fragment chỉ inflate layout tương ứng
        return PageFragment.newInstance(layouts[position]);
    }

    @Override
    public int getItemCount() {
        return layouts.length;
    }
}
