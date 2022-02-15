package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.AppLifecyclesImpl;
import com.phjt.shangxueyuan.bean.CourseChannelBean;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.bean.OrdinaryCourseBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.di.component.DaggerStudyComponent;
import com.phjt.shangxueyuan.mvp.contract.StudyContract;
import com.phjt.shangxueyuan.mvp.presenter.StudyPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.AllStudyCampActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseStudyTypeAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingBattalionAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.StudyMultiCardView;
import com.phjt.shangxueyuan.widgets.StudySingleCardView;
import com.phsxy.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
public class StudyFragment extends BaseFragment<StudyPresenter> implements StudyContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_go_buy_vip)
    TextView tvGoBuyVip;
    @BindView(R.id.rl_buy_vip)
    RelativeLayout rlBuyVip;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.tv_vip_tip)
    TextView tvVipTip;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.rv_course_type)
    RecyclerView rvCourseType;
    @BindView(R.id.rv_training_battalion)
    RecyclerView rvTrainingBattalion;
    @BindView(R.id.rl_training_battalion)
    RelativeLayout rlTrainingBattalion;

    private StudyMultiCardView smcStudy;
    private CourseStudyTypeAdapter courseStudyTypeAdapter;
    private List<CourseTypeBean> courseTypeBeanList = new ArrayList<>();
    private CourseTypeBean currentTypeBean;
    private int currentCourseType;
    private TrainingBattalionAdapter mTrainingBattalionAdapter;

    public static StudyFragment newInstance() {
        Bundle args = new Bundle();
        StudyFragment fragment = new StudyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerStudyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        smcStudy = view.findViewById(R.id.smc_study);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivCommonBack.setVisibility(View.VISIBLE);
        ivCommonBack.setImageResource(R.drawable.customer_service_icon);
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setImageResource(R.drawable.ic_news);
        tvCommonTitle.setText("我的学习");
        srfStudy.setEnableLoadMore(false);
        mTrainingBattalionAdapter = new TrainingBattalionAdapter(mContext);
        rvTrainingBattalion.setAdapter(mTrainingBattalionAdapter);
        rvTrainingBattalion.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTrainingBattalion.setHasFixedSize(true);
        rvTrainingBattalion.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        rvCourseType.setLayoutManager(layoutManager);
        courseStudyTypeAdapter = new CourseStudyTypeAdapter(courseTypeBeanList);
        rvCourseType.setAdapter(courseStudyTypeAdapter);
        courseStudyTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
            gotoStudy(position);
        });
        srfStudy.setOnRefreshLoadMoreListener(this);
        mTrainingBattalionAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<TrainingBattalionBean> data = adapter.getData();
            Intent intent = new Intent(mContext, TrainingCampDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, String.valueOf(data.get(position).getId()));
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void gotoStudy(int position) {
        if (courseTypeBeanList.get(position).getState() == 1) {
            currentCourseType = position;
            for (int i = 0; i < courseTypeBeanList.size(); i++) {
                courseTypeBeanList.get(i).setSelected(false);
            }
            courseTypeBeanList.get(position).setSelected(true);
            mPresenter.getChannelCourse(courseTypeBeanList.get(position).getId());
            currentTypeBean = courseTypeBeanList.get(position);
            courseStudyTypeAdapter.notifyDataSetChanged();
        } else if (1 == position) {
            Intent trainingIntent = new Intent(mContext, AllStudyCampActivity.class);
            trainingIntent.putExtra("studyType", position);
            startActivity(trainingIntent);
//            Intent intent = new Intent(getActivity(), AllStudyCampActivity.class);
//            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_INFORMATION));
//            startActivity(intent);
        } else if (2 == position) {
            Intent trainingIntent = new Intent(mContext, AllStudyCampActivity.class);
            trainingIntent.putExtra("studyType", position);
            startActivity(trainingIntent);
//            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
//            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_MARKETING));
//            startActivity(intent);
        }
    }

    @OnClick({R.id.iv_common_back, R.id.ic_common_right, R.id.rl_buy_vip, R.id.tv_go_battalion_study})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                //客服
