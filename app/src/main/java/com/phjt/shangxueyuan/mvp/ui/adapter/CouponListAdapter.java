package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.utils.NumberUtil;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/11/24 15:08
 * company: 普华集团
 * description: 描述
 */
public class CouponListAdapter extends BaseQuickAdapter<CouponBean, BaseViewHolder> {

    private Context mContext;

    public CouponListAdapter(Context context, @Nullable List<CouponBean> data) {
        super(R.layout.item_coupon, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setText(R.id.tv_coupon_title_item, item.getCouponName())
                .setText(R.id.tv_validity_item, "有效期至" + item.getCouponFailureTime())
                .setVisible(R.id.iv_to_use_item, false)
                .setVisible(R.id.iv_coupon_state_item, true)
                .addOnClickListener(R.id.iv_coupon_state_item)
                .addOnClickListener(R.id.tv_instructions_item);

        TextView tvInstructions = helper.getView(R.id.tv_instructions_item);
        tvInstructions.setTextColor(ContextCompat.getColor(mContext,
                TextUtils.isEmpty(item.getUseExplain()) ? R.color.color_4DB57D45 : R.color.color_B57D45));
        ImageView ivCouponState = helper.getView(R.id.iv_coupon_state_item);
        ivCouponState.setSelected(item.isChose());

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

    }
}
