package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 空闲模式监控服务
 * 确保持续监控设备空闲状态
 */
public class IdleModeService extends Service {
    private static final String TAG = "IdleModeService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "IdleModeService 已创建");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "IdleModeService 已启动");
        
        // 返回 START_STICKY 确保服务被杀死后会重新启动
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "IdleModeService 已销毁");
        
        // 服务被销毁时，尝试重新启动
        Intent restartService = new Intent(this, IdleModeService.class);
        startService(restartService);
    }
}

