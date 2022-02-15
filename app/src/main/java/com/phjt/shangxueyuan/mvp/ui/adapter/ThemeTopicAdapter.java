package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TopicItemInfoBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.phjt.base.utils.ArchitectUtils.startActivity;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ThemeTopicAdapter extends BaseQuickAdapter<TopicItemInfoBean.RecordsBean, BaseViewHolder> {

    public ThemeTopicAdapter(@Nullable List<TopicItemInfoBean.RecordsBean> data) {
        super(R.layout.item_topiclist, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(BaseViewHolder helper, TopicItemInfoBean.RecordsBean item) {
        ThemeMainImageAdapter themeMainImageAdapter;
        RoundedImageView ivIcon = helper.getView(R.id.iv_mine_head_pic);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon);
        TextView tvName = helper.getView(R.id.tv_name);
        ExpandableTextView tvComment = helper.getView(R.id.tv_comment);
        TextView tvLike = helper.getView(R.id.tv_like);
        TextView tvMyNotesReply = helper.getView(R.id.tv_my_notes_reply);
        ImageView ivMore = helper.getView(R.id.iv_more);
        TextView tvTopicName = helper.getView(R.id.tv_topicName);
        TextView tvIsTop = helper.getView(R.id.tv_isTop);
        TextView tvDate = helper.getView(R.id.tv_date);
        ImageView ivVip = helper.getView(R.id.iv_vip);
        ImageView circleDynamicImage = helper.getView(R.id.circle_dynamic_image);
        ImageView ivLike = helper.getView(R.id.iv_like);
        helper.addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.relat_comment)
                .addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.iv_notes_reply)
                .addOnClickListener(R.id.tv_share)
                .addOnClickListener(R.id.tv_my_notes_reply)
                .addOnClickListener(R.id.iv_more);

        RecyclerView recycleImageCourse = helper.getView(R.id.recycle_image_course);
        if ("0".equals(item.getIsAdministrator())) {
            ivMore.setVisibility(View.GONE);
        } else {
            ivMore.setVisibility(View.VISIBLE);
        }
        ivLike.setImageResource("0".equals(item.getLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
        circleDynamicImage.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(item.getThemeImg())) {
            recycleImageCourse.setVisibility(View.VISIBLE);
            List<String> imagelist = Arrays.asList(item.getThemeImg().split(","));
            if (!imagelist.isEmpty()) {
                GridLayoutManager gridLayoutManager;
                if (imagelist.size() == 4) {
                    gridLayoutManager = new GridLayoutManager(mContext, 2);
                } else {
                    gridLayoutManager = new GridLayoutManager(mContext, 3);
                }
                recycleImageCourse.setLayoutManager(gridLayoutManager);
                themeMainImageAdapter = new ThemeMainImageAdapter(imagelist);
                recycleImageCourse.setAdapter(themeMainImageAdapter);

                themeMainImageAdapter.setOnItemClickListener((adapter, view, position) -> {
                    List<String> adapterImages = adapter.getData();
                    Intent intent = new Intent(mContext, BigPhotoActivity.class);
                    intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(adapterImages));
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                    startActivity(intent);
                });
            }
        } else {
            recycleImageCourse.setVisibility(View.GONE);
        }

        tvTopicName.setText(item.getTopicName());
        tvDate.setText(item.getCreateTime());
        tvLike.setText(item.getThemeLikeNum() == 0 ? "赞" : item.getThemeLikeNum() + "");
        tvMyNotesReply.setText(String.valueOf(item.getThemeDynamicNum()));
        tvName.setText(item.getNickName());
        tvIsTop.setVisibility("1".equals(item.getThemeState()) ? View.VISIBLE : View.GONE);
        tvComment.setContent("1".equals(item.getThemeState()) ? "            " + item.getThemeName() : item.getThemeName());
        if ("1".equals(item.getVipState()) || "2".equals(item.getVipState())) {
            ivVip.setVisibility(View.VISIBLE);
        } else {
            ivVip.setVisibility(View.GONE);
        }
    }
}
