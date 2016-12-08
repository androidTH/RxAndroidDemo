package com.rxandroiddemo;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/2 13:42
 * @Description
 */

public class MyApp extends MultiDexApplication {

    public static MyApp mApp;


    public MyApp() {
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp=this;
    }

    public static  MyApp getsInstance(){
        return mApp;
    }
}
