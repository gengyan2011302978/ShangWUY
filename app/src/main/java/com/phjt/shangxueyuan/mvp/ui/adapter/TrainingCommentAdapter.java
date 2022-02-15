package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.ctetin.expandabletextviewlibrary.app.LinkType;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gengyan
 * date:    2021/2/1 14:36
 * company: 普华集团
 * description: 训练营评论列表
 */
public class TrainingCommentAdapter extends BaseQuickAdapter<TrainingCommentBean.DiaryListBean, BaseViewHolder> {

    private Context mContext;

    public TrainingCommentAdapter(Context context, @Nullable List<TrainingCommentBean.DiaryListBean> data) {
        super(R.layout.item_training_comment, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TrainingCommentBean.DiaryListBean item) {
        helper.setText(R.id.tv_comment_phone_item, item.getNickname())
                .setText(R.id.tv_like, CountNumUtils.getCountNum(item.getLikeNum()))
                .setText(R.id.tv_reply, String.valueOf(item.getCommentNum()))
                .addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.iv_notes_reply)
                .addOnClickListener(R.id.tv_reply);
        //头像
        RoundedImageView ivIcon = helper.getView(R.id.iv_comment_icon_item);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon, R.drawable.iv_mine_avatar);
        //vip状态展示
        ImageView ivVip = helper.getView(R.id.iv_vip_item);
        VipUtil.setVipImage(item.getVipState(), ivVip);
        //内容
        ExpandableTextView eTv = helper.getView(R.id.tv_comment_content_item);
        eTv.setContent(item.getContent());
        eTv.setLinkClickListener(new ExpandableTextView.OnLinkClickListener() {
            @Override
            public void onLinkClickListener(LinkType type, String content, String selfContent) {
//                Toast.makeText(mContext,"111111111111",Toast.LENGTH_SHORT).show();
            }
        });
        //点赞
        ImageView ivZan = helper.getView(R.id.iv_like);
        ivZan.setSelected(item.getLikestatus() != 0);

        RecyclerView rvComment = helper.getView(R.id.rv_comment_item);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        rvComment.setLayoutManager(layoutManager);
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rvComment.setAdapter(mAdapter);

        if (!TextUtils.isEmpty(item.getDiaryImg())) {
            List<String> imgList = Arrays.asList(item.getDiaryImg().split(","));
            mAdapter.setNewData(imgList);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(mContext, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(imgList));
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            });
        } else {
            mAdapter.setNewData(null);
        }
    }
}
