package com.rxandroiddemo;

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
    public void onCreate() {
        super.onCreate();
        this.mApp=this;
    }

    public static  MyApp getsInstance(){
        return mApp;
    }
}
