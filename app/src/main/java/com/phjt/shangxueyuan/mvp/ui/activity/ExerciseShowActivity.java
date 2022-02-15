package com.phjt.shangxueyuan.mvp.ui.activity;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerExerciseShowComponent;
import com.phjt.shangxueyuan.mvp.contract.ExerciseShowContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.ExerciseShowPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExerciseShowAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
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
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 作业详情展示
 */
public class ExerciseShowActivity extends BaseActivity<ExerciseShowPresenter> implements ExerciseShowContract.View, OnRefreshLoadMoreListener {

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
    @BindView(R.id.tv_exercise)
    TextView tvExercise;
    @BindView(R.id.rv_exercise_show)
    RecyclerView rvExerciseShow;
    @BindView(R.id.srl_exercise)
    SmartRefreshLayout srlExercise;

    public static String couName;
    private int pageNo = 1;
    private final int PAGE_SIZE = 10;
    private String exerciseId;
    public static String couId;
    private int couState;
    private String exerciseBookId;
    private ExerciseShowAdapter adapter;
    private View mEmptyView;
    private List<MineExerciseBean> commentBeans;

    /**
     * 训练营参数
     */
    private String mNoteTaskId;
    private String mTrainingCampId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExerciseShowComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exercise_show;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("作业详情");
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setBackgroundResource(R.drawable.share);

