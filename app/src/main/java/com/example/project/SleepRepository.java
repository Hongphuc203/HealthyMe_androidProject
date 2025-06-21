// SleepRepository.java
package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.project.SleepDb;
public class SleepRepository {
    private SleepDb dbHelper;

    public SleepRepository(Context context) {
        dbHelper = new SleepDb(context);
    }

    public void upsertSleep(String date, float duration) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm xem đã có record cho date này chưa
        Cursor cursor = db.query(
                SleepDb.TABLE_SLEEP,
                new String[]{SleepDb.COLUMN_ID},
                SleepDb.COLUMN_DATE + " = ?",
                new String[]{date},
                null, null, null
        );

        ContentValues values = new ContentValues();
        values.put(SleepDb.COLUMN_DATE, date);
        values.put(SleepDb.COLUMN_DURATION, duration);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(SleepDb.COLUMN_ID));
            db.update(
                    SleepDb.TABLE_SLEEP,
                    values,
                    SleepDb.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}
            );
        } else {
            db.insert(SleepDb.TABLE_SLEEP, null, values);
        }
        cursor.close();
        db.close();
    }

    public List<SleepEntry> getThisWeekSleep() {
        List<SleepEntry> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());  // set về thứ 2
        String start = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());

        Cursor c = db.rawQuery(
                "SELECT * FROM sleep WHERE date BETWEEN ? AND ? ORDER BY date ASC",
                new String[]{start, today}
        );

        while (c.moveToNext()) {
            SleepEntry entry = new SleepEntry(
                    c.getString(c.getColumnIndexOrThrow("date")),
                    (float) c.getDouble(c.getColumnIndexOrThrow("duration"))
            );
            result.add(entry);

        }

        c.close();
        db.close();
        Collections.reverse(result);  // đảo cho đúng thứ tự ngày
        return result;
    }


    public static class SleepEntry {
        public String date;
        public float duration;
        public SleepEntry(String date, float duration) {
            this.date = date;
            this.duration = duration;
        }
    }

}
