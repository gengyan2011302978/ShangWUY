package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class HompageCourseAdapterBanner extends BaseQuickAdapter<ListBannerBean, BaseViewHolder> {

    public HompageCourseAdapterBanner(@Nullable List<ListBannerBean> data) {
        super(R.layout.item_homepage_course_banner, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBannerBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getCoverUrl(), ivIcon);
    }
}
