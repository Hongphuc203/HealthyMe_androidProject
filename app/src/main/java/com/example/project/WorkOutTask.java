package com.example.project;
import java.io.Serializable;

public class WorkOutTask implements Serializable {

    public int id;
    public String date;
    public String time;
    public String title;
    public String difficulty;
    public String repetitions;

    public boolean isDone;

    public WorkOutTask(int id, String date, String time, String title, String difficulty, String reps, String weights, boolean isDone) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.difficulty = difficulty;
        this.repetitions = reps;
        this.isDone = isDone;
    }
    // Constructor rút gọn để dùng khi thêm mới task
    public WorkOutTask(String time, String title, String difficulty, String repetitions) {
        this.id = 0; // ID auto-increment trong DB
        this.date = ""; // sẽ gán sau nếu cần
        this.time = time;
        this.title = title;
        this.difficulty = difficulty;
        this.repetitions = repetitions;
        this.isDone = false;
    }
    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }
    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { this.isDone = done; }
    public int getId() { return id; }
    public void setDate(String date) {
        this.date = date;
    }


}
