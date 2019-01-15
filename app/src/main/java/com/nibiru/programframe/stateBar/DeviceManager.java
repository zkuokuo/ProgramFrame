package com.nibiru.programframe.stateBar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DeviceManager {
    private static String TAG = "DeviceManager";
    /* 获取总设备列表 */
    private ArrayList<String> localDevicesList;
    /* 获取SD卡设备路径列表 */
    private ArrayList<String> sdDevicesList;
    /* 获取USB设备路径列表 */
    private ArrayList<String> usbDevicesList;
    /* 获取sata设备路径列表 */
    private ArrayList<String> sataDevicesList;
    /* 获取内部存储设备路径列表 */
    private ArrayList<String> internalDevicesList;
    private Context mContext;
    private StorageManager manager;

    public DeviceManager(Context mContext) {
        this.mContext = mContext;
        /* 获取总设备列表 */
        localDevicesList = new ArrayList<>();
        String[] volumeList = null;
        manager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Method mMethodGetPaths = null;
        try {
            mMethodGetPaths = manager.getClass().getMethod("getVolumePaths");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            volumeList = (String[]) mMethodGetPaths.invoke(manager);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < volumeList.length; i++) {
            localDevicesList.add(volumeList[i]);
        }
        /* 获取内部存储设备路径列表 */
        internalDevicesList = new ArrayList<>();
        internalDevicesList.add(Environment.getExternalStorageDirectory().getPath());
        sdDevicesList = new ArrayList<>();
        usbDevicesList = new ArrayList<>();
        sataDevicesList = new ArrayList<>();
        String path;
        for (int i = 0; i < localDevicesList.size(); i++) {
            path = localDevicesList.get(i);
            if (!path.equals(Environment.getExternalStorageDirectory()
                    .getPath())) {
                if (path.contains("sd")) {
                    /* 获取SD卡设备路径列表 */
                    sdDevicesList.add(path);
                } else if (path.contains("usb")) {
                    /* 获取USB设备路径列表 */
                    usbDevicesList.add(path);
                } else if (path.contains("sata")) {
                    /* 获取sata设备路径列表 */
                    sataDevicesList.add(path);
                }
            }
        }
    }

    public void addSdPath(String sdString) {
        if (!sdDevicesList.contains(sdString)
                && !usbDevicesList.contains(sdString)
                && !sataDevicesList.contains(sdString)) {
            sdDevicesList.add(sdString);
        }
    }

    public boolean isLocalDevicesRootPath(String path) {
        for (int i = 0; i < localDevicesList.size(); i++) {
            if (path.equals(localDevicesList.get(i)))
                return true;
        }
        return false;
    }

    /**
     * 获取总设备的列表
     * @return
     */
    public ArrayList<String> getLocalDevicesList() {
        //采用深复制
        return (ArrayList<String>) localDevicesList.clone();
    }

    public boolean isInterStoragePath(String path) {
        if (internalDevicesList.contains(path)) {
            return true;
        }
        return false;
    }

    public boolean isSdStoragePath(String path) {
        if (sdDevicesList.contains(path)) {
            return true;
        }
        return false;
    }

    public boolean isUsbStoragePath(String path) {
        if (usbDevicesList.contains(path)) {
            return true;
        }
        return false;
    }

    public boolean isSataStoragePath(String path) {
        if (sataDevicesList.contains(path)) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getSdDevicesList() {
        return (ArrayList<String>) sdDevicesList.clone();
    }

    public ArrayList<String> getUsbDevicesList() {
        return (ArrayList<String>) usbDevicesList.clone();
    }

    public ArrayList<String> getInternalDevicesList() {
        return (ArrayList<String>) internalDevicesList.clone();
    }

    public ArrayList<String> getSataDevicesList() {
        return (ArrayList<String>) sataDevicesList.clone();
    }

    /**
     * @des 判断是不是有过个分区
     */
    public boolean hasMultiplePartition(String dPath) {
        try {
            File file = new File(dPath);
            String minor = null;
            String major = null;
            for (int i = 0; i < localDevicesList.size(); i++) {
                if (dPath.equals(localDevicesList.get(i))) {
                    String[] list = file.list();
                    if (0 == list.length) {
                        return false;
                    }
                    for (int j = 0; j < list.length; j++) {
                        /* 如果目录命名规则不满足"主设备号:次设备号"(当前分区的命名规则),则返回false */
                        int lst = list[j].lastIndexOf("_");
                        if (lst != -1 && lst != (list[j].length() - 1)) {
                            major = list[j].substring(0, lst);
                            minor = list[j].substring(lst + 1, list[j].length());
                            try {
                                Integer.valueOf(major);
                                Integer.valueOf(minor);
                            } catch (NumberFormatException e) {
                                /* 如果该字符串不能被解析为数字,则退出 */
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "hasMultiplePartition() exception e");
            return false;
        }
    }

    /**
     * @des 获取网络设备列表
     */
    public ArrayList<String> getNetDeviceList() {
        SharedPreferences pref = mContext.getSharedPreferences("Device", 0);
        String list = pref.getString("Net", null);
        if (list != null) {
            String[] split = list.split(",");
            if (split != null) {
                ArrayList<String> devList = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    devList.add(split[i]);
                }
                return devList;
            }
        }
        return null;
    }

    /**
     * @des 保存联网设备
     */
    public void saveNetDevice(String devPath) {
        if (devPath == null) {
            return;
        }
        SharedPreferences pref = mContext.getSharedPreferences("Device", 0);
        SharedPreferences.Editor editor = pref.edit();
        String list = pref.getString("Net", null);
        if (list == null) {
            editor.putString("Net", devPath);
        } else {
            list = list + "," + devPath;
            editor.putString("Net", list);
        }
        editor.commit();
    }

    /**
     * @des 删除联网设备
     */
    public void delNetDevice(String devPath) {
        if (devPath == null) {
            return;
        }
        ArrayList<String> list = getNetDeviceList();
        list.remove(devPath);
        String st = null;
        for (int i = 0; i < list.size(); i++) {
            if (st == null) {
                st = list.get(i);
            } else {
                st = st + "," + list.get(i);
            }
        }
        SharedPreferences pref = mContext.getSharedPreferences("Device", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Net", st);
        editor.commit();
    }
}
