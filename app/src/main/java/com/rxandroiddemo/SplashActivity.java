package com.rxandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {


    @Bind(R.id.fragment)
    FrameLayout mFragment;

    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.FullScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mRunnable=new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        };

        mFragment.postDelayed(mRunnable,2000);
    }

    private synchronized void goHome() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
