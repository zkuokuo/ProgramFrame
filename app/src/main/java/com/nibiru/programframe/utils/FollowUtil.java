package com.nibiru.programframe.utils;

import x.core.constant.Constant;
import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XBaseScene;
import x.core.ui.XImage;

/**
 * 公司:nibiru
 * 时间:2017/12/4
 * 描述:场景跟随移动的类
 * 用法: 1.在场景中或基类的oncreate方法中创建对象 FollowUtil mFollowUtil = new FollowUtil(this);
 * 2.在onResume中调用 mFollowUtil.onResume();
 * 3.在update方法中调用 mFollowUtil.followMethod();
 */
public class FollowUtil {
    Runnable mCheckFollowRunnable = null;
    public XImage bg;
    private XBaseScene mXBaseScene;
    private long FOLLOW_GAZE_DELAY = 300;
    private long mLastCheckTime = 0;
    private boolean isfirst_execute = true;

    public FollowUtil(XBaseScene scene) {
        this.mXBaseScene = scene;
        mXBaseScene.setSystemDialogEnableShowOnGazePosition(false);
        init();
    }

    /**
     * 创建一个黑色的挡板,这个挡板的作用是触发移动的范围
     */
    private void init() {
        bg = new XImage("transparent.png");
//        bg = new XImage("app_11.png");
        bg.setSize(CalculateUtils.transformSize(2600), CalculateUtils.transformSize(2000));
        bg.setCenterPosition(0, 0, Constants.CENTER_Z_BG - 0.2f);
        bg.setEventListener(new IXActorEventListener() {
            @Override
            public void onGazeEnter(XActor xActor) {
                checkFollowGazeState();
            }

            @Override
            public void onGazeExit(XActor xActor) {
                checkFollowGazeState();
            }

            @Override
            public boolean onGazeTrigger(XActor xActor) {
                return false;
            }

            @Override
            public void onAnimation(XActor xActor, boolean b) {

            }
        });
        bg.setName("tranbg");
        bg.setRenderOrder(0);
        mXBaseScene.addActor(bg);
    }

    public void resetresumeSD(String dialogName) {
        if (Constant.DIALOG_POWER.equals(dialogName) && bg != null) {
            bg.resumeSD();
        }
    }

    public void onResume() {
        if (!isfirst_execute) {
            mLastCheckTime = 0;
            mCheckFollowRunnable = null;
            followMethod();
        }
        isfirst_execute = false;
    }

    private void checkFollowGazeState() {
//        if (!Constants.isOpenFollow) {
//            onDestory();
//            return;
//        }
        synchronized (this) {
            if (mCheckFollowRunnable == null) {
                mCheckFollowRunnable = new CheckFollowGazeRunnable();
                if (mXBaseScene != null && mXBaseScene.isRunning()) {
                    mXBaseScene.runOnMainThreadDelayed(mCheckFollowRunnable, FOLLOW_GAZE_DELAY);
                } else {
                    mCheckFollowRunnable = null;
                }
            }
        }
    }

    /**
     * 伴随移动的方法
     */
    public void followMethod() {
        //二次检查，防止Gaze事件未触发导致跟随功能失效，无需每帧检查
        if (mCheckFollowRunnable == null && System.currentTimeMillis() - mLastCheckTime > 1000 && mXBaseScene != null && mXBaseScene.isRunning()) {
            mLastCheckTime = System.currentTimeMillis();
            checkFollowGazeState();
        }
    }

    public void onDestory() {
        mCheckFollowRunnable = null;
        if (mXBaseScene != null) {
            mXBaseScene.disableFollowGaze();
        }
    }

    /**
     * 点击ok键,让场景停止或开始跟随
     */
//    public boolean click_ok_stop_flolow(int keyCode) {
//        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
//            Constants.isOpenFollow = !Constants.isOpenFollow;
//            return true;
//        }
//        return false;
//    }

    class CheckFollowGazeRunnable implements Runnable {
        public void run() {
            synchronized (FollowUtil.this) {
                if (bg == null || !bg.isCreated() || mXBaseScene == null || !mXBaseScene.isRunning()) {
                    mCheckFollowRunnable = null;
                    return;
                }
                boolean enableFollowGaze;
                if (bg.isGazeInner()) {
                    enableFollowGaze = false;
                } else {
                    enableFollowGaze = true;
                }
                if (mXBaseScene.isFollowGaze() != enableFollowGaze) {
                    if (enableFollowGaze) {
                        mXBaseScene.enableFollowGaze(0, 0, Constants.CENTER_Z);
                    } else {
                        mXBaseScene.disableFollowGaze();
                    }
                }
                mCheckFollowRunnable = null;
            }
        }
    }
}




