package com.rxandroiddemo.base.contract;

import com.rxandroiddemo.base.BaseContract;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/8 14:03
 * @Description
 */

public class ElementContract {

    public interface View extends BaseContract.BaseView{
        void editUi(List<ZhuangbiImage> zhuangbiImages);
    }

    public interface ElementPresentr extends BaseContract.BasePresenter<View>{
        public abstract void lodeMineChannelsRequest(String key);
    }
}
