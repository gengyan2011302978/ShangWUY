package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageListBean;

import java.util.List;

/**
 * @author: Roy
 * date:   2020/10/20
 * company: 普华集团
 * description:系统消息
 */
public class SystemMessageAdapter extends BaseQuickAdapter<MessageListBean.RecordsBean, BaseViewHolder> {
    public SystemMessageAdapter(Context context, @Nullable List<MessageListBean.RecordsBean> data) {
        super(R.layout.adapter_system_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.RecordsBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_message_commont, item.getContent())
                .setText(R.id.tv_time, item.getCreateTime())
                .addOnClickListener(R.id.relat_item);
    }
}
