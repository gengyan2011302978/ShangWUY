package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.fragment.CircleFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.VipUtil;
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
public class ThemeMainAdapter extends BaseQuickAdapter<ThemeMainBean.RecordsBean, BaseViewHolder> {

    public ThemeMainAdapter(@Nullable List<ThemeMainBean.RecordsBean> data) {
        super(R.layout.item_dynamic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeMainBean.RecordsBean item) {
        ThemeMainCommentAdapter themeMainCommentAdapter;
        ThemeMainImageAdapter themeMainImageAdapter = null;
        RoundedImageView ivIcon = helper.getView(R.id.iv_mine_head_pic);
        AppImageLoader.loadResUrl(item.getPhoto(), ivIcon);
        TextView tvName = helper.getView(R.id.tv_name);
        ExpandableTextView tvComment = helper.getView(R.id.tv_comment);
        TextView className = helper.getView(R.id.class_name);
        TextView classDesc = helper.getView(R.id.class_desc);
        TextView tvLike = helper.getView(R.id.tv_like);
        TextView tvMyNotesReply = helper.getView(R.id.tv_my_notes_reply);
        TextView tvSpecialTitle = helper.getView(R.id.tv_specialTitle);
        TextView tvCommentTitle = helper.getView(R.id.tv_comment_title);
        TextView tvPointName = helper.getView(R.id.tv_pointName);
        TextView tvTopicName = helper.getView(R.id.tv_topicName);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvIsTop = helper.getView(R.id.tv_isTop);
        LinearLayout linearClass = helper.getView(R.id.linear_class);
        LinearLayout linearNote = helper.getView(R.id.linear_note);
        LinearLayout linearTopic = helper.getView(R.id.linear_topic);
        ImageView ivVip = helper.getView(R.id.iv_vip);
        ImageView circleDynamicImage = helper.getView(R.id.circle_dynamic_image);
        RecyclerView recycleCircleCourse = helper.getView(R.id.recycle_circle_course);
        ImageView ivLike = helper.getView(R.id.iv_like);
        ImageView classImage = helper.getView(R.id.class_image);
        AppImageLoader.loadResUrl(item.getCoverImg(), classImage);

        helper.addOnClickListener(R.id.iv_like)
                .addOnClickListener(R.id.tv_like)
                .addOnClickListener(R.id.iv_notes_reply)
                .addOnClickListener(R.id.tv_share)
                .addOnClickListener(R.id.iv_share)
                .addOnClickListener(R.id.linear_note)
                .addOnClickListener(R.id.linear_topic)
                .addOnClickListener(R.id.linear_class)
                .addOnClickListener(R.id.relat_comment)
                .addOnClickListener(R.id.tv_specialTitle)
                .addOnClickListener(R.id.recycle_circle_course)
                .addOnClickListener(R.id.tv_my_notes_reply)
                .addOnClickListener(R.id.iv_mine_head_pic)
                .addOnClickListener(R.id.tv_name)
                .addOnClickListener(R.id.iv_vip);

        RecyclerView recycleImageCourse = helper.getView(R.id.recycle_image_course);

        List<String> imagelist = new ArrayList<>();
        if ("4".equals(item.getThemeSource())) {
            circleDynamicImage.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getThemeImg())) {
                imagelist = Arrays.asList(item.getThemeImg().split(","));
                if (imagelist.size() == 0) {
                    recycleImageCourse.setVisibility(View.GONE);
                } else {
                    recycleImageCourse.setVisibility(View.VISIBLE);
                }
            } else {
                recycleImageCourse.setVisibility(View.GONE);
            }
        }
        if ("3".equals(item.getThemeSource())) {
            circleDynamicImage.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getLeaveImg())) {
                imagelist = Arrays.asList(item.getLeaveImg().split(","));
                if (imagelist.size() == 0) {
                    recycleImageCourse.setVisibility(View.GONE);
                } else {
                    recycleImageCourse.setVisibility(View.VISIBLE);
                }
            } else {
                recycleImageCourse.setVisibility(View.GONE);
            }
        }
        if ("2".equals(item.getThemeSource())) {
            circleDynamicImage.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getNotesImg())) {
                imagelist = Arrays.asList(item.getNotesImg().split(","));
                if (imagelist.size() == 0) {
                    recycleImageCourse.setVisibility(View.GONE);
                } else {
                    recycleImageCourse.setVisibility(View.VISIBLE);
                }
            } else {
                recycleImageCourse.setVisibility(View.GONE);
            }
        }
        if ("1".equals(item.getThemeSource())) {
            if (!TextUtils.isEmpty(item.getCouImg())) {
                imagelist = Arrays.asList(item.getCouImg().split(","));
                if (imagelist.size() == 0) {
                    circleDynamicImage.setVisibility(View.GONE);
                    linearClass.setVisibility(View.VISIBLE);
                } else {
                    linearClass.setVisibility(View.GONE);
                    recycleImageCourse.setVisibility(View.VISIBLE);
                }
            } else {
                recycleImageCourse.setVisibility(View.GONE);
                circleDynamicImage.setVisibility(View.GONE);
                linearClass.setVisibility(View.GONE);
            }
        }

        if (imagelist.size() == 0) {
            circleDynamicImage.setVisibility(View.GONE);
        }
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

        themeMainCommentAdapter = new ThemeMainCommentAdapter(item.getThemeReplyList().size() > 3 ? item.getThemeReplyList().subList(0, 3) : item.getThemeReplyList());
        if (item.getThemeReplyList().size() == 0 || item.getThemeReplyList() == null) {
            tvCommentTitle.setVisibility(View.GONE);
            recycleCircleCourse.setVisibility(View.GONE);
        } else {
            tvCommentTitle.setVisibility(View.VISIBLE);
            recycleCircleCourse.setVisibility(View.VISIBLE);
        }
        recycleCircleCourse.setLayoutManager(new LinearLayoutManager(mContext));
        recycleCircleCourse.setAdapter(themeMainCommentAdapter);
        themeMainCommentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CircleFragment.themeId = item.getId() + "";
                CircleFragment.indexCircle = helper.getAdapterPosition();
                String webUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_DYNAMIC);
                Intent intent = new Intent(mContext, MyWebViewActivity.class);
                if ("1".equals(item.getThemeSource())) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + item.getCommentId() + "&typeId=1" + "&type=1" + "&themeId=" + item.getId());
                }
                if ("2".equals(item.getThemeSource())) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + item.getNotesId() + "&typeId=2" + "&courseId=" + item.getCouId() + "&themeId=" + item.getId() + "&type=1");
                }
                if ("3".equals(item.getThemeSource())) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + item.getLeaveId() + "&typeId=3" + "&type=1" + "&themeId=" + item.getId());
                }
                if ("4".equals(item.getThemeSource())) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + item.getId() + "&typeId=4" + "&type=1");
                }
                startActivity(intent);
            }
        });

        tvPointName.setText(item.getPointName());
        tvTopicName.setText(item.getTopicName());
        tvDate.setText(item.getCreateTime());
        tvSpecialTitle.setText(String.format("#%s", item.getSpecialTitle()));
        tvName.setText(item.getNickName());
        className.setText(item.getName());
        classDesc.setText(item.getCouDescribe());
        tvIsTop.setVisibility(item.getThemeState() == 1 ? View.VISIBLE : View.GONE);
        if ("4".equals(item.getThemeSource())) {
            tvComment.setContent(item.getThemeState() == 1 ? "          " + item.getThemeName() : item.getThemeName());
            linearClass.setVisibility(View.GONE);
            tvSpecialTitle.setVisibility(View.GONE);
            linearNote.setVisibility(View.GONE);
            if (null != item.getTopicName()) {
                linearTopic.setVisibility(View.VISIBLE);
            } else {
                linearTopic.setVisibility(View.GONE);
            }
            ivLike.setImageResource("0".equals(item.getThemeLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
            tvLike.setText(item.getThemeLikeNum() == 0 ? "赞" : item.getThemeLikeNum() + "");
            tvMyNotesReply.setText(String.valueOf(item.getThemeDynamicNum()));

        }
        if ("3".equals(item.getThemeSource())) {
            tvComment.setContent(item.getThemeState() == 1 ? "          " + item.getLeaveWordContent() : item.getLeaveWordContent());
            linearClass.setVisibility(View.GONE);
            tvSpecialTitle.setVisibility(View.VISIBLE);
            linearNote.setVisibility(View.GONE);
            linearTopic.setVisibility(View.GONE);
            ivLike.setImageResource("0".equals(item.getLeaveLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
            tvLike.setText("0".equals(item.getLeaveLikeNum()) ? "赞" : item.getLeaveLikeNum());
            tvMyNotesReply.setText(String.valueOf(item.getLeaveBackNum()));
        }
        if ("2".equals(item.getThemeSource())) {
            tvComment.setContent(item.getThemeState() == 1 ? "          " + item.getBackContent() : item.getBackContent());
            linearClass.setVisibility(View.GONE);
            tvSpecialTitle.setVisibility(View.GONE);
            linearNote.setVisibility(View.VISIBLE);
            linearTopic.setVisibility(View.GONE);
            ivLike.setImageResource("0".equals(item.getNotesLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
            tvLike.setText("0".equals(item.getNotesLikeNum()) ? "赞" : item.getNotesLikeNum());
            tvMyNotesReply.setText(String.valueOf(item.getNotesBackNum()));
        }
        if ("1".equals(item.getThemeSource())) {
            tvComment.setContent(item.getThemeState() == 1 ? "          " + item.getContent() : item.getContent());
            tvSpecialTitle.setVisibility(View.GONE);
            linearNote.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getCouImg())) {
                linearClass.setVisibility(View.GONE);
            } else {
                linearClass.setVisibility(View.VISIBLE);
            }

            linearTopic.setVisibility(View.GONE);
            ivLike.setImageResource("0".equals(item.getCouLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
            tvLike.setText("0".equals(item.getCouLikeNum()) ? "赞" : item.getCouLikeNum());
            tvMyNotesReply.setText(String.valueOf(item.getCouBackNum()));
        }

        //Vip icon展示
        VipUtil.setVipImage(item.getVipState(), ivVip);
    }
}
