package com.nibiru.dagger2demo;

import javax.inject.Inject;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

public class Tongyi extends Noodle {
    @Inject
    public Tongyi() {
    }

    @Override
    public String toString() {
        return "统一方便面";
    }
}
