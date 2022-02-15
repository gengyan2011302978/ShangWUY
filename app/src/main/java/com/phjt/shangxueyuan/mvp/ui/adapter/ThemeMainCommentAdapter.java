package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ThemeMainCommentAdapter extends BaseQuickAdapter<ThemeMainBean.RecordsBean.ThemeReplyListBean, BaseViewHolder> {

    public ThemeMainCommentAdapter(@Nullable List<ThemeMainBean.RecordsBean.ThemeReplyListBean> data) {
        super(R.layout.item_cicle_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeMainBean.RecordsBean.ThemeReplyListBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_comment_icon_item);
        AppImageLoader.loadResUrl(item.getReplyPhoto(), ivIcon);
        TextView tvName = helper.getView(R.id.tv_comment_phone_item);
        ImageView ivVip = helper.getView(R.id.iv_vip);
        TextView tvComment = helper.getView(R.id.tv_comment_content_item);


        RecyclerView recycleCircleCourse = new RecyclerView(mContext);
        recycleCircleCourse.setLayoutManager(new LinearLayoutManager(mContext));

        tvName.setText(item.getReplyNickName());
        tvComment.setText(item.getThemeReplyContent());

        //Vip icon展示
        VipUtil.setVipImage(item.getReplyVipState(), ivVip);
    }
}
