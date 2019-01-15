package com.nibiru.programframe.data.source;

import com.nibiru.programframe.data.model.AppDetailData;
import com.nibiru.programframe.data.model.FavBaseResponse;
import com.nibiru.programframe.data.model.VideoData;
import com.nibiru.programframe.data.source.dbdata.DBHelper;
import com.nibiru.programframe.data.source.netdata.ApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

import static com.nibiru.programframe.data.source.netdata.BaseUrlConfig.channel;
import static com.nibiru.programframe.data.source.netdata.BaseUrlConfig.domin_url;
import static com.nibiru.programframe.data.source.netdata.BaseUrlConfig.login_pkg;
import static com.nibiru.programframe.data.source.netdata.BaseUrlConfig.pkg;
import static com.nibiru.programframe.data.source.netdata.BaseUrlConfig.version;

@Singleton
public class DataManager {
    private ApiService mApiService;
    private DBHelper mDBHelper;

    @Inject
    public DataManager(ApiService apiService, DBHelper DBHelper) {
        this.mApiService = apiService;
        this.mDBHelper = DBHelper;
    }

    public Observable<FavBaseResponse<List<VideoData>>> getFavVideoData(String token) {
        return mApiService.getFavVideoData(version, channel, token, login_pkg, pkg, domin_url);
    }

    public Observable<FavBaseResponse<List<AppDetailData>>> getFavAppData(String token) {
        return mApiService.getFavAppData(version, channel, token, login_pkg, pkg,
                domin_url, "1");
    }

    public Observable<FavBaseResponse> unFavVideoState(String token, int id) {
        return mApiService.unFavVideoState(version, channel, token, login_pkg, pkg, "" + id);
    }

    public Observable<FavBaseResponse> unFavAppState(String token, int id) {
        return mApiService.unFavAppState(version, channel, token, login_pkg, pkg, "" + id, "1");
    }


//    public void saveLoggedInUser(String username, String password, boolean isLogin) {
//        mDBHelper.saveLoggedInUser(username, password, isLogin);
//    }
//
//
//    public boolean isLogin() {
//        return mDBHelper.isLogin();
//    }
//
//    public void deleteLoggedInUser() {
//        mDBHelper.deleteLoggedInUser();
//    }
//
//    public void saveSearchHistory(String keyword) {
//        mDBHelper.saveSearchHistory(keyword);
//    }
//
//
//    public void deleteSearchHistory() {
//        mDBHelper.deleteSearchHistory();
//    }
}
