package com.rxandroiddemo.base.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.ZhuangbiListAdapter;
import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.BaseFragment;
import com.rxandroiddemo.bean.ZhuangbiImage;
import com.rxandroiddemo.utils.AppConstant;

import java.util.List;

import butterknife.Bind;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/5 18:24
 * @Description
 */

public class ElementaryFragment extends BaseFragment {

    private static final String TAG = ElementaryFragment.class.getSimpleName();

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

    public ZhuangbiListAdapter mAdapter = new ZhuangbiListAdapter();

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_elementary;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        search(mSearchRb1.getText().toString());
        mSwipeRefreshLayout.setColorSchemeColors(AppConstant.colors);
        mGridRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
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
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }

    public void search(String key) {
        compositeSubscription.add(ServiceRest.getInstance().search(key).subscribe(new SearchSub()));
    }

    public class SearchSub implements Observer<List<ZhuangbiImage>> {

        @Override
        public void onCompleted() {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<ZhuangbiImage> zhuangbiImages) {
            //更新UI
            Log.i(TAG, "数据长度" + zhuangbiImages.size());
            mAdapter.setImages(zhuangbiImages);
        }
    }
}
