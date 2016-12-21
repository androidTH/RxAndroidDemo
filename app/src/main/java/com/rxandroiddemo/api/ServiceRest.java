package com.rxandroiddemo.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.bean.GankBeautyResult;
import com.rxandroiddemo.bean.Item;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.utils.Constant;
import com.rxandroiddemo.module.HttpControl;
import com.rxandroiddemo.utils.NetWorkUtils;
import com.rxandroiddemo.utils.TimeUtil;
import com.rxandroiddemo.utils.rxandroid.JobExecutor;
import com.rxandroiddemo.utils.rxandroid.SchedulersCompat;
import com.rxandroiddemo.utils.rxutils.GankBeautyResultToItemsMapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/2 11:25
 * @Description
 */

public class ServiceRest {

    public static ServiceRest instance;

    private ServiceApi service;

    private static class SingletonHolder{
        public static ServiceRest mServiceRest=new ServiceRest();
    }

    private static class SingletonGankHolder{
        public static ServiceRest mServiceRest=new ServiceRest("");
    }

    private static class SingletonNewsHolder{
        public static ServiceRest mServiceRest=new ServiceRest("","");
    }

    public static ServiceRest getInstance() {
        return SingletonHolder.mServiceRest;
    }

    public static ServiceRest getGankInstance() {
        return SingletonGankHolder.mServiceRest;
    }

    public static ServiceRest getNewsInstance() {
        return SingletonNewsHolder.mServiceRest;
    }

    public ServiceRest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .client(HttpControl.getInstance())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        service = retrofit.create(ServiceApi.class);
    }

    public ServiceRest(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_GANK_URL)
                .client(HttpControl.getInstance())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        service = retrofit.create(ServiceApi.class);
    }

    public ServiceRest(String s,String t) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_163_URL)
                .client(HttpControl.getInstance())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create(gson)) // 添加Gson转换器
                .build();
        service = retrofit.create(ServiceApi.class);
    }

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "public,max-age=10";
    /**
     * 根据网络状况获取缓存的策略
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isConnected(MyApp.getsInstance()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    public Observable<List<Item>> getBeauties(int number, int page){
        return service.getBeauties(number,page).timeout(Constant.TIME_OUT, TimeUnit.MILLISECONDS).map(GankBeautyResultToItemsMapper.getInstance()).compose(SchedulersCompat.<List<Item>>applyIoSchedulers());
    }

    public Observable<List<ZhuangbiImage>> search(String q){
        return service.search(q).timeout(Constant.TIME_OUT, TimeUnit.MILLISECONDS).compose(SchedulersCompat.<List<ZhuangbiImage>>applyIoSchedulers());
    }

    public Observable<List<NewsSummary>> getNewsListData(final String type, final String id, final int startPage) {
        return service.getNewsList(getCacheControl(),type,id,startPage).flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
            @Override
            public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
                if (id.endsWith(Constant.HOUSE_ID)) {
                    // 房产实际上针对地区的它的id与返回key不同
                    return Observable.from(map.get("北京"));
                }
                return Observable.from(map.get(id));
            }
        }).map(new Func1<NewsSummary, NewsSummary>() {
            @Override
            public NewsSummary call(NewsSummary newsSummary) {
                String ptime = TimeUtil.formatDate(newsSummary.getPtime());
                newsSummary.setPtime(ptime);
                return newsSummary;
            }
        }).distinct().toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
            @Override
            public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
            }
        }).compose(SchedulersCompat.<List<NewsSummary>>applyIoSchedulers());
    };
}
