package com.nibiru.programframe.stateBar;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import com.nibiru.framelib.utils.MLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatMonitor {

    private Context mContext;
    private SoundReceiver soundReceiver;
    private HeadsetReceiver headsetReceiver;
    private NetworkStateReceiver netStateReceiver;
    private BatteryChangedReceiver batteryChangedReceiver;
    private BluetoothStateReceiver bluetoothStateReceiver;
    private TFStateReceiver tfBroadcastReceiver;
    private List<OnStatChangeListener> listener = new ArrayList<OnStatChangeListener>();
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private DeviceManager mDeviceManager;
    private WifiManager mWifiManager;

    public StatMonitor(Context context) {
        this.mContext = context;
        if (mDeviceManager == null)
            mDeviceManager = new DeviceManager(mContext);
    }

    public void initMonitor() {
        //网络变化监听
        netStateReceiver = new NetworkStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mContext.registerReceiver(netStateReceiver, filter);

        //音量变化监听
        soundReceiver = new SoundReceiver();
        filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        mContext.registerReceiver(soundReceiver, filter);

        //耳机插拔监听
        headsetReceiver = new HeadsetReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        mContext.registerReceiver(headsetReceiver, filter);

        //电量变化监听
        batteryChangedReceiver = new BatteryChangedReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        Intent batteryStatus = mContext.registerReceiver(batteryChangedReceiver, filter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
        int level = batteryStatus.getIntExtra("level", 0);
        int scale = batteryStatus.getIntExtra("scale", 100);
        double percent = level * 100 / (double) scale;
        if (listener != null && listener.size() > 0) {
            for (OnStatChangeListener lis : listener) {
                lis.onBatteryPower(isCharging);
                lis.onBatteryChanged((int) percent);
            }
        }
        //tf卡有没有挂载监听
        tfBroadcastReceiver = new TFStateReceiver();
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addDataScheme("file");
        mContext.registerReceiver(tfBroadcastReceiver, filter);

        //蓝牙监听
        bluetoothStateReceiver = new BluetoothStateReceiver();
        filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        mContext.registerReceiver(bluetoothStateReceiver, filter);

        handleTFState();
        handleNetworkState();
        handleBTState(-1);
    }

    public void finishMonitor() {
        if (netStateReceiver != null) {
            mContext.unregisterReceiver(netStateReceiver);
        }
        if (batteryChangedReceiver != null) {
            mContext.unregisterReceiver(batteryChangedReceiver);
        }
        if (bluetoothStateReceiver != null) {
            mContext.unregisterReceiver(bluetoothStateReceiver);
        }
        if (tfBroadcastReceiver != null) {
            mContext.unregisterReceiver(tfBroadcastReceiver);
        }

        if (soundReceiver != null) {
            mContext.unregisterReceiver(soundReceiver);
        }

        if (headsetReceiver != null) {
            mContext.unregisterReceiver(headsetReceiver);
        }

        listener = null;
    }

    /**
     * @des 获取蓝牙的状态
     */
    @SuppressLint("NewApi")
    private void handleBTState(int state) {
        if (state < 0) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter == null) {
                //说明蓝牙没有打开
                state = BluetoothAdapter.STATE_OFF;
            } else {
                state = adapter.getState();
                if (state == BluetoothAdapter.STATE_ON) {
                    if (adapter.getProfileConnectionState(4) == BluetoothProfile.STATE_CONNECTED) {
                        state = BluetoothAdapter.STATE_CONNECTED;
                    } else if (adapter.getProfileConnectionState(BluetoothProfile.A2DP) ==
                            BluetoothProfile.STATE_CONNECTED) {
                        state = BluetoothAdapter.STATE_CONNECTED;
                    } else if (adapter.getProfileConnectionState(BluetoothProfile.GATT) ==
                            BluetoothProfile.STATE_CONNECTED) {
                        state = BluetoothAdapter.STATE_CONNECTED;
                    } else if (adapter.getProfileConnectionState(BluetoothProfile.HEADSET) ==
                            BluetoothProfile.STATE_CONNECTED) {
                        state = BluetoothAdapter.STATE_CONNECTED;
                    } else if (adapter.getProfileConnectionState(BluetoothProfile.HEALTH) ==
                            BluetoothProfile.STATE_CONNECTED) {
                        state = BluetoothAdapter.STATE_CONNECTED;
                    }
                }
            }
        } else if (state == BluetoothAdapter.STATE_CONNECTING || state == BluetoothAdapter
                .STATE_DISCONNECTED || state == BluetoothAdapter.STATE_DISCONNECTING) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter == null) {
                state = BluetoothAdapter.STATE_OFF;
            } else {
                state = adapter.getState();
            }
        }
        if (listener != null && listener.size() > 0) {
            for (OnStatChangeListener lis : listener) {
                lis.onBluetoothStateChanged(state);
            }
        }
    }

    /**
     * 获取wifi信号的强度等级
     */
    int getRssi() {
        if (mWifiManager == null) {
            mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        }
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return 0;
        }
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 4);
        return level;
    }

    @SuppressLint("NewApi")
    private void handleTFState() {
        MLog.d("" + mDeviceManager.getSdDevicesList());
        if (mDeviceManager.getSdDevicesList() != null && mDeviceManager.getSdDevicesList().size() != 0) {
            for (int i = 0; i < mDeviceManager.getSdDevicesList().size(); i++) {
                if (android.os.Environment.getStorageState(new File(mDeviceManager.getSdDevicesList().get(i))).equals(
                        android.os.Environment.MEDIA_MOUNTED) && !mDeviceManager.getSdDevicesList
                        ().get(i).contains("internal_sd")) {
                    if (listener != null && listener.size() > 0) {
                        MLog.d("handleTFState: " + true);
                        for (OnStatChangeListener lis : listener) {
                            lis.onTFStateChanged(true);
                        }
                    }
                    return;
                }
            }
        }
        if (listener != null && listener.size() > 0) {
            MLog.d("handleTFState: " + false);
            for (OnStatChangeListener lis : listener) {
                lis.onTFStateChanged(false);
            }
        }
    }


    private void handleNetworkState() {
        //获得网络连接管理类
        connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                if (listener != null && listener.size() > 0) {
                    for (OnStatChangeListener lis : listener) {
                        lis.onNetworkStateChanged(info.getType(), 0, getRssi());
                    }
                }
            } else {
                if (listener != null && listener.size() > 0) {
                    for (OnStatChangeListener lis : listener) {
                        lis.onNetworkStateChanged(0, 0, getRssi());
                    }
                }
            }
        } else {
            if (listener != null && listener.size() > 0) {
                for (OnStatChangeListener lis : listener) {
                    lis.onNetworkStateChanged(0, 0, getRssi());
                }
            }
        }
    }

    /**
     * @des 获取音量的是不是静音
     */
    public void handleSoundState(Context context) {
        if (getAudioVolume(context) == 0) {
            if (listener != null && listener.size() > 0) {
                for (OnStatChangeListener lis : listener) {
                    lis.onSoundStateChanged(true);
                }
            }
        } else {
            if (listener != null && listener.size() > 0) {
                for (OnStatChangeListener lis : listener) {
                    lis.onSoundStateChanged(false);
                }
            }
        }
    }


    public int getAudioVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * @des 获取设备可用的内存空间
     */
    public long getAvailableMemory() {
        MemoryInfo mInfo = new MemoryInfo();
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mInfo);
        long availableMem = mInfo.availMem / 1048576L;
        return availableMem;

    }

    public List<OnStatChangeListener> getListener() {
        return listener;
    }

    public void setListener(OnStatChangeListener lis) {
        if (listener == null) {
            listener = new ArrayList<OnStatChangeListener>();
        }
        if (!listener.contains(lis)) {
            listener.add(lis);
        }
    }

    public void setControlService() {
        handleBTState(-1);
    }

    public interface OnStatChangeListener {
        public void onBluetoothStateChanged(int state);

        public void onNetworkStateChanged(int nettype, int state, int rssi);

        public void onBatteryChanged(int state);

        public void onBatteryPower(boolean state);

        public void onTFStateChanged(boolean state);

        public void onHeadsetStateChanged(boolean state);

        public void onSoundStateChanged(boolean state);

    }

    public class SoundReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                if (getAudioVolume(context) == 0) {
                    if (listener != null && listener.size() > 0) {
                        for (OnStatChangeListener lis : listener) {
                            lis.onSoundStateChanged(true);
                        }
                    }
                } else {
                    if (listener != null && listener.size() > 0) {
                        for (OnStatChangeListener lis : listener) {
                            lis.onSoundStateChanged(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * @des 获取耳机有没有插上的广播接收器类
     */
    public class HeadsetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", 0);
                if (state == 0) {
                    if (listener != null && listener.size() > 0) {
                        for (OnStatChangeListener lis : listener) {
                            lis.onHeadsetStateChanged(false);
                        }
                    }
                } else if (state == 1) {
                    if (listener != null && listener.size() > 0) {
                        for (OnStatChangeListener lis : listener) {
                            lis.onHeadsetStateChanged(true);
                        }
                    }
                }
                handleSoundState(mContext);
            }
        }
    }

    public class TFStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction() == Intent.ACTION_MEDIA_UNMOUNTED) {
                handleTFState();
            } else if (intent.getAction() == Intent.ACTION_MEDIA_MOUNTED) {
                if (intent.getData() != null) {
                    String path = intent.getData().getPath();
                    mDeviceManager.addSdPath(path);
                }
                handleTFState();
            }
        }
    }

    public class NetworkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                handleNetworkState();
            } else if (WifiManager.RSSI_CHANGED_ACTION.equals(intent.getAction())) {
                handleNetworkState();
            }
        }
    }

    public class BatteryChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                double percent = level * 100 / (double) scale;

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

                if (listener != null && listener.size() > 0) {
                    for (OnStatChangeListener lis : listener) {
                        lis.onBatteryPower(isCharging);
                        lis.onBatteryChanged((int) percent);
                    }
                }
            } else if (Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())) {
            } else if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            }
        }

    }

    public class BluetoothStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED
                    .equals(intent.getAction())) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.STATE_OFF);
                handleBTState(state);
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(intent.getAction()
            )) {
                int state = intent.getIntExtra(
                        BluetoothAdapter.EXTRA_CONNECTION_STATE,
                        BluetoothAdapter.STATE_DISCONNECTED);
                handleBTState(state);
            }
        }

    }
}
