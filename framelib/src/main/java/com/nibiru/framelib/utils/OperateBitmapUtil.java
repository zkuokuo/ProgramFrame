package com.nibiru.framelib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2017/10/12
 * 描述:
 */
public class OperateBitmapUtil {

    public static Bitmap circleCrop(Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    /**
     * @des 给bitmap边缘添加透明度
     */
    public static Bitmap getBitmap(Bitmap paramBitmap) {
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth() + 2,
                paramBitmap.getHeight() + 2, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        if (paramBitmap != null) {
            localCanvas.drawBitmap(paramBitmap, null, new RectF(1, 1, paramBitmap.getWidth() + 1, paramBitmap.getHeight() + 1),
                    localPaint);
        }
        return localBitmap;
    }

    public static Bitmap getBackgroundBitmap(int width, int height, int alaph, int r, int g, int b) {
        Paint paint = new Paint();
        paint.setARGB(alaph, r, g, b);
        paint.setTypeface(null);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(new Rect(2, 2, width - 2, height - 2), paint);
        return bitmap;
    }

    public static Bitmap getBitmap(Bitmap paramBitmap, int width, int height) {
        float ratio1 = (float) width / (float) height;
        float ratio2 = (float) height / (float) width;
        float ratio3 = (float) paramBitmap.getWidth() / (float) paramBitmap.getHeight();
        float ratio4 = (float) paramBitmap.getHeight() / (float) paramBitmap.getWidth();
        Bitmap localBitmap;
        if (ratio1 > 1.5f) {
            localBitmap = Bitmap.createBitmap(paramBitmap, 0,
                    (paramBitmap.getHeight() - (int) (paramBitmap.getHeight() * ratio2)) / 2, paramBitmap.getWidth(), (int) (paramBitmap.getHeight() * ratio2));
        } else if (ratio1 < 0.6f) {
            localBitmap = Bitmap
                    .createBitmap(paramBitmap,
                            (paramBitmap.getWidth() - (int) (paramBitmap
                                    .getWidth() * ratio1)) / 2, 0,
                            (int) (paramBitmap.getWidth() * ratio1),
                            paramBitmap.getHeight());
        } else {
            if (ratio3 < ratio4) {
                localBitmap = Bitmap.createBitmap(paramBitmap, 0,
                        (paramBitmap.getHeight() - (int) (paramBitmap
                                .getHeight() * ratio3)) / 2, paramBitmap
                                .getWidth(),
                        (int) (paramBitmap.getHeight() * ratio3));
            } else {
                localBitmap = Bitmap
                        .createBitmap(paramBitmap,
                                (paramBitmap.getWidth() - (int) (paramBitmap
                                        .getWidth() * ratio4)) / 2, 0,
                                (int) (paramBitmap.getWidth() * ratio4),
                                paramBitmap.getHeight());
            }
        }
        Bitmap localBitmap1 = Bitmap.createBitmap(localBitmap.getWidth() + 4,
                localBitmap.getHeight() + 4, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap1);
        Paint localPaint = new Paint();
        if (localBitmap != null) {
            localCanvas.drawBitmap(localBitmap, null, new RectF(2, 2, localBitmap.getWidth() + 2, localBitmap.getHeight() + 2), localPaint);
            // localBitmap.recycle();
        }
        return localBitmap1;
    }

    /**
     * @des 将文字绘制成bitmap
     */
    public static Bitmap generateWLT(String str, float textSize, int width, int height) {
        Bitmap bmTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(bmTemp);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        StaticLayout layout = new StaticLayout(str, textPaint, width, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvasTemp.save();
        layout.draw(canvasTemp);
        canvasTemp.restore();
        return bmTemp;
    }

    /**
     * @des 将bitmap的质量转化为最高的质量
     */
    public static Bitmap getARGB8888Bitmap(Bitmap bitmap) {
        if (bitmap != null && bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            final Bitmap map = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            map.setDensity(bitmap.getDensity());
            Canvas canvas = new Canvas(map);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
            bitmap.recycle();
            return map;
        }
        return bitmap;
    }

    /**
     * @des 创建一个矩形的bitmap
     */
    public static Bitmap generateBitmapRect(int width, int height, int color) {
        Bitmap localBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(color);
        localCanvas.drawRoundRect(new RectF(2, 2, width + 4, height + 4), 2, 2, localPaint);
        return localBitmap;
    }


    public static Bitmap generateBitmap(int width, int height, int color) {
        Bitmap localBitmap = Bitmap.createBitmap(width + 4, height + 4,
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(color);
        localCanvas.drawRect(new RectF(2, 2, width + 2, height + 2), localPaint);
        return localBitmap;
    }

    /**
     * 生成一个圆角bitmap图片
     * pixels 转圆角的弧度
     */
    public static Bitmap getBitmapRoundCorner(int width, int height, int color, int pixels) {
        Bitmap rectWithEdge = generateBitmapRect(width, height, color);
        Bitmap output = toRoundCorner(rectWithEdge, pixels);
        return output;
    }

    /**
     * @des 将矩形bitmap图片转换成四周带圆角的bitmap
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return output;
    }

    /**
     * @des 生成一个带圆角的bitmap
     */
    public static Bitmap generateRoundBitmap(int width, int height, int color, int radius) {
        Bitmap localBitmap = Bitmap.createBitmap(width + 4, height + 4,
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(color);
        localCanvas.drawRoundRect(new RectF(2, 2, width + 2, height + 2), radius, radius, localPaint);
        return localBitmap;
    }

    /**
     * @des 获取到一个圆形bitmap
     */
    public static Bitmap getBitmapCircle(int color) {
        Bitmap localBitmap = Bitmap.createBitmap(256, 256,
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setColor(color);
        localCanvas.drawCircle(128, 128, 127, localPaint);
        return localBitmap;
    }

    /**
     * 将圆角的bitmap去除圆角
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap removeYuanjiao(Bitmap bitmap, int pixels) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap creBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(creBitmap);

        Paint paint = new Paint();
        float roundPx = pixels;
        RectF rectF = new RectF(0, 0, bitmap.getWidth() - pixels,
                bitmap.getHeight() - pixels);
        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (!bitmap.isRecycled())
            bitmap.recycle();

        return creBitmap;
    }

    /**
     * 通过资源id转化成Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap ReadBitmapById(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
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
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap, boolean isRecycled) {

        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }

        // 下面这句是关键
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,
        // false);
    }

    /**
     * 按长方形裁切图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap ImageCropWithRect(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int nw, nh, retX, retY;
        if (w > h) {
            nw = h / 2;
            nh = h;
            retX = (w - nw) / 2;
            retY = 0;
        } else {
            nw = w / 2;
            nh = w;
            retX = w / 4;
            retY = (h - w) / 2;
        }

        // 下面这句是关键
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                false);
        if (bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;// Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
        // false);
    }

    /**
     * Bitmap --> byte[]
     *
     * @param bmp
     * @return
     */
    public static byte[] readBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 将图像裁剪成圆形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return output;
    }

    /**
     * @des 将图片变成带圆边的圆形图片
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        // 将图片变成圆角
        Bitmap roundBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int len = (width > height) ? height : width;
        canvas.drawCircle(width / 2, height / 2, len / 2 - 8, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len, true);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        // 将图片加圆边
        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(outBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffffffff);
        canvas.drawCircle(width / 2, height / 2, len / 2 - 4, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(roundBitmap, 0, 0, paint);
        bitmap.recycle();
        bitmap = null;
        roundBitmap.recycle();
        roundBitmap = null;
        scaledBitmap.recycle();
        scaledBitmap = null;
        return outBitmap;
    }

    /**
     * @des 将图片变成带圆边的圆形图片
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int width, int height, int color) {
        if (bitmap == null) {
            return null;
        }
        // 将图片变成圆角
        Bitmap roundBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int len = (width > height) ? height : width;
        canvas.drawCircle(width / 2, height / 2, len / 2 - 8, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len, true);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);
        // 将图片加圆边
        Bitmap outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(outBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        canvas.drawCircle(width / 2, height / 2, len / 2 - 4, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(roundBitmap, 0, 0, paint);
        bitmap.recycle();
        bitmap = null;
        roundBitmap.recycle();
        roundBitmap = null;
        scaledBitmap.recycle();
        scaledBitmap = null;
        return outBitmap;
    }

    /**
     * @des 获得指定大小的圆边的bitmap数组
     */
    public static ArrayList<Bitmap> getRadiusBitmapList(String[] pathArray, int size, int len, float radius, int color) {
        Bitmap canvasBitmap = null;
        Canvas canvas = null;
        Paint paint = null;
        RectF rectF = new RectF(0, 0, len - radius, len - radius);
        File file = null;
        FileInputStream fis = null;
        Bitmap bitmap = null;
        Bitmap scaledBitmap = null;

        ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        for (int i = 0; i < pathArray.length; i++) {
            file = new File(pathArray[i]);
            if (!file.exists())
                continue;
            try {
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                if (bitmap != null) {
                    canvasBitmap = Bitmap.createBitmap(len, len,
                            Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(canvasBitmap);
                    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(color);
                    canvas.drawRoundRect(rectF, radius, radius, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, len, len,
                            true);
                    canvas.drawBitmap(scaledBitmap, 0, 0, paint);
                    list.add(canvasBitmap);
                }
            } catch (FileNotFoundException e) {
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e1) {
                    }
                }
            }
            if (list.size() == size)
                break;
        }
        if (scaledBitmap != null && !scaledBitmap.isRecycled()) {
            scaledBitmap.recycle();
            scaledBitmap = null;
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return list;
    }

    /**
     * 按照一定的宽高比例裁剪图片
     *
     * @param bitmap
     * @param width  长边的比例
     * @param height 短边的比例
     * @return
     */
    public static Bitmap ImageCrop(Bitmap bitmap, float width, float height, boolean isRecycled) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX, retY;
        int nw, nh;
        if (w > h) {
            if (h > w * height / width) {
                nw = w;
                nh = (int) (w * height / width);
                retX = 0;
                retY = (h - nh) / 2;
            } else {
                nw = (int) (h * width / height);
                nh = h;
                retX = (w - nw) / 2;
                retY = 0;
            }
        } else {
            if (h > w * height / width) {
                nh = (int) (w * height / width);
                nw = w;
                retY = (h - nh) / 2;
                retX = 0;
            } else {
                nh = h;
                nw = (int) (h * width / height);
                retY = 0;
                retX = (w - nw) / 2;
            }
        }
        if (nh < 1) {
            nh = 1;
        }
        if (nw < 1) {
            nw = 1;
        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null, false);

        if (isRecycled && bitmap != null && !bitmap.equals(bmp) && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return bmp;
    }

    /**
     * 把两个bitmap合成为一个bitmap，以底层位图的长宽为基准
     *
     * @param backBitmap  在底部的位图
     * @param frontBitmap 盖在上面的位图
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {
        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }

    /**
     * 合并两张bitmap,按两张bitmap的原来大小
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 2, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，左右拼接
     *
     * @param leftBitmap
     * @param rightBitmap
     * @param isBaseMax   是否以宽度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_LR(Bitmap leftBitmap, Bitmap rightBitmap, boolean isBaseMax) {

        if (leftBitmap == null || leftBitmap.isRecycled()
                || rightBitmap == null || rightBitmap.isRecycled()) {
            return null;
        }
        int height = 0; // 拼接后的高度，按照参数取大或取小
        if (isBaseMax) {
            height = leftBitmap.getHeight() > rightBitmap.getHeight() ? leftBitmap.getHeight() :
                    rightBitmap.getHeight();
        } else {
            height = leftBitmap.getHeight() < rightBitmap.getHeight() ? leftBitmap.getHeight() :
                    rightBitmap.getHeight();
        }

        // 缩放之后的bitmap
        Bitmap tempBitmapL = leftBitmap;
        Bitmap tempBitmapR = rightBitmap;

        if (leftBitmap.getHeight() != height) {
            tempBitmapL = Bitmap.createScaledBitmap(leftBitmap, (int) (leftBitmap.getWidth() * 1f
                    / leftBitmap.getHeight() * height), height, false);
        } else if (rightBitmap.getHeight() != height) {
            tempBitmapR = Bitmap.createScaledBitmap(rightBitmap, (int) (rightBitmap.getWidth() *
                    1f / rightBitmap.getHeight() * height), height, false);
        }

        // 拼接后的宽度
        int width = tempBitmapL.getWidth() + tempBitmapR.getWidth();

        // 定义输出的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 缩放后两个bitmap需要绘制的参数
        Rect leftRect = new Rect(0, 0, tempBitmapL.getWidth(), tempBitmapL.getHeight());
        Rect rightRect = new Rect(0, 0, tempBitmapR.getWidth(), tempBitmapR.getHeight());

        // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
        Rect rightRectT = new Rect(tempBitmapL.getWidth(), 0, width, height);

        canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null);
        canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null);
        return bitmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() :
                    bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() :
                    bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight
                    () * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap
                    .getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }

    /**
     * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
     *
     * @param target   需要转化bitmap参数
     * @param newWidth 设置新的宽度
     * @return
     */
    public static Bitmap fitBitmapWidth(Bitmap target, int newWidth) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix, true);
        if (target != null && !target.equals(bmp) && !target.isRecycled()) {
            target.recycle();
            target = null;
        }
        return bmp;
    }

    /**
     * fuction: 设置固定的高度，宽度随之变化，使图片不会变形
     *
     * @param target   需要转化bitmap参数
     * @param newheght 设置新的高度
     * @return
     */
    public static Bitmap fitBitmapHeight(Bitmap target, int newheght) {
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        float scaleHeight = ((float) newheght) / height;
        matrix.postScale(scaleHeight, scaleHeight);
        Bitmap bmp = Bitmap.createBitmap(target, 0, 0, width, height, matrix, true);
        if (target != null && !target.equals(bmp) && !target.isRecycled()) {
            target.recycle();
            target = null;
        }
        return bmp;
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
}
