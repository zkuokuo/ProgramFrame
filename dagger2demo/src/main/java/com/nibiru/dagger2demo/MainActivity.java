package com.nibiru.dagger2demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nibiru.dagger2demo.module_providers.DaggerWaimaiPingTaiComponent;
import com.nibiru.dagger2demo.module_providers.ShangjiaAModule;

import javax.inject.Inject;

/**
 * 这个module主要是测试dagger2中注解是如何使用的
 */
public class MainActivity extends Activity {
    @Inject
    int testValue;
    @Inject
    String resultrant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerWaimaiPingTaiComponent.builder()
                .shangjiaAModule(new ShangjiaAModule("校长热狗店"))
                .build().inject(this);
    }

    public void click(View view) {
        Toast.makeText(this, "测试是不是可以把int数据传递过来---" + testValue+"---resultrant="+resultrant, Toast.LENGTH_SHORT).show();
    }

}
