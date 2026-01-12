package com.example.myapplication.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;

/**
 * 通知测试Activity
 * 原名：NotifActivity
 */
public class NotificationTestActivity extends Activity {
    private static final String TAG = "NotificationTestActivity";
    private AlertDialog mAlertDialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        mContext = this;
        
        // 启用ActionBar并设置返回按钮
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle("通知测试");
        }
        //检查通知权限是否开启
        NotificationManagerCompat notification = NotificationManagerCompat.from(mContext);
        boolean isEnabled = notification.areNotificationsEnabled();
        //如果没有开启通知权限，跳转到通知设置页面
        if (!isEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("请开启通知权限");
            builder.setPositiveButton("确定", (dialog, which) -> {
                Intent intent = new Intent();
                //通知设置action
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
                mContext.startActivity(intent);
            });
            builder.setNegativeButton("取消", (dialog, which) -> {
                dialog.dismiss();
            });
            mAlertDialog = builder.create();
            mAlertDialog.show();
        }

//        showNotification_low(mContext);
        Button btn1 = (Button) findViewById(R.id.bt1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                send1Notification();
//                showNotification_low(mContext);
                sendGoogleNotification();
            }
        });

        Button btn2 = (Button) findViewById(R.id.bt2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add1Notification();
            }
        });

        Button btn3 = (Button) findViewById(R.id.bt3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send10Notification();
            }
        });

        Button btn4 = (Button) findViewById(R.id.bt4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAutoCancelNotification_low();
            }
        });
//        showAutoCancelNotification_low();
    }

    public void sendGoogleNotification() {
        //26似乎不行,不过还能在notification中使用对应的接口
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String id = "channel_1"; //自定义设置通道ID属性
        String description = "123";//自定义设置通道描述属性
        int importance = NotificationManager.IMPORTANCE_HIGH;//通知栏管理重要提示消息声音设定
        NotificationChannel mChannel = new NotificationChannel(id, "123", importance);//建立通知栏通道类（需要有ID，重要属性）
        notificationManager.createNotificationChannel(mChannel);
        // Get the layouts to use in the custom notification
//        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
//        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);
// Apply the layouts to the notification.

        String CHANNEL_ID = "1";
        String CHANNEL_Name = "channel_name";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Notification customNotification = new NotificationCompat.Builder(this, id)
                .setSmallIcon(android.R.drawable.stat_notify_voicemail)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentTitle("custom notification")
                .setShowWhen(false)
                .setOngoing(false)
                .build();

        notificationManager.notify(666, customNotification);
    }

    public void send1Notification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, "channel_id")
                .setContentTitle("Notification Title low")
                .setContentText("Notification Content showNotification_low")
                .setSmallIcon(android.R.drawable.star_off)
                .setPriority(Notification.PRIORITY_LOW)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .build();

        notificationManager.notify(1, notification);
    }

    int mNotificationNum = 0;

    public void add1Notification() {
        int NOTIFICATION_ID = (int) (Math.random() * 10000);
        String channelId = "random_channel_ID is " + mNotificationNum;
        String channelName = "random_channel_NAME " + mNotificationNum;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{200, 500,300, 400});
        notificationManager.createNotificationChannel(channel);
//通知添加点击跳转
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        
        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("Notification add low")
                .setSmallIcon(android.R.drawable.star_off)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.star_off))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(contentIntent) // 设置点击通知后跳转的意图
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void send10Notification() {
        mNotificationNum = 0;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        for (int i = 0; i < 10; i++) {
            mNotificationNum++;
            String channelId = "random_channel_ID is " + mNotificationNum;
            String channelName = "random_channel_NAME " + mNotificationNum;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle("Notification 10 batch")
                    .setContentText("Notification Content showNotification_low")
                    .setSmallIcon(android.R.drawable.star_off)
                    .setPriority(Notification.PRIORITY_LOW)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(new long[]{0, 1000, 500, 1000})
                    .build();

            notificationManager.notify(mNotificationNum, notification);
        }

    }


    private void showNotification_default(Context context) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
        /*
        Notification.PRIORITY_DEFAULT
        Notification.PRIORITY_HIGH
        Notification.PRIORITY_LOW
        Notification.PRIORITY_MAX
        Notification.PRIORITY_MIN
         */
        Notification notification = new Notification.Builder(this, "channel_id")
                .setContentTitle("Notification Title def")
                .setContentText("Notification Content showNotification_default")
                .setSmallIcon(android.R.drawable.star_big_on)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .build();

        notificationManager.notify(1, notification);
    }

    private void showNotification_low(Context context) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE; // 或者使用 FLAG_MUTABLE，取决于你的需求
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flags);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name21", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setSound(null, null);
        channel.setBypassDnd(true);
        channel.enableVibration(false);

        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, "channel_id")
                .setContentTitle("Notification Title low")
                .setContentText("Notification Content showNotification_low")
                .setSmallIcon(android.R.drawable.star_off)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setProgress(100, 20, false)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .build();

        notificationManager.notify(1, notification);
    }

    private void showAutoCancelNotification_low() {
        int NOTIFICATION_ID = (int) 10000;
        String channelId = "random_channel_ID is " + mNotificationNum;
        String channelName = "random_channel_NAME " + mNotificationNum;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{10, 10});
        notificationManager.createNotificationChannel(channel);

        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("Notification add low")
                .setSmallIcon(android.R.drawable.star_off)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.star_off))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOngoing(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
        //延时4s取消此通知
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    notificationManager.cancel(NOTIFICATION_ID);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 处理ActionBar的返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void finish() {
        super.finish();
        // NotificationTestActivity关闭时：下层Activity从左侧进入，NotificationTestActivity向右侧退出
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}

