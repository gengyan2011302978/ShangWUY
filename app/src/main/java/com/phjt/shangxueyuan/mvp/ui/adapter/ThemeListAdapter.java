package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MotifBean;

/**
 * @author: gengyan
 * date:    2020/11/24 15:08
 * company: 普华集团
 * description: 描述
 */
public class ThemeListAdapter extends BaseQuickAdapter<MotifBean, BaseViewHolder> {

    private Context mContext;

    public ThemeListAdapter(Context context) {
        super(R.layout.item_theme);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MotifBean item) {
        helper.setText(R.id.tv_date,item.getMotifDate());
        helper.setText(R.id.tv_title,item.getMotifTitle());
    }
}
