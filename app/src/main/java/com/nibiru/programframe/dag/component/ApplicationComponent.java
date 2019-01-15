package com.nibiru.programframe.dag.component;

import com.nibiru.programframe.dag.module.ApplicationModule;
import com.nibiru.programframe.data.source.DataManager;
import com.nibiru.programframe.data.source.dbdata.DBHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    DBHelper dbHelper();
    DataManager dataManager();
}
