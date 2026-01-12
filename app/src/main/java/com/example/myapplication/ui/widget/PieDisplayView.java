package com.example.myapplication.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼图展示视图
 * 原名：PieView
 * 
 * 使用示例:
 *     PieDisplayView myBarView=findViewById(R.id.view1);
 *     myBarView.setItems(19.99288411891240960f,5.686861f,74.32026f);
 *     //myBarView.invalidate();
 *     initView();
 */
public class PieDisplayView extends View {

    private static final String TAG = "PieDisplayView";

    private Paint barPaint;                             //柱状图画笔
    private Paint shadowPaint;                          //阴影画笔
    private int xOffset = 1, yOffset = 1;                    //x和y方向的偏移量
    private float startX = 0f, startY = 0f, endX = 66f, endY = 135f;
    private int layerNum = 25;                            //画布的层数
    private int barMargin = 50;                           //每个柱状体之前的间隔
    private List<Float> percents = new ArrayList<>(); //每种数据的数值，用于计算比例，画出柱状体高度
    private float perSum;                                 //每种数据数值的总和
    private int mParsLen;
    static final int[] DEFAULT_BOTTOM_COLORS = {
            Color.parseColor("#008cff"),
            Color.parseColor("#44abff"),
            Color.parseColor("#008cff")
    };
    static final int[] DEFAULT_CENTRE_COLORS = {
            Color.parseColor("#b6b6b6"),
            Color.parseColor("#c0c0c0"),
            Color.parseColor("#b6b6b6")
    };
    static final int[] DEFAULT_TOP_COLORS = {
            Color.parseColor("#191919"),
            Color.parseColor("#191919"),
            Color.parseColor("#191919")
    };

    public PieDisplayView(Context context, AttributeSet attrs) {
        super(context,attrs);
        //setItems(1f,1f,100f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float rectangleLen = endY - startY;           //矩形的长度
        Log.w(TAG, "rectangleLen is:" + rectangleLen);

        float curStartY = endY;
        float curEndY = endY;
        for (int i = 0; i < mParsLen; i++) {
            autoChangePaint(i);
            //barPaint.setColor(Color.parseColor(DEFAULT_ITEMS_COLORS[i]));
            curStartY = curEndY - (percents.get(i) * rectangleLen) / perSum;
            float dis = curEndY - curStartY;
            //.w(TAG, "dis is:" + dis);
            //阴影线
            //canvas.drawOval(new RectF(startX, curStartY, endX, curStartY + 60),shadowPaint);
            //椭圆
            for (int j = 0; j < dis; j++) {
                float tempY = curStartY + j;
                //Log.w(TAG, "tempY is:" + tempY);
                //矩形区域内切椭圆
                RectF oval = new RectF(startX, tempY, endX, tempY + 20);
                canvas.drawOval(oval, barPaint);
            }
            //canvas.drawOval(new RectF(startX, curStartY + dis, endX, curStartY + dis + 60), shadowPaint);
            curEndY = curStartY;
            Log.w(TAG, "curStartY is:" + curStartY + "|| curEndY is:" + curEndY);
        }
        setTopOverBarPaint();
        canvas.drawOval(new RectF(startX, startY, endX, startY + 20), barPaint);


        float casStart = startY + (percents.get(mParsLen - 1) * rectangleLen) / perSum;
        autoCentreOverBarPaint();
        canvas.drawOval(new RectF(startX, casStart, endX, casStart + 20), barPaint);
    }

    public void setItems(float... pers) {

        for (int i = 0; i < pers.length; i++) {
            perSum += pers[i];
            percents.add(pers[i]);
        }
        mParsLen = percents.size();


        barPaint = new Paint();
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setStrokeWidth(1);
        barPaint.setColor(Color.LTGRAY);
        barPaint.setAntiAlias(true);

        shadowPaint = new Paint();
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setStrokeWidth(1);
        shadowPaint.setColor(Color.parseColor("#5a5a5a"));
        shadowPaint.setAntiAlias(true);
        invalidate();
    }

    private void autoChangePaint(int id) {
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        switch (id) {
            case 0:
                colors = DEFAULT_BOTTOM_COLORS;
                break;
            case 1:
                colors = DEFAULT_CENTRE_COLORS;
                break;
            case 2:
                colors = DEFAULT_TOP_COLORS;
                break;
        }

        float[] position = {0f, 0.35f, 1.0f};
        LinearGradient linearGradient = new LinearGradient(startX, endY, endX, endY, colors, position, Shader.TileMode.CLAMP);
        barPaint.setShader(linearGradient);
    }

    private void setTopOverBarPaint() {
        int[] colors = {
                Color.parseColor("#242424"),
                Color.parseColor("#242424"),
                Color.parseColor("#242424")
        };
        float[] position = {0f, 0.6f, 1.0f};
        LinearGradient linearGradient = new LinearGradient(startX, endY, endX, endY, colors, position, Shader.TileMode.CLAMP);
        barPaint.setShader(linearGradient);
    }

    private void autoCentreOverBarPaint() {
        int[] colors = {
                Color.parseColor("#999999"),
                Color.parseColor("#dad9d9")
        };
        float[] position = {0f, 0.6f};
        LinearGradient linearGradient = new LinearGradient(startX, endY, endX, endY, colors, position, Shader.TileMode.CLAMP);
        barPaint.setShader(linearGradient);
    }
}

