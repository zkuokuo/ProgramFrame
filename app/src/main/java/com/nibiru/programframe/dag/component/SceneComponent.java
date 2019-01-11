package com.nibiru.programframe.dag.component;

import com.nibiru.programframe.dag.module.SceneModule;
import com.nibiru.programframe.dag.scope.SceneScope;
import com.nibiru.programframe.ui.scenes.main.MainScene;

import dagger.Component;

@SceneScope
@Component(modules = SceneModule.class, dependencies = ApplicationComponent.class)
public interface SceneComponent {
    void inject(MainScene mainScene);
}
