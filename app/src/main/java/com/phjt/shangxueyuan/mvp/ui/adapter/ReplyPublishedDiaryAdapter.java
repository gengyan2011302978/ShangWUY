package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: Roy
 * date:   2021/1/15
 * company: 普华集团
 * description:回复发布日志
 */
public class ReplyPublishedDiaryAdapter extends BaseQuickAdapter<MyDiaryBean.DiaryCommentVos, BaseViewHolder> {

    private Context mContext;
    private int mSize;

    public ReplyPublishedDiaryAdapter(Context context) {
        super(R.layout.item_reply_published_diary);
        this.mContext = context;
    }

    public void setDataListSize(int size) {
        mSize = size;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyDiaryBean.DiaryCommentVos item) {
        helper.setText(R.id.tv_reply_name, item.getNickName());
        ExpandableTextView eTv = helper.getView(R.id.tv_reply_content);
        eTv.setContent(item.getReplyContent());
        RoundedImageView ivReHeadPic = helper.getView(R.id.iv_reply_head_pic);
        int failImage = R.drawable.iv_mine_avatar;
        GlideUtils.load(item.getPhoto(), ivReHeadPic, failImage);
        if (helper.getAdapterPosition() == mSize - 1) {
            helper.setVisible(R.id.view_reply_content, false);
        } else {
            helper.setVisible(R.id.view_reply_content, true);
        }
    }
}
