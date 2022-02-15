package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.GlideUtils;

/**
 * @author: Roy
 * date:   2020/9/17
 * company: 普华集团
 * description:
 */
public class ReleasedAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public ReleasedAdapter(Context context) {
        super(R.layout.item_released);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivReleased = helper.getView(R.id.iv_released);
        GlideUtils.load(item, ivReleased);
    }
}
