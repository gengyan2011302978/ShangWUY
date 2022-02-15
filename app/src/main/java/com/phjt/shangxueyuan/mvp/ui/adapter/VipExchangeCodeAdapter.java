package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.VipExchangeCodeBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class VipExchangeCodeAdapter extends BaseQuickAdapter<VipExchangeCodeBean.RecordsBean, BaseViewHolder> {

    public VipExchangeCodeAdapter(@Nullable List<VipExchangeCodeBean.RecordsBean> data) {
        super(R.layout.item_vipexchangecode_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipExchangeCodeBean.RecordsBean item) {
        TextView tvCode = helper.getView(R.id.tv_exchangecode);
        TextView tvType = helper.getView(R.id.tv_type);
        tvCode.setText("兑换码："+item.getCode());
        tvType.setText(item.getCommodityName());
    }
}
