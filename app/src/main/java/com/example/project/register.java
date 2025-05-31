package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.auth.api.signin.*;

public class register extends AppCompatActivity {

    EditText edtNewFullName, edtNewPassword, edtNewPhoneNumber, edtNewEmail;
    LinearLayout btnRegister, btnRegisterGG;
    TextView txtLogin;
    CheckBox chkPolicy;
    ImageView hideIcon;


    private GoogleSignInClient googleSignInClient;
    private static final int RC_GOOGLE_SIGN_IN = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        edtNewFullName = findViewById(R.id.edtNewFullName);
        edtNewPhoneNumber = findViewById(R.id.edtNewPhoneNumber);
        edtNewEmail = findViewById(R.id.edtNewEmail);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        chkPolicy = findViewById(R.id.chkAcceptPolicy);
        hideIcon = findViewById(R.id.hideIcon);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        btnRegister.setEnabled(false);
        btnRegister.setAlpha(0.5f); // làm mờ khi chưa được chọn

        chkPolicy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnRegister.setEnabled(isChecked);
            btnRegister.setAlpha(isChecked ? 1f : 0.5f);
        });

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/hc706wbf_expires_30_days.png")
                .into((ImageView) findViewById(R.id.profileIcon));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/rzn4cjkz_expires_30_days.png")
                .into((ImageView) findViewById(R.id.phoneIcon));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/e7bli6hs_expires_30_days.png")
                .into((ImageView) findViewById(R.id.emailIcon));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/4aj87ln8_expires_30_days.png")
                .into((ImageView) findViewById(R.id.lockIcon));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/hsrr1t8v_expires_30_days.png")
                .into((ImageView) findViewById(R.id.hideIcon));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/ppazy1yj_expires_30_days.png")
                .into((ImageView) findViewById(R.id.leftLine));

        Glide.with(this).load("https://storage.googleapis.com/tagjs-prod.appspot.com/v1/wUwcU40zhP/yvjo6yns_expires_30_days.png")
                .into((ImageView) findViewById(R.id.rb6y5u5242xw));


        btnRegister.setOnClickListener(v -> {
            String fullName = edtNewFullName.getText().toString().trim();
            String phone = edtNewPhoneNumber.getText().toString().trim();
            String email = edtNewEmail.getText().toString().trim();
            String password = edtNewPassword.getText().toString().trim();

            if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty())  {
                Toast.makeText(register.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!chkPolicy.isChecked()) return;

            btnRegister.setEnabled(false);
            btnRegister.setAlpha(0.5f);
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();

                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", fullName);
                            user.put("phone", phone);
                            user.put("email", email);
                            user.put("goal", "");
                            user.put("gender", "");
                            user.put("dob", "");
                            user.put("weight", "");
                            user.put("height", "");

                            Log.d("DEBUG", "Đang ghi Firestore với: " + user);

                            try {
                                db.collection("users").document(uid)
                                        .set(user)
                                        .addOnSuccessListener(unused -> {
                                            runOnUiThread(() -> {
                                                progressBar.setVisibility(View.GONE);
                                                Intent intent = new Intent(register.this, Register2.class);
                                                startActivity(intent);
                                                finish();
                                            });
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(register.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            btnRegister.setEnabled(true);
                                            btnRegister.setAlpha(1f);
                                        });
                            } catch (Exception e) {
                                Log.e("FIRESTORE_EXCEPTION", " Lỗi khi gọi .set(): " + e.getMessage(), e);
                            }

                        } else {
                            Toast.makeText(register.this, "Lỗi đăng ký: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Biến để theo dõi trạng thái hiển thị mật khẩu
        final boolean[] isPasswordVisible = {false};

        hideIcon.setOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                // Nếu đang hiển thị -> ẩn đi
                edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                hideIcon.setImageResource(R.drawable.visibility_on); // ẩn
            } else {
                // Nếu đang ẩn -> hiển thị
                edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                hideIcon.setImageResource(R.drawable.visibility_off);   // hiện
            }
            // Di chuyển con trỏ về cuối chuỗi
            edtNewPassword.setSelection(edtNewPassword.length());
            isPasswordVisible[0] = !isPasswordVisible[0];
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(register.this, LoginPage.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(register.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
