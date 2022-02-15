package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ExchangeCodeAdapter extends BaseQuickAdapter<ExchangeCodeBean.RecordsBean, BaseViewHolder> {

    public ExchangeCodeAdapter(@Nullable List<ExchangeCodeBean.RecordsBean> data) {
        super(R.layout.item_exchangecode_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExchangeCodeBean.RecordsBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView tvStart = helper.getView(R.id.tv_start_date);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvTime = helper.getView(R.id.tv_time);
        tvStart.setText("兑换日期："+item.getCreateTime());
        tvDate.setText("有效期至："+item.getCourseDuration());

        tvName.setText(item.getName());
        if (item.getEffectiveState().equals("1")){
            tvTime.setText("有效");
            tvTime.setBackgroundResource(R.drawable.bg_ff9a65_bg);
        }else {
            tvTime.setText("失效");
            tvTime.setBackgroundResource(R.drawable.bg_c2c2c2_bg);
        }
    }
}
