package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.utils.TimeUtils;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class CourseStudyTypeAdapter extends BaseQuickAdapter<CourseTypeBean, BaseViewHolder> {

    public CourseStudyTypeAdapter(@Nullable List<CourseTypeBean> data) {
        super(R.layout.item_study_course_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseTypeBean item) {

        if (item.getState() == 1) {
            if (item.getSumTimeLong() > 0) {
                String progress = TimeUtils.getStudyLook(item.getCouTypeWatchDuration(),item.getSumTimeLong());
                helper.setText(R.id.tv_type_status, progress + "%已学");
            } else {
                helper.setText(R.id.tv_type_status, "0%已学");
            }

        }
        CardView cardView = helper.getView(R.id.cv_root);
        if (helper.getAdapterPosition() == 0) {
            helper.setImageResource(R.id.iv_type_bg, R.drawable.ic_boc_course);
            helper.setText(R.id.tv_type_name, "BOC课程\u3000");
        } else if (helper.getAdapterPosition() == 1) {
            helper.setImageResource(R.id.iv_type_bg, R.drawable.ic_message_bg);
            helper.setText(R.id.tv_type_name, "信息化运\n营官");
        } else if (helper.getAdapterPosition() == 2) {
            helper.setImageResource(R.id.iv_type_bg, R.drawable.ic_business_bg);
            helper.setText(R.id.tv_type_name, "新营销架\n构师");
        }
        if (item.isSelected()) {
            cardView.setCardElevation(20);
        } else {
            cardView.setCardElevation(0);
        }

    }
}
