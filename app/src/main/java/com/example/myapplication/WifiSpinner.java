package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Spinner;
import android.content.res.TypedArray;

public class WifiSpinner extends Spinner {

    private static final int[] ATTRS = {android.R.attr.entries};

    public WifiSpinner(Context context) {
        super(context);
        init(null);
        setDropdownWidthToScreenWidth(context);
//        setBackgroundResource(R.drawable.blue_arrow);
    }

    public WifiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setDropdownWidthToScreenWidth(context);
//        setBackgroundResource(R.drawable.blue_arrow);
//        setDropDownHorizontalOffset(-60); // 设置水平偏移量
    }

    public WifiSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        setDropdownWidthToScreenWidth(context);
//        setBackgroundResource(R.drawable.blue_arrow);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, ATTRS);
            int entriesResId = a.getResourceId(0, 0);
            a.recycle();

            if (entriesResId != 0) {
                String[] items = getResources().getStringArray(entriesResId);
                WifiSpinnerAdapter<CharSequence> adapter = new WifiSpinnerAdapter<>(getContext(), R.layout.spinner_item, items);
                setAdapter(adapter);
            }
        }
    }

    private void setDropdownWidthToScreenWidth(Context context) {
        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        // 设置左右间距
        int margin = 32; // 例如，16px 左右各 16px

        // 计算下拉宽度
        int dropdownWidth = screenWidth - 2 * margin;
        setDropDownWidth(screenWidth);

        // 设置水平偏移量为 16px
        int horizontalOffset = -60;

        // 设置水平偏移量
        try {
            java.lang.reflect.Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(this);
            if (popupWindow != null) {
                popupWindow.setHorizontalOffset(horizontalOffset);
                Log.d("WifiSpinner", "Horizontal offset set to: " + horizontalOffset);
            }
        } catch (Exception e) {
            Log.e("WifiSpinner", "Error setting horizontal offset", e);
        }
//        setDropDownHorizontalOffset(-60);
//        setGravity(Gravity.CENTER_HORIZONTAL); // 设置文字靠右并垂直居中
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            try {
                // 反射获取 mPopup
                java.lang.reflect.Field popup = Spinner.class.getDeclaredField("mPopup");
                popup.setAccessible(true);

                // 获取 PopupWindow
                android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(this);
                if (popupWindow != null) {
                    // 设置偏移量，使弹框在 Spinner 下方
                    popupWindow.setVerticalOffset(this.getHeight());
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_border));
                }
                ((WifiSpinnerAdapter) getAdapter()).setSelection(getSelectedItemPosition());
            } catch (Exception e) {
                Log.e("WifiSpinner", "Error setting vertical offset", e);
            }
        }
    }
} 