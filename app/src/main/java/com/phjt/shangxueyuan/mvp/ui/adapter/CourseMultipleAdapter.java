package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/6/2 14:10
 * company: 普华集团
 * description: 描述
 */
public class CourseMultipleAdapter extends BaseQuickAdapter<TCMultipleBean, BaseViewHolder> {

    public CourseMultipleAdapter(@Nullable List<TCMultipleBean> data) {
        super(R.layout.item_course_multiple, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TCMultipleBean item) {
        TextView tvMultiple = helper.getView(R.id.tv_multiple_num_item);
        tvMultiple.setText(String.format("%sX", item.getSpeedLevel()));
        tvMultiple.setSelected(item.isSelect());
    }
}
