package com.phjt.shangxueyuan.mvp.ui.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.MyOrderDetailBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.event.TrainingBuySuccessEvent;
import com.phjt.shangxueyuan.di.component.DaggerOrderConfirmComponent;
import com.phjt.shangxueyuan.mvp.contract.OrderConfirmContract;
import com.phjt.shangxueyuan.mvp.presenter.OrderConfirmPresenter;
import com.phjt.shangxueyuan.utils.AES;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.OnPayResultListener;
import com.phjt.shangxueyuan.utils.PayUtil;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.paylibrary.wechatpay.WxPayBean;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 15:51
 * @description :订单确认页面
 */
public class OrderConfirmActivity extends BaseActivity<OrderConfirmPresenter> implements OrderConfirmContract.View, TextWatcher {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_commodity_info)
    TextView mTvCommodityInfo;
    @BindView(R.id.tv_currency)
    TextView mTvCurrency;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.et_commodity_count)
    EditText mEtCommodityCount;
    @BindView(R.id.iv_reduce)
    ImageView mIvReduce;
    @BindView(R.id.tv_currency_count)
    TextView mTvCurrencyCount;
    @BindView(R.id.tv_exchange_currency)
    TextView mTvExchangeCurrency;
    @BindView(R.id.iv_exchange_currency)
    ImageView mIvExchangeCurrency;
    @BindView(R.id.tv_exchange_bocc)
    TextView mTvExchangeBocc;
    @BindView(R.id.iv_exchange_bocc)
    ImageView mIvExchangeBocc;
    @BindView(R.id.tv_refund_tip)
    TextView mTvRefundTip;
    @BindView(R.id.tv_exchange_proportion)
    TextView mTvExchangeProportion;
    @BindView(R.id.exchange_confirm)
    TextView mExchangeConfirm;

    /**
     * 商品信息
     */
    private String mCommodityId = "";
    private String mCommodityName;
    private String mPrice;
    /**
     * 商品类型
     * 1-vip  2-大专栏 3-训练营 4-问答  13-开通达人专栏  14-开通直播宣讲 15-付费直播  16-一对一咨询    （个人添加，默认0为商品）
     */
    private int mCommodityType;
    /**
     * 兑换比例
     */
    private double mRadio;
    /**
     * 支付方式  9.学豆 10.bocc
     */
    private int mTrainingPayMethod = 9;
    /**
     * 支付方式 	1:学豆 2:bocc
     */
    private int mVirtualPayMethod = 1;
    /**
     * 购买金额
     */
    private double mCurrencyPrice;
    private double mBoccPrice;


    private PayUtil mPayUtil;
    private AdvanceOrderBean advanceOrderBean;
    /**
     * mPayType支付方式 1支付宝 2微信
     */
    private String mPayType = "0";
    private Dialog payTypeDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderConfirmComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText("确认订单");
        mEtCommodityCount.addTextChangedListener(this);

        Intent intent = getIntent();
        mCommodityId = intent.getStringExtra(Constant.BUNDLE_ORDER_COMMODITYID);
        mCommodityName = intent.getStringExtra(Constant.BUNDLE_ORDER_NAME);
        mPrice = intent.getStringExtra(Constant.BUNDLE_ORDER_REALPRICE);
        mCommodityType = intent.getIntExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 0);

        if (mCommodityType == 0) {
            mIvAdd.setClickable(true);
            mIvReduce.setClickable(true);
            mEtCommodityCount.setFocusable(true);
            mEtCommodityCount.setFocusableInTouchMode(true);
        } else {
            mIvAdd.setClickable(false);
            mIvReduce.setClickable(false);
            mEtCommodityCount.setFocusable(false);
            mEtCommodityCount.setFocusableInTouchMode(false);
        }


        if (mCommodityType == 15) {
            mCommodityName = "付费直播：" + mCommodityName;
        }
        mTvCommodityInfo.setText(mCommodityName);
        mTvCurrency.setText(String.format("%s学豆", String_Utils.linearNmber(mPrice)));
        setPrice();

        if (mPresenter != null) {
            mPresenter.getExchangeRatio();
        }
        mPayUtil = new PayUtil(this);
    }

    @Override
    public void showExchangeRatio(Double ratio) {
        DecimalFormat dF = new DecimalFormat("0.000");
        mRadio = Double.parseDouble(dF.format(1 / ratio));
        mTvExchangeProportion.setText(String.format("*当前学豆与BOCC兑换比例为1:%s", String_Utils.linearNmber(String.valueOf(mRadio))));
        setPrice();
    }

    private void setPrice() {
        String commodityCount = mEtCommodityCount.getText().toString().trim();
        mCurrencyPrice = Integer.parseInt(commodityCount) * Double.parseDouble(mPrice);
        mBoccPrice = mCurrencyPrice * mRadio;

        String strCurrencyPrice = String_Utils.linearNmber(String.valueOf(mCurrencyPrice));
        mTvCurrencyCount.setText(String.format("%s学豆", strCurrencyPrice));
        mTvExchangeCurrency.setText(String.format("%s学豆", strCurrencyPrice));
        mTvExchangeBocc.setText(String.format("≈ %s BOCC", String_Utils.linearNmber(String.valueOf(mBoccPrice))));
    }

    @Override
    public void paySuccess() {
        if (mCommodityType == 1) {
            EventBusManager.getInstance().post(new EventBean(EventBean.PAY_SUCCESS, Constant.ORDER_PAY_SUCCESS));
            showMessage("兑换成功");
        } else if (mCommodityType == 3) {
            EventBusManager.getInstance().post(new TrainingBuySuccessEvent(2));
            showMessage("兑换成功");
        } else if (mCommodityType == 4) {
            EventBusManager.getInstance().post(new EventBean(EventBean.ANSWERS_PAY, mVirtualPayMethod, Integer.parseInt(mEtCommodityCount.getText().toString().trim())));
        } else if (mCommodityType == 15) {
            setResult(MZBasePlayerActivity.PAY_RESULT_CODE);
            showMessage("兑换成功");
        } else {
            Intent intent = new Intent(this, OrderPaySuccessActivity.class);
            intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, mCommodityType);
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void showCurrencyNotEnough() {
        //先获取用户当前的学豆剩余（防止学豆变更），再弹框
        if (mPresenter != null) {
            mPresenter.getUserAssetsInfo();
        }
    }

    @Override
    public void showBoccNotEnough() {
        //判断BOC钱包入口状态是开启
        if (mPresenter != null) {
            mPresenter.isShowQftPointFlag();
        }
    }

    @Override
    public void showUserAssetsAndPay(UserAssetsBean userAssetsBean) {
        payTypeDialog = DialogUtils.showRechargeDialog(this, String.valueOf(mCurrencyPrice),
                true, userAssetsBean.getUserStudyCoin(),
                new DialogUtils.OnClickDialogListener() {
                    @Override
                    public void recharge(String payPrice, int payMethod) {
                        if (mPresenter != null) {
                            if ("0.00".equals(payPrice)) {
                                payPrice = "0.01";
                            }
                            mPresenter.createStudyCoinOrder(payPrice, payMethod);
                        }
                    }
                });
    }

    @Override
    public void isShowQftPointFlagSuccess(int status) {
        if (status == 0) {
            //开启
            DialogUtils.showCancelSureDialog(this, "BOCC余额不足", "",
                    "取消", "去兑换", new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            Intent intent = new Intent(OrderConfirmActivity.this, QiitongWebViewActivity.class);
                            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
                            startActivity(intent);
                        }
                    });
        } else {
            //未开启
            DialogUtils.showCancelSureDialog(this, "BOCC余额不足", "请去做任务获取",
                    "取消", "去做任务", new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            Intent intent = new Intent(OrderConfirmActivity.this, MyPointsActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }

    @Override
    public void createStudyCoinOrderSuccess(String baseBean, int payMethod) {
        String json = AES.decryptFromBase64(baseBean, "A1719AE38660EB36");
        MyOrderDetailBean myOrderDetailBean = new Gson().fromJson(json, MyOrderDetailBean.class);
        if (mPresenter != null) {
            mPresenter.sendRequestYuOrder(String.valueOf(myOrderDetailBean.getId()), String.valueOf(payMethod));
        }
    }

    @Override
    public void sendRequestYuOrderSuccess(AdvanceOrderBean bean, String payType) {
        advanceOrderBean = bean;
        gotoPay(bean, payType);
    }

    private void gotoPay(AdvanceOrderBean bean, String payType) {
        if ("1".equals(payType)) {
            if (!TextUtils.isEmpty(bean.getAliPerOrder())) {
                if (isAliPayInstalled(this)) {
                    mPayType = payType;
                    payAlipayResult(bean.getAliPerOrder(), payType);
                } else {
                    showTips("请安装支付宝客户端");
                }
            }
        } else if ("2".equals(payType)) {
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

    /**
     * 支付宝
     */
    private void payAlipayResult(String orderInfo, String payType) {
        mPayUtil.aliPay(orderInfo, new OnPayResultListener() {
            @Override
            public void onPaySuccess() {
                orderExchange();
                if (payTypeDialog != null && payTypeDialog.isShowing()) {
                    payTypeDialog.dismiss();
                }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PAY_SUCCESS && !TextUtils.equals(eventBean.getMsg(), Constant.ORDER_PAY_SUCCESS)) {
                orderExchange();
                if (payTypeDialog != null && payTypeDialog.isShowing()) {
                    payTypeDialog.dismiss();
                }
            } else if (type == EventBean.PAY_FAILED) {
                showPayFailDialog(mPayType);
            }
        }
    }

    /**
     * 支付失败弹框
     */
    private void showPayFailDialog(String payType) {
        DialogUtils.showPayFailDialog(this, getString(R.string.myorder_fail_str), getString(R.string.myorder_fail_hintstr), new DialogUtils.OnClickDialogListener() {
            @Override
            public void clickCancel() {
                UmengUtil.onUmengUtils(OrderConfirmActivity.this, UmengUtil.EVENT_ID_PAY_CANCEL, "支付失败，确认离开");
                finish();
            }

            @Override
            public void clickOk() {
                gotoPay(advanceOrderBean, payType);
            }
        });
    }

    @OnClick({R.id.iv_common_back, R.id.iv_add, R.id.iv_reduce, R.id.iv_exchange_currency, R.id.iv_exchange_bocc})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                SoftKeyboardUtil.hideSoftKeyboard(this);
                finish();
                break;
            case R.id.iv_add:
                isAdd(true);
                break;
            case R.id.iv_reduce:
                isAdd(false);
                break;
            case R.id.iv_exchange_currency:
                mTrainingPayMethod = 9;
                mVirtualPayMethod = 1;
                mIvExchangeCurrency.setImageResource(R.drawable.login_iv_arguemnt_check);
                mIvExchangeBocc.setImageResource(R.drawable.login_iv_arguemnt_uncheck);
                break;
            case R.id.iv_exchange_bocc:
                mTrainingPayMethod = 10;
                mVirtualPayMethod = 2;
                mIvExchangeCurrency.setImageResource(R.drawable.login_iv_arguemnt_uncheck);
                mIvExchangeBocc.setImageResource(R.drawable.login_iv_arguemnt_check);
                break;
            default:
                break;
        }
    }

    @SingleClick
    @OnClick(R.id.exchange_confirm)
    public void onConfirmClick(View view) {
        DialogUtils.showCancelSureDialog(this,
                "本次兑换将消耗" + (mVirtualPayMethod == 1 ? mTvExchangeCurrency.getText().toString() : String_Utils.linearNmber(String.valueOf(mBoccPrice)) + " BOCC"),
                "", "取消", "确认兑换",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        orderExchange();
                    }
                });
    }

    /**
     * 立即兑换
     */
    private void orderExchange() {
        if (mPresenter != null) {
            if (mCommodityType == 4) {
                mPresenter.answersConfirmOrder(mCommodityId, mVirtualPayMethod, Integer.parseInt(mEtCommodityCount.getText().toString().trim()));
            } else if (mCommodityType == 1 || mCommodityType == 3) {
                mPresenter.createTrainingOrder(mCommodityId, Integer.parseInt(mEtCommodityCount.getText().toString().trim()),
                        mTrainingPayMethod, mTrainingPayMethod == 9 ? mCurrencyPrice : mBoccPrice, mCommodityType);
            } else if (mCommodityType == 13 || mCommodityType == 14 || mCommodityType == 15 || mCommodityType == 16) {
                mPresenter.getAddOrder(mCommodityId, mCommodityType, mTrainingPayMethod);
            } else {
                mPresenter.exchangeVirtualCommodity(mCommodityId, mVirtualPayMethod, Integer.parseInt(mEtCommodityCount.getText().toString().trim()));
            }
        }
    }

    private void isAdd(boolean isAdd) {
        if (TextUtils.isEmpty(mEtCommodityCount.getText().toString().trim())) {
            mEtCommodityCount.setText("1");
        } else {
            int count = Integer.parseInt(mEtCommodityCount.getText().toString().trim());
            count = isAdd ? count + 1 : count - 1;
            mEtCommodityCount.setText(String.valueOf(count));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mEtCommodityCount.getText().toString().trim())) {
            mEtCommodityCount.setText("1");
            mEtCommodityCount.setSelection(mEtCommodityCount.getText().length());
        } else {
            int count = Integer.parseInt(mEtCommodityCount.getText().toString().trim());
            if (count < 1) {
                mEtCommodityCount.setText("1");
                mEtCommodityCount.setSelection(mEtCommodityCount.getText().length());
            } else if (count > 100) {
                mEtCommodityCount.setText("100");
                mEtCommodityCount.setSelection(mEtCommodityCount.getText().length());
            } else if (s.toString().startsWith("0")) {
                s.replace(0, 1, "");
            }
        }

        setPrice();
    }

    @Override
    public void showLoading() {
        if (mExchangeConfirm != null) {
            mExchangeConfirm.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mExchangeConfirm != null) {
            mExchangeConfirm.setClickable(true);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.show(message);
    }

    private boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }
}