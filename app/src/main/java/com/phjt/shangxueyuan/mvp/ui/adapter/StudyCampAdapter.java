package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.StudyCampBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: Roy
 * date:   2021/4/23
 * company: 普华集团
 * description:
 */
public class StudyCampAdapter extends BaseQuickAdapter<StudyCampBean, BaseViewHolder> {

    private Context mContext;
    private int mType;

    public StudyCampAdapter(Context context) {
        super(R.layout.item_study_camp);
        this.mContext = context;
    }

    public void setType(int type) {
        mType = type;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, StudyCampBean item) {
        helper.setText(R.id.tv_audition_title_item, item.getTrainingCampName())
                .setText(R.id.tv_audition_content_item, String.format(mContext.getString(R.string.mine_task_mum), item.getCurriculumStartTime()+"至"+item.getCurriculumEndTime()))
                .setText(R.id.tv_student_number, String.format(mContext.getString(R.string.mine_task_sign_up), item.getApplyNum()))
                .setText(R.id.tv_date, " ")
                .setVisible(R.id.view_time, false)
                .setVisible(R.id.rl_undulate, false)
                .setVisible(R.id.tv_status, true)
                .setText(R.id.tv_study_tasks_number, String.format(mContext.getString(R.string.mine_task_number), item.getTaskNum()));

        RoundedImageView ivIcon = helper.getView(R.id.iv_audition_item);
//        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        GlideUtils.load(item.getCoverImg(), ivIcon);
        TextView tvAuditionTitleItem = helper.getView(R.id.tv_audition_title_item);
        TextView tvPunchClock = helper.getView(R.id.tv_status);
        ImageView ivAlreadJoin = helper.getView(R.id.iv_alread_join);
        tvPunchClock.setBackgroundResource(R.drawable.bg_f650c_rectangle);
        tvPunchClock.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        if (item.getRegistrationStatus()==0){
            ivAlreadJoin.setVisibility(View.GONE);
            tvAuditionTitleItem.setText(item.getTrainingCampName());
        }else {
            ivAlreadJoin.setVisibility(View.VISIBLE);
            tvAuditionTitleItem.setText("          "+item.getTrainingCampName());
        }
        if (1 == item.getState()) {
            tvPunchClock.setText("未开始");
            tvPunchClock.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_657587_rectangle));
            helper.setText(R.id.tv_date, String.format("开始报名 %s",item.getRecruitStudentStartTime()))
                    .setVisible(R.id.view_time, true);
        } else if (0 == item.getState()) {
            tvPunchClock.setText("报名中");
            tvPunchClock.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_f650c_rectangle));
            helper.setText(R.id.tv_date, String.format("报名截止 %s",item.getRecruitStudentEndTime()))
                    .setVisible(R.id.view_time, true);
        } else if (2 == item.getState()) {
            tvPunchClock.setText("待开营");
            tvPunchClock.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_ffb30c_rectangle));
        } else if (3 == item.getState()) {
            ImageView ivUndulate = helper.getView(R.id.iv_undulate);
            helper.setVisible(R.id.rl_undulate, true);
            helper.setVisible(R.id.tv_status, false);
            AnimationDrawable animationDrawable = (AnimationDrawable) ivUndulate.getDrawable();
            //开启帧动画
            animationDrawable.start();
        } else if (4 == item.getState()) {
            tvPunchClock.setText("已结束");
            tvPunchClock.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_c0c0c0_rectangle));
        }

    }
}
