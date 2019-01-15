package com.nibiru.programframe.crashManager;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/1
 * 描述:CrashHandler.getInstance().init(context);
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //文件夹目录
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash_log/";
    //文件名
    private static final String FILE_NAME = "crash_";
    //文件名后缀
    private static final String FILE_NAME_SUFFIX = ".txt";
    //上下文
    private Context mContext;
    //单例模式
    private static CrashHandler sInstance = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    public void init(Context context) {
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
    }

    /**
     * 捕获异常回掉
     *
     * @param thread 当前线程
     * @param ex     异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", ex);
        //导出异常信息到SD卡
        dumpExceptionToSDCard(ex);
        //返回键
        SceneInstanceManager.getInstance().finishTopScene();
        SystemClock.sleep(1000);
    }


    /**
     * 导出异常信息到sd卡
     * 同时文件通过邮箱发送
     */
    private void dumpExceptionToSDCard(final Throwable ex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CrashUtils.dumpExceptionToSDCard(ex, mContext);
            }
        }).start();
    }
}
