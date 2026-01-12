package com.example.myapplication.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapter.WifiSpinnerAdapter;

/**
 * 自带右指向箭头的Spinner控件
 */
public class WifiExSpinner extends Spinner {

    private static final String TAG = "WifiExSpinner";
    private static final int[] ATTRS = {android.R.attr.entries};
    private Drawable arrowDrawable;
    private int arrowPadding = 8; // 箭头与Spinner文字的间距

    public WifiExSpinner(Context context) {
        super(context);
        init(null);
        setDropdownWidthToScreenWidth(context);
    }

    public WifiExSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setDropdownWidthToScreenWidth(context);
    }

    public WifiExSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        setDropdownWidthToScreenWidth(context);
    }

    private void init(AttributeSet attrs) {
        // 设置箭头图标，使用spinner_arrow资源
        arrowDrawable = getResources().getDrawable(R.drawable.gd);
        if (arrowDrawable != null) {
            arrowDrawable.setBounds(0, 0, arrowDrawable.getIntrinsicWidth(), arrowDrawable.getIntrinsicHeight());
        }

        // 设置文字靠左对齐
        setGravity(android.view.Gravity.START | android.view.Gravity.CENTER_VERTICAL);

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
                Log.d(TAG, "Horizontal offset set to: " + horizontalOffset);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting horizontal offset", e);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 先绘制Spinner的原始内容
        super.onDraw(canvas);
        
        // 然后绘制箭头图标在右边
        if (arrowDrawable != null) {
            int arrowWidth = arrowDrawable.getIntrinsicWidth();
            int arrowHeight = arrowDrawable.getIntrinsicHeight();
            
            // 计算箭头位置（右边居中，距离右边缘18dp）
            int rightMargin = (int) (18 * getResources().getDisplayMetrics().density); // 18dp转换为px
            int arrowLeft = getWidth() - arrowWidth - arrowPadding - rightMargin;
            int arrowTop = (getHeight() - arrowHeight) / 2;
            
            arrowDrawable.setBounds(arrowLeft, arrowTop, arrowLeft + arrowWidth, arrowTop + arrowHeight);
            arrowDrawable.draw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        // 如果有箭头图标，确保有足够的空间显示
        if (arrowDrawable != null) {
            int arrowWidth = arrowDrawable.getIntrinsicWidth();
            int rightMargin = (int) (18 * getResources().getDisplayMetrics().density); // 18dp转换为px
            int requiredPadding = arrowWidth + arrowPadding * 2 + rightMargin;
            
            // 检查当前宽度是否足够
            int currentWidth = getMeasuredWidth();
            if (currentWidth < requiredPadding) {
                // 如果宽度不够，强制设置最小宽度
                setMeasuredDimension(requiredPadding, getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        
        // 确保文字显示区域不被箭头遮挡
        if (arrowDrawable != null) {
            int arrowWidth = arrowDrawable.getIntrinsicWidth();
            int rightMargin = (int) (18 * getResources().getDisplayMetrics().density); // 18dp转换为px
            int paddingRight = arrowWidth + arrowPadding * 2 + rightMargin;
            
            // 设置左边距为0（文字靠左），右边距为箭头预留空间（包含18dp右边距）
            setPadding(0, getPaddingTop(), paddingRight, getPaddingBottom());
        }
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
                Log.e(TAG, "Error setting vertical offset", e);
            }
        }
    }

    // 设置箭头图标
    public void setArrowDrawable(Drawable drawable) {
        this.arrowDrawable = drawable;
        if (arrowDrawable != null) {
            arrowDrawable.setBounds(0, 0, arrowDrawable.getIntrinsicWidth(), arrowDrawable.getIntrinsicHeight());
        }
        invalidate();
    }

    // 设置箭头图标资源
    public void setArrowImageResource(int resId) {
        setArrowDrawable(getResources().getDrawable(resId));
    }

    // 设置箭头与Spinner文字的间距
    public void setArrowPadding(int padding) {
        this.arrowPadding = padding;
        invalidate();
        requestLayout();
    }

    // 获取箭头与Spinner文字的间距
    public int getArrowPadding() {
        return arrowPadding;
    }
}

