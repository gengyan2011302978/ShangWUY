package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingBattalionBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;

/**
 * @author: Roy
 * date:   2021/2/1
 * company: 普华集团
 * description:
 */
public class TrainingBattalionAdapter extends BaseQuickAdapter<TrainingBattalionBean, BaseViewHolder> {

    public TrainingBattalionAdapter(Context context) {
        super(R.layout.item_study_list);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainingBattalionBean item) {
        GlideUtils.load(item.getCoverImg(), helper.getView(R.id.iv_audition_item), R.drawable.image_placeholder);
        helper.setText(R.id.tv_audition_title_item, item.getTrainingCampName())
                .setText(R.id.tv_audition_content_item, item.getTrainingCampDesc())
                .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getApplyCount()) + "人在学");

        ProgressBar progressBar = helper.getView(R.id.pb_study_progress);
        if (item.getCompleteTaskNum() > 0) {
            String progress = TimeUtils.getStudyLook(item.getCompleteTaskNum(), item.getTaskNum());
            progressBar.setProgress(Integer.parseInt(progress));
            helper.setText(R.id.tv_study_progress, "已学习" + progress + "%");
        } else {
            helper.setText(R.id.tv_study_progress, "已学习:" + "0%");
        }
    }
}
