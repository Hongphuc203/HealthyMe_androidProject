package com.example.project;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkOutTaskDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "workout.db";
    private static final int DB_VERSION = 1;

    public WorkOutTaskDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE workout_schedule (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT," +
                "time TEXT," +
                "title TEXT," +
                "difficulty TEXT," +
                "repetitions TEXT," +
                "is_done INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS workout_schedule");
        onCreate(db);
    }
}
