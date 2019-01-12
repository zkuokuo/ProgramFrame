package com.nibiru.dagger2demo.component_depend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nibiru.dagger2demo.Baozi;
import com.nibiru.dagger2demo.Noodle;
import com.nibiru.dagger2demo.R;

import javax.inject.Inject;

/**
 * 这个module主要是测试dagger2中注解是如何使用的
 */
public class DependActivity extends Activity {
    @Inject
    Baozi mBaozi;
    @Inject
    Huotuichang mHuotuichang;
    @Inject
    Noodle mNoodle;
    @Inject
    Guazi mGuazi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XiaoChiComponent xiaoChiComponent = DaggerXiaoChiComponent.builder().build();
        DaggerFoodComponent.builder().xiaoChiComponent(xiaoChiComponent).build().inject(this);

    }

    public void click(View view) {
        Toast.makeText(this, mBaozi.toString() + "---" + mNoodle.toString() + "---" + mHuotuichang.toString() + "---" + mGuazi.toString(), Toast.LENGTH_SHORT).show();

    }

}
