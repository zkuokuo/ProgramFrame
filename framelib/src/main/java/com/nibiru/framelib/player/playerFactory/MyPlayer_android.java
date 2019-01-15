package com.nibiru.framelib.player.playerFactory;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;

public class MyPlayer_android implements IPlayer {
    private MediaPlayer player_android = null;

    public MediaPlayer getObject() {
        if (player_android != null) {
            return player_android;
        }
        return null;
    }

    public MyPlayer_android() {
        if (player_android == null) {
            player_android = new MediaPlayer();
            player_android.setAudioStreamType(AudioManager.STREAM_MUSIC);//测试音频双开；
//            MediaPlayerDecodeOpt.startVideoDecodeOpt(player_android);
            player_android.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.d("zkk","lzh_MEDIA_what value: " + what);
                    switch (what) {
                        case -1004:
                            Log.d("zkk","lzh_MEDIA_ERROR_IO");
//                            PlayerSence.errorType_what = -1004;
                            break;
                        case -1007:
                            Log.d("zkk","lzh_MEDIA_ERROR_MALFORMED");
//                            PlayerSence.errorType_what = -1007;
                            break;
                        case 200:
                            Log.d("zkk","lzh_MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
//                            PlayerSence.errorType_what = 200;
                            break;
                        case 100:
                            Log.d("zkk","lzh_MEDIA_ERROR_SERVER_DIED");
//                            PlayerSence.errorType_what = 100;
                            break;
                        case -110:
                            Log.d("zkk","lzh_MEDIA_ERROR_TIMED_OUT");
//                            PlayerSence.errorType_what = -110;
                            break;
                        case 1:
                            Log.d("zkk","lzh_MEDIA_ERROR_UNKNOWN");
//                            PlayerSence.errorType_what = 1;
                            break;
                        case -1010:
                            Log.d("zkk","lzh_MEDIA_ERROR_UNSUPPORTED");
//                            PlayerSence.errorType_what = -1010;
                            break;
                    }
                    Log.d("zkk","lzh_MEDIA_extra value: " + extra);
//                    PlayerSence.isVideoPlaying = true;
//                    MyApplication.playIsError = true;
                    return true;
                }
            });
            player_android.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    PlayerSence.isVideoPlaying = false;
//                    PlayerSence.isCompleteShowMessage = true;
                }
            });
            player_android.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player_android.start();
//                    PlayerSence.isVideoPlaying = true;
                    player_android.seekTo((int) (player_android.getDuration()
                            * (mProgress > 0 ? mProgress : 0)));
                }
            });
            player_android.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
//                    Log.d("zkk","liangzihan0717_当前的视频播放进度是 : " + mp.getCurrentPosition() + " , 时间： " + Utils.millisToString(mp.getCurrentPosition()));
                }
            });
        }
    }

    @Override
    public void setSurface(Surface surface) {
        if (player_android != null) {
            player_android.setSurface(surface);
        }
    }

    /**
     * 播放
     */
    private float mProgress = 0;

    @Override
    public synchronized void play(String path, float process) {
        try {
            if (player_android != null && !player_android.isPlaying()) {
                player_android.reset();

                if (getModuleTag_fromPlayScene() == 3 || getModuleTag_fromPlayScene() == 4 || getModuleTag_fromPlayScene() == 5) {
                    Log.d("zkk","当前的模式是：" + getModuleTag_fromPlayScene());
                }
                player_android.setDataSource(path);
                player_android.prepare();
                mProgress = process;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int process) {
        if (player_android != null) {
            player_android.seekTo(process);
        }
    }

    //返回播放状态
    @Override
    public boolean isPlaying() {
        if (player_android != null) {
            return player_android.isPlaying();
        }
        return false;
    }

    //获得当前的百分比进度，
    @Override
    public float getCurrentPositionProcess() {
        if (player_android != null) {
            int total = player_android.getDuration();
            int current = player_android.getCurrentPosition();
            return (float) current / (float) total;
        }

        return -1.0f;
    }

    //获得视频总时长，毫秒为单位
    @Override
    public int getDuration() {
        if (player_android != null) {
            int total = player_android.getDuration();
            return total;
        }
        return -1;
    }

    //停止视频
    @Override
    public void stop() {
        if (player_android != null) {
//            PlayerSence.isVideoPlaying = false;
            player_android.stop();
        }
    }

    @Override
    public void start() {
        if (player_android != null) {
            player_android.start();
//            PlayerSence.isVideoPlaying = true;
        }
    }

    @Override
    public void pause() {
        if (player_android != null) {
            player_android.pause();
//            PlayerSence.isVideoPlaying = false;
        }
    }

    @Override
    public void reset() {
        if (player_android != null) {
            player_android.reset();
        }
    }

    //获得视频的像素高度 宽度
    public int getVideoHeight() {
        if (player_android != null) {
            return player_android.getVideoHeight();
        }
        return 0;
    }

    //获得视频的宽度
    public int getVideoWidth() {
        if (player_android != null) {
            return player_android.getVideoWidth();
        }
        return 0;
    }

    //释放资源
    @Override
    public void release() {
        if (player_android != null) {
            player_android.stop();
            player_android.reset();
            player_android.release();
            player_android = null;
        }
    }

    @Override
    public int getCurrentPosition() {
        if (player_android != null) {
            return (int) ((getCurrentPositionProcess()) * (getDuration()));
        }
        return 0;
    }

    int moduleTag_fromPlayScene = 0;

    public int getModuleTag_fromPlayScene() {
        return moduleTag_fromPlayScene;
    }

    public void setModuleTag_fromPlayScene(int moduleTag_fromPlayScene) {
        this.moduleTag_fromPlayScene = moduleTag_fromPlayScene;
    }

}