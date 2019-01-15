package com.nibiru.programframe.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.nibiru.programframe.data.source.netdata.BaseUrlConfig;
import com.nibiru.vrconfig.IVRDataService;
import java.util.ArrayList;
import java.util.List;
import x.core.XBaseXRActivity;
import static android.content.Context.BIND_AUTO_CREATE;
import static com.nibiru.lib.vr.NibiruVR.getChannelCode;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2017/10/20
 * 描述:获取渠道号进行判断
 */
public class ChannelUtils {
    public static IVRDataService mService = null;
    /**
     * @des 判断是不是海外版的远程调用
     */
    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IVRDataService.Stub.asInterface(service);
            if (mService != null) {
                try {
                    BaseUrlConfig.isOversea = mService.isStoreOversea();
                    String channel = mService.getChannel();
                    if (channel.startsWith("MR")) {
//                        if (BaseUrlConfig.isOversea) {
//                            BaseUrlConfig.CHANNEL_OVERSEA = channel;
//                        } else {
//                            BaseUrlConfig.CHANNEL = channel;
//                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    /**
     * @des 获取是不是海外的, 同时获取渠道号
     */
    public static void getChannel(XBaseXRActivity context) {
        if (context.getNVRManagerService() != null) {
            String channel = getChannelCode();
            BaseUrlConfig.isOversea = context.isStoreOversea();
            if (channel.startsWith("MR")) {
//                if (LauncherHelper.isOversea) {
//                    LauncherHelper.CHANNEL_OVERSEA = channel;
//                } else {
//                    LauncherHelper.CHANNEL = channel;
//                }
            }
        } else {
            Intent intent_service = getServiceIntent(null, context);
            if (intent_service != null) {
                context.bindService(intent_service, serviceConnection, BIND_AUTO_CREATE);
            }
        }
    }

    private static Intent getServiceIntent(String packageName, XBaseXRActivity context) {
        List<ResolveInfo> mainList = context.getPackageManager().queryIntentServices(new Intent("com.nibiru.vrdata.service"), 0);
        List<Intent> mMatchIntents = new ArrayList<Intent>();
        if (mainList != null && mainList.size() > 0) {
            for (ResolveInfo info : mainList) {
                if (info != null && info.serviceInfo != null
                        && !TextUtils.isEmpty(info.serviceInfo.name)) {
                    if (packageName == null || TextUtils.equals(packageName, info.serviceInfo.packageName)) {
                        Intent intent = new Intent("com.nibiru.vrdata.service");
                        intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
                        intent.putExtra("packageName", info.serviceInfo.packageName);
                        mMatchIntents.add(intent);
                    }
                }
            }
        }
        if (mMatchIntents.size() > 0) {
            return mMatchIntents.get(0);
        }
        return null;
    }

    /**
     * @des 需要在activity的onDestroy方法中进行解除绑定
     */
    public static void unBindChannelService(XBaseXRActivity context) {
        if (mService != null) {
            context.unbindService(serviceConnection);
            serviceConnection = null;
        }
    }
}
