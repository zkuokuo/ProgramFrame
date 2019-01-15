package com.nibiru.framelib.tips;

import x.core.action.XRotateByAction;
import x.core.ui.XBaseScene;
import x.core.ui.XImageText;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/9
 * 描述:加载中提示
 */

public class MyLoadingTip {
    private static final String TAG = Class.class.getSimpleName();
    private XBaseScene mScene;
    private XImageText mLoading;
    private float center_z = -1.4f;
    private XRotateByAction mRotate;

    public MyLoadingTip(XBaseScene scene) {
        this.mScene = scene;
    }


    public void showLoading(String loading_img) {
        initLoadActor(loading_img, center_z, "");
        mScene.runOnRenderThread(new Runnable() {
            @Override
            public void run() {
                if (mLoading != null) {
                    mLoading.setEnabled(true);
                    mRotate = new XRotateByAction(1000, 0, 0, -1000 * 360);
                    mLoading.runAction(mRotate);
                }
            }
        });
    }

    public void hideLoading() {
        if (isLoading()) {
            mScene.runOnRenderThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoading != null) {
                        if (mRotate != null) {
                            mRotate.setDuration(0);
                        }
                        mLoading.setEnabled(false);
                    }
                }
            });
        }
    }

    public boolean isLoading() {
        return mLoading != null && mLoading.isEnabled();
    }


    public void showLoading(String loading_img, String tiptxet) {
        initLoadActor(loading_img, center_z, tiptxet + "");
        mScene.runOnRenderThread(new Runnable() {
            @Override
            public void run() {
                if (mLoading != null) {
                    mLoading.setEnabled(true);
                    mRotate = new XRotateByAction(1000, 0, 0, -1000 * 360);
                    mLoading.getImage().runAction(mRotate);
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initLoadActor(String loading_img, float center_z, String titleSelected) {
        if (!mScene.isRunning()) return;
        if (isLoading()) {
            hideLoading();
        }
        if (mLoading == null) {
            mLoading = new XImageText(loading_img, loading_img);
            mLoading.setSize(TipsCalculateUtils.transformSize(100), TipsCalculateUtils.transformSize(100));
            mLoading.setSizeOfImage(TipsCalculateUtils.transformSize(100), TipsCalculateUtils.transformSize(100));
            mLoading.setCenterPosition(0, 0, center_z);
            mLoading.setRenderOrder(80);
            mLoading.setTitle(titleSelected, titleSelected);
            mLoading.setSizeOfTitle(TipsCalculateUtils.transformSize(250), TipsCalculateUtils.transformSize(30));
            mLoading.setTitlePosition(0, TipsCalculateUtils.transformSize(-70));
            mScene.addActor(mLoading);
        } else {
            mLoading.setImgName(loading_img, loading_img);
            mLoading.setTitle(titleSelected, titleSelected);
        }
    }
}
