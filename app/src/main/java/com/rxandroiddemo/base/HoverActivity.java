package com.rxandroiddemo.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rxandroiddemo.R;
import com.rxandroiddemo.ui.HoverScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HoverActivity extends BaseActivity implements HoverScrollView.OnScrollListener {


    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.tv_like_num)
    TextView tvLikeNum;
    @Bind(R.id.user_detail)
    RelativeLayout mUserDetail;
    @Bind(R.id.top_user_detail)
    RelativeLayout mTopUserDetail;
    @Bind(R.id.hoverscrollview)
    HoverScrollView hoverscrollview;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.activity_hover)
    RelativeLayout activityHover;

    private Animation mBottoDismAnim;
    private Animation mBottoShowAnim;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hover;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void attachView() {
        findViewById(R.id.activity_hover).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onScroll(hoverscrollview.getScrollY());
            }
        });
        hoverscrollview.setmListener(this);
    }

    @Override
    public void initDatas(Bundle savedInstanceState) {
          webview.loadUrl("http://finance.ifeng.com/a/20161115/15007869_0.shtml");
          webview.setWebViewClient(new WebViewClient() {
              @Override
              public boolean shouldOverrideUrlLoading(WebView view, String url) {
                  view.loadUrl(url);
                  return true;
              }
          });
          mBottoDismAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_dimission);
          mBottoShowAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_show);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onScroll(int scrollY) {
        //在最开始mUserDetail距离屏幕顶部是有一段距离的，而最开始scrollY=0，
        //所以在最开始的时候我们取两者的最大值就可以使两个View重合起来
        //因为我们是在所有的View都测量完毕后调用过onScroll方法的，
        //所以mUserDetail.getTop()得到的值是正确的值
        int userDetailView2Top = Math.max(scrollY,mUserDetail.getTop());
        //调用mTopUserDetail的layout方法，设置其在屏幕上的位置
        Log.i("onScroll",mTopUserDetail.getTop()+"滚动距离"+scrollY+"view2Top"+userDetailView2Top);
        mTopUserDetail.layout(0, userDetailView2Top, mTopUserDetail.getWidth(), userDetailView2Top + mTopUserDetail.getHeight());
    }

    @Override
    public void onScrollToTop() {
        if (!llBottom.isShown()) {
            llBottom.clearAnimation();
            llBottom.startAnimation(mBottoShowAnim);
            llBottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onScrollToBottom() {
        if (llBottom.isShown()) {
            llBottom.clearAnimation();
            llBottom.startAnimation(mBottoDismAnim);
            llBottom.setVisibility(View.GONE);
        }
    }
}
