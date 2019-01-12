package com.nibiru.programframe.data.source;

import com.nibiru.programframe.data.model.ArticleListResponse;
import com.nibiru.programframe.data.model.Banner;
import com.nibiru.programframe.data.model.BaseResponse;
import com.nibiru.programframe.data.model.Category;
import com.nibiru.programframe.data.model.HotKey;
import com.nibiru.programframe.data.model.LoginResponse;
import com.nibiru.programframe.data.model.NavCategory;
import com.nibiru.programframe.data.model.SearchHistory;
import com.nibiru.programframe.data.model.User;
import com.nibiru.programframe.data.source.dbdata.DBHelper;
import com.nibiru.programframe.data.source.netdata.ApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {
    private ApiService mApiService;
    private DBHelper mDBHelper;

    @Inject
    public DataManager(ApiService apiService, DBHelper DBHelper) {
        this.mApiService = apiService;
        this.mDBHelper = DBHelper;
    }

    public Observable<BaseResponse<List<Banner>>> getBannerData() {
        return mApiService.getBannerData();
    }

    public Observable<BaseResponse<ArticleListResponse>> getArticles(int page) {
        return mApiService.getArticles(page);
    }

    public Observable<BaseResponse<List<Category>>> getProjectCategories() {
        return mApiService.getProjectCategories();
    }

    public Observable<BaseResponse<ArticleListResponse>> getProjectArticles(int page, int cid) {
        return mApiService.getProjectArticles(page, cid);
    }

    public Observable<BaseResponse<List<Category>>> getHierarchyCategories() {
        return mApiService.getHierarchyCategories();
    }

    public Observable<BaseResponse<ArticleListResponse>> getHierarchyArticles(int page, int cid) {
        return mApiService.getHierarchyArticles(page, cid);
    }

    public Observable<BaseResponse<List<NavCategory>>> getNavCategories() {
        return mApiService.getNavCategories();
    }

    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return mApiService.getHotKey();
    }

    public Observable<BaseResponse<ArticleListResponse>> searchArticles(int page, String keyword) {
        return mApiService.searchArticles(page, keyword);
    }

    public Observable<BaseResponse<LoginResponse>> signin(String username, String password) {
        return mApiService.signin(username, password);
    }

    public Observable<BaseResponse<LoginResponse>> signup(
            String username, String password, String repassword) {
        return mApiService.signup(username, password, repassword);
    }

    public void saveLoggedInUser(String username, String password, boolean isLogin) {
        mDBHelper.saveLoggedInUser(username, password, isLogin);
    }

    public User getLoggedInUser() {
        return mDBHelper.getLoggedInUser();
    }

    public boolean isLogin() {
        return mDBHelper.isLogin();
    }

    public void deleteLoggedInUser() {
        mDBHelper.deleteLoggedInUser();
    }

    public void saveSearchHistory(String keyword) {
        mDBHelper.saveSearchHistory(keyword);
    }

    public List<SearchHistory> querySearchHistory() {
        return mDBHelper.querySearchHistory();
    }

    public void deleteSearchHistory() {
        mDBHelper.deleteSearchHistory();
    }
}
