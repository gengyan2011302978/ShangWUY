package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerApplyWithdrawalComponent;
import com.phjt.shangxueyuan.mvp.contract.ApplyWithdrawalContract;
import com.phjt.shangxueyuan.mvp.presenter.ApplyWithdrawalPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.NumberUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 11:37
 * company: 普华集团
 * description: 申请提现
 */
public class ApplyWithdrawalActivity extends BaseActivity<ApplyWithdrawalPresenter> implements ApplyWithdrawalContract.View, TextWatcher {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.et_withdrawal_amount)
    EditText mEtWithdrawalAmount;
    @BindView(R.id.iv_clear)
    ImageView mIvClear;
    @BindView(R.id.tv_withdrawal_amount)
    TextView mTvWithdrawalAmount;
    @BindView(R.id.tv_apply_withdrawal)
    TextView mTvApplyWithdrawal;
    /**
     * 用户资产
     */
    private double mUserAsset;
    /**
     * 最低提现金额
     */
    private double mMinimumWithdrawal;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplyWithdrawalComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_apply_withdrawal;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText(getResources().getString(R.string.wallet_apply_withdrawal));
        mEtWithdrawalAmount.addTextChangedListener(this);

        Intent intent = getIntent();
        mUserAsset = intent.getDoubleExtra(Constant.BUNDLE_USER_ASSET, 0);
        mMinimumWithdrawal = intent.getDoubleExtra(Constant.BUNDLE_MINIMUM_WITHDRAWAL, 0);

        mEtWithdrawalAmount.setHint(getResources().getString(R.string.withdrawal_amount_lowest) + NumberUtil.getStrDouble(mMinimumWithdrawal));
        mTvWithdrawalAmount.setText(String.format("￥%s", NumberUtil.getStrDouble(mUserAsset)));
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.iv_clear, R.id.tv_apply_withdrawal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_clear:
                mEtWithdrawalAmount.setText("");
                break;
            case R.id.tv_apply_withdrawal:
                String withdrawalAmount = mEtWithdrawalAmount.getText().toString();
                if (TextUtils.isEmpty(withdrawalAmount)) {
                    showMessage("请输入提现金额");
                    return;
                }
                double applyWithdrawal = Double.parseDouble(withdrawalAmount);
                if (applyWithdrawal < mMinimumWithdrawal) {
                    showMessage("最低提现金额为" + mMinimumWithdrawal + "元");
                    return;
                } else if (applyWithdrawal > mUserAsset) {
                    showMessage("可提现金额不足");
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.confirmWithdrawal(applyWithdrawal);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void confirmWithdrawalSuccess() {
        showMessage("提现受理中，请耐心等待");
        setResult(101);
        finish();
    }

    @Override
    public void showLoading() {
        if (mTvApplyWithdrawal != null) {
            mTvApplyWithdrawal.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mTvApplyWithdrawal != null) {
            mTvApplyWithdrawal.setClickable(true);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = s.toString();
        if (TextUtils.isEmpty(content)) {
            mIvClear.setVisibility(View.GONE);
            mTvApplyWithdrawal.setSelected(false);
        } else {
            //不能以"."开头
            if (content.startsWith(".")) {
                mEtWithdrawalAmount.setText("");
                return;
            }
            //判断小数点后只能输入两位
            if (content.contains(".")) {
                if (content.length() - 1 - content.indexOf(".") > 2) {
                    content = content.subSequence(0, content.indexOf(".") + 3).toString();
                    mEtWithdrawalAmount.setText(content);
                    mEtWithdrawalAmount.setSelection(content.length());
                }
            }
            //如果第一个数字为0，第二个不为点，就不允许输入
            if (content.startsWith("0") && content.trim().length() > 1) {
                if (!".".equals(content.substring(1, 2))) {
                    mEtWithdrawalAmount.setText(content.subSequence(0, 1));
                    mEtWithdrawalAmount.setSelection(1);
                    return;
                }
//                if (s.toString().length() == 4) {
//                    //针对输入0.00的特殊处理
//                    if (Double.valueOf(s.toString()) < 0.01) {
//                        Toast.makeText(this, "最小为0.01", Toast.LENGTH_SHORT).show();
//                        mEtWithdrawalAmount.setText("0.01");
//                        mEtWithdrawalAmount.setSelection(mEtWithdrawalAmount.getText().toString().trim().length());
//                    }
//                }
            }

            mIvClear.setVisibility(View.VISIBLE);
            double applyWithdrawal = Double.parseDouble(content);
            if (applyWithdrawal >= mMinimumWithdrawal && applyWithdrawal <= mUserAsset) {
                mTvApplyWithdrawal.setSelected(true);
            } else {
                mTvApplyWithdrawal.setSelected(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

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
