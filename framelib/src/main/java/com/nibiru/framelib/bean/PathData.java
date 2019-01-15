package com.nibiru.framelib.bean;

import java.util.List;

/**
 * Created by EGuang on 2018/7/17.
 */
public class PathData {
    int shape;
    int paintColor;
    float ovalLeft;
    float ovalTop;
    float ovalRight;
    float ovalBottom;
    String uuid;
    //针对不规则线条
    List<LinePath> movePathList;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public float getOvalLeft() {
        return ovalLeft;
    }

    public void setOvalLeft(float ovalLeft) {
        this.ovalLeft = ovalLeft;
    }

    public float getOvalTop() {
        return ovalTop;
    }

    public void setOvalTop(float ovalTop) {
        this.ovalTop = ovalTop;
    }

    public float getOvalRight() {
        return ovalRight;
    }

    public void setOvalRight(float ovalRight) {
        this.ovalRight = ovalRight;
    }

    public float getOvalBottom() {
        return ovalBottom;
    }

    public void setOvalBottom(float ovalBottom) {
        this.ovalBottom = ovalBottom;
    }

    public List<LinePath> getMovePathList() {
        return movePathList;
    }

    public void setMovePathList(List<LinePath> movePathList) {
        this.movePathList = movePathList;
    }

    public static class LinePath {
        float sX;
        float sY;
        float eX;
        float eY;

        public LinePath(float sX, float sY, float eX, float eY) {
            this.sX = sX;
            this.sY = sY;
            this.eX = eX;
            this.eY = eY;
        }

        public float getsX() {
            return sX;
        }

        public void setsX(float sX) {
            this.sX = sX;
        }

        public float getsY() {
            return sY;
        }

        public void setsY(float sY) {
            this.sY = sY;
        }

        public float geteX() {
            return eX;
        }

        public void seteX(float eX) {
            this.eX = eX;
        }

        public float geteY() {
            return eY;
        }

        public void seteY(float eY) {
            this.eY = eY;
        }
    }
}
