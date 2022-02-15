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
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.di.component.DaggerMineExerciseComponent;
import com.phjt.shangxueyuan.mvp.contract.MineExerciseContract;
import com.phjt.shangxueyuan.mvp.presenter.MineExercisePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseShowActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MineExerciseAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :我的作业-全部/已提交/未提交
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/17 10:24
 */

public class MineExerciseFragment extends BaseFragment<MineExercisePresenter> implements MineExerciseContract.View, OnRefreshLoadMoreListener {
    public static final String TYPE = "type_id";
    @BindView(R.id.rv_exercise)
    RecyclerView rvExercise;
    @BindView(R.id.srl_exercise)
    SmartRefreshLayout srlExercise;

    private int pageNo = 1;
    private  final int PAGE_SIZE = 10;

    /**
     * -1:全部/ 1已提交/0未提交
     */
    private int mType;
    private MineExerciseAdapter adapter;
    private View mEmptyView;

    /**
     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
     *
     * @return 对应Fragment实例
     */
    public static MineExerciseFragment newInstance(int type) {
        Bundle args = new Bundle();
        MineExerciseFragment fragment = new MineExerciseFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineExerciseComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_exercise, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE);
        }
        srlExercise.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvExercise.setLayoutManager(layoutManager);
        adapter = new MineExerciseAdapter(getActivity(),null);
        rvExercise.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        ImageView imageNodata = mEmptyView.findViewById(R.id.image_nodata);
        imageNodata.setImageResource(R.drawable.ic_no_datas_bg);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        if (mType == -1) {
            tvNodata.setText("您还没有被分配的作业");
        } else {
            tvNodata.setText("您还没有已提交的作业");
        }
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        loadData(true);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            List<MineExerciseBean> data = adapter.getData();
            String mExerciseBookId = "0";
            if (!TextUtils.isEmpty(data.get(position).getExerciseBookId())) {
                mExerciseBookId = data.get(position).getExerciseBookId();
            }
            Intent intent = new Intent(getContext(), ExerciseShowActivity.class);
            intent.putExtra(Constant.BUNDLE_EXERCISE_ID, data.get(position).getId());
            intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, data.get(position).getCouId());
            intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, mExerciseBookId);
            intent.putExtra("commentBeans",(Serializable) data);
            startActivity(intent);
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
        if (srlExercise != null) {
            srlExercise.finishRefresh();
            srlExercise.finishLoadMore();
        }
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
            String type = "";
            if (mType != -1) {
                type = String.valueOf(mType);
            }
            mPresenter.myExerciseBookList(type, pageNo, PAGE_SIZE, isReFresh);
        }
    }

    @Override
    public void showCommentListRefresh(List<MineExerciseBean> commentBeans) {
        if (adapter != null) {
            adapter.setNewData(commentBeans);
        }
    }

    @Override
    public void showCommentListLoadMore(List<MineExerciseBean> commentBeans) {
        if (adapter != null) {
            adapter.addData(commentBeans);
        }
    }

    @Override
    public void canLoadMore() {
        if (srlExercise != null) {
            srlExercise.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlExercise != null) {
            srlExercise.setEnableLoadMore(false);
        }

    }

    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
