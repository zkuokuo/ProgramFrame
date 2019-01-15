package com.nibiru.programframe.ui.scenes.main.view;

/**
 * 作者:zkk
 * 公司:nibiru
 * 描述:
 */
public class MainView {
//    public int COLUMN = 3;
//    public int ROW = 3;
//    public final int PAGE_NUM = 5;//总页数
//    public static final float Z = -5F;
//    public XBaseScene mScene;
//    public XImage mLoading;
//    public XLabel error;
//    public XLabel tipLabel;
//    public XLabel rePostionTip;
//    public XImageText game;
//    public XImageText video;
//    public XLabel data;
//    public XImage[] bottombtns = new XImage[5];
//    public XActorGridView actorGridView;
//    public Random random;
//    public String[] appBgNames = new String[COLUMN * ROW * PAGE_NUM];
//    public XRotateByAction mRotate;
//    public boolean isLoading;
//    public MainAdapter myAdapter;
//    public IXActorEventListener listener;
//
//    @Inject
//    public MainView() {
//    }
//
//    public MainView attachScene(XBaseScene scene) {
//        random = new Random();
//        mScene = scene;
//        return this;
//    }
//
//    public void init(IXActorEventListener listener) {
//        this.listener = listener;
//        mLoading = new XImage("loading.png");
//        mLoading.setSize(0.5f, 0.5f);
//        mLoading.setCenterPosition(0, 0, Z + 1);
//        mLoading.setRenderOrder(100);
//        addActor(mLoading);
//        mLoading.setEnabled(false);
//
//        error = new XLabel("");
//        error.setSize(2f, 0.15f);
//        error.setCenterPosition(0, 0, Z + 1);
//        error.setRenderOrder(14);
//        error.setTextColor(Color.WHITE);
//        error.setAlignment(XLabel.XAlign.Center);
//        addActor(error);
//        error.setEnabled(false);
//
//        tipLabel = new XLabel("");
//        tipLabel.setSize(2f, 0.15f);
//        tipLabel.setCenterPosition(0, -0.5f, Z + 1);
//        tipLabel.setRenderOrder(14);
//        tipLabel.setTextColor(Color.WHITE);
//        tipLabel.setAlignment(XLabel.XAlign.Center);
//        addActor(tipLabel);
//        tipLabel.setEnabled(false);
//
//        rePostionTip = new XLabel(mScene.getString(R.string.tip));
//        rePostionTip.setTextColor(Color.WHITE);
//        rePostionTip.setCenterPosition(0f, 0, Z);
//        rePostionTip.setSize(2.4f, 0.2f);
//        rePostionTip.setAlignment(XLabel.XAlign.Center);
//        rePostionTip.setArrangementMode(XLabel.XArrangementMode.MultiRow);
//        addActor(rePostionTip);
//        rePostionTip.setEnabled(false);
//
//        initlistView();
//    }
//
//    private void initlistView() {
//        for (int i = 0; i < COLUMN * ROW * PAGE_NUM; i++) {
//            int bg_index = random.nextInt(15);
//            appBgNames[i] = "app_" + bg_index + ".png";
//        }
//        float z = -4.5f;
//        float zz = z + 0.11f;
//        XLabel fav = new XLabel(mScene.getString(R.string.fav));
//        fav.setCenterPosition(-1.2f, 1.3f, zz);
//        fav.setSize(1f, 0.18f);
//        fav.setAlignment(XLabel.XAlign.Center);
//        fav.setArrangementMode(XLabel.XArrangementMode.SingleRowClip);
//        addActor(fav);
//
//        video = new XImageText("tab_left_focused.png", "tab_left_focused.png");
//        video.setName("video");
//        video.setTitle(mScene.getString(R.string.video), mScene.getString(R.string.video));
//        video.setCenterPosition(-0.4f, 1.3f, zz);
//        video.setSize(0.6f, 0.25f);
//        video.setSizeOfImage(0.6f, 0.25f);
//        video.setTitlePosition(0, 0);
//        video.setSizeOfTitle(0.35f, 0.16f);
//        video.setTitleColor(Color.BLACK, Color.BLACK);
//        video.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
//        video.setEventListener(listener);
//        video.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowNotMove, XLabel.XArrangementMode.SingleRowNotMove);
//        addActor(video);
//
//        game = new XImageText("tab_right.png", "tab_right.png");
//        game.setName("game");
//        game.setTitle(mScene.getString(R.string.game), mScene.getString(R.string.game));
//        game.setCenterPosition(0.2f, 1.3f, zz);
//        game.setSize(0.6f, 0.25f);
//        game.setSizeOfImage(0.6f, 0.25f);
//        game.setTitlePosition(0, 0);
//        game.setSizeOfTitle(0.35f, 0.16f);
//        game.setTitleColor(Color.WHITE, Color.WHITE);
//        game.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
//        game.setEventListener(listener);
//        game.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowNotMove, XLabel.XArrangementMode.SingleRowNotMove);
//        addActor(game);
//
//        Bitmap bitmap1 = OperateBitmapUtil.getBitmapRoundCorner(400, 200, R.color.circle_btn_normal, 50);
//        createBitmapTexture(bitmap1, "circle_btn_nromal.png");
//        XImageText helper = new XImageText("kuang.png", "kuang.png");
//        helper.setName("helper");
//        helper.setTitle(mScene.getString(R.string.helper), mScene.getString(R.string.helper));
//        helper.setCenterPosition(1f, 1.3f, zz);
//        helper.setSize(0.8f, 0.25f);
//        helper.setSizeOfImage(0.8f, 0.25f);
//        helper.setSizeOfTitle(0.35f, 0.16f);
//        helper.setTitlePosition(0, 0);
//        helper.setEnableGazeAnimation(true);
//        helper.setGazeAnimation(XAnimation.AnimationType.SCALE, Constants.scalesize, 150);
//        helper.setTitleColor(mScene.getResources().getColor(R.color.text), mScene.getResources().getColor(R.color.text));
//        helper.setTitleAlign(XLabel.XAlign.Center, XLabel.XAlign.Center);
//        helper.setEventListener(listener);
//        helper.setTitleArrangementMode(XLabel.XArrangementMode.SingleRowNotMove, XLabel.XArrangementMode.SingleRowNotMove);
//        addActor(helper);
//
//        data = new XLabel(mScene.getString(R.string.fav_nodata));
//        data.setCenterPosition(0, 0, zz);
//        data.setSize(0.2f, 0.2f);
//        data.setRenderOrder(16);
//        data.setArrangementMode(XLabel.XArrangementMode.SingleRowNotMove);
//        data.setAlignment(XLabel.XAlign.Center);
//        addActor(data);
//        data.setEnabled(false);
//        initbottom();
//    }
//
//    private void initbottom() {
//        String[] selectpic = {"list_left_focused.png", "list_right_focused.png", "list_back_focused.png",
//                "list_delete_focused.png", "list_refresh_focused.png"};
//        String[] unSelectpic = {"list_left.png", "list_right.png", "list_back.png", "list_delete.png", "list_refresh.png"};
//        String[] name = {"left", "right", "back", "delete", "refresh"};
//        for (int i = 0; i < bottombtns.length; i++) {
//            bottombtns[i] = new XImage(selectpic[i], unSelectpic[i]);
//            bottombtns[i].setName(name[i]);
//            bottombtns[i].setCenterPosition(-1f + 0.5f * i, -1.4f, -4.49f);
//            bottombtns[i].setEventListener(listener);
//            bottombtns[i].setSize(0.3f, 0.3f);
//            bottombtns[i].setEnableGazeAnimation(true);
//            bottombtns[i].setGazeAnimation(XAnimation.AnimationType.SCALE, Constants.scalesize, 150);
//            addActor(bottombtns[i]);
//        }
//        XImage fouce = new XImage("black.png");
//        fouce.setCenterPosition(0, -1.4f - 0.25f, -4.8f);
//        fouce.setSize(3f, 0.3f);
//        fouce.setRotationX(-60f);
//        addActor(fouce);
//    }
//
//
//    public void initGridView(List<VideoData> videoDatas) {
//        if (actorGridView == null) {
//            int type = mScene.getIntent().getIntExtra("type", 0);
//            XActorPageView.PageViewDefaultType t = XActorPageView.PageViewDefaultType.values()[type];
//
//            //构造一个GridView，指定类型、页宽、页高、行数和列数
//            actorGridView = new XActorGridView(t, 2.8f, 2.2f, COLUMN, ROW);
//            //初始化Adapter
//            myAdapter = new MainAdapter(videoDatas, (MainScene) mScene);
//            //与PageView一样，GridView也支持设置每一页的控制点从而控制每一页显示位置
//            List<XVec3> temp = new ArrayList<>();
//            temp.add(new XVec3(-8.5f, 0, Z + 0.5f));
//            temp.add(new XVec3(0, 0, Z + 0.5f));
//            temp.add(new XVec3(8.5f, 0, Z + 0.5f));
//            actorGridView.setControlPoints(temp);
//            //设置适配器
//            actorGridView.setAdapter(myAdapter);
//            //设置背景图片
//            actorGridView.setPageBackGroundName("transparent.png");
//            addActor(actorGridView);
//        }
//    }
//
//
//    public void addActor(XActor actor) {
//        if (mScene != null) {
//            mScene.addActor(actor);
//        } else {
//            new Throwable("Scene is null!");
//        }
//    }
//
//    public void showLoading() {
//        hideLoading();
//        if (!isLoading) {
//            isLoading = !isLoading;
//            mScene.runOnRenderThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mLoading != null) {
//                        mLoading.setEnabled(true);
//                        mRotate = new XRotateByAction(1000, 0, 0, -1000 * 360);
//                        mLoading.runAction(mRotate);
//                    }
//                }
//            });
//        }
//    }
//
//    public void hideLoading() {
//        isLoading = false;
//        if (mLoading != null) {
//            if (mRotate != null) {
//                mRotate.setDuration(0);
//            }
//            mLoading.setEnabled(false);
//        }
//    }
//
//    /**
//     * 重置一下gridview的状态
//     */
//    public void resetGridViewState() {
//        if (actorGridView != null) {
//            actorGridView.setCurrentShowPosition(0);//跳转到第三页
//        }
//    }
//
//    /**
//     * 获取到底部按钮的控件
//     */
//    public XImage getBottomBtnActor(int index) {
//        if (index < bottombtns.length) {
//            return bottombtns[index];
//        } else {
//            return null;
//        }
//    }
//
//    public void showErrorTip(String message) {
//        if (data != null) {
//            data.setEnabled(true);
//            data.setTextContent(message);
//        }
//    }

}
