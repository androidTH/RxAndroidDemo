package com.rxandroiddemo.module;


import android.content.Context;

import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.R;
import com.rxandroiddemo.api.ServiceApi;
import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.api.cookie.JavaCookieJar;
import com.rxandroiddemo.api.cookie.PersistentCookieStore;
import com.rxandroiddemo.api.support.HeaderInterceptor;
import com.rxandroiddemo.api.support.LoggingInterceptor;
import com.rxandroiddemo.utils.LogUtils;

import java.io.File;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.Platform;

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
                //.sslSocketFactory(getSSLSocketFactory(MyApp.getsInstance(),new int[]{R.raw.hoootao}))
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

    protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }

        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));

                if (is != null) {
                    is.close();
                }
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");//"TLS" SSL协议 由服务器端确定
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}