        exerciseId = getIntent().getStringExtra(Constant.BUNDLE_EXERCISE_ID);
        couId = getIntent().getStringExtra(Constant.PUNCH_CARDS_COURSE_ID);
        exerciseBookId = getIntent().getStringExtra(Constant.BUNDLE_EXERCISE_BOOK_ID);
        mNoteTaskId = getIntent().getStringExtra(Constant.BUNDLE_NODE_TASK_ID);
        mTrainingCampId = getIntent().getStringExtra(Constant.BUNDLE_TRAINING_CAMP_ID);
        commentBeans = (List<MineExerciseBean>) getIntent().getSerializableExtra("commentBeans");
        if (commentBeans == null) {
            commentBeans = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvExerciseShow.setLayoutManager(layoutManager);
        adapter = new ExerciseShowAdapter(this, null);
        adapter.setTrainingCamp(!TextUtils.isEmpty(mTrainingCampId));
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        rvExerciseShow.setAdapter(adapter);
        rvExerciseShow.setFocusableInTouchMode(false);
        srlExercise.setOnRefreshLoadMoreListener(this);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExerciseShowBean exerciseShowBean = (ExerciseShowBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_look_answer:
                        Intent intent = new Intent(ExerciseShowActivity.this, ExerciseDoActivity.class);
                        intent.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseId);
                        intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBookId);
                        intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, couId);
                        intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, mTrainingCampId);
                        intent.putExtra(Constant.BUNDLE_NODE_TASK_ID, mNoteTaskId);
                        if (exerciseShowBean.getState() == 1) {
                            intent.putExtra(Constant.BUNDLE_EXERCISE_UPDATE, true);
                        }
                        startActivityForResult(intent, 100);
                        break;
                    case R.id.tv_public_display:
                        if (exerciseShowBean.getState() == 2) {
                            if (exerciseShowBean.getExerciseRecordState() == 0) {
                                DialogUtils.showPublicExcicseDialog(ExerciseShowActivity.this, new DialogUtils.OnCancelSureClick() {
                                    @SuppressLint("CheckResult")
                                    @Override
                                    public void clickSure() {
                                        CommonHttpManager.getInstance(ExerciseShowActivity.this)
                                                .obtainRetrofitService(ApiService.class)
                                                .openState(1, exerciseShowBean.getExerciseUserRecordId())
                                                .compose(RxUtils.applySchedulers())
                                                .subscribe(baseBean -> {
                                                    if (baseBean.isOk()) {
                                                        if (exerciseShowBean.getExerciseRecordState() == 0) {
                                                            exerciseShowBean.setExerciseRecordState(1);
                                                        } else {
                                                            exerciseShowBean.setExerciseRecordState(0);
                                                        }
                                                        adapter.notifyItemChanged(2, exerciseShowBean);
                                                    } else {

                                                    }
                                                }, throwable -> LogUtils.e("网络异常"));
                                    }
                                });

                            } else {
                                CommonHttpManager.getInstance(ExerciseShowActivity.this)
                                        .obtainRetrofitService(ApiService.class)
                                        .openState(0, exerciseShowBean.getExerciseUserRecordId())
                                        .compose(RxUtils.applySchedulers())
                                        .subscribe(baseBean -> {
                                            if (baseBean.isOk()) {
                                                if (exerciseShowBean.getExerciseRecordState() == 0) {
                                                    exerciseShowBean.setExerciseRecordState(1);
                                                } else {
                                                    exerciseShowBean.setExerciseRecordState(0);
                                                }
                                                adapter.notifyItemChanged(2, exerciseShowBean);
                                            } else {

                                            }
                                        }, throwable -> LogUtils.e("网络异常"));

                            }
                        } else {
                            Intent intent1 = new Intent(ExerciseShowActivity.this, ExerciseDoActivity.class);
                            intent1.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseId);
                            intent1.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBookId);
                            intent1.putExtra(Constant.PUNCH_CARDS_COURSE_ID, couId);
                            intent1.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, mTrainingCampId);
                            intent1.putExtra(Constant.BUNDLE_NODE_TASK_ID, mNoteTaskId);
                            startActivityForResult(intent1, 100);
                        }

                        break;
                    case R.id.iv_zan_item:
                    case R.id.tv_zan_count_item:
                        CommonHttpManager.getInstance(ExerciseShowActivity.this)
                                .obtainRetrofitService(ApiService.class)
                                .answerUp(exerciseShowBean.getExerciseUserRecordId())
                                .compose(RxUtils.applySchedulers())
                                .subscribe(baseBean -> {
                                    if (baseBean.isOk()) {
                                        if ("0".equals(exerciseShowBean.getLikeState())) {
                                            exerciseShowBean.setLikeState("1");
                                            exerciseShowBean.setLikeNum(exerciseShowBean.getLikeNum() + 1);
                                        } else {
                                            exerciseShowBean.setLikeState("0");
                                            exerciseShowBean.setLikeNum(exerciseShowBean.getLikeNum() - 1);
                                        }
                                        adapter.notifyItemChanged(position + 1, exerciseShowBean);
                                    } else {

                                    }
                                }, throwable -> LogUtils.e("网络异常"));

                        break;
                    default:
                        break;
                }
            }
        });

        getUserAnswer(1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getUserAnswer(1);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        getUserAnswer(1);
    }

    private void getUserAnswer(int type) {
        if (mPresenter != null) {
            mPresenter.userAnswer(String.valueOf(type), exerciseId, couId, mTrainingCampId, pageNo, PAGE_SIZE, true);
        }
    }

    @Override
    public void exerciseSuccess(BaseBean<ExerciseBean> baseBean) {
        View header = LayoutInflater.from(this).inflate(R.layout.item_exercise_header, rvExerciseShow, false);
        TextView exerciseTitle = header.findViewById(R.id.exercise_title);
        TextView tvExerciseState = header.findViewById(R.id.tv_exercise_state);
        TextView tvExerciseTime = header.findViewById(R.id.tv_exercise_time);
        exerciseTitle.setText(baseBean.data.getExerciseName());
        couName = baseBean.data.getCouName();
        if (adapter != null) {
            List<ExerciseShowBean> exerciseShowBeans = adapter.getData();
            ExerciseShowBean exerciseShowBean = new ExerciseShowBean();
            exerciseShowBean.setQuestionVoList(baseBean.data.getQuestionVoList());
            exerciseShowBean.setState(baseBean.data.getState());
            exerciseShowBeans.add(0, exerciseShowBean);
            for (int i = 0; i < exerciseShowBeans.size(); i++) {
                exerciseShowBeans.get(i).setState(baseBean.data.getState());
            }
        }
        couState = baseBean.data.getState();
        if (baseBean.data.getState() == 0) {
            tvExercise.setVisibility(View.VISIBLE);
            tvExerciseState.setText("未提交");
        }
        if (baseBean.data.getState() == 1) {
            tvExerciseState.setText("已提交");
            tvExerciseState.setTextColor(ContextCompat.getColor(this, R.color.color_30D989));
        }
        if (baseBean.data.getState() == 2) {
            tvExerciseState.setText("已点评");
            tvExerciseState.setTextColor(ContextCompat.getColor(this, R.color.color666666));
        }
        tvExerciseTime.setText(String.format("发布时间：%s", baseBean.data.getCreateTime()));
        if (commentBeans != null && commentBeans.size() > 0) {
            for (int i = 0; i < commentBeans.size(); i++) {
                if (commentBeans.get(i).getId().equals(exerciseId)) {
                    for (int j = 0; j < baseBean.data.getQuestionVoList().size(); j++) {
                        baseBean.data.getQuestionVoList().get(j).setPosition(i);
                        baseBean.data.getQuestionVoList().get(j).setCommentBeans(commentBeans);
                    }
                    break;
                }
            }
        }
        adapter.setHeaderView(header);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void exerciseAnswerSuccess(List<ExerciseShowBean> baseBean, String type) {
        if (mPresenter != null) {
            if ("1".equals(type)) {
                for (int i = 0; i < baseBean.size(); i++) {
                    baseBean.get(i).setAnwserType(2 + i);
                }
                adapter.setNewData(baseBean);
                getUserAnswer(0);
            } else {
                if (baseBean.size() > 0) {
                    if (baseBean.get(0).getRecordVos().size() > 0) {
                        List<ExerciseShowBean> exerciseShowBeans = adapter.getData();
                        ExerciseShowBean exerciseShowBean = new ExerciseShowBean();
                        exerciseShowBean = baseBean.get(0);
                        exerciseShowBean.setAnwserType(1);
                        exerciseShowBeans.add(0, exerciseShowBean);
                    }
                }
                mPresenter.exerciseBookDetail(exerciseId, couId, exerciseBookId, mTrainingCampId);
            }
        }
    }

    @Override
    public void exerciseAnswerLoadMore(List<ExerciseShowBean> baseBean, String type) {
        if (adapter != null) {
            for (int i = 0; i < baseBean.size(); i++) {
                baseBean.get(i).setAnwserType(3 + i);
            }
            adapter.addData(baseBean);
        }
    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        ShareBean shareBean = new ShareBean();
        shareBean.setTitle(shareItemBean.getTitle());
        shareBean.setContent(shareItemBean.getContent());
        shareBean.setUrl(shareItemBean.getUrl());
        shareBean.setImgUrl(shareItemBean.getPhoto());

        ShareUtils.showSharePop(this, shareBean);
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

    @OnClick({R.id.tv_common_left, R.id.iv_common_back, R.id.tv_exercise, R.id.ic_common_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_common_left:
                finish();
                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.ic_common_right:
                if (mPresenter != null) {
                    mPresenter.getShareexErciseBook(exerciseId, couId, mTrainingCampId);
                }
                break;
            case R.id.tv_exercise:
                Intent intent = new Intent(this, ExerciseDoActivity.class);
                intent.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseId);
                intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBookId);
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, couId);
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, mTrainingCampId);
                intent.putExtra(Constant.BUNDLE_NODE_TASK_ID, mNoteTaskId);
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            finish();
        }
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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
