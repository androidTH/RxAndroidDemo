package com.rxandroiddemo.base;

/**
 * @author yuyh.
 * @date 16/8/6.
 */
public interface BaseContract {

    interface BaseView {

        void showError();

        void complete();

    }

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }
}
