package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Roy
 * date:   2021/1/15
 * company: 普华集团
 * description:我的打卡-发表的日记
 */
public class PublishedDiaryAdapter extends BaseQuickAdapter<MyDiaryBean, BaseViewHolder> {

    private Context mContext;

    public PublishedDiaryAdapter(Context context) {
        super(R.layout.item_published_diary);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyDiaryBean item) {
        helper.setText(R.id.tv_recyclear_like_num, item.getLikeNum() > 1000 ? "999+" : String.valueOf(item.getLikeNum()))
                .setText(R.id.tv_recyclear_phone_num, item.getNickName())
                .setText(R.id.tv_day_num, String.format("已坚持%s天", item.getClockDay()))
                .setText(R.id.tv_course, String.format("#%s", item.getMotifTitle()))
                .setVisible(R.id.tv_course, TextUtils.isEmpty(item.getMotifTitle()) ? false : true)
                .setText(R.id.tv_recyclear_time, item.getCreateTime());
        Drawable mLikeUn = ContextCompat.getDrawable(mContext, R.drawable.ic_like_un);
        Drawable mLike = ContextCompat.getDrawable(mContext, R.drawable.ic_like);

        TextView tvLikeNum = helper.getView(R.id.tv_recyclear_like_num);
        tvLikeNum.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getState() == 0 ? mLikeUn : mLike, null, null, null);
        helper.getView(R.id.tv_recyclear_like_num).setVisibility(View.VISIBLE);
        helper.addOnClickListener(R.id.tv_recyclear_like_num);
        helper.addOnClickListener(R.id.tv_reply);
        helper.addOnClickListener(R.id.view_show);
        helper.addOnClickListener(R.id.tv_course);
        //内容
        ExpandableTextView eTv = helper.getView(R.id.tv_recyclear_content);
        eTv.setContent(item.getContent());

        RoundedImageView ivReHeadPic = helper.getView(R.id.iv_re_head_pic);
        RecyclerView rvImg = helper.getView(R.id.rv_img);

        helper.setVisible(R.id.tv_see_more_commentsy, false);
        if (!TextUtils.isEmpty(item.getDiaryImg())) {
            setReleased(rvImg, item.getDiaryImg());
        } else {
            setEmpty(rvImg);
        }
        int failImage = R.drawable.iv_mine_avatar;
        GlideUtils.load(item.getPhoto(), ivReHeadPic, failImage);
        RecyclerView rvReply = helper.getView(R.id.rv_reply);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvReply.setLayoutManager(layoutManager);
        ReplyPublishedDiaryAdapter adapter = new ReplyPublishedDiaryAdapter(mContext);
        rvReply.setAdapter(adapter);
        if (item.getDiaryCommentVos() != null && item.getDiaryCommentVos().size() > 0) {
            adapter.setNewData(item.getDiaryCommentVos());
            if (item.getDiaryCommentVos().size() > 3) {
                helper.setVisible(R.id.tv_see_more_commentsy, true);
            }
            adapter.setDataListSize(item.getDiaryCommentVos().size());
        } else {
            adapter.setNewData(new ArrayList<>());
        }
    }

    /**
     * 设置发表图片
     */
    private void setReleased(RecyclerView rcyReleased, String notesImg) {
        List<String> imgList = Arrays.asList(notesImg.split(","));
        if (imgList.size() > 4) {
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
            rcyReleased.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false);
            rcyReleased.setLayoutManager(layoutManager);
        }
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rcyReleased.setAdapter(mAdapter);
        mAdapter.setNewData(imgList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, BigPhotoActivity.class);
            intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(imgList));
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
            ArchitectUtils.startActivity(intent);
        });
    }

    /**
     * 设置空图像
     *
     * @param rcyReleased
     */
    private void setEmpty(RecyclerView rcyReleased) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        rcyReleased.setLayoutManager(layoutManager);
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rcyReleased.setAdapter(mAdapter);
        mAdapter.setNewData(new ArrayList<>());
    }
}
