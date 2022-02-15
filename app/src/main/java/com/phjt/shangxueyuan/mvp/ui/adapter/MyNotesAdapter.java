package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
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

import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author: Roy
 * date:   2020/6/1
 * company: 普华集团
 * description:我的笔记
 */
public class MyNotesAdapter extends BaseQuickAdapter<MyNotesBean, BaseViewHolder> {

    private Context mContext;
    private int mType;

    public MyNotesAdapter(Context context, List<MyNotesBean> myNotesBeans, int type) {
        super(R.layout.item_my_notes, myNotesBeans);
        this.mContext = context;
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyNotesBean item) {
        helper.setText(R.id.tv_my_notes_conceal, item.getOpenState() == 1 ? "【公开】" : "【私密】")
                .setText(R.id.tv_my_notes_reply, item.getBackNum() > 999 ? "999+" : item.getBackNum() + "")
                .setText(R.id.tv_designation, item.getNickName())
                .setText(R.id.tv_like, "赞")
                .setText(R.id.tv_time, TextUtils.isEmpty(item.getCreatTime()) ? "" : item.getCreatTime())
                .setText(R.id.tv_my_notes_address, TextUtils.isEmpty(item.getPointName()) ? " " : "于  " + item.getPointName());

        if (0 != item.getNotesLikeNum()) {
            helper.setText(R.id.tv_like, item.getNotesLikeNum() > 999 ? "999+" : String.valueOf(item.getNotesLikeNum()));
        } else {
            helper.setText(R.id.tv_like, "赞");
        }

        //vip状态展示
        ImageView ivVip = helper.getView(R.id.iv_vip_item);
        VipUtil.setVipImage(item.getVipState(), ivVip);

        //初始化默认值
        helper.getView(R.id.tv_time).setVisibility(View.GONE);
        helper.getView(R.id.tv_like).setVisibility(View.GONE);
        helper.getView(R.id.iv_like).setVisibility(View.GONE);
        helper.getView(R.id.iv_notes_reply).setVisibility(View.GONE);
        helper.getView(R.id.tv_my_notes_reply).setVisibility(View.GONE);
        helper.getView(R.id.tv_share).setVisibility(View.GONE);
        helper.getView(R.id.view_reply).setVisibility(View.INVISIBLE);

        // mType = 0:课程——我的笔记,  课程——1:所有笔记 ，  2我的——我的笔记
        helper.getView(R.id.iv_re_head_pic).setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_designation).setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.iv_vip_item).setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_my_notes_conceal).setVisibility(mType == 1 ? View.GONE : View.VISIBLE);
//        helper.setText(R.id.tv_my_notes_content, mType == 1 ? item.getNoteContent() : "\u3000\u3000\u3000\u3000" + item.getNoteContent());
        helper.getView(R.id.tv_time).setVisibility(mType == 2 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.tv_editor).setVisibility(item.getEditState() == 0 ? View.INVISIBLE : View.VISIBLE);

//        TextView tvClickContent = helper.getView(R.id.tv_click_content);
        TextView tvEditor = helper.getView(R.id.tv_editor);
        ImageView ivLike = helper.getView(R.id.iv_like);
        ivLike.setImageResource(item.getLikeState() == 0 ? R.drawable.ic_like_un : R.drawable.ic_like);

        //点赞按钮设置
        setAudit(helper, item.getOpenState(), item.getAuditState());
        RoundedImageView ivReHeadPic = helper.getView(R.id.iv_re_head_pic);
        RecyclerView rcyReleased = helper.getView(R.id.rcy_released);
        int failImage = R.drawable.iv_mine_avatar;
        ivReHeadPic.setImageResource(failImage);
        GlideUtils.load(item.getPhoto(), ivReHeadPic, failImage);
        TextView tvReply = helper.getView(R.id.tv_auditing);
        TextView tvUpState = helper.getView(R.id.tv_my_notes_up_state);
        View clUpState = helper.getView(R.id.gray_view);
        tvReply.setVisibility(View.GONE);

        ExpandableTextView tvContent = helper.getView(R.id.tv_my_notes_content);
        tvContent.setContent(mType == 1 ? item.getNoteContent() : "\u3000\u3000\u3000\u3000" + item.getNoteContent());
