package com.phjt.shangxueyuan.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.ActivityLifecycleCallbacksImpl;
import com.phjt.shangxueyuan.bean.TrainingCatalogSecondBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.bean.event.CommentEventBean;
import com.phjt.shangxueyuan.di.component.DaggerTrainingPlayComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.TrainingPlayContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingPlayPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCommentAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.TrainingUtils;
import com.phjt.shangxueyuan.widgets.popupwindow.TrainingCatalogPop;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerVideoId;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_TYPE;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: ????????????
 * description: ?????????????????????
 */
public class TrainingPlayActivity extends BaseActivity<TrainingPlayPresenter> implements
        TrainingPlayContract.View, IWithoutImmersionBar, SuperPlayerView.OnSuperPlayerViewCallback, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.layout)
    ConstraintLayout mLayout;
    @BindView(R.id.spv_video)
    SuperPlayerView mSpvVideo;
    @BindView(R.id.iv_course_back)
    ImageView mIvCourseBack;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.tv_training_title)
    TextView mTvTrainingTitle;
    @BindView(R.id.tv_training_time)
    TextView mTvTrainingTime;
    @BindView(R.id.tv_training_study_count)
    TextView mTvTrainingStudyCount;
    @BindView(R.id.tv_training_comment_count)
    TextView mTvTrainingCommentCount;
    @BindView(R.id.tv_training_catalog)
    TextView mTvTrainingCatalog;
    @BindView(R.id.rv_training_comment)
    RecyclerView mRvTrainingComment;

    /**
     * ??????????????? ??????
     */
    private int mConfig = Configuration.ORIENTATION_PORTRAIT;
    private int videoHeight;
    /**
     * ????????????
     */
    private List<TCMultipleBean> mMultipleBeanList;
    /**
     * ???????????? ????????????
     */
    private float mSpeedLevel = 1f;
    /**
     * ???????????????
     */
    private TrainingDetailBean mDetailBean;
    /**
     * ???????????????????????????
     */
    private TrainingCatalogSecondBean mSecondBean;
    /**
     * ?????????id
     */
    private String mTrainingCampId;
    /**
     * ????????????id
     */
    private String mSecondId;
    /**
     * ?????????????????????1.?????? 2.?????????
     */
    private int mTaskType;

    /*
     * ?????????
     */
    private View emptyView;
    private TrainingCommentAdapter commentAdapter;

    private NotesEditingFragment mNotesEditingFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingPlayComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_training_play;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDetailBean = (TrainingDetailBean) bundle.getSerializable(Constant.BUNDLE_TRAINING_DETAIL_BEAN);
            mSecondBean = (TrainingCatalogSecondBean) bundle.getSerializable(Constant.BUNDLE_TRAINING_PLAY_BEAN);
        }
        //????????????????????????
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_333333)
                .statusBarDarkFont(false).keyboardEnable(true)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> {
                    //????????????????????????????????????
                    if (isPopup) {

                    } else {

                    }
                })
                .setOnBarListener(barProperties -> {
                    //???????????????
                    setFullScreen(this, !barProperties.isPortrait());
                })
                .init();
        videoHeight = AutoSizeUtils.dp2px(this, 210f);
        mSpvVideo.setPlayerViewCallback(this);
        initMultipleList();
        mSpvVideo.setTimeReport(new SuperPlayerView.TimeReport() {
            @Override
            public void submitLookTime(long currentTime, long countTime) {
                //??????60s????????????????????????
                if (mPresenter != null) {
                    mPresenter.updateTaskRecordById(mTrainingCampId, mSecondId, currentTime == countTime ? 1 : 0, currentTime, false);
                }
            }

            @Override
            public void submitEndTime(long currentTime, long countTime) {
                //??????????????????
                if (mPresenter != null) {
                    mPresenter.updateTaskRecordById(mTrainingCampId, mSecondId, 1, countTime, false);
                }
            }

            @Override
            public void getCurrentTime(long currentTime, long countTime) {
                //?????? ????????????????????? ??? ?????????
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvTrainingComment.setLayoutManager(layoutManager);
        //??????RecycleView item?????????????????????
        ((SimpleItemAnimator) Objects.requireNonNull(mRvTrainingComment.getItemAnimator())).setSupportsChangeAnimations(false);
        commentAdapter = new TrainingCommentAdapter(this, null);
        mRvTrainingComment.setAdapter(commentAdapter);
        commentAdapter.setOnItemChildClickListener(this);
        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        commentAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        if (mDetailBean != null) {
            mTrainingCampId = mDetailBean.getId();
            getTrainingComment();
        }

        if (mSecondBean != null) {
            playVideo(mSecondBean);
        }
    }

    /**
     * ??????????????????
     */
    private void getTrainingComment() {
        if (mPresenter != null && mSecondBean != null) {
            mPresenter.getTrainingComment(mSecondBean.getId());
        }
    }

    private void initMultipleList() {
        mMultipleBeanList = new ArrayList<>();
        mMultipleBeanList.add(new TCMultipleBean(1f));
        mMultipleBeanList.add(new TCMultipleBean(1.25f));
        mMultipleBeanList.add(new TCMultipleBean(1.5f));
        mMultipleBeanList.add(new TCMultipleBean(2f));
    }

    private void setMultipleBeanList() {
        for (int i = 0; i < mMultipleBeanList.size(); i++) {
            TCMultipleBean multipleBean = mMultipleBeanList.get(i);
            multipleBean.setSelect(multipleBean.getSpeedLevel() == mSpeedLevel);
        }
    }

    @Override
    public void showTrainingComment(List<TrainingCommentBean.DiaryListBean> diaryList) {
        commentAdapter.setNewData(diaryList);
        if (diaryList != null && !diaryList.isEmpty()) {
            mTvTrainingCommentCount.setVisibility(View.VISIBLE);
            mTvTrainingCommentCount.setText(String.format("???%s???", CountNumUtils.getCountNum(diaryList.size())));
        } else {
            mTvTrainingCommentCount.setVisibility(View.GONE);
            showTrainingCommentEmpty();
        }
    }

    @Override
    public void showTrainingCommentEmpty() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void trainingCommentLikeSuccess(TrainingCommentBean.DiaryListBean commentBean, int position) {
        if (commentAdapter != null) {
            commentAdapter.notifyItemChanged(position);
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void updateTimeSuccess() {
        finish();
    }

    @Override
    public void playEndAndRefreshTrainingData() {
        //????????????????????????????????????????????????
        EventBusManager.getInstance().post(Constant.EVENT_TRAINING_CAMP);
    }

    /**
     * ??????item??????????????????
     */
    @SingleClick
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        TrainingCommentBean.DiaryListBean commentBean = (TrainingCommentBean.DiaryListBean) adapter.getData().get(position);
        if (commentBean != null) {
            switch (view.getId()) {
                case R.id.iv_like:
                case R.id.tv_like:
                    //??????
                    if (mPresenter != null && checkBuyState()) {
                        mPresenter.trainingCommentLike(commentBean, position);
                    }
                    break;
                case R.id.iv_notes_reply:
                case R.id.tv_reply:
                    //??????
                    if (checkBuyState()) {
                        commentBean.setPosition(position);
                        Intent intent = new Intent(this, TrainingCommentDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.BUNDLE_TRAINING_COMMENT_BEAN, commentBean);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, Constant.COMMENT_DETAIL_REQUEST_CODE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * ???????????? ???????????????
     *
     * @return true???????????? false:????????????
     */
    private boolean checkBuyState() {
        if (mDetailBean != null) {
            if (mDetailBean.isBuy()) {
                return true;
            } else {
                TrainingUtils.checkBuyTime(this, mDetailBean, true);
                return false;
            }
        } else {
            return false;
        }
    }

    @SingleClick
    @OnClick({R.id.iv_course_back, R.id.iv_play, R.id.tv_write_comment, R.id.tv_training_catalog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_course_back:
                finish();
                break;
            case R.id.iv_play:
                break;
            case R.id.tv_write_comment:
                if (checkBuyState()) {
                    showEditDialog();
                }
                break;
            case R.id.tv_training_catalog:
                if (mDetailBean != null && checkBuyState()) {
                    TrainingCatalogPop catalogPop = new TrainingCatalogPop(this);
                    catalogPop.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    catalogPop.showCatalogList(mDetailBean);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     */
    private void showEditDialog() {
        if (null == mNotesEditingFragment) {
            mNotesEditingFragment = new NotesEditingFragment();
        }
        if (!mNotesEditingFragment.isAdded() && !TextUtils.isEmpty(mTrainingCampId) && !TextUtils.isEmpty(mSecondId)) {
            //????????????
            onPause();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_COURSE_ID, mTrainingCampId);
            bundle.putString(Constant.BUNDLE_TRAINING_SECOND_ID, mSecondId);
            bundle.putBoolean(NotesEditingFragment.BUNDLE_IS_COMMENT, true);
            bundle.putInt(BUNDLE_TYPE, 0);
            mNotesEditingFragment.setArguments(bundle);
            mNotesEditingFragment.show(getSupportFragmentManager(), "training_comment_dialog");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.COMMENT_DETAIL_REQUEST_CODE && resultCode == Constant.COMMENT_DETAIL_RESULT_CODE && data != null) {
            //???????????????????????????????????????
            TrainingCommentBean.DiaryListBean commentBean = (TrainingCommentBean.DiaryListBean) data.getSerializableExtra(Constant.BUNDLE_TRAINING_COMMENT_BEAN);
            if (commentAdapter != null && commentBean != null) {
                int position = commentBean.getPosition();
                TrainingCommentBean.DiaryListBean diaryListBean = commentAdapter.getData().get(position);
                diaryListBean.setLikeNum(commentBean.getLikeNum());
                diaryListBean.setLikestatus(commentBean.getLikestatus());
                diaryListBean.setCommentNum(commentBean.getCommentNum());
                diaryListBean.setReplyVoList(commentBean.getReplyVoList());
                commentAdapter.notifyItemChanged(position);
            }
        } else if (null != mNotesEditingFragment) {
            mNotesEditingFragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * ????????????
     *
     * @param secondBean ??????????????????
     */
    private void playVideo(TrainingCatalogSecondBean secondBean) {
        this.mSecondBean = secondBean;
        mSecondId = secondBean.getId();
        mTaskType = secondBean.getTaskType();

        mTvTrainingTitle.setText(secondBean.getOtherName());
        mTvTrainingTime.setText(String.format("???????????????%s", secondBean.getUpdateTime()));
        mTvTrainingStudyCount.setText(String.format(Locale.getDefault(), "???????????????%d", secondBean.getReadNum()));
        //??????????????????
        if (mPresenter != null) {
            mPresenter.updateTaskById(mSecondId);
        }

        //???????????????????????????view?????????????????????
        if (mTaskType != 1) {
            return;
        }

        mIvCourseBack.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);

        //??????loading view???????????????
        if (mSpvVideo.mControllerWindow != null) {
            mSpvVideo.mControllerWindow.showLoadingView();
            mSpvVideo.updateTitle(secondBean.getOtherName());
            mSpvVideo.mControllerWindow.localPlayHide();
        }
        if (mSpvVideo.mControllerFullScreen != null) {
            mSpvVideo.mControllerFullScreen.localPlayHide();
        }

        //??????????????????
        Long lastWatchTime = secondBean.getLastWatchTime();
        mSpvVideo.setStartPlayTime(lastWatchTime);

        SuperPlayerModel model = new SuperPlayerModel();
        model.appId = BuildConfig.TENCENT_PLAY_APPID;
        model.videoId = new SuperPlayerVideoId();
        model.videoId.fileId = secondBean.getVideoId();
        mSpvVideo.playWithModel(model);

        //??????????????????
        if (mSpvVideo.mVodPlayer != null) {
            mSpvVideo.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(TrainingCatalogSecondBean secondBean) {
        if (secondBean != null) {
            //????????????
            playVideo(secondBean);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CommentEventBean eventBean) {
        if (eventBean != null) {
            //??????????????????
            getTrainingComment();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(TrainingDetailBean detailBean) {
        if (detailBean != null) {
            //?????????????????????
            this.mDetailBean = detailBean;
        }
    }

    /**
     * ---------------------------------- SuperPlayerView??????????????? ?????? ----------------------------------
     */
    @Override
    public void onStartFullScreenPlay() {
        hideStatusBar();
    }

    @Override
    public void onStopFullScreenPlay() {
        showStatusBar();
    }

    @Override
    public void onClickSmallReturnBtn() {
        onBackCheck();
    }

    @Override
    public void onShowMore(int type) {

    }

    @Override
    public void onWeChatCallBack(int statusId, String name) {

    }

    @Override
    public void onMultiple(int type) {
        setMultipleBeanList();
        if (type == 1) {
            mSpvVideo.mControllerWindow.showMultipleView(mMultipleBeanList, false);
        } else if (type == 2) {
            mSpvVideo.mControllerFullScreen.showMultipleView(mMultipleBeanList);
        }
    }

    @Override
    public void onMultipleChange(float speedLevel) {
        this.mSpeedLevel = speedLevel;
    }

    @Override
    public void onCollection() {

    }

    @Override
    public void onDownload(int type) {

    }

    @Override
    public void onPlayEnd() {
        //??????????????????????????? ???????????????????????????
        if (mDetailBean != null && !mDetailBean.isBuy()) {
            TrainingUtils.tryLookEndTimeCheck(this, mDetailBean);
        }
    }

    @Override
    public void onPlayEvent(boolean isPause) {

    }

    /**
     * ---------------------------------- SuperPlayerView??????????????? ?????? ----------------------------------
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackCheck();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ?????????????????????
     * ??????????????????????????????
     */
    private void onBackCheck() {
        if (mTaskType == 1 && mPresenter != null && mSpvVideo != null && mSpvVideo.currentTime > 30) {
            long currentTime = mSpvVideo.currentTime;
            long countTime = mSpvVideo.countTime;
            if (currentTime > countTime || (countTime - currentTime) < 4) {
                currentTime = countTime;
            }
            mPresenter.updateTaskRecordById(mTrainingCampId, mSecondId, currentTime == countTime ? 1 : 0, currentTime, true);
        } else {
            finish();
        }
    }


    /**
     * ?????????????????????
     */
    public void hideCommentLayout() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //??????-1
            mConfig = Configuration.ORIENTATION_PORTRAIT;
            mSpvVideo.getLayoutParams().height = videoHeight;
            mSpvVideo.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mSpvVideo.mControllerWindow.show();

            mTvTrainingCatalog.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //??????-2
            mConfig = Configuration.ORIENTATION_LANDSCAPE;
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSpvVideo.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mSpvVideo.setLayoutParams(params);
            ImmersionBar.with(this).fullScreen(true).init();

            hideCommentLayout();
            mTvTrainingCatalog.setVisibility(View.GONE);
        }
        mSpvVideo.dispatchConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            AutoSizeCompat.autoConvertDensity(super.getResources(), 375f,
                    AutoSizeConfig.getInstance().getScreenWidth() < AutoSizeConfig.getInstance().getScreenHeight());
        }
        return super.getResources();
    }

    public void setFullScreen(Activity activity, boolean isfull) {
        ImmersionBar.with(activity).fullScreen(isfull).fitsSystemWindows(!isfull)
                .hideBar(isfull ? BarHide.FLAG_HIDE_BAR : BarHide.FLAG_SHOW_BAR).init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSpvVideo != null && mTaskType == 1) {
            mSpvVideo.onResume();
        }
        //???????????????????????????????????????
        EventBusManager.getInstance().post(Constant.EVENT_TRAINING_CAMP);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!ActivityLifecycleCallbacksImpl.isBackground && mSpvVideo != null) {
            mSpvVideo.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mSpvVideo != null) {
            if (mSpvVideo.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
                mSpvVideo.onPause();
            }
            mSpvVideo.resetPlayer();
        }
        super.onDestroy();
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
