package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:37
 * company: 普华集团
 * description: 描述
 */
public class CourseTypeAdapter extends BaseQuickAdapter<CourseTypeItemBean, BaseViewHolder> {

    public CourseTypeAdapter(@Nullable List<CourseTypeItemBean> data) {
        super(R.layout.item_course_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseTypeItemBean item) {
        TextView tvContent = helper.getView(R.id.tv_course_type);
        tvContent.setText(item.getName());
        tvContent.setSelected(item.isSelect());
    }
}
