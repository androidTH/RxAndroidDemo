package com.rxandroiddemo.base.contract;

import com.rxandroiddemo.base.BaseModel;
import com.rxandroiddemo.base.BasePresenter;
import com.rxandroiddemo.base.BaseView;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import rx.Observable;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/7 17:17
 * @Description
 */

public interface ElementMainContract {

    public interface Model extends BaseModel{
         Observable<List<ZhuangbiImage>> getListImages(String key);
    }

    public interface View extends BaseView{
         void editUi(List<ZhuangbiImage> zhuangbiImages);
    }

    public abstract  class PresenterImpl extends BasePresenter<View,Model> {
        public abstract void lodeMineChannelsRequest(String key);
    }
}
