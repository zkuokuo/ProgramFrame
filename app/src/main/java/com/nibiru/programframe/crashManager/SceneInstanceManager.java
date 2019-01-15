package com.nibiru.programframe.crashManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import x.core.ui.XBaseScene;

/**
 * 用来保存已经打开的scene的堆栈
 */
public class SceneInstanceManager {
    private SceneInstanceManager() {

    }
    private static SceneInstanceManager mSceneInstanceManager = new SceneInstanceManager();

    public static SceneInstanceManager getInstance() {
        return mSceneInstanceManager;
    }

    //创建一个map集合,去存储XBaseScene对象
    private List<XBaseScene> mXBaseScenes = new ArrayList<>();

    public void addScene(XBaseScene xBaseScene) {
        if (xBaseScene != null) {
            synchronized (SceneInstanceManager.class) {
                if (xBaseScene != null) {
                    if (!mXBaseScenes.contains(xBaseScene)) {
                        mXBaseScenes.add(xBaseScene);
                    }
                }
            }
        }
    }

    public void finishAll() {
        ListIterator<XBaseScene> it = mXBaseScenes.listIterator();
        while (it.hasNext()) {
            XBaseScene xBaseScene = it.next();
            if (!xBaseScene.isSceneFinishing()) {
                xBaseScene.finish();
                it.remove();
            }
        }
    }

    public void finishTopScene() {
        XBaseScene xBaseScene = mXBaseScenes.get(mXBaseScenes.size() - 1);
        if (xBaseScene != null && !xBaseScene.isSceneFinishing()) {
            xBaseScene.finish();
        }
    }

}
