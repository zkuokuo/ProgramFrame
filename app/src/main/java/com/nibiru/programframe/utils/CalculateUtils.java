package com.nibiru.programframe.utils;

import java.math.BigDecimal;

public class CalculateUtils {
    public static final float AA = 5.55f;
    public static final float BB = 0.39f;
    public static final float CC = 1.65f;
    public static final float DD = 0.375f;

    //z轴坐标
    public static float Z = -1.5f - 1f;
    public static float zz = -1.5f;
    //面片整体高度
    public static float maxH = 1080f;
    private static float mFactor;

    static {
        float ps = 0.0f;
        float z = 14.41f + zz;
        float maxHeight = maxH;
        if (z <= 10) {
            ps = sub(AA, (mul(BB, z)));
        } else {
            ps = sub(CC, mul(DD, sub(z, 10)));
        }
        mFactor = maxHeight / ps * 0.3f;
    }

    public static float transformSize(float px) {
        return px / mFactor;
    }

    /**
     * @param width
     * @param marginLeft 左上顶点到X边界的距离
     * @return
     */
    public static float transformCenterX(float width, float marginLeft) {
        return (width / 2f + marginLeft - 1440 / 2f) / mFactor;
    }

    /**
     * @param width
     * @param marginLeft 离父控件左边的距离
     * @param referWidth 父控件的宽度
     * @return
     */
    public static float transformCenterX(float width, float marginLeft, float referWidth) {
        return (width / 2f + marginLeft - referWidth / 2f) / mFactor;
    }

    /**
     * @param height
     * @param marginTop 左上定点到Y边界的距离
     * @return
     */
    public static float transformCenterY(float height, float marginTop) {
        return -(height / 2f + marginTop - 1080 / 2f) / mFactor;
    }

    /**
     * @param height
     * @param marginTop   离父控件上边沿的距离
     * @param referHeight 父控件的高度
     * @return
     */
    public static float transformCenterY(float height, float marginTop, float referHeight) {
        return -(height / 2f + marginTop - referHeight / 2f) / mFactor;
    }

    private static float mul(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.multiply(b2).floatValue();
    }

    private static float sub(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.subtract(b2).floatValue();
    }

    private static float add(float value1, float value2) {
        BigDecimal b1 = new BigDecimal(Float.toString(value1));
        BigDecimal b2 = new BigDecimal(Float.toString(value2));
        return b1.add(b2).floatValue();
    }
}
