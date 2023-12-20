package com.example.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.view.NumKeyboard;
import com.example.myapplication.view.PasswordEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SecActivity extends Activity {
    final static String LOG_TAG = SecActivity.class.getSimpleName();
    FrameLayout frameLayout;
    Toolbar mToolbar;
    public static final String SCALE_FREE = "자유";
    public static final String SCALE_SQUARE = "정사각형";
    private View mDeleteButton;
    private PasswordEditText mPasswordEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad_layout);
//        mPasswordEntry = findViewById(R.id.pinEntry);
//        mDeleteButton = findViewById(R.id.delete_button);
//        //mDeleteButton.setVisibility(View.GONE);
//        mDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // check for time-based lockouts
//                if (mPasswordEntry.isEnabled()) {
//                    mPasswordEntry.deleteLastChar();
//                }
//            }
//        });

//        mPasswordEntry.setTextChangeListener(new PasswordEditText.TextChangeListener() {
//            @Override
//            public void onTextChangeListener(int size) {
//                mKey_size=size;
//                if (size >= 4 && mPasswordEntry.isEnabled()) {
//                    verifyPasswordAndUnlock();
//                }else if(size>0){
//                    mKey_return.setText(R.string.kg_pin_delete);
//                }else if(size==0){
//                    mKey_return.setText(R.string.kg_pin_return);
//                }
//            }
//        });

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