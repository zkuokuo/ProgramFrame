package com.nibiru.programframe.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/14
 * 描述:用来管理跳转的类
 */

public class SkipUtils {
    /**
     * 跳转到收藏的链接码页面的方法
     */
    public static void goToFAV(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.FAV");
        intent.putExtra("isSettings", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
