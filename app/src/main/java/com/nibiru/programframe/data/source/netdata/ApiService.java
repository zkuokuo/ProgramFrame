package com.nibiru.programframe.data.source.netdata;

import com.nibiru.programframe.data.model.ArticleListResponse;
import com.nibiru.programframe.data.model.Banner;
import com.nibiru.programframe.data.model.BaseResponse;
import com.nibiru.programframe.data.model.Category;
import com.nibiru.programframe.data.model.HotKey;
import com.nibiru.programframe.data.model.LoginResponse;
import com.nibiru.programframe.data.model.NavCategory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


	// 获取首页文章数据
	@GET("article/list/{page}/json")
	Observable<BaseResponse<ArticleListResponse>> getArticles(@Path("page") int page);

	// 获取首页 banner 数据
	@GET("banner/json")
	Observable<BaseResponse<List<Banner>>> getBannerData();

	// 获取项目分类
	@GET("project/tree/json")
	Observable<BaseResponse<List<Category>>> getProjectCategories();

	// 获取项目分类下文章数据
	@GET("project/list/{page}/json")
	Observable<BaseResponse<ArticleListResponse>> getProjectArticles(
            @Path("page") int page, @Query("cid") int cid
    );

	// 获取体系分类
    @GET("tree/json")
    Observable<BaseResponse<List<Category>>> getHierarchyCategories();

   	// 获取体系分类下的文章
    @GET("article/list/{page}/json")
    Observable<BaseResponse<ArticleListResponse>> getHierarchyArticles(
            @Path("page") int page, @Query("cid") int cid
    );

	// 获取导航数据
    @GET("navi/json")
    Observable<BaseResponse<List<NavCategory>>> getNavCategories();

	// 搜索热词
	@GET("hotkey/json")
	Observable<BaseResponse<List<HotKey>>> getHotKey();

	// 搜索
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleListResponse>> searchArticles(
            @Path("page") int page, @Field("k") String keyword
    );

    // 登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResponse>> signin(
            @Field("username") String username, @Field("password") String password
    );

    // 注册
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginResponse>> signup(
            @Field("username") String username,
            @Field("password") String password,
            @Field("repassword") String repassword
    );
}