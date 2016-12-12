package com.rxandroiddemo.base.presenter;

import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.RxPresenter;
import com.rxandroiddemo.base.contract.ElementContract;
import com.rxandroiddemo.base.contract.NewsContract;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.utils.Constant;
import com.rxandroiddemo.utils.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.Subject;

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
