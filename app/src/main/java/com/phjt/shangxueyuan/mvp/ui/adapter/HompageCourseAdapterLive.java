package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CouRecommendListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.utils.crash.Utils;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

import androidx.annotation.Nullable;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class HompageCourseAdapterLive extends BaseQuickAdapter<CouRecommendListBean, BaseViewHolder> {

    public HompageCourseAdapterLive(@Nullable List<CouRecommendListBean> data) {
        super(R.layout.item_homepage_course_live, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouRecommendListBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        RoundedImageView ivAuditionItem = helper.getView(R.id.iv_audition_item);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDesc = helper.getView(R.id.tv_desc);
        RelativeLayout relat_couse = helper.getView(R.id.relat_couse);
        LinearLayout linearLiving = helper.getView(R.id.linear_living);

        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvTime = helper.getView(R.id.tv_course_time);
        TextView tvType = helper.getView(R.id.tv_course_type);
        TextView tvType1 = helper.getView(R.id.tv_course_typet);
        tvType.setText(item.getTypeName());
        tvType1.setText(item.getCoursewareName());
        TextView tvLook = helper.getView(R.id.tv_course_look);
        TextView tvLook1 = helper.getView(R.id.tv_course_look1);
        tvTitle.setText(item.getName());
        tvNumber.setText(item.getTeacherName());
        TextView tvStatus = helper.getView(R.id.tv_status);
        tvStatus.setBackgroundResource(R.drawable.bg_ffb30c_rectangle);
        switch (item.getStatus()) {
            case 0:
                tvStatus.setText("预告");
                linearLiving.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 3:
                ImageView ivUndulate = helper.getView(R.id.iv_undulate);
                AnimationDrawable animationDrawable = (AnimationDrawable) ivUndulate.getDrawable();
                //开启帧动画
                animationDrawable.start();
                linearLiving.setVisibility(View.VISIBLE);
                tvStatus.setVisibility(View.GONE);
                break;
            case 2:
                tvStatus.setText("回放");
                linearLiving.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvStatus.setText("直播生成回放中");
                linearLiving.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }


        if (TextUtils.isEmpty(item.getCouId())) {
            relat_couse.setVisibility(View.GONE);
            ivIcon.setVisibility(View.GONE);
            AppImageLoader.loadResUrl(item.getCoverImg(), ivAuditionItem);
        } else {
            tvStatus.setVisibility(View.GONE);
            ivAuditionItem.setVisibility(View.GONE);
            relat_couse.setVisibility(View.VISIBLE);
            AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        }


        tvTime.setText(item.getLiveStartTime());
        tvLook.setText(Utils.numberFix(item.getPopular()));
        tvLook1.setText(String.format("%s人在学", Utils.numberFix(item.getStudyNum())));
    }
}
