package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.StudyCampBean;
import com.phjt.shangxueyuan.di.component.DaggerStudyCampComponent;
import com.phjt.shangxueyuan.mvp.contract.StudyCampContract;
import com.phjt.shangxueyuan.mvp.presenter.StudyCampPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.StudyCampAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :Roy
 * description :成长营
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */
public class StudyCampFragment extends BaseFragment<StudyCampPresenter> implements StudyCampContract.View, OnRefreshLoadMoreListener {
    @BindView(R.id.rv_study_camp)
    RecyclerView rvStudyCamp;
    @BindView(R.id.srl_study_camp)
    SmartRefreshLayout srlStudyCamp;
    private View mEmptyView;
    private int pageNo = 1;
    private final int PAGE_SIZE = 10;
    private StudyCampAdapter adapter;
    private String trainingCampType = "";

    public static StudyCampFragment newInstance(String trainingCampType) {
        StudyCampFragment fragment = new StudyCampFragment();
        Bundle args = new Bundle();
        args.putString("trainingCampType", trainingCampType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerStudyCampComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_study_camp, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            trainingCampType = bundle.getString("trainingCampType");
        }
        srlStudyCamp.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvStudyCamp.setLayoutManager(layoutManager);
        adapter = new StudyCampAdapter(getActivity());
        rvStudyCamp.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        ImageView imageNodata = mEmptyView.findViewById(R.id.image_nodata);
        imageNodata.setImageResource(R.drawable.ic_study_no_datas_bg);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        tvNodata.setText("暂时没有学习营");
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        loadData(true,trainingCampType);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            List<StudyCampBean> data = adapter.getData();
            Intent intent = new Intent(getActivity(), TrainingCampDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, data.get(position).getId());
            startActivity(intent);
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (srlStudyCamp != null) {
            srlStudyCamp.finishRefresh();
            srlStudyCamp.finishLoadMore();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false,trainingCampType);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true,trainingCampType);
    }

    private void loadData(boolean isReFresh,String trainingCampType) {
        if (mPresenter != null) {
            mPresenter.getStudyCampList(trainingCampType,pageNo, PAGE_SIZE, isReFresh);
        }
    }


    @Override
    public void showStudyCampRefresh(List<StudyCampBean> commentBeans) {
        if (adapter != null) {
            adapter.setNewData(commentBeans);
        }
    }

    @Override
    public void showStudyCampLoadMore(List<StudyCampBean> commentBeans) {
        if (adapter != null) {
            adapter.addData(commentBeans);
        }
    }

    @Override
    public void canLoadMore() {
        if (srlStudyCamp != null) {
            srlStudyCamp.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlStudyCamp != null) {
            srlStudyCamp.setEnableLoadMore(false);
        }
    }

    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
