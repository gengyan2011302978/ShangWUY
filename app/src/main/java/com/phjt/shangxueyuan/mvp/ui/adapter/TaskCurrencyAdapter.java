package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mzmedia.utils.String_Utils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.TaskCurrencySecondBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/6/28 17:56
 * company: 普华集团
 * description: 学豆任务
 */
public class TaskCurrencyAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Context mContext;

    public static final int TYPE_TASK_FIRST = 10;
    public static final int TYPE_TASK_SECOND = 11;

    public TaskCurrencyAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_TASK_FIRST, R.layout.item_task_currency_first);
        addItemType(TYPE_TASK_SECOND, R.layout.item_points_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_TASK_FIRST:
                if (helper.getAdapterPosition() == 0) {
                    helper.setVisible(R.id.iv_line, false);
                }
                TaskCurrencyFirstBean firstBean = (TaskCurrencyFirstBean) item;
                helper.setText(R.id.tv_task_currency_name_item, firstBean.getTypeName());
                helper.setVisible(R.id.tv_task_currency_name, false);
                if (firstBean.getTypeName().contains("分享行为") || firstBean.getTypeName().contains("支付行为")) {
                    helper.setVisible(R.id.tv_task_currency_name, false);
                }
                break;
            case TYPE_TASK_SECOND:
                TaskCurrencySecondBean secondBean = (TaskCurrencySecondBean) item;

                String detailTitle;
                if (secondBean.getType() == 3) {
                    detailTitle = secondBean.getIntegralName();
                } else {
                    detailTitle = secondBean.getIntegralName() + "（" + String_Utils.linearNmber(secondBean.getFinishNum()) + "/" + String_Utils.linearNmber(secondBean.getIntegralFrequency()) + ")";
                }
                helper.setText(R.id.tv_points_detail_title, detailTitle)
                        .setText(R.id.tv_points_detail_content, String_Utils.linearNmber(secondBean.getIntegralNum()))
                        .setText(R.id.tv_points_detail, "每次可获得价值 ")
                        .addOnClickListener(R.id.tv_points);
                Integer integralType = secondBean.getIntegralType();
                if (integralType == 48 ||integralType == 63 || integralType == 64 || integralType == 65) {
                    helper.setText(R.id.tv_points_detail_title, secondBean.getIntegralName())
                            .setText(R.id.tv_points_detail, "每支付1学豆，可获得价值 ");
                }

                TextView tvPoints = helper.getView(R.id.tv_points);
                //0去完成1已完成2未获得3已获得
                Integer finishFlag = secondBean.getFinishFlag();
                if (finishFlag == 0) {
                    tvPoints.setBackgroundResource(R.drawable.bg_to_finish);
                    tvPoints.setText(secondBean.getFinishDesc());
                } else if (finishFlag == 1) {
                    tvPoints.setBackgroundResource(R.drawable.bg_off_stocks);
                    tvPoints.setText(secondBean.getFinishDesc());
                } else if (finishFlag == 2) {
                    tvPoints.setBackgroundResource(R.drawable.bg_not_available);
                    tvPoints.setText(secondBean.getFinishDesc());
                } else if (finishFlag == 3) {
                    tvPoints.setBackgroundResource(R.drawable.bg_off_stocks);
                    tvPoints.setText(secondBean.getFinishDesc());
                }

                break;
            default:
                break;
        }
    }
}
