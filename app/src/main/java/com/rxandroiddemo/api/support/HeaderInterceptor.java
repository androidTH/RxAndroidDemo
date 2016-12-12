package com.rxandroiddemo.api.support;

import android.text.TextUtils;

import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.utils.DeviceUtils;
import com.rxandroiddemo.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Cookie拦截器。用于保存和设置Cookies
 *
 * @author yuyh.
 * @date 16/8/6.
 */
public final class HeaderInterceptor implements Interceptor {

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
//        if (original.url().toString().contains("book/") ||
//                original.url().toString().contains("book-list/") ||
//                original.url().toString().contains("toc/") ||
//                original.url().toString().contains("post/") ||
//                original.url().toString().contains("user/")) {
//            Request request = original.newBuilder()
//                    .addHeader("User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]") // 不能转UTF-8
//                    .addHeader("X-User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]")
//                    .addHeader("X-Device-Id", DeviceUtils.getIMEI(MyApp.getsInstance()))
//                    .addHeader("Host", "api.zhuishushenqi.com")
//                    .addHeader("Connection", "Keep-Alive")
//                    .addHeader("If-None-Match", "W/\"2a04-4nguJ+XAaA1yAeFHyxVImg\"")
//                    .addHeader("If-Modified-Since", "Tue, 02 Aug 2016 03:20:06 UTC")
//                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC) //缓存有效期
//                    .build();
//            return chain.proceed(request);
//        }
//        return chain.proceed(original);
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetWorkUtils.isConnected(MyApp.getsInstance())) {
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl)? CacheControl.FORCE_NETWORK:CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isConnected(MyApp.getsInstance())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
