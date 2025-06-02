package com.example.project;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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
import com.google.android.material.imageview.ShapeableImageView;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TakePhoto extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private PreviewView previewView;
    private ShapeableImageView flashButton;
    private ShapeableImageView captureButton;
    private ShapeableImageView rotateButton;
    private View frontButton, leftButton, backButton, rightButton;

    private ImageCapture imageCapture;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    private boolean flashEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        // === 1) Bind tất cả view ngay sau setContentView ===
        previewView   = findViewById(R.id.cameraPreview);
        flashButton   = findViewById(R.id.FlashButton);
        captureButton = findViewById(R.id.ButtonTakePhoto);
        rotateButton  = findViewById(R.id.RotateCam);
        frontButton   = findViewById(R.id.Front);
        leftButton    = findViewById(R.id.Left);
        backButton    = findViewById(R.id.Back);
        rightButton   = findViewById(R.id.Right);

        // === 2) (Tuỳ chọn) Load thêm hình user nếu cần ===
        // Ví dụ bạn muốn load cho 4 ảnh sample:
        ImageView img6 = findViewById(R.id.rb2wawm0zecw);
        ImageView img7 = findViewById(R.id.r7lb5e91w2qw);
        ImageView img8 = findViewById(R.id.rqch9rq3aays);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/33b85f34-31f4-4ab5-8954-c3b4a616debc")
                .into(img6);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/55460de2-33a8-4e66-aa93-c63c24db6c3a")
                .into(img7);
        Glide.with(this)
                .load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/72e235a9-2a2b-4d29-b510-7a0063745518")
                .into(img8);

        // === 3) Xin quyền CAMERA ===
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ Manifest.permission.CAMERA },
                    REQUEST_CAMERA_PERMISSION
            );
        } else {
            startCamera();
        }

        // === 4) Flash toggle ===
        flashButton.setOnClickListener(v -> {
            flashEnabled = !flashEnabled;
            if (imageCapture != null) {
                imageCapture.setFlashMode(
                        flashEnabled
                                ? ImageCapture.FLASH_MODE_ON
                                : ImageCapture.FLASH_MODE_OFF
                );
                // Cập nhật icon (drawable gán sẵn trong res/drawable)
                flashButton.setImageResource(
                        flashEnabled
                                ? R.drawable.flash_on
                                : R.drawable.flash_off
                );
            }
        });

        // === 5) Capture photo ===
        captureButton.setOnClickListener(v -> takePhoto());

        // === 6) Switch front/back camera ===
        rotateButton.setOnClickListener(v -> {
            cameraSelector = cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    ? CameraSelector.DEFAULT_FRONT_CAMERA
                    : CameraSelector.DEFAULT_BACK_CAMERA;
            startCamera();
        });

        // === 7) Bottom buttons demo ===
        frontButton.setOnClickListener(v ->
                Toast.makeText(this, "Front clicked", Toast.LENGTH_SHORT).show());
        leftButton.setOnClickListener(v ->
                Toast.makeText(this, "Left clicked",  Toast.LENGTH_SHORT).show());
        backButton.setOnClickListener(v ->
                Toast.makeText(this, "Back clicked",  Toast.LENGTH_SHORT).show());
        rightButton.setOnClickListener(v ->
                Toast.makeText(this, "Right clicked", Toast.LENGTH_SHORT).show());
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> providerFuture =
                ProcessCameraProvider.getInstance(this);

        providerFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = providerFuture.get();

                // Preview use-case
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // ImageCapture use-case
                imageCapture = new ImageCapture.Builder()
                        .setFlashMode(
                                flashEnabled
                                        ? ImageCapture.FLASH_MODE_ON
                                        : ImageCapture.FLASH_MODE_OFF
                        )
                        .build();

                // Unbind trước rồi bind lại
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                );
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        String name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(System.currentTimeMillis());
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + name);
        cv.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cv.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera");
        }

        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cv
                ).build();

        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(
                            @NonNull ImageCapture.OutputFileResults results
                    ) {
                        Uri uri = results.getSavedUri();
                        Toast.makeText(
                                TakePhoto.this,
                                "Saved: " + (uri != null ? uri : "unknown"),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    @Override
                    public void onError(
                            @NonNull ImageCaptureException exc
                    ) {
                        Toast.makeText(
                                TakePhoto.this,
                                "Error: " + exc.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            Toast.makeText(
                    this,
                    "Camera permission required",
                    Toast.LENGTH_LONG
            ).show();
            finish();
        }
    }
}
