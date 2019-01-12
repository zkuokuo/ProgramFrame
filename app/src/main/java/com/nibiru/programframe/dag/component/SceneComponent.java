package com.nibiru.programframe.dag.component;

import com.nibiru.programframe.dag.module.SceneModule;
import com.nibiru.programframe.dag.scope.SceneScope;
import com.nibiru.programframe.ui.scenes.main.MainScene;
import dagger.Component;

@SceneScope //相当于在Scene中单例的作用范围
@Component(modules = SceneModule.class, dependencies = ApplicationComponent.class)
public interface SceneComponent {
    //为什么不注入到BaseScene中,因为不是所有继承BaseScene的类都需要,
    void inject(MainScene mainScene);
}
