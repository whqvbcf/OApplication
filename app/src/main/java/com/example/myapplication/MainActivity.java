package com.example.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.view.NumKeyboard;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    final static String LOG_TAG = MainActivity.class.getSimpleName();
    FrameLayout frameLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        WifiSpinner spinner = findViewById(R.id.custom_spinner);
        String[] items = {"无", "WEP", "WPA/WPA2 PSK", "802.1x EAP"};
        WifiSpinnerAdapter adapter = new WifiSpinnerAdapter(this, R.layout.spinner_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

//        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, R.layout.spinner_dropdown_item, getResources().getStringArray(R.array.spinner_items));
//        spinner.setAdapter(adapter);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenWidth = displayMetrics.widthPixels;
//        int offset = (screenWidth - 560) / 2;
//        spinner.setDropDownWidth(screenWidth);
////        spinner.setDropDownHorizontalOffset(-offset);
//        Log.d(LOG_TAG, "=======screenWidth = " + screenWidth + " offset = " + offset);
//
//        spinner.setBackgroundResource(android.R.color.transparent);
//        spinner.setBackgroundColor(Color.TRANSPARENT);
//
//        spinner.getPopupBackground().setAlpha(0);//okki
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                for (int i = 0; i < parent.getCount(); i++) {
//                    View item = parent.getChildAt(i);
//                    if (item instanceof CheckedTextView) {
//                        ((CheckedTextView) item).setChecked(i == position);
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Do nothing
//            }
//        });
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        // TODO Auto-generated method stub
        super.onTitleChanged(title, color);
        if (title != null) {
            //Log.w(LOG_TAG, "www ... onTitleChanged not null title = " + title);
            setConsumerActionBar("mainPage");
        }
    }

    private void switchTo(Locale locale) {
        Resources standardResources = getResources();
        AssetManager assets = standardResources.getAssets();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration config = new Configuration(standardResources.getConfiguration());
        config.locale = locale;
        Resources defaultResources = new Resources(assets, metrics, config);
        Log.w(LOG_TAG, "www ... onTitleChanged not null defaultResources = " + defaultResources.getDisplayMetrics().toString());
    }

    private MarqueeText mConsumerTitle;

    private void setConsumerActionBar(CharSequence title) {
        if (getActionBar() == null) {
            return;
        }
        //Log.w(LOG_TAG, "www ... setConsumerActionBar ont null title =" + title);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);

        mConsumerTitle = new MarqueeText(this);
        mConsumerTitle.setText(title);
        mConsumerTitle.setTextColor(Color.WHITE);
        //mConsumerTitle.setTextSize(20);
        mConsumerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.consumer_action_bar_title_size));
//        mConsumerTitle.setFocusable(true);
//        mConsumerTitle.setFocusableInTouchMode(true);
        mConsumerTitle.setSingleLine();
        mConsumerTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mConsumerTitle.setMarqueeRepeatLimit(-1);


        LinearLayout actionbarLayout = new LinearLayout(this);

        getActionBar().setCustomView(actionbarLayout, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT));
        ActionBar.LayoutParams mP = (ActionBar.LayoutParams) actionbarLayout
                .getLayoutParams();
        mP.gravity = mP.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
        actionbarLayout.addView(mConsumerTitle);
        getActionBar().setCustomView(actionbarLayout, mP);
    }


}