package com.rxandroiddemo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.rxandroiddemo.base.BaseActivity;
import com.rxandroiddemo.base.fragment.CacheFragment;
import com.rxandroiddemo.base.fragment.ElementaryFragment;
import com.rxandroiddemo.base.fragment.MapFragment;
import com.rxandroiddemo.base.fragment.TokenAdvancedFragment;
import com.rxandroiddemo.base.fragment.TokenFragment;
import com.rxandroiddemo.base.fragment.ZipFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(android.R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {
         mCommonToolbar.setTitle("主页");
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ElementaryFragment();
                    case 1:
                        return new MapFragment();
                    case 2:
                        return new ZipFragment();
                    case 3:
                        return new TokenFragment();
                    case 4:
                        return new TokenAdvancedFragment();
                    case 5:
                        return new CacheFragment();
                    default:
                        return new ElementaryFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_elementary);
                    case 1:
                        return getString(R.string.title_map);
                    case 2:
                        return getString(R.string.title_zip);
                    case 3:
                        return getString(R.string.title_token);
                    case 4:
                        return getString(R.string.title_token_advanced);
                    case 5:
                        return getString(R.string.title_cache);
                    default:
                        return getString(R.string.title_elementary);
                }
            }
        });
        mTabs.setupWithViewPager(mViewPager);
    }
}
