package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerOrderPaySuccessComponent;
import com.phjt.shangxueyuan.mvp.contract.OrderPaySuccessContract;
import com.phjt.shangxueyuan.mvp.presenter.OrderPaySuccessPresenter;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallHomeFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/21 14:32
 * @description : 订单 支付成功页面
 */
public class OrderPaySuccessActivity extends BaseActivity<OrderPaySuccessPresenter> implements OrderPaySuccessContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_to_mall)
    TextView tvToMall;
    @BindView(R.id.tv_customer_tip)
    TextView mTvCustomerTip;

    /**
     * 商品类型
     * 1-vip  2-大专栏 3-训练营 4-问答  13-开通达人专栏  14-开通直播宣讲 15-付费直播  16-一对一咨询    （个人添加，默认0为商品）
     */
    private int mCommodityType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderPaySuccessComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_pay_success;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvCommonTitle.setText("兑换成功");
        mCommodityType = getIntent().getIntExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 0);

        if (mCommodityType == 0) {
            tvToMall.setText("商城首页");
            mTvCustomerTip.setText("熵吾优客服将尽快和您联系，交付商品");
        } else {
            tvToMall.setText("返回");
            mTvCustomerTip.setText("熵吾优客服将尽快和您联系");
        }
    }

    @OnClick({R.id.iv_common_back, R.id.tv_to_mall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
            case R.id.tv_to_mall:
                toJumpPage();
                break;
            default:
                break;
        }
    }

    /**
     * 页面跳转统一管理
     */
    private void toJumpPage() {
        if (mCommodityType == 0) {
            Intent intent = new Intent(this, MallHomeFragment.class);
            startActivity(intent);
        } else {
            //刷新webview
            EventBusManager.getInstance().post(new EventBean(EventBean.PAY_SUCCESS, ""));
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toJumpPage();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

}