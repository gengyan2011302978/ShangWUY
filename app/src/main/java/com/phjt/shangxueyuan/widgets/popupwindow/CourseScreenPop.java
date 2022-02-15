package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseTeacherAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseTypeAdapter;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/3/27 12:01
 * company: 普华集团
 * description: 课程刷新的PopWindow
 */
public class CourseScreenPop extends PopupWindow {

    private Context mContext;
    public static final int COURSE_TYPE = 1;
    public static final int COURSE_TEACHER = 2;
    public static final int COURSE_SORT = 3;

    @BindView(R.id.rv_vertical_pop)
    RecyclerView mRvVertical;
    @BindView(R.id.rv_teacher_horizontal)
    RecyclerView mRvTeacher;
    @BindView(R.id.teacher_group)
    Group mTeacherGroup;

    private int mType;
    private CourseTypeAdapter typeAdapter;
    private CourseTeacherAdapter teacherAdapter;

    public CourseScreenPop(Context context, int type) {
        super(context);
        this.mContext = context;
        this.mType = type;

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_course_screen, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent3));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvVertical.setLayoutManager(layoutManager);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mRvTeacher.setLayoutManager(gridLayoutManager);
    }

    /**
     * 课程列表 和 筛选 赋值
     */
    public void setCourseType(List<CourseTypeItemBean> itemBeanList) {
        typeAdapter = new CourseTypeAdapter(itemBeanList);
        typeAdapter.setNewData(itemBeanList);
        mRvVertical.setAdapter(typeAdapter);

        mRvVertical.setVisibility(View.VISIBLE);
        mTeacherGroup.setVisibility(View.GONE);

        typeAdapter.setOnItemClickListener((adapter, view, position) -> {
            CourseTypeItemBean itemBean = (CourseTypeItemBean) adapter.getData().get(position);
            if (screenChose != null) {
                if (mType == COURSE_TYPE) {
                    screenChose.getScreenData(itemBean.getId(), null, itemBean.getName());
                } else if (mType == COURSE_SORT) {
                    screenChose.getScreenData(null, itemBean.getId(), itemBean.getName());
                }
            }
            dismiss();
        });
    }

    /**
     * 讲师列表赋值
     */
    public void setCourseTeacher(List<CourseTeacherItemBean> teacherItemBeanList) {
        teacherAdapter = new CourseTeacherAdapter(mContext, teacherItemBeanList, mRvTeacher);
        teacherAdapter.setNewData(teacherItemBeanList);
        mRvTeacher.setAdapter(teacherAdapter);

        mRvVertical.setVisibility(View.GONE);
        mTeacherGroup.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_reset_pop, R.id.tv_sure_pop, R.id.view_bottom_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reset_pop:
                if (teacherAdapter != null) {
                    teacherAdapter.resetRv();
                }
                break;
            case R.id.tv_sure_pop:
                if (teacherAdapter != null && screenChose != null) {
                    CourseTeacherItemBean itemBean = teacherAdapter.getSelectId();
                    if (itemBean != null) {
                        screenChose.getScreenTeacher(itemBean.getId(), itemBean.getName());
                    } else {
                        screenChose.getScreenTeacher(null, "讲师");
                    }
                }
                dismiss();
                break;
            case R.id.view_bottom_pop:
                dismiss();
                break;
            default:
                break;
        }
    }

    public IScreenChose screenChose;

    public void setScreenChose(IScreenChose screenChose) {
        this.screenChose = screenChose;
    }

    public interface IScreenChose {

        //选择 课程列表 和 排序
        default void getScreenData(String typeId, String sort, String name) {

        }

        //选择 讲师
        default void getScreenTeacher(String teacherId, String name) {

        }
    }
}
