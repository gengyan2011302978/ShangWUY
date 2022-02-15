package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.utils.NumberUtil;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/8/4 11:10
 * company: 普华集团
 * description: 收入记录
 */
public class WithdrawalRecordAdapter extends BaseQuickAdapter<WithdrawalRecordBean, BaseViewHolder> {

    public Context mContext;

    public WithdrawalRecordAdapter(Context context, @Nullable List<WithdrawalRecordBean> data) {
        super(R.layout.item_wallet_withdrawal, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawalRecordBean item) {
        helper.setText(R.id.tv_withdrawal_amount_item, "￥" + NumberUtil.getStrDouble(item.getWithdrawMoney()))
                .setText(R.id.tv_withdrawal_date_item, item.getApplyTime())
                .setText(R.id.tv_withdrawal_state_item, item.getAuditState() == 1 ? "已提现" : "受理中");
    }
}
