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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    public static final String SCALE_FREE = "자유";
    public static final String SCALE_SQUARE = "정사각형";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_txt_num);
//
//        EditText mEditNum = findViewById(R.id.et_num);
//        TextView mDisplay = findViewById(R.id.tv_display);
//        NumKeyboard mNumKeyboard = findViewById(R.id.numKeyboard);
//        mNumKeyboard.setOnNumKeyBoardLister(new NumKeyboard.NumKeyBoardLister() {
//            @Override
//            public void onNumLister(int num) {
//                Toast.makeText(MainActivity.this, "" + num, Toast.LENGTH_SHORT).show();
//                //将输入的数字填写输入框中
//                //mEditNum.getText() = mEditNum.getText().append(num);
//                mEditNum.setText(mEditNum.getText().toString() + num);
//                //将光标设置到尾部
//                mEditNum.setSelection(mEditNum.getText().toString().length());
//            }
//
//            @Override
//            public void onDelLister() {
//                Toast.makeText(MainActivity.this,"删除", Toast.LENGTH_SHORT).show();
//                if (!mEditNum.getText().toString().isEmpty()) {
//                    int length = mEditNum.getText().toString().length();
//                    //从后往前逐个删除
//                    //mEditNum.text = mEditNum.text.delete(length - 1, length)
//                    mEditNum.setText(mEditNum.getText().toString().substring(0,length - 1));
//                    //将光标设置到尾部
//                    mEditNum.setSelection(mEditNum.getText().toString().length());
//                    //mEditNum.setSelection(mEditNum.text.length)
//                }
//            }
//
//            @Override
//            public void onDownLister() {
//                Toast.makeText(MainActivity.this,"完成", Toast.LENGTH_SHORT).show();
//                if (!mEditNum.getText().toString().isEmpty()) {
////                    mDisplay.text = mEditNum.text
//                    mDisplay.setText(mEditNum.getText().toString());
//                }
//            }
//        });
//        String mScale = "2 : 3";
//        int index = mScale.indexOf(" ");
//        int x = (index > 0 ? Integer.parseInt(mScale.substring(0, index)) : -1);
//        int y = mScale.length();
//        Log.w(LOG_TAG, "www ... Integer.parseInt " + mScale + " = " + x);
//        Log.w(LOG_TAG, "www ... lenth " + mScale + " = " + y);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_task);
        TextView tvDeviceName = findViewById(R.id.title_tx0);
        tvDeviceName.setSelected(true);
//        ProgressDialog pd = new ProgressDialog(this,android.R.style.Theme_NoTitleBar_Fullscreen);
//        //pd.show();
//        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.dialog_wave_progress_layout, null);
//        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams params = pd.getWindow().getAttributes();
//
//        //pd.setIndeterminateDrawable(this.getResources().getDrawable(R.drawable.anim_drawable_bg));
//        pd.setCancelable(false);
//        pd.setCanceledOnTouchOutside(false);
//        pd.getWindow().setAttributes(params);
//        pd.setContentView(view);
//        TextView tx=(TextView)view.findViewById(R.id.wave_message);
//        tx.setText("abcdefg");
//        switchTo(Locale.getDefault());


