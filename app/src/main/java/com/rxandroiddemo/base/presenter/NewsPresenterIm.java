package com.rxandroiddemo.base.presenter;

import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.RxPresenter;
import com.rxandroiddemo.base.contract.NewsContract;
import com.rxandroiddemo.bean.NewsSummary;

import java.util.List;

import rx.Observer;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/9 17:12
 * @Description
 */

public class NewsPresenterIm extends RxPresenter<NewsContract.View> implements NewsContract.NewsPresenter {

    @Override
    public void loadNewsList(String type,final String id, int startPage) {
        addSubscrebe(ServiceRest.getNewsInstance().getNewsListData(type, id, startPage).subscribe(new Observer<List<NewsSummary>>() {
            @Override
            public void onCompleted() {
                 mView.complete();
            }

            @Override
            public void onError(Throwable e) {
                 mView.showError();
            }

            @Override
            public void onNext(List<NewsSummary> newsSummaryList) {
                 mView.getNewsList(newsSummaryList);
            }
        }));
    }
}
