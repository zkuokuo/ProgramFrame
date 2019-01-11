package com.nibiru.programframe.ui.base;

public class BasePresenter<V extends BaseContract.Scene> implements BaseContract.Presenter<V> {

    private V scene;

    public BasePresenter() {
    }

    @Override
    public void attachScene(V scene) {
        this.scene = scene;
    }

    @Override
    public void detachScene() {
        scene = null;
    }

    public boolean isViewAttached() {
        return scene != null;
    }

    public V getScene() {
        return scene;
    }
}
