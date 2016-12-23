package com.rxandroiddemo.base.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.ItemListAdapter;
import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.BaseFragment;
import com.rxandroiddemo.bean.Item;
import com.rxandroiddemo.utils.AppConstant;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 18:24
 * @Description
 */

public class MapFragment extends BaseFragment {

    private int page = 0;

    @Bind(R.id.pageTv)
    TextView mPageTv;
    @Bind(R.id.previousPageBt)
    AppCompatButton mPreviousPageBt;
    @Bind(R.id.nextPageBt)
    AppCompatButton mNextPageBt;
    @Bind(R.id.tipBt)
    Button mTipBt;
    @Bind(R.id.gridRv)
    RecyclerView mGridRv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    ItemListAdapter mAdapter = new ItemListAdapter();

    private CompositeSubscription compositeSubscription=new CompositeSubscription();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_map;
    }

    @Override
    public void attachView() {
       mSwipeRefreshLayout.setColorSchemeColors(AppConstant.colors);
       mGridRv.setLayoutManager(new GridLayoutManager(getActivity(),3));
       mGridRv.setHasFixedSize(true);
       mGridRv.setAdapter(mAdapter);
    }

    @Override
    public void initDatas() {

    }

    @OnClick(R.id.previousPageBt)
    public void onPreviousClick(){
        loadPage(--page);
        if (page == 1) {
            mPreviousPageBt.setEnabled(false);
        }
    }

    @OnClick(R.id.nextPageBt)
    public void onNextClick(){
        loadPage(page++);
        if(page==2){
            mNextPageBt.setEnabled(true);
        }
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

    private void loadPage(int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        compositeSubscription.add(ServiceRest.getGankInstance().getBeauties(10,page).subscribe(new GankObserver()));
    }

    public class GankObserver implements Observer<List<Item>> {
        @Override
        public void onCompleted() {
            mSwipeRefreshLayout.setEnabled(false);
        }

        @Override
        public void onError(Throwable e) {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Item> items) {
            mSwipeRefreshLayout.setRefreshing(false);
            mPageTv.setText(getString(R.string.page_with_number,String.valueOf(page)));
            mAdapter.setItems(items);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(compositeSubscription.hasSubscriptions()){
            compositeSubscription.clear();
        }
    }
}
