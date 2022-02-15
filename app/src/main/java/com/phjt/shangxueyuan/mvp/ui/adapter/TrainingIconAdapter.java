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
 * date:    2021/1/18 14:26
 * company: 普华集团
 * description: 训练营-参与人数头像
 */
public class TrainingIconAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public TrainingIconAdapter(@Nullable List<String> data) {
        super(R.layout.item_training_icon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setVisible(R.id.item_view, helper.getLayoutPosition() == 0);
        RoundedImageView imageView = helper.getView(R.id.iv_icon);
        AppImageLoader.loadResUrl(item, imageView, R.drawable.iv_mine_avatar);
    }
}
