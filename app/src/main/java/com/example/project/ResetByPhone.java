package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ResetByPhone extends AppCompatActivity {
    EditText edtPhone, edtOtp;
    Button btnSendOtp, btnVerifyOtp;
    String mVerificationId;

    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_phone);

        edtPhone = findViewById(R.id.edtPhone);
        edtOtp = findViewById(R.id.edtOtp);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        btnSendOtp.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString();
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                            .setPhoneNumber("+84" + phone.substring(1)) // Việt Nam
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(this)
                            .setCallbacks(callbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        });

        btnVerifyOtp.setOnClickListener(v -> {
            String code = edtOtp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> {
                        Toast.makeText(this, "Xác thực thành công", Toast.LENGTH_SHORT).show();
                        // Có thể cho user tạo mật khẩu mới ở đây
                        Intent intent = new Intent(ResetByPhone.this, ResetPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    // Tự động xác thực
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnSuccessListener(authResult -> {
                                Toast.makeText(ResetByPhone.this, "Tự động xác thực thành công", Toast.LENGTH_SHORT).show();
                            });
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(ResetByPhone.this, "Lỗi xác thực: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    mVerificationId = verificationId;
                    mResendToken = token;
                    edtOtp.setVisibility(View.VISIBLE);
                    btnVerifyOtp.setVisibility(View.VISIBLE);
                    Toast.makeText(ResetByPhone.this, "Mã OTP đã được gửi", Toast.LENGTH_SHORT).show();
                }
            };
}
