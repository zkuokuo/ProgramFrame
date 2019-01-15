package com.nibiru.programframe.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build;

import com.nibiru.programframe.R;
import com.nibiru.programframe.data.model.PathData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import x.core.ui.XBaseScene;
import x.core.ui.XImage;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2018/5/23
 * 描述: 用于绘制bitmap的工具类
 * 给定大小bitmap,按照屏幕大小来转换像素值
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DrawBtimapUtils {
    private Canvas mCanvas;
    private Paint mPaint;
    public Bitmap mTempBitmap;
    private XBaseScene mScene;
    private int ID = R.drawable.banner_mask2;
    private float factorW = 1;
    private float factorH = 1;

    public void setFactorH(float factorH) {
        this.factorH = factorH;
    }

    public void setFactorW(float factorW) {
        this.factorW = factorW;
    }

    public DrawBtimapUtils(XBaseScene scene, float lineWidth) {
        this.mScene = scene;
        initPaint(lineWidth);
    }

    /**
     * 这个设置可以让绘制的轨迹为透明色
     */
    public void setPathTrance() {
        if (mPaint != null) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        }
    }

    public void setPaintColor(int color) {
        if (mPaint != null) {
            mPaint.setColor(color);
        }
    }

    public void setPaintColor(int a, int r, int g, int b) {
        if (mPaint != null) {
            mPaint.setARGB(a, r, g, b);
        }
    }

    public void setPaintAlpa(int size) {
        if (mPaint != null) {
            mPaint.setAlpha(size);
        }
    }


    private void initPaint(float lineWidth) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);//不填充
        mPaint.setStrokeWidth(lineWidth);  //线的宽度
    }

    /**
     * 绘制矩形,正方形
     */
    public Bitmap drawRectangle(float left, float top, float right, float bottom) {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), R.drawable.banner_mask2);//测试使用
