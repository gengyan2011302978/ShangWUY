package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
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
 * date:    2020/5/7 11:21
 * company: 普华集团
 * description: 描述
 */
public class CourseCommentAdapter extends BaseQuickAdapter<CourseCommentBean, BaseViewHolder> {

    private Context mContext;

    public CourseCommentAdapter(Context context, @Nullable List<CourseCommentBean> data) {
        super(R.layout.item_course_comment, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseCommentBean item) {
        helper.setText(R.id.tv_comment_phone_item, item.getNickName())
                .setText(R.id.tv_like, CountNumUtils.getCountNum(item.getLikeNum()))
                .setText(R.id.tv_my_notes_reply, String.valueOf(item.getBackNum()))
                .addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.tv_comment_content_item)
                .addOnClickListener(R.id.tv_my_notes_reply)
                .addOnClickListener(R.id.iv_notes_reply)
                .addOnClickListener(R.id.tv_share)
                .addOnClickListener(R.id.gray_view)
                .addOnClickListener(R.id.iv_comment_icon_item)
                .addOnClickListener(R.id.tv_comment_phone_item)
                .addOnClickListener(R.id.iv_vip_item);

        //头像
        RoundedImageView ivIcon = helper.getView(R.id.iv_comment_icon_item);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon, R.drawable.iv_mine_avatar);
        //vip状态展示
        ImageView ivVip = helper.getView(R.id.iv_vip_item);
        VipUtil.setVipImage(item.getVipState(), ivVip);
        //内容
        ExpandableTextView eTv = helper.getView(R.id.tv_comment_content_item);
        eTv.setContent(item.getContent());
        //审核状态
        TextView tvCommentStatus = helper.getView(R.id.tv_comment_status);
        //点赞
        TextView tvCommentZan = helper.getView(R.id.tv_like);
        ImageView ivZan = helper.getView(R.id.iv_like);
        ivZan.setSelected(item.getUserStatus() == 1);

        //底部 点赞 评论 分享布局  ————底部置灰遮罩
        View mBottomGrayView = helper.getView(R.id.gray_view);
        if (item.getAuditState() == 0) {
            tvCommentStatus.setText("审核中");
            tvCommentStatus.setVisibility(View.VISIBLE);
            mBottomGrayView.setVisibility(View.VISIBLE);
        } else if (item.getAuditState() == 2) {
            tvCommentStatus.setText("审核未通过");
            tvCommentStatus.setVisibility(View.VISIBLE);
            mBottomGrayView.setVisibility(View.VISIBLE);
        } else {
            //审核通过
            tvCommentStatus.setVisibility(View.GONE);
            mBottomGrayView.setVisibility(View.GONE);
        }

        RecyclerView rvComment = helper.getView(R.id.rv_comment_item);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        rvComment.setLayoutManager(layoutManager);
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rvComment.setAdapter(mAdapter);

        if (!TextUtils.isEmpty(item.getImg())) {
            List<String> imgList = Arrays.asList(item.getImg().split(","));
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
