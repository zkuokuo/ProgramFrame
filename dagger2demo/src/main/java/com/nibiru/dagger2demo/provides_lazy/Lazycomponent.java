package com.nibiru.dagger2demo.provides_lazy;

import dagger.Component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Component(modules = Lazymodule.class)
public interface Lazycomponent {
    void inject(LazyActivity threeActivity);
}
