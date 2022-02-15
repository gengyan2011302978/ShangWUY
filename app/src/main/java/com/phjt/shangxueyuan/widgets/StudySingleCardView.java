package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseBean;
import com.phjt.shangxueyuan.bean.OrdinaryCourseBean;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyColumnActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.StudyCardAdapter;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class StudySingleCardView extends LinearLayout {


    private TextView tvStudyType;
    private RecyclerView rvStudy;
    private StudyCardAdapter mAdapter;
    private Context mContext;
    private LinearLayout emptyView;
    private LinearLayout llGoToStudy;
    private LinearLayout llStudy;
    List<CourseBean> data;

    public StudySingleCardView(Context context) {
        super(context);
        initView(context);
    }

    public StudySingleCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StudySingleCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_single_study_card, this);
        llGoToStudy = findViewById(R.id.ll_go_study);
        llStudy = findViewById(R.id.ll_study);
        tvStudyType = findViewById(R.id.tv_study_type);
        emptyView = findViewById(R.id.ll_empty_view);
        rvStudy = findViewById(R.id.rv_study);
        rvStudy.setLayoutManager(new LinearLayoutManager(context));
        data = new ArrayList<>();
        this.mContext = context;
    }

    public void setData(OrdinaryCourseBean bean) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.clear();
        data = bean.getRecordList();
        if (null!=data){
            if (data.size() > 0) {
                mAdapter = new StudyCardAdapter(mContext, data);
                rvStudy.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    toCourseDetail(bean.getRecordList().get(position));
                });
            } else {
                if ("0".equals(String.valueOf(bean.getId()))) {
                    llStudy.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(VISIBLE);
                }
            }
        }
        tvStudyType.setText(bean.getName());
        llGoToStudy.setOnClickListener(null);
        llGoToStudy.setOnClickListener(v -> {
            if ("0".equals(String.valueOf(bean.getId()))) {
                Intent intent = new Intent(mContext, MyColumnActivity.class);
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, String.valueOf(bean.getId()));
                mContext.startActivity(intent);
            }

        });
    }

    private void toCourseDetail(CourseBean courseBean) {
        if (courseBean.getUpState() != 0) {
            Intent intent = new Intent(mContext, CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, courseBean.getId());
            intent.putExtra(Constant.BUNDLE_COURSE_NAME, courseBean.getName());
            mContext.startActivity(intent);
        }
    }
}
