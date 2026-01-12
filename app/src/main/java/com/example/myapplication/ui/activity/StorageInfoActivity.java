package com.example.myapplication.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StatFs;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 存储空间信息Activity
 * 原名：ThrActivity
 */
public class StorageInfoActivity extends Activity {
    private static final String TAG = "StorageInfoActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_info);
        
        // 启用ActionBar并设置返回按钮
        // homeAsUpIndicator已在themes.xml中定义
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle("存储空间信息");
        }
        
        // 初始化ConsumerFormat按钮
        Button btnConsumerFormat = findViewById(R.id.btn_consumer_format);
        btnConsumerFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchConsumerFormatActivity();
            }
        });
        
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.android.webview", 0);
            Log.d(TAG, "version name: " + pi.versionName);
            Log.d(TAG, "version code: " + pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Android System WebView is not found");
        }
        WebView webView2 = new WebView(getApplicationContext());
        String useragent=webView2.getSettings().getUserAgentString();

        Log.e(TAG, "useragent: " + useragent);
        WebView webView = findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        webView.loadUrl("chrome://version");

        // 获取/mnt/internal存储空间信息
        getInternalStorageInfo();
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
        // StorageInfoActivity关闭时：下层Activity从左侧进入，StorageInfoActivity向右侧退出
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    /**
     * 启动DialogContainerActivity（测试用）
     */
    private void launchNotifActivity() {
        try {
//            Intent intent = new Intent(this, NotificationTestActivity.class);

            Intent intent = new Intent();
            intent.setAction("android.consumer.action.DialogContainerActivity");
            startActivity(intent);
            // StorageInfoActivity启动其他Activity：新Activity从右侧进入，StorageInfoActivity向左侧退出
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            Log.d(TAG, "Successfully launched DialogContainerActivity");
        } catch (Exception e) {
            String errorMsg = "Failed to launch DialogContainerActivity: " + e.getMessage();
            Log.e(TAG, errorMsg, e);
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     * 启动ConsumerFormatActivity
     */
    private void launchConsumerFormatActivity() {
        try {
            Intent intent = new Intent();
            intent.setAction("com.android.settings.deviceinfo.action.ConsumerFormatActivity");
            
            // 尝试使用ActivityOptions设置水平动画（API 16+）
            try {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.anim_slide_in_right,  // 进入动画：从右侧滑入
                    R.anim.anim_slide_out_left   // 退出动画：向左侧滑出
                );
                startActivity(intent, options.toBundle());
                Log.d(TAG, "Successfully launched ConsumerFormatActivity with ActivityOptions");
            } catch (Exception optionsException) {
                // 如果ActivityOptions失败，使用传统方式
                Log.w(TAG, "ActivityOptions failed, fallback to traditional method");
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        } catch (Exception e) {
            String errorMsg = "Failed to launch ConsumerFormatActivity: " + e.getMessage();
            Log.e(TAG, errorMsg, e);
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取/mnt/internal存储空间信息
     */
    private void getInternalStorageInfo() {
        String path = "/mnt/internal";
        try {
            File file = new File(path);
            if (!file.exists()) {
                Log.e(TAG, "Path does not exist: " + path);
                updateStorageInfoDisplay("Path does not exist: " + path);
                return;
            }

            if (!file.canRead()) {
                Log.e(TAG, "Cannot read path: " + path);
                updateStorageInfoDisplay("Cannot read path: " + path);
                return;
            }

            StatFs statFs = new StatFs(path);
            
            // 获取总空间和可用空间（以字节为单位）
            long totalBytes = statFs.getTotalBytes();
            long availableBytes = statFs.getAvailableBytes();
            long usedBytes = totalBytes - availableBytes;
            
            // 转换为MB和GB
            double totalMB = totalBytes / (1024.0 * 1024.0);
            double totalGB = totalMB / 1024.0;
            double availableMB = availableBytes / (1024.0 * 1024.0);
            double availableGB = availableMB / 1024.0;
            double usedMB = usedBytes / (1024.0 * 1024.0);
            double usedGB = usedMB / 1024.0;

            //log
            Log.i(TAG, "===before /mnt/internal Storage Raw Data ===");
            Log.i(TAG, "Total Bytes: " + totalGB);
            Log.i(TAG, "Available Bytes: " + availableGB);
            Log.i(TAG, "Used Bytes: " + usedGB);
            // 计算使用率
            double usagePercent = (double) usedBytes / totalBytes * 100;
            
            // 格式化显示
            DecimalFormat df = new DecimalFormat("#.##");
            
            // 同时使用三种方法获取存储大小
            long usedBytesDu = getUsedSpaceByDu(path);
            long usedBytesDf = getUsedSpaceByDf(path);
            long usedBytesRecursive = getUsedSpaceByRecursive(path);
            
            // 计算三种方法的MB值
            double usedMBStatFs = usedBytes / (1024.0 * 1024.0);
            double usedMBDu = usedBytesDu > 0 ? usedBytesDu / (1024.0 * 1024.0) : 0;
            double usedMBDf = usedBytesDf > 0 ? usedBytesDf / (1024.0 * 1024.0) : 0;
            double usedMBRecursive = usedBytesRecursive > 0 ? usedBytesRecursive / (1024.0 * 1024.0) : 0;
            
            // 输出三种方法的对比结果（MB单位）
            Log.i(TAG, "=== Three Methods Comparison (MB) ===");
            Log.i(TAG, "StatFs used: " + df.format(usedMBStatFs) + " MB");
            Log.i(TAG, "du command: " + (usedBytesDu > 0 ? df.format(usedMBDu) + " MB" : "Failed"));
            Log.i(TAG, "df command: " + (usedBytesDf > 0 ? df.format(usedMBDf) + " MB" : "Failed"));
            Log.i(TAG, "recursive: " + (usedBytesRecursive > 0 ? df.format(usedMBRecursive) + " MB" : "Failed"));
            
            // 选择最准确的方法（优先使用du命令）
            long selectedUsedBytes = usedBytes;
            String selectedMethod = "StatFs";
            double selectedUsedMB = usedMBStatFs;
            
            if (usedBytesDu > 0) {
                selectedUsedBytes = usedBytesDu;
                selectedMethod = "du command";
                selectedUsedMB = usedMBDu;
            } else if (usedBytesDf > 0) {
                selectedUsedBytes = usedBytesDf;
                selectedMethod = "df command";
                selectedUsedMB = usedMBDf;
            } else if (usedBytesRecursive > 0) {
                selectedUsedBytes = usedBytesRecursive;
                selectedMethod = "recursive calculation";
                selectedUsedMB = usedMBRecursive;
            }
            
            Log.i(TAG, "Selected method: " + selectedMethod + " - " + df.format(selectedUsedMB) + " MB");
            
            // 使用选择的方法更新数据
            if (selectedUsedBytes != usedBytes) {
                usedBytes = selectedUsedBytes;
                usedMB = selectedUsedMB;
                usedGB = usedMB / 1024.0;
                usagePercent = (double) usedBytes / totalBytes * 100;
            }
            // 构建详细的对比信息
            StringBuilder comparisonInfo = new StringBuilder();
            comparisonInfo.append("=== 存储空间对比分析 ===\n");
            comparisonInfo.append("路径: ").append(path).append("\n\n");
            
            comparisonInfo.append("=== 三种方法对比 (MB) ===\n");
            comparisonInfo.append("StatFs: ").append(df.format(usedMBStatFs)).append(" MB\n");
            comparisonInfo.append("du命令: ").append(usedBytesDu > 0 ? df.format(usedMBDu) + " MB" : "失败").append("\n");
            comparisonInfo.append("df命令: ").append(usedBytesDf > 0 ? df.format(usedMBDf) + " MB" : "失败").append("\n");
            comparisonInfo.append("递归计算: ").append(usedBytesRecursive > 0 ? df.format(usedMBRecursive) + " MB" : "失败").append("\n\n");
            
            comparisonInfo.append("=== 最终结果 ===\n");
            comparisonInfo.append("选择方法: ").append(selectedMethod).append("\n");
            comparisonInfo.append("总空间: ").append(df.format(totalMB)).append(" MB (").append(String.format("%.2f", totalGB)).append(" GB)\n");
            comparisonInfo.append("已使用: ").append(df.format(usedMB)).append(" MB (").append(String.format("%.2f", usedGB)).append(" GB)\n");
            comparisonInfo.append("可用空间: ").append(df.format(availableMB)).append(" MB (").append(String.format("%.2f", availableGB)).append(" GB)\n");
            comparisonInfo.append("使用率: ").append(String.format("%.1f", usagePercent)).append("%");
            
            String info = comparisonInfo.toString();
            
            // 输出到Log
            Log.i(TAG, "=== /mnt/internal Storage Information ===");
            Log.i(TAG, "total: " + df.format(totalMB) + " MB (" + String.format("%.2f", totalGB) + " GB)");
            Log.i(TAG, "used: " + df.format(usedMB) + " MB (" + String.format("%.2f", usedGB) + " GB)");
            Log.i(TAG, "available: " + df.format(availableMB) + " MB (" + String.format("%.2f", availableGB) + " GB)");
            Log.i(TAG, "usagePercent: " + String.format("%.1f", usagePercent) + "%");
            
            // 更新界面显示
            updateStorageInfoDisplay(info);
        } catch (Exception e) {
            String errorMsg = "Failed to get storage information: " + e.getMessage();
            Log.e(TAG, errorMsg, e);
            updateStorageInfoDisplay(errorMsg);
        }
    }

    /**
     * 使用du命令获取已使用空间
     */
    private long getUsedSpaceByDu(String path) {
        try {
            Process process = Runtime.getRuntime().exec("du -sb " + path);
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 1) {
                        try {
                            long usedBytes = Long.parseLong(parts[0]);
                            reader.close();
                            process.waitFor();
                            return usedBytes;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Failed to parse du command result: " + e.getMessage());
                        }
                    }
                }
            }
            reader.close();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "Failed to execute du command: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * 使用df命令获取已使用空间
     */
    private long getUsedSpaceByDf(String path) {
        try {
            Process process = Runtime.getRuntime().exec("df " + path);
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(path)) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        try {
                            long used = Long.parseLong(parts[2]) * 1024;  // 转换为字节
                            reader.close();
                            process.waitFor();
                            return used;
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Failed to parse df command result: " + e.getMessage());
                        }
                    }
                }
            }
            reader.close();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "Failed to execute df command: " + e.getMessage());
        }
        return -1;
    }
    
    /**
     * 使用递归计算获取已使用空间
     */
    private long getUsedSpaceByRecursive(String path) {
        try {
            long size = calculateDirectorySize(new File(path));
            return size > 0 ? size : -1;
        } catch (Exception e) {
            Log.e(TAG, "Failed to calculate directory size: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * 递归计算目录大小
     */
    private long calculateDirectorySize(File directory) {
        long size = 0;
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        size += calculateDirectorySize(file);
                    } else {
                        size += file.length();
                    }
                }
            }
        }
        return size;
    }

    /**
     * 更新存储信息显示
     */
    private void updateStorageInfoDisplay(String info) {
        TextView storageInfoText = findViewById(R.id.storage_info_text);
        if (storageInfoText != null) {
            storageInfoText.setText(info);
        }
    }
}

