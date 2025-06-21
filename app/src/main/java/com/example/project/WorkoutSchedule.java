package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutSchedule extends AppCompatActivity {

    private View dimView;
    private LinearLayout openBox, mainContent;
    private RecyclerView rvDays;
    private DayAdapter adapter;
    private TextView popupTitle, popupDateTime;

    private WorkOutTask currentTask;
    private EditText currentTaskView;
    private String selectedDate;

    private final int[] currentYearMonth = {
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1
    };

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_schedule);

        dimView     = findViewById(R.id.dimView);
        openBox     = findViewById(R.id.OpenBox);
        mainContent = findViewById(R.id.main);
        popupTitle = findViewById(R.id.textPopupTitle);
        popupDateTime = findViewById(R.id.textPopupDateTime);

        dimView.setVisibility(View.GONE);
        openBox.setVisibility(View.GONE);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        selectedDate = sdf.format(new Date());

        findViewById(R.id.ButtonDone).setOnClickListener(v -> hideOverlay());
        findViewById(R.id.CloseButton).setOnClickListener(v -> hideOverlay());

        findViewById(R.id.MarkDone).setOnClickListener(v -> {
            if (currentTask != null && !currentTask.isDone()) {
                int id = currentTask.getId();
                new WorkoutRepository(this).updateDoneStatus(id, true);

                // Cập nhật trạng thái và giao diện ngay lập tức
                currentTask.setDone(true);
                if (currentTaskView != null) {
                    currentTaskView.setBackgroundResource(R.drawable.bg_task_done);
                    currentTaskView.setTextColor(Color.parseColor("#A5A3AF"));
                }

                // Làm mới biến
                currentTask = null;
                currentTaskView = null;

                // Nếu bạn vẫn muốn refresh toàn bộ timeline:
                // populateTimeline(); (có thể bỏ)
                hideOverlay();
            } else {
                hideOverlay();
            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        WorkOutTask task = (WorkOutTask) result.getData().getSerializableExtra("newTask");
                        populateTimeline();
                    }
                }
        );

        rvDays = findViewById(R.id.rvDays);
        rvDays.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new DayAdapter(generateDaysForMonth(currentYearMonth[0], currentYearMonth[1]), date -> {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdfDate.format(date.getTime());
            adapter.setSelectedDate(selectedDate);
            adapter.notifyDataSetChanged();
            populateTimeline();
        });
        adapter.setSelectedDate(selectedDate);
        rvDays.setAdapter(adapter);

        findViewById(R.id.PreviousMonth).setOnClickListener(v -> {
            if (currentYearMonth[1] == 1) {
                currentYearMonth[0]--;
                currentYearMonth[1] = 12;
            } else currentYearMonth[1]--;
            reloadMonth();
        });

        findViewById(R.id.NextMonth).setOnClickListener(v -> {
            if (currentYearMonth[1] == 12) {
                currentYearMonth[0]++;
                currentYearMonth[1] = 1;
            } else currentYearMonth[1]++;
            reloadMonth();
        });

        reloadMonth();
        populateTimeline();

        findViewById(R.id.ButtonAdd).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWorkoutSchedule.class);
            launcher.launch(intent);
        });
    }

    private void showOverlay() {
        dimView.setVisibility(View.VISIBLE);
        openBox.setVisibility(View.VISIBLE);
        mainContent.setEnabled(false);
    }

    private void hideOverlay() {
        dimView.setVisibility(View.GONE);
        openBox.setVisibility(View.GONE);
        mainContent.setEnabled(true);
    }

    private List<Calendar> generateDaysForMonth(int year, int month) {
        List<Calendar> days = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int day = 1; day <= maxDay; day++) {
            Calendar c = Calendar.getInstance();
            c.set(year, month - 1, day);
            days.add(c);
        }
        return days;
    }

    private void reloadMonth() {
        TextView tvMonth = findViewById(R.id.MonthChoosen);
        Calendar temp = Calendar.getInstance();
        temp.set(currentYearMonth[0], currentYearMonth[1] - 1, 1);

        String monthName = temp.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String header = monthName + " " + currentYearMonth[0];
        tvMonth.setText(header);

        List<Calendar> days = generateDaysForMonth(currentYearMonth[0], currentYearMonth[1]);
        adapter.setDays(days);
    }

    private void populateTimeline() {
        LinearLayout timelineContainer = findViewById(R.id.timelineContainer);
        timelineContainer.removeAllViews();

        for (int hour = 0; hour < 24; hour++) {
            TextView label = new TextView(this);
            String ampm = (hour < 12) ? "AM" : "PM";
            int displayHour = (hour % 12 == 0) ? 12 : (hour % 12);
            String labelText = String.format(Locale.getDefault(), "%02d:00 %s", displayHour, ampm);

            label.setText(labelText);
            label.setTextSize(13f);
            label.setTextColor(Color.parseColor("#B6B4C1"));

            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            labelParams.setMargins(dpToPx(40), dpToPx(24), 0, dpToPx(6));
            label.setLayoutParams(labelParams);

            View divider = new View(this);
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            );
            dividerParams.setMargins(dpToPx(30), 0, dpToPx(30), 0);
            divider.setLayoutParams(dividerParams);
            divider.setBackgroundColor(Color.parseColor("#E0E0E0"));

            timelineContainer.addView(label);
            timelineContainer.addView(divider);
        }

        WorkoutRepository repo = new WorkoutRepository(this);
        List<WorkOutTask> tasks = repo.getWorkoutByDate(selectedDate);
        for (WorkOutTask task : tasks) {
            addTaskToTimeline(task);
        }
    }

    private int extractHour(String timeString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mma", Locale.US);
            Date date = sdf.parse(timeString.replace(" ", "").toUpperCase());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.HOUR_OF_DAY);
        } catch (Exception e) {
            return 0;
        }
    }

    private void addTaskToTimeline(WorkOutTask task) {
        LinearLayout timelineContainer = findViewById(R.id.timelineContainer);

        EditText taskView = new EditText(this);
        taskView.setText(task.getTitle() + ", " + task.getTime());
        taskView.setTextSize(12f);
        taskView.setTextColor(task.isDone() ? Color.parseColor("#A5A3AF") : Color.WHITE);
        taskView.setHint("Ab Workout, 7:30am");
        taskView.setInputType(InputType.TYPE_CLASS_TEXT);
        taskView.setBackgroundResource(task.isDone() ? R.drawable.bg_task_done : R.drawable.cr50lr495c050f6eea4ce);
        taskView.setFocusable(false);
        taskView.setPadding(dpToPx(15), dpToPx(9), dpToPx(15), dpToPx(9));

        taskView.setOnClickListener(v -> {
            showOverlay();
            popupTitle.setText(task.getTitle() + " | " + task.getTime());
            popupDateTime.setText(task.getDate() + " | " + task.getTime());
            currentTask = task;
            currentTaskView = taskView;
        });

        int insertIndex = findInsertPosition(task);
        timelineContainer.addView(taskView, insertIndex);
    }

    private int findInsertPosition(WorkOutTask task) {
        int taskHour = extractHour(task.getTime());
        LinearLayout timelineContainer = findViewById(R.id.timelineContainer);

        for (int i = 0; i < timelineContainer.getChildCount(); i++) {
            View child = timelineContainer.getChildAt(i);
            if (child instanceof TextView) {
                String text = ((TextView) child).getText().toString();
                if (text.contains(":")) {
                    int hourInView = extractHour(text);
                    if (taskHour < hourInView) {
                        return i;
                    }
                }
            }
        }
        return timelineContainer.getChildCount();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
