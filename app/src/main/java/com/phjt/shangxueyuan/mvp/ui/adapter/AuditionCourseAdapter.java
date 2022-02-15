package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.view.roundImg.RoundedImageView;


import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/27 11:39
 * company: 普华集团
 * description: 试听课程的adapter
 */
public class AuditionCourseAdapter extends BaseQuickAdapter<CourseItemBean, BaseViewHolder> {

    private Context mContext;

    public AuditionCourseAdapter(Context context, @Nullable List<CourseItemBean> data) {
        super(R.layout.item_audition_course, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseItemBean item) {
        if (null!=item){
            helper.setText(R.id.tv_audition_title_item, TextUtils.isEmpty(item.getName())?"":item.getName())
                    .setText(R.id.tv_audition_content_item, TextUtils.isEmpty(item.getCouDesc())?"":item.getCouDesc())
                    .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getStudyNum()) + "人在学");
            RoundedImageView ivIcon = helper.getView(R.id.iv_audition_item);
            AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);

            //总时长
            Long sumTimeLong = item.getSumTimeLong();
            //课程观看时长
            Long courseWatchDuration = item.getCourseWatchDuration();
            helper.setText(R.id.tv_rate_of_learning, "已学习" + TimeUtils.getStudyLook(courseWatchDuration, sumTimeLong) + "%");
        }
    }
}
