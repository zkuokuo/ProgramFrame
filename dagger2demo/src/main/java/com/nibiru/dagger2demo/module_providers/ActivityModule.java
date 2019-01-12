package com.nibiru.dagger2demo.module_providers;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Module
public class ActivityModule {

    @Provides
    public int provideActivityTest() {
        return 1234567890;
    }
}
