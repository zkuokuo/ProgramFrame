package com.nibiru.programframe.ui;

import x.core.util.XBaseXRApplication;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/3/29
 * 描述:
 */

public class MyApplication extends XBaseXRApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
//                .name("myRealm.realm")
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(realmConfiguration);
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(512, 512)
//                .diskCacheExtraOptions(512, 512, null)
//                .threadPoolSize(3)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .tasksProcessingOrder(QueueProcessingType.FIFO)
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
//                .memoryCacheSize(10 * 1024 * 1024)
//                .diskCacheSize(100 * 1024 * 1024)
//                .writeDebugLogs().build();
//        ImageLoader.getInstance().init(config);
    }
}
