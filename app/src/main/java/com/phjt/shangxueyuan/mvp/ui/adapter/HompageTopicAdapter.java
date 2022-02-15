package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TopicMainBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

import static com.phjt.shangxueyuan.utils.CountNumUtils.getStudyNum;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class HompageTopicAdapter extends BaseQuickAdapter<TopicMainBean, BaseViewHolder> {

    public HompageTopicAdapter(@Nullable List<TopicMainBean> data) {
        super(R.layout.item_topic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicMainBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_topic);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView topicName = helper.getView(R.id.topic_name);
        TextView topicNumb = helper.getView(R.id.topic_numb);
        if (item.getId() == 999) {
            topicName.setText(String.valueOf(item.getTopicName()));
        } else {
            topicName.setText(String.format("#%s", item.getTopicName()));
        }
        if (item.getViewNum() == -1) {
            topicNumb.setText("创建话题");
            ivIcon.setBackgroundResource(R.drawable.ic_topic_bg);
        } else {
            topicNumb.setText(String.format("%s人围观", getStudyNum(item.getViewNum())));
        }

    }
}
