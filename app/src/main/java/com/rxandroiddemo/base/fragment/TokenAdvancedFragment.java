package com.rxandroiddemo.base.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rxandroiddemo.R;
import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.base.model.ElementModel;
import com.rxandroiddemo.base.presenter.ElementPresenter;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 18:24
 * @Description
 */

public class TokenAdvancedFragment extends  BaseFragment<ElementPresenter,ElementModel> implements ElementMainContract.View {

    @Bind(R.id.searchRb1)
    AppCompatRadioButton searchRb1;
    @Bind(R.id.searchRb2)
    AppCompatRadioButton searchRb2;
    @Bind(R.id.searchRb3)
    AppCompatRadioButton searchRb3;
    @Bind(R.id.searchRb4)
    AppCompatRadioButton searchRb4;
    @Bind(R.id.tipBt)
    Button tipBt;
    @Bind(R.id.gridRv)
    RecyclerView gridRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_elementary;
    }


    @Override
    protected int getTitleRes() {
        return R.string.title_token_advanced;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
