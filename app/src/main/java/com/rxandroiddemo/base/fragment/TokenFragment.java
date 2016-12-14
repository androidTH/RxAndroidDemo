package com.rxandroiddemo.base.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.NewsListAdapter;
import com.rxandroiddemo.adapter.support.OnLoadMoreListener;
import com.rxandroiddemo.base.BaseRVFragment;
import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.base.contract.NewsContract;
import com.rxandroiddemo.base.model.ElementModel;
import com.rxandroiddemo.base.presenter.ElementPresenter;
import com.rxandroiddemo.base.presenter.NewsPresenterIm;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.ui.EasyRecyclerView;
import com.rxandroiddemo.ui.swipe.OnRefreshListener;
import com.rxandroiddemo.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 18:24
 * @Description
 */

public class TokenFragment extends BaseRVFragment implements NewsContract.View, OnLoadMoreListener {


    //http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html
    private static String TAG = TokenFragment.class.getSimpleName();

    @Bind(R.id.recyclerview)
    EasyRecyclerView mRecyclerview;

    private NewsListAdapter mNewsListAdapter;
    private NewsPresenterIm newsPresenterIm;
    protected int start = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_token;
    }

    @Override
    public void attachView() {
        newsPresenterIm = new NewsPresenterIm();
        newsPresenterIm.attachView(this);
        newsPresenterIm.loadNewsList("headline", "T1348647909107", start);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initDatas() {
        mNewsListAdapter = new NewsListAdapter(getActivity());
        mRecyclerview.setAdapterWithProgress(mNewsListAdapter);
        mRecyclerview.setRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                newsPresenterIm.loadNewsList("headline", "T1348647909107", start);
            }
        });
        mNewsListAdapter.setMore(R.layout.common_more_view, this);
        mNewsListAdapter.setNoMore(R.layout.common_nomore_view);
    }

    @Override
    public void configViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getNewsList(List<NewsSummary> newsSummaryList) {
        Log.i(TAG, "长度" + newsSummaryList.size());
        start += 20;
        if (start == 80) {
            mNewsListAdapter.addAll(new ArrayList<NewsSummary>());
        } else {
            mNewsListAdapter.addAll(newsSummaryList);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onLoadMore() {
        if (NetWorkUtils.isConnected(getApplicationContext())) {
            Log.i(TAG, "onLoadMore" + start);
            newsPresenterIm.loadNewsList("headline", "T1348647909107", start);
        }
    }
}
