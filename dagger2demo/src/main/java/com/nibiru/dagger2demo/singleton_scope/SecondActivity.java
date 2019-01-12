package com.nibiru.dagger2demo.singleton_scope;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nibiru.dagger2demo.R;

import javax.inject.Inject;

/**
 * 这个module主要是测试dagger2中注解是如何使用的
 */
public class SecondActivity extends Activity {
    @Inject
    TestSingleton mTestSingleton1;
    @Inject
    TestSingleton mTestSingleton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerActivityComponent.builder().build().inject(this);
    }

    public void click(View view) {
        Toast.makeText(SecondActivity.this, "test1 hashcode:" + mTestSingleton1.toString() + " test2 hashcode:" + mTestSingleton2.toString(), Toast.LENGTH_LONG).show();
    }
}
