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
import android.text.TextUtils;
import android.view.WindowManager;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.di.component.DaggerCourseLocalPlayComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.CourseLocalPlayContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseLocalPlayPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 09/07/2020 09:20
 * company: 普华集团
 * description: 本地视频播放
 */
public class CourseLocalPlayActivity extends BaseActivity<CourseLocalPlayPresenter> implements
        CourseLocalPlayContract.View, IWithoutImmersionBar, SuperPlayerView.OnSuperPlayerViewCallback {

    @BindView(R.id.spv_video)
    SuperPlayerView mSuperPlayerView;

    /**
     * 下载 传递的本地路径
     */
    private String localPath;
    /**
     * 课程id
     */
    public String courseId;
    /**
     * 倍速实体
     */
    private List<TCMultipleBean> mMultipleBeanList;
    /**
     * 当前倍速 全局记录
     */
    private float mSpeedLevel = 1f;
    /**
     * 是否是直播回放  1横屏播放 2竖屏播放
     */
    private boolean isLivePlayback;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCourseLocalPlayComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_course_local_play;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(false)
                .statusBarColor(R.color.color_333333)
                .statusBarDarkFont(false)
                .setOnBarListener(barProperties -> {
                    //横竖屏切换
                    setFullScreen(this, !barProperties.isPortrait());
                })
                .init();
        mSuperPlayerView.setPlayerViewCallback(this);
        initMultipleList();
        isLivePlayback = getIntent().getIntExtra(Constant.ISLIVEPLAYBACK,0)==1?true:false;
        Intent intent = getIntent();
        if (intent != null) {
            courseId = intent.getStringExtra(Constant.BUNDLE_COURSE_ID);
            localPath = intent.getStringExtra(Constant.BUNDLE_LOCAL_PATH);
            if (!TextUtils.isEmpty(localPath)) {
                playLocalVideo();
            }//storage/emulated/0/Android/data/com.phjt.shangxueyuan/files/txdownload/8cea73709f0ea1e039acc0752bc7a848.m3u8.sqlite
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

    /**
     * 播放-本地下载视频
     */
    public void playLocalVideo() {
        //显示loading view
        if (mSuperPlayerView.mControllerWindow != null) {
            mSuperPlayerView.mControllerWindow.showLoadingView();
            mSuperPlayerView.mControllerWindow.localPlayHide();

            if (isLivePlayback) {
                mSuperPlayerView.mControllerWindow.hideFullScreen();
                mSuperPlayerView.mControllerWindow.isShowIvLock = true;
            }
        }
        if (mSuperPlayerView.mControllerFullScreen != null) {
            mSuperPlayerView.mControllerFullScreen.localPlayHide();
        }
        //更新标题
        mSuperPlayerView.updateTitle(getIntent().getStringExtra(Constant.BUNDLE_POINT_NAME));

        //观看进度赋值
        long pointWatchDuration = 0L;
        mSuperPlayerView.setStartPlayTime(pointWatchDuration);

        SuperPlayerModel model = new SuperPlayerModel();
        model.url = localPath;
        mSuperPlayerView.playWithModel(model);

        //播放速率赋值
        if (mSuperPlayerView.mVodPlayer != null) {
            mSuperPlayerView.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSuperPlayerView.getLayoutParams();
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        mSuperPlayerView.setLayoutParams(params);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏-1
            mSuperPlayerView.mControllerWindow.show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏-2
            if (isFirst) {
                setFullScreen(this, true);
            } else {
                ImmersionBar.with(this).fullScreen(true).init();
                isFirst = true;
            }
        }
        mSuperPlayerView.dispatchConfigurationChanged(newConfig);
    }

    private boolean isFirst;

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
        finish();
    }

    @Override
    public void onMultiple(int type) {
        setMultipleBeanList();
        if (type == 1) {
            mSuperPlayerView.mControllerWindow.showMultipleView(mMultipleBeanList, true);
        } else if (type == 2) {
            mSuperPlayerView.mControllerFullScreen.showMultipleView(mMultipleBeanList);
        }
    }

    @Override
    public void onMultipleChange(float speedLevel) {
        this.mSpeedLevel = speedLevel;
    }

    @Override
    public void onShowMore(int type) {

    }

    @Override
    public void onWeChatCallBack(int statusId, String name) {

    }

    @Override
    public void onCollection() {

    }

    @Override
    public void onDownload(int type) {

    }

    @Override
    public void onPlayEnd() {

    }

    @Override
    public void onPlayEvent(boolean isPause) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView != null) {
            mSuperPlayerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSuperPlayerView != null) {
            mSuperPlayerView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mSuperPlayerView != null) {
            if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
                mSuperPlayerView.onPause();
            }
            mSuperPlayerView.resetPlayer();
        }
        super.onDestroy();
    }
}
