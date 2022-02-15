package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.IntroductionTopCardsBean;
import com.phjt.shangxueyuan.utils.GlideUtils;

/**
 * @author: Roy
 * date:   2021/1/18
 * company: 普华集团
 * description:
 */
public class IntroductionPunchCardsAdapter extends BaseQuickAdapter<IntroductionTopCardsBean.UserVos, BaseViewHolder> {

    private Context mContext;

    public IntroductionPunchCardsAdapter(Context context) {
        super(R.layout.item_introduction_punch_cards);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, IntroductionTopCardsBean.UserVos item) {
        ImageView ivAssociatedHead = helper.getView(R.id.iv_re_head_pic);
        GlideUtils.load(item.getPhoto(), ivAssociatedHead, R.drawable.iv_mine_avatar);
    }
}
