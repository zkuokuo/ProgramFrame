package com.nibiru.programframe.data.model;

import android.graphics.Bitmap;

import java.util.List;


/**
 * 作者:dick
 * 邮箱:dick.zhang@inibiru.com
 * 创建日期: 2017/4/24
 * 描述:
 */
public class AppDetailData {
    public String appDesc;
    public String appName;
    public int category;
    public int download;
    public String iconUrl;
    public int id;
    public String md5code;
    public String packageName;
    public float score;
    public long size;
    public int source;
    public String sourceUrl;
    public int supportDevice;
    public int type;
    public String versionCode;
    public String versionName;
    public List<String> shotCuts;

    public Bitmap pic;
    private String bitmapTextureName;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getBitmapTextureName() {
        return bitmapTextureName;
    }

    public void setBitmapTextureName(String bitmapTextureName) {
        this.bitmapTextureName = bitmapTextureName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMd5code() {
        return md5code;
    }

    public void setMd5code(String md5code) {
        this.md5code = md5code;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getSupportDevice() {
        return supportDevice;
    }

    public void setSupportDevice(int supportDevice) {
        this.supportDevice = supportDevice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public List<String> getShotCuts() {
        return shotCuts;
    }

    public void setShotCuts(List<String> shotCuts) {
        this.shotCuts = shotCuts;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        if (id == ((AppDetailData) o).getId()) {
            return true;
        }
        return false;
    }
}
