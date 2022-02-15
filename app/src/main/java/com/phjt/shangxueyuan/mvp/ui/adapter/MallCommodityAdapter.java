package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mzmedia.utils.String_Utils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MallCommodityBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/6/22 16:45
 * company: 普华集团
 * description: 描述
 */
public class MallCommodityAdapter extends BaseQuickAdapter<MallCommodityBean, BaseViewHolder> {

    private Context mContext;

    /**
     * 是否是 兑换记录
     */
    private boolean isExchangeRecord;

    public void setExchangeRecord(boolean exchangeRecord) {
        isExchangeRecord = exchangeRecord;
    }

    public MallCommodityAdapter(Context context, List<MallCommodityBean> data) {
        super(R.layout.item_mall_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MallCommodityBean item) {
        RoundedImageView ivCommodityItem = helper.getView(R.id.iv_commodity_item);
        AppImageLoader.loadResUrl(item.getCommodityUrl(), ivCommodityItem);

        helper.setText(R.id.tv_commodity_title, item.getCommodityName())
                .setText(R.id.tv_commodity_des, item.getCommodityDesc())
                .setText(R.id.tv_commodity_price, String_Utils.linearNmber(String.valueOf(item.getStudyCoin())) + "学豆")
                .setText(R.id.tv_exchange_count, isExchangeRecord ? "数量：" + item.getExchangeNum() : item.getExchangeNum() + "人已兑换");
    }
}
