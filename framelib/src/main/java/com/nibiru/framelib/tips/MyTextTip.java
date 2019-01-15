package com.nibiru.framelib.tips;

import android.graphics.Color;

import x.core.ui.XBaseScene;
import x.core.ui.XLabel;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/9
 * 描述:
 */

public class MyTextTip {
    private XBaseScene mScene;
    private XLabel error;
    private float center_z = -1.4f;

    public MyTextTip(XBaseScene scene) {
        this.mScene = scene;
    }

    public void showTextTip(String content) {
        if (mScene.isRunning()) {
            hideTextTip();
            if (error == null) {
                error = new XLabel(content);
                error.setSize(TipsCalculateUtils.transformSize(600), TipsCalculateUtils.transformSize(30));
                error.setCenterPosition(0, 0, center_z);
                error.setTextColor(Color.WHITE);
                error.setAlignment(XLabel.XAlign.Center);
                error.setRenderOrder(50);
                mScene.addActor(error);
            } else {
                error.setEnabled(true);
                error.setTextContent(content);
            }
        }
    }

    public void showTextTip(String content, int textColor) {
        if (mScene.isRunning()) {
            hideTextTip();
            if (error == null) {
                error = new XLabel(content);
                error.setSize(TipsCalculateUtils.transformSize(600), TipsCalculateUtils.transformSize(30));
                error.setCenterPosition(0, 0, center_z);
                error.setTextColor(textColor);
                error.setAlignment(XLabel.XAlign.Center);
                error.setRenderOrder(50);
                mScene.addActor(error);
            } else {
                error.setEnabled(true);
                error.setTextContent(content);
            }
        }
    }

    public void hideTextTip() {
        if (error != null && error.isEnabled()) {
            error.setEnabled(false);
        }
    }
}
