package com.rxandroiddemo.base.contract;

import com.rxandroiddemo.base.BaseContract;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.bean.ZhuangbiImage;

import java.util.List;

import rx.Observable;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/9 16:58
 * @Description
 */

public class NewsContract {

    public interface View extends BaseContract.BaseView{
        void getNewsList(List<NewsSummary> newsSummaryList);
    }

    public interface NewsPresenter extends BaseContract.BasePresenter<View>{
        public abstract void loadNewsList(final String type, final String id, final int startPage);
    }
}
