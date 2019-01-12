package com.nibiru.dagger2demo.component_depend;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

@Module
public class XiaoChiModule {

    @Provides
    public Guazi provideGuazi() {
        return new Guazi();
    }

    @Provides
    public Huotuichang provideHuotuichang() {
        return new Huotuichang();
    }
}
