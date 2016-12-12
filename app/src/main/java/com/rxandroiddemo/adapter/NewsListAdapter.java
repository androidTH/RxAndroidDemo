package com.rxandroiddemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rxandroiddemo.MyApp;
import com.rxandroiddemo.R;
import com.rxandroiddemo.adapter.support.BaseViewHolder;
import com.rxandroiddemo.adapter.support.RecyclerArrayAdapter;
import com.rxandroiddemo.bean.NewsSummary;
import com.rxandroiddemo.utils.DisplayUtil;
import com.rxandroiddemo.utils.ToastUitl;

import java.util.List;

/**
 * @auther jjr
 * @date 创建时间： 2016/12/9 14:42
 * @Description
 */

public class NewsListAdapter extends RecyclerArrayAdapter<NewsSummary> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_PHOTO_ITEM =1;

    public NewsListAdapter(Context context,List<NewsSummary> newsSummaryList) {
        super(context,newsSummaryList);
    }

    @Override
    public int getViewType(int position) {
       if(!TextUtils.isEmpty(getAllData().get(position).getDigest())){
           return TYPE_ITEM;
       }else{
           return TYPE_PHOTO_ITEM;
       }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_PHOTO_ITEM){
            return new BaseViewHolder<NewsSummary>(parent,R.layout.item_news_photo) {
                @Override
                public void setData(NewsSummary item) {
                    holder.setText(R.id.news_summary_title_tv,item.getTitle());
                    holder.setText(R.id.news_summary_ptime_tv,item.getPtime());
                    setImageView(holder,item);
                    holder.setOnClickListener(R.id.ll_root, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUitl.show("点击了", Toast.LENGTH_LONG);
                        }
                    });
                }
            };
        }
        return new BaseViewHolder<NewsSummary>(parent, R.layout.item_news) {
                @Override
                public void setData(NewsSummary item) {
                    holder.setText(R.id.news_summary_title_tv, item.getTitle());
                    holder.setText(R.id.news_summary_digest_tv, item.getDigest());
                    holder.setText(R.id.news_summary_ptime_tv, item.getPtime());
                    holder.setImageUrl(R.id.news_summary_photo_iv, item.getImgsrc());
                }
            };
    }

    private void setImageView(BaseViewHolder holder, NewsSummary newsSummary) {
        int PhotoThreeHeight = (int) DisplayUtil.dip2px(90);
        int PhotoTwoHeight = (int) DisplayUtil.dip2px(120);
        int PhotoOneHeight = (int) DisplayUtil.dip2px(150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;
        LinearLayout news_summary_photo_iv_group= (LinearLayout) holder.getView(R.id.news_summary_photo_iv_group);
        ViewGroup.LayoutParams layoutParams = news_summary_photo_iv_group.getLayoutParams();

        if (newsSummary.getAds() != null) {
            List<NewsSummary.AdsBean> adsBeanList = newsSummary.getAds();
            int size = adsBeanList.size();
            if (size >= 3) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();
                imgSrcRight = adsBeanList.get(2).getImgsrc();
                layoutParams.height = PhotoThreeHeight;
                holder.setText(R.id.news_summary_title_tv, MyApp.getsInstance().getResources()
                        .getString(R.string.photo_collections, adsBeanList.get(0).getTitle()));
            } else if (size >= 2) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();
                imgSrcMiddle = adsBeanList.get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = adsBeanList.get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else if (newsSummary.getImgextra() != null) {
            int size = newsSummary.getImgextra().size();
            if (size >= 3) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();
                imgSrcRight = newsSummary.getImgextra().get(2).getImgsrc();

                layoutParams.height = PhotoThreeHeight;
            } else if (size >= 2) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();

                layoutParams.height = PhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();

                layoutParams.height = PhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsSummary.getImgsrc();

            layoutParams.height = PhotoOneHeight;
        }

        setPhotoImageView(holder, imgSrcLeft, imgSrcMiddle, imgSrcRight);
        news_summary_photo_iv_group.setLayoutParams(layoutParams);
    }


    private void setPhotoImageView(BaseViewHolder holder, String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            holder.setVisible(R.id.news_summary_photo_iv_left,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_left,imgSrcLeft);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_left,false);
        }
        if (imgSrcMiddle != null) {
            holder.setVisible(R.id.news_summary_photo_iv_middle,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_middle,imgSrcMiddle);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_middle,false);
        }
        if (imgSrcRight != null) {
            holder.setVisible(R.id.news_summary_photo_iv_right,true);
            holder.setImageUrl(R.id.news_summary_photo_iv_right,imgSrcRight);
        } else {
            holder.setVisible(R.id.news_summary_photo_iv_right,false);
        }
    }
}
