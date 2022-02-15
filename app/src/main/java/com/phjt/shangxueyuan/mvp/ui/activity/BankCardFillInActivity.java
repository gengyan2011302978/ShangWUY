package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ComplexColorCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BankCardFillBean;
import com.phjt.shangxueyuan.di.component.DaggerBankCardFillInComponent;
import com.phjt.shangxueyuan.mvp.contract.BankCardFillInContract;
import com.phjt.shangxueyuan.mvp.presenter.BankCardFillInPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.MyFilter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.RegexUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 15:15
 * company: 普华集团
 * description: 银行卡信息填写
 */
public class BankCardFillInActivity extends BaseActivity<BankCardFillInPresenter> implements BankCardFillInContract.View, TextWatcher {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_account_number)
    EditText mEtAccountNumber;
    @BindView(R.id.et_receiving_bank)
    EditText mEtReceivingBank;
    @BindView(R.id.et_deposit_bank)
    EditText mEtDepositBank;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_card_save)
    RoundTextView mTvCardSave;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBankCardFillInComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bank_card_fill_in;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText(getResources().getString(R.string.binding_bank_card));
        EditText[] etViews = new EditText[]{mEtName, mEtAccountNumber, mEtReceivingBank, mEtDepositBank, mEtPhone};
        for (EditText etView : etViews) {
            etView.addTextChangedListener(this);
        }

        mEtName.setFilters(new InputFilter[]{new MyFilter(10)});
        mEtReceivingBank.setFilters(new InputFilter[]{new MyFilter(10)});
        mEtDepositBank.setFilters(new InputFilter[]{new MyFilter(15)});

        Intent intent = getIntent();
        BankCardFillBean cardFillBean = (BankCardFillBean) intent.getSerializableExtra(Constant.BUNDLE_BANKCARD_INFO);
        if (cardFillBean != null) {
            setCardInfo(cardFillBean);
        }
    }

    /**
     * 回显银行卡信息
     *
     * @param cardFillBean 银行卡信息实体
     */
    private void setCardInfo(BankCardFillBean cardFillBean) {
        mEtName.setText(cardFillBean.getCardholder());
        mEtAccountNumber.setText(cardFillBean.getCardNumber());
        mEtReceivingBank.setText(cardFillBean.getBankName());
        mEtDepositBank.setText(cardFillBean.getBankAddress());
        mEtPhone.setText(cardFillBean.getCardholderPhone());
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_card_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_card_save:
                String name = mEtName.getText().toString().trim();
                String accountNumber = mEtAccountNumber.getText().toString().trim();
                String receivingBank = mEtReceivingBank.getText().toString().trim();
                String depositBank = mEtDepositBank.getText().toString().trim();
                String phone = mEtPhone.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    showMessage("请填写姓名");
                    return;
                } else if (TextUtils.isEmpty(accountNumber)) {
                    showMessage("请填写收款账号");
                    return;
                } else if (accountNumber.length() < 12 || accountNumber.length() > 21) {
                    showMessage("收款账号格式不正确");
                    return;
                } else if (TextUtils.isEmpty(receivingBank)) {
                    showMessage("请填写收款银行");
                    return;
                } else if (TextUtils.isEmpty(depositBank)) {
                    showMessage("请填写开户银行");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    showMessage("请填写持卡人手机号");
                    return;
                } else if (!RegexUtils.isMobileSimple(phone)) {
                    showMessage("手机号格式不正确");
                    return;
                }
                BankCardFillBean bankCardFillBean = new BankCardFillBean();
                bankCardFillBean.setCardholder(name);
                bankCardFillBean.setCardNumber(accountNumber);
                bankCardFillBean.setBankName(receivingBank);
                bankCardFillBean.setBankAddress(depositBank);
                bankCardFillBean.setCardholderPhone(phone);
                if (mPresenter != null) {
                    mPresenter.saveBankCardInfo(bankCardFillBean);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void saveInfoSuccess() {
        setResult(100);
        finish();
    }

    @Override
    public void saveInfoFailed() {
        showMessage("绑定银行卡信息失败");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String name = mEtName.getText().toString().trim();
        String accountNumber = mEtAccountNumber.getText().toString().trim();
        String receivingBank = mEtReceivingBank.getText().toString().trim();
        String depositBank = mEtDepositBank.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(accountNumber) || TextUtils.isEmpty(receivingBank)
                || TextUtils.isEmpty(depositBank) || TextUtils.isEmpty(phone)) {
            mTvCardSave.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.color_E3E3E3));
        } else {
            mTvCardSave.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.color_4E7EF9));
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
