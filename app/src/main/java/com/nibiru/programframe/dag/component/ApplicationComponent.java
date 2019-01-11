package com.nibiru.programframe.dag.component;

import com.nibiru.programframe.dag.module.ApplicationModule;
import com.nibiru.programframe.data.source.DataManager;
import com.nibiru.programframe.data.source.dbdata.DatabaseHelper;
import javax.inject.Singleton;
import dagger.Component;
import x.core.GlobalApplication;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    GlobalApplication application();
    DataManager dataManager();
    DatabaseHelper databaseHelper();
}
