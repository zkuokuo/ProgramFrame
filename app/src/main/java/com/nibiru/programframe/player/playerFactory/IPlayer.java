package com.nibiru.programframe.player.playerFactory;

import android.view.Surface;

public interface IPlayer {
    void setSurface(Surface surface);
    void play(String path, float process);
    void seekTo(int process);
    boolean isPlaying();
    float getCurrentPositionProcess();
    int getDuration();
    void stop();
    void start();
    void pause();
    void reset();
    int getVideoHeight();
    int getVideoWidth();
    void release();
    int getCurrentPosition();
}