package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class AirplaneModeHelper {
    private static final String TAG = "AirplaneModeHelper";

    /**
     * 检查飞行模式是否已开启
     */
    public static boolean isAirplaneModeOn(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), 
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        } catch (Exception e) {
            Log.e(TAG, "无法获取飞行模式状态: " + e.getMessage());
            return false;
        }
    }

    /**
     * 设置飞行模式状态
     * 注意：这需要系统权限，在普通应用中可能无法工作
     */
    public static boolean setAirplaneMode(Context context, boolean enabled) {
        try {
            // 设置飞行模式状态
            Settings.Global.putInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON,
                enabled ? 1 : 0);

            // 发送广播通知系统飞行模式状态已改变
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", enabled);
            context.sendBroadcast(intent);

            Log.d(TAG, "飞行模式已" + (enabled ? "开启" : "关闭"));
            return true;
        } catch (SecurityException e) {
            Log.e(TAG, "没有权限修改飞行模式: " + e.getMessage());
            return false;
        } catch (Exception e) {
            Log.e(TAG, "设置飞行模式失败: " + e.getMessage());
            return false;
        }
    }

    /**
     * 开启飞行模式
     */
    public static boolean enableAirplaneMode(Context context) {
        return setAirplaneMode(context, true);
    }

    /**
     * 关闭飞行模式
     */
    public static boolean disableAirplaneMode(Context context) {
        return setAirplaneMode(context, false);
    }
} 