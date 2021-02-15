package com.todo.hw02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTask extends AppCompatActivity {

    public static final String TASK_KEY = "TASK_KEY";
    private static final String TAG = MainActivity.TAG;
    public static final SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");

    Button setDate;
    Button submit;
    Button cancel;

    TextView dateSelected;

    EditText taskName;

    RadioGroup priority;

    Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setTitle(R.string.createtaskactivity_title);

        taskName = findViewById(R.id.createtasknamevalue);
        priority = findViewById(R.id.radioGroup);
        setDate = findViewById(R.id.createtasksetdate);
        submit = findViewById(R.id.createtasksubmitbutton);
        cancel = findViewById(R.id.createtaskcancelbutton);
        dateSelected = findViewById(R.id.createtaskdatevalue);

        currentTask = new Task();

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar today = Calendar.getInstance();
                DatePickerDialog date = new DatePickerDialog(CreateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);
                        currentTask.date = dateformat.format(c.getTime());
                        dateSelected.setText(currentTask.date);
                        Log.d(TAG, "onDateSet: "+currentTask.date);
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
//                date.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int priorityCode = priority.getCheckedRadioButtonId();
                try {
                    if (taskName.getText() != null && taskName.getText().toString() != null && !taskName.getText().toString().isEmpty()) {
                        currentTask.taskName = taskName.getText().toString();
                    } else {
                        throw new Exception(getString(R.string.createtaskactivity_tasknametoast));
                    }

                    try {
                        if (dateSelected.getText() != null && dateSelected.getText().toString() != null && !dateSelected.getText().toString().isEmpty()) {
                            currentTask.date = dateSelected.getText().toString();
                        } else {
                            throw new Exception(getString(R.string.createtaskactivity_taskdatetoast));
                        }
                    } catch (Exception e) {
                        throw new Exception(getString(R.string.createtaskactivity_taskdatetoast));
                    }

                    if (priorityCode == -1) {
                        throw new Exception(getString(R.string.createtaskactivity_priorityslecttoast));
                    } else if (priorityCode == R.id.createtaskpriorityhigh) {
                        currentTask.priority = getString(R.string.createtaskactivity_priorityhigh);
                    } else if (priorityCode == R.id.createtaskprioritymedium) {
                        currentTask.priority = getString(R.string.createtaskactivity_prioritymedium);
                    } else if (priorityCode == R.id.createtaskprioritylow) {
                        currentTask.priority = getString(R.string.createtaskactivity_prioritylow);
                    }

                    Intent intent = getIntent();
                    intent.putExtra(CreateTask.TASK_KEY, currentTask);
                    setResult(MainActivity.SUCCESS_CODE, intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(CreateTask.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}