package com.rxandroiddemo.api;

import com.rxandroiddemo.bean.GankBeautyResult;
import com.rxandroiddemo.bean.Item;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.utils.Constant;
import com.rxandroiddemo.module.HttpControl;
import com.rxandroiddemo.utils.rxandroid.JobExecutor;
import com.rxandroiddemo.utils.rxandroid.SchedulersCompat;
import com.rxandroiddemo.utils.rxutils.GankBeautyResultToItemsMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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

    public static ServiceRest getInstance() {
        return SingletonHolder.mServiceRest;
    }

    public static ServiceRest getGankInstance() {
        return SingletonGankHolder.mServiceRest;
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

    public static ServiceRest getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new ServiceRest();
        return instance;
    }

    public Observable<List<Item>> getBeauties(int number, int page){
        return service.getBeauties(number,page).timeout(Constant.TIME_OUT, TimeUnit.MILLISECONDS).map(GankBeautyResultToItemsMapper.getInstance()).compose(SchedulersCompat.<List<Item>>applyIoSchedulers());
    }

    public Observable<List<ZhuangbiImage>> search(String q){
        return service.search(q).timeout(Constant.TIME_OUT, TimeUnit.MILLISECONDS).compose(SchedulersCompat.<List<ZhuangbiImage>>applyIoSchedulers());
    }
}
