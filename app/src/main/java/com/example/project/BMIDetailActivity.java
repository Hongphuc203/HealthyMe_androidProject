package com.example.project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BMIDetailActivity extends AppCompatActivity {

    EditText edtHeight, edtWeight;
    Button btnCalculate;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_detail);

        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);

        btnCalculate.setOnClickListener(v -> {
            try {
                float height = Float.parseFloat(edtHeight.getText().toString());
                float weight = Float.parseFloat(edtWeight.getText().toString());

                if (height <= 0 || weight <= 0) {
                    txtResult.setText("Chiều cao và cân nặng phải lớn hơn 0.");
                    return;
                }

                float bmi = weight / ((height / 100f) * (height / 100f));
                String category;

                if (bmi < 18.5f) {
                    category = "Thiếu cân";
                } else if (bmi < 25f) {
                    category = "Bình thường";
                } else if (bmi < 30f) {
                    category = "Thừa cân";
                } else {
                    category = "Béo phì";
                }

                txtResult.setText(String.format("BMI: %.1f\nTình trạng: %s", bmi, category));
            } catch (Exception e) {
                txtResult.setText("Vui lòng nhập đúng định dạng số.");
            }
        });
    }
}
