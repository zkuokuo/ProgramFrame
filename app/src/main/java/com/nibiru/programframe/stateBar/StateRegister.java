package com.nibiru.programframe.stateBar;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/13
 * 描述:
 */

public class StateRegister implements StatMonitor.OnStatChangeListener {
    private StatMonitor mStatMonitor;
    private Context mContext;

    public void StateRegister(Context context) {
        this.mContext = context;
    }

    /**
     * @des 机器各种状态的监听
     */
    public void registeStateAction(boolean isRegister) {
        if (isRegister) {
            mStatMonitor = new StatMonitor(mContext);
            mStatMonitor.setListener(this);
            mStatMonitor.initMonitor();
        } else {
            if (mStatMonitor != null) {
                mStatMonitor.finishMonitor();
            }
        }
    }

    /**
     * @des 蓝牙状态改变事件的回调
     */
    @Override
    public void onBluetoothStateChanged(int state) {
        if (state == BluetoothAdapter.STATE_CONNECTED) {
            StateHelper.bluetoothState = StateHelper.BLUETOOTH_STATE_CONNECTED;
        } else if (state == BluetoothAdapter.STATE_ON
                || state == BluetoothAdapter.STATE_CONNECTING
                || state == BluetoothAdapter.STATE_DISCONNECTED
                || state == BluetoothAdapter.STATE_DISCONNECTING) {
            StateHelper.bluetoothState = StateHelper.BLUETOOTH_STATE_ON;
        } else {
            StateHelper.bluetoothState = StateHelper.BLUETOOTH_STATE_OFF;
        }
    }

    /**
     * @des 网络状态改变的时间回调
     */
    @Override
    public void onNetworkStateChanged(int nettype, int state, int rssi) {
        if (nettype == ConnectivityManager.TYPE_WIFI && state == 0) {
            StateHelper.wifiState = StateHelper.WIFI_STATE_ON;
            if (rssi == 0) {
                StateHelper.wifiState = StateHelper.WIFI_STATE1;
            } else if (rssi == 1) {
                StateHelper.wifiState = StateHelper.WIFI_STATE2;
            } else if (rssi == 2) {
                StateHelper.wifiState = StateHelper.WIFI_STATE3;
            } else if (rssi == 3) {
                StateHelper.wifiState = StateHelper.WIFI_STATE4;
            }
            StateHelper.isWifiAvailable = true;
        } else {
            StateHelper.isWifiAvailable = false;
            StateHelper.wifiState = StateHelper.WIFI_STATE_OFF;
        }
    }

    /**
     * @des 电量改变的回调
     */
    @Override
    public void onBatteryChanged(int state) {
        StateHelper.batteryLevelValue = state;
    }

    /**
     * @des 是不是在充电的事件回调
     */
    @Override
    public void onBatteryPower(boolean state) {
        if (state) {
            StateHelper.batteryState = StateHelper.BATTERY_POWER;
        } else {
            StateHelper.batteryState = StateHelper.BATTERY_NOT_POWER;
        }
    }

    /**
     * @des TF卡有没有插入的回调
     */
    @Override
    public void onTFStateChanged(boolean state) {
        if (state) {
            StateHelper.sdcardState = StateHelper.SDCARD_IN;
        } else {
            StateHelper.sdcardState = StateHelper.SDCARD_OUT;
        }
    }

    /**
     * @des 耳机有没有插入的回调
     */
    @Override
    public void onHeadsetStateChanged(boolean state) {
        if (state) {
            StateHelper.headsetState = StateHelper.HEADSET_IN;
        } else {
            StateHelper.headsetState = StateHelper.HEADSET_OUT;
        }
    }

    /**
     * @des 音量状态变化的监听
     */
    @Override
    public void onSoundStateChanged(boolean state) {
        if (state) {
            StateHelper.soundState = StateHelper.SOUND_OFF;
        } else {
            StateHelper.soundState = StateHelper.SOUND_ON;
        }
    }
}
