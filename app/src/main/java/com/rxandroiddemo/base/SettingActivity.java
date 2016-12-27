package com.rxandroiddemo.base;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.R;
import com.rxandroiddemo.base.activity.AboutActivity;
import com.rxandroiddemo.base.activity.TextActivity;
import com.rxandroiddemo.ui.statusutils.StatusBarUtil;
import com.rxandroiddemo.utils.AppConstant;
import com.rxandroiddemo.utils.CacheUtils;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.tvCacheSize)
    public TextView mCacheSize;
    @Bind(R.id.tv_version)
    public TextView mVersion;
    @Bind(R.id.cleanCache)
    public RelativeLayout mClearCache;
    @Bind(R.id.aboutUs)
    public RelativeLayout mAboutUs;
    @Bind(R.id.relayout_send_us)
    public RelativeLayout mRelayoutSendUs;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {
        mColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColor(SettingActivity.this, mColor, 0);
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.mipmap.ab_back);
    }

    @Override
    public void attachView() {
        Slidr.attach(this);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mVersion.setText(AppConstant.getVersion(this));
        mCacheSize.setText(CacheUtils.getCacheSize(this));
    }

    @Override
    public void configViews() {
        mClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.get(MyApp.getsInstance()).clearMemory();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MyApp.getsInstance()).clearDiskCache();
                    }
                }).start();
                CacheUtils.cleanApplicationCache(SettingActivity.this);
                mCacheSize.setText(CacheUtils.getCacheSize(SettingActivity.this));
            }
        });
        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mAboutMe=new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(mAboutMe);
            }
        });

        mRelayoutSendUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mTextActivity=new Intent(SettingActivity.this,TextActivity.class);
                startActivity(mTextActivity);
            }
        });
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
}
