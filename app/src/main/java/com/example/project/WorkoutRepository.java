package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository {
    private WorkOutTaskDBHelper dbHelper;

    public WorkoutRepository(Context context) {
        dbHelper = new WorkOutTaskDBHelper(context);
    }

    public void insert(WorkOutTask workout) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", workout.date);
        values.put("time", workout.time);
        values.put("title", workout.title);
        values.put("difficulty", workout.difficulty);
        values.put("repetitions", workout.repetitions);
        values.put("is_done", workout.isDone ? 1 : 0);
        db.insert("workout_schedule", null, values);
        db.close();
    }

    public List<WorkOutTask> getWorkoutByDate(String date) {
        List<WorkOutTask> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM workout_schedule WHERE date = ?", new String[]{date});
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            String taskDate = c.getString(c.getColumnIndexOrThrow("date"));
            String time = c.getString(c.getColumnIndexOrThrow("time"));
            String title = c.getString(c.getColumnIndexOrThrow("title"));
            String difficulty = c.getString(c.getColumnIndexOrThrow("difficulty"));
            String reps = c.getString(c.getColumnIndexOrThrow("repetitions"));
            // Nếu bạn không dùng weights trong DB, truyền tạm null
            boolean isDone = c.getInt(c.getColumnIndexOrThrow("is_done")) == 1;

            list.add(new WorkOutTask(id, taskDate, time, title, difficulty, reps, null, isDone));
        }
        c.close();
        db.close();
        return list;
    }

    // Sửa trong WorkoutRepository.java
    public boolean updateDoneStatus(int taskId, boolean isDone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_done", isDone ? 1 : 0);
        int rows = db.update("workout_schedule", values, "id = ?", new String[]{String.valueOf(taskId)});
        db.close();
        return rows > 0;
    }



}
