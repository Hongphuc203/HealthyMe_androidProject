package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Map;
import java.util.HashMap;

public class LoginPage extends AppCompatActivity {


    LinearLayout btnLogin, btnLoginGG;
    TextView txtRegister, forgotPassword;
    EditText edtEmail, edtPassword;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginGG = findViewById(R.id.btnLoginGG);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Ép kiểu rõ ràng về ImageView để tránh lỗi ambiguous method call
        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);
        ImageView img4 = findViewById(R.id.img4);
        ImageView img5 = findViewById(R.id.img5);
        ImageView img6 = findViewById(R.id.img6);
        ImageView img7 = findViewById(R.id.img7);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5e7098a2-003f-401f-8cc3-b2c50c39829a").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/2b44e21f-a5d5-42d4-a55f-eec1345beec0").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/61962a0c-7ff4-4d8b-9bf4-5b5fee957887").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/45b65fca-0a37-4e7d-a312-12655ba81a23").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/0cc02715-9c52-4f58-8a77-6da2dc7e1be0").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/a4c74317-aa16-435f-b5c7-e1fdae1315c6").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/d14fe48b-26a5-4ea9-9999-18eb0f2c9632").into(img7);


        // Biến để theo dõi trạng thái hiển thị mật khẩu
        final boolean[] isPasswordVisible = {false};

        img3.setOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                // Nếu đang hiển thị -> ẩn đi
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                img3.setImageResource(R.drawable.visibility_on); // ẩn
            } else {
                // Nếu đang ẩn -> hiển thị
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                img3.setImageResource(R.drawable.visibility_off);   // hiện
            }
            // Di chuyển con trỏ về cuối chuỗi
            edtPassword.setSelection(edtPassword.length());
            isPasswordVisible[0] = !isPasswordVisible[0];
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi nhấn
                Toast.makeText(getApplicationContext(), "Đã nhấn Login", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện đăng nhập bằng email/password

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginPage.this, "Vui lòng nhập đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth = FirebaseAuth.getInstance();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, home.class));
                            finish();
                        } else {
                            Toast.makeText(LoginPage.this, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            Toast.makeText(this, "Lỗi Google Sign In: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // lấy từ google-services.json
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnLoginGG.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        });



        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(LoginPage.this, register.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginPage.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("email", user.getEmail());
                        userData.put("fullName", user.getDisplayName());

                        FirebaseFirestore.getInstance().collection("users")
                                .document(user.getUid())
                                .set(userData, SetOptions.merge())  // merge để không ghi đè
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Đăng nhập Google thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginPage.this, home.class));
                                    finish();
                                });
                    } else {
                        Toast.makeText(this, "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
