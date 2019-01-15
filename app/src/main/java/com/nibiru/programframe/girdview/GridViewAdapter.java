package com.nibiru.programframe.girdview;

import java.util.ArrayList;
import java.util.List;

import x.core.adapter.XItemAdapter;
import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XImage;
import x.core.ui.XImageText;
import x.core.ui.group.XItem;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/8
 * 描述:这是一个gridview的适配器
 */
public class GridViewAdapter<E> extends XItemAdapter {
    private List<E> sceneBeanList = new ArrayList<>();
    private int position;
    private AbstractXGridViewScene mScene;

    public GridViewAdapter(List<E> sceneBeanList, AbstractXGridViewScene scene) {
        this.sceneBeanList = sceneBeanList;
        this.mScene = scene;
    }

    public void setSceneBeanList(List<E> sceneBeanList) {
        this.sceneBeanList = sceneBeanList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getCount() {
        return sceneBeanList.size();
    }

    @Override
    public Object getObject(int i) {
        return null;
    }

    XImageText xImageText;

    @Override
    public XItem getXItem(final int position, XItem convertItem, XActor xActor) {
        setPosition(position);
        if (convertItem == null) {
            convertItem = new XItem();
            xImageText = (XImageText) mScene.getGridViewActor();
        } else {
            xImageText = (XImageText) convertItem.getChild("gridviewXImageText");
        }

        xImageText.setEventListener(new IXActorEventListener() {
            @Override
            public void onGazeEnter(XActor xActor) {
                mScene.onGridViewItemEnter(xActor, position);
            }

            @Override
            public void onGazeExit(XActor xActor) {
                mScene.onGridViewItemExit(xActor, position);
            }

            @Override
            public boolean onGazeTrigger(XActor xActor) {
                mScene.onGridViewItemOrLayerTrigger(xActor, position);
                return true;
            }

            @Override
            public void onAnimation(XActor xActor, boolean b) {

            }
        });


        XImage playIcon = (XImage) xImageText.getChild("play");
        if (playIcon != null) {
            playIcon.setEventListener(new IXActorEventListener() {
                @Override
                public void onGazeEnter(XActor xActor) {
                }

                @Override
                public void onGazeExit(XActor xActor) {
                }

                @Override
                public boolean onGazeTrigger(XActor xActor) {
                    mScene.onGridViewItemOrLayerTrigger(xActor, position);
                    return true;
                }

                @Override
                public void onAnimation(XActor xActor, boolean b) {

                }
            });
        }

        convertItem.addLayer(xImageText);
        mScene.updateView(xImageText, position);
        return convertItem;
    }
}
