package com.rxandroiddemo.api;

import com.rxandroiddemo.bean.GankBeautyResult;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/2 11:35
 * @Description
 */

public interface ServiceApi {

    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);
}
