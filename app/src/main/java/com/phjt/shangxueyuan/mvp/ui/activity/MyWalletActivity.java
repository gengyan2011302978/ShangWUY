package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.di.component.DaggerMyWalletComponent;
import com.phjt.shangxueyuan.mvp.contract.MyWalletContract;
import com.phjt.shangxueyuan.mvp.presenter.MyWalletPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.IncomeRecordFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.MyInvitationFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.WithdrawalRecordFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.roundView.RoundTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 09:43
 * company: 普华集团
 * description: 我的钱包
 */
public class MyWalletActivity extends BaseActivity<MyWalletPresenter> implements MyWalletContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView mTvCommonRight;
    @BindView(R.id.tv_withdrawal_amount)
    TextView mTvWithdrawalAmount;
    @BindView(R.id.tv_apply_withdrawal)
    RoundTextView mTvApplyWithdrawal;
    @BindView(R.id.tv_withdrawal_rule)
    TextView mTvWithdrawalRule;
    @BindView(R.id.tab_wallet)
    SlidingTabLayout mTabWallet;
    @BindView(R.id.vp_wallet)
    ViewPager mVpWallet;

    /**
     * 提现记录
     */
    private WithdrawalRecordFragment mWithdrawalRecordFragment;
    /**
     * 用户资产
     */
    private String mUserAsset;
    /**
     * 最低提现金额
     */
    private double mMinimumWithdrawal;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyWalletComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText("邀请记录");
//        mTvCommonRight.setVisibility(View.VISIBLE);
        mTvCommonRight.setText(getResources().getString(R.string.bank_card));

        mWithdrawalRecordFragment = WithdrawalRecordFragment.newInstance();
        String[] titles = {"我的邀请", "收入记录"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MyInvitationFragment.newInstance());
        fragments.add(IncomeRecordFragment.newInstance());
//        fragments.add(mWithdrawalRecordFragment);
        MyHomeAdapter adapter = new MyHomeAdapter(getSupportFragmentManager(), fragments);
        mVpWallet.setAdapter(adapter);
        mTabWallet.setViewPager(mVpWallet, titles);

        getWalletMoneys();
    }

    /**
     * 获取可提现金额、最小提现金额、累积提现金额
     */
    public void getWalletMoneys() {
        if (mPresenter != null) {
//            mPresenter.getWalletMoneys();
            mPresenter.getUserAuthSum();
        }
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_common_right, R.id.tv_apply_withdrawal, R.id.tv_withdrawal_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_common_right:
                Intent applyIntent = new Intent(this, BindingBankCardActivity.class);
                startActivity(applyIntent);
                break;
            case R.id.tv_apply_withdrawal:
//                TipsUtil.show("暂未开放兑换");
                Intent exchangeIntent = new Intent(this, MyWebViewActivity.class);
                exchangeIntent.putExtra(BUNDLE_WEB_TITLE, "");
                exchangeIntent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CURRENCY_EXCHANG_BOCC) + "?type=1");
                startActivity(exchangeIntent);
//                if (mPresenter != null) {
//                    mPresenter.applyWithdrawal();
//                }
                break;
            case R.id.tv_withdrawal_rule:
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_MALL_EXCHANGE_RULE));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void showWithDrawalMoney(String walletMoney) {
        mUserAsset = walletMoney;
//        mMinimumWithdrawal = walletMoneyBean.getLeastAsset();

        mTvWithdrawalAmount.setText(String_Utils.linearNmber(mUserAsset+"")+" BOCC");
//        if (mWithdrawalRecordFragment != null) {
//            mWithdrawalRecordFragment.showWithdrawalAmount(walletMoneyBean.getAlreadyWithdrawAsset());
//        }
    }

    @Override
    public void showBankCardDialog() {
        DialogUtils.showBindingBankCardDialog(this, new DialogUtils.OnCancelSureClick() {
            @Override
            public void clickSure() {
                Intent intent = new Intent(MyWalletActivity.this, BankCardFillInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void applyWithdrawalSuccess() {
        Intent intent = new Intent(this, ApplyWithdrawalActivity.class);
        intent.putExtra(Constant.BUNDLE_USER_ASSET, mUserAsset);
        intent.putExtra(Constant.BUNDLE_MINIMUM_WITHDRAWAL, mMinimumWithdrawal);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWithdrawalRecordFragment != null) {
            mWithdrawalRecordFragment.refreshData();
        }
        getWalletMoneys();
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
