package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ViewColumnBean;
import com.phjt.shangxueyuan.di.component.DaggerMyColumnComponent;
import com.phjt.shangxueyuan.mvp.contract.MyColumnContract;
import com.phjt.shangxueyuan.mvp.presenter.MyColumnPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ViewmyColumnAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 12/09/2020 17:27
 * company: 普华集团
 * description: 模版请保持更新
 */
public class MyColumnActivity extends BaseActivity<MyColumnPresenter> implements MyColumnContract.View, OnRefreshLoadMoreListener {

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
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.rv_view_record)
    RecyclerView rvViewRecord;
    @BindView(R.id.srl_view_record)
    SmartRefreshLayout srlViewRecord;
    private int currentPage = 1;
    public  final int PAGE_SIZE = 10;
    private ViewmyColumnAdapter viewmyColumnAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyColumnComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_column;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("专栏课程");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewRecord.setLayoutManager(layoutManager);
        List<ViewColumnBean.RecordsBean> data = new ArrayList<>();
        viewmyColumnAdapter = new ViewmyColumnAdapter(this, data);
        rvViewRecord.setAdapter(viewmyColumnAdapter);
        srlViewRecord.setOnRefreshLoadMoreListener(this);
        if (mPresenter != null) {
            mPresenter.getMyColumn(1, PAGE_SIZE, true);
        }
        viewmyColumnAdapter.setOnItemClickListener((adapter, view, position) -> {
            ViewColumnBean.RecordsBean recordsBean = (ViewColumnBean.RecordsBean) adapter.getData().get(position);
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, recordsBean.getId() + "");
            startActivity(intent);
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void showViewRecordRefresh(List list) {
        viewmyColumnAdapter.setNewData(list);
    }

    @Override
    public void showViewRecordLoadMore(List list) {
        viewmyColumnAdapter.addData(list);
    }

    @Override
    public void canLoadMore() {
        if (srlViewRecord != null) {
            srlViewRecord.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlViewRecord != null) {
            srlViewRecord.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlViewRecord != null) {
            srlViewRecord.finishRefresh();
            srlViewRecord.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        viewmyColumnAdapter.setEmptyView(R.layout.empty_layout, rvViewRecord);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage += 1;
        mPresenter.getMyColumn(currentPage, PAGE_SIZE, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentPage = 1;
        mPresenter.getMyColumn(currentPage, PAGE_SIZE, true);
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
        finish();
    }
}
