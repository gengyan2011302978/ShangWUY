package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseBean;
import com.phjt.shangxueyuan.bean.CourseChannelBean;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.widgets.CustomViewpager;

import java.util.List;

/**
 * @author: yuyang
 * date:    2020/03/27
 * company: 普华集团
 * description: ViewPager页面
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<CourseChannelBean> pagerList;
    private CustomViewpager customViewpager;

    public MyViewPagerAdapter(Context mContext, List<CourseChannelBean> pagerList, CustomViewpager customViewpager) {
        this.mContext = mContext;
        this.pagerList = pagerList;
        this.customViewpager = customViewpager;
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_study_viewpager, null);
        customViewpager.setObjectForPosition(view, position);
        if (pagerList.get(position).getCourseRecord().size() > 0) {
            RecyclerView rvStudy = view.findViewById(R.id.rv_study);
            rvStudy.setLayoutManager(new LinearLayoutManager(mContext));
            List<CourseBean> courseBeanList = pagerList.get(position).getCourseRecord();
            StudyCardAdapter mAdapter = new StudyCardAdapter(mContext, courseBeanList);
            rvStudy.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((adapter, view1, position1) -> {
                toCourseDetail(courseBeanList.get(position1));
            });
        } else {
            view.findViewById(R.id.ll_empty_view).setVisibility(View.VISIBLE);
        }
        container.addView(view);
        return view;
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