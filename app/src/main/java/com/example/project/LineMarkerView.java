package com.example.project;

import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class LineMarkerView extends MarkerView {
    private final TextView tvDate, tvValue;
    private final String[] labels;
    private final float[] values;

    /**
     * @param context  Context
     * @param labels   Mảng nhãn trục X (ví dụ {"Sun","Mon",...})
     * @param values   Mảng giá trị tương ứng (0–100)
     */
    public LineMarkerView(Context context, String[] labels, float[] values) {
        super(context, R.layout.marker_line);
        this.labels = labels;
        this.values = values;
        tvDate = findViewById(R.id.tvDate);
        tvValue = findViewById(R.id.tvValue);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int idx = (int) e.getX();
        if (idx >= 0 && idx < labels.length) {
            tvDate.setText(labels[idx]);
            tvValue.setText(String.format("%.0f%%", values[idx]));
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Đẩy tooltip lên trên và căn giữa theo chiều ngang
        return new MPPointF(-getWidth() / 2f, -getHeight() - 10);
    }
}
