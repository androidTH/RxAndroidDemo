package com.rxandroiddemo.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;

import com.rxandroiddemo.R;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class AppConstant {

    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com/";

    public static final String API_BASE_URL = "http://www.zhuangbi.info/";
    public static final String API_BASE_GANK_URL = "http://gank.io/api/";
    public static final String API_BASE_163_URL = "http://c.m.163.com/";

    public static final int MILLISECONDS_300 = 300;
    public static final int MILLISECONDS_400 = 400;
    public static final int MILLISECONDS_600 = 600;

    public static final long TIME_OUT = 8 * 1000;
    public static boolean mSwitch;
    // 房产id
    public static final String HOUSE_ID = "5YyX5Lqs";

    public static final String HOME_CURRENT_TAB_POSITION="HOME_CURRENT_TAB_POSITION";

    public static int[] colors=new int[]{
            Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW
    };

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.can_not_find_version_name);
        }
    }

    /**
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
