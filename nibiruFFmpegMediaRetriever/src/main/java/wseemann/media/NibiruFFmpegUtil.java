package wseemann.media;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Images;
import android.util.Log;

public class NibiruFFmpegUtil {
	/**
	 * 默认大小256*256
	 * @param filePath
	 * @return
	 */
	public static Bitmap getVideoThumbnail(String filePath) {
		return getVideoThumbnail(filePath, 256, 256);
	}

	public static Bitmap getVideoThumbnail(String filePath, int viewWidth, int viewHeight) {
		Bitmap bitmap = null;
		FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
		try {
			fmmr.setDataSource(filePath);
			Bitmap b2 = fmmr.getScaledFrameAtTime(10000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC,
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

	/**
	 * 使用Android默认方式获取缩略图
	 */
	public static Bitmap getVideoThumbnailDefault(String videoPath, int width, int height) {
		Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, Images.Thumbnails.MINI_KIND);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