//        tvContent.isClick(true);
        setClick(helper);

        Drawable drawable = mContext.getDrawable(R.drawable.ic_edit);
        drawable.setBounds(0, 0, 35, 35);
        tvEditor.setCompoundDrawables(null, null, drawable, null);

        setState(item.getUpState(), item.getAuditState(), tvUpState, clUpState, tvReply);
        if (!TextUtils.isEmpty(item.getNotesImg())) {
            setReleased(rcyReleased, item.getNotesImg(), item.getUpState());
        } else {
            setEmpty(rcyReleased);
        }
    }

    /**
     * 点赞按钮设置
     *
     * @param helper
     * @param penState   1 公开
     * @param auditState 1 审核通过
     */
    private void setAudit(BaseViewHolder helper, int penState, int auditState) {
        if (penState == 1 && auditState == 1) {
            helper.getView(R.id.tv_like).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_like).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_my_notes_reply).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_notes_reply).setVisibility(View.VISIBLE);
            helper.getView(R.id.view_reply).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_share).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击事件
     *
     * @param helper
     */
    private void setClick(BaseViewHolder helper) {
        helper.addOnClickListener(R.id.tv_my_notes_address);
        helper.addOnClickListener(R.id.iv_like);
        helper.addOnClickListener(R.id.tv_like);
        helper.addOnClickListener(R.id.tv_editor);
        helper.addOnClickListener(R.id.tv_my_notes_conceal);
        helper.addOnClickListener(R.id.tv_my_notes_content);
        helper.addOnClickListener(R.id.tv_my_notes_content);
        helper.addOnClickListener(R.id.tv_share);
        helper.addOnClickListener(R.id.iv_re_head_pic);
        helper.addOnClickListener(R.id.tv_designation);
        helper.addOnClickListener(R.id.iv_vip_item);
    }

    /**
     * s设置图像
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

    /**
     * 设置布局
     *
     * @param state
     * @param auditState
     * @param tvUpState
     * @param clUpState
     * @param tvReply
     */
    private void setState(int state, int auditState, TextView tvUpState, View clUpState, TextView tvReply) {
        if (state == 0) {
            tvUpState.setVisibility(View.VISIBLE);
            tvUpState.setText("课程已下架");
            tvUpState.setTextColor(Color.WHITE);
            tvUpState.getPaint().setFakeBoldText(true);
            tvUpState.setBackgroundResource(R.drawable.tv_reply_backgrounds);
            tvUpState.setPadding(20, 5, 20, 5);
            clUpState.setVisibility(View.VISIBLE);
        } else {
            tvUpState.setVisibility(View.GONE);
            clUpState.setVisibility(View.GONE);
            //  auditState :-1审核未通过 ；0审核中 ；1审核通过
            if (auditState == 0) {
                tvReply.setVisibility(View.VISIBLE);
                tvReply.setText("待审核");
                tvReply.setTextColor(Color.WHITE);
                tvReply.getPaint().setFakeBoldText(true);
                tvReply.setBackgroundResource(R.drawable.tv_reply_background);
                tvReply.setPadding(20, 5, 20, 5);

            } else if (auditState == -1) {
                tvReply.setText("审核未通过");
                tvReply.setTextColor(Color.WHITE);
                tvReply.getPaint().setFakeBoldText(true);
                tvReply.setBackgroundResource(R.drawable.tv_reply_background);
                tvReply.setPadding(20, 5, 20, 5);
                tvReply.setVisibility(View.VISIBLE);
            } else {
                tvReply.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置问答图片
     */
    private void setReleased(RecyclerView rcyReleased, String notesImg, int upState) {
        List<String> imgList = Arrays.asList(notesImg.split(","));
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        rcyReleased.setLayoutManager(layoutManager);
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rcyReleased.setAdapter(mAdapter);
        mAdapter.setNewData(imgList);
        if (upState != 0) {
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(mContext, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(imgList));
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            });
        }
    }

}
