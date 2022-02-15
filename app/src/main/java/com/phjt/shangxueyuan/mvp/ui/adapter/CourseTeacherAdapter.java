package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.view.roundView.RoundTextView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 13:50
 * company: 普华集团
 * description: 描述
 */
public class CourseTeacherAdapter extends BaseQuickAdapter<CourseTeacherItemBean, BaseViewHolder> {

    private RecyclerView mRecycleView;
    private Context mContext;

    public CourseTeacherAdapter(Context context, @Nullable List<CourseTeacherItemBean> data, RecyclerView recyclerView) {
        super(R.layout.item_course_teacher, data);
        this.mRecycleView = recyclerView;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseTeacherItemBean item) {
        helper.setText(R.id.tv_course_teacher_name_item, item.getName());
        RoundTextView tvName = helper.getView(R.id.tv_course_teacher_name_item);

        if (item.isSelect()) {
            setTextViewSelect(tvName);
        } else {
            setTextViewNormal(tvName);
        }

        tvName.setOnClickListener(v -> {
            resetRv();
            item.setSelect(true);
            setTextViewSelect(tvName);
        });
    }

    /**
     * 获取别选择的实体
     *
     * @return 选择的老师实体
     */
    public CourseTeacherItemBean getSelectId() {
        CourseTeacherItemBean itemBean = null;
        List<CourseTeacherItemBean> teacherItemBeans = getData();
        for (int i = 0; i < teacherItemBeans.size(); i++) {
            CourseTeacherItemBean courseTeacherItemBean = teacherItemBeans.get(i);
            if (courseTeacherItemBean.isSelect()) {
                itemBean = courseTeacherItemBean;
            }
        }
        return itemBean;
    }

    public void resetRv() {
        for (int i = 0; i < mRecycleView.getChildCount(); i++) {
            ConstraintLayout layout = (ConstraintLayout) mRecycleView.getChildAt(i);
            RoundTextView tvName = layout.findViewById(R.id.tv_course_teacher_name_item);
            setTextViewNormal(tvName);
            getData().get(i).setSelect(false);
        }
    }

    public void setTextViewNormal(RoundTextView textView) {
        textView.getDelegate().setBackgroundColor( ContextCompat.getColor(mContext,R.color.color_F5F5F5));
        textView.setTextColor( ContextCompat.getColor(mContext,R.color.color_666666));
    }

    public void setTextViewSelect(RoundTextView textView) {
        textView.getDelegate().setBackgroundColor( ContextCompat.getColor(mContext,R.color.color_D0DBFC));
        textView.setTextColor( ContextCompat.getColor(mContext,R.color.color_4071FC));
    }
}
