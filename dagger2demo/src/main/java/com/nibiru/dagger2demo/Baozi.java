package com.nibiru.dagger2demo;

import javax.inject.Inject;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

public class Baozi {
    String name;

    @Inject
    public Baozi() {
        this.name = "小笼包";

    }

    public Baozi(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
