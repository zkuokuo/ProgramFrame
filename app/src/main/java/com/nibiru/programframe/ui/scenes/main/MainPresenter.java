package com.nibiru.programframe.ui.scenes.main;

import com.nibiru.programframe.data.model.Article;
import com.nibiru.programframe.data.model.ArticleListResponse;
import com.nibiru.programframe.data.model.Banner;
import com.nibiru.programframe.data.model.BaseResponse;
import com.nibiru.programframe.data.source.DataManager;
import com.nibiru.programframe.ui.base.BasePresenter;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
            mDisposable.dispose();
        }
    }

    @Override
    public void getArticles(final int page) {
        Observable<BaseResponse<ArticleListResponse>> observable = mDataManager.getArticles(page);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<BaseResponse<ArticleListResponse>, List<Article>>() {
                    @Override
                    public List<Article> apply(
                            @NonNull BaseResponse<ArticleListResponse> response)
                            throws Exception {
                        return response.getData().getDatas();
                    }
                }).subscribeWith(new Observer<List<Article>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<Article> articles) {
                getScene().showArticles(page, articles);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                getScene().showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getBannerData() {
        Observable<BaseResponse<List<Banner>>> observable = mDataManager.getBannerData();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<BaseResponse<List<Banner>>, List<Banner>>() {
                    @Override
                    public List<Banner> apply(@NonNull BaseResponse<List<Banner>> response)
                            throws Exception {
                        return response.getData();
                    }
                }).subscribeWith(new Observer<List<Banner>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<Banner> banners) {
                getScene().showBannerData(banners);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getScene().showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
