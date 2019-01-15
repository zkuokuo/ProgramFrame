package com.nibiru.framelib.player.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

public class VideoData implements Serializable{
	public static final String TABLE_NAME = "video";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String LAST_DURATION = "last_duration";
	public static final String MODULE_TAG = "module_tag";
	public static final String TOTAL_DURATION = "total_duration";
	public static long total_duration = 0;
	public static final String DECODE_WARE = "decode_ware";
	public static int decode_ware = 100;
	public static final String CLARITY_LEVEL = "clarity_level";
	public static int clarity_level = 1;
	private int id;
	public static int clarity_leveldata = 1;
	private String title;
	private String album;
	private String artist;
	private String displayName;
	private String mimeType;
	private String path;
	private long duration;
	private int width;
	private int height;
	private long size;
	private long dateModified;
	private String sortLetter;
	private long lastDuration;
	
	private Bitmap thumb;
	private String textureName;
	private boolean isTextureRefreshed;
	private boolean isTitleRefreshed;

	public static final String FISHEYE_SIZE = "fisheye_area_size";
	public static int fisheye_area_size = 70;

	public VideoData() {

	}

	public VideoData(int id, String title, String album, String artist,
                     String displayName, String mimeType, String path, long duration,
                     int width , int height,
                     long size, long dateModified, String sortLetter) {
		this.id = id;
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.displayName = displayName;
		this.mimeType = mimeType;
		this.path = path;
		this.duration = duration;
		this.width = width;
		this.height = height;
		this.size = size;
		this.dateModified = dateModified;
		this.sortLetter = sortLetter;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getDateModified() {
		return dateModified;
	}

	public void setDateModified(long dateModified) {
		this.dateModified = dateModified;
	}

	public Bitmap getThumb() {
		return thumb;
	}

	public void setThumb(Bitmap thumb) {
		this.thumb = thumb;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public boolean isTextureRefreshed() {
		return isTextureRefreshed;
	}

	public void setTextureRefreshed(boolean isTextureRefreshed) {
		this.isTextureRefreshed = isTextureRefreshed;
	}

	public boolean isTitleRefreshed() {
		return isTitleRefreshed;
	}

	public void setTitleRefreshed(boolean isTitleRefreshed) {
		this.isTitleRefreshed = isTitleRefreshed;
	}

	public String getSortLetter() {
		return sortLetter;
	}

	public void setSortLetter(String sortLetter) {
		this.sortLetter = sortLetter;
	}

	public long getLastDuration() {
		return lastDuration;
	}

	public void setLastDuration(long lastDuration) {
		this.lastDuration = lastDuration;
	}

	@Override
	public String toString() {
		return "VideoData [id=" + id + ", title=" + title + ", album=" + album
				+ ", artist=" + artist + ", displayName=" + displayName
				+ ", mimeType=" + mimeType + ", path=" + path + ", duration="
				+ duration + ", size=" + size + ", dateModified="
				+ dateModified + ", textureName=" + textureName
				+ ", isTextureRefreshed=" + isTextureRefreshed
				+ ", sortLetter=" + sortLetter + "]";
	}
}
