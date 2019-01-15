package com.nibiru.framelib.tips;

import android.graphics.Bitmap;
import x.core.ui.XBaseScene;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/8/9
 * 描述:对提示的类进行统一的管理封装
 */
public class TipsManager {

    private XBaseScene mScene;
    private MyToastTip myToastTip;
    private MyLoadingTip mLoadingTip;
    private MyTextTip mTextTip;
    private MyDialogTip mMyDialogTip;

    public TipsManager(XBaseScene scene) {
        this.mScene = scene;
    }

    /*----------------- toast --------------------*/
    public void showtoast(String msg, int textColor, String bgimage, int during) {
        if (myToastTip == null) {
            myToastTip = new MyToastTip(mScene);
        }
        myToastTip.showtoast(msg, textColor, bgimage, during);
    }

    public void showtoast(String msg, int textColor, Bitmap bgimage, int during) {
        if (myToastTip == null) {
            myToastTip = new MyToastTip(mScene);
        }
        myToastTip.showtoast(msg, textColor, bgimage, during);
    }

    public void showtoast(String msg, int textcolor, int during) {
        if (myToastTip == null) {
            myToastTip = new MyToastTip(mScene);
        }
        myToastTip.showtoast(msg, textcolor, during);
    }

    public void hidetoast() {
        if (myToastTip == null) {
            myToastTip = new MyToastTip(mScene);
        }
        myToastTip.hidetoast();
    }
    /*----------------- end --------------------*/

    /*----------------- loading --------------------*/
    public void showLoading(String loading_img) {
        if (mLoadingTip == null) {
            mLoadingTip = new MyLoadingTip(mScene);
        }
        mLoadingTip.showLoading(loading_img);
    }

    public void hideLoading() {
        if (mLoadingTip == null) {
            mLoadingTip = new MyLoadingTip(mScene);
        }
        mLoadingTip.hideLoading();
    }

    public boolean isLoading() {
        if (mLoadingTip == null) {
            mLoadingTip = new MyLoadingTip(mScene);
        }
        return mLoadingTip.isLoading();
    }

    public void showLoading(String loading_img, String tiptxet) {
        if (mLoadingTip == null) {
            mLoadingTip = new MyLoadingTip(mScene);
        }
        mLoadingTip.showLoading(loading_img, tiptxet);
    }

    /*----------------- end --------------------*/

    /*----------------- texttip --------------------*/
    public void showTextTip(String content) {
        if (mTextTip == null) {
            mTextTip = new MyTextTip(mScene);
        }
        mTextTip.showTextTip(content);
    }

    public void showTextTip(String content, int textColor) {
        if (mTextTip == null) {
            mTextTip = new MyTextTip(mScene);
        }
        mTextTip.showTextTip(content, textColor);
    }

    public void hideTextTip() {
        if (mTextTip == null) {
            mTextTip = new MyTextTip(mScene);
        }
        mTextTip.hideTextTip();
    }
    /*----------------- end --------------------*/

    /*----------------- MyDialog --------------------*/
    public void showDialog(String titlestr, String contentstr, String leftbtn, String rightbtn) {
        if (mMyDialogTip == null) {
            mMyDialogTip = new MyDialogTip(mScene);
        }
        mMyDialogTip.showDialog(titlestr, contentstr, leftbtn, rightbtn);
    }

    public void hideDialog() {
        if (mMyDialogTip == null) {
            mMyDialogTip = new MyDialogTip(mScene);
        }
        mMyDialogTip.hideDialog();
    }

    public boolean dialogisShow() {
        if (mMyDialogTip == null) {
            mMyDialogTip = new MyDialogTip(mScene);
        }
        return mMyDialogTip.dialogisShow();
    }

    public void setoncickLisener(MyOnclickLisener onclickLisener) {
        if (mMyDialogTip == null) {
            mMyDialogTip = new MyDialogTip(mScene);
        }
        mMyDialogTip.setoncickLisener(onclickLisener);
    }
    /*----------------- end --------------------*/
}

