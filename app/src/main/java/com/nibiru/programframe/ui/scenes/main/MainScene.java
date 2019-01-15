package com.nibiru.programframe.ui.scenes.main;

import android.graphics.Color;

import com.nibiru.programframe.ui.base.BaseScene;
import com.nibiru.programframe.utils.CalculateUtils;

import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XImageText;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/11
 * 描述:
 */
public class MainScene extends BaseScene<MainPresenter> implements MainContract.NewScene, IXActorEventListener {
    String[] titles = new String[]{"添加", "查询",};
    String[] names = new String[]{"showArticles", "showBannerData",};

    @Override
    public void onCreate() {
        super.onCreate();
        mSceneComponent.inject(this);
        presenter.attachScene(this);
        initview();
    }

    private void initview() {
        for (int i = 0; i < titles.length; i++) {
            XImageText button = new XImageText("2.png", "1.png");
            button.setCenterPosition(0.2f, CalculateUtils.transformSize(-120) * i, -1.5f);
            button.setSizeOfImage(CalculateUtils.transformSize(400), CalculateUtils.transformSize(80));
            button.setSize(CalculateUtils.transformSize(400), CalculateUtils.transformSize(80));
            button.setTitle(titles[i], titles[i]);
            button.setTitleColor(Color.BLACK, Color.BLACK);
            button.setSizeOfTitle(CalculateUtils.transformSize(400), CalculateUtils.transformSize(60));
            button.setTitlePosition(0, 0);
            button.setName(names[i]);
            button.setEventListener(this);
            addActor(button);
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
        if (name.equals("showArticles")) {
            return true;
        } else if (name.equals("showBannerData")) {
            return true;
        }

        return false;
    }

    @Override
    public void onAnimation(XActor xActor, boolean b) {

    }
}
