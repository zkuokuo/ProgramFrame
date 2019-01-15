package com.nibiru.programframe.stateBar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;

import com.nibiru.programframe.utils.CalculateUtils;
import com.nibiru.programframe.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XBaseScene;
import x.core.ui.XImage;
import x.core.ui.XLabel;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/2/12
 * 描述:监听机器各种状态
 */
public class StatueView implements IXActorEventListener {
    private XBaseScene mScene;
    private XLabel timeLabel;
    private XImage batteryImage;
    public XImage batteryProgress;
    private XLabel batteryLvLabel;
    private int power = -1;
    private XImage wifiImage;
    private XImage bluetoothImage;
    private XImage batteryPower;
    private int bluetoothState;
    private int batteryState;
    private int wifiState;

    private String lastTime;
    private String lastTimeZone;
    private long mTimeStamp;
    private SimpleDateFormat mFormat;
    private float fixY = 0;
    private WifiManager mWifiManager;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    public boolean isNeedrefreshState = true;

    public StatueView(XBaseScene scene) {
        this.mScene = scene;
        ownInit();
    }

    public void ownInit() {
        mWifiManager = (WifiManager) mScene.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mBluetoothManager = (BluetoothManager) mScene.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        initStatusBar();
    }

    /**
     * 用来刷新显示的方法
     */
    public void refreshState() {
        if (isNeedrefreshState) {
            refreshStatusBar();
            refreshTime();
        }
    }

    public void initStatusBar() {
        timeLabel = new XLabel(StateHelper.getCurTimeStr() + " AM");
        timeLabel.setTextColor(Color.WHITE);
        timeLabel.setCenterPosition(CalculateUtils.transformCenterX(117, 800, 1440), CalculateUtils.transformCenterY(25, 212 + fixY, 1080), Constants.CENTER_Z);
        timeLabel.setAlignment(XLabel.XAlign.Right);
        timeLabel.setArrangementMode(XLabel.XArrangementMode.SingleRowNotMove);
        timeLabel.setSize(CalculateUtils.transformSize(117), CalculateUtils.transformSize(25));
        timeLabel.setName("time_label");
        mScene.addActor(timeLabel);

        bluetoothImage = new XImage("mr_bluetooth_off.png");
        bluetoothImage.setCenterPosition(CalculateUtils.transformCenterX(19, 933, 1440), CalculateUtils.transformCenterY(25, 210 + fixY, 1080), Constants.CENTER_Z);
        bluetoothImage.setName("bluetooth");
        bluetoothImage.setEventListener(this);
        bluetoothImage.setSize(CalculateUtils.transformSize(19), CalculateUtils.transformSize(25));
        mScene.addActor(bluetoothImage);

        wifiImage = new XImage("mr_wifi_off.png");
        wifiImage.setCenterPosition(CalculateUtils.transformCenterX(28, 967, 1440), CalculateUtils.transformCenterY(20, 214 + fixY, 1080), Constants.CENTER_Z);
        wifiImage.setName("wifi");
        wifiImage.setEventListener(this);
        wifiImage.setSize(CalculateUtils.transformSize(28), CalculateUtils.transformSize(20));
        mScene.addActor(wifiImage);

        batteryImage = new XImage("kuang.png");
        batteryImage.setCenterPosition(CalculateUtils.transformCenterX(50, 1011, 1440), CalculateUtils.transformCenterY(21, 213 + fixY, 1080), Constants.CENTER_Z);
        batteryImage.setSize(CalculateUtils.transformSize(50), CalculateUtils.transformSize(21));
        batteryImage.setName("battery");
        mScene.addActor(batteryImage);

        if (StateHelper.batteryLevelValue <= 15) {
            batteryProgress = new XImage("low_power.png");
        } else {
            batteryProgress = new XImage("dian.png");
        }
        batteryProgress.setCenterPosition(CalculateUtils.transformCenterX(40, 1014, 1440), CalculateUtils.transformCenterY(18, 215 + fixY, 1080), Constants.CENTER_Z + 0.0011f);
        batteryProgress.setSize(CalculateUtils.transformSize(40), CalculateUtils.transformSize(18));
        batteryProgress.setRenderOrder(8);
        mScene.addActor(batteryProgress);
        batteryProgress.setProgress(StateHelper.batteryLevelValue / 100f);

        batteryLvLabel = new XLabel(StateHelper.batteryLevelValue + "%");
        batteryLvLabel.setTextColor(Color.WHITE);
        batteryLvLabel.setCenterPosition(CalculateUtils.transformCenterX(50, 1070, 1440), CalculateUtils.transformCenterY(25, 213 + fixY, 1080), Constants.CENTER_Z + 0.001f);
        batteryLvLabel.setSize(CalculateUtils.transformSize(50), CalculateUtils.transformSize(25));
        batteryLvLabel.setArrangementMode(XLabel.XArrangementMode.SingleRowNotMove);
        batteryLvLabel.setName("batteryLabel");
        mScene.addActor(batteryLvLabel);

        batteryPower = new XImage("powering.png");
        batteryPower.setCenterPosition(CalculateUtils.transformCenterX(15, 998, 1440), CalculateUtils.transformCenterY(20, 213 + fixY, 1080), Constants.CENTER_Z + 0.002f);
        batteryPower.setSize(CalculateUtils.transformSize(15), CalculateUtils.transformSize(20));
        batteryPower.setName("batteryPower");
        batteryPower.setRenderOrder(9);
    }

