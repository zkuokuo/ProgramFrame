package com.nibiru.programframe.ui.scenes.main;

import com.nibiru.programframe.data.model.Article;
import com.nibiru.programframe.data.model.Banner;
import com.nibiru.programframe.ui.base.BaseContract;

import java.util.List;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/11
 * 描述:
 */
public class MainContract {
    interface NewScene extends BaseContract.Scene {
        void showArticles(int page, List<Article> data);
        void showBannerData(List<Banner> data);
    }

    interface NewPresenter extends BaseContract.Presenter<NewScene> {
        void getArticles(int page);
        void getBannerData();
    }
}
