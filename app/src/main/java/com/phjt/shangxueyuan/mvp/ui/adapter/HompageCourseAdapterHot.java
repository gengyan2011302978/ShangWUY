package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.HomeHotRecommendListBean;
import com.phjt.shangxueyuan.utils.crash.Utils;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class HompageCourseAdapterHot extends BaseQuickAdapter<HomeHotRecommendListBean, BaseViewHolder> {

    public HompageCourseAdapterHot(@Nullable List<HomeHotRecommendListBean> data) {
        super(R.layout.item_homepage_course_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeHotRecommendListBean item) {
        if (null==item){
            return;
        }
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDesc = helper.getView(R.id.tv_desc);
        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvType = helper.getView(R.id.tv_course_type);
        TextView tvType1 = helper.getView(R.id.tv_course_typet);
        TextView tvHotDesc = helper.getView(R.id.tv_hot_desc);
        TextView tvHotTitle = helper.getView(R.id.tv_hot_title);
        ImageView ivWeekNew = helper.getView(R.id.iv_week_new);
        ImageView ivHotBg = helper.getView(R.id.iv_hot_bg);
        tvTitle.setText(TextUtils.isEmpty(item.getName()) ? "" : item.getName());
        tvNumber.setText(String.format("%s人在学", Utils.numberFix(String.valueOf(item.getStudyNum()))));
        tvHotTitle.setText("TOP " + (helper.getAdapterPosition() + 1));

        if (helper.getAdapterPosition() == 0) {
            ivWeekNew.setVisibility(View.VISIBLE);
            tvHotDesc.setText("本周上新");
            tvHotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_D2733D));
            tvHotDesc.setTextColor(ContextCompat.getColor(mContext, R.color.color_D2733D));
            ivHotBg.setBackgroundResource(R.drawable.hot_fist_bg);
        } else {
            ivWeekNew.setVisibility(View.GONE);
        }
        if (helper.getAdapterPosition() == 1) {
            tvHotDesc.setText("商科精品");
            tvHotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_648EB3));
            tvHotDesc.setTextColor(ContextCompat.getColor(mContext, R.color.color_648EB3));
            ivHotBg.setBackgroundResource(R.drawable.hot_second_bg);
        }
        if (helper.getAdapterPosition() == 2) {
            tvHotDesc.setText("财商文化");
            tvHotTitle.setTextColor(ContextCompat.getColor(mContext, R.color.color_974304));
            tvHotDesc.setTextColor(ContextCompat.getColor(mContext, R.color.color_974304));
            ivHotBg.setBackgroundResource(R.drawable.hot_third_bg);
        }
//        if (TextUtils.equals(item.getFreeType(), "2")) {
//            tvType.setBackgroundResource(R.drawable.bg_fd8b4b_rectangle);
//            tvType.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFFFFF));
        tvType.setText(TextUtils.isEmpty(item.getTypeName()) ? "" : item.getTypeName());
        tvType1.setText(TextUtils.isEmpty(item.getCoursewareName()) ? "" : item.getCoursewareName());
//        } else {
//            tvType.setBackgroundResource(R.drawable.bg_5e8bff_rectangle);
//            tvType.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFFFFF));
////            if (9 == item.getType()) {
////                tvType.setText("直播回放");
////            } else {
////                tvType.setText("公开课");
////            }
//            tvType.setText(item.getTypeName());
//            tvType1.setText(item.getCoursewareName());
//        }

        // 修改item宽高
//        if (9 == item.getType() || 10 == item.getType()) {
//            ViewGroup.LayoutParams params = helper.itemView.getLayoutParams();
//            if (params == null) {
//                params = new ViewGroup.LayoutParams(ScreenUtils.dip2px(mContext, 155), ViewGroup.LayoutParams.WRAP_CONTENT);
//            } else {
//                params.width = ScreenUtils.dip2px(mContext, 155);
//            }
//            helper.itemView.setLayoutParams(params);
//        }
    }
}
