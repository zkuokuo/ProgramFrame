package com.nibiru.dagger2demo.component_depend;

import dagger.Component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

@Component(modules = XiaoChiModule.class)
public interface XiaoChiComponent {
    Guazi provideGuazi();
    Huotuichang provideHuotuichang();
}