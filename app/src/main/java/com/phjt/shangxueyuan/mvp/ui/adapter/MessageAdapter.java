package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageBean;

/**
 * @author: renzhiming
 * date:    2020/03/27
 * company: 普华集团
 * description: 消息
 */
public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    private Context mContxt;

    public MessageAdapter(Context context) {
        super(R.layout.message_adapter);
        mContxt = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_message_info,    String.valueOf(item.getMessageCount()))
                .setText(R.id.tv_message_data, item.getMessageDate());
        ImageView ivMessage = helper.getView(R.id.iv_message);
        if (item.getType() == 1) {
            helper.setText(R.id.tv_message_name, "系统消息");
            ivMessage.setImageDrawable(mContxt.getResources().getDrawable(R.drawable.ic_msg_system));
        } else if (item.getType() == 2) {
            helper.setText(R.id.tv_message_name, "课程提醒");
            ivMessage.setImageDrawable(mContxt.getResources().getDrawable(R.drawable.ic_msg_course));
        } else if (item.getType() == 3) {
            helper.setText(R.id.tv_message_name, "互动提醒");
            ivMessage.setImageDrawable(mContxt.getResources().getDrawable(R.drawable.ic_msg_interact));
        } else if (item.getType() == 4) {
            helper.setText(R.id.tv_message_name, "活动公告");
            ivMessage.setImageDrawable(mContxt.getResources().getDrawable(R.drawable.ic_msg_notice));
        }
        helper.getView(R.id.tv_message_info).setVisibility(item.getMessageCount() > 0 ? View.VISIBLE : View.GONE);
    }
}
