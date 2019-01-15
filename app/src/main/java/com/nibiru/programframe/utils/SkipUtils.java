package com.nibiru.programframe.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/14
 * 描述:用来管理跳转的类
 */

public class SkipUtils {
    /**
     * 跳转到收藏的链接码页面的方法
     */
    public static void goToFAV(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.FAV");
        intent.putExtra("isSettings", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到播放视频的方法
     */
//    public static boolean playVideo(int position, List<VideoData> mVideoList, Context context) {
//        if (position >= mVideoList.size()) {
//            return false;
//        }
//        VideoData videoData = mVideoList.get(position);
//        if (videoData != null && videoData.getVideoSegs() != null && videoData.getVideoSegs().size() > 0) {
//            try {
//                VideoData.VideoSegsBean segsBean = videoData.getVideoSegs().get(0);
//                Intent intent = new Intent("com.nibiru.vr.media.pptv.action.ONLINE_FAV");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("openType_download", segsBean.isOpenType_download());
//                intent.putExtra("openType_sdk", segsBean.isOpenType_sdk());
//                intent.putExtra("openType_serverParse", segsBean.isOpenType_serverParse());
//                intent.putExtra("openType_webview", segsBean.isOpenType_webview());
//                intent.putExtra("uid", videoData.getUid());
//                intent.putExtra("format", segsBean.getFormat());
//                intent.putExtra("playLink", segsBean.getPlayLink());
//                intent.putExtra("parseLink", segsBean.getParseLink());
//                context.startActivity(intent);
//            } catch (Exception e) {
//
//            }
//        }
//        return false;
//    }

    /**
     * 跳转到市场应用详情的界面
     */
//    public static void downloadApp(int position, List<AppDetailData> mAppList, Context context) {
//        if (position >= mAppList.size()) {
//            return;
//        }
//        AppDetailData appDetailData = mAppList.get(position);
//        Intent intent = new Intent("com.nibiru.vrstore.DETAIL");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra("id", appDetailData.getId());
//        context.startActivity(intent);
//    }
}
