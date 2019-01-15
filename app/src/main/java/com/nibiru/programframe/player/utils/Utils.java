package com.nibiru.programframe.player.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import nibiru.LogManager.LogUtil;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class Utils {
	/**
	 * 获取视频缩略图
	 *
	 * @param videoPath
	 * @param width
	 * @param height
	 * @param kind
	 * @return
	 */
	public static Bitmap createVideoThumbnail(String videoPath, int width,
			int height, int kind) {
		Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static Bitmap getBitmapWithEdge(Bitmap bitmap) {
		Bitmap localBitmap = Bitmap.createBitmap(bitmap.getWidth() + 4,
				bitmap.getHeight() + 4, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		Paint localPaint = new Paint();
		if (bitmap != null) {
			localCanvas.drawBitmap(bitmap, null, new RectF(2, 2, bitmap.getWidth() + 2,
					bitmap.getHeight() + 2), localPaint);
		}
		return localBitmap;
	}
	
	
	
	
	public static Bitmap generateThumbBitmap(Resources r, Bitmap bitmap) {
		int width = Constants.THUMBNAIL_WIDTH;
		int height = Constants.THUMBNAIL_HEIGHT;
		Bitmap localBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(localBitmap);
//		canvas.drawARGB(255, 255, 0, 0);
		Paint tempPaint = new Paint();
		tempPaint.setAntiAlias(true);
		RectF tempRect = new RectF(4, 4, width - 4, height - 4);
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, null, tempRect, tempPaint);
		}
		Bitmap tempBitmap = Utils.getImageFromAssetsFile(r , "ic_video_name_bg.png");
		tempRect = new RectF(0, 0, width, height);
		canvas.drawBitmap(tempBitmap, null, tempRect, tempPaint);
		
//		RectF tempRect_1 = new RectF(4f, 4f, width - 4f, height - 4f);
		tempBitmap = Utils.getImageFromAssetsFile(r , "ic_video_border.png");
		canvas.drawBitmap(tempBitmap, null, tempRect, tempPaint);
		
		return localBitmap;
	}
	
	private static DecimalFormat format = (DecimalFormat) NumberFormat
			.getInstance(Locale.getDefault());

	public static String millisToString(long millis) {
		boolean negative = millis < 0;
		millis = Math.abs(millis);

		millis /= 1000;
		int sec = (int) (millis % 60);
		millis /= 60;
		int min = (int) (millis % 60);
		millis /= 60;
		int hours = (int) millis;

		String time;
		format.applyPattern("00");

		if (millis > 0)
			time = (negative ? "-" : "") + hours + ":" + format.format(min)
					+ ":" + format.format(sec);
		else
			time = (negative ? "-" : "") + min + ":" + format.format(sec);

		return time;
	}
	
	public static Bitmap getVideoThumbnail(String videoPath, long time) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(videoPath);
			LogUtil.d("time: " + time);
			bitmap = retriever.getFrameAtTime(time * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
		} catch (IllegalArgumentException ex) {
			// Assume this is a corrupt video file
		} catch (Exception ex) {
			// Assume this is a corrupt video file.
		} finally {
			try {
				retriever.release();
			} catch (Exception ex) {
				// Ignore failures while cleaning up.
			}
		}
		LogUtil.d((bitmap == null) + "");

		if (bitmap == null)
			return null;

		/*if (kind == Images.Thumbnails.MINI_KIND) {
			// Scale down the bitmap if it's too large.
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			int max = Math.max(width, height);
			if (max > 512) {
				float scale = 512f / max;
				int w = Math.round(scale * width);
				int h = Math.round(scale * height);
				bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
			}
		}*/
		int width = Constants.SEGMENT_THUMBNAIL_WIDTH;
		int height = Constants.SEGMENT_THUMBNAIL_HEIGHT;
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		Bitmap temp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(temp);
		Paint tempPaint = new Paint();
		tempPaint.setAntiAlias(true);
		RectF tempRect = new RectF(0, 0, width, height);
		canvas.drawBitmap(bitmap, null, tempRect, tempPaint);
		bitmap.recycle();
		return temp;
	}
	
	public static Bitmap getVideoThumbnail(String filePath, int viewWidth, int viewHeight, long time) {
		Bitmap bitmap = null;
		FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
		try {
			fmmr.setDataSource(filePath);
			Bitmap b2 = fmmr.getScaledFrameAtTime(1000*time, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,
					viewWidth, viewHeight);
			// fmmr.getFrameAtTime(10000000,
			// FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC);
			if (b2 != null) {
				bitmap = b2;
			}
			// 如果图片宽度规格超过viewWidth,则进行压缩
			if (bitmap != null && bitmap.getWidth() > viewWidth) {
				bitmap = ThumbnailUtils.extractThumbnail(bitmap, viewWidth, viewHeight,
						ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			}

		} catch (IllegalArgumentException ex) {
			Log.d("ccc", "getVideoThumbnail：" + ex.getLocalizedMessage());
		} finally {
			fmmr.release();
		}
		return bitmap;
	}

	public static Bitmap getImageFromAssetsFile(Resources resources , String fileName) {
		Bitmap image = null;
		AssetManager am = resources.getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * 获取圆角位图的方法
	 * @param bitmap 需要转化成圆角的位图
	 * @param pixels 圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
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
		return output;
	}

}
