package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerExchangeVoucherComponent;
import com.phjt.shangxueyuan.mvp.contract.ExchangeVoucherContract;
import com.phjt.shangxueyuan.mvp.presenter.ExchangeVoucherPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExchangeVoucherAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :兑换礼券
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */
public class ExchangeVoucherActivity extends BaseActivity<ExchangeVoucherPresenter> implements ExchangeVoucherContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_exchange_voucher)
    RecyclerView rvExchangeVoucher;
    @BindView(R.id.srl_exchange_voucher)
    SmartRefreshLayout srlExchangeVoucher;
    @BindView(R.id.tv_invalid_coupons)
    TextView tvInvalidCoupons;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.tv_expiration_reminder)
    TextView tvExpirationReminder;

    @BindView(R.id.view_invalid_coupons)
    View viewCoupons;
    private List<InitIndexSiteInfoBean> indexList;
    private ExchangeVoucherAdapter adapter;
    private View mEmptyView;
    /**
     * mType:0 可用优惠券 ; 1 失效优惠券
     */
    private int mType;
    /**
     * mInVoucherType:1 消息跳去优惠券;
     */
    private int mInVoucherType;

    private int pageNo = 1;
    public final int PAGE_SIZE = 10;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerExchangeVoucherComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exchange_voucher;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("优惠券");
        tvCommonRight.setText("兑换码");
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color666666));
        tvCommonRight.setVisibility(View.VISIBLE);
        indexList = new ArrayList<>();
        mType = getIntent().getIntExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
        mInVoucherType = getIntent().getIntExtra(Constant.MESSAGE_IN_VOUCHER_TYPE, 0);
        if (mPresenter != null) {
            mPresenter.getInitIndexSiteInfo();
            mPresenter.getExchangeCouponList(mType, pageNo, PAGE_SIZE, true);
        }

        if (mType == 1) {
            tvExpirationReminder.setVisibility(View.VISIBLE);
            viewCoupons.setVisibility(View.GONE);
            tvInvalidCoupons.setVisibility(View.GONE);
            tvCommonRight.setVisibility(View.GONE);
        } else {
            viewCoupons.setVisibility(View.VISIBLE);
            tvInvalidCoupons.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvExchangeVoucher.setLayoutManager(layoutManager);
        adapter = new ExchangeVoucherAdapter(this);
        rvExchangeVoucher.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        srlExchangeVoucher.setOnRefreshLoadMoreListener(this);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<CouponBean> data = adapter.getData();
            int jumpType = data.get(position).getJumpType();
            String useExplain = data.get(position).getUseExplain();
            switch (view.getId()) {
                case R.id.iv_to_use_item:
                    //去使用
                    setTouseItem(jumpType);
                    break;
                case R.id.tv_instructions_item:
                    //使用说明
                    if (!TextUtils.isEmpty(useExplain)) {
                        DialogUtils.showExchangeVoucheDialog(ExchangeVoucherActivity.this, useExplain, true, null);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 去使用
     * jumpType: 1 vip 2 大专栏
     */
    private void setTouseItem(int jumpType) {
        if (jumpType == 1) {
            VipUtil.toVipPage(this);
        } else {
            if (mInVoucherType == 1) {
                Intent intent = new Intent(this, HomePagerActivity.class);
                startActivity(intent);
            } else {
                finish();
            }
            EventBus.getDefault().post(new EventBean(EventBean.PUSH_HOME, ""));
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void getInitIndexSiteInfoListSuccess(List<InitIndexSiteInfoBean> bean) {
        if (bean != null && bean.size() > 0) {
            indexList = bean;
        }
    }

    @Override
    public void getExchangeCouponListSuccess(BaseListBean<CouponBean> bean, boolean isRefresh) {
        adapter.setType(mType);
        srlExchangeVoucher.setEnableLoadMore(pageNo < bean.getTotalPage());
        if (isRefresh) {
            adapter.setNewData(new ArrayList<>());
            if (bean.getRecords().size() > 0) {
                adapter.setNewData(bean.getRecords());
            } else if (bean.getRecords().size() == 0) {
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srlExchangeVoucher.setEnableLoadMore(false);
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srlExchangeVoucher.finishRefresh();
        } else {
            if (bean.getRecords().size() > 0) {
                adapter.addData(bean.getRecords());
            } else {
                srlExchangeVoucher.setEnableLoadMore(false);
            }
            srlExchangeVoucher.finishLoadMore();
        }
        if (mType != 1) {
            tvInvalidCoupons.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getExchangeCouponListFailed(boolean isRefresh) {
        if (adapter != null && mEmptyView != null && tvInvalidCoupons != null&& isRefresh) {
            mEmptyView.setVisibility(View.VISIBLE);
            tvInvalidCoupons.setVisibility(View.GONE);
        }
        if (srlExchangeVoucher != null) {
            srlExchangeVoucher.finishRefresh();
            srlExchangeVoucher.finishLoadMore();
        }
    }


    @OnClick({R.id.iv_common_back, R.id.tv_invalid_coupons, R.id.tv_common_right})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_invalid_coupons:
                //查看失效优惠券
                intent = new Intent(this, ExchangeVoucherActivity.class);
                intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 1);
                break;
            case R.id.tv_common_right:
                //兑换码
                if (indexList.size() > 0) {
                    intent = new Intent(this, ExchangeCodeActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, indexList.get(0).getId());
                }
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            mPresenter.getExchangeCouponList(mType, pageNo, PAGE_SIZE, isReFresh);
        }
    }
}
