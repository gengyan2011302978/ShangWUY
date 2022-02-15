package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.event.ChangeMainItemEvent;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyFragmentPagerAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.UmengUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.shangxueyuan.mvp.ui.fragment.MainWebFragment.TYPE_INFORMATION_OPERATION_OFFICER;
import static com.phjt.shangxueyuan.mvp.ui.fragment.MainWebFragment.TYPE_NEW_MARKETING_ARCHITECT;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;

/**
 * @author: Created by shaopengfei
 * date: 2020/12/25 16:47
 * company: 普华集团
 * description: 描述 首页
 */
public class MainVIPFragment extends BaseLazyLoadFragment {

    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private String[] mTitles;
    private MyFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    private MainFragment mMainFragment;
    private MainWebFragment mInformationFragment;
    private MainWebFragment mArchitectFragment;

    public static MainVIPFragment newInstance() {
        return new MainVIPFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_vip, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitles = new String[]{
                getString(R.string.business_together_to_enjoy_title),
                getString(R.string.information_operation_officer_title),
                getString(R.string.new_marketing_architect_title)};
        mMainFragment = MainFragment.newInstance();
        mInformationFragment = MainWebFragment.newInstance(TYPE_INFORMATION_OPERATION_OFFICER);
        mArchitectFragment = MainWebFragment.newInstance(TYPE_NEW_MARKETING_ARCHITECT);
        mFragments.add(mMainFragment);
        mFragments.add(mInformationFragment);
        mFragments.add(mArchitectFragment);

        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mTitles, mFragments);
        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(2);
        tab.setViewPager(viewpager);
        tab.setCurrentTab(0);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getIndexEventBean(ChangeMainItemEvent event) {
        if (event == null) {
            return;
        }
        if (event.getIndex() >= 0 && event.getIndex() < mFragments.size()
                && tab != null) {
            tab.setCurrentTab(event.getIndex());
        }
    }

    @OnClick({R.id.customer_service_icon, R.id.message_icon})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.customer_service_icon:
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                onEventMainPage("在线客服", UmengUtil.EVENT_ZXKF_MAIN);
                break;
            case R.id.message_icon:
                intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                onEventMainPage("站内消息", UmengUtil.EVENT_ZNXX_MAIN);
                break;
            default:
                break;
        }
    }

    /**
     * 未读消息总数
     */
    public void showUnReadCount(Integer count) {
        if (tvMainInfo != null) {
            if (count != null && count > 0) {
                tvMainInfo.setVisibility(View.VISIBLE);
                if (count < 100) {
                    tvMainInfo.setText(count + "");
                } else {
                    tvMainInfo.setText("99+");
                }
            } else {
                tvMainInfo.setVisibility(View.GONE);
            }
        }
    }

    public void reLoad() {
        if (mMainFragment != null) {
            mMainFragment.reLoad();
        }
    }

    @Override
    public void lazyLoadData() {

    }
}
