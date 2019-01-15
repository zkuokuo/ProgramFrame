package com.nibiru.programframe.player;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;

import com.nibiru.framelib.player.bean.VideoData;
import com.nibiru.framelib.player.playerFactory.IPlayer;
import com.nibiru.framelib.player.playerFactory.MyPlayerFactory;
import com.nibiru.framelib.player.playerFactory.MyPlayer_android;
import com.nibiru.framelib.player.playerFactory.MyPlayer_vlc;
import com.nibiru.framelib.player.utils.MediaManager;
import com.nibiru.framelib.utils.MLog;

import java.util.List;

import x.core.ui.XBaseScene;
import x.core.ui.XSpaceRect;
import x.core.ui.surface.XSurfaceArea;
import x.core.ui.surface.XSurfaceConfig;
import x.core.util.MediaPlayerDecodeOpt;
import x.core.util.XVec3;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/12/13
 * 描述:点击向上键可以切换软硬件解码,点击向下键可以切换播放模式
 */
public class MyPlayerScene extends XBaseScene implements MediaManager.OnMediaScanListener {
    private VideoData videoData_all;
    private MyPlayer_vlc myXVLCMediaPlayer;
    private MyPlayer_android myXAndroidMediaPlayer;
    private XSurfaceArea m_VideoArea;
    private int currentplay_moudle = IMAX_2D;//当前播放模式
    public static final int IMAX_2D = 0;//2d正常模式
    public static final int IMAX_3D_LEFTRIGHT = 1;//3d左右眼正常模式
    public static final int IMAX_3D_UPDOWN = 2;//3d上下正常模式
    public static final int ALL360_2D = 3;//2d360度全屏模式
    public static final int ALL360_3D_LEFTRIGHT = 4;//3d左右眼全屏模式
    public static final int ALL360_3D_UPDOWN = 5;//3d上下眼全屏模式
    public static final int ALL180_2D = 6; //2d180度模式
    public static final int ALL180_3D_LEFTRIGHT = 7;//3d左右眼180度模式
    public static final int ALL180_3D_UPDOWN = 8;//3d上下眼180度模式
    public static final int BALL_2D = 9;//2d球幕模式
    public static final int BALL_3D_LEFTRIGHT = 10;//3d左右眼球幕模式
    public static final int BALL_3D_UPDOWN = 11;//3d上下眼球幕模式
    public static final int FISHEYE_2D = 12;//2d鱼眼模式
    public static final int HARDWARE = 100;//软解码
    public static final int SOFTWARE = 101;//硬解码
    public IPlayer m_VideoPlayer;
    public int currentware = SOFTWARE;//当前解码模式
    private boolean isNeedChangeWare = false;
    private MediaManager mediaManager;

    @Override
    public void onCreate() {
        //扫描本地视频
        mediaManager = MediaManager.getInstance(this);
        mediaManager.setMediaListener(this);
        mediaManager.scanVideo();
    }

    private void exeplay(VideoData videoData) {
        //1.获取资源
        videoData_all = videoData;
        //2.初始化播放器
        initPlayer();
        //3.播放的时候判断是软解播放还是硬解播放
        decodechange(HARDWARE);
        //4.判断播放模式初始化播放区域
        choosePlayMode();
        //5.播放结束的处理
        playComplete();
    }

    /**
     * 扫描本地视频
     */
    @Override
    public void onMediaScanResult(final List<VideoData> list) {
        if (list != null && list.size() > 0) {
            runOnRenderThread(new Runnable() {
                @Override
                public void run() {
                    exeplay(list.get(0));
                }
            });
        } else {
            MLog.d("list==null");
        }
    }

    /**
     * 更新扫面
     */
    @Override
    public void onMediaScanUpdate(VideoData video) {

    }

