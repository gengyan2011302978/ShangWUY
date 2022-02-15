package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;

/**
 * @author: Roy
 * date:   2021/1/16
 * company: 普华集团
 * description:
 */
public class EditAdapter extends BaseQuickAdapter<CourseTypeItemBean, BaseViewHolder> {

    public EditAdapter() {
        super(R.layout.item_course_edit);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseTypeItemBean item) {
        TextView tvContent = helper.getView(R.id.tv_course_type);
        tvContent.setText(item.getName());
    }
}
