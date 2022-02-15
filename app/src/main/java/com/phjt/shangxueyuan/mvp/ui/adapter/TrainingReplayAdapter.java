package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2021/2/4 15:36
 * company: 普华集团
 * description: 训练营-评论-回复列表
 */
public class TrainingReplayAdapter extends BaseQuickAdapter<TrainingCommentBean.ReplyVoListBean, BaseViewHolder> {

    private Context mContext;

    public TrainingReplayAdapter(Context context, @Nullable List<TrainingCommentBean.ReplyVoListBean> data) {
        super(R.layout.item_training_commnet_reply, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainingCommentBean.ReplyVoListBean item) {
        helper.setText(R.id.tv_reply_phone_item, item.getNickname())
                .setText(R.id.tv_reply_time_item, item.getCreateTime())
                .setText(R.id.tv_like, CountNumUtils.getCountNum(item.getLikeNum()))
                .addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_like);

        //头像
        RoundedImageView ivIcon = helper.getView(R.id.iv_reply_icon_item);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon, R.drawable.iv_mine_avatar);
        //vip状态展示
        ImageView ivVip = helper.getView(R.id.iv_vip_item);
        VipUtil.setVipImage(item.getVipState(), ivVip);

        //内容
        ExpandableTextView eTv = helper.getView(R.id.tv_reply_content_item);
        eTv.setContent(item.getReplyContent());
        //点赞
        ImageView ivZan = helper.getView(R.id.iv_like);
        ivZan.setSelected(item.getLikestatus() != 0);
    }
}
