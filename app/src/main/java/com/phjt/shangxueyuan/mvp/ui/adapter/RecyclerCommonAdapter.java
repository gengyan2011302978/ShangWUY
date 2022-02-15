package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;


/**
 * @author: Roy
 * date:   2020/6/2
 * company: 普华集团
 * description:
 */
public class RecyclerCommonAdapter extends BaseQuickAdapter<NotesDetailsBean, BaseViewHolder> {
    public RecyclerCommonAdapter(Context context) {
        super(R.layout.recyclear_item);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, NotesDetailsBean item) {

        helper.setText(R.id.tv_recyclear_like_num, "999+")
                .setText(R.id.tv_recyclear_phone_num, item.getNickName())
                .setText(R.id.tv_recyclear_content, item.getBackContent());
        helper.getView(R.id.tv_recyclear_like_num).setVisibility(View.GONE);
        helper.addOnClickListener(R.id.tv_recyclear_like_num);
        RoundedImageView ivReHeadPic = helper.getView(R.id.iv_re_head_pic);
        int failImage = R.drawable.iv_mine_avatar;
        GlideUtils.load(item.getPhoto(), ivReHeadPic, failImage);
    }
}
