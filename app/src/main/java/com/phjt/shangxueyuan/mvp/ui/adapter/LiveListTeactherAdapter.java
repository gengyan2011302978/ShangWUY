package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TeacherLiveBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

public class LiveListTeactherAdapter extends BaseQuickAdapter<TeacherLiveBean, BaseViewHolder> {

    private Context mContext;

    public LiveListTeactherAdapter(Context context, @Nullable List<TeacherLiveBean> data) {
        super(R.layout.item_live_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherLiveBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_audition_item);
        AppImageLoader.loadResUrl(item.getCover(), ivIcon);
        TextView tvStatus = helper.getView(R.id.tv_status);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvScan = helper.getView(R.id.tv_scan);
        TextView tvStudyPeopleItem = helper.getView(R.id.tv_study_people_item);
        TextView tvAuditionTitleItem = helper.getView(R.id.tv_audition_title_item);
        tvDate.setVisibility(View.GONE);
        tvScan.setVisibility(View.VISIBLE);
        TextView tvRateOfLearning = helper.getView(R.id.tv_rate_of_learning);

        TextView tvLiveTime = helper.getView(R.id.tv_live_time);
        tvLiveTime.setText(item.getTeacherLiveStartTime());
        tvAuditionTitleItem.setText(item.getName());
        tvStudyPeopleItem.setText(item.getPopular());
        helper.addOnClickListener(R.id.tv_rate_of_learning);
        helper.addOnClickListener(R.id.relat_icon);
        helper.addOnClickListener(R.id.tv_audition_title_item);
        helper.addOnClickListener(R.id.tv_live_time);
        helper.addOnClickListener(R.id.iv_people);
        helper.addOnClickListener(R.id.tv_live_commpany);
        helper.addOnClickListener(R.id.tv_study_people_item);
        if (item.getLiveStyle() == 0) {
            tvScan.setText("横屏直播");
        } else {
            tvScan.setText("竖屏直播");
        }
        tvRateOfLearning.setText("立即开播");
        tvRateOfLearning.setBackgroundResource(R.drawable.bg_f650c_rectangle);
        tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvStatus.setBackgroundResource(R.drawable.bg_ffb30c_rectangle);
        switch (item.getStatus()) {
            case 0:
                tvStatus.setText("未直播");
                break;
            case 1:
                tvStatus.setText("直播中");
                tvRateOfLearning.setText("继续直播");
                tvRateOfLearning.setBackgroundResource(R.drawable.bg_f7f7f7_rectangles);
                tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF9A65));
                break;
            case 2:
                tvStatus.setText("回放");
                break;
            case 3:
                tvStatus.setText("直播中");
                tvRateOfLearning.setText("继续直播");
                tvRateOfLearning.setBackgroundResource(R.drawable.bg_f7f7f7_rectangles);
                tvRateOfLearning.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF9A65));
                break;
            case 4:
                tvStatus.setText("直播生成回放中");
                break;
            default:
                break;
        }
    }
}
