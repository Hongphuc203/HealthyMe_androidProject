package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SleepDB.java
 * Lớp này giúp tạo và quản lý cơ sở dữ liệu SQLite cho phần Sleep Tracker.
 */
public class SleepDb extends SQLiteOpenHelper {
    // Tên database và version
    private static final String DATABASE_NAME = "SleepTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    public static final String TABLE_SLEEP = "sleep";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";         // TEXT, định dạng "YYYY-MM-DD"
    public static final String COLUMN_DURATION = "duration"; // REAL, số giờ ngủ (ví dụ 7.5)

    // Câu lệnh tạo bảng:
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_SLEEP + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_DURATION + " REAL NOT NULL" +
                    ");";

    // Câu lệnh xóa bảng khi upgrade
    private static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_SLEEP;

    /**
     * Constructor bắt buộc phải có Context, vì SQLiteOpenHelper không có constructor không tham số.
     */
    public SleepDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Phương thức onCreate sẽ được gọi khi database chưa tồn tại và cần được tạo mới.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * Phương thức onUpgrade sẽ được gọi khi phiên bản DATABASE_VERSION thay đổi,
     * dùng để xóa bảng cũ rồi tạo lại (hoặc thực hiện migration).
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nếu cần nâng cấp cấu trúc, xóa bảng cũ và tạo lại.
        // (Trong thực tế, nếu muốn giữ dữ liệu, bạn cần viết logic ALTER TABLE tương ứng.)
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    /**
     * Ví dụ một phương thức chèn dữ liệu vào bảng sleep.
     * Phải khai báo có kiểu trả về (ở đây return long) và tham số đầy đủ.
     */
    public long insertSleepRecord(String date, float duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        android.content.ContentValues values = new android.content.ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_DURATION, duration);

        long newRowId = db.insert(TABLE_SLEEP, null, values);
        db.close();
        return newRowId;
    }

    /**
     * Ví dụ một phương thức đọc toàn bộ record từ bảng sleep, trả về Cursor.
     */
    public android.database.Cursor getAllSleepRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_SLEEP,
                new String[]{COLUMN_ID, COLUMN_DATE, COLUMN_DURATION},
                null, null, null, null,
                COLUMN_DATE + " DESC"
        );
    }

    // Bạn có thể thêm các phương thức update, delete tương tự với kiểu trả về và tham số đầy đủ.
}
