package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.AppLifecyclesImpl;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.ExpirationNoticeBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.MainAnnouncementBean;
import com.phjt.shangxueyuan.bean.TabEntity;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.event.ConnectBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.event.MsgEvent;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.common.NetworkChangeReceiver;
import com.phjt.shangxueyuan.di.component.DaggerHomePagerComponent;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.HomePagerContract;
import com.phjt.shangxueyuan.mvp.presenter.HomePagerPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.CircleFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.EliteFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.MainVIPFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallHomeFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.MineFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.StudyFragment;
import com.phjt.shangxueyuan.service.DownLoadCacheService;
import com.phjt.shangxueyuan.utils.AppUtils;
import com.phjt.shangxueyuan.utils.BannerUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.GetConfig;
import com.phjt.shangxueyuan.widgets.MineGuideView;
import com.phjt.shangxueyuan.widgets.NetworkUtils;
import com.phjt.shangxueyuan.widgets.UserGuideView;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.NoScrollViewPager;
import com.phsxy.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.fragment.MainFragment.getIdByType;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_ADDRESS;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_LINK_TYPE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_PLANET_PHONE;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;


/**
 * @author: Created by Template
 * date: 03/24/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
public class HomePagerActivity extends BaseActivity<HomePagerPresenter> implements HomePagerContract.View,
        OnTabSelectListener, INotFitsImmersionBar {

    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.vp_home)
    NoScrollViewPager mVpHome;
    @BindView(R.id.tv_noconnect)
    TextView tvNoconnect;
    @BindView(R.id.vt_user_guide_view)
    ViewStub vtUserGuideView;

    @BindView(R.id.vs_mine_guide)
    ViewStub vsMineGuideView;

    private final String[] mTitles = {"首页", "商城", "圈子", "学习", "我的"};
    private final int[] mIconUnselectIds = {
            R.drawable.tab_unselect1, R.drawable.tab_mall_unselect, R.drawable.tab_unselect3,
            R.drawable.tab_unselect2, R.drawable.tab_unselect5};
    private final int[] mIconSelectIds = {
            R.drawable.tab_select1, R.drawable.tab_mall_select, R.drawable.tab_select3,
            R.drawable.tab_select2, R.drawable.tab_select5};
    @BindView(R.id.iv_i_know)
    ImageView ivIKnow;
    @BindView(R.id.relat_circle_above)
    RelativeLayout relatCircleAbove;


    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyHomeAdapter homeAdapter;
    private MainVIPFragment mMainVIPFragment;
    private MallHomeFragment mMallHomeFragment;
    private CircleFragment circlefragment;
    private MineFragment mMineFragment;
    private StudyFragment mStudyFragment;
    private String linkType;
    private String linkAddress;
    private String planetPhone;

    /**
     * 当前fragment索引
     */
    private int positionFragment = 0;
    /**
     * 网络状态监听
     */
    private NetworkChangeReceiver mNetworkChangeReceiver;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomePagerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home_pager;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getIntentData();
        int tabCount = mTitles.length;
        for (int i = 0; i < tabCount; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mMainVIPFragment = MainVIPFragment.newInstance();
        mStudyFragment = StudyFragment.newInstance();
        circlefragment = CircleFragment.newInstance();
        mMallHomeFragment = MallHomeFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        mFragments.add(mMainVIPFragment);
        mFragments.add(mMallHomeFragment);
        mFragments.add(circlefragment);
        mFragments.add(mStudyFragment);
        mFragments.add(mMineFragment);

        homeAdapter = new MyHomeAdapter(getSupportFragmentManager(), mFragments);
        mVpHome.setAdapter(homeAdapter);
        mVpHome.setScrollEnable(false);
        mVpHome.setOffscreenPageLimit(4);
        mVpHome.setCurrentItem(0);
        mTabLayout.setOnTabSelectListener(this);
        mTabLayout.setTabData(mTabEntities);
        if (mPresenter != null) {
            //清除缓存数量
            mPresenter.getAnnouncementJson();
            mPresenter.getExpirationNotice();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, intentFilter);
        MyApplication.instance().setDatabase();
        FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
        List<File> userInfos = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().build().list();
        List<File> learnBeanList = userInfos;
        for (int i = 0; i < learnBeanList.size(); i++) {
            if (learnBeanList.get(i).getStatus() == 1) {
                DownLoadCacheService.txStopDownloadVideo(learnBeanList.get(i).getSeq());
                learnBeanList.get(i).setStatus(File.DOWNLOAD_PAUSE);
                fileDao.update(learnBeanList.get(i));
            }
        }

        //友盟推送——设置Tag
        AppLifecyclesImpl.getPushAgent().getTagManager().addTags((b, result) -> {
        }, Constant.PUSH_IS_LOGIN);

        //友盟推送——设置Alias
        String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        if (!TextUtils.isEmpty(phone)) {
            AppLifecyclesImpl.getPushAgent().setAlias(phone, Constant.PUSH_PHONE, (b, s) -> {
//                LogUtils.e("============Home:" + phone + b);
            });
        }

        //H5分包
        ConstantWeb.getHttpWebAddressList(this);
    }

    private void getIntentData() {
        planetPhone = getIntent().getStringExtra(BUNDLE_PLANET_PHONE);
        linkType = getIntent().getStringExtra(BUNDLE_LINK_TYPE);
        linkAddress = getIntent().getStringExtra(BUNDLE_LINK_ADDRESS);
        if (!TextUtils.isEmpty(planetPhone) && !TextUtils.isEmpty(SPUtils.getInstance().getString(Constant.SP_TOKEN))) {
            showFloatView();
            mPresenter.getBindStatus(planetPhone, SPUtils.getInstance().getString(Constant.SP_MOBILE));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getIntentData();
    }


    @Override
    public void onTabSelect(int position) {
        positionFragment = position;
        if (mVpHome != null) {
            mVpHome.setCurrentItem(position, false);
        }
        if (position == 0) {
            onEventMainPage("底栏菜单-首页", UmengUtil.EVENT_DBSY_MAIN);
        }
        if (position == 1) {
            onEventMainPage("底栏菜单-商城", UmengUtil.EVENT_DBMALL_MAIN);
        }
        if (position == 2) {
            if (SPUtils.getInstance().getInt(Constant.BUNDLE_CIRCLE_BG) < 2
                    && relatCircleAbove != null) {
                mVpHome.setFocusableInTouchMode(false);
                mVpHome.setFocusable(false);
                relatCircleAbove.setVisibility(View.VISIBLE);
                relatCircleAbove.setFocusableInTouchMode(true);
                relatCircleAbove.setFocusable(true);
            }
            onEventMainPage("底栏菜单-圈子", UmengUtil.EVENT_DBQZ_MAIN);
        }
        if (position == 3) {
            onEventMainPage("底栏菜单-学习", UmengUtil.EVENT_DBXX_MAIN);
        }
        if (position == 4) {
            if (SPUtils.getInstance().getInt(Constant.BUNDLE_MINE_BG, 0) < 1) {
                if (vsMineGuideView != null) {
                    View view = vsMineGuideView.inflate();
                    if (view instanceof MineGuideView) {
                        MineGuideView guideView = (MineGuideView) view;
                        guideView.setDoAfterGuide(() -> {
                            mMineFragment.showGuideView();
                        });
                    }
                }
            }
            onEventMainPage("底栏菜单-我的", UmengUtil.EVENT_JYZNTZAN_MAIN);
        }
        if (position == 0 || position == 2 || position == 4 || position == 3) {
            if (mPresenter != null) {
                mPresenter.getUnReadCount();
            }
        }
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.isNetWorkAvailable(this)) {
            tvNoconnect.setVisibility(View.GONE);
        }
        if (mPresenter != null) {
            mPresenter.getUnReadCount();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(MsgEvent msgEvent) {
        if (mPresenter != null) {
            mPresenter.getUnReadCount();
        }
    }

    @Override
    public void showUnReadCount(Integer count) {
        if (mMainVIPFragment != null) {
            mMainVIPFragment.showUnReadCount(count);
        }
        if (mMineFragment != null) {
            mMineFragment.showUnReadCount(count);
        }
        if (mStudyFragment != null) {
            mStudyFragment.showUnReadCount(count);
        }
        if (circlefragment != null) {
            circlefragment.showUnReadCount(count);
        }
    }

    @Override
    public void showMaintenanceDialog(MainAnnouncementBean bean) {
        if (bean.getSign() == 1) {
            DialogUtils.mainAnnoDialog(this, bean.getTitle(), bean.getText());
        } else {
            if (mPresenter != null) {
                mPresenter.getCheckVersion();
            }
        }
    }

    @Override
    public void showUpdateDialog(UpdateAppBean count) {
        if (count.getVersionCode() > GetConfig.getVerCode(this)) {
            DialogUtils.AppUpdateDialog(this, "V" + count.getVersionName(), count.getDescription(), count.getVersionUrl());
        } else {
            showGuideView();
        }
    }

    /**
     * 加载引导
     */
    @Override
    public void showGuideView() {
        if (SPUtils.getInstance().getInt(Constant.BUNDLE_HOMPAGE_BG, 0) < 2 && TextUtils.isEmpty(planetPhone)) {
            if (vtUserGuideView != null) {
                View view = vtUserGuideView.inflate();
                if (view instanceof UserGuideView) {
                    UserGuideView guideView = (UserGuideView) view;
                    guideView.setDoAfterGuide(() -> {
                        if (mPresenter != null) {
                            mPresenter.getActivityBannerList();
                        }
                    });
                }
            }
        } else {
            if (mPresenter != null) {
                mPresenter.getActivityBannerList();
            }
        }
    }

    @Override
    public void showActivityBanner(List<ListBannerBean> bannerBeanList) {
        DialogUtils.showHomeActivityDialog(this, bannerBeanList, new DialogUtils.OnBannerClick() {
            @Override
            public void clickBanner(ListBannerBean bannerBean) {
                BannerUtils.bannerClick(HomePagerActivity.this, bannerBean, getIdByType(9));
            }
        });
    }

    /**
     * 优惠券到期通知
     */
    @Override
    public void expirationNoticeSuccess(ExpirationNoticeBean bean) {
        if (bean != null) {
            ExpirationNoticeBean.NormalNotice normalNotice = bean.getNormalNotice();
            ExpirationNoticeBean.ExpiredNotice expiredNotice = bean.getExpiredNotice();
            if (normalNotice != null) {
                DialogUtils.showNoticeDialog(this, "" + normalNotice.getTile(), "" + normalNotice.getContent(), new DialogUtils.OnClickDialogListener() {
                    @Override
                    public void clickOk() {
                        Intent intent = new Intent(HomePagerActivity.this, ExchangeVoucherActivity.class);
                        intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
                        startActivity(intent);
                    }
                });

            } else if (expiredNotice != null) {
                DialogUtils.showNoticeDialog(this, "" + expiredNotice.getTile(), "" + expiredNotice.getContent(), new DialogUtils.OnClickDialogListener() {
                    @Override
                    public void clickOk() {
                        Intent intent = new Intent(HomePagerActivity.this, ExchangeVoucherActivity.class);
                        intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void getBindStatus(boolean isBind) {
        if (isBind) {
            mTabLayout.postDelayed(() -> AppUtils.goDetailPage(HomePagerActivity.this, linkType, linkAddress), 2000);
        } else {
            mTabLayout.postDelayed(() -> gotoBind(), 2000);
        }
    }

    private void gotoBind() {
        Intent intent = new Intent(HomePagerActivity.this, BindPlanetAccountActivity.class);
        intent.putExtra(BUNDLE_PLANET_PHONE, planetPhone);
        intent.putExtra(BUNDLE_LINK_TYPE, linkType);
        intent.putExtra(BUNDLE_LINK_ADDRESS, linkAddress);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(ConnectBean connectBean) {
        if (tvNoconnect.getVisibility() != 8) {
            if (connectBean.isConnect()) {
                mMainVIPFragment.reLoad();
                mStudyFragment.refreshData();
                //TODO mEliteFragment.reLoad();
                mMineFragment.onResume();
                tvNoconnect.setVisibility(View.GONE);
            } else {
                tvNoconnect.setVisibility(View.VISIBLE);
            }
        }
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
        if ((mNowTime - mPressedTime) > 1000) {
            TipsUtil.showTips("再按一次退出程序");
            mPressedTime = mNowTime;
        } else {
            List<File> userInfos = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.FileType.eq(("video"))).build().list();
            List<File> learnBeanList = userInfos;
            for (int i = 0; i < learnBeanList.size(); i++) {
                if (learnBeanList.get(i).getStatus() == 1) {
                    DownLoadCacheService.txStopDownloadVideo(learnBeanList.get(i).getSeq());
                }
            }
            AppManager.getAppManager().killAll();
            MobclickAgent.onKillProcess(this);
            System.exit(0);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PROPELLING_MOVEMENT) {
                //推送横幅
                String title = String.valueOf(eventBean.getTitle());
                String content = String.valueOf(eventBean.getContent());
                DialogUtils.showNoticeDialog(this, "" + title, "" + content, new DialogUtils.OnClickDialogListener() {
                    @Override
                    public void clickOk() {
                        Intent intent = new Intent(HomePagerActivity.this, ExchangeVoucherActivity.class);
                        intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
                        startActivity(intent);
                    }
                });
            } else if (type == EventBean.PUSH_VIP) {
                VipUtil.toVipPage(this);
            } else if (type == EventBean.PUSH_EXAM) {
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "考试系统");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_EXAM));
                startActivity(intent);
            } else if (type == EventBean.PUSH_MESSAGE) {
                if (!TextUtils.isEmpty(eventBean.getMsg())) {
                    Intent intent = new Intent(this, MyWebViewActivity.class);
                    intent.putExtra(Constant.BUNDLE_WEB_URL, eventBean.getMsg());
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, MessageActivity.class));
                }
            } else if (type == EventBean.PUSH_HOME) {
                if (mTabLayout != null && mVpHome != null) {
                    mVpHome.setCurrentItem(0);
                    mTabLayout.setCurrentTab(0);
                }
            } else if (type == EventBean.PUSH_CIRCLE) {
                if (mTabLayout != null && mVpHome != null) {
                    mVpHome.setCurrentItem(2);
                    mTabLayout.setCurrentTab(2);
                }
            } else if (type == EventBean.TRAINING_CAMP_SEND && !TextUtils.isEmpty(eventBean.getMsg())) {
                Intent intent = new Intent(this, TrainingCampDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, eventBean.getMsg());
                startActivity(intent);
            }
        }
    }


    @OnClick({R.id.iv_i_know, R.id.relat_circle_above})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_i_know:
                if (relatCircleAbove != null) {
                    relatCircleAbove.setVisibility(View.GONE);
                }
                SPUtils.getInstance().put(Constant.BUNDLE_CIRCLE_BG, 2);
                break;
            case R.id.relat_circle_above:
                break;

            default:
                break;
        }
    }

    private void showFloatView() {
        EasyFloat.with(this).setLayout(R.layout.layout_float)
                .setLocation(0, 500)
                .setDragEnable(false)
                .setShowPattern(ShowPattern.FOREGROUND)
                .setTag("float")
                .setFilter(LoginActivity.class)
                .registerCallbacks(new OnFloatCallbacks() {
                    @Override
                    public void createdResult(boolean b, @org.jetbrains.annotations.Nullable String s, @org.jetbrains.annotations.Nullable View view) {

                    }

                    @Override
                    public void show(@NotNull View view) {

                    }

                    @Override
                    public void hide(@NotNull View view) {

                    }

                    @Override
                    public void dismiss() {

                    }

                    @Override
                    public void touchEvent(@NotNull View view, @NotNull MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            AppUtils.wakeUpPlanet(HomePagerActivity.this, false);
                        }
                    }

                    @Override
                    public void drag(@NotNull View view, @NotNull MotionEvent motionEvent) {

                    }

                    @Override
                    public void dragEnd(@NotNull View view) {

                    }
                })
                .show();
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

}
