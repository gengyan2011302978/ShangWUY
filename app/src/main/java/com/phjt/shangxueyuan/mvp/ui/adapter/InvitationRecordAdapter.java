package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.InvitationRecordBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/8/28 10:56
 * company: 普华集团
 * description: 邀请列表
 */
public class InvitationRecordAdapter extends BaseQuickAdapter<InvitationRecordBean, BaseViewHolder> {

    private Context mContext;

    public InvitationRecordAdapter(Context context, @Nullable List<InvitationRecordBean> data) {
        super(R.layout.item_invitation_record, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitationRecordBean item) {
        helper.setText(R.id.tv_invitation_phone_item, item.getInviteeMobile()
                .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));

        TextView invitationState = helper.getView(R.id.tv_invitation_state_item);
        if (item.getActivatedState() == 0) {
            invitationState.setText("已激活");
            invitationState.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        } else {
            invitationState.setText("未激活");
            invitationState.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF2F48));
        }
    }
}
