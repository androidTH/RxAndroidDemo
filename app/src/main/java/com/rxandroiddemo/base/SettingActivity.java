package com.rxandroiddemo.base;


import android.os.Bundle;
import android.view.View;

import com.r0adkll.slidr.Slidr;
import com.rxandroiddemo.R;
import com.rxandroiddemo.ui.statusutils.StatusBarUtil;

public class SettingActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {
        mColor = getResources().getColor(R.color.colorPrimary);
        StatusBarUtil.setColor(SettingActivity.this, mColor, 0);
        mCommonToolbar.setTitle("设置");
        mCommonToolbar.setNavigationIcon(R.mipmap.ab_back);
    }

    @Override
    public void attachView() {
//        Slidr.attach(this);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void configViews() {

    }
}
