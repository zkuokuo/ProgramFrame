package com.nibiru.programframe.girdview;

import com.nibiru.programframe.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XImageText;
import x.core.ui.XLabel;
import x.core.util.XActorAnimation;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/8
 * 描述:
 */
public class GridViewTestScene extends AbstractXGridViewScene {
    List<String> test = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initview();
        for (int i = 0; i < 10; i++) {
            test.add(i + "--" + i);
        }
        notifyDataSetChanged(test);
        XLabel button = new XLabel("向右翻页");
        button.setCenterPosition(0, 0f, -1);
        button.setSize(0.4f, 0.05f);
        button.setRenderOrder(50);
        button.setEventListener(new IXActorEventListener() {
            @Override
            public void onGazeEnter(XActor xActor) {

            }

            @Override
            public void onGazeExit(XActor xActor) {

            }

            @Override
            public boolean onGazeTrigger(XActor xActor) {
                doPageLeftRight(true);
                return true;
            }

            @Override
            public void onAnimation(XActor xActor, boolean b) {

            }
        });
        addActor(button);
    }

    private void initview() {

    }

    @Override
    public void onResume() {
    }


    @Override
    protected XActor getGridViewActor() {
        XImageText item = new XImageText("eg.png", "eg.png");
        item.setName("gridviewXImageText");
        item.setSizeOfImage(3.3f, 2.55f);
        item.setSize(3.3f, 2.55f);
        item.setRenderOrder(10);
        item.setGazeAnimation(XActorAnimation.AnimationType.SCALE, 0.05f, 150);
        return item;
    }

    @Override
    protected void onGridViewItemOrLayerTrigger(XActor xActor, int position) {
        super.onGridViewItemOrLayerTrigger(xActor, position);
        MLog.d("点击的位置是==" + position);
    }

    @Override
    protected void updateView(XActor xImageText, int position) {
        //这个方法使用来刷新每一个item的,不过感觉这个可以放在adapter中进行刷新.
        MLog.d("需要刷新的位置是: " + position);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

}
