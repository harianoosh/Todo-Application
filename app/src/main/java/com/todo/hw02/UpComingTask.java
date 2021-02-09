package com.todo.hw02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpComingTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpComingTask extends Fragment {

    public static String TAG = MainActivity.TAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "UpComingTask onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "UpComingTask onCreateView: ");
        return inflater.inflate(R.layout.fragment_up_coming_task, container, false);
    }
}