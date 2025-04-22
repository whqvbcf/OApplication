package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Spinner;

public class WifiSpinner extends Spinner {

    public WifiSpinner(Context context) {
        super(context);
        setDropdownWidthToScreenWidth(context);
    }

    public WifiSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDropdownWidthToScreenWidth(context);
//        setDropDownHorizontalOffset(-60); // 设置水平偏移量
    }

    public WifiSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDropdownWidthToScreenWidth(context);
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
        setDropDownWidth(dropdownWidth);

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

                    Log.d("WifiSpinner", "VerticalOff offset set to: " + this.getHeight());
                    // 设置偏移量，使弹框在 Spinner 下方
                    popupWindow.setVerticalOffset(this.getHeight());

                    // 尝试更大的水平偏移量
//                    int horizontalOffset = -50; // 尝试更大的偏移量
//                    popupWindow.setHorizontalOffset(horizontalOffset);
//                    Log.d("WifiSpinner", "Horizontal offset set to: " + horizontalOffset);
                }
            } catch (Exception e) {
                Log.e("WifiSpinner", "Error setting horizontal offset", e);
            }
        }
    }
} 