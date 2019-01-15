package com.nibiru.programframe.data.model;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/14
 * 描述:
 */

public class FavBaseResponse<T> {
    public int status;
    public int resCode;
    private T appList;
    private T videoDetailViewSet;

    public T getAppList() {
        return appList;
    }

    public void setAppList(T appList) {
        this.appList = appList;
    }

    public T getVideoDetailViewSet() {
        return videoDetailViewSet;
    }

    public void setVideoDetailViewSet(T videoDetailViewSet) {
        this.videoDetailViewSet = videoDetailViewSet;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }



}
