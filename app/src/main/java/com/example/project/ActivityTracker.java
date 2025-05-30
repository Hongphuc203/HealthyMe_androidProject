package com.example.project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ActivityTracker extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tracker);

        // Load images with Glide
        ImageView img1 = findViewById(R.id.r9vxk0xpx8bh);
        ImageView img2 = findViewById(R.id.rsvknvh3spf8);
        ImageView img3 = findViewById(R.id.rs953glnnor);
        ImageView img4 = findViewById(R.id.r8nibriua228);
        ImageView img5 = findViewById(R.id.ruxljn2fm4zq);
        ImageView img6 = findViewById(R.id.rbqmyoczpn6k);
        ImageView img7 = findViewById(R.id.rockynw9luub);
        ImageView img8 = findViewById(R.id.ryac7qjdxy7k);
        ImageView img9 = findViewById(R.id.r4ay1hoewdwh);
        ImageView img10 = findViewById(R.id.rc3wdu715gk);

        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/5629039c-4d1b-4556-b92a-8007f0e5a957").into(img1);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/532f76ab-369f-4bc5-83b2-21a0211afe7b").into(img2);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9ecf69c3-79ee-4208-8a48-70593f1718c5").into(img3);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/ad0324e4-ea64-4a55-854c-7683f94e6b1c").into(img4);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4875b3ce-99d3-433b-a175-93a71514b845").into(img5);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/61e78d25-68a6-4a1a-9f55-5017069f728d").into(img6);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/4afdda57-61d7-4300-9f70-f5432f745cf2").into(img7);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/c8483056-9d27-4a08-add1-a187a04fb013").into(img8);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/76bcc091-7d71-4703-b53b-69c83d741663").into(img9);
        Glide.with(this).load("https://figma-alpha-api.s3.us-west-2.amazonaws.com/images/9d90ee21-2ea2-41e9-b2f4-7c58a95b2d9c").into(img10);

        // Setup buttons with click listeners
        View button1 = findViewById(R.id.rwugnf75ag6);
        View button2 = findViewById(R.id.rzltaurndrn);
        View button3 = findViewById(R.id.rf3su2mq4qd);
        View button4 = findViewById(R.id.r3u6qoic76wf);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Pressed");
            }
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        BarChart barChart = findViewById(R.id.barChart);
        String[] days = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setGranularity(1f); // Bắt buộc để hiển thị đúng từng cột
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(12f);
        xAxis.setLabelCount(days.length);
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 25));
        entries.add(new BarEntry(1, 65));
        entries.add(new BarEntry(2, 50));
        entries.add(new BarEntry(3, 70));
        entries.add(new BarEntry(4, 85));
        entries.add(new BarEntry(5, 30));
        entries.add(new BarEntry(6, 80));

        BarDataSet dataSet = new BarDataSet(entries, "");
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#00FFB1"));
        colors.add(Color.parseColor("#BF72EF"));
        colors.add(Color.parseColor("#00FFB1"));
        colors.add(Color.parseColor("#BF72EF"));
        colors.add(Color.parseColor("#00FFB1"));
        colors.add(Color.parseColor("#BF72EF"));
        colors.add(Color.parseColor("#00FFB1"));
        dataSet.setColors(colors);

        BarData barData = new BarData(dataSet);
        barData.setDrawValues(false);
        barData.setBarWidth(0.8f);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setTouchEnabled(false);
        barChart.invalidate();

    }
}
