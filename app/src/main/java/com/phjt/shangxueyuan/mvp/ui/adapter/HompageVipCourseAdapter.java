package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CouRecommendListBean;
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
public class HompageVipCourseAdapter extends BaseQuickAdapter<CouRecommendListBean, BaseViewHolder> {

    public HompageVipCourseAdapter(@Nullable List<CouRecommendListBean> data) {
        super(R.layout.item_homepage_course, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouRecommendListBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.iv_logo);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivIcon);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvDesc = helper.getView(R.id.tv_desc);
        TextView tvNumber = helper.getView(R.id.tv_number);
        TextView tvLabel = helper.getView(R.id.tv_label);
        TextView tvType = helper.getView(R.id.tv_course_type);
        TextView tvType1 = helper.getView(R.id.tv_course_typet);
        TextView tvTeacherName = helper.getView(R.id.tv_teacher_name);
        tvTitle.setText(item.getName());
        tvTeacherName.setText(item.getTeacherName());
        tvDesc.setText(item.getCouDesc());
        tvNumber.setText(String.format("%s人在学", Utils.numberFix(item.getStudyNum())));
        tvLabel.setText(item.getTypeName());
        if (TextUtils.equals(item.getFreeType(), "2")) {
            tvType.setText("VIP精选");
            tvType1.setText(item.getCoursewareName());
        }

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
