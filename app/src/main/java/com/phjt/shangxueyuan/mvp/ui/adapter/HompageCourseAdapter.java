package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.tools.ScreenUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseRecommendListBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.utils.crash.Utils;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class HompageCourseAdapter extends BaseQuickAdapter<CourseRecommendListBean, BaseViewHolder> {

    public HompageCourseAdapter(@Nullable List<CourseRecommendListBean> data) {
        super(R.layout.item_homepage_course, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseRecommendListBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDesc = helper.getView(R.id.tv_desc);
        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvType = helper.getView(R.id.tv_course_type);
        TextView tvType1 = helper.getView(R.id.tv_course_typet);
        tvTitle.setText(item.getName());
        tvDesc.setText(item.getCouDesc());
        tvNumber.setText(String.format("%s人在学", Utils.numberFix(item.getStudyNum())));

        if (TextUtils.equals(item.getFreeType(), "2")) {
            tvType.setBackgroundResource(R.drawable.bg_fd8b4b_rectangle);
            tvType.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFFFFF));
            tvType.setText("VIP专属");
            tvType1.setText(item.getCoursewareName());
        } else {
            tvType.setBackgroundResource(R.drawable.bg_5e8bff_rectangle);
            tvType.setTextColor(ContextCompat.getColor(mContext, R.color.colorFFFFFF));
//            if (9 == item.getType()) {
//                tvType.setText("直播回放");
//            } else {
//                tvType.setText("公开课");
//            }
            tvType.setText(item.getTypeName());
            tvType1.setText(item.getCoursewareName());
        }

        // 修改item宽高
        if (9 == item.getType() || 10 == item.getType()) {
            ViewGroup.LayoutParams params = helper.itemView.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(ScreenUtils.dip2px(mContext, 155), ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                params.width = ScreenUtils.dip2px(mContext, 155);
            }
            helper.itemView.setLayoutParams(params);
        }
    }
}
