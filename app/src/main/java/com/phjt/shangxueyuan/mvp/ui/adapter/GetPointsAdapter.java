package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mzmedia.utils.String_Utils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.PointsDetailBean;

/**
 * @author: Roy
 * date:   2020/10/21
 * company: 普华集团
 * description:
 */
public class GetPointsAdapter extends BaseQuickAdapter<PointsDetailBean, BaseViewHolder> {

    private Context mContext;
    private int mType;
    private int mTotalCount;

    public GetPointsAdapter(Context context) {
        super(R.layout.item_get_points);
        this.mContext = context;

    }

    public void setType(int type) {
        this.mType = type;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    @Override
    protected void convert(BaseViewHolder helper, PointsDetailBean item) {
        helper.setText(R.id.tv_get_points_title, item.getIntegralName())
                .setText(R.id.tv_get_points_data, item.getCreateTime());
        TextView tvPointsNum = helper.getView(R.id.tv_points_num);
        helper.getView(R.id.view_label).setVisibility(View.VISIBLE);
        if (mType == 1) {
            tvPointsNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFAAD14));
            tvPointsNum.setText("+" + String_Utils.linearNmber(item.getIntegralDetail()));
        } else {
            tvPointsNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF000000));
            tvPointsNum.setText("-" + String_Utils.linearNmber(item.getIntegralDetail()));
        }
        if (helper.getAdapterPosition() ==mTotalCount-1){
            helper.getView(R.id.view_label).setVisibility(View.GONE);
        }

    }
}
