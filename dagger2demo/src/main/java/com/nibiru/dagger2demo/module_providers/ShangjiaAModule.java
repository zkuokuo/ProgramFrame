package com.nibiru.dagger2demo.module_providers;

import com.nibiru.dagger2demo.Baozi;
import com.nibiru.dagger2demo.Kangshifu;

import dagger.Module;
import dagger.Provides;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述: Dagger2 为了能够对第三方库中的类进行依赖注入，提供了 @Provides 和 @Module 两个注解。
 * Dagger2 中规定，用 @Provides 注解的依赖必须存在一个用 @Module 注解的类中。
 * <p>
 * 所以，什么时候用 new 创建对象，什么时候可以直接返回传入的参数就很明显了。
 * 对于被 @Inject 注解过构造方法或者在一个 Module 中的被 @Provides 注解的方法提供了依赖时，
 * 就可以直接返回传入的参数，而第三方的库或者 SDK 自带的类就必须手动创建了。
 * (我的理解是同时被@inject注解过两次以上可以传入参数)
 */
@Module
public class ShangjiaAModule {
    String restaurant;

    public ShangjiaAModule(String restaurant) {
        this.restaurant = restaurant;
    }

    @Provides
    public Baozi provideBaozi() {
        return new Baozi("豆沙包");
    }

//    @Provides
//    public Noodle providenoodle(Tongyi noodle) {
//        return noodle;
//    }

    @Provides
    public Kangshifu providenoodle() {
        return new Kangshifu();
    }

    //@Provides
//    public String provideResturant() {
//        return "王小二包子店";
//    }
    @Provides
    public String provideResturant() {
        return restaurant;
    }
}
