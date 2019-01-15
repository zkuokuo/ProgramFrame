package com.nibiru.framelib.player.playerFactory;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import org.videolan.libvlc.media.MediaPlayer;


public class MyPlayer_vlc implements IPlayer {
    private MediaPlayer player_vlc;
    private Context context;

    public MediaPlayer getObject() {
        if (player_vlc != null) {
            return player_vlc;
        }
        return null;
    }


    public MyPlayer_vlc(Context context) {
        this.context=context;
        if (player_vlc == null) {
            player_vlc = new MediaPlayer();
            player_vlc.setEventListener(new org.videolan.libvlc.MediaPlayer.EventListener() {
                @Override
                public void onEvent(org.videolan.libvlc.MediaPlayer.Event event) {
                    if (event.type == org.videolan.libvlc.MediaPlayer.Event.Playing) {
                        //开始播放
//                        player_vlc.seekTo((int) (player_vlc.getDuration() * temp_pro));

                    } else if (event.type == org.videolan.libvlc.MediaPlayer.Event.EncounteredError) {
                        //播放出错
//                        PlayerSence.isVideoPlaying = false;
//                        MyApplication.playIsError = true;
                    } else if (event.type == org.videolan.libvlc.MediaPlayer.Event.EndReached) {
                        //完成监听
//                        PlayerSence.isVideoPlaying = false;
//                        PlayerSence.isCompleteShowMessage = true;
                    }
                }
            });
        }
    }

    @Override
    public void setSurface(Surface surface) {
        if (player_vlc != null) {
            try {
                player_vlc.setSurface(surface);
            } catch (Exception e) {
                Log.d("zkk", "setSurface异常：" + e.toString());
            }
        }
    }

    public boolean isOnlineUri(String path) {
        return "http".equals(Uri.parse(path).getScheme()) ||
                "https".equals(Uri.parse(path).getScheme()) ||
                "rtmp".equals(Uri.parse(path).getScheme()) ||
                "rtsp".equals(Uri.parse(path).getScheme()) ||
                "rtp".equals(Uri.parse(path).getScheme());
    }

    @Override
    public void play(String path, float process) {
        try {
            if (player_vlc != null) {
                if (isOnlineUri(path)) {
                    player_vlc.setDataSource(context, Uri.parse(path));
                } else {
                    player_vlc.setDataSource(path);
                }
                player_vlc.prepare();
                player_vlc.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int process) {
        if (player_vlc != null) {
            player_vlc.seekTo(process);
        }
    }

    @Override
    public boolean isPlaying() {
        if (player_vlc != null) {
            return player_vlc.isPlaying();
        }
        return false;
    }

    @Override
    public float getCurrentPositionProcess() {
        if (player_vlc != null) {
            int total = player_vlc.getDuration();
            int current = player_vlc.getCurrentPosition();
            return (float) current / (float) total;
        }

        return -1.0f;
    }

    @Override
    public int getDuration() {
        if (player_vlc != null) {
            int total = player_vlc.getDuration();
            return total;
        }
        return -1;
    }

    @Override
    public void stop() {
        if (player_vlc != null) {
            player_vlc.stop();
        }
    }

    @Override
    public void start() {
        if (player_vlc != null) {
            player_vlc.start();
        }
    }

    @Override
    public void pause() {
        if (player_vlc != null) {
            player_vlc.pause();
        }
    }

    @Override
    public void reset() {
        if (player_vlc != null) {
            player_vlc.reset();
        }
    }

    //获得视频的像素高度 宽度
    public int getVideoHeight() {
        if (player_vlc != null) {
            return player_vlc.getVideoHeight();
        }
        return 0;
    }

    //获得视频的宽度
    public int getVideoWidth() {
        if (player_vlc != null) {
            return player_vlc.getVideoWidth();
        }
        return 0;
    }

    //释放资源
    public void release() {
        if (player_vlc != null) {
            player_vlc.stop();
            player_vlc.reset();
            player_vlc.release();
            player_vlc = null;
        }
    }

    @Override
    public int getCurrentPosition() {
        if (player_vlc != null) {
            return (int) ((getCurrentPositionProcess()) * (getDuration()));
        }
        return 0;
    }
}