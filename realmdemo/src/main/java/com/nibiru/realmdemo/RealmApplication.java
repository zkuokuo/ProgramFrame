package com.nibiru.realmdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nibiru.realmdemo.model.PersonMigration;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/16
 * 描述:
 */

public class RealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在Realm中Stetho需要配置
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name("realmdemo.realm") //文件名
                .schemaVersion(2) //版本号
//                .deleteRealmIfMigrationNeeded() // 声明版本冲突时自动删除原数据库。
//                .inMemory()//声明数据库只在内存中持久化。
                .migration(new PersonMigration()) //指定迁移操作的迁移类。
//                .encryptionKey()   // 指定数据库的密钥。
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
