package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ThemeMainImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ThemeMainImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_cicle_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_comment_icon_item);
        AppImageLoader.loadResUrl(item, ivIcon);
    }
}
