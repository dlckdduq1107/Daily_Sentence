package com.example.dailysentence;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //설정화면을 관리하는 액티비티
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

    }
}
