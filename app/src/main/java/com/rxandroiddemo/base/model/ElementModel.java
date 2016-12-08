package com.rxandroiddemo.base.model;

import com.rxandroiddemo.api.ServiceRest;
import com.rxandroiddemo.base.contract.ElementMainContract;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import rx.Observable;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/7 17:22
 * @Description
 */

public class ElementModel implements ElementMainContract.Model {

    @Override
    public Observable<List<ZhuangbiImage>> getListImages(String key) {
        return ServiceRest.getInstance().search(key);
    }
}
