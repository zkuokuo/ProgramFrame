package com.nibiru.programframe.ui.scenes.main.view;

import javax.inject.Inject;

import x.core.listener.IXActorEventListener;
import x.core.ui.XActor;
import x.core.ui.XBaseScene;

/**
 * 作者:zkk
 * 公司:nibiru
 * 描述:
 */
public class MainView {
    private XBaseScene mScene;
    private IXActorEventListener listener;

    @Inject
    public MainView() {
    }

    public MainView attachScene(XBaseScene scene) {
        mScene = scene;
        return this;
    }

    public void addActor(XActor actor) {
        if (mScene != null) {
            mScene.addActor(actor);
        } else {
            new Throwable("Scene is null!");
        }
    }

    public void init(IXActorEventListener listener) {
        this.listener = listener;
    }

}
