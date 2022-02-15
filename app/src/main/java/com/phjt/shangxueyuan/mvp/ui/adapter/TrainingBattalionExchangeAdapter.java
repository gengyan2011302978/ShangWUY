package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingBattalionExchangeBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: Roy
 * date:   2021/4/9
 * company: 普华集团
 * description:兑换码
 */
public class TrainingBattalionExchangeAdapter extends BaseQuickAdapter<TrainingBattalionExchangeBean, BaseViewHolder> {

    public TrainingBattalionExchangeAdapter() {
        super(R.layout.item_exchangecode_adapter);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainingBattalionExchangeBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView tvStart = helper.getView(R.id.tv_start_date);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvTime = helper.getView(R.id.tv_time);
        tvStart.setText("兑换日期：" + item.getCreateTime());
        tvDate.setText("有效期至：" + item.getCourseDuration());

        tvName.setText(item.getName());
        if (item.getEffectiveState() == 1) {
            tvTime.setText("有效");
            tvTime.setBackgroundResource(R.drawable.bg_ff9a65_bg);
        } else {
            tvTime.setText("失效");
            tvTime.setBackgroundResource(R.drawable.bg_c2c2c2_bg);
        }
    }
}