//        Intent intent = new Intent("action.configure.local");
//        ComponentName cn=new ComponentName("com.android.settings","com.android.settings.language.SystemLanguageServer");
//        intent.setComponent(cn);
//        intent.putExtra("id", 2);
//        startService(intent);
        simpleDateFormatFunction();
        Date mDate = new Date();
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
//        mDateStr = dateFormat.format(mDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E.MM.dd.yyyy", Locale.ENGLISH);
        String mDateStr = dateFormat.format(mDate);
        Log.w(LOG_TAG, "www ... mDateStr = " + mDateStr);

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM.dd EEEE", Locale.ENGLISH);
        String mDateStr2 = dateFormat2.format(mDate);
        Log.w(LOG_TAG, "www ... mDateStr2 = " + mDateStr2);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        // TODO Auto-generated method stub
        super.onTitleChanged(title, color);
        if (title != null) {
            //Log.w(LOG_TAG, "www ... onTitleChanged not null title = " + title);
            setConsumerActionBar("巡检任务");
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

    /**
     * SimpleDateFormat函数语法：
     * G 年代标志符
     * y 年
     * M 月
     * d 日
     * h 时表示12小时制，在上午或下午 (1~12)
     * H 时表示24小时制，在一天中 (0~23)
     * m 分
     * s 秒
     * S 毫秒
     * E 周几
     * D 一年中的第几天
     * F 一月中第几个星期几
     * w 一年中第几个星期
     * W 一月中第几个星期
     * a 上午 / 下午 标记符
     * k 时 在一天中 (1~24) （24小时制，比如上午9点就是9，不会再是09）
     * K 时 在上午或下午 (0~11) （12小时制， 比如上午9点就是9，不会再是09）
     * z 时区
     */

    private void simpleDateFormatFunction() {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        SimpleDateFormat myFmt1 = new SimpleDateFormat("yy/MM/dd HH:mm");
        SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
        SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        SimpleDateFormat myFmt4 = new SimpleDateFormat(
                "一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z时区");
        SimpleDateFormat myFmt5 = new SimpleDateFormat("yyyy年MM月dd日 ahh时mm分ss秒");
        Date now = new Date(); //在模拟器上运行，得到的时间正好比本地时间晚12个小时，模拟器设置里面的时区不对，设置的是北美时区，修改为中国时区就对了
        System.out.println(myFmt.format(now));  // 2019年10月12日 09时47分16秒   yyyy年MM月dd日 HH时mm分ss秒
        System.out.println(myFmt1.format(now)); // 19/10/12 09:47  yy/MM/dd HH:mm
        System.out.println(myFmt2.format(now)); // 2019-10-12 09:47:16 yyyy-MM-dd HH:mm:ss
        System.out.println(myFmt3.format(now)); // 2019年10月12日 09时47分16秒 周六 yyyy年MM月dd日 HH时mm分ss秒 E
        System.out.println(myFmt4.format(now)); // 一年中的第 285 天 一年中第41个星期 一月中第2个星期 在一天中9时 GMT+08:00时区 一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z时区
        System.out.println(now.toString()); // Sat Oct 12 10:06:08 GMT+08:00 2019
        System.out.println(now.toLocaleString()); //2019年10月12日 上午10:25:06
        System.out.println(myFmt5.format(now)); //2019年10月12日 上午10时30分39秒
        System.out.println();

        //年的不同长度表示
        SimpleDateFormat yFmt1 = new SimpleDateFormat("yyyy年");
        String year1 = (yFmt1.format(now)); //2019年
        SimpleDateFormat yFmt2 = new SimpleDateFormat("yyy年");
        String year2 = (yFmt2.format(now)); //2019年
        SimpleDateFormat yFmt3 = new SimpleDateFormat("yy年");
        String year3 = (yFmt3.format(now)); //19年
        SimpleDateFormat yFmt4 = new SimpleDateFormat("y年");
        String year4 = (yFmt4.format(now)); //2019年
        //月的不同长度表示
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");//yyyy-M-dd 和 yyyy-MM-dd 都可以表示2008-04-24 而且这里必须注意format的格式必须同字符串的划分根式相同，否则crash
        String dstr = "2008-04-24";
        try {
            Date date = sdf.parse(dstr); //Thu Apr 24 00:00:00 GMT+08:00 2008
            SimpleDateFormat mFmt1 = new SimpleDateFormat("yyyy-M-dd");
            String month1 = (mFmt1.format(date)); //2008-4-24
            SimpleDateFormat mFmt2 = new SimpleDateFormat("yyyy-MM-dd");
            String month2 = (mFmt2.format(date)); //2008-04-24
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //E 周几的不同长度表示
        Date date = new Date(); //Thu Apr 24 00:00:00 GMT+08:00 2008
        SimpleDateFormat eFmt1 = new SimpleDateFormat("这是E");
        String e1 = (eFmt1.format(date)); //周六 如果系统语言是英文的，那么就是Sat
        SimpleDateFormat eFmt2 = new SimpleDateFormat("这是EEEE");
        String e2 = (eFmt2.format(date)); //星期六 如果系统语言是英文的，那么就是Saturday
        System.out.println();

        //D 一年中的第几天不同长度表示
        Date date1 = new Date(); //Thu Apr 24 00:00:00 GMT+08:00 2008
        SimpleDateFormat dFmt1 = new SimpleDateFormat("这是D");
        String d1 = (dFmt1.format(date1)); //这是285
        SimpleDateFormat dFmt2 = new SimpleDateFormat("这是DDDD");
        String d2 = (dFmt2.format(date1)); //这是0285
        System.out.println();

        //F 一月中第几个星期几不同长度表示
        SimpleDateFormat fFmt1 = new SimpleDateFormat("这是第F个");
        String f1 = (fFmt1.format(new Date())); //这是第2个
        SimpleDateFormat fFmt2 = new SimpleDateFormat("这是第FFFF个");
        String f2 = (fFmt2.format(new Date())); //这是第0002个
        System.out.println();

        //w 一年中第几个星期不同长度表示
        SimpleDateFormat wFmt1 = new SimpleDateFormat("第w个星期");
        String w1 = (wFmt1.format(new Date())); //第41个星期
        SimpleDateFormat wFmt2 = new SimpleDateFormat("第wwww个星期");
        String w2 = (wFmt2.format(new Date())); //第0041个星期
        System.out.println();

        //W 一月中第几个星期不同长度表示
        SimpleDateFormat WFmt1 = new SimpleDateFormat("一月中第W个星期");
        String W1 = (WFmt1.format(new Date())); //一月中第2个星期
        SimpleDateFormat WFmt2 = new SimpleDateFormat("一月中第WWWW个星期");
        String W2 = (WFmt2.format(new Date())); //一月中第0002个星期
        System.out.println();

        //中英文表示上午和下午a 和 aa
        SimpleDateFormat aFmt = new SimpleDateFormat("ahh:mm");
        String a = (aFmt.format(new Date())); //下午 03:12 或者 PM 03:15
        SimpleDateFormat aaFmt = new SimpleDateFormat("aahh:mm");
        String aa = (aaFmt.format(new Date())); //下午 03:12 或者 PM 03:15
        SimpleDateFormat a2Fmt = new SimpleDateFormat("hh:mma");
        String a2 = (a2Fmt.format(new Date())); //03:14 下午 或者 03:15 PM
        SimpleDateFormat aa2Fmt = new SimpleDateFormat("hh:mmaa");
        String aa2 = (aa2Fmt.format(new Date())); //03:14 下午 或者 03:15 PM
        System.out.println();

        //小时的不同长度表示，12小时制h / hh 或者24小时制 H / HH
        SimpleDateFormat hFmt = new SimpleDateFormat("h:mm");
        String h1 = (hFmt.format(new Date())); // 3:59
        SimpleDateFormat hFmt2 = new SimpleDateFormat("hh:mm");
        String h2 = (hFmt2.format(new Date())); // 03:59
        System.out.println();
    }
}