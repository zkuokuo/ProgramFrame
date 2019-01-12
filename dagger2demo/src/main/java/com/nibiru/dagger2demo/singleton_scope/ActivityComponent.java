package com.nibiru.dagger2demo.singleton_scope;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Singleton
@Component(modules = SecondActivityModule.class)
public interface ActivityComponent {
    void inject(SecondActivity secondActivity);
}
