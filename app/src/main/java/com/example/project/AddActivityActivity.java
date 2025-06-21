package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddActivityActivity extends AppCompatActivity {

    private LinearLayout btnBackAddActivity;
    private Spinner spinnerActivityType;
    private EditText edtActivityValue;
    private TextView txtActivityUnit;
    private Button btnSaveActivity;

    private final Map<String, String> unitMap = new HashMap<String, String>() {{
        put("WATER", "ml");
        put("WALK", "steps");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        spinnerActivityType = findViewById(R.id.spinnerActivityType);
        edtActivityValue = findViewById(R.id.edtActivityValue);
        txtActivityUnit = findViewById(R.id.txtActivityUnit);
        btnSaveActivity = findViewById(R.id.btnSaveActivity);
        btnBackAddActivity = findViewById(R.id.btnBackAddActivity);

        // Back button
        ImageView backIcon = findViewById(R.id.rxqq26ubv85g);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/cb405007-cb92-406c-b6b7-cd65ee898fad").into(backIcon);

        btnBackAddActivity.setOnClickListener(v -> {
            Intent intent = new Intent(AddActivityActivity.this, ActivityTracker.class);
            startActivity(intent);
        });

        String[] activityTypes = {"WATER", "WALK"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, activityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityType.setAdapter(adapter);

        // Cập nhật đơn vị khi chọn loại hoạt động
        spinnerActivityType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = spinnerActivityType.getSelectedItem().toString();
                txtActivityUnit.setText(unitMap.getOrDefault(type, ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnSaveActivity.setOnClickListener(v -> {
            String type = spinnerActivityType.getSelectedItem().toString();
            String valueStr = edtActivityValue.getText().toString().trim();
            String unit = unitMap.getOrDefault(type, "");

            if (valueStr.isEmpty()) {
                Toast.makeText(this, "Please enter a value.", Toast.LENGTH_SHORT).show();
                return;
            }

            long value = Long.parseLong(valueStr);
            String title = "";
            switch (type) {
                case "WATER":
                    title = "Drank " + value + " " + unit;
                    break;
                case "WALK":
                    title = "Walked " + value + " " + unit;
                    break;
                case "FOOD":
                    title = "Ate " + value + " " + unit;
                    break;
            }
            long timestamp = System.currentTimeMillis();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) return;

            Map<String, Object> activityData = new HashMap<>();
            activityData.put("type", type);
            activityData.put("value", value);
            activityData.put("unit", unit);
            activityData.put("title", title);
            activityData.put("timestamp", timestamp);

            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.getUid())
                    .collection("activities")
                    .add(activityData)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "Activity saved!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
