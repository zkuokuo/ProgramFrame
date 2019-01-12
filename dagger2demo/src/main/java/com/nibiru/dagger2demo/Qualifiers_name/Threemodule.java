package com.nibiru.dagger2demo.Qualifiers_name;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */
@Module
public class Threemodule {
    @Provides
    @Phone
    public String providePhone() {
        return "手机";
    }

    @Provides
    @Computer
    public String prividecomputer() {
        return "电脑";
    }

}
