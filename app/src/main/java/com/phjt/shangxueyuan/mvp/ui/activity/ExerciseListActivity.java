package com.phjt.shangxueyuan.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.di.component.DaggerExerciseListComponent;
import com.phjt.shangxueyuan.mvp.contract.ExerciseListContract;
import com.phjt.shangxueyuan.mvp.presenter.ExerciseListPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MineExerciseAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ExerciseListActivity extends BaseActivity<ExerciseListPresenter> implements ExerciseListContract.View, OnRefreshLoadMoreListener {

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
    @BindView(R.id.rv_exercise)
    RecyclerView rvExercise;
    @BindView(R.id.srl_exercise)
    SmartRefreshLayout srlExercise;
    MineExerciseAdapter exerciseAdapter;
    private View mEmptyView;
    private int pageNo = 1;
    private  final int PAGE_SIZE = 30;
    private String couId;
    private String couType;
    public static Activity instance;
    List<MineExerciseBean> listCommentBeans = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExerciseListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exercise_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("作业列表");
        instance = this;
        couId = getIntent().getStringExtra(Constant.BUNDLE_COURSE_ID);
        couType = getIntent().getStringExtra(Constant.BUNDLE_COURSE_TYPE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvExercise.setLayoutManager(layoutManager);
        exerciseAdapter = new MineExerciseAdapter(this,listCommentBeans);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        exerciseAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        rvExercise.setAdapter(exerciseAdapter);
        srlExercise.setOnRefreshLoadMoreListener(this);
        loadData(true);
        exerciseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MineExerciseBean exerciseBean = (MineExerciseBean) adapter.getData().get(position);
                Intent intent = new Intent(ExerciseListActivity.this, ExerciseShowActivity.class);
                intent.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseBean.getId());
                intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBean.getExerciseBookId());
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, exerciseBean.getCouId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("commentBeans", (Serializable) listCommentBeans);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData(boolean b) {
        if (mPresenter != null) {
            mPresenter.exerciseBookList(couId, couType, pageNo, PAGE_SIZE, b);
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
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        loadData(true);
    }

    @Override
    public void showCommentListRefresh(List<MineExerciseBean> commentBeans) {
        if (exerciseAdapter != null) {
            if (null!=exerciseAdapter.getData()&&exerciseAdapter.getData().size()>0){
                for (int i = 0; i < exerciseAdapter.getData().size(); i++) {
                    exerciseAdapter.getData().set(i,commentBeans.get(i));
                }
                exerciseAdapter.notifyDataSetChanged();
            }else {
                exerciseAdapter.setNewData(commentBeans);
            }
            srlExercise.finishRefresh();
            listCommentBeans = commentBeans;

        }
    }

    @Override
    public void showCommentListLoadMore(List<MineExerciseBean> commentBeans) {
        if (exerciseAdapter != null) {
            exerciseAdapter.addData(commentBeans);
            srlExercise.finishLoadMore();
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

    @OnClick({R.id.tv_common_left, R.id.iv_common_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_common_left:
                finish();
                break;
            case R.id.iv_common_back:
                finish();
                break;
        }
    }
}
