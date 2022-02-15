package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.di.component.DaggerScannerComponent;
import com.phjt.shangxueyuan.mvp.contract.ScannerContract;
import com.phjt.shangxueyuan.mvp.presenter.ScannerPresenter;

import com.phjt.shangxueyuan.R;
import com.tbruyelle.rxpermissions2.RxPermissions;


import butterknife.BindView;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :扫一扫
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */
public class ScannerActivity extends BaseActivity<ScannerPresenter> implements ScannerContract.View, ZXingScannerView.ResultHandler {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.scanner_view)
    ZXingScannerView mScannerView;
    @BindView(R.id.tv_scanner_login)
    TextView tvLogin;
    @BindView(R.id.tv_scanner_cancel)
    TextView tvCancel;
    @BindView(R.id.cl_result)
    ConstraintLayout clResult;
    private int rCode;
    private String mCertificate = "";


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerScannerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scanner;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("扫一扫");

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        sweep();
    }


    @SuppressLint("CheckResult")
    private void sweep() {
        new RxPermissions(this).request(Manifest.permission.CAMERA,
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        // Register ourselves as a handler for scan results.
                        mScannerView.setResultHandler(this);
                        // Start camera on resume
                        mScannerView.startCamera();
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        mScannerView.stopCamera();
        mCertificate = rawResult.getText();
        if (mPresenter != null && !TextUtils.isEmpty(mCertificate)) {
            mPresenter.getScanQRcode(mCertificate);
        }
    }

    @Override
    public void loadDataSuccess() {
        mScannerView.setVisibility(View.GONE);
        clResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDataFailure() {
        mScannerView.stopCamera();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @OnClick({R.id.iv_common_back, R.id.tv_scanner_cancel, R.id.tv_scanner_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                if (mPresenter != null && !TextUtils.isEmpty(mCertificate)) {
                    mPresenter.getScanRcodeConfirm(mCertificate, 0, ScannerActivity.this, true);
                } else {
                    finish();
                }
                break;
            case R.id.tv_scanner_cancel:
                if (mPresenter != null && !TextUtils.isEmpty(mCertificate)) {
                    mPresenter.getScanRcodeConfirm(mCertificate, 0, ScannerActivity.this, true);
                }
                break;
            case R.id.tv_scanner_login:
                if (mPresenter != null && !TextUtils.isEmpty(mCertificate)) {
                    mPresenter.getScanRcodeConfirm(mCertificate, 1, ScannerActivity.this, false);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPresenter != null && !TextUtils.isEmpty(mCertificate)) {
                mPresenter.getScanRcodeConfirm(mCertificate, 0, ScannerActivity.this, true);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
