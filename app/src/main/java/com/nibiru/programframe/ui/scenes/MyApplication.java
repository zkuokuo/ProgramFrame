package com.nibiru.programframe.ui.scenes;

import com.nibiru.programframe.dag.component.ApplicationComponent;
import com.nibiru.programframe.dag.component.DaggerApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import x.core.GlobalApplication;


/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/7/17
 * 描述:
 */
public class MyApplication extends GlobalApplication {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().build();

        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
