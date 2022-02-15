package com.phjt.shangxueyuan.mvp.ui.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.MyOrderDetailBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerCurrencyRechargeComponent;
import com.phjt.shangxueyuan.mvp.contract.CurrencyRechargeContract;
import com.phjt.shangxueyuan.mvp.presenter.CurrencyRechargePresenter;
import com.phjt.shangxueyuan.utils.AES;
import com.phjt.shangxueyuan.utils.OnPayResultListener;
import com.phjt.shangxueyuan.utils.PayUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.paylibrary.wechatpay.WxPayBean;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/25 09:39
 * @description : 学豆充值
 */
public class CurrencyRechargeActivity extends BaseActivity<CurrencyRechargePresenter> implements CurrencyRechargeContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView mTvCommonRight;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.et_currency)
    EditText mEtCurrency;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    @BindView(R.id.tv_recharge_tip)
    TextView mTvRechargeTip;
    private Dialog dialog;
    private PayUtil mPayUtil;
    private AdvanceOrderBean advanceOrderBean;
    /**
     * mPayType支付方式 1支付宝 2微信
     */
    private String mPayType = "0";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCurrencyRechargeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_currency_recharge;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText("学豆充值");
        mTvCommonRight.setVisibility(View.VISIBLE);

        mPayUtil = new PayUtil(this);
        if (mPresenter != null) {
            mPresenter.getUserAssetsInfo();
        }
    }

    @OnClick({R.id.iv_common_back, R.id.tv_recharge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_recharge:
                if (mEtCurrency.getText().toString().equals("")) {
                    showTips("请输入金额");
                    return;
                }
                dialog = DialogUtils.showRechargeDialog(this, mEtCurrency.getText().toString(),
                        false, null,
                        new DialogUtils.OnClickDialogListener() {
                            @Override
                            public void recharge(String payPrice, int payMethod) {
                                if (null != mPresenter) {
                                    Objects.requireNonNull(mPresenter).createStudyCoinOrder(payPrice, payMethod);
                                }
                            }
                        });
                break;
            default:
                break;
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
    public void createStudyCoinOrderSuccess(String baseBean, int mPayType) {
        String json = AES.decryptFromBase64(baseBean, "A1719AE38660EB36");
        MyOrderDetailBean myOrderDetailBean = new Gson().fromJson(json, MyOrderDetailBean.class);
        if (mPresenter != null) {
            mPresenter.sendRequestYuOrder(String.valueOf(myOrderDetailBean.getId()), String.valueOf(mPayType));
        }
    }

    private boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    /**
     * 支付宝
     */
    private void payAlipayResult(String orderInfo, String payType) {
        mPayUtil.aliPay(orderInfo, new OnPayResultListener() {
            @Override
            public void onPaySuccess() {
                setResult(MZBasePlayerActivity.RECHARGE_RESULT_CODE);
                finish();
            }

            @Override
            public void onPayError(String error) {
                showPayFailDialog(payType);//支付失败弹框
            }

            @Override
            public void onPayCancel() {
                showPayFailDialog(payType);//支付失败弹框
            }
        });
    }

    /**
     * 微信
     */
    private void payWXpayResult(AdvanceOrderBean.WXPerOrder bean) {
        WxPayBean wxBean = new WxPayBean();
        wxBean.setAppid(bean.getAppid_wx());
        wxBean.setPartnerid(bean.getPartnerid());
        wxBean.setPrepayid(bean.getPrepayid());
        wxBean.setNoncestr(bean.getNoncestr());
        wxBean.setTimestamp(bean.getTimestamp());
        wxBean.setPackageValue(bean.getPackage_wx());
        wxBean.setSign(bean.getSign());
        mPayUtil.weChatPay(bean.getAppid_wx(), wxBean);
    }


    /**
     * 支付失败弹框
     */
    private void showPayFailDialog(String payType) {
        DialogUtils.showPayFailDialog(this, getString(R.string.myorder_fail_str), getString(R.string.myorder_fail_hintstr), new DialogUtils.OnClickDialogListener() {
            @Override
            public void clickCancel() {
                UmengUtil.onUmengUtils(CurrencyRechargeActivity.this, UmengUtil.EVENT_ID_PAY_CANCEL, "支付失败，确认离开");
                finish();
            }

            @Override
            public void clickOk() {
                gotoPay(advanceOrderBean, payType);
            }
        });
    }

    @Override
    public void createStudyCoinOrderFailed(String msg) {

    }

    @Override
    public void sendRequestYuOrderSuccess(AdvanceOrderBean bean, String payType) {
        advanceOrderBean = bean;
        gotoPay(bean, payType);

    }

    @Override
    public void showUserAssets(UserAssetsBean assetsBean) {
        mTvCommonRight.setText("学豆余额：" + String_Utils.linearNmber(assetsBean.getUserStudyCoin()));
        mTvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color999999));
        mTvCommonRight.setTextSize(13f);
    }

    private void gotoPay(AdvanceOrderBean bean, String payType) {
        if (payType.equals("1")) {
            if (!TextUtils.isEmpty(bean.getAliPerOrder())) {
                if (isAliPayInstalled(this)) {
                    mPayType = payType;
                    payAlipayResult(bean.getAliPerOrder(), payType);
                } else {
                    showTips("请安装支付宝客户端");
                }
            }
        } else if (payType.equals("2")) {
            if (bean.getWxPerOrder() != null) {
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    mPayType = payType;
                    payWXpayResult(bean.getWxPerOrder());
                } else {
                    showTips("请安装微信客户端");
                }
            }
        }
    }

    @Override
    public void sendRequestYuOrderFailed(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PAY_SUCCESS) {
                showTips("支付成功");
                setResult(MZBasePlayerActivity.RECHARGE_RESULT_CODE);
                this.finish();
            } else if (type == EventBean.PAY_FAILED) {
                showPayFailDialog(mPayType);
            }
        }
    }
}