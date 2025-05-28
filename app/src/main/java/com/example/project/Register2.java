package com.example.project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page_2);

        EditText edtGender = findViewById(R.id.edtGender);

        edtGender.setFocusable(false);
        edtGender.setClickable(true);

        edtGender.setOnClickListener(v -> {
            String[] genders = {"Nam", "Nữ", "Khác"};

            new AlertDialog.Builder(Register2.this)
                    .setTitle("Chọn giới tính")
                    .setItems(genders, (dialog, which) -> {
                        edtGender.setText(genders[which]);
                    })
                    .show();
        });
        EditText edtBirth = findViewById(R.id.edtBirth);
        EditText edtWeight = findViewById(R.id.edtWeight); // "KG"
        EditText edtHeight = findViewById(R.id.edtHeight);  // "CM"

        LinearLayout btnConfirm = findViewById(R.id.btnConfirm); // Nút "Confirm"

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/703e6c74-f1fd-4837-a6a9-d59ca67998d4")
                .into((ImageView) findViewById(R.id.rhpvc3i0squa));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/10e4bcc6-01ec-4cf0-8eb9-721363e00223")
                .into((ImageView) findViewById(R.id.rep039uiaw7t));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/bcd08335-a640-4810-97df-61613978b40e")
                .into((ImageView) findViewById(R.id.rl6oiditcfvs));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0f501b68-a635-44be-9424-74f5f0f46802")
                .into((ImageView) findViewById(R.id.rgvos9pd44o9));
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/b94a21b2-b972-467f-bfe6-68b760748bd9")
                .into((ImageView) findViewById(R.id.rqa8xd6ycl4));

        // Mở DatePicker cho ngày sinh
        edtBirth.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Register2.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String birth = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                        edtBirth.setText(birth);
                    },
                    year, month, day
            );

            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        // Xử lý khi nhấn Confirm
        btnConfirm.setOnClickListener(v -> {
            String birth = edtBirth.getText().toString().trim();
            String weight = edtWeight.getText().toString().trim();
            String height = edtHeight.getText().toString().trim();
            String gender = edtGender.getText().toString().trim();

            // --- Kiểm tra rỗng ---
            if (birth.isEmpty() || weight.isEmpty() || height.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Kiểm tra ngày sinh hợp lệ ---
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Calendar now = Calendar.getInstance();
            int age;

            try {
                Date parsedBirth = sdf.parse(birth);
                if (parsedBirth == null) throw new Exception();

                Calendar birthCal = Calendar.getInstance();
                birthCal.setTime(parsedBirth);

                age = now.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
                    age--; // Trừ thêm nếu chưa đến sinh nhật trong năm
                }

                if (age < 10 || age > 100) {
                    Toast.makeText(this, "Tuổi phải từ 10 đến 100", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Kiểm tra cân nặng ---
            int weightVal;
            try {
                weightVal = Integer.parseInt(weight);
                if (weightVal < 20 || weightVal > 200) {
                    Toast.makeText(this, "Cân nặng phải từ 20kg đến 200kg", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Cân nặng phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Kiểm tra chiều cao ---
            int heightVal;
            try {
                heightVal = Integer.parseInt(height);
                if (heightVal < 100 || heightVal > 250) {
                    Toast.makeText(this, "Chiều cao phải từ 100cm đến 250cm", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Chiều cao phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Ghi dữ liệu lên Firestore ---
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String, Object> updateData = new HashMap<>();
            updateData.put("birth", birth);
            updateData.put("weight", weightVal);
            updateData.put("height", heightVal);
            updateData.put("gender", gender); // có thể chuẩn hóa ("Nam" → "male") nếu cần

            FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .update(updateData)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register2.this, Register3_1.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }
}
