package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MotifDiaryListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ThemeNotesCommentAdapter extends BaseQuickAdapter<MotifDiaryListBean.DiaryCommentVosBean, BaseViewHolder> {

    public ThemeNotesCommentAdapter(@Nullable List<MotifDiaryListBean.DiaryCommentVosBean> data) {
        super(R.layout.item_notes_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MotifDiaryListBean.DiaryCommentVosBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_comment_icon_item);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon);
        TextView tvName = helper.getView(R.id.tv_comment_phone_item);
        ImageView ivVip = helper.getView(R.id.iv_vip);
        TextView tvComment = helper.getView(R.id.tv_comment_content_item);
        TextView tvMore = helper.getView(R.id.tv_more);

        if (helper.getPosition()==2){
            tvMore.setVisibility(View.VISIBLE);
        }else {
            tvMore.setVisibility(View.GONE);
        }

        RecyclerView recycleCircleCourse = new RecyclerView(mContext);
        recycleCircleCourse.setLayoutManager(new LinearLayoutManager(mContext));

        tvName.setText(item.getNickName());
        tvComment.setText(item.getReplyContent());

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_DIARY_DETAILS)+"?id="+item.getId());
                mContext.startActivity(intent);
            }
        });
        //Vip icon展示
//        VipUtil.setVipImage(item.get(), ivVip);
    }
}
