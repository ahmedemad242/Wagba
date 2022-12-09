package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wagba.databinding.ActivityHistoryBinding;

public class History extends AppCompatActivity {

    private ActivityHistoryBinding activityHistoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }
}