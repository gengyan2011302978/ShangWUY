package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BankCardFillBean;
import com.phjt.shangxueyuan.di.component.DaggerBindingBankCardComponent;
import com.phjt.shangxueyuan.mvp.contract.BindingBankCardContract;
import com.phjt.shangxueyuan.mvp.presenter.BindingBankCardPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.view.roundView.RoundTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 17:26
 * company: 普华集团
 * description: 绑定银行卡
 */
public class BindingBankCardActivity extends BaseActivity<BindingBankCardPresenter> implements BindingBankCardContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.add_card_bg)
    View mAddCardBg;
    @BindView(R.id.tv_add_card)
    TextView mTvAddCard;
    @BindView(R.id.empty_group)
    Group mEmptyGroup;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.tv_card_num)
    TextView mTvCardNum;
    @BindView(R.id.tv_edit)
    RoundTextView mTvEdit;
    @BindView(R.id.cv_card)
    CardView mCvCard;

    /**
     * 银行卡信息
     */
    private BankCardFillBean mBankCardBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindingBankCardComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_binding_bank_card;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText(getResources().getString(R.string.binding_bank_card));

        if (mPresenter != null) {
            mPresenter.getBankCardInfo();
        }
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_edit, R.id.tv_add_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_edit:
                Intent editIntent = new Intent(this, BankCardFillInActivity.class);
                editIntent.putExtra(Constant.BUNDLE_BANKCARD_INFO, mBankCardBean);
                startActivityForResult(editIntent, 101);
                break;
            case R.id.tv_add_card:
                Intent addIntent = new Intent(this, BankCardFillInActivity.class);
                startActivityForResult(addIntent, 101);
                break;
            default:
                break;
        }
    }

    @Override
    public void showBankCardEmpty() {
        mEmptyGroup.setVisibility(View.VISIBLE);
        mCvCard.setVisibility(View.GONE);
    }

    @Override
    public void showBankCardInfo(BankCardFillBean bankCardBean) {
        mEmptyGroup.setVisibility(View.GONE);
        mCvCard.setVisibility(View.VISIBLE);

        mBankCardBean = bankCardBean;
        mTvName.setText(bankCardBean.getCardholder());
        mTvBank.setText(bankCardBean.getBankName());

        String cardNum = bankCardBean.getCardNumber();
        if (TextUtils.isEmpty(cardNum)) {
            cardNum = "";
        } else if (cardNum.length() > 4) {
            StringBuilder sbCardNum = new StringBuilder();
            for (int i = 0; i < cardNum.length() - 4; i++) {
                sbCardNum.append("*");
                if ((i + 1) % 4 == 0) {
                    sbCardNum.append(" ");
                }
            }
            cardNum = sbCardNum.toString() + cardNum.substring(cardNum.length() - 4);
        }
        mTvCardNum.setText(cardNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.getBankCardInfo();
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
