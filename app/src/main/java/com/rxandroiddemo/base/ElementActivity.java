package com.rxandroiddemo.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.ZhuangbiListAdapter;
import com.rxandroiddemo.base.contract.ElementContract;
import com.rxandroiddemo.base.presenter.ElementSearchPresenter;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.utils.Constant;

import java.util.List;

import butterknife.Bind;

public class ElementActivity extends BaseActivity implements ElementContract.View {

    private static String TAG=ElementActivity.class.getSimpleName();

    @Bind(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @Bind(R.id.searchRb1)
    AppCompatRadioButton mSearchRb1;
    @Bind(R.id.searchRb2)
    AppCompatRadioButton mSearchRb2;
    @Bind(R.id.searchRb3)
    AppCompatRadioButton mSearchRb3;
    @Bind(R.id.searchRb4)
    AppCompatRadioButton mSearchRb4;
    @Bind(R.id.tipBt)
    Button mTipBt;
    @Bind(R.id.gridRv)
    RecyclerView mGridRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ElementSearchPresenter mSearchPresenter;

    public ZhuangbiListAdapter mAdapter = new ZhuangbiListAdapter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_element;
    }

    @Override
    public void initToolBar() {
      mCommonToolbar.setTitle("测试");
    }

    //进行Presenter的实例化
    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mSearchPresenter = new ElementSearchPresenter(MyApp.getsInstance());
        mSearchPresenter.attachView(this);

        search(mSearchRb1.getText().toString());
        mSwipeRefreshLayout.setColorSchemeColors(Constant.colors);
        mGridRv.setLayoutManager(new GridLayoutManager(this, 3));
        mGridRv.setHasFixedSize(true);
        mSwipeRefreshLayout.setEnabled(false);
        mGridRv.setAdapter(mAdapter);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String key="";
                if (i == R.id.searchRb1) {
                    key = mSearchRb1.getText().toString();
                } else if (i == R.id.searchRb2) {
                    key = mSearchRb2.getText().toString();
                } else if (i == R.id.searchRb3) {
                    key = mSearchRb3.getText().toString();
                } else if (i == R.id.searchRb4) {
                    key = mSearchRb4.getText().toString();
                }
                search(key);
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    public void search(String key) {
        mSearchPresenter.lodeMineChannelsRequest(key);
    }

    @Override
    public void editUi(List<ZhuangbiImage> zhuangbiImages) {
        Log.i(TAG, "数据长度" + zhuangbiImages.size());
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setImages(zhuangbiImages);
    }

    @Override
    public void showError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.detachView();
    }
}
