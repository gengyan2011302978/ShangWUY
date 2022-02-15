package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MotifDiaryListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.phjt.base.utils.ArchitectUtils.startActivity;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class ThemeHistoryAdapter extends BaseQuickAdapter<MotifDiaryListBean, BaseViewHolder> {

    public ThemeHistoryAdapter(Context context) {
        super(R.layout.item_notes_bottom);
    }

    @Override
    protected void convert(BaseViewHolder helper, MotifDiaryListBean item) {
        RoundedImageView ivHead = helper.getView(R.id.iv_mine_head_pic);
        AppImageLoader.loadResUrl(item.getPhoto(),ivHead,R.drawable.iv_mine_avatar);
        helper.setText(R.id.tv_name, item.getNickName());
        ExpandableTextView tvComment = helper.getView(R.id.tv_comment);
        tvComment.setContent(item.getContent());
        helper.setText(R.id.tv_date, item.getCreateTime());
        TextView tvLike = helper.getView(R.id.tv_like);
        if (item.getLikeNum() != 0) {
            tvLike.setText(item.getLikeNum() + "");
            tvLike.setVisibility(View.VISIBLE);
        } else {
            tvLike.setVisibility(View.INVISIBLE);
        }
        helper.addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.tv_edit_comment);
        ImageView ivLike = helper.getView(R.id.iv_like);
        TextView ivVip = helper.getView(R.id.iv_vip);
        ivLike.setImageResource("0".equals(item.getState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
        ivVip.setText("已坚持"+item.getClockDay()+"天");
        ThemeNotesCommentAdapter themeMainCommentAdapter;
        ThemeMainImageAdapter themeMainImageAdapter = null;
        RecyclerView recycleImageCourse = helper.getView(R.id.recycle_image_course);
        List<String> imagelist = new ArrayList<>();
        imagelist = Arrays.asList(item.getDiaryImg().split(","));
        GridLayoutManager gridLayoutManager;
        if (item.getDiaryImg().equals("")) {
            recycleImageCourse.setVisibility(View.GONE);
        } else {
            recycleImageCourse.setVisibility(View.VISIBLE);
        }
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

        RecyclerView recycleCircleCourse = helper.getView(R.id.recycle_circle_course);
        themeMainCommentAdapter = new ThemeNotesCommentAdapter(item.getDiaryCommentVos().size() > 3 ? item.getDiaryCommentVos().subList(0, 3) : item.getDiaryCommentVos());
        if (item.getDiaryCommentVos().size() == 0 || item.getDiaryCommentVos() == null) {
            recycleCircleCourse.setVisibility(View.GONE);
        } else {
            recycleCircleCourse.setVisibility(View.VISIBLE);
        }
        recycleCircleCourse.setLayoutManager(new LinearLayoutManager(mContext));
        recycleCircleCourse.setAdapter(themeMainCommentAdapter);
    }
}
