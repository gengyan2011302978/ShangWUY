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
 * @author: Roy
 * date:   2020/9/18
 * company: 普华集团
 * description:编辑笔记adapter
 */
public class NotesEditingAdapter extends BaseQuickAdapter<FeedbackPictureBean, BaseViewHolder> {
    private boolean isChanged = false;
    public NotesEditingAdapter(@Nullable List<FeedbackPictureBean> data) {
        super(R.layout.item_notes_editing, data);
    }

    public void ItemChanged(){
        isChanged = true;
    }
    @Override
    protected void convert(BaseViewHolder helper, FeedbackPictureBean item) {
        int layoutPosition = helper.getLayoutPosition();
        helper.addOnClickListener(R.id.iv_add);
        helper.addOnClickListener(R.id.iv_delete);
        ImageView ivAdd = helper.getView(R.id.iv_add);
        ImageView ivShow = helper.getView(R.id.iv_show);
        ImageView ivDelete = helper.getView(R.id.iv_delete);

        if (!StringUtils.isEmpty(item.getAbsolutePath())) {
            ivAdd.setVisibility(View.GONE);
            ivDelete.setVisibility(View.VISIBLE);
            GlideUtils.load(item.getAbsolutePath(), ivShow);

        } else {
            ivAdd.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.GONE);
            ivShow.setImageBitmap(null);
        }
    }
}
