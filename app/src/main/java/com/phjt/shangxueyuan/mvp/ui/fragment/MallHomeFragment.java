package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerMallHomeComponent;
import com.phjt.shangxueyuan.mvp.contract.MallHomeContract;
import com.phjt.shangxueyuan.mvp.presenter.MallHomePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.MallExchangeRecordActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.view.roundImg.RoundedImageView;
import com.phsxy.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:43
 * @description : 商城首页
 */
public class MallHomeFragment extends BaseFragment<MallHomePresenter> implements MallHomeContract.View {

    @BindView(R.id.iv_mall_title)
    TextView mIvMallTitle;
    @BindView(R.id.iv_mall_head)
    RoundedImageView mIvMallHead;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_currency_count)
    TextView mTvCurrencyCount;
    @BindView(R.id.tv_bocc_count)
    TextView mTvBoccCount;
    @BindView(R.id.stl_mall)
    SlidingTabLayout mStlMall;
    @BindView(R.id.vp_mall)
    ViewPager mVpMall;

    /**
     * 对用户是否可见
     */
    private boolean isVisibleToUser;

    public static MallHomeFragment newInstance() {
        Bundle args = new Bundle();
        MallHomeFragment fragment = new MallHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerMallHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mall_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MallListFragment.newInstance(0));
        fragments.add(MallListFragment.newInstance(1));

        String[] titles = new String[]{"虚拟商品", "实物商品"};
        MyHomeAdapter adapter = new MyHomeAdapter(getChildFragmentManager(), fragments);
        mVpMall.setAdapter(adapter);
        mVpMall.setOffscreenPageLimit(fragments.size());
        mStlMall.setViewPager(mVpMall, titles);

        String nickName = SPUtils.getInstance().getString(Constant.SP_NICK_NAME);
        String photoUrl = SPUtils.getInstance().getString(Constant.SP_AVATAR);
        AppImageLoader.loadResUrl(photoUrl, mIvMallHead, R.drawable.iv_mine_avatar);
        mTvNickname.setText(nickName);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        getUserAssetsInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserAssetsInfo();
    }

    private void getUserAssetsInfo() {
        if (isVisibleToUser && mPresenter != null) {
            mPresenter.getUserAssetsInfo();
        }
    }

    @Override
    public void showUserAssets(UserAssetsBean userAssetsBeanInfo) {
        mTvCurrencyCount.setText(String_Utils.linearNmber(userAssetsBeanInfo.getUserStudyCoin()));
        mTvBoccCount.setText(String_Utils.linearNmber(userAssetsBeanInfo.getUserBocc()));
    }

    @OnClick({R.id.tv_exchange_rule, R.id.tv_exchange_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exchange_rule:
                Intent exchangeIntent = new Intent(mContext, MyWebViewActivity.class);
                exchangeIntent.putExtra(BUNDLE_WEB_TITLE, "兑换规则");
                exchangeIntent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_MALL_EXCHANGE_RULE));
                startActivity(exchangeIntent);
                break;
            case R.id.tv_exchange_record:
                Intent intent = new Intent(mContext, MallExchangeRecordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(@Nullable @org.jetbrains.annotations.Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.show(message);
    }
}