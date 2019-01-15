package com.nibiru.programframe.player.playerFactory;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/12/12
 * 描述: 生成player的工厂类
 */
public class MyPlayerFactory {

    public static MyPlayer_vlc produceVLCPlayer() {
        return new MyPlayer_vlc();
    }

    public static MyPlayer_android produceAndroidPlayer() {
        return new MyPlayer_android();
    }
}
