package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;

import com.phjt.shangxueyuan.bean.MineBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: Roy
 * date:   2020/8/4
 * company: 普华集团
 * description:
 */
public class MineAdapter extends BaseQuickAdapter<MineBean, BaseViewHolder> {
    public MineAdapter(Context context) {
        super(R.layout.item_mine);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, MineBean item) {

        helper.setText(R.id.tv_item_mine_name, item.getName());
        ImageView ivIcon = helper.getView(R.id.iv_item_mine_icon);
        ivIcon.setImageDrawable(item.getDrawable());

    }
}
