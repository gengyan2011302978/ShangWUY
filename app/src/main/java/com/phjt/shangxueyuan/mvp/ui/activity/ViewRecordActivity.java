package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.ViewRecordBean;
import com.phjt.shangxueyuan.di.component.DaggerViewRecordComponent;
import com.phjt.shangxueyuan.mvp.contract.ViewRecordContract;
import com.phjt.shangxueyuan.mvp.presenter.ViewRecordPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ViewRecordAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
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
 * date: 03/27/2020 17:38
 * company: 普华集团
 * description: 观看记录
 */
public class ViewRecordActivity extends BaseActivity<ViewRecordPresenter> implements ViewRecordContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_view_record)
    RecyclerView rvViewRecord;
    @BindView(R.id.srl_view_record)
    SmartRefreshLayout srlViewRecord;

    private ViewRecordAdapter viewRecordAdapter;
    private int currentPage;
    public final int PAGE_SIZE = 10;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerViewRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_view_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("学习记录");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewRecord.setLayoutManager(layoutManager);
        List<ViewRecordBean.RecordsBean> data = new ArrayList<>();
        viewRecordAdapter = new ViewRecordAdapter(this, data);
        rvViewRecord.setAdapter(viewRecordAdapter);
        srlViewRecord.setOnRefreshLoadMoreListener(this);

        viewRecordAdapter.setOnItemClickListener((adapter, view, position) -> {
            ViewRecordBean.RecordsBean recordsBean = (ViewRecordBean.RecordsBean) adapter.getData().get(position);
            if (recordsBean != null) {
                if (recordsBean.getDelStatus() == 0) {
                    //0 已删除 1未删除
                    TipsUtil.showTips("课程已失效");
                } else if (recordsBean.getUpState() != 0) {
                    //	0.课程（默认） 1.大专栏
                    if (recordsBean.getCouType() == 1) {
                        toCourseDetail(recordsBean);
                    } else {
                        //1.免费；2.会员
                        Integer freeType = recordsBean.getFreeType();
                        if (freeType == 2) {
                            checkVipState(recordsBean);
                        } else {
                            toCourseDetail(recordsBean);
                        }
                    }
                } else {
                    // upState:0下架 1上架
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        srlViewRecord.autoRefresh();
    }

    @OnClick({R.id.iv_common_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showViewRecordRefresh(List list) {
        viewRecordAdapter.setNewData(list);
    }

    @Override
    public void showViewRecordLoadMore(List list) {
        viewRecordAdapter.addData(list);
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
        viewRecordAdapter.setEmptyView(R.layout.empty_layout, rvViewRecord);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage += 1;
        mPresenter.getViewRecord(currentPage, PAGE_SIZE, false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentPage = 1;
        mPresenter.getViewRecord(currentPage, PAGE_SIZE, true);
    }


    @VipStateCheck
    private void checkVipState(ViewRecordBean.RecordsBean recordsBean) {
        toCourseDetail(recordsBean);
    }

    private void toCourseDetail(ViewRecordBean.RecordsBean recordsBean) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(Constant.BUNDLE_COURSE_ID, recordsBean.getCouId());
        intent.putExtra(Constant.BUNDLE_COURSE_NAME, recordsBean.getName());
        startActivity(intent);
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

}
