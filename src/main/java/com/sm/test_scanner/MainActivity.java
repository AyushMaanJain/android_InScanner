package com.sm.test_scanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.BuildConfig;
import com.sm.test_scanner.ui.DashBoard;
import com.sm.test_scanner.ui.DataContainer;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    DashBoard db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(
                    new StrictMode.VmPolicy.Builder()
                            .detectLeakedSqlLiteObjects()
                            .detectLeakedClosableObjects()
                            .penaltyLog()
                            .build());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DashBoard(getApplicationContext());
        db.setQrListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        FrameLayout nv=findViewById(R.id.master);
        nv.addView(db);
        LinearLayout ll=findViewById(R.id.bottom);

        LinearLayout.LayoutParams half=new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels/2)-20, ViewGroup.LayoutParams.MATCH_PARENT);
        half.setMargins(10,10,10,10);
        Button rec=new Button(this);
        rec.setText("Recieve");
        rec.setLayoutParams(half);
        rec.setTextColor(0xffffffff);;
        rec.setBackgroundResource(R.drawable.button_bg);
        Button sen=new Button(this);
        sen.setText("Send");
        sen.setLayoutParams(half);
        sen.setTextColor(0xffffffff);;
        sen.setBackgroundResource(R.drawable.button_bg);


        ll.addView(rec);ll.addView(sen);
    }


    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            startScanner();
        }
        else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 12);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScanner();
        }else{
            Toast.makeText(this, "Camera is required for scanning qr code", Toast.LENGTH_LONG).show();
        }
    }

    public void startScanner(){
        Intent in=new Intent(this,ScannerActivity.class);
        startActivity(in);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!DataContainer.QRDATA.equals("")){
            db.newRow();
        }
    }
    public void test(){

    }
}