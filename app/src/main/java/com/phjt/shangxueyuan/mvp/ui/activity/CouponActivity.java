package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.di.component.DaggerCouponComponent;
import com.phjt.shangxueyuan.mvp.contract.CouponContract;
import com.phjt.shangxueyuan.mvp.presenter.CouponPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.CouponListAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 11/24/2020 11:40
 * company: 普华集团
 * description: 优惠券页面
 */
public class CouponActivity extends BaseActivity<CouponPresenter> implements CouponContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvTitle;
    @BindView(R.id.rv_coupon)
    RecyclerView mRvCoupon;

    /*
     * 空页面
     */
    private View emptyView;

    private CouponListAdapter mCouponAdapter;
    /**
     * 支付页面选中的优惠卷id
     */
    private String couponId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCouponComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_coupon;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvTitle.setText("选择优惠券");
        initRv();

        // commodityId: 商品id   type:1 vip 2 课程   activityState:1未参加过首购活动 0代表已经参加过首购活动
        Intent intent = getIntent();
        String commodityId = intent.getStringExtra(Constant.BUNDLE_ORDER_COMMODITYID);
        int type = intent.getIntExtra(Constant.BUNDLE_ORDER_TYPE, 1);
        int activityState = intent.getIntExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, 0);
        couponId = intent.getStringExtra(CouponActivity.COUPON_ID);

        if (mPresenter != null) {
            mPresenter.getCouponList(commodityId, type, activityState);
        }
    }

    public void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvCoupon.setLayoutManager(layoutManager);
        List<CouponBean> couponBeans = new ArrayList<>();
        mCouponAdapter = new CouponListAdapter(this, couponBeans);
        mRvCoupon.setAdapter(mCouponAdapter);

        emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        mCouponAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        mCouponAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CouponBean couponBean = (CouponBean) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.tv_instructions_item:
                    if (!TextUtils.isEmpty(couponBean.getUseExplain())) {
                        //使用说明
                        DialogUtils.showExchangeVoucheDialog(this, couponBean.getUseExplain(), false, null);
                    }
                    break;
                case R.id.iv_coupon_state_item:
                    resetRvChose(position);
                    break;
                default:
                    break;
            }
        });
    }

    private void resetRvChose(int position) {
        if (mCouponAdapter != null) {
            List<CouponBean> couponBeans = mCouponAdapter.getData();
            for (int i = 0; i < couponBeans.size(); i++) {
                CouponBean couponBean = couponBeans.get(i);
                if (couponBean != null) {
                    if (couponBean.isChose() && position == i) {
                        //点击的是已选中的优惠卷
                        couponBean.setChose(false);
                    } else {
                        couponBean.setChose(i == position);
                    }
                }
            }
            mCouponAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCouponList(List<CouponBean> couponBeanList) {
        if (mCouponAdapter != null) {
            for (int i = 0; i < couponBeanList.size(); i++) {
                CouponBean couponBean = couponBeanList.get(i);
                if (couponBean != null && TextUtils.equals(couponId, couponBean.getUserCouponId())) {
                    couponBean.setChose(true);
                }
            }
            mCouponAdapter.setNewData(couponBeanList);
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
        finishPage();
    }

    /**
     * 优惠券id
     */
    public static final String COUPON_ID = "coupon_id";
    /**
     * 优惠券类型 1.满减优惠 2.折扣优惠
     */
    public static final String COUPON_TYPE = "coupon_type";
    /**
     * 折扣和满减数
     */
    public static final String COUPON_CONTENT_PRICE = "coupon_content_price";
    /**
     * 优惠金额
     */
    public static final String COUPON_OPTIMAL_AMOUNT = "coupon_optimal_amount";

    private void finishPage() {
        CouponBean couponBean = getBackCouponData();
        Intent intent = new Intent();
        if (couponBean != null) {
            intent.putExtra(COUPON_ID, couponBean.getUserCouponId());
            intent.putExtra(COUPON_TYPE, couponBean.getCouponContent());
            intent.putExtra(COUPON_CONTENT_PRICE, couponBean.getCouponContentPrice());
            intent.putExtra(COUPON_OPTIMAL_AMOUNT, couponBean.getOptimalAmount());
        }
        setResult(OpenVipActivity.COUPON_REQUEST_CODE, intent);
        finish();
    }

    /**
     * 返回时，返回选取的优惠卷信息
     */
    private CouponBean getBackCouponData() {
        if (mCouponAdapter != null) {
            List<CouponBean> couponBeans = mCouponAdapter.getData();
            for (int i = 0; i < couponBeans.size(); i++) {
                CouponBean couponBean = couponBeans.get(i);
                if (couponBean != null && couponBean.isChose()) {
                    return couponBean;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishPage();
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

}
