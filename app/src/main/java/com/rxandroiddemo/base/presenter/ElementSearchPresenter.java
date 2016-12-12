package com.rxandroiddemo.base.presenter;

import android.content.Context;

import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.RxPresenter;
import com.rxandroiddemo.base.contract.ElementContract;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import rx.Observer;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/8 14:07
 * @Description
 */

public class ElementSearchPresenter extends RxPresenter<ElementContract.View> implements ElementContract.ElementPresentr {

    private Context mContext;

    public ElementSearchPresenter(Context context) {
        this.mContext=context;
    }

    @Override
    public void lodeMineChannelsRequest(String key) {
        addSubscrebe(ServiceRest.getInstance().search(key).subscribe(new Observer<List<ZhuangbiImage>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ZhuangbiImage> zhuangbiImages) {
                  mView.editUi(zhuangbiImages);
            }
        }));
    }
}
