package com.nibiru.dagger2demo.module_providers;

import com.nibiru.dagger2demo.MainActivity;
import com.nibiru.dagger2demo.ZhaiNan;

import dagger.Component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Component(modules = {ShangjiaAModule.class,ActivityModule.class})
public interface WaimaiPingTaiComponent {
    ZhaiNan waimai();
    void zhuru(ZhaiNan zhaiNan);
    void inject(MainActivity mainActivity);
}
