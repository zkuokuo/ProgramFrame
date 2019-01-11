package com.nibiru.programframe.ui.base;

public interface BaseContract {

    interface Scene {
        boolean isNetworkConnected();
        void showError(String message);
    }

    interface Presenter<V extends Scene> {
        void attachScene(V Scene);
        void detachScene();
    }
}