    /**
     * 显示或者隐藏状态栏
     */
    public void showOrHideStartBar(boolean isshow) {
        if (timeLabel != null) {
            timeLabel.setEnabled(isshow);
        }
        if (bluetoothImage != null) {
            bluetoothImage.setEnabled(isshow);
        }
        if (wifiImage != null) {
            wifiImage.setEnabled(isshow);
        }
        if (batteryImage != null) {
            batteryImage.setEnabled(isshow);
        }
        if (batteryProgress != null) {
            batteryProgress.setEnabled(isshow);
        }
        if (batteryLvLabel != null) {
            batteryLvLabel.setEnabled(isshow);
        }
        if (batteryPower != null) {
            batteryPower.setEnabled(isshow);
        }
        isNeedrefreshState = isshow;
    }


    private void refreshStatusBar() {
        int tempPower = StateHelper.batteryLevelValue;
        if (tempPower == 0) {
            if (batteryProgress.isEnabled()) {
                batteryProgress.setEnabled(false);
            }
        } else {
            if (!batteryProgress.isEnabled()) {
                batteryProgress.setEnabled(true);
            }
        }
        if (power != tempPower && batteryProgress != null && batteryProgress.isCreated()) {
            power = tempPower;
            batteryLvLabel.setTextContent(power + "%");
            if (power <= 15) {
                batteryProgress.setImageName("low_power.png");
            } else {
                batteryProgress.setImageName("dian.png");
            }
            batteryProgress.setProgress(power / 100f);
        }

        int batteryStateTemp = StateHelper.batteryState;
        if (batteryState != batteryStateTemp) {
            batteryState = batteryStateTemp;
            if (batteryState == StateHelper.BATTERY_POWER) {
                mScene.addActor(batteryPower);
            } else if (batteryState == StateHelper.BATTERY_NOT_POWER) {
                mScene.removeActor(batteryPower);
            }
        }

        int bluetoothStateTemp = StateHelper.bluetoothState;
        if (bluetoothState != bluetoothStateTemp) {
            bluetoothState = bluetoothStateTemp;
            if (bluetoothState == StateHelper.BLUETOOTH_STATE_ON) {
                bluetoothImage.setImageName("mr_bluetooth.png");
            } else if (bluetoothState == StateHelper.BLUETOOTH_STATE_OFF) {
                bluetoothImage.setImageName("mr_bluetooth_off.png");
            } else if (bluetoothState == StateHelper.BLUETOOTH_STATE_CONNECTED) {
                bluetoothImage.setImageName("mr_bluetooth.png");
            }
        }

        int wifiStateTemp = StateHelper.wifiState;
        if (wifiState != wifiStateTemp) {
            wifiState = wifiStateTemp;
            if (wifiState == StateHelper.WIFI_STATE_OFF) {
                wifiImage.setImageName("mr_wifi_off.png");
            } else if (wifiState == StateHelper.WIFI_STATE1) {
                wifiImage.setImageName("mr_wifi_1.png");
            } else if (wifiState == StateHelper.WIFI_STATE2) {
                wifiImage.setImageName("mr_wifi_2.png");
            } else if (wifiState == StateHelper.WIFI_STATE3) {
                wifiImage.setImageName("mr_wifi_3.png");
            } else if (wifiState == StateHelper.WIFI_STATE4) {
                wifiImage.setImageName("mr_wifi.png");
            } else if (wifiState == StateHelper.WIFI_STATE_ON) {
                wifiImage.setImageName("wifi_0.png");
            }
        }
    }

    private void refreshTime() {
        long timestamp = System.currentTimeMillis();
        if (!(mTimeStamp > 0)) {
            mTimeStamp = timestamp;
        } else if (!(timestamp - mTimeStamp > 50000)) {
            return;
        }
        mTimeStamp = timestamp;
        if (mFormat == null) {
            mFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            lastTimeZone = TimeZone.getDefault().getDisplayName();
            mFormat.setTimeZone(TimeZone.getDefault());
        }
        String time = mFormat.format(new Date(timestamp));
        if (!time.equals(lastTime)) {
            String currTimeZone = TimeZone.getDefault().getDisplayName();
            if (lastTimeZone != null && !lastTimeZone.equals(currTimeZone)) {
                mFormat.setTimeZone(TimeZone.getDefault());
                lastTimeZone = currTimeZone;
            }
            lastTime = time;
            String[] split = lastTime.split(":");
            if (split != null && split.length == 2) {
                int i = Integer.parseInt(split[0]);
                if (i <= 12) {
                    timeLabel.setTextContent(lastTime + "  AM");
                } else {
                    i -= 12;
                    String tt = "";
                    if (i < 10) {
                        tt = "0" + i;
                    } else {
                        tt = i + "";
                    }
                    timeLabel.setTextContent(tt + ":" + split[1] + "  PM");
                }
            } else {
                timeLabel.setTextContent(lastTime + "  AM");
            }
        }
    }

    //跳转到设置wifi列表的场景
    private void goToWIfiScene() {
        Intent intent = new Intent();
        intent.setAction("android.nibiru.settings.WIFI_SETTINGS");
        intent.putExtra("isWifi", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mScene.startAppActivity(intent, false);
    }

    //跳转到设置bluetooth场景
    private void goToBlueToothScene() {
        Intent intent = new Intent();
        intent.setAction("android.nibiru.settings.BLUE_TOOTH");
        intent.putExtra("isBlueTooth", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mScene.startAppActivity(intent, false);
    }

    @Override
    public void onGazeEnter(XActor xActor) {

    }

    @Override
    public void onGazeExit(XActor xActor) {

    }

    @Override
    public boolean onGazeTrigger(XActor xActor) {
        String name = xActor.getName();
        if ("bluetooth".equals(name)) {
            goToBlueToothScene();
            return true;
        } else if ("wifi".equals(name)) {
            goToWIfiScene();
        }
        return false;
    }

    @Override
    public void onAnimation(XActor xActor, boolean b) {

    }

}
