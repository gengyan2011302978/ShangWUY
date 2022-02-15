package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageListBean;

/**
 * @author: Roy
 * date:   2020/10/20
 * company: 普华集团
 * description:互动消息
 */
public class InteractionMessageAdapter  extends BaseQuickAdapter<MessageListBean.RecordsBean, BaseViewHolder> {
    public InteractionMessageAdapter(Context context) {
        super(R.layout.adapter_message_interaction);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.RecordsBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_message_commont,item.getContent())
                .setText(R.id.tv_time, item.getCreateTime());
    }
}
