package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.event.CourseDesTipBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerAuditionComponent;
import com.phjt.shangxueyuan.mvp.contract.AuditionContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.AuditionPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.AuditionCourseAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.LiveListSecondAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.MZLiveUtils;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.popupwindow.CourseTeacherPop;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
public class AuditionFragment extends BaseLazyLoadFragment<AuditionPresenter> implements AuditionContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_hot)
    TextView mTvHot;
    @BindView(R.id.tv_new)
    TextView mTvNew;
    @BindView(R.id.tv_teacher)
    RoundTextView mTvTeacher;
    @BindView(R.id.cl_tip)
    ConstraintLayout mClTip;
    @BindView(R.id.rv_audition)
    RecyclerView mRvAudition;
    @BindView(R.id.srl_audition)
    SmartRefreshLayout mSrlAudition;
    @BindView(R.id.tv_tip_content)
    TextView mTvTipContent;

    public static final String COURSE_FIRST_TYPE_ID = "course_first_type_id";
    public static final String COURSE_LEVEL = "course_level";
    public static final String COURSE_TYPE_ID = "course_type_id";
    public static final String COURSE_TYPE_NAME = "course_type_name";
    public static final String COURSE_DES = "course_des";
    public static final int COURSE_REQUEST_CODE = 100;

    private int currentPage;
    public final int PAGE_SIZE = 10;

    private AuditionCourseAdapter courseAdapter;

    /**
     * 首页一级分类id (用于讲师列表的请求)
     */
    private String firstTypeId;
    /**
     * 课程分类等级
     */
    private int level;
    /**
     * 课程类别 二级id
     */
    private String typeId;
    /**
     * 排序方式 1：最近，2：最热
     */
    private Integer sort;

    /**
     * 讲师id
     */
    private String lecturerId;

    /**
     * 空页面
     */
    private View emptyView;
    /**
     * 描述
     */
    private String description;
    /**
     * 一级分类标题+二级分类标题
     */
    private String name;
    /**
     * 是否是BOC中的课程
     */
    private boolean isBocCourse = false;

    public static AuditionFragment newInstance(String firstTypeId, int level, String typeId, String name, String description) {
        Bundle args = new Bundle();
        args.putString(COURSE_FIRST_TYPE_ID, firstTypeId);
        args.putInt(COURSE_LEVEL, level);
        args.putString(COURSE_TYPE_ID, typeId);
        args.putString(COURSE_TYPE_NAME, name);
        args.putString(COURSE_DES, description);
        AuditionFragment fragment = new AuditionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAuditionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audition, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            firstTypeId = bundle.getString(COURSE_FIRST_TYPE_ID);
            level = bundle.getInt(COURSE_LEVEL);
            typeId = bundle.getString(COURSE_TYPE_ID);
            name = bundle.getString(COURSE_TYPE_NAME);
            description = bundle.getString(COURSE_DES);
            //默认类别 传null
            sort = null;
        }

        isBocCourse = !TextUtils.isEmpty(name) && (name.contains("初级课程") || name.contains("中级课程") || name.contains("高级课程"));
        boolean isShowTip;
        if (isBocCourse) {
            isShowTip = SPUtils.getInstance().getBoolean(name, true);
        } else {
            isShowTip = SPUtils.getInstance().getBoolean(Constant.COURSE_SHOW_DES, true);
        }
        mClTip.setVisibility(isShowTip && !TextUtils.isEmpty(description) ? View.VISIBLE : View.GONE);
        mTvTipContent.setText(description);

        mSrlAudition.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvAudition.setLayoutManager(layoutManager);
        List<CourseItemBean> itemBeanList = new ArrayList<>();
        courseAdapter = new AuditionCourseAdapter(mContext, itemBeanList);
        mRvAudition.setAdapter(courseAdapter);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        courseAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            CourseItemBean itemBean = (CourseItemBean) adapter.getData().get(position);
            if (itemBean != null) {
                Intent intent = new Intent(mContext, CourseDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, itemBean.getId());
                intent.putExtra(Constant.BUNDLE_COURSE_NAME, itemBean.getName());
                startActivityForResult(intent, COURSE_REQUEST_CODE);
                setShowImg();
            }
        });

        onUmengEvent("");
    }

    /**
     * 刷新观看人数
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COURSE_REQUEST_CODE) {
            refreshData();
        }
    }

    @Override
    public void lazyLoadData() {
        mSrlAudition.autoRefresh();
    }

    /**
     * 是否加载过数据 true加载过
     */
    private boolean isFirstLoad;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstLoad) {
            refreshData();
        }
    }

    public void refreshData() {
        if (mSrlAudition != null) {
            onRefresh(mSrlAudition);

        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            mPresenter.getCourseList(level, typeId, sort, lecturerId, currentPage, PAGE_SIZE, true);
            if (level == 1 && name.contains("直播")) {
                mPresenter.getStudentLiveList();
            }

        }
        isFirstLoad = true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getCourseList(level, typeId, sort, lecturerId, currentPage, PAGE_SIZE, false);
        }
    }

    @Override
    public void showAuditionCourseRefresh(List<CourseItemBean> itemBeanList) {
        if (courseAdapter != null&&itemBeanList!=null) {
            courseAdapter.setNewData(itemBeanList);
        }
    }

    @Override
    public void showAuditionCourseLoadMore(List<CourseItemBean> itemBeanList) {
        if (courseAdapter != null) {
            courseAdapter.addData(itemBeanList);
        }
    }

    @Override
    public void getStudentLiveListSuccess(List<LiveBean> itemBeanList) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.activity_live_list, mRvAudition, false);
        RecyclerView rvLive = header.findViewById(R.id.rv_Live);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvLive.setLayoutManager(layoutManager);
        LiveListSecondAdapter adapter = new LiveListSecondAdapter(mContext, itemBeanList);
        rvLive.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveBean liveBean = (LiveBean) adapter.getData().get(position);
                if (liveBean.getStatus() == 1 && liveBean.getStatus() == 0) {
                    CommonHttpManager.getInstance(getActivity())
                            .obtainRetrofitService(ApiService.class)
                            .liveInfo(liveBean.getId() + "")
                            .compose(RxUtils.applySchedulers())
                            .subscribe((BaseBean<LiveBean> baseBean) -> {
                                if (baseBean.isOk()) {
                                    if (baseBean.data.getStatus() == 1) {
                                        MZLiveUtils.toLivePlay(mContext, liveBean.getTicketId(), liveBean.getLiveStyle(), liveBean.getId());
                                    } else {
                                        refreshData();
                                    }
                                } else {

                                }
                            }, throwable -> LogUtils.e("网络异常"));
                } else {
                    if (liveBean != null && !TextUtils.isEmpty(liveBean.getTicketId())) {
                        MZLiveUtils.toLivePlay(mContext, liveBean.getTicketId(), liveBean.getLiveStyle(), liveBean.getId());
                    }
                }
            }
        });
        courseAdapter.setHeaderView(header);
    }

    @SingleClick
    @OnClick({R.id.tv_hot, R.id.tv_new, R.id.tv_teacher, R.id.iv_tip_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hot:
                mTvHot.setTextColor(ContextCompat.getColor(mContext, R.color.color_4071FC));
                mTvNew.setTextColor(ContextCompat.getColor(mContext, R.color.color_868686));
                sort = 2;
                refreshData();
                onUmengEvent("最热");
                setShowImg();
                break;
            case R.id.tv_new:
                mTvHot.setTextColor(ContextCompat.getColor(mContext, R.color.color_868686));
                mTvNew.setTextColor(ContextCompat.getColor(mContext, R.color.color_4071FC));
                sort = 1;
                refreshData();
                onUmengEvent("最新");
                setShowImg();
                break;
            case R.id.tv_teacher:
                if (mPresenter != null) {
                    //全部中，一级分类id和二级分类id相同，传null
                    if (TextUtils.equals(firstTypeId, typeId)) {
                        mPresenter.getCourseTeacherList(firstTypeId, null);
                    } else {
                        mPresenter.getCourseTeacherList(firstTypeId, typeId);
                    }
                }
                setShowImg();
                onUmengEvent("讲师");
                break;
            case R.id.iv_tip_close:
                if (isBocCourse) {
                    mClTip.setVisibility(View.GONE);
                    SPUtils.getInstance().put(name, false);
                } else {
                    SPUtils.getInstance().put(Constant.COURSE_SHOW_DES, false);
                    EventBusManager.getInstance().post(new CourseDesTipBean());
                }
                break;
            default:
                break;
        }
    }

    private void setShowImg() {
        if (getActivity() instanceof CourseCategoryActivity) {
            CourseCategoryActivity activity = (CourseCategoryActivity) getActivity();
            activity.noIsShowImg();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CourseDesTipBean tipBean) {
        if (mClTip != null) {
            mClTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCourseTeacherPop(List<CourseTeacherItemBean> courseTeacherList) {
        courseTeacherList.add(0, new CourseTeacherItemBean("全部", false));
        for (int i = 0; i < courseTeacherList.size(); i++) {
            if (TextUtils.isEmpty(lecturerId)) {
                courseTeacherList.get(0).setSelect(true);
            } else {
                CourseTeacherItemBean itemBean = courseTeacherList.get(i);
                if (itemBean != null && TextUtils.equals(lecturerId, itemBean.getId())) {
                    itemBean.setSelect(true);
                }
            }
        }
        CourseTeacherPop teacherPop = new CourseTeacherPop(mContext);
        teacherPop.setCourseTeacher(courseTeacherList);
        teacherPop.showAsDropDown(mTvTeacher, 0, 0);

        teacherPop.setTeacherPop((id, name) -> {
            lecturerId = id;
            mTvTeacher.setText(name);
            refreshData();
        });
    }

    /**
     * 友盟事件统计
     *
     * @param type 最新、最热统计
     */
    public void onUmengEvent(String type) {
        //友盟事件统计
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (name.contains("精品试听")) {
            if (TextUtils.isEmpty(type)) {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_AUDITION, name);
            } else {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_AUDITION_TYPE, name + type);
            }
        } else if (name.contains("初级课程")) {
            if (TextUtils.isEmpty(type)) {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_PRIMARY, name);
            } else {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_PRIMARY_TYPE, name + type);
            }
        } else if (name.contains("中级课程")) {
            if (TextUtils.isEmpty(type)) {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_MIDDLE, name);
            } else {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_MIDDLE_TYPE, name + type);
            }
        } else if (name.contains("高级课程")) {
            if (TextUtils.isEmpty(type)) {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_HIGH, name);
            } else {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_HIGH_TYPE, name + type);
            }
        } else if (name.contains("创富通道")) {
            if (TextUtils.isEmpty(type)) {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_RICH, name);
            } else {
                UmengUtil.onUmengUtils(mContext, UmengUtil.COURSE_CATEGORY_RICH_TYPE, name + type);
            }
        }

    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void canLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.finishRefresh();
            mSrlAudition.finishLoadMore();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }
}
