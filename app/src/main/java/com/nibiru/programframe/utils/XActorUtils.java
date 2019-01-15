package com.nibiru.programframe.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.nibiru.framelib.utils.OperateBitmapUtil;

import x.core.listener.IXActorKeyEventListener;
import x.core.ui.XEditText;
import x.core.ui.XImage;
import x.core.ui.XImageText;
import x.core.ui.XLabel;
import x.core.ui.XProgressBar;
import x.core.ui.XSkeletonActor;
import x.core.ui.XUI;
import x.core.ui.group.XActorListView;
import x.core.util.PicturesUtil;

import static com.nibiru.programframe.utils.CalculateUtils.transformSize;
import static x.core.ui.XEditText.LayoutParam;
import static x.core.ui.XEditText.XEditStyle;
import static x.core.util.PicturesUtil.isBitmapTextureCreated;

/**
 * Created by EGuang on 2018/4/3.
 */
public class XActorUtils {
    /**
     * @param actorName 控件名称
     * @param content 控件内容
     * @param align 控件文字显示位置
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XLabel createLabel(String actorName, String content, XLabel.XAlign align, float width, float height, float centerX, float centerY, float centerZ) {
        XLabel mLabel = new XLabel("");
        if (actorName != null) {
            mLabel.setName(actorName);
        }
        if (content != null) {
            mLabel.setTextContent(content);
        }
        if (align != null) {
            mLabel.setAlignment(align);
        } else {
            mLabel.setAlignment(XLabel.XAlign.Center);
        }
        mLabel.setSize(width, height);
        mLabel.setCenterPosition(centerX, centerY, centerZ);
        return mLabel;
    }

    /**
     * XLabel 作为子控件创建
     * @param actorName 控件名称
     * @param content 显示内容
     * @param align 显示位置
     * @param width 宽度
     * @param height 高度
     * @param params 子控件在父控件中的位置
     * @return
     */
    public static XLabel createChildLabel(String actorName, String content, XLabel.XAlign align, float width, float height, LayoutParam params) {
        XLabel mLabel = new XLabel("");
        if (actorName != null) {
            mLabel.setName(actorName);
        }
        if (content != null) {
            mLabel.setTextContent(content);
        }
        if (align != null) {
            mLabel.setAlignment(align);
        }
        mLabel.setSize(width, height);
        mLabel.setLayoutParam(params);
        return mLabel;
    }

