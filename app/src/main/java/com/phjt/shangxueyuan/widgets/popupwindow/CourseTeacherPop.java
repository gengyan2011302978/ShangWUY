package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseTeacherPopAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2020/5/7 9:52
 * company: 普华集团
 * description: 课程讲师PopWindow
 */
public class CourseTeacherPop extends PopupWindow {

    @BindView(R.id.rv_teacher_pop)
    RecyclerView mRvPop;

    private Context mContext;
    private CourseTeacherPopAdapter teacherPopAdapter;

    public CourseTeacherPop(Context context) {
        super(context);
        this.mContext = context;

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_course_teacher, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(AutoSizeUtils.dp2px(mContext, 197f));
        // 设置PopupWindow弹出窗体的高
        this.setHeight(AutoSizeUtils.dp2px(mContext, 228f));
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvPop.setLayoutManager(layoutManager);
    }

    /**
     * 讲师列表赋值
     */
    public void setCourseTeacher(List<CourseTeacherItemBean> teacherItemBeanList) {
        teacherPopAdapter = new CourseTeacherPopAdapter(mContext, teacherItemBeanList, mRvPop);
        mRvPop.setAdapter(teacherPopAdapter);

        teacherPopAdapter.setOnItemClickListener((adapter, view, position) -> {
            CourseTeacherItemBean itemBean = (CourseTeacherItemBean) adapter.getData().get(position);
            if (itemBean != null && teacherPop != null) {
                teacherPop.teacherSelect(itemBean.getId(), itemBean.getName());
            }
            dismiss();
        });
    }

    public ICourseTeacherPop teacherPop;

    public void setTeacherPop(ICourseTeacherPop teacherPop) {
        this.teacherPop = teacherPop;
    }

    public interface ICourseTeacherPop {
        void teacherSelect(String id, String name);
    }
}
