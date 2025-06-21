package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Map;
import java.util.HashMap;

public class LoginPage extends AppCompatActivity {


    LinearLayout btnLogin, btnLoginGG;
    TextView txtRegister, forgotPassword;
    EditText edtEmail, edtPassword;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            FirebaseFirestore.getInstance().collection("users")
//                    .document(currentUser.getUid())
//                    .get()
//                    .addOnSuccessListener(doc -> {
//                        if (doc.exists() && doc.contains("goal") && !doc.getString("goal").isEmpty()) {
//                            startActivity(new Intent(LoginPage.this, home.class));
//                        } else {
//                            startActivity(new Intent(LoginPage.this, ChooseWorkOutType.class));
//                        }
//                        finish(); // không quay lại Login khi nhấn back
//                    });
//            return;
//        }

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

        forgotPassword.setOnClickListener(v -> {
                    startActivity(new Intent(this, ForgotPassword.class));
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
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            DocumentReference docRef = db.collection("users").document(uid);

                            docRef.get().addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    String goal = documentSnapshot.getString("goal");

                                    if (goal != null && !goal.isEmpty()) {
                                        // Nếu đã chọn goal → về trang home
                                        startActivity(new Intent(LoginPage.this, home.class));
                                    } else {
                                        // Nếu chưa chọn goal → sang ChooseWorkOutType
                                        startActivity(new Intent(LoginPage.this, ChooseWorkOutType.class));
                                    }
                                } else {
                                    // Nếu là lần đầu → tạo mới document → sang Register2
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("fullName", user.getDisplayName());
                                    userData.put("email", user.getEmail());

                                    docRef.set(userData)
                                            .addOnSuccessListener(unused -> {
                                                Intent intent = new Intent(LoginPage.this, Register2.class);
                                                startActivity(intent);
                                            });
                                }
                                finish();
                            });
                        } else {
                            Toast.makeText(this, "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Cấu hình Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Đăng ký launcher cho kết quả từ Google SignIn
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            Toast.makeText(this, "Google sign in thất bại", Toast.LENGTH_SHORT).show();
                            Log.e("GOOGLE_LOGIN", "Lỗi: " + e.getMessage(), e);
                        }
                    }
                }
        );

        btnLoginGG.setOnClickListener(v -> {
            // Bắt buộc cho phép chọn lại tài khoản
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                googleSignInLauncher.launch(signInIntent);
            });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Google Sign In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        DocumentReference docRef = db.collection("users").document(uid);

                        docRef.get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                // Kiểm tra xem đã có đầy đủ thông tin chưa
                                String gender = documentSnapshot.getString("gender");
                                String birth = documentSnapshot.getString("birth");
                                String height = documentSnapshot.getString("goal");
                                String weight = documentSnapshot.getString("goal");
                                String goal = documentSnapshot.getString("goal");

                                if (gender == null || gender.isEmpty() || birth == null || birth.isEmpty() || height == null || height.isEmpty() || weight == null || weight.isEmpty()) {
                                    // Thiếu thông tin -> chuyển sang Register2
                                    Intent intent = new Intent(LoginPage.this, Register2.class);
                                    startActivity(intent);
                                } else if (goal == null || goal.isEmpty()) {
                                    // Chưa chọn goal -> chuyển sang ChooseWorkOutType
                                    Intent intent = new Intent(LoginPage.this, ChooseWorkOutType.class);
                                    startActivity(intent);
                                } else {
                                    // Nếu đã ầy đủ thông tin -> chuyển sang home
                                    Intent intent = new Intent(LoginPage.this, home.class);
                                    startActivity(intent);
                                }
                            } else {
                                // Lần đầu → tạo document + chuyển Register2
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("fullName", user.getDisplayName());
                                userData.put("email", user.getEmail());

                                docRef.set(userData)
                                        .addOnSuccessListener(unused -> {
                                            Intent intent = new Intent(LoginPage.this, Register2.class);
                                            startActivity(intent);
                                        });
                            }
                            finish();
                        });
                    } else {
                        Toast.makeText(this, "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
