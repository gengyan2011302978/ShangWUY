package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.MZLiveUtils;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.view.roundImg.RoundedImageView;
import com.phsxy.utils.LogUtils;

import java.util.List;

public class LiveListSecondAdapter extends BaseQuickAdapter<LiveBean, BaseViewHolder> {

    private Context mContext;

    public LiveListSecondAdapter(Context context, @Nullable List<LiveBean> data) {
        super(R.layout.item_live_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_audition_item);
        AppImageLoader.loadResUrl(item.getCover(), ivIcon);
        TextView tvStatus = helper.getView(R.id.tv_status);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvStudyPeopleItem = helper.getView(R.id.tv_study_people_item);
        TextView tvAuditionTitleItem = helper.getView(R.id.tv_audition_title_item);
        TextView tvRateOfLearning = helper.getView(R.id.tv_rate_of_learning);
        TextView tvLiveTime = helper.getView(R.id.tv_live_time);
        if (item.getReserveState() == 0) {
            tvRateOfLearning.setText("预约");
            tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color_F650C));
        } else {
            tvRateOfLearning.setText("已预约");
            tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color999999));
        }
        tvStatus.setBackgroundResource(R.drawable.bg_ffb30c_rectangle);
        switch (item.getStatus()) {
            case 0:
                tvStatus.setText("未直播");
                break;
            case 1:
            case 3:
                tvStatus.setText("直播中");
                tvStatus.setBackgroundResource(R.drawable.bg_f650c_rectangle);
                tvRateOfLearning.setText("进入");
                tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color_F650C));
                break;
            case 2:
                tvStatus.setText("回放");
                break;
            case 4:
                tvStatus.setText("直播生成回放中");
                break;
            default:
                break;
        }
        tvDate.setText(item.getLiveStartTime());
        tvStudyPeopleItem.setText(item.getPopular());
        tvAuditionTitleItem.setText(item.getName());
        tvRateOfLearning.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if (item.getReserveState() == 0 && item.getStatus() != 1) {
                    CommonHttpManager.getInstance(mContext)
                            .obtainRetrofitService(ApiService.class)
                            .liveReserve(item.getId() + "")
                            .compose(RxUtils.applySchedulers())
                            .subscribe(baseBean -> {
                                if (baseBean.isOk()) {
                                    tvRateOfLearning.setText("已预约");
                                    tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color999999));
                                } else {

                                }
                            }, throwable -> LogUtils.e("网络异常"));
                } else if (item.getStatus() == 1) {
                    MZLiveUtils.toLivePlay(mContext, item.getTicketId(), item.getLiveStyle(), item.getId());
                }
            }
        });
    }
}
