# OApplication - Android 应用项目

> 版本: 1.0.0  
> 更新日期: 2026-01-12  
> 最低 SDK: Android 13 (API 33)  
> 目标平台: Spreadtrum Android 设备

---

## 📋 目录

- [项目概述](#项目概述)
- [项目架构](#项目架构)
- [业务功能](#业务功能)
- [组件状态](#组件状态)
- [代码规范](#代码规范)
- [资源命名规范](#资源命名规范)
- [版本记录](#版本记录)
- [已知问题](#已知问题)

---

## 项目概述

本项目是一个 Android 原生应用，主要用于设备管理和系统功能测试。采用分层组件化架构，具有清晰的包结构和命名规范。

### 技术栈

- **语言**: Java
- **最低 SDK**: Android 13 (API 33)
- **构建工具**: Gradle
- **UI 框架**: Android Native (Activity + View)

---

## 项目架构

### 包结构

```
com.example.myapplication/
├── ui/                          # UI 层
│   ├── activity/                # Activity 组件
│   │   ├── MainActivity.java              # 主界面
│   │   ├── SecondaryActivity.java         # 次级界面（密码键盘）
│   │   ├── StorageInfoActivity.java       # 存储信息界面
│   │   └── NotificationTestActivity.java  # 通知测试界面
│   ├── adapter/                 # 适配器组件
│   │   ├── WifiSpinnerAdapter.java        # WiFi 下拉选择适配器
│   │   └── CustomSpinnerAdapter.java      # 通用下拉选择适配器
│   └── widget/                  # 自定义控件
│       ├── MarqueeTextView.java           # 跑马灯文本控件
│       ├── WifiSpinner.java               # WiFi 选择下拉框
│       ├── WifiExSpinner.java             # 扩展 WiFi 选择下拉框
│       ├── PieChartView.java              # 饼图控件
│       ├── PieDisplayView.java            # 饼图展示控件
│       └── password/            # 密码输入相关控件
│           ├── PasswordTextView.java      # 密码文本显示
│           ├── PasswordEditText.java      # 密码编辑框
│           ├── ConsumerPasswordTextView.java  # 消费者密码视图
│           ├── NumKeyboardView.java       # 数字键盘视图
│           └── NumPadKeyView.java         # 数字按键视图
├── service/                     # 服务层
│   ├── IdleModeService.java              # 设备空闲模式服务
│   └── IdleModeReceiver.java             # 空闲模式广播接收器
├── helper/                      # 工具辅助类
│   ├── AirplaneModeHelper.java           # 飞行模式控制辅助
│   └── RunHelper.java                    # 运行时辅助工具
└── utils/                       # 通用工具类（预留）
```

### 架构分层说明

| 层级 | 职责 | 包路径 |
|------|------|--------|
| **UI 层** | 界面展示、用户交互 | `ui.activity`, `ui.adapter`, `ui.widget` |
| **服务层** | 后台服务、广播接收 | `service` |
| **辅助层** | 业务辅助功能 | `helper` |
| **工具层** | 通用工具方法 | `utils` |

---

## 业务功能

### 1. 主界面 (MainActivity)
- WiFi 类型选择（WEP、WPA/WPA2 PSK、802.1x EAP）
- 网络类型切换（WiFi、移动网络、蓝牙、以太网）
- 自定义 ActionBar 跑马灯标题
- 自动启动 IdleModeService

### 2. 密码键盘界面 (SecondaryActivity)
- 4 位数字密码输入
- 自定义数字键盘布局
- 密码显示/隐藏动画
- 密码错误震动反馈

### 3. 存储信息界面 (StorageInfoActivity)
- 显示 `/mnt/internal` 存储空间信息
- 多种存储计算方式对比（StatFs、du、df、递归）
- 跳转系统格式化界面 (ConsumerFormatActivity)
- 深色主题支持
- 水平滑动动画

### 4. 通知测试界面 (NotificationTestActivity)
- 发送 Google 标准通知
- 批量发送通知测试（10条）
- 自动取消通知测试
- 通知权限检查和引导

### 5. 后台服务
- **IdleModeService**: 监听设备空闲模式变化
- **IdleModeReceiver**: 接收系统广播
  - `DEVICE_IDLE_MODE_CHANGED` - 空闲模式变化
  - `SCREEN_ON/OFF` - 屏幕开关
  - `BOOT_COMPLETED` - 开机完成

---

## 组件状态

### Activity 组件

| 组件名 | 状态 | 主题 | 入口 |
|--------|------|------|------|
| MainActivity | ✅ 可用 | 默认主题 | Intent Action |
| SecondaryActivity | ✅ 可用 | 默认主题 | Intent Action |
| StorageInfoActivity | ✅ 可用 | Theme.StorageInfoActivity | Intent Action |
| NotificationTestActivity | ✅ 可用 | Theme.NotificationTestActivity | **LAUNCHER** |

### 自定义 Widget 组件

| 组件名 | 状态 | 说明 |
|--------|------|------|
| MarqueeTextView | ✅ 可用 | 自动滚动的跑马灯文本 |
| WifiSpinner | ✅ 可用 | 全屏宽度下拉框 |
| WifiExSpinner | ✅ 可用 | 带右箭头的下拉框 |
| PieChartView | ✅ 可用 | 环形饼图 |
| PieDisplayView | ✅ 可用 | 3D 柱状饼图 |
| PasswordTextView | ✅ 可用 | 密码点动画显示 |
| PasswordEditText | ✅ 可用 | 密码圆点编辑框 |
| ConsumerPasswordTextView | ✅ 可用 | 消费者密码视图 |
| NumKeyboardView | ✅ 可用 | 数字键盘容器 |
| NumPadKeyView | ✅ 可用 | 单个数字按键 |

### Service 组件

| 组件名 | 状态 | 导出 | 说明 |
|--------|------|------|------|
| IdleModeService | ✅ 可用 | false | 后台空闲模式服务 |
| IdleModeReceiver | ✅ 可用 | true | 系统广播接收器 |

---

## 代码规范

### 命名规范

#### Java 类命名
- **Activity**: `功能名Activity` (如 `StorageInfoActivity`)
- **Service**: `功能名Service` (如 `IdleModeService`)
- **Receiver**: `功能名Receiver` (如 `IdleModeReceiver`)
- **Adapter**: `功能名Adapter` (如 `WifiSpinnerAdapter`)
- **自定义 View**: `功能名View` (如 `PieChartView`, `NumKeyboardView`)
- **Helper**: `功能名Helper` (如 `AirplaneModeHelper`)

#### 变量命名
- 成员变量: `m` 前缀驼峰命名 (如 `mContext`, `mAdapter`)
- 常量: 全大写下划线分隔 (如 `LOG_TAG`, `NOTIFICATION_ID`)
- 局部变量: 小写驼峰命名 (如 `intent`, `notificationManager`)

#### 方法命名
- 一般方法: 小写驼峰命名 (如 `onCreate`, `getStorageInfo`)
- 事件处理: `on` + 事件名 (如 `onClick`, `onItemSelected`)
- 工具方法: 动词 + 名词 (如 `calculateDirectorySize`, `formatBytes`)

### 代码风格

```java
// 类结构示例
public class ExampleActivity extends Activity {
    // 1. 常量定义
    private static final String TAG = "ExampleActivity";
    
    // 2. 成员变量
    private Context mContext;
    
    // 3. 生命周期方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ...
    }
    
    // 4. 公共方法
    public void publicMethod() { }
    
    // 5. 私有方法
    private void privateMethod() { }
    
    // 6. 内部类
    private class InnerClass { }
}
```

---

## 资源命名规范

### 布局文件 (layout/)

| 前缀 | 说明 | 示例 |
|------|------|------|
| `activity_` | Activity 布局 | `activity_storage_info.xml` |
| `view_` | 自定义 View 布局 | `view_num_keyboard.xml` |
| `dialog_` | 对话框布局 | `dialog_wave_progress_layout.xml` |
| `spinner_` | Spinner 相关布局 | `spinner_item.xml` |

### Drawable 资源 (drawable/)

| 前缀 | 说明 | 示例 |
|------|------|------|
| `ic_` | 图标资源 | `ic_arrow_back_blue.xml` |
| `bg_` / `bg` | 背景资源 | `bg.png` |
| `anim_` | 动画 Drawable | `anim_drawable_bg.xml` |
| `numpadkey_` | 数字键盘相关 | `numpadkey_background.xml` |
| `spinner_` | Spinner 相关 | `spinner_background.xml` |

### 动画资源 (anim/)

| 前缀 | 说明 | 示例 |
|------|------|------|
| `anim_slide_` | 滑动动画 | `anim_slide_in_right.xml` |
| `anim_fade_` | 淡入淡出动画 | `anim_fade_in.xml` |
| `anim_` | 其他动画 | `anim_shake.xml` |

### 主题/样式 (values/)

| 前缀 | 说明 | 示例 |
|------|------|------|
| `Theme.` | 主题定义 | `Theme.StorageInfoActivity` |
| `style_` | 通用样式 | `style_button_primary` |

---

## 版本记录

### v1.0.0 (2026-01-12)

#### 架构重构
- ✅ 采用分层组件化架构
- ✅ 重新组织包结构 (`ui`, `service`, `helper`, `utils`)
- ✅ 统一命名规范

#### Activity 迁移
- `ThrActivity` → `StorageInfoActivity`
- `NotifActivity` → `NotificationTestActivity`
- `SecActivity` → `SecondaryActivity`

#### Widget 迁移
- `MarqueeText` → `MarqueeTextView`
- `NumKeyboard` → `NumKeyboardView`
- `NumPadKey` → `NumPadKeyView`
- `PieChart` → `PieChartView`
- `PieView` → `PieDisplayView`

#### 布局更新
- 更新所有布局文件中的自定义 View 引用路径
- 修复 `activity_keypad_layout.xml` 类引用
- 修复 `activity_main.xml` 类引用
- 修复 `activity_txt_num.xml` 类引用

---

## 已知问题

### 待解决
| 问题 | 优先级 | 描述 |
|------|--------|------|
| 动画白边 | 中 | 从外部 Activity 返回时右侧可能出现白边 |
| Doze 模式 | 低 | 需要系统白名单或电池优化豁免 |

### 待优化
- [ ] `utils/` 包下工具类待补充
- [ ] 部分 drawable 资源命名待规范化（如 `gd.xml`, `ly.xml`）
- [ ] 旧备份文件清理（`*.java.bk`, `*.anmabk`）

---

## 开发指南

### 添加新 Activity

1. 在 `ui/activity/` 下创建 `功能名Activity.java`
2. 在 `res/layout/` 下创建 `activity_功能名.xml`
3. 在 `AndroidManifest.xml` 注册 Activity
4. 如需特殊主题，在 `themes.xml` 中定义

### 添加新 Widget

1. 在 `ui/widget/` 或 `ui/widget/子分类/` 下创建 `功能名View.java`
2. 在 `res/layout/` 下创建 `view_功能名.xml`（如需要）
3. 在 `res/values/attrs.xml` 添加自定义属性（如需要）

### 构建命令

```bash
# Debug 构建
./gradlew assembleDebug

# Release 构建
./gradlew assembleRelease

# 清理构建
./gradlew clean
```

---

## 联系方式

如有问题，请提交 Issue 或联系项目负责人。

