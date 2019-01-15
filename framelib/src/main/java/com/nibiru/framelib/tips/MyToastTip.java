package com.nibiru.framelib.tips;

import android.graphics.Bitmap;

import java.util.Timer;
import java.util.TimerTask;

import x.core.ui.XBaseScene;
import x.core.ui.XImageText;
import x.core.ui.XLabel;
import x.core.ui.XToast;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/9
 * 描述:toast提示
 */

public class MyToastTip {

    private static final String TAG = Class.class.getSimpleName();
    private XBaseScene mScene;
    private float center_z = -1.4f;
    private XToast toast;
    private XImageText mScanResult;
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;


    public MyToastTip(XBaseScene scene) {
        this.mScene = scene;
    }

    /**
     * 有背景的toast提示
     */
    public void showtoast(String msg, int textColor, String bgimage, int during) {
        if (!mScene.isRunning()) return;
        if (mScanResult == null) {
            mScanResult = new XImageText(bgimage, bgimage);
            mScanResult.setSize(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(100f));
            mScanResult.getImage().setSize(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(100f));
            mScanResult.setCenterPosition(0, TipsCalculateUtils.transformCenterY(100f, 690f), center_z);
            mScanResult.setSizeOfTitle(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(40f));
            mScanResult.setTitlePosition(0, 0);
            mScanResult.setTitle(msg, msg);
            mScanResult.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mScanResult.setTitleColor(textColor, textColor);
            mScanResult.setRenderOrder(50);
            mScene.addActor(mScanResult);
        } else {
            mScanResult.setEnabled(true);
            mScanResult.setImgName(bgimage, bgimage);
            mScanResult.setTitle(msg, msg);
            mScanResult.setTitleColor(textColor, textColor);
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mScanResult.setEnabled(false);
            }
        };
        mTimer.schedule(mTimerTask, during);
    }

    /**
     * 有背景的toast提示
     */
    public void showtoast(String msg, int textColor, Bitmap bgimage, int during) {
        if (!mScene.isRunning()) return;
        if (mScanResult == null) {
            mScanResult = new XImageText(bgimage, bgimage);
            mScanResult.setSize(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(100f));
            mScanResult.getImage().setSize(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(100f));
            mScanResult.setCenterPosition(0, TipsCalculateUtils.transformCenterY(100f, 690f), center_z);
            mScanResult.setSizeOfTitle(TipsCalculateUtils.transformSize(600f), TipsCalculateUtils.transformSize(40f));
            mScanResult.setTitlePosition(0, 0);
            mScanResult.setTitle(msg, msg);
            mScanResult.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mScanResult.setTitleColor(textColor, textColor);
            mScanResult.setRenderOrder(50);
            mScene.addActor(mScanResult);
        } else {
            mScanResult.setEnabled(true);
            mScanResult.setImageBitmap(bgimage, bgimage);
            mScanResult.setTitle(msg, msg);
            mScanResult.setTitleColor(textColor, textColor);
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mScanResult.setEnabled(false);
            }
        };
        mTimer.schedule(mTimerTask, during);
    }

    /**
     * 没有背景的toast提示
     */
    public void showtoast(String msg, int textcolor, int during) {
        if (!mScene.isRunning()) return;
        toast = XToast.makeToast(mScene, msg, during);
        toast.setColor(textcolor);
        toast.setSize(TipsCalculateUtils.transformSize(600), TipsCalculateUtils.transformSize(50));
        toast.setGravity(0, 0, center_z);
        toast.setRenderOrder(50);
        toast.show();
    }

    /**
     * 隐藏没有背景的提示的toast
     */
    public void hidetoast() {
        if (toast != null) {
            if (toast.isEnabled()) {
                toast.cancel();
            }
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        if (mScanResult != null) {
            mScanResult.setEnabled(false);
        }
    }
}
