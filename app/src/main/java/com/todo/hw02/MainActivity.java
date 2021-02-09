package com.todo.hw02;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "demo";
    public static final String TOTAL_TASKS = "TOTAL_TASKS";
    public static final int SUCCESS_CODE = 200;
    public static final String ARRAY_INDEX_DISPLAYING = "ARRAY_INDEX_DISPLAYING";

    TextView heading;
    Button viewTasks;
    Button createTask;

    ArrayList<Task> tasks;

    View upComingTaskFragment;

    TextView taskName;
    TextView taskDate;
    TextView taskPriority;

    Task upcomingtask = new Task();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == MainActivity.SUCCESS_CODE || resultCode == DisplayTask.DELETE_CODE) {
            if (data.getComponent() != null && data.getComponent().getClassName().equalsIgnoreCase(CreateTask.class.getName())) {
                if (resultCode == MainActivity.SUCCESS_CODE && data != null && data.getSerializableExtra(CreateTask.TASK_KEY) != null) {
                    Log.d(TAG, "onActivityResult: " + data.getSerializableExtra(CreateTask.TASK_KEY));
                    tasks.add((Task) data.getSerializableExtra(CreateTask.TASK_KEY));
                }
            } else if (data.getComponent() != null && data.getComponent().getClassName().equalsIgnoreCase(DisplayTask.class.getName())) {
                if (resultCode == DisplayTask.DELETE_CODE && data != null && data.getSerializableExtra(DisplayTask.UPDATED_TASKS) != null) {
                    tasks = (ArrayList<Task>) data.getSerializableExtra(DisplayTask.UPDATED_TASKS);
                }
            }

            String currHeading = heading.getText().toString();
            Log.d(TAG, "onResume: " + heading.getText());
            heading.setText(currHeading.replaceAll("\\d+", String.valueOf(tasks.size())));

            if (tasks != null && tasks.size() > 0) {
                ArrayList<Task> upcomingtasks = (ArrayList<Task>) tasks.stream().filter(c-> {
                    try {
                        return CreateTask.dateformat.parse(c.date).compareTo(CreateTask.dateformat.parse(CreateTask.dateformat.format(new Date())))>=0;
                    } catch (ParseException e) {
                        return false;
                    }
                }).collect(Collectors.toList());

                if (upcomingtasks.size() > 0){
                    upcomingtasks.sort(new Comparator<Task>() {
                        @Override
                        public int compare(Task o1, Task o2) {
                            try {
                                return CreateTask.dateformat.parse(o1.date).compareTo(CreateTask.dateformat.parse(o2.date));
                            } catch (ParseException e) {
                                Log.d(TAG, "compare: "+e.getLocalizedMessage());
                            }
                            return 0;
                        }
                    });
                    upcomingtask = upcomingtasks.get(0);

                    taskName.setText(upcomingtask.taskName);
                    taskDate.setText(upcomingtask.date);
                    taskPriority.setText(upcomingtask.priority);
                } else {
                    taskName.setText(getString(R.string.mainactivity_upcomingtaskempty));
                    taskDate.setText(getString(R.string.mainactivity_upcomingtaskblank));
                    taskPriority.setText(getString(R.string.mainactivity_upcomingtaskblank));
                }
            } else {
                taskName.setText(getString(R.string.mainactivity_upcomingtaskempty));
                taskDate.setText(getString(R.string.mainactivity_upcomingtaskblank));
                taskPriority.setText(getString(R.string.mainactivity_upcomingtaskblank));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.mainactivity_title);

        tasks = new ArrayList<>();
        heading = findViewById(R.id.mainActivityheading);
        viewTasks = findViewById(R.id.viewTasksButton);
        createTask = findViewById(R.id.createTaskButton);
        upComingTaskFragment = findViewById(R.id.upcomingFragment);

        taskName = upComingTaskFragment.findViewById(R.id.upcomingTaskName);
        taskDate = upComingTaskFragment.findViewById(R.id.upcomingTaskDate);
        taskPriority = upComingTaskFragment.findViewById(R.id.upcomingTaskPriority);

        taskName.setText(getString(R.string.mainactivity_upcomingtaskempty));
        taskDate.setText(getString(R.string.mainactivity_upcomingtaskblank));
        taskPriority.setText(getString(R.string.mainactivity_upcomingtaskblank));

        viewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> currentTasks = new ArrayList<>();
                for (Task task : tasks) {
                    currentTasks.add(task.taskName);
                }
                if (!(currentTasks.size() > 0)) {
                    currentTasks.add(getString(R.string.notasks));
                }
                AlertDialog.Builder dailoge = new AlertDialog.Builder(MainActivity.this);
                dailoge.setTitle(R.string.dailogetitle).setItems(currentTasks.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentTasks.size() >0 && currentTasks.get(0) != null && !currentTasks.get(0).equalsIgnoreCase(getString(R.string.notasks))){
                            Intent intent = new Intent(MainActivity.this, DisplayTask.class);
                            intent.putExtra(DisplayTask.DISPLAY_TASK_KEY, tasks.get(which));
                            intent.putExtra(ARRAY_INDEX_DISPLAYING, which);
                            intent.putExtra(MainActivity.TOTAL_TASKS, tasks);
                            startActivityForResult(intent,SUCCESS_CODE);
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.notasks), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(getString(R.string.cancelbutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dailoge.create().show();
            }
        });

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getString(R.string.intentname));
                startActivityForResult(intent, SUCCESS_CODE);
            }
        });
    }
}