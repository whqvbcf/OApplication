package com.example.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.PowerManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.myapplication.R;

public class ConsumerPasswordTextView extends PasswordTextView {
    // 画笔-->绘制背景
    private Paint mRectPaint;
    // 画笔--> 绘制密码
    private Paint mPasswordPaint;
    // 一个密码所占的宽度
    private int mPasswordItemWidth;
    // 密码的个数默认为4位数
    private int mPasswordNumber = 4;
    // 背景颜色
    private int mBgColor = Color.parseColor("#40ffffff");
    // 背景边框大小
    private int mBgSize = 1;
    // 背景边框圆角大小
    private int mBgCorner = 0;
    // 分割线的颜色
    private int mDivisionLineColor = mBgColor;
    // 分割线的大小
    private int mDivisionLineSize = 0;
    // 密码圆点的颜色
    private int mPasswordColor = Color.parseColor("#CCFFFFFF");
    // 密码圆点的半径大小
    private int mPasswordRadius = 10;
    // 背景圆点的半径大小
    private int mBgRadius = 10;
    private int mTextSpace = 0;
    //qp add  start
    private Animation shake;
    private PasswordEditText.UserActivityListener mUserActivityListener;

    public interface UserActivityListener {
        void onUserActivity();
    }

    private PowerManager mPM;
    private String mText = "";

    public void setUserActivityListener(PasswordEditText.UserActivityListener userActivitiListener) {
        mUserActivityListener = userActivitiListener;
    }

    private void userActivity() {
        //mPM.userActivity(SystemClock.uptimeMillis(), false);
        if (mUserActivityListener != null) {
            mUserActivityListener.onUserActivity();
        }
    }
    //qp add end

    public ConsumerPasswordTextView(Context context) {
        this(context, null);
    }

    public ConsumerPasswordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
        //不显示光标 qqq
        //setCursorVisible(false);
        //不弹出系统软键盘 qqq
        //setInputType(InputType.TYPE_NULL);
        //背景去掉
        //setBackground(null);
        initPaint();
        mPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (ps_animated) {
                    setEnabled(true);
                }
//                setEnabled(true);
                String password = "";
                //111
                setText(password);
                mTextChangeListener.onTextChangeListener(password.length());
                //qqq
                postInvalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 初始化属性
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        // 获取大小
        mDivisionLineSize = (int) array.getDimension(R.styleable.PasswordEditText_divisionLineSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PasswordEditText_passwordRadius, dip2px(mPasswordRadius));
        mBgRadius = (int) array.getDimension(R.styleable.PasswordEditText_bgRadius, dip2px(mBgRadius));
        mBgSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PasswordEditText_bgCorner, 0);
        // 获取颜色
        mBgColor = array.getColor(R.styleable.PasswordEditText_bgColor, mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.PasswordEditText_divisionLineColor, mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.PasswordEditText_passwordColor, mPasswordColor);
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        //初始化绘制边的画笔
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setDither(true);
        mRectPaint.setColor(mBgColor);
        //初始化密码远点的画笔
        mPasswordPaint = new Paint();
        mPasswordPaint.setAntiAlias(true);
        mPasswordPaint.setDither(true);
        mPasswordPaint.setColor(mPasswordColor);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //不需要调用super.onDraw(canvas); 为什么不需要呢？你去调用试试看，就明白为什么了
        //super.onDraw(canvas);
        //一个密码的宽度
        //oo
        mPasswordItemWidth = (getWidth() - mBgSize * 2 - (mPasswordNumber - 1) * mDivisionLineSize) / mPasswordNumber;
        mTextSpace = (getWidth() - mBgRadius * mPasswordNumber * 2) / (mPasswordNumber - 1);
        drawRect(canvas);
        drawPassword(canvas);

    }

    /**
     * 绘制背景框
     *
     * @param canvas 画布
     */
    private void drawRect(Canvas canvas) {
        //画实心
        mRectPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mPasswordNumber; i++) {
            //
            //int cx = mBgSize + i * mDivisionLineSize + i * mPasswordItemWidth + mPasswordItemWidth / 2;
            int cx = (2 * i + 1) * mBgRadius + i * mTextSpace;
            int cy = getHeight() / 2;
            canvas.drawCircle(cx, cy, mBgRadius, mRectPaint);
        }
    }


    /**
     * 绘制圆点密码
     *
     * @param canvas 画布
     */
    private void drawPassword(Canvas canvas) {
        //圆点密码是实行的
        mPasswordPaint.setStyle(Paint.Style.FILL);
        int length = getText().toString().length();
        for (int i = 0; i < length; i++) {
            //int cx = mBgSize + i * mDivisionLineSize + i * mPasswordItemWidth + mPasswordItemWidth / 2;
            int cx = (2 * i + 1) * mBgRadius + i * mTextSpace;
            int cy = getHeight() / 2;
            canvas.drawCircle(cx, cy, mPasswordRadius, mPasswordPaint);
        }
    }

    public void append(char c) {
        String number = String.valueOf(c);
        if (TextUtils.isEmpty(number)) {
            return;
        }
        //把密码取取出来
        String password = getText().toString().trim();
        if (password.length() <= mPasswordNumber) {
            //密码叠加
            password += number;
            //qqq
            setText(password);
        }
        userActivity();
        if (mTextChangeListener != null)
            mTextChangeListener.onTextChangeListener(password.length());
        //qqq
        Log.i("qqq", "======password: " + password);
        postInvalidate();
    }

    public String getText2() {
        return getText().toString().trim();
    }

    /**
     * 删除密码
     */
    public void deleteLastChar() {
        String password = getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        password = password.substring(0, password.length() - 1);
        //qqq
        setText(password);
        userActivity();
        if (mTextChangeListener != null)
            mTextChangeListener.onTextChangeListener(password.length());
        //qqq
        postInvalidate();
    }

    boolean ps_animated;

    public void reset(boolean animated, boolean announce) {
        ps_animated = animated;
        if (announce) {
            startAnimation(shake);
        } else {
            String password = "";
            //qqq
            setText(password);
            mTextChangeListener.onTextChangeListener(password.length());
        }

        //qqq
        postInvalidate();
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    //字段长短监听
    private PasswordEditText.TextChangeListener mTextChangeListener;

    public interface TextChangeListener {
        void onTextChangeListener(int size);
    }

    public void setTextChangeListener(PasswordEditText.TextChangeListener textChangeListener) {
        mTextChangeListener = textChangeListener;
    }
}