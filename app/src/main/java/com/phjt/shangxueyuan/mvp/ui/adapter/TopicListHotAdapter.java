package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TopicListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class TopicListHotAdapter extends BaseQuickAdapter<TopicListBean.RecordsBean, BaseViewHolder> {

    public TopicListHotAdapter(@Nullable List<TopicListBean.RecordsBean> data) {
        super(R.layout.item_topic_hot_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicListBean.RecordsBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_topic);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView topicName = helper.getView(R.id.topic_name);
        TextView topicNumb = helper.getView(R.id.topic_numb);
        TextView topicDesc = helper.getView(R.id.topic_desc);
        topicDesc.setText(item.getFocusDescribe());
        topicName.setText("#"+item.getTopicName());
        topicNumb.setText(item.getTopicDynamicNum()+"条评论");

    }
}