//                DialogUtils.openWeChatDialog(getActivity());
                Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                break;
            case R.id.ic_common_right:
                //消息
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;

            case R.id.rl_buy_vip:
                //加入vip
                VipUtil.toVipPage(getContext());
                break;

            case R.id.tv_go_battalion_study:
                //训练营
                break;
            default:
                break;
        }
    }

    public void refreshData() {
        if (mPresenter != null) {
            mPresenter.getCourseType();
//            mPresenter.researchColumn();
            mPresenter.getOrdinaryCourse();
            mPresenter.getTrainingBattalion();
            mPresenter.getUserInfo();
        }
    }

    /**
     * 头部——获取课程分类
     *
     * @param data
     */
    @Override
    public void getCourseTypeSuccess(List<CourseTypeBean> data) {
        courseTypeBeanList.clear();
        if (data != null && data.size() > 0) {
            if (data.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    courseTypeBeanList.add(data.get(i));
                }
            } else {
                courseTypeBeanList = data;
            }
            courseTypeBeanList.get(currentCourseType).setSelected(true);
            courseStudyTypeAdapter.setNewData(courseTypeBeanList);
            mPresenter.getChannelCourse(courseTypeBeanList.get(currentCourseType).getId());
            currentTypeBean = courseTypeBeanList.get(currentCourseType);
        }
    }


    @Override
    public void getCourseTypeFailed(String msg) {
        showMessage(msg);
    }

    @Override
    public void getChannelCourseSuccess(List<CourseChannelBean> data) {
        srfStudy.finishRefresh();
        smcStudy.setData(getContext(), data, currentTypeBean);
    }

    @Override
    public void getChannelCourseFailed(String msg) {
        showMessage(msg);
    }

    /**
     * 获取大多普通课程成功
     *
     * @param data
     */
    @Override
    public void getOrdinaryCourseSuccess(List<OrdinaryCourseBean> data) {
        if (data != null && data.size() > 0) {
            llContainer.removeAllViews();
            for (int i = 0; i < data.size(); i++) {
                StudySingleCardView studySingleCardView = new StudySingleCardView(getContext());
                studySingleCardView.setData(data.get(i));
                llContainer.addView(studySingleCardView);
            }
        }
        mPresenter.researchColumn();
    }

    /**
     * 获取普通课程失败
     *
     * @param
     */
    @Override
    public void getOrdinaryCourseFailed(String msg) {
        showMessage(msg);
        mPresenter.researchColumn();
    }

    /**
     * 专栏列表成功
     *
     * @param data
     */
    @Override
    public void getResearchColumnSuccess(List<OrdinaryCourseBean> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                StudySingleCardView studySingleCardView = new StudySingleCardView(getContext());
                studySingleCardView.setData(data.get(i));
                llContainer.addView(studySingleCardView);
            }
        }
    }


    @Override
    public void getResearchColumnFailed(String msg) {

    }

    @Override
    public void loadDataSuccess(UserInfoBean beans) {
        if (beans != null) {
            int vipState = beans.getVipState();
            if (vipState == 1 || vipState == 2) {
                rlBuyVip.setVisibility(View.GONE);
            } else {
                rlBuyVip.setVisibility(View.VISIBLE);
            }
            String phone = beans.getMobile();
            //友盟推送——设置Alias
            if (!TextUtils.isEmpty(phone)) {
                SPUtils.getInstance().put(Constant.SP_MOBILE, beans.getMobile());
                AppLifecyclesImpl.getPushAgent().setAlias(phone, Constant.PUSH_PHONE, (b, s) -> {
//                    LogUtils.e("===============Mine:别名绑定" + phone + b);
                });
            }
            //保存用户id
            SPUtils.getInstance().put(Constant.SP_USER_ID, beans.getId());
        }
    }

    @Override
    public void loadError() {
        srfStudy.finishRefresh();
    }

    @Override
    public void getTrainingBattalionSuccess(List<TrainingBattalionBean> beans) {
        if (beans != null && beans.size() > 0) {
            rlTrainingBattalion.setVisibility(View.VISIBLE);
            mTrainingBattalionAdapter.setNewData(beans);
        }
    }

    @Override
    public void getTrainingBattalionFailed() {
        rlTrainingBattalion.setVisibility(View.GONE);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    /**
     * 未读消息总数
     */
    public void showUnReadCount(Integer count) {
        if (tvMainInfo != null) {
            if (count != null && count > 0) {
                tvMainInfo.setVisibility(View.VISIBLE);
                if (count < 100) {
                    tvMainInfo.setText(count + "");
                } else {
                    tvMainInfo.setText("99+");
                }
            } else {
                tvMainInfo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
