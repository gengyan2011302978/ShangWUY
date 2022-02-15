package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;

import java.util.List;


/**
 * @author: yuyang
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 学习
 */
public class StudyCardAdapter extends BaseQuickAdapter<CourseBean, BaseViewHolder> {

    public StudyCardAdapter(Context context, @Nullable List<CourseBean> data) {
        super(R.layout.item_study_list, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseBean item) {
        GlideUtils.load(item.getCoverImg(), helper.getView(R.id.iv_audition_item), R.drawable.image_placeholder);
        helper.setVisible(R.id.tv_off_state, item.getUpState() == 0)
                .setText(R.id.tv_audition_title_item, item.getName())
                .setText(R.id.tv_audition_content_item, item.getCouDesc())
                .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getStudyNum()) + "人在学");

        ProgressBar progressBar = helper.getView(R.id.pb_study_progress);
        if (item.getSumTimeLong() > 0) {
            String progress = TimeUtils.getStudyLook(item.getCourseWatchDuration(), item.getSumTimeLong());
            progressBar.setProgress(Integer.parseInt(progress));
            helper.setText(R.id.tv_study_progress, "已学习" + progress + "%");
        } else {
            helper.setText(R.id.tv_study_progress, "已学习:" + "0%");
        }
    }
}
