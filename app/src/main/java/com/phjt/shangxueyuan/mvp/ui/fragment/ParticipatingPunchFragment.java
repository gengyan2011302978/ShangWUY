package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.di.component.DaggerParticipatingPunchComponent;
import com.phjt.shangxueyuan.mvp.contract.ParticipatingPunchContract;
import com.phjt.shangxueyuan.mvp.presenter.ParticipatingPunchPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.IntroductionPunchCardsActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ParticipatingPunchAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :打卡-参与的打卡
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

public class ParticipatingPunchFragment extends BaseFragment<ParticipatingPunchPresenter> implements ParticipatingPunchContract.View, OnRefreshLoadMoreListener {
    @BindView(R.id.rv_participating_punch)
    RecyclerView rvParticipatingPunch;
    @BindView(R.id.srl_participating_punch)
    SmartRefreshLayout srlParticipatingPunch;

    private int pageNo = 1;
    private  final int PAGE_SIZE = 10;
    private ParticipatingPunchAdapter adapter;
    private View mEmptyView;

    /**
     * 课程、训练营 打卡列表使用
     */
    private String mCourseId;
    private String mCourseType;
    private int mType;


    public static ParticipatingPunchFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(Constant.PUNCHM_TYPE, type);
        ParticipatingPunchFragment fragment = new ParticipatingPunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerParticipatingPunchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_participating_punch, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCourseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            mCourseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            mType = bundle.getInt(Constant.PUNCHM_TYPE);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvParticipatingPunch.setLayoutManager(layoutManager);
        adapter = new ParticipatingPunchAdapter(getActivity());
        rvParticipatingPunch.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        ImageView imageDodata = mEmptyView.findViewById(R.id.image_nodata);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        imageDodata.setBackgroundResource(R.drawable.ic_puncn_nodata);
        tvNodata.setText("你还没有参与打卡");
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        srlParticipatingPunch.setOnRefreshLoadMoreListener(this);
        pageNo = 1;

        adapter.setOnItemClickListener((adapter, view, position) -> {
            List<ParticipatingPunchBean> data = adapter.getData();
            // 0未参与 1打卡 2已打卡 3结束
            if (0 == data.get(position).getPunchStatus()) {
                Intent intent = new Intent(getContext(), IntroductionPunchCardsActivity.class);
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, data.get(position).getCouId());
                intent.putExtra(Constant.PARTICIPATION_PUNCH_CARDSID, data.get(position).getId());
                intent.putExtra(Constant.PUNCHM_TYPE, mType);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "打卡主页");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PUNCH_CLOCK) + "?id=" + data.get(position).getId());
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, data.get(position).getCouId());
                intent.putExtra(Constant.PARTICIPATION_PUNCH_CARDSID, data.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        loadData(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            if (TextUtils.isEmpty(mCourseId)) {
                //我的打开列表
                mPresenter.getMyPunchCardList(pageNo, PAGE_SIZE, isReFresh);
            } else {
                //课程、训练营 打卡列表
                mPresenter.getPunchClockList(mCourseId, mCourseType, pageNo, PAGE_SIZE, isReFresh);
            }
        }
    }

    @Override
    public void loadSuccess(BaseListBean<ParticipatingPunchBean> bean, boolean isRefresh) {
        if (bean != null) {
            srlParticipatingPunch.setEnableLoadMore(pageNo < bean.getTotalPage());
            if (isRefresh) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setNewData(bean.getRecords());
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srlParticipatingPunch.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srlParticipatingPunch.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                } else {
                    srlParticipatingPunch.setEnableLoadMore(false);
                }
                srlParticipatingPunch.finishLoadMore();
            }
        }
    }

    @Override
    public void loadFailed(boolean isReFresh) {
        if (srlParticipatingPunch != null) {
            srlParticipatingPunch.finishRefresh();
            srlParticipatingPunch.finishLoadMore();
        }
        if (adapter != null && mEmptyView != null && isReFresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void setData(@Nullable Object data) {

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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