    /**
     * 播放完毕的回调
     */
    private void playComplete() {
        myXAndroidMediaPlayer.getObject().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                m_VideoPlayer.release();
            }
        });
        myXVLCMediaPlayer.getObject().setOnCompletionListener(new org.videolan.libvlc.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(org.videolan.libvlc.media.MediaPlayer mp) {
                m_VideoPlayer.release();
            }
        });
    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        myXVLCMediaPlayer = MyPlayerFactory.produceVLCPlayer(this.getApplicationContext());
        myXVLCMediaPlayer.getObject().setOnVideoSizeChangedListener(new org.videolan.libvlc.media.MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(org.videolan.libvlc.media.MediaPlayer mp, int width, int height) {
                if (m_VideoArea != null) {
                    m_VideoArea.notifySurfaceSizeChanged(width, height);
                }
            }
        });

        myXAndroidMediaPlayer = new MyPlayer_android();
        myXAndroidMediaPlayer.getObject().setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                if (m_VideoArea != null) {
                    m_VideoArea.notifySurfaceSizeChanged(width, height);
                }
            }
        });
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        autoAdaptVideoAreaSize(m_VideoPlayer);
    }

    int videoWidth;
    int videoHeight;
    float scale_x = 16 / 9f;
    float scale_y = 1;
    float scale_z = 1;

    /**
     * 动态的调整视频显示的大小
     */
    public void autoAdaptVideoAreaSize(IPlayer m_VideoPlayer) {
        if (null != m_VideoPlayer) {
            videoWidth = videoData_all.getWidth();
            videoHeight = videoData_all.getHeight();
//            MLog.log("媒体库信息获取宽高分别是：width : " + videoWidth + "\nheight : " + videoHeight);
            scale_x = 16 / 9f;
            scale_y = 1;
            scale_z = 1;
            if (videoHeight == 0 || videoWidth == 0) {
                videoWidth = m_VideoPlayer.getVideoWidth();
                videoHeight = m_VideoPlayer.getVideoHeight();
                if (videoHeight != -1 && videoWidth != -1) {
                    scale_x = (float) videoWidth / videoHeight;
//                    MLog.log("视频解析获取宽高分别是：width : " + videoWidth + "\nheight : " + videoHeight);
                }
            } else {
                scale_x = (float) videoWidth / videoHeight;
            }
            if ((currentplay_moudle == IMAX_2D || currentplay_moudle == IMAX_3D_LEFTRIGHT || currentplay_moudle == IMAX_3D_UPDOWN)) {
//                MLog.log("缩放的宽高比例（x轴缩放比例）是：width / height = " + scale_x);
                m_VideoArea.getRightSurface().setScale(1, scale_y, scale_z);
                m_VideoArea.getLeftSurface().setScale(1, scale_y, scale_z);

                m_VideoArea.getRightSurface().setScale(scale_x, scale_y, scale_z);
                m_VideoArea.getLeftSurface().setScale(scale_x, scale_y, scale_z);
            }
        } else {
            MLog.d("m_VideoPlayer == null");
        }
    }


    /**
     * 播放模式选择
     */
    public void choosePlayMode() {
        if (currentplay_moudle == IMAX_2D) {
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_imax_2D();
        } else if (currentplay_moudle == IMAX_3D_LEFTRIGHT) {
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_imax_3D_leftRight();
        } else if (currentplay_moudle == IMAX_3D_UPDOWN) {
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_imax_3D_upDown();
        } else if (currentplay_moudle == ALL360_2D) {
            //全景状态下矫正位置
//            cancelRotateView();
            //ALL360_2D
            setParameters(false, true, false, currentplay_moudle);
            initVideoPlayer_all360_2D();
//            playButtonControllMartrix();
        } else if (currentplay_moudle == ALL360_3D_LEFTRIGHT) {
//            cancelRotateView();
            //ALL360_3D_LEFTRIGHT
            setParameters(false, true, false, currentplay_moudle);
            initVideoPlayer_all360_3D_leftRight();
//            playButtonControllMartrix();
        } else if (currentplay_moudle == ALL360_3D_UPDOWN) {
            //全景状态下矫正位置
//            cancelRotateView();
            //ALL360_3D_UPDOWN
            setParameters(false, true, false, currentplay_moudle);
            initVideoPlayer_all360_3D_upDown();
//            playButtonControllMartrix();
        } else if (currentplay_moudle == ALL180_2D) {
            setParameters(true, true, false, currentplay_moudle);
            initVideoPlayer_all180_2D();
        } else if (currentplay_moudle == ALL180_3D_LEFTRIGHT) {
            setParameters(true, true, false, currentplay_moudle);
            initVideoPlayer_all180_3D_leftRight();
        } else if (currentplay_moudle == ALL180_3D_UPDOWN) {
            setParameters(true, true, false, currentplay_moudle);
            initVideoPlayer_all180_3D_upDown();
        } else if (currentplay_moudle == BALL_2D) {
            //BALL_2D
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_ball_2D();
        } else if (currentplay_moudle == BALL_3D_LEFTRIGHT) {
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_ball_3D_leftRight();
        } else if (currentplay_moudle == BALL_3D_UPDOWN) {
            setParameters(true, false, true, currentplay_moudle);
            initVideoPlayer_ball_3D_upDown();
        } else if (currentplay_moudle == FISHEYE_2D) {
            setParameters(true, true, false, currentplay_moudle);
            initVideoPlayer_fishEye_2D();
        }
    }

    /**
     * 设置参数
     */
    public void setParameters(boolean fov, boolean onlyOne, boolean iMAX_isBALL, int moudle) {
        setFovEnable(fov);
        currentplay_moudle = moudle;
    }

    /**
     * XSurfaceArea相应模式的中心朝向
     */
    public XVec3 center() {
        XVec3 center = new XVec3(0f, 0f, -8.5f);
        return center;
    }

    public XSpaceRect pose() {
        XSpaceRect pose = new XSpaceRect();
        pose.setFront(0, 0, 1);
        pose.setUp(0, 1, 0);
        return pose;
    }

    public XVec3 center1() {
        XVec3 center1 = new XVec3(0f, 0f, 0f);
        return center1;
    }

    float real_width = 6;
    float real_height = 6;
    private float nvrVideoScale = 0;//系统提供的比例参数

    public float getImax_width() {
        if (nvrVideoScale <= -1) {
            return real_width;
        } else {
            return real_width = 6 * (1 + nvrVideoScale);
        }
    }

    public float getImax_height() {
        if (nvrVideoScale <= -1) {
            return real_height;
        } else {
            return real_height = 6 * (1 + nvrVideoScale);
        }
    }

    /**
     * 2DIMAX
     */
    public void initVideoPlayer_imax_2D() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_2D, XSurfaceConfig.Format.TYPE_PLANE)
                .setCenter(center())
                .setSizeAdaptive(false)
                .setWidth(getImax_width())
                .setHeight(getImax_height())
                .setPosition(pose())
                .buildParameters();
        changeXSurfaceArea(parameters, "imax_2D");
    }

    /**
     * 3DIMAX左右分屏
     */
    public void initVideoPlayer_imax_3D_leftRight() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_PLANE)
                .setCenter(center())
                .setSizeAdaptive(false)
                .setWidth(getImax_width())
                .setHeight(getImax_height())
                .setPosition(pose())
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_HORIZONTAL)
                .buildParameters();
        changeXSurfaceArea(parameters, "imax_3D_leftRight");
    }

    /**
     * 3DIMAX上下分屏
     */
    public void initVideoPlayer_imax_3D_upDown() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_PLANE)
                .setCenter(center())
                .setSizeAdaptive(false)
                .setWidth(getImax_width())
                .setHeight(getImax_height())
                .setPosition(pose())
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_VERTICAL)
                .buildParameters();
        changeXSurfaceArea(parameters, "imax_3D_upDown");
    }

    /**
     * 2D全景360
     */
    public void initVideoPlayer_all360_2D() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_2D, XSurfaceConfig.Format.TYPE_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .setSphereMesh(50, 25)
                .buildParameters();
        changeXSurfaceArea(parameters, "all360_2D");
    }

    /**
     * 3D全景360左右分屏
     */
    public void initVideoPlayer_all360_3D_leftRight() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_HORIZONTAL)
                .setSphereMesh(50, 25)
                .buildParameters();
        changeXSurfaceArea(parameters, "3D_leftRight");
    }

    /**
     * 3D全景360上下分屏
     */
    public void initVideoPlayer_all360_3D_upDown() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_VERTICAL)
                .setSphereMesh(50, 25)
                .buildParameters();
        changeXSurfaceArea(parameters, "all360_3D_upDown");
    }

    /**
     * 2D全景180
     */
    public void initVideoPlayer_all180_2D() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_2D, XSurfaceConfig.Format.TYPE_SEMI_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .buildParameters();
        changeXSurfaceArea(parameters, "all180_2D");
    }

    /**
     * 3D全景180左右分屏
     */
    public void initVideoPlayer_all180_3D_leftRight() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_SEMI_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_HORIZONTAL)
                .setSphereMesh(20, 10)
                .buildParameters();
        changeXSurfaceArea(parameters, "all180_3D_leftRight");
    }

    /**
     * 3D全景180上下分屏
     */
    public void initVideoPlayer_all180_3D_upDown() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_SEMI_PANORAMA)
                .setCenter(center1())
                .setRadius(10)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_VERTICAL)
                .buildParameters();
        changeXSurfaceArea(parameters, "all180_3D_upDown");
    }

    /**
     * 2D球幕
     */
    public void initVideoPlayer_ball_2D() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_2D, XSurfaceConfig.Format.TYPE_SPHERE)
                .setCenter(center1())
                .setRadius(10)
                .setSphereArea(-22, 33, -50, 50)
                .buildParameters();
        changeXSurfaceArea(parameters, "ball_2D");
    }

    /**
     * 3D球幕左右分屏
     */
    public void initVideoPlayer_ball_3D_leftRight() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_SPHERE)
                .setCenter(center1())
                .setRadius(10)
                .setSphereArea(-22, 33, -50, 50)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_HORIZONTAL)
                .buildParameters();
        changeXSurfaceArea(parameters, "ball_3D_leftRight");
    }

    /**
     * 3D球幕上下分屏
     */
    public void initVideoPlayer_ball_3D_upDown() {
        XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_3D, XSurfaceConfig.Format.TYPE_SPHERE)
                .setCenter(center1())
                .setRadius(10)
                .setSphereArea(-22, 33, -50, 50)
                .setVideoLayout(XSurfaceConfig.Layout.LAYOUT_VERTICAL)
                .buildParameters();
        changeXSurfaceArea(parameters, "ball_3D_upDown");
    }

    /**
     * 2D鱼眼模式
     */
    boolean isFisheyeSB = true;

    public void initVideoPlayer_fishEye_2D() {
        final XSurfaceArea.Parameters parameters = new XSurfaceArea.Builder(this, XSurfaceConfig.Mode.MODE_2D, XSurfaceConfig.Format.TYPE_SPHERE)
                .setCenter(center1())
                .setRadius(10)
                .setSubSphereAngle(VideoData.fisheye_area_size)
                .setSubSphereFront(isFisheyeSB)
                .buildParameters();
        changeXSurfaceArea(parameters, "fishEye_2D");
    }


    private void changeXSurfaceArea(XSurfaceArea.Parameters parameters, final String log) {
        if (m_VideoArea == null) {
            m_VideoArea = new XSurfaceArea.Builder(this, parameters).build();
            addActor(m_VideoArea);
            initPlayerAndSetTexture();
        } else {
            m_VideoArea.updateParameters(parameters, new Runnable() {
                @Override
                public void run() {
                    MLog.d("切换纹理成功的回调，模式 ：" + log);
                    if (isNeedChangeWare) {//如果需要切换模式就
                        isNeedChangeWare = false;
                        initPlayerAndSetTexture();
                    }
                }
            });
        }
    }

    /**
     * XSurfaceArea、播放器的实例化、播放
     */
    public void initPlayerAndSetTexture() {
        releaseAndCreateVideoPlayer();
        enableVideoMode();
        startpaly();
    }

    public synchronized void startpaly() {
        //v700、s900需要兼容纹理优化
        startRepairClarityForMediaplayer_hardware();
        m_VideoPlayer.play(videoData_all.getPath(), 0);
    }

    /**
     * 实例化前释放上一个播放器，随后重新创建一个新的播放器
     */
    Surface surface;
    boolean isInitSurfaceArea = false;

    public synchronized void releaseAndCreateVideoPlayer() {
        if (!isInitSurfaceArea) {
            isInitSurfaceArea = true;
            surface = new Surface(m_VideoArea.getSurfaceTexture());
        }
        //判断解码方式
        if (currentware == SOFTWARE) {//(注：因为软解播放器在切换模式或者鱼眼状态调节大小时会出现setSurface重复attach的问题，故重新实例化播放器)
            if (myXAndroidMediaPlayer != null) {
                myXAndroidMediaPlayer.stop();
                myXAndroidMediaPlayer.setSurface(null);
            }
            if (myXVLCMediaPlayer == null) {
                myXVLCMediaPlayer = MyPlayerFactory.produceVLCPlayer(this.getApplicationContext());
            }
            myXVLCMediaPlayer.setSurface(surface);
            m_VideoPlayer = myXVLCMediaPlayer;
        } else if (currentware == HARDWARE) {
            if (myXVLCMediaPlayer != null) {
                myXVLCMediaPlayer.stop();
                myXVLCMediaPlayer.setSurface(null);
            }
            if (myXAndroidMediaPlayer == null) {
                myXAndroidMediaPlayer = MyPlayerFactory.produceAndroidPlayer();
            }
            myXAndroidMediaPlayer.setSurface(surface);
            m_VideoPlayer = myXAndroidMediaPlayer;
        }
    }

    /**
     * 优化硬解方式下全景360的3d左右和上下的状态下的纹理问题（开启）
     */
    public void startRepairClarityForMediaplayer_hardware() {
        myXAndroidMediaPlayer.setModuleTag_fromPlayScene(currentplay_moudle);
    }

    /**
     * 优化硬解方式下全景360的3d左右和上下的状态下的纹理问题（关闭）
     */
    public void stopRepairClarityForMediaplayer_hardware() {
        if (myXAndroidMediaPlayer != null && myXAndroidMediaPlayer.getObject() != null) {
            MediaPlayerDecodeOpt.stopVideoDecodeOpt(myXAndroidMediaPlayer.getObject());
        }
    }


    /**
     * 当解码模式发生改变时调用的方法
     */
    private void decodechange(int moudle) {
        if (currentware != moudle) {
            isNeedChangeWare = true;
        }
        currentware = moudle;
        if (currentware == HARDWARE) {
            m_VideoPlayer = myXAndroidMediaPlayer;
        } else if (currentware == SOFTWARE) {
            m_VideoPlayer = myXVLCMediaPlayer;
        }
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
        if (myXAndroidMediaPlayer != null && myXAndroidMediaPlayer.isPlaying()) {
            myXAndroidMediaPlayer.pause();
        }
        if (myXVLCMediaPlayer != null && myXVLCMediaPlayer.isPlaying()) {
            myXVLCMediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        stopRepairClarityForMediaplayer_hardware();
    }

    @Override
    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if (currentplay_moudle < 12) {
                currentplay_moudle++;
            } else {
                currentplay_moudle = 0;
            }
            choosePlayMode();
            Log.d("zkk", "onKeyDown:当前模式是------" + currentplay_moudle);
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (currentware == HARDWARE) {
                decodechange(SOFTWARE);
            } else {
                decodechange(HARDWARE);
            }
            initPlayerAndSetTexture();
            Log.d("zkk", "onKeyDown:当前解码模式------" + currentware);
        }
        return super.onKeyDown(keyCode);
    }


}
