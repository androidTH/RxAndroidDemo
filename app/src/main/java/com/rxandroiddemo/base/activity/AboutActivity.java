package com.rxandroiddemo.base.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.r0adkll.slidr.Slidr;
import com.rxandroiddemo.R;
import com.rxandroiddemo.base.BaseActivity;

public class AboutActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }


    @Override
    public void initToolBar() {
        super.initToolBar();
        Slidr.attach(this);
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
}
