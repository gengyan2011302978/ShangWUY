package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;
import com.phjt.shangxueyuan.utils.TimeUtils;


/**
 * @author: Roy
 * date:   2020/5/7
 * company: 普华集团
 * description:初/中/高级课程Adapter
 */
public class CourseClassifyAdapter extends BaseQuickAdapter<CourseClassifyBean.ChildListBean, BaseViewHolder> {

    private Context mContext;
    /**
     * type 课程类别: 0初级/1中级/2高级
     */
    private int type = 0;


    public CourseClassifyAdapter(Context context) {
        super(R.layout.item_course_classify);
        this.mContext = context;
    }

    public void setType(int types) {
        this.type = types;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseClassifyBean.ChildListBean item) {
        CourseClassifyBean.LatestWatch latestWatch = item.getLatestWatch();
        helper.setText(R.id.tv_course_level, String.valueOf(item.getName()))
                .setText(R.id.tv_course_form, "敬请期待")
                .setText(R.id.tv_course_content, !TextUtils.isEmpty(item.getDescription()) ? item.getDescription() : "");
        ProgressBar mProgressBar = helper.getView(R.id.pb_num);
        helper.addOnClickListener(R.id.tv_access_learning);
        helper.addOnClickListener(R.id.tv_course_form);
        long courseWatchDuration = item.getTotalWatchDuration();
        long sumTimeLong = item.getSumTimeLong();
        helper.setText(R.id.tv_progressbar_num, "课程学习进度" + TimeUtils.getStudyLook(courseWatchDuration, sumTimeLong) + "%");
        mProgressBar.setProgress(Integer.valueOf(TimeUtils.getStudyLook(courseWatchDuration, sumTimeLong)));

        if (item.getState() == 1) {
            helper.getView(R.id.tv_course_form).setVisibility(View.INVISIBLE);
            helper.getView(R.id.tv_progressbar_num).setVisibility(View.VISIBLE);
            helper.getView(R.id.pb_num).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_access_learning).setVisibility(View.VISIBLE);
            helper.getView(R.id.fl_watch_history).setVisibility(View.VISIBLE);

            if (latestWatch != null) {
                if (!TextUtils.isEmpty(latestWatch.getName())) {
                    helper.setText(R.id.tv_watch_history, "上次观看：" + latestWatch.getName());
                    helper.addOnClickListener(R.id.tv_continue_watch);
                }
            } else {
                helper.getView(R.id.fl_watch_history).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.tv_course_form).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_progressbar_num).setVisibility(View.GONE);
            helper.getView(R.id.pb_num).setVisibility(View.GONE);
            helper.getView(R.id.tv_access_learning).setVisibility(View.GONE);
            helper.getView(R.id.fl_watch_history).setVisibility(View.GONE);
        }


    }
}
