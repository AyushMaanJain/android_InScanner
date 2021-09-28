package com.sm.test_scanner.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sm.test_scanner.R;
import com.sm.test_scanner.ScannerActivity;
import com.sm.test_scanner.databinding.ActivityMainBinding;
import com.sm.test_scanner.ui.DashBoard;
import com.sm.test_scanner.ui.DataContainer;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;
    private DashBoard db;
    private ActivityMainBinding binding;
    static Context context;

    public static PlaceholderFragment newInstance(Context context,int index) {
        PlaceholderFragment.context=context;
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void addTable(){
        db.newRow();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db=new DashBoard(getContext());
        db.setQrListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataContainer.IN_OUT=index;
                Log.e("73???",index+"");
                checkPermission();
            }
        });
        LinearLayout.LayoutParams half=new LinearLayout.LayoutParams((getResources().getDisplayMetrics().widthPixels)-20, ViewGroup.LayoutParams.MATCH_PARENT);
        half.setMargins(10,10,10,10);
        Button rec=new Button(context);
        rec.setTextSize(20);
        rec.setPadding(0,0,0,0);
        rec.setLayoutParams(half);
        rec.setTextColor(0xffffffff);;
        rec.setBackgroundResource(R.drawable.button_bg);

        FrameLayout nv=binding.master;
View mai=binding.main;
        LinearLayout ll=binding.bottom;



        ll.addView(rec);

        nv.addView(db);
        if(index==DataContainer.IN){
            rec.setText("IN");
            mai.setBackgroundColor(0xfff1ffeb);
        }
        else if(index==DataContainer.OUT){
            rec.setText("OUT");
            mai.setBackgroundColor(0xffc2ffff);
        }
        return root;
    }


        public void checkPermission(){
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                startScanner();
            }
            else {
                getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, 12);
            }

    }
    public void startScanner(){

        Intent in=new Intent(context, ScannerActivity.class);
        startActivity(in);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}