package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerAuthenticationComponent;
import com.phjt.shangxueyuan.mvp.contract.AuthenticationContract;
import com.phjt.shangxueyuan.mvp.presenter.AuthenticationPresenter;
import com.phjt.shangxueyuan.utils.BitmapUtils;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.view.roundImg.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :身份认证
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/05/11 15:02
 */
public class AuthenticationActivity extends BaseActivity<AuthenticationPresenter> implements AuthenticationContract.View, FileUploadUtils.UploadCallback {
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_re_positive)
    TextView tvRePositive;
    @BindView(R.id.tv_negative_side_positive)
    TextView tvNegativeSidePositive;
    @BindView(R.id.tv_in_hand)
    TextView tvInHand;
    @BindView(R.id.iv_positive)
    RoundedImageView ivPositive;
    @BindView(R.id.iv_negative_side)
    RoundedImageView ivNegativeSide;
    @BindView(R.id.iv_in_hand)
    RoundedImageView ivInHand;
    @BindView(R.id.cl_accomplish)
    ConstraintLayout clAccomplish;
    @BindView(R.id.view_accomp)
    View viewAccomp;
    @BindView(R.id.scrollview_accomp)
    NestedScrollView scrollViewAccomp;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.tv_auth_submit)
    TextView mTvAuthSubmit;


    private int clickIndex;
    private String cerFrontUrl = "";
    private String cerBackUrl = "";
    private String handCardUrl = "";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerAuthenticationComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_authentication;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("身份认证");
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.iv_positive, R.id.iv_negative_side, R.id.tv_auth_submit, R.id.tv_back_to_homepage, R.id.iv_in_hand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                onBackPressed();
                break;
            case R.id.iv_positive:
                clickIndex = 0;
                startOpenCamera();
                break;
            case R.id.iv_negative_side:
                clickIndex = 1;
                startOpenCamera();
                break;
            case R.id.iv_in_hand:
                clickIndex = 2;
                startOpenCamera();
                break;
            case R.id.tv_auth_submit:
                if (TextUtils.isEmpty(cerFrontUrl)) {
                    TipsUtil.show("请上传身份证正面照片");
                    return;
                }
                if (TextUtils.isEmpty(cerBackUrl)) {
                    TipsUtil.show("请上传身份证反面照片");
                    return;
                }
//                if (TextUtils.isEmpty(handCardUrl)) {
//                    TipsUtil.show("请上传手持身份证正面照");
//                    return;
//                }
                if (mPresenter != null) {
                    mPresenter.addUserCertificateInfo(cerFrontUrl, cerBackUrl, handCardUrl);
                }
                break;
            case R.id.tv_back_to_homepage:
                finish();
                break;
            default:
                break;
        }
    }


    @SuppressLint("CheckResult")
    public void startOpenCamera() {
        new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE).subscribe(aBoolean -> {
            if (aBoolean) {
                PictureSelector.create(AuthenticationActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .imageEngine(GlideEngine.createGlideEngine())
                        .isCompress(true)
                        .selectionMode(PictureConfig.SINGLE)
                        .isEnableCrop(false)
                        .circleDimmedLayer(false)
                        .showCropGrid(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

            } else {
                TipsUtil.show("没有权限");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (!selectList.isEmpty()) {
                    LocalMedia localMedia = selectList.get(0);

                    File file = new File(localMedia.getCompressPath());
                    if (!file.exists()) {
                        return;
                    }
                    String uploadPath = BitmapUtils.compressImageUpload(file.getAbsolutePath());
                    switch (clickIndex) {
                        case 0:
                            FileUploadUtils.upload(this, new File(uploadPath), clickIndex, this);
                            break;
                        case 1:
                            FileUploadUtils.upload(this, new File(uploadPath), clickIndex, this);
                            break;
                        case 2:
                            FileUploadUtils.upload(this, new File(uploadPath), clickIndex, this);
                        default:
                            break;
                    }
                    break;
                }
            default:
                break;
        }
    }

    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        if (bean != null && bean.realUrl != null) {
            switch (bean.what) {
                case 0:
                    cerFrontUrl = bean.realUrl;
                    if (ivPositive != null) {
                        GlideUtils.load(bean.realUrl, ivPositive);
                    }
                    tvRePositive.setText("重新上传");
                    tvRePositive.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    cerBackUrl = bean.realUrl;
                    GlideUtils.load(bean.realUrl, ivNegativeSide);
                    tvNegativeSidePositive.setText("重新上传");
                    tvNegativeSidePositive.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    handCardUrl = bean.realUrl;
                    GlideUtils.load(bean.realUrl, ivInHand);
                    tvInHand.setText("重新上传");
                    tvInHand.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onFileUploadError(Throwable e) {
        showMessage("图片上传失败");
    }


    @Override
    public void addSuccess() {
        tvCommonTitle.setText("提示");
        tvExplain.setText(R.string.exercise_explain);
        clAccomplish.setVisibility(View.VISIBLE);
        viewAccomp.setVisibility(View.GONE);
        scrollViewAccomp.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        if (mTvAuthSubmit != null) {
            mTvAuthSubmit.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mTvAuthSubmit != null) {
            mTvAuthSubmit.setClickable(true);
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
