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
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTrainingCampBean;
import com.phjt.shangxueyuan.di.component.DaggerTrainingCampComponent;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingCampPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCampAdapter;
import com.phjt.shangxueyuan.utils.Constant;
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
 * description :我的课程-训练营/专栏
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */

public class TrainingCampFragment extends BaseFragment<TrainingCampPresenter> implements TrainingCampContract.View, OnRefreshLoadMoreListener {
    public static final String TYPE = "type_id";
    @BindView(R.id.rv_training_camp)
    RecyclerView rvTrainingCamp;
    @BindView(R.id.srl_training_camp)
    SmartRefreshLayout srlTrainingCamp;
    private int pageNo = 1;
    private  final int PAGE_SIZE = 10;

    /**
     * 0:训练营 1:专栏
     */
    private int mType;
    private TrainingCampAdapter adapter;
    private View mEmptyView;

    /**
     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
     *
     * @return 对应Fragment实例
     */
    public static TrainingCampFragment newInstance(int type) {
        Bundle args = new Bundle();
        TrainingCampFragment fragment = new TrainingCampFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingCampComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_camp, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTrainingCamp.setLayoutManager(layoutManager);
        adapter = new TrainingCampAdapter(getActivity());
        rvTrainingCamp.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        srlTrainingCamp.setOnRefreshLoadMoreListener(this);
        loadData(true);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            // 0:训练营, 1 :专栏
            if (0 == mType) {
                List<MyTrainingCampBean> data = adapter.getData();
                Intent intent = new Intent(getActivity(), TrainingCampDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID,data.get(position).getId());
                startActivity(intent);
            } else if (1 == mType) {
                List<MyTrainingCampBean> data = adapter.getData();
                Intent intent = new Intent(getActivity(), TopicInfoActivity.class);
                intent.putExtra(Constant.BUNDLE_TOPIC_ID, String.valueOf(data.get(position).getId()));
                startActivity(intent);
            }
        });
    }

    /**
     * 此方法使用请查看 {@link IFragment#setData(Object)} 注释 目的：建立统一入口 与 Fragment 进行通信
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
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
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            mPresenter.getTrainingBattalionList(mType, pageNo, PAGE_SIZE, isReFresh);
        }
    }


    @Override
    public void LoadSuccess(BaseListBean<MyTrainingCampBean> bean, boolean isRefresh) {
        if (bean != null) {
            srlTrainingCamp.setEnableLoadMore(pageNo < bean.getTotalPage());
            if (isRefresh) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setNewData(bean.getRecords());
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srlTrainingCamp.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srlTrainingCamp.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                } else {
                    srlTrainingCamp.setEnableLoadMore(false);
                }
                srlTrainingCamp.finishLoadMore();
            }
            adapter.setType(mType);
        }
    }

    @Override
    public void LoadFailed(boolean isReFresh) {
        if (srlTrainingCamp != null) {
            srlTrainingCamp.finishRefresh();
            srlTrainingCamp.finishLoadMore();
        }
        if (adapter != null && mEmptyView != null && isReFresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
