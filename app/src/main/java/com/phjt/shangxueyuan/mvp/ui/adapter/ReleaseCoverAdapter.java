package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ReleaseCoverBean;
import com.phjt.shangxueyuan.common.AppImageLoader;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ReleaseCoverAdapter extends BaseQuickAdapter<ReleaseCoverBean, BaseViewHolder> {

    public ReleaseCoverAdapter(@Nullable List<ReleaseCoverBean> data) {
        super(R.layout.item_cover, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReleaseCoverBean item) {
        ImageView ivIcon = helper.getView(R.id.iv_cover_one);
        ImageView ivChenck = helper.getView(R.id.iv_chenck_one);
        AppImageLoader.loadResUrl(item.getImgUrl(), ivIcon);
        helper.addOnClickListener(R.id.iv_cover_one);
        ivChenck.setVisibility(item.getCheckStatus()==1?View.VISIBLE:View.GONE);
    }
}
