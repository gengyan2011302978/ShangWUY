package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.phjt.shangxueyuan.R;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseMultipleAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/6/2 14:07
 * company: 普华集团
 * description: 描述
 */
public class CourseMultipleVerticalDialog extends BaseCourseDialog {

    @BindView(R.id.rv_multiple)
    RecyclerView mRvMultiple;

    public CourseMultipleVerticalDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_course_multiple_vertical);
        ButterKnife.bind(this);

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 1);
        getWindow().setAttributes(lp);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvMultiple.setLayoutManager(layoutManager);
    }

    public void setData(List<TCMultipleBean> multipleBeanList) {
        CourseMultipleAdapter multipleAdapter = new CourseMultipleAdapter(multipleBeanList);
        mRvMultiple.setAdapter(multipleAdapter);

        multipleAdapter.setOnItemClickListener((adapter, view, position) -> {
            TCMultipleBean multipleBean = (TCMultipleBean) adapter.getData().get(position);
            if (mListener != null) {
                mListener.multipleChoseCallBack(multipleBean.getSpeedLevel());
            }
            dismiss();
        });
    }

    @OnClick(R.id.tv_multiple_cancel)
    public void click(View view) {
        dismiss();
    }
}
