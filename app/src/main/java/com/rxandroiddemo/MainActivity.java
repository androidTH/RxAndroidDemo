package com.rxandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.rxandroiddemo.base.AndroidBugActivity;
import com.rxandroiddemo.base.BaseActivity;
import com.rxandroiddemo.base.HoverActivity;
import com.rxandroiddemo.base.SettingActivity;
import com.rxandroiddemo.base.fragment.ElementaryFragment;
import com.rxandroiddemo.base.fragment.MapFragment;
import com.rxandroiddemo.base.fragment.TokenFragment;
import com.rxandroiddemo.base.fragment.ZipFragment;
import com.rxandroiddemo.bean.TabEntity;
import com.rxandroiddemo.ui.statusutils.StatusBarUtil;
import com.rxandroiddemo.utils.AppConstant;
import com.rxandroiddemo.utils.LogUtils;
import com.rxandroiddemo.utils.MultiDexUtils;
import com.rxandroiddemo.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.commontablayout)
    CommonTabLayout mTabLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    private String[] mTitles = {"首页", "美女", "视频", "关注"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ElementaryFragment newsMainFragment;
    private MapFragment photosMainFragment;
    private ZipFragment videoMainFragment;
    private TokenFragment careMainFragment;

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColorForDrawerLayout(this,mDrawerLayout, getResources().getColor(R.color.colorPrimary), 0);
        mCommonToolbar.setTitle("");
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    @Override
    public void attachView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mCommonToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        initFragment(savedInstanceState);
    }

    @Override
    public void configViews() {

    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsMainFragment = (ElementaryFragment) getSupportFragmentManager().findFragmentByTag("newsMainFragment");
            photosMainFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("photosMainFragment");
            videoMainFragment = (ZipFragment) getSupportFragmentManager().findFragmentByTag("videoMainFragment");
            careMainFragment = (TokenFragment) getSupportFragmentManager().findFragmentByTag("careMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            newsMainFragment = new ElementaryFragment();
            photosMainFragment = new MapFragment();
            videoMainFragment = new ZipFragment();
            careMainFragment = new TokenFragment();

            transaction.add(R.id.fl_body, newsMainFragment, "newsMainFragment");
            transaction.add(R.id.fl_body, photosMainFragment, "photosMainFragment");
            transaction.add(R.id.fl_body, videoMainFragment, "videoMainFragment");
            transaction.add(R.id.fl_body, careMainFragment, "careMainFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        mTabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(newsMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(newsMainFragment);
                transaction.hide(videoMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(photosMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(careMainFragment);
                transaction.show(videoMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(newsMainFragment);
                transaction.hide(photosMainFragment);
                transaction.hide(videoMainFragment);
                transaction.show(careMainFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
           mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUitl.show(getString(R.string.exit_tips), Toast.LENGTH_LONG);
                return true;
            } else {
                finish(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_msg:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                Intent mHoverIntent=new Intent(MainActivity.this,HoverActivity.class);
                startActivity(mHoverIntent);
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_set:
                Intent mSetIntent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(mSetIntent);
                break;
            case R.id.nav_about:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            final List externalClasses = new MultiDexUtils().getExternalDexClasses(this);
            if(externalClasses != null) {
                Log.i("MulitDexutils",externalClasses.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
