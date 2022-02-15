package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageListBean;

/**
 * @author: Roy
 * date:   2020/10/20
 * company: 普华集团
 * description:课程消息Adapter
 */
public class CourseMessageAdapter extends BaseQuickAdapter<MessageListBean.RecordsBean, BaseViewHolder> {
    public CourseMessageAdapter(Context context) {
        super(R.layout.adapter_course_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.RecordsBean item) {
        helper.setText(R.id.tv_title, "【直播上线】" + item.getTitle())
                .setText(R.id.tv_message_commont, item.getContent())
                .setText(R.id.tv_time, String.valueOf(item.getCreateTime()))
                .addOnClickListener(R.id.tv_play_back);
        //	1 邀请有礼 2 BOC初级课程列表 3 精品试听课程列表 4 反馈
        if (1 == item.getMouduleId()) {
            helper.setText(R.id.tv_play_back, "点击下方链接进行邀请好友");
        } else if (2 == item.getMouduleId()) {
            helper.setText(R.id.tv_play_back, "点击下方链接查看更多BOC商科课程");
        } else if (3 == item.getMouduleId()) {
            helper.setText(R.id.tv_play_back, "点击下方链接查看更多公开课程");
        } else if (4 == item.getMouduleId()) {
            helper.setText(R.id.tv_play_back, "点击下方链接进行意见反馈");
        } else if (29 == item.getMessageClassify()) {
            helper.setText(R.id.tv_play_back, "点击下方链接查看训练营详情");
        } else if (31 == item.getMessageClassify()) {
            helper.setText(R.id.tv_play_back, "点击下方链接查看直播课程");
        } else if ("0".equals(item.getCourseId()) && !TextUtils.isEmpty(item.getTopicId()) && !"0".equals(item.getTopicId())) {
            helper.setText(R.id.tv_play_back, "点击下方链接查看话题详情");
        } else {
            helper.setText(R.id.tv_play_back, "点击下方链接查看课程详情及观看直播回放");
        }
    }
}
