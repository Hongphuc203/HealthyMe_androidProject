package com.example.project;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * MyMarkerView sẽ hiển ngày (MM-DD) và số giờ ngủ tại điểm người dùng chạm.
 */
public class MyMarkerView extends MarkerView {

    private final TextView tvMarkerDate;
    private final TextView tvMarkerValue;

    public MyMarkerView(Context context) {
        super(context, R.layout.marker_view);
        // Bind 2 TextView từ layout marker_view.xml
        tvMarkerDate  = findViewById(R.id.tvMarkerDate);
        tvMarkerValue = findViewById(R.id.tvMarkerValue);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // e.getX() trả về chỉ số (index) của ngày, e.getY() là số giờ ngủ.
        // Giả sử chúng dùng labels trong activity để format X → "MM-DD".
        int index = (int) e.getX();
        float duration = e.getY();

        // Lấy danh sách ngày đã truyền vào Chart; ở đây chúng xử lý đơn giản
        // Nếu muốn mapping index → nhãn ngày chuẩn, bạn cần truyền labels từ Activity sang.
        // Trong ví dụ này, ta giả định labels = ["05-28", "05-29", ...] được gán tại Activity
        // và lưu vào một biến tĩnh dễ truy cập, ví dụ SleepTracker.LABELS.
        String dateLabel = SleepTracker.LABELS.get(index);

        tvMarkerDate.setText(dateLabel);

        // Format số giờ ngủ: ví dụ 7.5 → "7h 30m"
        int hours = (int) duration;
        int minutes = Math.round((duration - hours) * 60);
        tvMarkerValue.setText(hours + "h " + minutes + "m");

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Trả offset sao cho marker hiện ở trên điểm, giữ chính giữa bên dưới.
        // width = có thể lấy từ getWidth()
        float offsetX = - (getWidth() / 2f);
        float offsetY = - getHeight();
        return new MPPointF(offsetX, offsetY);
    }
}
