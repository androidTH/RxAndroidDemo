package com.rxandroiddemo.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rxandroiddemo.R;
import com.rxandroiddemo.ui.statusutils.AndroidBug5497Workaround;
import com.rxandroiddemo.ui.statusutils.StatusBarUtil;

public class AndroidBugActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_android_bug;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        StatusBarUtil.setColor(AndroidBugActivity.this,mColor, 0);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas(Bundle savedInstanceState) {

    }

    @Override
    public void configViews() {

    }

    public void dynamicAdjustmentHeight(View view){
        AndroidBug5497Workaround.assistActivity(this);
    }
}
