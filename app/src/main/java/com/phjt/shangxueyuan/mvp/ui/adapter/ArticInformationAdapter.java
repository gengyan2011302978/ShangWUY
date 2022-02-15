package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ArticleListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ArticInformationAdapter extends BaseQuickAdapter<ArticleListBean.RecordsBean, BaseViewHolder> {

    public ArticInformationAdapter(@Nullable List<ArticleListBean.RecordsBean> data) {
        super(R.layout.item_homepage_imformation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListBean.RecordsBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getArticleImg(), ivIcon);
        ImageView ivTop = helper.getView(R.id.iv_top);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDesc = helper.getView(R.id.tv_desc);
        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvArticleLabel = helper.getView(R.id.tv_articleLabel);
        TextView tvClassifyName = helper.getView(R.id.tv_classifyName);
        tvArticleLabel.setText(item.getArticleLabel());
        tvClassifyName.setText(item.getClassifyName());
        tvTitle.setText(item.getArticleTitle());
        if (item.getArticleTop()==1){
            ivTop.setVisibility(View.VISIBLE);
        }else {
            ivTop.setVisibility(View.GONE);
        }
        tvDesc.setText(item.getArticleDesc());
        tvNumber.setText(item.getArticleNum()+"人在看");
    }
}
