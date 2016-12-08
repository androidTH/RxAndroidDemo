package com.rxandroiddemo.base.presenter;

import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import rx.Observer;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/7 17:26
 * @Description
 */

public class ElementPresenter extends ElementMainContract.PresenterImpl{

    @Override
    public void lodeMineChannelsRequest(String key) {
        mView.showLoading("正在加载");
        mRxManage.add(mModel.getListImages(key).subscribe(new Observer<List<ZhuangbiImage>>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorTip(e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<ZhuangbiImage> zhuangbiImages) {
                mView.editUi(zhuangbiImages);
            }
        }));
    }
}
