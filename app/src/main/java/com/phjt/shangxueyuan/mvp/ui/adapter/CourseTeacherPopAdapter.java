package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/5/7 10:06
 * company: 普华集团
 * description: 描述
 */
public class CourseTeacherPopAdapter extends BaseQuickAdapter<CourseTeacherItemBean, BaseViewHolder> {

    private RecyclerView mRecycleView;
    private Context mContext;

    public CourseTeacherPopAdapter(Context context, @Nullable List<CourseTeacherItemBean> data, RecyclerView recyclerView) {
        super(R.layout.item_course_teacher_pop, data);
        this.mRecycleView = recyclerView;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseTeacherItemBean item) {
        helper.setText(R.id.tv_teacher_name_item, item.getName());
        TextView tvName = helper.getView(R.id.tv_teacher_name_item);
        ImageView ivArrow = helper.getView(R.id.iv_arrow_item);
        setSelectState(item.isSelect(), tvName, ivArrow);

//        setOnItemClickListener((adapter, view, position) -> {
//            resetRv();
//            item.setSelect(true);
//            setSelectState(true, tvName, ivArrow);
//        });
    }

    public void resetRv() {
        for (int i = 0; i < mRecycleView.getChildCount(); i++) {
            ConstraintLayout layout = (ConstraintLayout) mRecycleView.getChildAt(i);
            TextView tvName = layout.findViewById(R.id.tv_teacher_name_item);
            ImageView ivArrow = layout.findViewById(R.id.iv_arrow_item);
            setSelectState(false, tvName, ivArrow);
            getData().get(i).setSelect(false);
        }
    }

    public void setSelectState(boolean isSelect, TextView tvName, ImageView ivArrow) {
        tvName.setSelected(isSelect);
        ivArrow.setVisibility(isSelect ? View.VISIBLE : View.GONE);
    }

}
