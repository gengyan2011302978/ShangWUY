package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phsxy.utils.StringUtils;

import java.util.List;

/**
 * @author: Created by shaopengfei
 * date: 2020/5/8 13:45
 * company: 普华集团
 * description: 意见反馈图片适配器
 */
public class FeedbackAdapter extends BaseQuickAdapter<FeedbackPictureBean, BaseViewHolder> {
    public FeedbackAdapter(@Nullable List<FeedbackPictureBean> data) {
        super(R.layout.item_feedback_picture, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedbackPictureBean item) {
        helper.addOnClickListener(R.id.iv_add);
        helper.addOnClickListener(R.id.iv_delete);
        ImageView ivAdd = helper.getView(R.id.iv_add);
        ImageView ivShow = helper.getView(R.id.iv_show);
        ImageView ivDelete = helper.getView(R.id.iv_delete);

        if (StringUtils.isEmpty(item.getAbsolutePath())) {
            ivAdd.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.GONE);
            ivShow.setImageBitmap(null);
        } else {
            ivAdd.setVisibility(View.GONE);
            ivDelete.setVisibility(View.VISIBLE);
            GlideUtils.load(item.getAbsolutePath(), ivShow);
        }
    }
}
