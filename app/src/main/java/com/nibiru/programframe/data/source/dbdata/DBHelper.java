package com.nibiru.programframe.data.source.dbdata;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * 使用的时候需要注意,realm在哪个线程中创建就需要在哪个线程中使用,否则会报错误
 */
@Singleton
public class DBHelper {
    private Realm realm = Realm.getDefaultInstance();

    @Inject
    public DBHelper() {

    }

//    public void saveSearchHistory(String keyword) {
//        AppDetailData searchHistory = realm.where(AppDetailData.class).equalTo("keyword", keyword).findFirst();
//        realm.beginTransaction();
//        if (searchHistory != null) {
//            searchHistory.deleteFromRealm();
//        }
//        SearchHistory history = realm.createObject(SearchHistory.class);
//        history.setKeyword(keyword);
//        realm.commitTransaction();
//    }
//
//    public List<SearchHistory> querySearchHistory() {
//        RealmResults<SearchHistory> realmResults = realm.where(SearchHistory.class).findAll();
//        return realm.copyFromRealm(realmResults);
//    }
//
//    public void deleteSearchHistory() {
//        realm.beginTransaction();
//        realm.where(SearchHistory.class).findAll().deleteAllFromRealm();
//        realm.commitTransaction();
//    }
//
//    public void saveLoggedInUser(String username, String password, boolean isLogin) {
//        realm.beginTransaction();
//        User user = realm.createObject(User.class);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setLogin(isLogin);
//        realm.commitTransaction();
//    }
//
//    public User getLoggedInUser() {
//        return realm.where(User.class).equalTo("isLogin", true).findFirst();
//    }
//
//    public boolean isLogin() {
//        return getLoggedInUser() != null;
//    }
//
//    public void deleteLoggedInUser() {
//        realm.beginTransaction();
//        getLoggedInUser().deleteFromRealm();
//        realm.commitTransaction();
//    }
}
