package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseChannelBean;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyViewPagerAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TimeUtils;

import java.util.List;

public class StudyMultiCardView extends LinearLayout {


    private TextView tvCourseTypeName;
    private TextView tvProgress;
    private LinearLayout llGoToStudy;
    private Context mContext;
    private CustomViewpager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private int selectPosition;

    public StudyMultiCardView(Context context) {
        super(context);
        initView(context);
    }

    public StudyMultiCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StudyMultiCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        View.inflate(context, R.layout.view_multy_study_card, this);
        llGoToStudy = findViewById(R.id.ll_go_study);
        tvCourseTypeName = findViewById(R.id.tv_study_type);
        tvProgress = findViewById(R.id.tv_study_progress);
        mViewPager = findViewById(R.id.vp_study);
        mSlidingTabLayout = findViewById(R.id.stl_study);
    }

    public void setData(Context context, List<CourseChannelBean> data, CourseTypeBean currentTypeBean) {
        tvCourseTypeName.setText(currentTypeBean.getName());
        mViewPager.setAdapter(new MyViewPagerAdapter(context, data, mViewPager));

        mSlidingTabLayout.setViewPager(mViewPager, listToArray(data));
        llGoToStudy.setOnClickListener(null);
        llGoToStudy.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CourseCategoryActivity.class);
            intent.putExtra(Constant.COURSE_TYPE_SECOND_ID, data.get(selectPosition).getId());
            intent.putExtra(Constant.COURSE_TYPE_ID, currentTypeBean.getId());
            mContext.startActivity(intent);
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.resetHeight(position);
                if (position < data.size()) {
                    changeProgress(data, position);
                    selectPosition = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(selectPosition);
        mViewPager.resetHeight(selectPosition);
        changeProgress(data, selectPosition);
        mSlidingTabLayout.updateTabSelection(selectPosition);
    }

    private void changeProgress(List<CourseChannelBean> data, int position) {
        if (data.get(position).getSumTimeLong() > 0) {
            String progress = TimeUtils.getStudyLook(data.get(position).getCouTypeWatchDuration(),data.get(position).getSumTimeLong());
            tvProgress.setText(data.get(position).getName() + "学习进度" + progress + "%");
        } else {
            tvProgress.setText(data.get(position).getName() + "学习进度0%");
        }
    }

    private String[] listToArray(List<CourseChannelBean> mTitles) {
        String[] array = new String[mTitles.size()];
        for (int i = 0; i < mTitles.size(); i++) {
            array[i] = mTitles.get(i).getName();
        }
        return array;
    }

}