//            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        mCanvas.drawRect(left * factorW, top * factorH, right * factorW, bottom * factorH, mPaint);
        return mTempBitmap;
    }

    /**
     * 绘制直线
     */
    public Bitmap drawline(PathData.LinePath linePath) {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        mCanvas.drawLine(linePath.getsX() * factorW, linePath.getsY() * factorH, linePath.geteX() * factorW, linePath.geteY() * factorH, mPaint);
        return mTempBitmap;
    }

    /**
     * 绘制不规则的线
     *
     * @param linePath
     * @return
     */
    public Bitmap drawlinesss(List<PathData.LinePath> linePath) {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        Path path = new Path();
        if (linePath.size() > 0) {
            path.moveTo(linePath.get(0).getsX() * factorW, linePath.get(0).getsY() * factorH);
            if (linePath.size() == 1) {
                path.lineTo(linePath.get(0).geteX() * factorW, linePath.get(0).geteY() * factorH);
            } else {
                for (int i = 1; i < linePath.size(); i++) {
                    path.lineTo(linePath.get(i).geteX() * factorW, linePath.get(i).geteY() * factorH);
                }
            }
            mCanvas.drawPath(path, mPaint);
        }
        return mTempBitmap;
    }

    /**
     * 绘制椭圆
     */
    public Bitmap drawOval(PathData pathData) {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        mCanvas.drawOval(pathData.getOvalLeft() * factorW, pathData.getOvalTop() * factorH, pathData.getOvalRight() * factorW, pathData.getOvalBottom() * factorH, mPaint);
        return mTempBitmap;
    }

    /**
     * 绘制圆
     */
    public Bitmap drawCircle(Point centerpoint, float radius) {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        mCanvas.drawCircle(centerpoint.x, centerpoint.y, radius, mPaint);
        return mTempBitmap;
    }

    /**
     * 画箭头
     */
    public Bitmap drawArrow(PathData.LinePath linePath) {
        float sx = linePath.getsX() * factorW;
        float sy = linePath.getsY() * factorH;
        float ex = linePath.geteX() * factorW;
        float ey = linePath.geteY() * factorH;
        int width = 10;
        int size = 5;
        int count = 20;
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        switch (width) {
            case 0:
                size = 5;
                count = 20;
                break;
            case 5:
                size = 8;
                count = 30;
                break;
            case 10:
                size = 11;
                count = 40;
                break;
        }
        float x = ex - sx;
        float y = ey - sy;
        double d = x * x + y * y;
        double r = Math.sqrt(d);
        float zx = (float) (ex - (count * x / r));
        float zy = (float) (ey - (count * y / r));
        float xz = zx - sx;
        float yz = zy - sy;
        double zd = xz * xz + yz * yz;
        double zr = Math.sqrt(zd);
        Path triangle = new Path();
        triangle.moveTo(sx, sy);
        triangle.lineTo((float) (zx + size * yz / zr), (float) (zy - size * xz / zr));
        triangle.lineTo((float) (zx + size * 2 * yz / zr), (float) (zy - size * 2 * xz / zr));
        triangle.lineTo(ex, ey);
        triangle.lineTo((float) (zx - size * 2 * yz / zr), (float) (zy + size * 2 * xz / zr));
        triangle.lineTo((float) (zx - size * yz / zr), (float) (zy + size * xz / zr));
        triangle.close();
        mPaint.setStyle(Paint.Style.FILL);//不填充
        mCanvas.drawPath(triangle, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);//不填充
        return mTempBitmap;
    }

    /**
     * @des 按照一定的宽高缩放bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        if (bm == null) {
            return null;
        }
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (bm.isRecycled() && bm != null && !bm.equals(newbm) && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        return newbm;
    }

    /**
     * 对bitmap进行旋转处理
     */
    public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        try {
            Bitmap tempbitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return tempbitmap;
        } catch (OutOfMemoryError ex) {
        }
        return bm;
    }

    /**
     * 清除bitmap上绘制的线
     */
    public void clear() {
        if (mTempBitmap == null) {
            Bitmap photo = BitmapFactory.decodeResource(mScene.getResources(), ID);
            mTempBitmap = photo.copy(Bitmap.Config.ARGB_8888, true);
            mCanvas = new Canvas(mTempBitmap);
        }
        mTempBitmap.eraseColor(0x00000000);
    }

    /**
     * 创建纹理ID然后在显示在控件上
     */
    private int mLastTexture;

    private void createTextureIDandShow(final XImage drawimage) {
        mScene.runOnRenderThread(new Runnable() {
            @Override
            public void run() {
                int texture = initTexture(mTempBitmap);
                drawimage.setImgTextureID(texture);
                if (mLastTexture > 0) {
                    GLES20.glDeleteTextures(1, new int[]{mLastTexture}, 0);
                }
                mLastTexture = texture;
            }
        });
    }

    /**
     * 创建纹理ID,不销毁bitmap
     *
     * @param bitmapTmp
     * @return
     */
    public int initTexture(Bitmap bitmapTmp) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //  Mipmap
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        try {
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLUtils.getInternalFormat(bitmapTmp),
                    bitmapTmp,
                    GLUtils.getType(bitmapTmp),
                    0
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        return textureId;
    }

    /**
     * 生成纹理的id,销毁bitmap
     *
     * @param drawableId
     * @return
     */
    public int initTexture(int drawableId) {
        //生成纹理ID
        int[] textures = new int[1];
        GLES20.glGenTextures
                (
                        1,       //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0   //偏移量
                );
        int textureId = textures[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        //通过输入流加载图片
        InputStream is = mScene.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //实际加载纹理
        GLUtils.texImage2D(
                GLES20.GL_TEXTURE_2D,  //纹理类型，在OpenGL ES中必须为GL10.GL_TEXTURE_2D
                0,              //纹理的层次，0表示基本图像层，可以理解为直接贴图
                bitmapTmp,            //纹理图像
                0            //纹理边框尺寸
        );
        bitmapTmp.recycle();   //纹理加载成功后释放图片
        return textureId;
    }

}
