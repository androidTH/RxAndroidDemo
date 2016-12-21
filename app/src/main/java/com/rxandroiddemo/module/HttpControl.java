package com.rxandroiddemo.module;


import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.api.ServiceApi;
import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.api.cookie.JavaCookieJar;
import com.rxandroiddemo.api.cookie.PersistentCookieStore;
import com.rxandroiddemo.api.support.HeaderInterceptor;
import com.rxandroiddemo.api.support.LoggingInterceptor;
import com.rxandroiddemo.utils.LogUtils;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * okhttp 工具类
 */
public class HttpControl {

    private static volatile OkHttpClient clientSingleton;

//    private static File cacheFile = new File(MyApp.getsInstance().getCacheDir(), "Test"); // /data/data/包名/cache
    private static File cacheFile = new File(MyApp.getsInstance().getExternalCacheDir(), "Test"); // /sdcard/Android/data/包名/cache
    private static Cache cacheData = new Cache(cacheFile, 1024 * 1024 * 10);

    public static OkHttpClient provideOkHttpClient() {
        LoggingInterceptor logging = new LoggingInterceptor(new MyLog());
        logging.setLevel(LoggingInterceptor.Level.BODY);

        CookieHandler cookieHandler = new CookieManager(new PersistentCookieStore(MyApp.getsInstance()),
                CookiePolicy.ACCEPT_ALL);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .cache(cacheData)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(logging)
                .cookieJar(new JavaCookieJar(cookieHandler));
        return builder.build();
    }

    public static class MyLog implements LoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            LogUtils.i("LoggingInterceptor",message);
        }
    }

    public static OkHttpClient getInstance() {
        if (clientSingleton == null) {
            synchronized (HttpControl.class) {
                if (clientSingleton == null) {
                    clientSingleton=provideOkHttpClient();
                    return clientSingleton;
                }
            }
        }
        return clientSingleton;
    }
}