    /**
     * XImageText（没有title）
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param width 控件的宽度
     * @param height 控件的高度
     * @param centerX 控件的X轴
     * @param centerY 控件的Y轴
     * @param centerZ 控件的Z轴
     * @return
     */
    public static XImageText createXImageText(String actorName, String selectedImgName, String unSelectedImgName, float width, float height, float centerX, float centerY, float centerZ) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        mImageText.setCenterPosition(centerX, centerY, centerZ);
        return mImageText;
    }

    /**
     * XImageText
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param title 控件上的内容显示
     * @param titleSize 控件文字的大小
     * @param width 控件的宽度
     * @param height 控件的高度
     * @param centerX 控件的X轴
     * @param centerY 控件的Y轴
     * @param centerZ 控件的Z轴
     * @return
     */
    public static XImageText createXImageText(String actorName, String selectedImgName, String unSelectedImgName, String title, float titleSize, float width, float height, float centerX, float centerY, float centerZ) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setSizeOfTitle(width, titleSize);
            mImageText.setTitlePosition(0f, 0f);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
        }
        mImageText.setCenterPosition(centerX, centerY, centerZ);
        return mImageText;
    }

    /**
     * XImageText
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param title 控件文字显示
     * @param selectedColor 控件选中文字颜色
     * @param unselectedColor 控件未选中文字颜色
     * @param titleSize 文字大小
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XImageText createXImageText(String actorName, String selectedImgName, String unSelectedImgName, String title, int selectedColor, int unselectedColor, float titleSize, float width, float height, float centerX, float centerY, float centerZ) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setTitleColor(selectedColor, unselectedColor);
            mImageText.setSizeOfTitle(width, titleSize);
            mImageText.setTitlePosition(0f, 0f);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
        }
        mImageText.setCenterPosition(centerX, centerY, centerZ);
        return mImageText;
    }

    public static XImageText createXImageText(String actorName, String selectedImgName, String unSelectedImgName, float width, float height) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        return mImageText;
    }

    public static XImageText createXImageText(String actorName, String selectedImgName, String unSelectedImgName, String title, float titleSize, float width, float height) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setSizeOfTitle(width, titleSize);
            mImageText.setTitlePosition(0f, 0f);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
        }
        return mImageText;
    }

    /**
     * XImageText(没有title)
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams 子控件在父控件的位置
     * @return
     */
    public static XImageText createChildXImageText(String actorName, String selectedImgName, String unSelectedImgName, float width, float height, LayoutParam mParams) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        mImageText.setLayoutParam(mParams);
        return mImageText;
    }

    /**
     * XImageText
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param title 控件文字内容
     * @param titleSize 控件文字大小
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams 子控件在父控件的位置
     * @return
     */
    public static XImageText createChildXImageText(String actorName, String selectedImgName, String unSelectedImgName, String title, float titleSize, float width, float height, LayoutParam mParams) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setSizeOfTitle(width, titleSize);
            mImageText.setTitlePosition(0f, 0f);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
        }
        mImageText.setLayoutParam(mParams);
        return mImageText;
    }

    /**
     * XImageText
     * @param actorName 控件名称
     * @param selectedImgName 控件选中显示
     * @param unSelectedImgName 控件未选中显示
     * @param title 控件文字显示
     * @param selectedColor 控件选中文字颜色
     * @param unselectedColor 控件未选中文字颜色
     * @param titleSize 文字大小
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams 子控件在父控件的位置
     * @return
     */
    public static XImageText createChildXImageText(String actorName, String selectedImgName, String unSelectedImgName, String title, int selectedColor, int unselectedColor, float titleSize, float width, float height, LayoutParam mParams) {
        XImageText mImageText = new XImageText(selectedImgName,unSelectedImgName);
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSize(width, height);
        mImageText.setSizeOfImage(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setTitleColor(selectedColor, unselectedColor);
            mImageText.setSizeOfTitle(width, titleSize);
            mImageText.setTitlePosition(0f, 0f);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
        }
        mImageText.setLayoutParam(mParams);
        return mImageText;
    }

    /**
     * XImage
     * @param actorName 控件名称
     * @param imageName 控件显示
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XImage createXImage(String actorName, String imageName, float width, float height, float centerX, float centerY, float centerZ) {
        XImage mImage = new XImage(imageName);
        if (actorName != null) {
            mImage.setName(actorName);
        }
        mImage.setSize(width, height);
        mImage.setCenterPosition(centerX, centerY, centerZ);
        return mImage;
    }

    /**
     * XImage
     * @param actorName 控件名称
     * @param imageName 空间显示
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams
     * @return
     */
    public static XImage createChildXImage(String actorName, String imageName, float width, float height, LayoutParam mParams) {
        XImage mImage = new XImage(imageName);
        if (actorName != null) {
            mImage.setName(actorName);
        }
        mImage.setSize(width, height);
        mImage.setLayoutParam(mParams);
        return mImage;
    }

    /**
     * XSkeletonActor
     * @param actorName 模型名称
     * @param nsmPath nsm文件路径
     * @param pngPath png图片路径（不需要具体到图片名称，以"/"结尾）
     * @param location 文件加载路径
     * @param animScale 缩放比例
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XSkeletonActor createXSkeletonActor(String actorName, String nsmPath, String pngPath, XUI.Location location, float animScale, float centerX, float centerY, float centerZ) {
        XSkeletonActor mXSkeletonActor = new XSkeletonActor(nsmPath, new String[]{pngPath}, location);
        if (actorName != null) {
            mXSkeletonActor.setName(actorName);
        }
        mXSkeletonActor.setAnimationScale(animScale);
        mXSkeletonActor.setTranslate(centerX, centerY, centerZ);
        return mXSkeletonActor;
    }

    /**
     * XSkeletonActor
     * @param actorName 控件名称
     * @param nsmPath nsm文件路径
     * @param pngPath png文件路径
     * @param location 文件加载位置
     * @param animScale 缩放比例
     * @param mParams 子控件在父控件上的位置
     * @return
     */
    public static XSkeletonActor createChildXSkeletornActor(String actorName, String nsmPath, String pngPath, XUI.Location location, float animScale, LayoutParam mParams) {
        XSkeletonActor mXSkeletonActor = new XSkeletonActor(nsmPath, new String[]{pngPath}, location);
        if (actorName != null) {
            mXSkeletonActor.setName(actorName);
        }
        mXSkeletonActor.setAnimationScale(animScale);
        mXSkeletonActor.setLayoutParam(mParams);
        return mXSkeletonActor;
    }

    /**
     * XEditText
     * @param actorName 控件名称
     * @param hintText 提示文字
     * @param editStyle 输入框编辑风格
     * @param width 控件宽度
     * @param height 控件高度
     * @param textSizeRatio 输入框文字大小
     * @param centerX 输入框X轴
     * @param centerY 输入框Y轴
     * @param centerZ 输入框Z轴
     * @return
     */
    public static XEditText createXEditText(String actorName, String hintText, XEditStyle editStyle, float width, float height, float textSizeRatio, float centerX, float centerY, float centerZ) {
        String hintS = "";
        if (hintText != null) {
            hintS = hintText;
        }
        XEditText mEditText;
        if (editStyle != null) {
            mEditText = new XEditText(hintS, editStyle);
        } else {
            mEditText = new XEditText(hintS, getXEditDefaultStyle());
        }
        mEditText.setName(actorName);
        mEditText.setSize(width, height);
        mEditText.setTextSize(textSizeRatio);
        mEditText.setCenterPosition(centerX, centerY, centerZ);
        return mEditText;
    }

    /**
     * XEditText
     * @param actorName 控件名称
     * @param hintText 提示文字
     * @param editStyle 输入框编辑风格
     * @param width 控件宽度
     * @param height 控件高度
     * @param textSizeRatio 输入框文字大小
     * @param  textColor 文字颜色
     * @param centerX 输入框X轴
     * @param centerY 输入框Y轴
     * @param centerZ 输入框Z轴
     * @return
     */
    public static XEditText createXEditText(String actorName, String hintText, XEditStyle editStyle, float width, float height, float textSizeRatio, int textColor, float centerX, float centerY, float centerZ) {
        String hintS = "";
        if (hintText != null) {
            hintS = hintText;
        }
        XEditText mEditText;
        if (editStyle != null) {
            mEditText = new XEditText(hintS, editStyle);
        } else {
            mEditText = new XEditText(hintS, getXEditDefaultStyle());
        }
        mEditText.setName(actorName);
        mEditText.setSize(width, height);
        mEditText.setTextSize(textSizeRatio);
        mEditText.setTextColor(textColor);
        mEditText.setCenterPosition(centerX, centerY, centerZ);
        return mEditText;
    }

    /**
     * XEditText
     * @param actorName 控件名称
     * @param hintText 提示文字
     * @param editStyle 输入框编辑风格
     * @param width 输入框宽度
     * @param height 输入框高度
     * @param textSizeRatio 输入框文字大小
     * @param mParams 子控件在父控件中的位置
     * @return
     */
    public static XEditText createChildXEditText(String actorName, String hintText, XEditStyle editStyle, float width, float height, float textSizeRatio, LayoutParam mParams) {
        String hintS = "";
        if (hintText != null) {
            hintS = hintText;
        }
        XEditText mEditText;
        if (editStyle != null) {
            mEditText = new XEditText(hintS, editStyle);
        } else {
            mEditText = new XEditText(hintS, getXEditDefaultStyle());
        }
        mEditText.setName(actorName);
        mEditText.setSize(width, height);
        mEditText.setTextSize(textSizeRatio);
        mEditText.setLayoutParam(mParams);
        return mEditText;
    }

    public static XEditStyle getXEditDefaultStyle() {
        XEditStyle editStyle = new XEditStyle();
        editStyle.backgroundColor = Color.argb(150, 226, 226, 255);
        editStyle.strokeColor = Color.argb(255, 0, 0, 0);
        editStyle.strokeWidth  = 0.0F;
        editStyle.textColor = Color.argb(0, 255, 255, 255);
        editStyle.xAlign = XLabel.XAlign.Left;
        editStyle.xArrangementMode = XLabel.XArrangementMode.SingleRowClipRightByLength;
        return editStyle;
    }

    /**
     * 带下划线的文字
     * @param actorName 控件名称
     * @param title 文字内容
     * @param underlineName 下划线图片
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XImageText createUnderLineBtn(String actorName, String title, String underlineName, float width, float height, float centerX, float centerY, float centerZ){
        if (!isBitmapTextureCreated("transparent_bg")) {
            Bitmap transBmp = OperateBitmapUtil.getBackgroundBitmap(80, 20, 0, 0, 0, 0);
            PicturesUtil.createBitmapTexture(transBmp, "transparent_bg", true);
        }
        XImageText mImageText = new XImageText("transparent_bg", "transparent_bg");
        if (actorName != null) {
            mImageText.setName(actorName);
        }
        mImageText.setSizeOfImage(width, height);
        mImageText.setSize(width, height);
        if (title != null) {
            mImageText.setTitle(title, title);
            mImageText.setSizeOfTitle(width, height);
            mImageText.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
            mImageText.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowMove, XLabel.XArrangementMode.SingleRowClip);
            mImageText.setTitlePosition(0f, 0f);
        }
        mImageText.setCenterPosition(centerX, centerY, centerZ);

        XImage underlineConnect = new XImage(underlineName);
        underlineConnect.setSize(width, transformSize(2));
        underlineConnect.setRenderOrder(20);
        mImageText.addChild(1, underlineConnect, new LayoutParam(0f, -height/2 - transformSize(3), 0.01f));
        return mImageText;
    }

    /**
     * XActorListView
     * @param actorName 控件名称
     * @param listViewType listView滑动方式
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴坐标
     * @param centerY 控件Y轴坐标
     * @param centerZ 控件Z轴坐标
     * @return
     */
    public static XActorListView createXActorListView(String actorName, XActorListView.ListViewType listViewType, float width, float height, float centerX, float centerY, float centerZ) {
        XActorListView mActorListView = new XActorListView(listViewType);
        if (actorName != null) {
            mActorListView.setName(actorName);
        }
        mActorListView.setSize(width, height);
        mActorListView.setMoveMode(XActorListView.ListViewMoveMode.PAGE);
        mActorListView.setMaskRenderOrder(40);
        mActorListView.setCenterPosition(centerX, centerY, centerZ);
        mActorListView.setKeyEventListener(new IXActorKeyEventListener() {
            @Override
            public boolean onKeyDown(int i) {
                return false;
            }

            @Override
            public boolean onKeyUp(int i) {
                return false;
            }
        });
        return mActorListView;
    }

    /**
     * XActorListView
     * @param actorName 控件名称
     * @param listViewType listView滑动方式
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams 子控件在父控件中的位置
     * @return
     */
    public static XActorListView createChildXActorListView(String actorName, XActorListView.ListViewType listViewType, float width, float height, LayoutParam mParams) {
        XActorListView mActorListView = new XActorListView(listViewType);
        if (actorName != null) {
            mActorListView.setName(actorName);
        }
        mActorListView.setSize(width, height);
        mActorListView.setMoveMode(XActorListView.ListViewMoveMode.PAGE);
        mActorListView.setMaskRenderOrder(40);
        mActorListView.setLayoutParam(mParams);
        mActorListView.setKeyEventListener(new IXActorKeyEventListener() {
            @Override
            public boolean onKeyDown(int i) {
                return false;
            }

            @Override
            public boolean onKeyUp(int i) {
                return false;
            }
        });
        return mActorListView;
    }

    /**
     * XProgressBar
     * @param actorName 控件名称
     * @param p 进度
     * @param bg 背景色
     * @param fg 前景色
     * @param width 控件宽度
     * @param height 控件高度
     * @param centerX 控件X轴
     * @param centerY 控件Y轴
     * @param centerZ 控件Z轴
     * @return
     */
    public static XProgressBar createXProgessBar(String actorName, float p, String bg, String fg, float width, float height, float centerX, float centerY, float centerZ){
        XProgressBar mProgressBar = new XProgressBar(p, bg, fg);
        if(actorName != null) {
            mProgressBar.setName(actorName);
        }
        mProgressBar.setSize(width, height);
        mProgressBar.setCenterPosition(centerX, centerY, centerZ);
        return mProgressBar;
    }

    /**
     * XProgress
     * @param actorName 控件名称
     * @param p 当前进度
     * @param bg 控件背景
     * @param fg 控件前景色
     * @param width 控件宽度
     * @param height 控件高度
     * @param mParams 子控件在父控件的位置
     * @return
     */
    public static XProgressBar createChildXProgressBar(String actorName, float p, String bg, String fg, float width, float height, LayoutParam mParams) {
        XProgressBar mProgressBar = new XProgressBar(p, bg, fg);
        if (actorName != null) {
            mProgressBar.setName(actorName);
        }
        mProgressBar.setSize(width, height);
        mProgressBar.setLayoutParam(mParams);
        return mProgressBar;
    }
}
