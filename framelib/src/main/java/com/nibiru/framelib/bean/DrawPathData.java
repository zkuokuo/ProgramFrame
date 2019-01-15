package com.nibiru.framelib.bean;

import java.util.List;

/**
 * Created by EGuang on 2018/7/17.
 */

public class DrawPathData {
    int code;
    int displayWidth;
    int displayHeight;
    public List<PathData> pathDataList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public List<PathData> getPathDataList() {
        return pathDataList;
    }

    public void setPathDataList(List<PathData> pathDataList) {
        this.pathDataList = pathDataList;
    }
}
