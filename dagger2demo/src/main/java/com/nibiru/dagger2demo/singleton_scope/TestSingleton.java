package com.nibiru.dagger2demo.singleton_scope;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:单例
 * 分析@Singleton 其实就等同于分析 @Scope 。Scope 的字面意思是作用域，也就是表达一种能力的范围。
 * @Singleton 所拥有的单例能力是以 Component 为范围的限定的。同一个component对应的单例是同一个对象
 */
@Singleton
public class TestSingleton {
    @Inject
    public TestSingleton() {
    }
}
