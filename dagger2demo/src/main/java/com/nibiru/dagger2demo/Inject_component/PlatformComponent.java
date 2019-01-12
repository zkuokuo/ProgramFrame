package com.nibiru.dagger2demo.Inject_component;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/12
 * 描述:两种方式将ZhaiNan对象注入到MainActivity中
 * @Component 和 @Inject 的配合就能够使用 Dagger2 了，
 * 但这里面存在一个局限，@Inject 只能标记在我们自己编写的类的构造方法中，
 */
//@Component()
//public interface PlatformComponent {
//    ZhaiNan waimai(); //这种方式注入只能注入指定的返回的对象
//    void getZhaiNan(MainActivity mainActivity);//这种方式注入可以注入所有@inject注释的对象
//}
