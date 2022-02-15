package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mzmedia.utils.String_Utils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.IncomeRecordBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/8/4 11:10
 * company: 普华集团
 * description: 收入记录
 */
public class IncomeRecordAdapter extends BaseQuickAdapter<IncomeRecordBean, BaseViewHolder> {

    public Context mContext;

    public IncomeRecordAdapter(Context context, @Nullable List<IncomeRecordBean> data) {
        super(R.layout.item_wallet_income, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeRecordBean item) {
        helper.setText(R.id.tv_income_amount_item,  String_Utils.linearNmber(item.getIntegralDetail())+"BOCC")
                .setText(R.id.tv_income_date_item, item.getCreateTime())
                .setText(R.id.tv_income_phone_item, "来源：" + item.getMobile())
                .setText(R.id.tv_income_vip_item, item.getIntegralName());
    }

}
