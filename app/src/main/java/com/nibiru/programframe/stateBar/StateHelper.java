package com.nibiru.programframe.stateBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class StateHelper {
    /**
     * 蓝牙状态
     */
    public static final int BLUETOOTH_STATE_OFF = 0;
    public static final int BLUETOOTH_STATE_ON = 1;
    public static final int BLUETOOTH_STATE_CONNECTED = 2;
    /**
     * WIFI状态
     */
    public static final int WIFI_STATE_OFF = 0;
    public static final int WIFI_STATE_ON = 5;
    public static final int WIFI_STATE1 = 1;
    public static final int WIFI_STATE2 = 2;
    public static final int WIFI_STATE3 = 3;
    public static final int WIFI_STATE4 = 4;
    /**
     * SDcard状态
     */
    public static final int SDCARD_OUT = 0;
    public static final int SDCARD_IN = 1;
    /**
     * 耳机状态
     */
    public static final int HEADSET_OUT = 0;
    public static final int HEADSET_IN = 1;
    /**
     * 电池状态
     */
    public static final int BATTERY_NOT_POWER = 0;
    public static final int BATTERY_POWER = 1;

    /**
     * 机器是不是静音
     */
    public static final int SOUND_OFF = 0;
    public static final int SOUND_ON = 1;

    public static boolean isWifiAvailable = false;

    public static int batteryLevelValue = 100;
    public static int bluetoothState;
    public static int wifiState;
    public static int sdcardState;
    public static int headsetState;
    public static int batteryState;
    public static int soundState;
    public static SimpleDateFormat format;

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurTimeStr() {
        if (format == null) {
            format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            format.setTimeZone(TimeZone.getDefault());
        }
        String time = format.format(Calendar.getInstance().getTime());
        return time;
    }

}
