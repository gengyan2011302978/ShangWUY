package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.MyOrderDetailBean;
import com.phjt.shangxueyuan.bean.PayMethodBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.event.TrainingBuySuccessEvent;
import com.phjt.shangxueyuan.di.component.DaggerOpenVipComponent;
import com.phjt.shangxueyuan.mvp.contract.OpenVipContract;
import com.phjt.shangxueyuan.mvp.presenter.OpenVipPresenter;
import com.phjt.shangxueyuan.utils.AES;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.OnPayResultListener;
import com.phjt.shangxueyuan.utils.PayUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.paylibrary.wechatpay.WxPayBean;
import com.phsxy.utils.LogUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


/**
 * @author: Created by Template
 * date: 03/27/2020 15:37
 * company: ????????????
 * description: ?????????????????????
 */
public class OpenVipActivity extends BaseActivity<OpenVipPresenter> implements OpenVipContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_order_name)
    TextView tvOrderName;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;
    @BindView(R.id.tv_alipay_check_status)
    TextView tvAlipayCheckStatus;
    @BindView(R.id.tv_wechat_pay_check_status)
    TextView tvWechatPayCheckStatus;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.line_alipay)
    TextView lineAlipay;
    @BindView(R.id.ll_wechat_pay)
    LinearLayout llWechatPay;
    @BindView(R.id.line_wechat_pay)
    TextView lineWechatPay;
    @BindView(R.id.ll_coupon)
    LinearLayout mLlCoupon;
    @BindView(R.id.tv_discount_item)
    TextView mTvDiscountItem;
    @BindView(R.id.tv_reduce_price)
    TextView mTvReducePrice;
    @BindView(R.id.tv_count_price)
    TextView mTvCountPrice;

    /**
     * mPayType???????????? 1????????? 2??????
     */
    private int mPayType = 0;
    private int mPayTypeValue = 2;

    private PayUtil mPayUtil;
    private AdvanceOrderBean advanceOrderBean;
    private String cardType;
    /**
     * ??????
     */
    private String realPrice;
    /**
     * ??????id
     */
    private String commodityId;
    /**
     * ?????? 1 ????????????????????????  0 ?????????????????????????????????
     */
    private int activityState;
    /**
     * 1 vip(??????)  2 ??????
     */
    private int type;
    /**
     * ??????????????????id
     */
    private String couponId;
    /**
     * ???????????????????????????
     */
    private boolean hasUseCoupon = false;

    public static final int COUPON_REQUEST_CODE = 100;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOpenVipComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_open_vip;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        commodityId = getIntent().getStringExtra(Constant.BUNDLE_ORDER_COMMODITYID);
        tvOrderName.setText(getIntent().getStringExtra(Constant.BUNDLE_ORDER_NAME));
        realPrice = getIntent().getStringExtra(Constant.BUNDLE_ORDER_REALPRICE);
        cardType = getIntent().getStringExtra(Constant.BUNDLE_ORDER_CARDTYPE);
        activityState = Integer.parseInt(getIntent().getStringExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE));
        type = getIntent().getIntExtra(Constant.BUNDLE_ORDER_TYPE, 1);

        mPayUtil = new PayUtil(this);
        tvCommonTitle.setText("????????????");
        tvVipPrice.setText(String.format("??%s", realPrice));
        mTvCountPrice.setText(String.format("??%s", realPrice));

        if (mPresenter != null) {
            mPresenter.getPayMethod();
            mPresenter.getCouponList(commodityId, type, activityState);
        }
    }

    @Override
    public void getPayMethodSuccess(List<PayMethodBean> pays) {
        String mPayCode = "ALI_PAY_STATUS";
        String mWXCode = "WX_PAY_STATUS";
        String mVlaue = "1";
        for (int i = 0; i < pays.size(); i++) {
            if (pays.get(i) != null && pays.get(i).getCode() != null && pays.get(i).getVlaue() != null) {
                if (pays.get(i).getCode().equals(mPayCode) && pays.get(i).getVlaue().equals(mVlaue)) {
                    llAlipay.setVisibility(View.VISIBLE);
                    lineAlipay.setVisibility(View.VISIBLE);
                    mPayType = 1;
                    setImgChoosePayType();
                }
                if (pays.get(i).getCode().equals(mWXCode) && pays.get(i).getVlaue().equals(mVlaue)) {
                    llWechatPay.setVisibility(View.VISIBLE);
                    lineWechatPay.setVisibility(View.VISIBLE);
                    if (mPayType == 0) {
                        mPayType = 2;
                        setImgChoosePayType();
                    }
                }
            }
        }
    }

    @Override
    public void getPayMethodFailed(String msg) {
        showMessage(msg);
    }

    /**
     * ??????????????????????????????
     *
     * @param couponBean ??????????????????????????????????????????????????????
     */
    @Override
    public void showCoupon(CouponBean couponBean) {
        if (couponBean != null) {
            hasUseCoupon = true;
            couponId = couponBean.getUserCouponId();
            showCouponContent(couponBean.getCouponContent(), couponBean.getCouponContentPrice(), couponBean.getOptimalAmount());
        } else {
            couponId = null;
            showCouponContent(0, 0, 0);
        }
    }

    @OnClick({R.id.iv_common_back, R.id.tv_alipay_check_status, R.id.tv_wechat_pay_check_status,
            R.id.ll_alipay, R.id.ll_wechat_pay, R.id.tv_submit, R.id.ll_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_alipay_check_status:
            case R.id.ll_alipay:
                //?????????????????????
                mPayType = 1;
                //??????????????????????????????
                setImgChoosePayType();
                break;
            case R.id.tv_wechat_pay_check_status:
            case R.id.ll_wechat_pay:
                //??????????????????
                mPayType = 2;
                //??????????????????????????????
                setImgChoosePayType();
                break;
            case R.id.tv_submit:
                //??????????????????
                doSubmit();
                break;
            case R.id.ll_coupon:
                //?????????
                if (hasUseCoupon) {
                    Intent couponIntent = new Intent(this, CouponActivity.class);
                    couponIntent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, commodityId);
                    couponIntent.putExtra(Constant.BUNDLE_ORDER_TYPE, type);
                    couponIntent.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, activityState);
                    couponIntent.putExtra(CouponActivity.COUPON_ID, couponId);
                    startActivityForResult(couponIntent, COUPON_REQUEST_CODE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????????????????
     */
    private void setImgChoosePayType() {
        if (mPayType == 1) {
            tvAlipayCheckStatus.setBackgroundResource(R.drawable.login_iv_arguemnt_check);
            tvWechatPayCheckStatus.setBackgroundResource(R.drawable.login_iv_arguemnt_uncheck);
        } else if (mPayType == mPayTypeValue) {
            tvAlipayCheckStatus.setBackgroundResource(R.drawable.login_iv_arguemnt_uncheck);
            tvWechatPayCheckStatus.setBackgroundResource(R.drawable.login_iv_arguemnt_check);
        }
    }

    @SingleClick
    private void doSubmit() {
        if (mPresenter != null) {
            mPresenter.requestOrderDetail(commodityId, mPayType, activityState, couponId, type);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("====================requestCode::" + requestCode + "data::" + data);
        if (requestCode == COUPON_REQUEST_CODE && data != null) {
            couponId = data.getStringExtra(CouponActivity.COUPON_ID);
            int couponType = data.getIntExtra(CouponActivity.COUPON_TYPE, 0);
            double couponContentPrice = data.getDoubleExtra(CouponActivity.COUPON_CONTENT_PRICE, 0);
            double optimalAmount = data.getDoubleExtra(CouponActivity.COUPON_OPTIMAL_AMOUNT, 0);

            showCouponContent(couponType, couponContentPrice, optimalAmount);
        }
    }

    /**
     * ?????????????????????
     *
     * @param couponType         0.???????????? 1.????????????
     * @param couponContentPrice ?????????????????????
     * @param optimalAmount      ????????????
     */
    private void showCouponContent(int couponType, double couponContentPrice, double optimalAmount) {
        if (TextUtils.isEmpty(couponId)) {
            mTvDiscountItem.setVisibility(View.GONE);
            if (hasUseCoupon) {
                mTvReducePrice.setText("??????????????????");
                mTvReducePrice.setTextColor(ContextCompat.getColor(this, R.color.color_F2242D));
            } else {
                mTvReducePrice.setText("??????????????????");
                mTvReducePrice.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            }
        } else {
            mTvReducePrice.setTextColor(ContextCompat.getColor(this, R.color.color_F2242D));
            if (couponType == 0) {
                mTvDiscountItem.setVisibility(View.GONE);
            } else {
                mTvDiscountItem.setVisibility(View.VISIBLE);
                mTvDiscountItem.setText(String.format("%s???", couponContentPrice * 10));
            }
            mTvReducePrice.setText(String.format("-??%s", optimalAmount));
        }

        //??????????????????????????????????????????
        double payPrice = new BigDecimal(Double.parseDouble(realPrice) - optimalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (payPrice < 0.01) {
            payPrice = 0.01;
        }
        mTvCountPrice.setText(String.format("??%s", payPrice));
    }

    @Override
    public void requestOrderDetailSuccess(String bean) {
        String json = AES.decryptFromBase64(bean, "A1719AE38660EB36");
        MyOrderDetailBean myOrderDetailBean = new Gson().fromJson(json, MyOrderDetailBean.class);
        if (mPresenter != null) {
            mPresenter.sendRequestYuOrder(String.valueOf(myOrderDetailBean.getId()), String.valueOf(mPayType));
        }
    }

    @Override
    public void requestOrderDetailFailed(String msg) {
        showMessage(msg);
    }

    @Override
    public void sendRequestYuOrderSuccess(AdvanceOrderBean bean) {
        advanceOrderBean = bean;
        gotoPay(bean);
    }

    @Override
    public void sendRequestYuOrderFailed(String msg) {
        showTips(msg);
    }

    private void gotoPay(AdvanceOrderBean bean) {
        if (mPayType == 1) {
            if (!TextUtils.isEmpty(bean.getAliPerOrder())) {
                if (isAliPayInstalled(this)) {
                    payAlipayResult(bean.getAliPerOrder());
                } else {
                    showTips("???????????????????????????");
                }
            }
        } else if (mPayType == mPayTypeValue) {
            if (bean.getWxPerOrder() != null) {
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    payWXpayResult(bean.getWxPerOrder());
                } else {
                    showTips("????????????????????????");
                }
            }
        }
    }

    /**
     * ?????????
     */
    private void payAlipayResult(String orderInfo) {
        mPayUtil.aliPay(orderInfo, new OnPayResultListener() {
            @Override
            public void onPaySuccess() {
                EventBusManager.getInstance().post(new EventBean(EventBean.PAY_SUCCESS, null));
                EventBusManager.getInstance().post(new TrainingBuySuccessEvent(2));
            }

            @Override
            public void onPayError(String error) {
                showPayFailDialog();//??????????????????
            }

            @Override
            public void onPayCancel() {
                showPayFailDialog();//??????????????????
            }
        });
    }

    /**
     * ??????
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
     * ??????????????????
     */
    private void showPayFailDialog() {
        DialogUtils.showPayFailDialog(this, getString(R.string.myorder_fail_str), getString(R.string.myorder_fail_hintstr), new DialogUtils.OnClickDialogListener() {
            @Override
            public void clickCancel() {
                UmengUtil.onUmengUtils(OpenVipActivity.this, UmengUtil.EVENT_ID_PAY_CANCEL, "???????????????????????????");
                finish();
            }

            @Override
            public void clickOk() {
                gotoPay(advanceOrderBean);
            }
        });
    }

    private boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PAY_SUCCESS) {
                showTips("????????????");
                this.finish();
                UmengUtil.onEventPayPage(OpenVipActivity.this, cardType, mPayType);
            } else if (type == EventBean.PAY_FAILED) {
                showPayFailDialog();
            }
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
        showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
