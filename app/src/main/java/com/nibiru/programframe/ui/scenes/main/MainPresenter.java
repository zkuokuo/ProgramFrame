package com.nibiru.programframe.ui.scenes.main;

import com.nibiru.programframe.data.source.DataManager;
import com.nibiru.programframe.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/11
 * 描述:
 */
public class MainPresenter extends BasePresenter<MainContract.NewScene> implements MainContract.NewPresenter {
    private DataManager mDataManager;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void detachScene() {
        super.detachScene();
        if (mDisposable != null) {
            //取消请求
            mDisposable.dispose();
        }
    }

}
