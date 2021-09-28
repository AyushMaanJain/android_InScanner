package com.sm.test_scanner;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.test_scanner.ui.CustomVievPager;
import com.sm.test_scanner.ui.DataContainer;
import com.sm.test_scanner.ui.main.PlaceholderFragment;
import com.sm.test_scanner.ui.main.SectionsPagerAdapter;
import com.sm.test_scanner.databinding.ActivityMainaBinding;

import java.util.ArrayList;

public class MainActivityA extends AppCompatActivity {

    private ActivityMainaBinding binding;
    SectionsPagerAdapter sectionsPagerAdapter;
    public static ArrayList<String> datas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float density=getResources().getDisplayMetrics().density;
        int wid=getResources().getDisplayMetrics().widthPixels;
        int w=(int)(wid/density);
        Toast.makeText(this, w+"dp", Toast.LENGTH_SHORT).show();
        binding = ActivityMainaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
viewPager.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
});

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setBackgroundResource(R.drawable.tab_out);



        TabLayout.Tab tb= tabs.newTab();
        View v=getLayoutInflater().inflate(R.layout.custom_tab,null);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView tv=v.findViewById(R.id.text1);
        tv.setText("IN");
        tb.setCustomView(v);
        tabs.addTab(tb);

        TabLayout.Tab tb2= tabs.newTab();
        View v2=getLayoutInflater().inflate(R.layout.custom_tab,null);
        v2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TextView tv2=v2.findViewById(R.id.text1);
        tv2.setText("OUT");
        tb2.setCustomView(v2);
        tabs.addTab(tb2);
        tv2.setTextColor(0xffffffff);
        tv.setTextColor(0xff000000);


        tabs.setSelectedTabIndicatorColor(0xffffffff);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                viewPager.setCurrentItem(pos);
                switch (pos){
                    case 1:
                       // tabs.setSelectedTabIndicatorColor(0xffc2ffff);
                        //tv.setTextColor(0xffc2ffff);
                        tv.setTextColor(0xffffffff);

                        tabs.setBackgroundResource(R.drawable.tab_in);
                        //tv2.setTextColor(0xff008080);
                        tv2.setTextColor(0xff000000);

                    break;
                    default:
                        //tabs.setSelectedTabIndicatorColor(0xfff1ffeb);
                        tabs.setBackgroundResource(R.drawable.tab_out);
                        //tv2.setTextColor(0xfff1ffeb);
                        tv2.setTextColor(0xffffffff);
                        //tv.setTextColor(0xff008000);
                        tv.setTextColor(0xff000000);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


}

    @Override
    protected void onResume() {
        super.onResume();
        if(DataContainer.QRDATA!=""){
        try{PlaceholderFragment pf=(PlaceholderFragment) sectionsPagerAdapter.pf[DataContainer.IN_OUT];
        pf.addTable();
        }catch (Exception e){
            Log.e("L57L",e.toString());
        }}else{
          //  Toast.makeText(this, "scanner stopped"+DataContainer.QRDATA, Toast.LENGTH_SHORT).show();
        }
    }
}