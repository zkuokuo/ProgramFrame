package com.nibiru.programframe.data.model;

import android.graphics.Bitmap;

import java.util.List;

/**
 *  设置数据模型
 */
public class VideoData {
	private boolean isChecked;
	private String actor;
	private String director;
	private int duration;
	private long onlineTime;
	private String slotLink;
	private String thumbLink;
	private String title;
	private int uid;
	private String content;
	private String vid;
	private List<VideoSegsBean> videoSegs;
	private Bitmap pic;

	public Bitmap getPic() {
		return pic;
	}

	public void setPic(Bitmap pic) {
		this.pic = pic;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public String getSlotLink() {
		return slotLink;
	}

	public void setSlotLink(String slotLink) {
		this.slotLink = slotLink;
	}

	public String getThumbLink() {
		return thumbLink;
	}

	public void setThumbLink(String thumbLink) {
		this.thumbLink = thumbLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUid() {
		return uid;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public List<VideoSegsBean> getVideoSegs() {
		return videoSegs;
	}

	public void setVideoSegs(List<VideoSegsBean> videoSegs) {
		this.videoSegs = videoSegs;
	}


	public class VideoSegsBean {

		private int format;
		private boolean openType_download;
		private boolean openType_sdk;
		private boolean openType_serverParse;
		private boolean openType_webview;
		private int platform;
		private String platformDesc;
		private String playLink;
		private String parseLink;

		public int getFormat() {
			return format;
		}

		public void setFormat(int format) {
			this.format = format;
		}

		public boolean isOpenType_download() {
			return openType_download;
		}

		public void setOpenType_download(boolean openType_download) {
			this.openType_download = openType_download;
		}

		public boolean isOpenType_sdk() {
			return openType_sdk;
		}

		public void setOpenType_sdk(boolean openType_sdk) {
			this.openType_sdk = openType_sdk;
		}

		public boolean isOpenType_serverParse() {
			return openType_serverParse;
		}

		public void setOpenType_serverParse(boolean openType_serverParse) {
			this.openType_serverParse = openType_serverParse;
		}

		public boolean isOpenType_webview() {
			return openType_webview;
		}

		public void setOpenType_webview(boolean openType_webview) {
			this.openType_webview = openType_webview;
		}

		public int getPlatform() {
			return platform;
		}

		public void setPlatform(int platform) {
			this.platform = platform;
		}

		public String getPlatformDesc() {
			return platformDesc;
		}

		public void setPlatformDesc(String platformDesc) {
			this.platformDesc = platformDesc;
		}

		public String getPlayLink() {
			return playLink;
		}

		public void setPlayLink(String playLink) {
			this.playLink = playLink;
		}

		public void setParseLink(String parseLink) {
			this.parseLink = parseLink;
		}

		public String getParseLink() {
			return parseLink;
		}
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(uid == ((VideoData) o).getUid()) {
			return true;
		}
		return false;
	}
    
}
