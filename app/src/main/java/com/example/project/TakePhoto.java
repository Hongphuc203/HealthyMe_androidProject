package com.example.project;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TakePhoto extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private static final String PREFS_NAME = "MyImagePrefs";

    private PreviewView previewView;
    private ShapeableImageView flashButton, captureButton, rotateButton;
    private View frontButton, leftButton, backButton, rightButton;
    private ImageView frontBg, leftBg, backBg, rightBg;

    private String currentTarget = "front";
    private boolean flashEnabled = false;

    private ImageCapture imageCapture;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

    // Lưu URI ảnh lần trước và lần này cho từng hướng
    private Uri lastFrontUri = null;
    private Uri currentFrontUri = null;
    private Uri lastLeftUri = null;
    private Uri currentLeftUri = null;
    private Uri lastBackUri = null;
    private Uri currentBackUri = null;
    private Uri lastRightUri = null;
    private Uri currentRightUri = null;
    Button btnToCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        btnToCompare = findViewById(R.id.btnToCompare);
        btnToCompare.setOnClickListener(v -> {
            Intent intent = new Intent(this, CompareResult1.class);
            startActivity(intent);
        });

        bindViews();
        setupButtonListeners();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }

        // Load ảnh mẫu cho các vùng trừ front
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/33b85f34-31f4-4ab5-8954-c3b4a616debc")
                .into(leftBg);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/55460de2-33a8-4e66-aa93-c63c24db6c3a")
                .into(backBg);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/72e235a9-2a2b-4d29-b510-7a0063745518")
                .into(rightBg);

        // Đọc URI đã lưu từ SharedPreferences (nếu có) để khởi tạo last/current trước khi chụp
        loadUrisFromPrefs();
    }

    private void bindViews() {
        previewView   = findViewById(R.id.cameraPreview);
        flashButton   = findViewById(R.id.FlashButton);
        captureButton = findViewById(R.id.ButtonTakePhoto);
        rotateButton  = findViewById(R.id.RotateCam);

        frontButton   = findViewById(R.id.Front);
        leftButton    = findViewById(R.id.Left);
        backButton    = findViewById(R.id.Back);
        rightButton   = findViewById(R.id.Right);

        frontBg = findViewById(R.id.ImagOfFront);
        leftBg  = findViewById(R.id.ImagOfLeft);
        backBg  = findViewById(R.id.ImagOfBack);
        rightBg = findViewById(R.id.ImagOfRight);
    }

    private void setupButtonListeners() {
        frontButton.setOnClickListener(v -> {
            currentTarget = "front";
            Toast.makeText(this, "Front selected", Toast.LENGTH_SHORT).show();
        });

        leftButton.setOnClickListener(v -> {
            currentTarget = "left";
            Toast.makeText(this, "Left selected", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            currentTarget = "back";
            Toast.makeText(this, "Back selected", Toast.LENGTH_SHORT).show();
        });

        rightButton.setOnClickListener(v -> {
            currentTarget = "right";
            Toast.makeText(this, "Right selected", Toast.LENGTH_SHORT).show();
        });

        flashButton.setOnClickListener(v -> {
            flashEnabled = !flashEnabled;
            if (imageCapture != null) {
                imageCapture.setFlashMode(flashEnabled
                        ? ImageCapture.FLASH_MODE_ON
                        : ImageCapture.FLASH_MODE_OFF);
                flashButton.setImageResource(flashEnabled
                        ? R.drawable.flash_on
                        : R.drawable.flash_off);
            }
        });

        captureButton.setOnClickListener(v -> takePhoto());

        rotateButton.setOnClickListener(v -> {
            cameraSelector = (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                    ? CameraSelector.DEFAULT_FRONT_CAMERA
                    : CameraSelector.DEFAULT_BACK_CAMERA;
            startCamera();
        });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setFlashMode(flashEnabled
                                ? ImageCapture.FLASH_MODE_ON
                                : ImageCapture.FLASH_MODE_OFF)
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(System.currentTimeMillis());

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + name);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera");
        }

        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
        ).build();

        imageCapture.takePicture(options, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults results) {
                        Uri uri = results.getSavedUri();
                        if (uri != null) {
                            switch (currentTarget) {
                                case "front":
                                    if (currentFrontUri != null) {
                                        lastFrontUri = currentFrontUri;
                                    }
                                    currentFrontUri = uri;
                                    saveUrisToPrefs("front", lastFrontUri, currentFrontUri);
                                    break;
                                case "left":
                                    if (currentLeftUri != null) {
                                        lastLeftUri = currentLeftUri;
                                    }
                                    currentLeftUri = uri;
                                    saveUrisToPrefs("left", lastLeftUri, currentLeftUri);
                                    break;
                                case "back":
                                    if (currentBackUri != null) {
                                        lastBackUri = currentBackUri;
                                    }
                                    currentBackUri = uri;
                                    saveUrisToPrefs("back", lastBackUri, currentBackUri);
                                    break;
                                case "right":
                                    if (currentRightUri != null) {
                                        lastRightUri = currentRightUri;
                                    }
                                    currentRightUri = uri;
                                    saveUrisToPrefs("right", lastRightUri, currentRightUri);
                                    break;
                            }
                            applyImageToTarget(uri);
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(TakePhoto.this,
                                "Capture failed: " + exception.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Lưu URI "lần trước" và "lần này" cho một target (front/left/back/right)
     * vào SharedPreferences với key "<target>_last" và "<target>_current".
     */
    private void saveUrisToPrefs(String target, Uri lastUri, Uri currentUri) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (lastUri != null) {
            editor.putString(target + "_last", lastUri.toString());
        } else {
            editor.remove(target + "_last");
        }
        editor.putString(target + "_current", currentUri.toString());
        editor.apply();
    }

    /**
     * Đọc các URI đã lưu trong SharedPreferences vào biến lastXxxUri và currentXxxUri
     * để khi mở lại màn hình chụp, biến vẫn giữ URI cũ.
     */
    private void loadUrisFromPrefs() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String frontLastStr    = prefs.getString("front_last", null);
        String frontCurrentStr = prefs.getString("front_current", null);
        if (frontLastStr != null)  lastFrontUri    = Uri.parse(frontLastStr);
        if (frontCurrentStr != null) currentFrontUri = Uri.parse(frontCurrentStr);

        String leftLastStr    = prefs.getString("left_last", null);
        String leftCurrentStr = prefs.getString("left_current", null);
        if (leftLastStr != null)  lastLeftUri    = Uri.parse(leftLastStr);
        if (leftCurrentStr != null) currentLeftUri = Uri.parse(leftCurrentStr);

        String backLastStr    = prefs.getString("back_last", null);
        String backCurrentStr = prefs.getString("back_current", null);
        if (backLastStr != null)  lastBackUri    = Uri.parse(backLastStr);
        if (backCurrentStr != null) currentBackUri = Uri.parse(backCurrentStr);

        String rightLastStr    = prefs.getString("right_last", null);
        String rightCurrentStr = prefs.getString("right_current", null);
        if (rightLastStr != null)  lastRightUri    = Uri.parse(rightLastStr);
        if (rightCurrentStr != null) currentRightUri = Uri.parse(rightCurrentStr);
    }

    private void applyImageToTarget(Uri uri) {
        Glide.with(this)
                .load(uri)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                Transition<? super Drawable> transition) {
                        switch (currentTarget) {
                            case "front":
                                frontBg.setBackground(null);
                                frontBg.setBackground(resource);
                                break;
                            case "left":
                                leftBg.setBackground(null);
                                leftBg.setBackground(resource);
                                leftBg.setImageResource(0);
                                break;
                            case "back":
                                backBg.setBackground(null);
                                backBg.setBackground(resource);
                                backBg.setImageResource(0);
                                break;
                            case "right":
                                rightBg.setBackground(null);
                                rightBg.setBackground(resource);
                                rightBg.setImageResource(0);
                                break;
                        }
                    }

                    @Override
                    public void onLoadCleared(@NonNull Drawable placeholder) {
                        // không cần xử lý gì thêm
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(this, "Cần quyền truy cập camera", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
