package com.nibiru.dagger2demo.Qualifiers_name;

import dagger.Component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Component(modules = Threemodule.class)
public interface Treecomponent {
    void inject(ThreeActivity threeActivity);
}
