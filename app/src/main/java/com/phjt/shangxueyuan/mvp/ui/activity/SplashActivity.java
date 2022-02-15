package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.UMInitHelper;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.di.component.DaggerSplashComponent;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.SplashContract;
import com.phjt.shangxueyuan.mvp.presenter.SplashPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.MZLiveUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.SPUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_ADDRESS;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_TYPE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_PLANET_PHONE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;

/**
 * @author: gengyan
 * date:    2020/11/5
 * company: 普华集团
 * description: 应用启动页
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements INotFitsImmersionBar, SplashContract.View {

    private CountDownTimer countDownTimer;
    private String linkType;
    private String linkAddress;
    private String planetPhone;
    private String planetUuid;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        planetUuid = getIntent().getStringExtra("planet_uuid");
        planetPhone = getIntent().getStringExtra(BUNDLE_PLANET_PHONE);
        linkType = getIntent().getStringExtra(BUNDLE_LINK_TYPE);
        linkAddress = getIntent().getStringExtra(BUNDLE_LINK_ADDRESS);
        if (!TextUtils.isEmpty(planetUuid)) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra("planet_uuid", planetUuid);
            startActivity(intent);
        }
        if (!TextUtils.isEmpty(planetPhone)) {
            judgementAction();
            return;
        }

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            checkWebJump();
            finish();
            return;
        }
        if (!SPUtils.getInstance().getBoolean(Constant.AGREEMENT, false)) {
            showPoint();
        } else {
            if (null != mPresenter) {
                //停服公告
                mPresenter.getAnnouncementJson();
            }
        }
        ConstantWeb.getHttpWebAddressList(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 显示温馨提示
     */
    private void showPoint() {
        DialogUtils.showProtocolBoxBackupDialog(this, "温馨提示", "欢迎来到熵吾优！",
                getResources().getString(R.string.welcoming_content), "不同意", "同意",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickCancel() {
                        finish();
                    }

                    @Override
                    public void clickAgreement() {
                        Intent intent = new Intent(SplashActivity.this, MyWebViewActivity.class);
                        intent.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy_policy));
                        intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_AGREEMENT));
                        startActivity(intent);
                    }

                    @Override
                    public void clickPrivacyAgreement() {
                        Intent intent3 = new Intent(SplashActivity.this, MyWebViewActivity.class);
                        intent3.putExtra(BUNDLE_WEB_TITLE, getString(R.string.terms_and_privacy));
                        intent3.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PRIVACY));
                        startActivity(intent3);
                    }

                    @Override
                    public void clickSure() {
                        SPUtils.getInstance().put(Constant.AGREEMENT, true);
                        UMInitHelper.init(getApplicationContext());
                        if (null != mPresenter) {
                            //停服公告
                            mPresenter.getAnnouncementJson();
                        }
                    }
                });
    }

    private void checkLogin() {
        countDownTimer = new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                checkGuide();
            }
        };
        countDownTimer.start();
    }

    private void checkGuide() {
        String gd = SPUtils.getInstance().getString(Constant.GO_GUIDE);
        if (TextUtils.isEmpty(gd) && TextUtils.isEmpty(planetPhone)) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            judgementAction();
        }
    }

    /**
     * 判断下一步去哪儿
     */
    private void judgementAction() {
        String token = SPUtils.getInstance().getString(Constant.SP_TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
            intent.putExtra(BUNDLE_LINK_TYPE, linkType);
            intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, HomePagerActivity.class);
            intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
            intent.putExtra(BUNDLE_LINK_TYPE, linkType);
            intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
            startActivity(intent);
        }

        checkWebJump();

        finish();
    }

    /**
     * 判断H5拉起应用跳转
     */
    private void checkWebJump() {
        Uri uri = getIntent().getData();
        String token = SPUtils.getInstance().getString(Constant.SP_TOKEN, "");
        if (uri != null && !TextUtils.isEmpty(token)) {
            //课程回放  type = 1   活动通知  type=2   训练营 TrainCampCourse  课程详情 VideoCourse
            String type = uri.getQueryParameter("type");
            String trainingCampId = uri.getQueryParameter("id");
            String courseId = uri.getQueryParameter("courseId");
            if (TextUtils.equals(type, "1")) {
                Intent messageCourseIntent = new Intent(this, InteractionMessageActivity.class);
                messageCourseIntent.putExtra(Constant.MESSAGE_HEADER, "课程提醒");
                messageCourseIntent.putExtra(Constant.MESSAGE_TYPE, 2);
                startActivity(messageCourseIntent);
            } else if (TextUtils.equals(type, "2")) {
                Intent messageIntent = new Intent(this, MessageActivity.class);
                startActivity(messageIntent);
            } else if (TextUtils.equals(type, "TrainCampCourse") && !TextUtils.isEmpty(trainingCampId)) {
                Intent trainingIntent = new Intent(this, TrainingCampDetailActivity.class);
                trainingIntent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, trainingCampId);
                startActivity(trainingIntent);
            } else if (TextUtils.equals(type, "VideoCourse") && !TextUtils.isEmpty(courseId)) {
                Intent courseIntent = new Intent(this, CourseDetailActivity.class);
                courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
                startActivity(courseIntent);
            } else if (TextUtils.equals(type, "3")) {
                int liveStyle = 0;
                String ticketId = uri.getQueryParameter("ticketId");
                String mId = uri.getQueryParameter("id");
                String status = uri.getQueryParameter("status");
                String liveStyles = uri.getQueryParameter("liveStyle");
                if (!TextUtils.isEmpty(liveStyles)) {
                    liveStyle = Integer.parseInt(liveStyles);
                }
                MZLiveUtils.toLivePlay(this, ticketId, liveStyle, mId);
            }
        }
    }

    @Override
    public void showMaintenanceDialog(MainAnnouncementBean bean) {
        if (bean.getSign() == 1) {
            DialogUtils.mainAnnoDialog(this, bean.getTitle(), bean.getText());
        } else {
            checkLogin();
        }
    }

    @Override
    public void fail() {
        checkLogin();
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
