package com.nibiru.dagger2demo.provides_lazy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nibiru.dagger2demo.Qualifiers_name.Computer;
import com.nibiru.dagger2demo.Qualifiers_name.Phone;
import com.nibiru.dagger2demo.R;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

/**
 * @Inject Lazy<User> lazyUser; //注入Lazy元素
 * @Inject Provider<User> providerUser; //注入Provider元素
 * <p>
 * DaggerComponent.create().inject(this);//依赖注入
 * <p>
 * User user1 = lazyUser.get();
 * //在这时才创建user1,以后每次调用get会得到同一个user1对象
 * User user2 = providerFruit.get();
 * 在这时创建user2,以后每次调用get会再强制调用Module的Provides方法一次，
 * 根据Provides方法具体实现的不同，可能返回跟user2是同一个对象，也可能不是。
 */
public class LazyActivity extends Activity {

    @Inject
    @Phone
    String phone;
    @Inject
    @Computer
    String computer;


    @Inject
    @Named("TestLazy")
    Lazy<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerLazycomponent.builder().build().inject(this);
    }

    public void click(View view) {
        Toast.makeText(this, "phone=" + name.get(), Toast.LENGTH_SHORT).show();
    }
}
