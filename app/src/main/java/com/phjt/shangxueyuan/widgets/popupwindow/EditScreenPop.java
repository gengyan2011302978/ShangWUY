package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseTypeItemBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.EditAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Roy
 * date:   2021/1/16
 * company: 普华集团
 * description:删除
 */
public class EditScreenPop extends PopupWindow {

    private Context mContext;
    @BindView(R.id.rv_vertical_pop)
    RecyclerView mRvVertical;
    public IEditScreenPop teacherPop;



    public EditScreenPop(Context context) {
        super(context);
        this.mContext = context;

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_edit_screen, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent9));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvVertical.setLayoutManager(layoutManager);
        List<CourseTypeItemBean> itemBeanList = new ArrayList<>();
        CourseTypeItemBean bean = new CourseTypeItemBean("1", "编辑");
        CourseTypeItemBean bean2 = new CourseTypeItemBean("2", "删除");

        itemBeanList.add(bean);
        itemBeanList.add(bean2);
        EditAdapter mAdapter = new EditAdapter();
        mAdapter.setNewData(itemBeanList);
        mRvVertical.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CourseTypeItemBean> data = adapter.getData();
                teacherPop.getScreen(data.get(position).getId(),data.get(position).getName());
                dismiss();
            }
        });
    }



    public void setTeacherPop(IEditScreenPop teacherPop) {
        this.teacherPop = teacherPop;
    }

    public interface IEditScreenPop {

        default void getScreen(String teacherId, String name) {

        }
    }

}
