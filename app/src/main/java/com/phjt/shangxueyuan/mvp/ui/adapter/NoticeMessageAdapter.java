package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.bean.MessageListBean;

/**
 * @author: Roy
 * date:   2020/10/20
 * company: 普华集团
 * description:公告消息Adapter
 */
public class NoticeMessageAdapter extends BaseQuickAdapter<MessageListBean.RecordsBean, BaseViewHolder> {
    public NoticeMessageAdapter(Context context) {
        super(R.layout.adapter_notice_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.RecordsBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_message_commont, item.getContent())
                .setText(R.id.tv_time, item.getCreateTime());
    }
}
