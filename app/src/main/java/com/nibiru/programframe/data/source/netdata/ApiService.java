package com.nibiru.programframe.data.source.netdata;

import com.nibiru.programframe.data.model.AppDetailData;
import com.nibiru.programframe.data.model.FavBaseResponse;
import com.nibiru.programframe.data.model.VideoData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    /*----------------- 获取收藏的视频的数据 --------------------*/
    @POST("NibiruPay/user/listCollectionListV2")
    Observable<FavBaseResponse<List<VideoData>>> getFavVideoData(@Query("version") String version,
                                                                 @Query("channel") String channel,
                                                                 @Query("token") String token,
                                                                 @Query("tokenpackagename") String tokenpackagename,
                                                                 @Query("packagename") String packagename,
                                                                 @Query("url") String url);
    /*----------------- 获取收藏的应用的数据 --------------------*/
    @POST("NibiruPay/user/listCollectionList")
    Observable<FavBaseResponse<List<AppDetailData>>> getFavAppData(@Query("version") String version,
                                                                   @Query("channel") String channel,
                                                                   @Query("token") String token,
                                                                   @Query("tokenpackagename") String tokenpackagename,
                                                                   @Query("packagename") String packagename,
                                                                   @Query("url") String url,
                                                                   @Query("sourcetype") String sourcetype);

    /*----------------- 删除收藏视频调用的接口 --------------------*/
    @POST("NibiruPay/user/deleteCollection")
    Observable<FavBaseResponse> unFavVideoState(@Query("version") String version,
                                                @Query("channel") String channel,
                                                @Query("token") String token,
                                                @Query("tokenpackagename") String tokenpackagename,
                                                @Query("packagename") String packagename,
                                                @Query("sourceid") String sourceid);

    /*----------------- 删除收藏应用调用的接口 --------------------*/
    @POST("NibiruPay/user/deleteCollection")
    Observable<FavBaseResponse> unFavAppState(@Query("version") String version,
                                              @Query("channel") String channel,
                                              @Query("token") String token,
                                              @Query("tokenpackagename") String tokenpackagename,
                                              @Query("packagename") String packagename,
                                              @Query("sourceid") String sourceid,
                                              @Query("sourcetype") String sourcetype);

}