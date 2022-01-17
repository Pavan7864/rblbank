package com.app.rblbank.activities;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.app.rblbank.R;


public class LocalizeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

       //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        MyAppSession.deviceId  = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        setLocale();

    }

    void setLocale(){

        Resources res= getResources();
        DisplayMetrics dm= res.getDisplayMetrics();
        Configuration conf= res.getConfiguration();
        res.updateConfiguration(conf, dm);
    }

    private EditText mEditText;
    private Rect mRect = new Rect();
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

//        int[] location = new int[2];
//        mEditText.getLocationOnScreen(location);
//        mRect.left = location[0];
//        mRect.top = location[1];
//        mRect.right = location[0] + mEditText.getWidth();
//        mRect.bottom = location[1] + mEditText.getHeight();
//
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();

//        if (action == MotionEvent.ACTION_DOWN && !mRect.contains(x, y)) {
//            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            input.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
//        }
        return super.dispatchTouchEvent(ev);
    }


    void hideSoftKeyboard(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}

