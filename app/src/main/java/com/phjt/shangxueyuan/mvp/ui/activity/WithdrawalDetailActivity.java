package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.google.gson.Gson;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.WithdrawalRecordBean;
import com.phjt.shangxueyuan.di.component.DaggerWithdrawalDetailComponent;
import com.phjt.shangxueyuan.mvp.contract.WithdrawalDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.WithdrawalDetailPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.NumberUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:05
 * company: 普华集团
 * description: 提现记录详情
 */
public class WithdrawalDetailActivity extends BaseActivity<WithdrawalDetailPresenter> implements WithdrawalDetailContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.tv_card_num)
    TextView mTvCardNum;
    @BindView(R.id.tv_name)
    TextView mTvName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawalDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdrawal_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText(getResources().getString(R.string.withdrawal_detail));

        Intent intent = getIntent();
        String id = intent.getStringExtra(Constant.BUNDLE_WITHDRAWAL_ID);
        if (mPresenter != null) {
            mPresenter.getWithdrawalDetail(id);
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showWithdrawalDetail(WithdrawalRecordBean recordBean) {
        mTvAmount.setText(String.format("￥%s", NumberUtil.getStrDouble(recordBean.getWithdrawMoney())));
        mTvDate.setText(recordBean.getApplyTime());
        //0待审核 1已审核
        Integer auditState = recordBean.getAuditState();
        if (auditState == 0) {
            mTvState.setText("受理中");
            mTvState.setTextColor(ContextCompat.getColor(this, R.color.color_2775FE));
        } else {
            mTvState.setText("已提现");
            mTvState.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        }

        WithdrawalRecordBean.WithdrawAccountInfoBean withdrawAccountInfo =
                new Gson().fromJson(recordBean.getWithdrawAccountInfo(), WithdrawalRecordBean.WithdrawAccountInfoBean.class);
        if (withdrawAccountInfo != null) {
            mTvBank.setText(withdrawAccountInfo.getBankName());
            mTvCardNum.setText(withdrawAccountInfo.getCardNumber());
            mTvName.setText(withdrawAccountInfo.getCardholder());
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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

}
