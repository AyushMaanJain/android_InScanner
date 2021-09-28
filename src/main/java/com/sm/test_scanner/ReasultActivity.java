package com.sm.test_scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReasultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasult);
        String s=getIntent().getStringExtra("Raw Data");
        TextView tv=findViewById(R.id.raw_displayer);
        tv.setText(s);
    }
}