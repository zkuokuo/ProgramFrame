package com.nibiru.programframe.player.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import nibiru.LogManager.LogUtil;
import nibiru.player.bean.VideoData;
import wseemann.media.NibiruFFmpegUtil;

public class MediaManager {
	private Context mContext;
	private static MediaManager self;
	public OnMediaScanListener mListener;
	private ScanVideoTask scanVideoTask;
	private LruCache<String, Bitmap> lruCache;
	private DiskLruCache mDiskLruCache;

	public static MediaManager getInstance(Context mContext) {
		if (self == null) {
			synchronized (MediaManager.class) {
				if (self == null) {
					self = new MediaManager(mContext);
				}
			}
		}
		return self;
	}

	private MediaManager(Context mContext) {
		this.mContext = mContext.getApplicationContext();
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);// 获取最大的运行内存
		int maxSize = maxMemory / 4;
		lruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 这个方法会在每次存入缓存的时候调用
				return value.getRowBytes() * value.getHeight() / 1024;
			}

			/* 当缓存大于我们设定的最大值时，会调用这个方法，我们可以用来做内存释放操作 */
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
				if (evicted && oldValue != null && !oldValue.isRecycled()) {
					LogUtil.d("图片资源回收：" + key);
					oldValue.recycle();
				}
			}
		};

		try {
			File cacheDir = getDiskCacheDir(mContext.getApplicationContext(), "thumb");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(mContext.getApplicationContext()), 1,
					20 * 1024 * 1024);
		} catch (IOException e) {
			LogUtil.d(e.getLocalizedMessage());
		}
	}

	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		File cacheDirFile = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cacheDirFile = context.getExternalCacheDir();
			// 手动删除了SDCard/Android/data/你的应用包名/cache/目录
			if (cacheDirFile == null) {
				cacheDirFile = context.getCacheDir();
			}
		} else {
			cacheDirFile = context.getCacheDir();
		}
		cachePath = cacheDirFile.getPath();
		return new File(cachePath + File.separator + uniqueName);
	}

	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public boolean isScanning() {
		return scanVideoTask != null;
	}

	public void scanVideo() {
		if(isScanning()) {
			scanVideoTask.stopTask();
			scanVideoTask.cancel(true);
		}
		scanVideoTask = new ScanVideoTask();
		scanVideoTask.execute();
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null && bitmap != null) {
			synchronized (lruCache) {
				lruCache.put(key, bitmap);
			}
		}
	}

	private Bitmap getBitmapFromMemCache(String key) {
		Bitmap bitmap = null;
		// 先从硬引用缓存中获取
		synchronized (lruCache) {
			bitmap = lruCache.get(key);
			if (bitmap != null) {
				// 找到该Bitmap之后，将其移到LinkedHashMap的最前面，保证它在LRU算法中将被最后删除。
				lruCache.remove(key);
				lruCache.put(key, bitmap);
				return bitmap;
			}
		}
		return null;
	}

	public Bitmap getBitmapFromDiskCache(String imageUrl) {
		InputStream is = null;
		try {
			String key = hashKeyForDisk(imageUrl);
			DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
			if (snapShot != null) {
				is = snapShot.getInputStream(0);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				LogUtil.d("use disk cache!");
				return bitmap;
			}
		} catch (IOException e) {
			LogUtil.d(e.getLocalizedMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void addBitmapToDiskCache(String imageUrl, Bitmap bmp) {
		String key = hashKeyForDisk(imageUrl);
		if (hasDiskCache(imageUrl))
			return;
		try {
			DiskLruCache.Editor editor = mDiskLruCache.edit(key);
			if (null == editor) {
				return;
			}
			editor.newOutputStream(0).write(bitmapToByte(bmp));
			editor.commit();
			mDiskLruCache.flush();
		} catch (IOException e) {
			LogUtil.d(e.getLocalizedMessage());
		}
	}
	
	public byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}
	
	public boolean hasDiskCache(String imageUrl) {
		String key = hashKeyForDisk(imageUrl);
		DiskLruCache.Snapshot snapShot = null;
		try {
			snapShot = mDiskLruCache.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return snapShot != null;
	}
	
	public static String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	public Bitmap getVideoThumbToCache(String path) {
		return lruCache.get(path);
	}

	public Bitmap getVideoThumb(VideoData video) {
		return getVideoThumbToCache(video.getPath());
	}
	
	public void addVideoThumbToCache(String path, Bitmap bitmap) {
		if (path == null || bitmap == null)
			return;
		if (getVideoThumbToCache(path) == null) {
			// 当前地址没有缓存时，就添加
			lruCache.put(path, bitmap);
		}
	}

	private class ShowThumbByTask extends AsyncTask<Void, Void, Bitmap> {
		private VideoData videoData;

		public ShowThumbByTask(VideoData videoData) {
			this.videoData = videoData;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			String path = videoData.getPath();
			// 先获取内存中的Bitmap
			Bitmap bitmap = getBitmapFromMemCache(path);
			LogUtil.d("Player", "bitmap memory cache: " + (bitmap == null));
			// 后获取文件缓存
			if (bitmap == null || bitmap.isRecycled()) {
				bitmap = getBitmapFromDiskCache(path);
				LogUtil.d("Player", "bitmap disk cache: " + (bitmap == null));
			}
			// 若该Bitmap不在内存缓存中，则将其加入到调度任务列表中
			if (bitmap == null) {
				bitmap = Utils.createVideoThumbnail(path,
						Constants.THUMBNAIL_WIDTH, Constants.THUMBNAIL_HEIGHT,
						MediaStore.Video.Thumbnails.MINI_KIND);
				if (bitmap != null) {
					bitmap = addCashThum(path , bitmap);
					LogUtil.d("Player", "First get thum way");
				}
			}
			
			if(bitmap == null) {
				bitmap = Utils.getVideoThumbnail(path, 1);
				if (bitmap != null) {
					bitmap = addCashThum(path , bitmap);
					LogUtil.d("Player", "Second get thum way");
				}
			}

			//下面的两个加入第三方的获取缩略图的方法后放开
			if (bitmap == null) {
				bitmap = NibiruFFmpegUtil.getVideoThumbnail(path, 128, 128);
				if (bitmap != null) {
					bitmap = addCashThum(path , bitmap);
					LogUtil.d("Player", "Third get thum way(第三方)");
				}
			}

			if (bitmap == null) {
				bitmap = Utils.getVideoThumbnail(path, 128, 128, 5);
				if (bitmap != null) {
					bitmap = addCashThum(path , bitmap);
					LogUtil.d("Player", "Fourth get thum way");
				}
			}
			
			if (bitmap == null) {
				// 修改成默认图片
				bitmap = Utils.getImageFromAssetsFile(mContext.getResources() , "first_small.png");
				if (bitmap != null) {
					bitmap = addCashThum(path , bitmap);
					LogUtil.d("Player", "All ways can not get thum, use default thum!!!");
				}
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			/*if(bitmap == null) {
				return;
			}*/
			videoData.setTextureName("texture_" + videoData.getId());
			videoData.setThumb(bitmap);
			if(mListener != null) {
				mListener.onMediaScanUpdate(videoData);
			}
		}
	}

	private Bitmap addCashThum(String path , Bitmap bitmap) {
		bitmap = Utils.toRoundCorner(bitmap ,7);
		bitmap = Utils.generateThumbBitmap(mContext.getResources(), bitmap);
		addBitmapToMemoryCache(path, bitmap);
		addBitmapToDiskCache(path, bitmap);
		return bitmap;
	}
	
	private class ScanVideoTask extends AsyncTask<Void, Void, List<VideoData>> {
		private boolean isRun = false;

		public void stopTask() {
			isRun = false;
		}

		@Override
		protected List<VideoData> doInBackground(Void... params) {
			isRun = true;
			return getVideoList();
		}
		
		@Override
		protected void onPostExecute(List<VideoData> result) {
			if(!isRun) {
				return;
			}
			if(mListener != null) {
				mListener.onMediaScanResult(result);
			}
			isRun = false;



		}
	}
	
	//将指定的文件更新至媒体库中
//	public void test_scanMediaVideo(){
//		MediaScannerConnection.scanFile(mContext, new String[] {  }, null, new MediaScannerConnection.OnScanCompletedListener() {
//			
//			@Override
//			public void onScanCompleted(String path, Uri uri) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}

//	List<String> audio360List;
	public List<VideoData> getVideoList() {
//		audio360List = new ArrayList<>();
//		audio360List = getAudio360FileToSeparate();
		LogUtil.d("Player" , "getVideoList方法开始::" + System.currentTimeMillis());
		List<VideoData> list = null;
		Cursor cursor = mContext.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		if (cursor != null) {
			list = new ArrayList<VideoData>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
				String album = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
				String artist = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
				String displayName = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
				String mimeType = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
				String path = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
				//因为视频资源的获取是从媒体库中获得，不能实时得到修改的视频title，故从路径中截取视频title
				String[] tempStrs = path.split("/");
				String title = /*cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))*/tempStrs[tempStrs.length - 1];
				long dateModified = cursor.getLong(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));
				long duration = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

				//获取视频的宽高
				int width = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
				int height = cursor.getInt(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT));

				long size = cursor.getLong(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
//				String pinyin = PinyinUtils.getPingYin(title);
//				String sortLetter = pinyin.substring(0, 1).toUpperCase(Locale.getDefault());
				VideoData video = new VideoData(id, title, album, artist,
						displayName, mimeType, path, duration, width, height, size, dateModified, "aa"/*sortLetter*/);
				
//				if (!audio360List.contains(video.getPath())){//用于audio360定制的播放器
					new ShowThumbByTask(video).execute();
					list.add(video);
//				}
			}
			cursor.close();
		}
		LogUtil.d("Player" , "getVideoList方法结束::" + System.currentTimeMillis() + ",list的size： " + list.size());
		return list;
	}
	
	public void realease() {
		if (lruCache != null) {
			lruCache.evictAll();
		}
	}

	public void setMediaListener(OnMediaScanListener listener) {
		this.mListener = listener;
	}

	public interface OnMediaScanListener {

		public void onMediaScanResult(List<VideoData> list);
		
		public void onMediaScanUpdate(VideoData video);
	}

	private List<String> videoFilePathList;
	public List<String> getAudio360FileToSeparate(){
		videoFilePathList = new ArrayList<>();
		File file = new File("/sdcard/audio360");
		if (file.exists()){
			LogUtil.d("audio360文件夹下的name ：" + file.getName());
			File[] files = file.listFiles();
			for(int i = 0 ; i < files.length ; i ++){
				LogUtil.d("audio360文件夹下的文件路径是：" + files[i].getPath().toString());
				String tempName = "/storage/emulated/0/audio360/" + files[i].getName();
				videoFilePathList.add(tempName);
				LogUtil.d("audio360文件夹下的文件转化成媒体库的路径是 ：" + tempName);
			}
		} else {
			LogUtil.d("audio360文件夹不存在，等待360本地播放器打开创建");
		}
		return videoFilePathList;
	}

}
