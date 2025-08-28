package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class IdleModeReceiver extends BroadcastReceiver {
    private static final String TAG = "IdleModeReceiver";
    private static final String ACTION_AIRPLANE_MODE_TIMER = "com.example.myapplication.AIRPLANE_MODE_TIMER";
    private static final int AIRPLANE_MODE_DELAY = 10 * 60 * 1000; // 10分钟
    
    private static PendingIntent airplaneModePendingIntent;
    private static AlarmManager alarmManager;
    private static boolean isAirplaneModeEnabled = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "收到广播: " + action);

        if (action == null) return;

        switch (action) {
            case "android.intent.action.DEVICE_IDLE_MODE_CHANGED":
                handleIdleModeChanged(context);
                break;
            case "android.intent.action.SCREEN_ON":
                handleScreenOn(context);
                break;
            case "android.intent.action.SCREEN_OFF":
                handleScreenOff(context);
                break;
            case "android.intent.action.BOOT_COMPLETED":
                handleBootCompleted(context);
                break;
            case ACTION_AIRPLANE_MODE_TIMER:
                enableAirplaneMode(context);
                break;
        }
    }

    private void handleBootCompleted(Context context) {
        try {
            // 启动IdleModeService
            Log.d(TAG, "系统启动完成，启动IdleModeService");
            Intent serviceIntent = new Intent(context, IdleModeService.class);
            context.startService(serviceIntent);
            
            // 检查飞行模式状态
            boolean isAirplaneModeOn = AirplaneModeHelper.isAirplaneModeOn(context);
            Log.d(TAG, "开机检测飞行模式状态: " + (isAirplaneModeOn ? "已开启" : "已关闭"));
            
            if (isAirplaneModeOn) {
                Log.d(TAG, "检测到飞行模式已开启，正在自动关闭...");
                
                // 延迟执行，确保系统完全启动
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean success = AirplaneModeHelper.disableAirplaneMode(context);
                        if (success) {
                            Log.d(TAG, "开机自动关闭飞行模式成功");
                        } else {
                            Log.e(TAG, "开机自动关闭飞行模式失败");
                        }
                    }
                }, 3000); // 延迟3秒执行
            } else {
                Log.d(TAG, "飞行模式未开启，无需处理");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "处理开机事件时发生异常: " + e.getMessage());
        }
    }

    private void handleIdleModeChanged(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && powerManager.isDeviceIdleMode()) {
            Log.d(TAG, "设备进入light doze模式，设置10分钟后开启飞行模式");
            scheduleAirplaneMode(context);
        }
    }

    private void handleScreenOn(Context context) {
        Log.d(TAG, "屏幕点亮");
        
        // 取消定时任务
        cancelAirplaneModeTimer(context);
        
        // 如果飞行模式已开启，则关闭它
        if (isAirplaneModeEnabled || AirplaneModeHelper.isAirplaneModeOn(context)) {
            disableAirplaneMode(context);
        }
    }

    private void handleScreenOff(Context context) {
        Log.d(TAG, "屏幕关闭");
    }

    private void scheduleAirplaneMode(Context context) {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        Intent intent = new Intent(context, IdleModeReceiver.class);
        intent.setAction(ACTION_AIRPLANE_MODE_TIMER);
        
        airplaneModePendingIntent = PendingIntent.getBroadcast(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            long triggerTime = System.currentTimeMillis() + AIRPLANE_MODE_DELAY;
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                airplaneModePendingIntent
            );
            Log.d(TAG, "已设置飞行模式定时任务，将在10分钟后执行");
        }
    }

    private void cancelAirplaneModeTimer(Context context) {
        if (alarmManager != null && airplaneModePendingIntent != null) {
            alarmManager.cancel(airplaneModePendingIntent);
            airplaneModePendingIntent = null;
            Log.d(TAG, "已取消飞行模式定时任务");
        }
    }

    private void enableAirplaneMode(Context context) {
        boolean success = AirplaneModeHelper.enableAirplaneMode(context);
        if (success) {
            isAirplaneModeEnabled = true;
            Log.d(TAG, "已开启飞行模式");
        } else {
            Log.e(TAG, "开启飞行模式失败");
        }
    }

    private void disableAirplaneMode(Context context) {
        boolean success = AirplaneModeHelper.disableAirplaneMode(context);
        if (success) {
            isAirplaneModeEnabled = false;
            Log.d(TAG, "已关闭飞行模式");
        } else {
            Log.e(TAG, "关闭飞行模式失败");
        }
    }
} 