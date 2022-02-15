package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.GlideUtils;

/**
 * @author: Roy
 * date:   2021/6/30
 * company: 普华集团
 * description:
 */
public class ViewQuestionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public ViewQuestionAdapter(Context context) {
        super(R.layout.item_view_question);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivReleased = helper.getView(R.id.iv_released);
        GlideUtils.load(item, ivReleased);
    }
}
