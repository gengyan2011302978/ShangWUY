package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TopicListBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class LearnInforAdapter extends BaseQuickAdapter<TopicListBean.RecordsBean, BaseViewHolder> {

    public LearnInforAdapter(@Nullable List<TopicListBean.RecordsBean> data) {
        super(R.layout.item_learn_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicListBean.RecordsBean item) {


    }
}
