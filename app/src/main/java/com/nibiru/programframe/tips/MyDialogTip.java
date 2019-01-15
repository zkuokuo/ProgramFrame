package com.nibiru.programframe.tips;

import android.graphics.Color;
import android.text.TextUtils;
import com.nibiru.programframe.utils.Constants;
import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XActorGroup;
import x.core.ui.XBaseScene;
import x.core.ui.XImageText;
import x.core.ui.XLabel;
import x.core.ui.XPanel;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/9
 * 描述:弹窗提示
 */
public class MyDialogTip implements IXActorEventListener {
    private static final String TAG = Class.class.getSimpleName();
    private XBaseScene mScene;
    private XPanel dialogbg;
    private MyOnclickLisener onclickLisener;

    public MyDialogTip(XBaseScene scene) {
        this.mScene = scene;

    }

    public void setoncickLisener(MyOnclickLisener onclickLisener) {
        this.onclickLisener = onclickLisener;

    }

    /**
     * 显示dialog提示
     */
    public void showDialog(String titlestr, String contentstr, String leftbtn, String rightbtn) {
        initDialog(titlestr, contentstr, leftbtn, rightbtn);
    }

    /**
     * 隐藏dialog
     */
    public void hideDialog() {
        if (dialogbg != null && dialogbg.isEnabled()) {
            dialogbg.setEnabled(false);
        }
    }

    /**
     * 获取dialog的显示状态
     */
    public boolean dialogisShow() {
        if (dialogbg != null) {
            return dialogbg.isEnabled() ? true : false;
        } else {
            return false;
        }
    }

    /**
     * 对dialog的布局进行初始化
     */
    private void initDialog(String titlestr, String contentstr, String leftbtn, String rightbtn) {
        if (dialogbg == null) {
            dialogbg = new XPanel();
            dialogbg.setBackGround("dialogbg.png");
            dialogbg.setSize(TipsCalculateUtils.transformSize(401), TipsCalculateUtils.transformSize(220));
            dialogbg.setRenderOrder(20);
            dialogbg.setCenterPosition(TipsCalculateUtils.transformCenterX(401, 845), TipsCalculateUtils.transformCenterY(220, 435), Constants.CENTER_Z + 0.1f);

            XLabel title = new XLabel(titlestr + "");
            title.setSize(TipsCalculateUtils.transformSize(300), TipsCalculateUtils.transformSize(24));
            title.setTextColor(Color.WHITE);
            title.setAlignment(XLabel.XAlign.Center);
            title.setName("title");
            dialogbg.addChild(title, new XActorGroup.LayoutParam(TipsCalculateUtils.transformCenterX(300, 50, 400), TipsCalculateUtils.transformCenterY(24, 10, 220), 0.001f, 4));
            if (TextUtils.isEmpty(titlestr)) {
                title.setEnabled(false);
            }

            XLabel content = new XLabel(contentstr + "");
            content.setName("content");
            content.setAlignment(XLabel.XAlign.Center);
            content.setTextColor(Color.WHITE);
            content.setSize(TipsCalculateUtils.transformSize(400), TipsCalculateUtils.transformSize(24));
            content.setMaxLine(2);
            content.setArrangementMode(XLabel.XArrangementMode.MultiRow);
            dialogbg.addChild(content, new XActorGroup.LayoutParam(TipsCalculateUtils.transformCenterX(400, 5, 401), TipsCalculateUtils.transformCenterY(24, 104, 220), 0.001f, 4));
            if (TextUtils.isEmpty(contentstr)) {
                content.setEnabled(false);
            }

            XImageText cancle = new XImageText("btnbgb", "btnbgb");
            cancle.setSize(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(30));
            cancle.setName("cancle");
            cancle.setSizeOfImage(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(30));
            cancle.setTitle(leftbtn, leftbtn);
            cancle.setSizeOfTitle(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(18));
            cancle.setTitlePosition(0, 0);
            cancle.setEventListener(this);
            dialogbg.addChild(cancle, new XActorGroup.LayoutParam(TipsCalculateUtils.transformCenterX(80, 70, 401), TipsCalculateUtils.transformCenterY(30, 169, 220), 0.001f, 4));
            if (TextUtils.isEmpty(leftbtn)) {
                cancle.setEnabled(false);
            }

            XImageText confirm = new XImageText("btnbgb", "btnbgb");
            confirm.setSize(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(30));
            confirm.setName("confirm");
            confirm.setSizeOfImage(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(30));
            confirm.setTitle(rightbtn, rightbtn);
            confirm.setSizeOfTitle(TipsCalculateUtils.transformSize(80), TipsCalculateUtils.transformSize(18));
            confirm.setTitlePosition(0, 0);
            confirm.setEventListener(this);
            dialogbg.addChild(confirm, new XActorGroup.LayoutParam(TipsCalculateUtils.transformCenterX(80, 250, 401), TipsCalculateUtils.transformCenterY(30, 169, 220), 0.001f, 4));
            if (TextUtils.isEmpty(rightbtn)) {
                confirm.setEnabled(false);
            }
            mScene.addActor(dialogbg);
        } else {
            dialogbg.setEnabled(true);
            XLabel title = (XLabel) dialogbg.getChild("title");
            if (title != null) {
                if (!TextUtils.isEmpty(titlestr)) {
                    title.setEnabled(true);
                    title.setTextContent(titlestr + "");
                } else {
                    title.setEnabled(false);
                }
            }
            XLabel content = (XLabel) dialogbg.getChild("content");
            if (content != null) {
                if (!TextUtils.isEmpty(contentstr)) {
                    content.setTextContent(contentstr + "");
                } else {
                    content.setEnabled(false);
                }
            }
            XImageText cancle = (XImageText) dialogbg.getChild("cancle");
            if (cancle != null) {
                if (!TextUtils.isEmpty(leftbtn)) {
                    cancle.setEnabled(true);
                    cancle.setTitle(leftbtn + "", leftbtn + "");
                } else {
                    cancle.setEnabled(false);
                }
            }
            XImageText confirm = (XImageText) dialogbg.getChild("confirm");
            if (confirm != null) {
                if (!TextUtils.isEmpty(rightbtn)) {
                    confirm.setEnabled(true);
                    confirm.setTitle(rightbtn + "", rightbtn + "");
                } else {
                    confirm.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onGazeEnter(XActor xActor) {

    }

    @Override
    public void onGazeExit(XActor xActor) {

    }

    @Override
    public boolean onGazeTrigger(XActor xActor) {
        String name = xActor.getName();
        if (name.equals("cancle")) {
            if (onclickLisener != null) {
                onclickLisener.onclickLeftbtn();
            }
            return true;
        } else if (name.equals("confirm")) {
            if (onclickLisener != null) {
                onclickLisener.onclickRightbtn();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onAnimation(XActor xActor, boolean b) {

    }

}
