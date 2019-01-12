package com.nibiru.dagger2demo.Qualifiers_name;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nibiru.dagger2demo.R;

import javax.inject.Inject;

/**
 * 这个module主要是测试dagger2中注解是如何使用的
 */
public class ThreeActivity extends Activity {
    @Inject
    @Phone
    String phone;
    @Inject
    @Computer
    String computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerTreecomponent.builder().build().inject(this);
    }

    public void click(View view) {
        Toast.makeText(this, "phone=" + phone + "---computer-" + computer, Toast.LENGTH_SHORT).show();
    }
}
