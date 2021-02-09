package com.todo.hw02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayTask extends AppCompatActivity {

    public static final String DISPLAY_TASK_KEY = "DISPLAY_TASK_KEY";
    public static final String UPDATED_TASKS = "UPDATED_TASKS";
    public static final int DELETE_CODE = 202;
    private static final String TAG = MainActivity.TAG;

    TextView taskName;
    TextView date;
    TextView priority;

    Button delete;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        setTitle(R.string.displayactivity_title);

        taskName = findViewById(R.id.displayActivityNameValue);
        date = findViewById(R.id.displayActivityDateValue);
        priority = findViewById(R.id.displayActivityPriorityValue);

        delete = findViewById(R.id.displayActivityDelete);
        cancel = findViewById(R.id.displayActivityCancel);

        Intent intent = getIntent();
        Task currentTask = (Task)intent.getSerializableExtra(DISPLAY_TASK_KEY);
        taskName.setText(currentTask.taskName);
        date.setText(currentTask.date);
        priority.setText(currentTask.priority);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayTask.this);
                dialog.setTitle(getString(R.string.deletetitle));
                dialog.setMessage(getString(R.string.deletetaskmessage));
                dialog.setPositiveButton(getString(R.string.labelok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Task> updatedTasks = (ArrayList<Task>) intent.getSerializableExtra(MainActivity.TOTAL_TASKS);
                        int removeIndex = (Integer) intent.getIntExtra(MainActivity.ARRAY_INDEX_DISPLAYING, 0);
                        Task isupdated = updatedTasks.remove(removeIndex);
                        Log.d(TAG, "onClick: "+updatedTasks+ " -- "+isupdated+" -- "+(Integer) intent.getIntExtra(MainActivity.ARRAY_INDEX_DISPLAYING, 0));
                        intent.putExtra(UPDATED_TASKS, updatedTasks);
                        setResult(DELETE_CODE, intent);
                        finish();
                    }
                });
                dialog.setNegativeButton(getString(R.string.cancelbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.create().show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}