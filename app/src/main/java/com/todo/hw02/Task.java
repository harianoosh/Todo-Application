package com.todo.hw02;

import java.io.Serializable;

public class Task implements Serializable {
    String taskName;
    String date;
    String priority;

    public Task(String taskName, String date, String priority) {
        this.taskName = taskName;
        this.date = date;
        this.priority = priority;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", date='" + date + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
