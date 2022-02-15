package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.utils.NumberUtil;

/**
 * @author: Roy
 * date:   2020/11/24
 * company: 普华集团
 * description:兑换礼券Adapter
 */
public class ExchangeVoucherAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {

    private Context mContext;
    private int mType;

    public ExchangeVoucherAdapter(Context context) {
        super(R.layout.item_coupon);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setVisible(R.id.iv_coupon_state_item, false)
                .setText(R.id.tv_coupon_title_item, item.getCouponName())
                .setText(R.id.tv_validity_item, String.format("有效期至%s", item.getCouponFailureTime()));

        TextView tvInstructions = helper.getView(R.id.tv_instructions_item);
        View viewGray = helper.getView(R.id.view_gray);
        tvInstructions.setTextColor(ContextCompat.getColor(mContext,
                TextUtils.isEmpty(item.getUseExplain()) ? R.color.color_4DB57D45 : R.color.color_B57D45));
        Drawable mArrowRight = ContextCompat.getDrawable(mContext, R.drawable.coupon_arrow_right);
        Drawable mArrowRightun = ContextCompat.getDrawable(mContext, R.drawable.coupon_arrow_right_un);

        tvInstructions.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, TextUtils.isEmpty(item.getUseExplain()) ? mArrowRightun : mArrowRight, null);
        // 0:无使用门槛 ; 1:满N元使用
        if (0 == item.getUseControl()) {
            helper.setText(R.id.tv_coupon_rule_item, "无使用门槛");
        } else {
            helper.setText(R.id.tv_coupon_rule_item, String.format(mContext.getResources().getString(R.string.tips), item.getUseControlPrice()));
        }

        TextView tvCouponDiscount = helper.getView(R.id.tv_coupon_discount_item);
        //0.满减优惠 1.折扣优惠
        if (0 == item.getCouponContent()) {
            helper.setVisible(R.id.tv_rmb_item, true);
            helper.setVisible(R.id.tv_discount_item, false);
            tvCouponDiscount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f);
            tvCouponDiscount.setText(NumberUtil.getStrByDoubleToInt(item.getCouponContentPrice()));
        } else {
            helper.setVisible(R.id.tv_rmb_item, false);
            helper.setVisible(R.id.tv_discount_item, true);
            tvCouponDiscount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f);
            tvCouponDiscount.setText(String.valueOf(item.getCouponContentPrice() * 10));
        }

        if (mType == 1) {
            helper.setVisible(R.id.iv_to_use_item, false);
            helper.setVisible(R.id.iv_lose_efficacy, true);
            viewGray.setVisibility(View.VISIBLE);
        } else {
            helper.setVisible(R.id.iv_to_use_item, true);
            helper.addOnClickListener(R.id.iv_to_use_item);
        }
        if (!TextUtils.isEmpty(item.getUseExplain())) {
            helper.addOnClickListener(R.id.tv_instructions_item);
        }

    }

    public void setType(int type) {
        mType = type;
    }
}
