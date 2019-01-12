package com.nibiru.dagger2demo;

import javax.inject.Inject;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:
 */

public class ZhaiNan {
    @Inject
    Baozi bz;

    @Inject
    Noodle nd;

    @Inject
    public ZhaiNan() {
    }

    @Inject
    String resturant;

    public String eat() {
        StringBuffer sp = new StringBuffer();
        sp.append("我从: ");
        sp.append(resturant.toString());
        sp.append("订的外卖, ");
        sp.append("我吃的是: ");
        if (bz != null) {
            sp.append(bz.toString());
        }
        if (nd != null) {
            sp.append("   ");
            sp.append(nd.toString());
        }
        return sp.toString();
    }
}
