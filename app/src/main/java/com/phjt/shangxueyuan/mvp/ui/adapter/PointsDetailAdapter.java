package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TaskListBean;


/**
 * @author: Roy
 * date:   2020/10/21
 * company: 普华集团
 * description:
 */
public class PointsDetailAdapter extends BaseQuickAdapter<TaskListBean, BaseViewHolder> {

    private Context mContext;

    public PointsDetailAdapter(Context context) {
        super(R.layout.item_points_detail);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean item) {

        helper.setText(R.id.tv_points_detail_title, item.getIntegralName() + "（" + item.getFinishNum() + "/" + item.getIntegralFrequency() + ")")
                .setText(R.id.tv_points_detail_content, "每次可获" + item.getIntegralNum() + "积分");
        TextView tvPoints = helper.getView(R.id.tv_points);
        int integralType = item.getIntegralType();

        if (integralType == 1 || integralType == 2 || integralType == 3 || integralType == 4) {
            tvPoints.setBackgroundResource(R.drawable.bg_to_finish);
            tvPoints.setText("去完成");
            if (item.getFinishNum() == item.getIntegralFrequency()) {
                tvPoints.setBackgroundResource(R.drawable.bg_off_stocks);
                tvPoints.setText("已完成");
            }else {
                helper.addOnClickListener(R.id.tv_points);
            }
        } else if (integralType == 5 || integralType == 6 || integralType == 7) {
            if (item.getFinishNum() == item.getIntegralFrequency()) {
                tvPoints.setBackgroundResource(R.drawable.bg_off_stocks);
                tvPoints.setText("已完成");
            }else {
                tvPoints.setBackgroundResource(R.drawable.bg_not_available);
                tvPoints.setText("未获得");
            }
        }

    }
}
