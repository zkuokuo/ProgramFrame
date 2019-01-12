package com.nibiru.dagger2demo.singleton_scope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Module
public class SecondActivityModule {
    @Provides
    @Singleton
    public TestSingleton provideTestSingleton() {
        return new TestSingleton();
    }
}
