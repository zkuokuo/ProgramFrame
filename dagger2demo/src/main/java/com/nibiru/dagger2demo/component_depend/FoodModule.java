package com.nibiru.dagger2demo.component_depend;

import com.nibiru.dagger2demo.Baozi;
import com.nibiru.dagger2demo.Kangshifu;
import com.nibiru.dagger2demo.Noodle;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

@Module
public class FoodModule {
    @Provides
    public Baozi provideBaozi() {
        return new Baozi();
    }

    @Provides
    public Noodle provideNoodle() {
        return new Kangshifu();
    }
}
