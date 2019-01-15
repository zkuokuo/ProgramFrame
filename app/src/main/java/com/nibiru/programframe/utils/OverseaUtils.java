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

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2017/10/20
 * 描述:判断是不是海外版本的方法
 */
public class OverseaUtils {
    public static IVRDataService mService = null;

    public static boolean getOverSea(XBaseXRActivity activity) {
        if (activity.getNVRManagerService() != null) {
            BaseUrlConfig.isOversea = activity.isStoreOversea();
            if (BaseUrlConfig.ISTEST) {
                BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV_TEST;
                BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL_TEST;
            } else if (!BaseUrlConfig.isOversea) {
                BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV;
                BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL;
            } else {
                BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV_OS;
                BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL_OS;
            }
        } else {
            Intent intent_service = getServiceIntent(null, activity);
            if (intent_service != null) {
                activity.bindService(intent_service, serviceConnection, BIND_AUTO_CREATE);
            }
        }
        return false;
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
     * @des 判断是不是海外版的远程调用
     */
    private static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IVRDataService.Stub.asInterface(service);
            if (mService != null) {
                try {
                    BaseUrlConfig.isOversea = mService.isStoreOversea();
                    if (BaseUrlConfig.ISTEST) {
                        BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV_TEST;
                        BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL_TEST;
                    } else if (!BaseUrlConfig.isOversea) {
                        BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV;
                        BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL;
                    } else {
                        BaseUrlConfig.urlFav = BaseUrlConfig.URLFAV_OS;
                        BaseUrlConfig.domin_url = BaseUrlConfig.DOMIN_URL_OS;
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

    public static void unBindService(XBaseXRActivity activity) {
        if (mService != null) {
            activity.unbindService(serviceConnection);
            serviceConnection = null;
        }
    }
}
