package com.rxandroiddemo.base.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.ItemListAdapter;
import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.base.model.ElementModel;
import com.rxandroiddemo.base.presenter.ElementPresenter;
import com.rxandroiddemo.bean.Item;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 18:24
 * @Description
 */

public class ZipFragment extends  BaseFragment<ElementPresenter,ElementModel> implements ElementMainContract.View {

    @Bind(R.id.zipLoadBt)
    Button mZipLoadBt;
    @Bind(R.id.tipBt)
    Button mTipBt;
    @Bind(R.id.gridRv)
    RecyclerView mGridRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private CompositeSubscription compositeSubscription=new CompositeSubscription();

    ItemListAdapter mAdapter = new ItemListAdapter();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_zip;
    }

    @Override
    protected int getTitleRes() {
        return R.string.title_zip;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void attachView() {
        mGridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mGridRv.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeColors(Constant.colors);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.zipLoadBt)
    public void onZipLoad(){
        compositeSubscription.add(Observable.zip(ServiceRest.getGankInstance().getBeauties(200, 1), ServiceRest.getInstance().search("可爱"), new
                Func2<List<Item>, List<ZhuangbiImage>, List<Item>>() {
                    @Override
                    public List<Item> call(List<Item> items, List<ZhuangbiImage> zhuangbiImages) {
                        for (int i = 0; i < items.size() / 2 && i < zhuangbiImages.size(); i++) {
                            items.add(items.get(i * 2));
                            items.add(items.get(i * 2 + 1));
                            Item zhuangbiItem = new Item();
                            ZhuangbiImage zhuangbiImage = zhuangbiImages.get(i);
                            zhuangbiItem.description = zhuangbiImage.description;
                            zhuangbiItem.imageUrl = zhuangbiImage.image_url;
                            items.add(zhuangbiItem);
                        }
                        return items;
                    }
                }).subscribe(new ZipObserver()));
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
        if(compositeSubscription.hasSubscriptions()){
            compositeSubscription.clear();
        }
    }

    public class ZipObserver implements Observer<List<Item>> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Item> items) {
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.setItems(items);
        }
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
