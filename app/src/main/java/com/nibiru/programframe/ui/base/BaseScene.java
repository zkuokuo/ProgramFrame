package com.nibiru.programframe.ui.base;

import com.nibiru.framelib.utils.NetworkUtils;
import com.nibiru.programframe.dag.component.DaggerSceneComponent;
import com.nibiru.programframe.dag.component.SceneComponent;
import com.nibiru.programframe.ui.scenes.MyApplication;
import javax.inject.Inject;
import x.core.ui.XBaseScene;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/11
 * 描述:
 */
public abstract class BaseScene<P extends BaseContract.Presenter> extends XBaseScene implements BaseContract.Scene {
    @Inject
    protected P presenter;
    protected SceneComponent mSceneComponent;

    @Override
    public void onCreate() {
        mSceneComponent = DaggerSceneComponent.builder()
                .applicationComponent(((MyApplication) getGlobalApplication()).getApplicationComponent())
                .build();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachScene();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkAvailable(this);
    }

    @Override
    public void showError(String message) {

    }
}
