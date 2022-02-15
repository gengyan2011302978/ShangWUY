package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;
import com.phjt.shangxueyuan.bean.DescBean;
import com.phjt.shangxueyuan.di.component.DaggerCourseClassifyComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseClassifyContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseClassifyPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseClassifyAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 17:36
 * company: 普华集团
 * description: 初/中/高级课程
 */
public class CourseClassifyActivity extends BaseActivity<CourseClassifyPresenter> implements CourseClassifyContract.View, OnRefreshLoadMoreListener {
    public static final String COURSE_CLASSIFY_TYPE = "CourseClassifyType";

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_course_classify)
    RecyclerView rvCourseClassify;
    @BindView(R.id.srl_course_classify)
    SmartRefreshLayout srlCourseClassify;

    /**
     * type 课程类别: 0初级/1中级/2高级
     */
    private int type;
    private int mTypeValue=2;
    private CourseClassifyAdapter mAdapter;
    private TextView tvCourseClassify;
    private TextView tvCourseClassifyContent;
    private FrameLayout flHeader;
    private View headerView, footerView;
    private String couTypeIds;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCourseClassifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_course_classify;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra(CourseClassifyActivity.COURSE_CLASSIFY_TYPE, 0);
        couTypeIds = getIntent().getStringExtra(Constant.COURSE_TYPE_ID) + "";
        headerView = View.inflate(this, R.layout.item_course_classify_list_header, null);
        footerView = View.inflate(this, R.layout.item_course_classify_list_footer, null);
        flHeader = headerView.findViewById(R.id.fl_header);
        tvCourseClassifyContent = headerView.findViewById(R.id.tv_boc_course_classify_content);
        tvCourseClassify = headerView.findViewById(R.id.tv_boc_course_classify);
        srlCourseClassify.setOnRefreshLoadMoreListener(this);
        setView();
        initRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        srlCourseClassify.autoRefresh();
    }

    /**
     * 头部添加背景
     */
    private void setView() {
        srlCourseClassify.setEnableLoadMore(false);
        if (type == 0) {
            flHeader.setBackgroundResource(R.drawable.img_elementary);
            tvCourseClassify.setTextColor(ContextCompat.getColor(this, R.color.color_B85F1C));
            tvCourseClassify.setBackgroundResource(R.drawable.shape_bg_learning);
        } else if (type == 1) {
            flHeader.setBackgroundResource(R.drawable.img_middle_ranking);
            tvCourseClassify.setTextColor(ContextCompat.getColor(this, R.color.color_0D2684));
            tvCourseClassify.setBackgroundResource(R.drawable.shape_bg_middle_ranking);
        } else if (type == mTypeValue) {
            flHeader.setBackgroundResource(R.drawable.img_high_ranking);
            tvCourseClassify.setTextColor(ContextCompat.getColor(this, R.color.color_580775));
            tvCourseClassify.setBackgroundResource(R.drawable.shape_bg_high_ranking);
        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCourseClassify.setLayoutManager(layoutManager);
        mAdapter = new CourseClassifyAdapter(this);
        rvCourseClassify.setAdapter(mAdapter);
        mAdapter.setType(type);

        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);


        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<CourseClassifyBean.ChildListBean> data = adapter.getData();
            switch (view.getId()) {
                case R.id.tv_continue_watch:
                    //继续观看
                    CourseClassifyBean.LatestWatch latestWatch = data.get(position).getLatestWatch();
                    if (latestWatch != null) {
                        Intent intent = new Intent(CourseClassifyActivity.this, CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, latestWatch.getId());
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_access_learning:
                    //进入学习
                    Intent intent = new Intent(CourseClassifyActivity.this, CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_SECOND_ID, String.valueOf(data.get(position).getId()));
                    intent.putExtra(Constant.COURSE_TYPE_ID, couTypeIds);
                    startActivity(intent);
                    break;
                case R.id.tv_course_form:
                    //敬请期待
                    showTips("暂未开放，敬请期待~");
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void loadDataSuccess(CourseClassifyBean bean) {
        if (bean != null) {
            String researchChannelDesc = bean.getResearchChannelDesc();
            tvCommonTitle.setText(!TextUtils.isEmpty(bean.getName()) ? bean.getName() : "");
            DescBean resultBean = new Gson().fromJson(researchChannelDesc, DescBean.class);
            if (bean.getChildList() != null && bean.getChildList().size() > 0) {
                mAdapter.setNewData(bean.getChildList());
                mAdapter.setHeaderView(headerView);
                mAdapter.setFooterView(footerView);

                if (resultBean != null) {
                    tvCourseClassify.setText(resultBean.getBoldText() + "");
                    tvCourseClassifyContent.setText(getString(R.string.boc_ranking) + resultBean.ordinaryText);
                }
            }else {
                if (mAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
        }
        srlCourseClassify.finishRefresh();
    }
    @Override
    public void loadDataFail() {
        srlCourseClassify.finishRefresh();
        if (mAdapter != null && mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.getCourseClassifyList(couTypeIds);
        }
    }


    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }

}
