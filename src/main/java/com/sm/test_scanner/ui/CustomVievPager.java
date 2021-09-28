package com.sm.test_scanner.ui;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class CustomVievPager extends ViewPager {
    public CustomVievPager(@NonNull Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
