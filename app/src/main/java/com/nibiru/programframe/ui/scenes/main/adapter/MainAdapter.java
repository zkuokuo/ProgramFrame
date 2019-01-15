package com.nibiru.programframe.ui.scenes.main.adapter;

import java.util.List;

import x.core.adapter.XItemAdapter;
import x.core.ui.XActor;
import x.core.ui.group.XItem;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/14
 * 描述:
 */
public class MainAdapter<E> extends XItemAdapter {
    private List<E> sceneBeanList;
//    private MainScene mMainScene;

    public void setSceneBeanList(List<E> sceneBeanList) {
        this.sceneBeanList = sceneBeanList;
    }

//    //返回GridView的Item总数
//    public MainAdapter(List<E> sceneBeanList, MainScene baseScene) {
//        this.sceneBeanList = sceneBeanList;
//        this.mMainScene = baseScene;
//    }

    @Override
    public int getCount() {
        return sceneBeanList == null ? 0 : sceneBeanList.size();
    }

    @Override
    public Object getObject(int position) {
        return null;
    }

    @Override
    public XItem getXItem(int position, XItem convertItem, XActor parent) {
//        synchronized (MainAdapter.class) {
//            if (position < 0 || position >= sceneBeanList.size()) return null;
//        }
//        //如果Item尚未创建，新建一个Item
//        if (convertItem == null) {
//            convertItem = new XItem();
//            XImageText gridViewActorList = mMainScene.getGridViewActorList(position);
//            convertItem.addLayer(gridViewActorList);
//            mMainScene.updateView(gridViewActorList, position);
//        } else {
//            XImageText imageText = (XImageText) convertItem.getChild("gridviewXImageText_" + position);
//            if (imageText != null) {
//                mMainScene.updateView(imageText, position);
//                XActor icon_delete = imageText.getLayer("icon_delete");
//                if (icon_delete != null) {
//                    if (mMainScene.isClick()) {
//                        icon_delete.setEnabled(true);
//                    } else {
//                        icon_delete.setEnabled(false);
//                    }
//                }
//            }
//        }
        return convertItem;
    }
}
