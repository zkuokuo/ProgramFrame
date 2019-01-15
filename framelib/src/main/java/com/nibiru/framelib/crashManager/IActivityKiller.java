package com.nibiru.framelib.crashManager;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/6
 * 描述:
 */
import android.os.Message;

public interface IActivityKiller {

    void finishLaunchActivity(Message message);

    void finishResumeActivity(Message message);

    void finishPauseActivity(Message message);

    void finishStopActivity(Message message);


}