package com.rxandroiddemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/22 10:26
 * @Description
 */

public class HoverScrollView extends ScrollView {

    private OnScrollListener mListener;
    private int downY;
    private int offsetY;

    public interface OnScrollListener {
        void onScroll(int scrollY);

        void onScrollToTop();

        void onScrollToBottom();
    }

    public HoverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HoverScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                offsetY = moveY - downY;
                downY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                if (offsetY > 0) {
                    if (mListener != null) {
                        mListener.onScrollToTop();
                    }
                } else {
                    if (mListener != null) {
                        mListener.onScrollToBottom();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScroll(t);
        }
    }

    public OnScrollListener getmListener() {
        return mListener;
    }

    public void setmListener(OnScrollListener mListener) {
        this.mListener = mListener;
    }
}
