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
import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.base.contract.NewsContract;
import com.rxandroiddemo.base.model.ElementModel;
import com.rxandroiddemo.base.presenter.ElementPresenter;
import com.rxandroiddemo.base.presenter.NewsPresenterIm;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.ui.EasyRecyclerView;

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

public class TokenFragment extends BaseFragment<ElementPresenter,ElementModel> implements NewsContract.View,ElementMainContract.View{


    //http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html
    private static String TAG=TokenFragment.class.getSimpleName();

    @Bind(R.id.recyclerview)
    EasyRecyclerView mRecyclerview;

    private List<NewsSummary> mNewsList=new ArrayList<>();
    private NewsListAdapter mNewsListAdapter;
    private NewsPresenterIm newsPresenterIm;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_token;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_token;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void attachView() {
        newsPresenterIm = new NewsPresenterIm();
        newsPresenterIm.attachView(this);
        newsPresenterIm.loadNewsList("headline", "T1348647909107", 0);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initDatas() {
//        mRecyclerview.setAdapter(mNewsListAdapter);
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
        ButterKnife.unbind(this);
    }

    @Override
    public void getNewsList(List<NewsSummary> newsSummaryList) {
        Log.i(TAG,"长度"+newsSummaryList.size());
        mNewsList.addAll(newsSummaryList);
        if(mNewsListAdapter==null){
          mNewsListAdapter=new NewsListAdapter(getActivity(),mNewsList);
          mRecyclerview.setAdapter(mNewsListAdapter);
        }else{
          mNewsListAdapter.addAll(mNewsList);
        }

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void editUi(List<ZhuangbiImage> zhuangbiImages) {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
