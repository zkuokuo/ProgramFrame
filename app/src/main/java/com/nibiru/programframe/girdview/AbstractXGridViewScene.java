package com.nibiru.programframe.girdview;

import java.util.ArrayList;
import java.util.List;

import x.core.listener.IXActorKeyEventListener;
import x.core.ui.XActor;
import x.core.ui.XBaseScene;
import x.core.ui.group.XActorGridView;
import x.core.ui.group.XActorPageView;
import x.core.util.XVec3;

/**
 * Created by Nibiru on 2018/2/26.
 */

public abstract class AbstractXGridViewScene<E> extends XBaseScene {
    protected int COLUMN = 3;//列数
    protected int ROW = 2;//行数
    protected float ACTOR_WIDTH = 3.3f;//控件宽度
    protected float ACTOR_HEIGHT = 2.55f;//控件高度
    protected float gap_H = 0.235f;
    protected float gap_V = 0.28f;
    private List<E> sceneBeanList = new ArrayList<>();
    public XActorGridView xActorGridView;
    protected GridViewAdapter myAdapter;
    protected boolean isPageTurning;
    protected boolean isPageTurning_lzh = true;
    protected int mCurrentPage = 1;
    protected int mCurrentCount = 1;

    @Override
    public void onCreate() {
        initGridView();
    }

    public void initGridView() {
        XActorPageView.PageViewDefaultType t = XActorPageView.PageViewDefaultType.values()[0];
        //构造一个GridView，指定类型、页宽、页高、行数和列数
        xActorGridView = new XActorGridView(t, ACTOR_WIDTH * COLUMN + (COLUMN - 1) * gap_H,
                ACTOR_HEIGHT * ROW + (ROW - 1) * gap_V, ROW, COLUMN);
        List<XVec3> temp = new ArrayList<>();
        temp.add(new XVec3(-15.8f, 0.0f, -29f));
        temp.add(new XVec3(0, 0.0f, -7));
        temp.add(new XVec3(15.8f, 0.0f, -29f));
        xActorGridView.setControlPoints(temp);
        myAdapter = new GridViewAdapter(sceneBeanList, this);
        xActorGridView.translateBy(0f, 1.2f, 0f);
        //设置适配器
        xActorGridView.setAdapter(myAdapter);
        //设置显示页的数量和布局方式
        xActorGridView.setVisiblePageLayout(XActorPageView.VisiblePage.LEFT_MIDDLE_RIGHT);
        //设置背景图片
//        xActorGridView.setPageBackGroundName("test_none.png");
        //关闭循环翻页
        xActorGridView.setCirculation(false);
        //关闭点击两侧页面触发翻页的功能（默认开启）
        xActorGridView.setEnableSidePageTriggerMove(true);
        //设置页切换动画更新，在开始动画和结束动画时回调
        xActorGridView.setPageAnimationListener(new XActorPageView.IXPageAnimationListener() {
            @Override
            public void onBegin() {
                isPageTurning = false;
                isPageTurning_lzh = false;
            }

            //onEnd回调参数为当前处于正中间的页对象
            @Override
            public void onEnd(XActorPageView.XPageState state) {
                isPageTurning = true;
                isPageTurning_lzh = true;
            }
        });
        xActorGridView.setKeyEventListener(new IXActorKeyEventListener() {
            @Override
            public boolean onKeyDown(int keyCode) {
                return false;
            }

            @Override
            public boolean onKeyUp(int i) {
                return false;
            }
        });
        addActor(xActorGridView);
    }

    /**
     * 左右翻页
     *
     * @param istoRight
     */
    public void doPageLeftRight(boolean istoRight) {
        if (sceneBeanList.size() < 0) {
            return;
        }
        if (istoRight) {
            if (mCurrentPage == mCurrentCount) {
                return;
            }
            if (!isPageTurning) {
                return;
            }
            if (getPosition() == sceneBeanList.size() - 1 && (getPosition() + 1) % (COLUMN * ROW) == 0) {
                loadMore();
            } else {
                mCurrentPage++;
                xActorGridView.moveRight();
            }
        } else {
            if (mCurrentPage == 1) {
                return;
            }
            if (!isPageTurning) {
                return;
            }
            mCurrentPage--;
            xActorGridView.moveLeft();
        }
        updateControllerButtonState(mCurrentPage > 1, mCurrentPage < mCurrentCount);
        updateControllerLabelState(mCurrentPage);
    }


    /**
     * 翻页时自动回调，加载更多数据，数据加载完后调用
     * 更新数据
     */
    protected void loadMore() {

    }

    /**
     * gridview中的某一个item被点击
     *
     * @param position 数据在集合中的位置
     */
    protected void onGridViewItemOrLayerTrigger(XActor xActor, int position) {

    }

    /**
     * 当小白点进入item的时候
     */
    protected void onGridViewItemEnter(XActor xActor, int position) {

    }

    /**
     * 当小白点退出item的时候
     */
    protected void onGridViewItemExit(XActor xActor, int position) {

    }

    /**
     * 创建gridview中的单个Item
     *
     * @return
     */
    protected abstract XActor getGridViewActor();

    /**
     * 更新翻页的Button
     *
     * @param isFirstPage
     * @param isEndPage
     */
    protected void updateControllerButtonState(boolean isFirstPage, boolean isEndPage) {

    }

    /**
     * 更新当前页显示
     */
    protected void updateControllerLabelState(int currentPage) {

    }

    /**
     * 更新纹理
     *
     * @param xImageText
     * @param position
     */
    protected abstract void updateView(XActor xImageText, int position);

    public void notifyDataSetChanged(List<E> sceneBeanList) {
        this.sceneBeanList = sceneBeanList;
        mCurrentCount = ((sceneBeanList.size() - 1) / (ROW * COLUMN) + 1);
        myAdapter.setSceneBeanList(sceneBeanList);
        myAdapter.notifyDataSetChanged();
        updateControllerLabelState(mCurrentPage);
    }

    public int getPosition() {
        return myAdapter.getPosition();
    }

    public boolean isDeleteClicked = false;
}
