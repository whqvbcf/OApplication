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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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