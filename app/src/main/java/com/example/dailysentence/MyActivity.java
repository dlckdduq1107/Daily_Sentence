package com.example.dailysentence;

import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//정보입력액티비티
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }
}
