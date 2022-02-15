package com.phjt.shangxueyuan.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.ActivityLifecycleCallbacksImpl;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;
import com.phjt.shangxueyuan.bean.CourseDetailAndCatalogBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.event.DeleteDownLoadBean;
import com.phjt.shangxueyuan.mvp.ui.fragment.DataFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.NoteFragment;
import com.phjt.shangxueyuan.utils.AppUtils;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.LoadingDialog;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.widgets.dialog.ShareLivePlaybackDialog;
import com.phjt.shangxueyuan.widgets.dialog.ShareToPlatformDialog;
import com.phjt.shangxueyuan.widgets.popupwindow.DownloadHorizontalPop;
import com.phjt.shangxueyuan.widgets.popupwindow.DownloadLivePop;
import com.phjt.shangxueyuan.widgets.popupwindow.DownloadVerticalPop;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;
import com.phjt.shangxueyuan.bean.event.ConnectBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.VipStateBean;
import com.phjt.shangxueyuan.bean.event.WebToStudyBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.common.NetworkChangeReceiver;
import com.phjt.shangxueyuan.di.component.DaggerCourseDetailComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.CourseDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseDetailPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseCatalogFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseIntroduceFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.sharestatistic.ShareInit;
import com.phsxy.utils.NetworkUtils;
import com.phsxy.utils.ToastUtils;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerVideoId;
import com.tencent.liteav.demo.play.SuperPlayerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 课程详情页
 */
public class CourseDetailActivity extends BaseActivity<CourseDetailPresenter> implements CourseDetailContract.View,
        IWithoutImmersionBar, ViewPager.OnPageChangeListener, SuperPlayerView.OnSuperPlayerViewCallback {

    @BindView(R.id.layout)
    ConstraintLayout mLayout;
    @BindView(R.id.spv_video)
    SuperPlayerView mSuperPlayerView;
    @BindView(R.id.tab_course_detail)
    SlidingTabLayout mTabDetail;
    @BindView(R.id.vp_course_detail)
    ViewPager mVpDetail;
    @BindView(R.id.group)
    Group mGroup;
    @BindView(R.id.iv_open_member)
    ImageView mIvMember;
    @BindView(R.id.iv_video_holder)
    ImageView mIvVideoHolder;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.iv_course_back)
    ImageView mIvBack;
    @BindView(R.id.course_off_layout)
    View mCourseOffLayout;
    @BindView(R.id.course_layout)
    ConstraintLayout mCourseLayout;
    @BindView(R.id.no_network_layout)
    View mNoNetworkLayout;
    @BindView(R.id.iv_no_network_back)
    ImageView mIvNoNetworkBack;
    @BindView(R.id.tv_open_member)
    TextView mTvOpenMember;
    @BindView(R.id.linear_buy)
    LinearLayout mLinearBuy;
    @BindView(R.id.linear_buy_course)
    LinearLayout mLinearBuyCourse;
    @BindView(R.id.tv_sellprice)
    TextView mTvSellprice;
    //专栏价格
    public static String realPrice;
    //专栏是否购买
    private String buyStatus;

    /**
     * 当前横竖屏 状态
     */
    private int mConfig = Configuration.ORIENTATION_PORTRAIT;

    private int videoHeight;
    /**
     * 0.普通用户；1.普通vip；2.永久vip；3.vip已过
     */
    private int vipStates;
    /**
     * 课程id
     */
    public static String courseId;
    /**
     * 专栏id
     */
    String columnId = "";
    /**
     * 专栏名称
     */
    public static String columnName;

    private CourseIntroduceFragment mCourseIntroduceFragment;
    private CourseCatalogFragment mCourseCatalogFragment;
    /**
     * 笔记 及笔记个数
     */
    private NoteFragment mNoteFragment;
    /**
     * 当前ViewPager中的Fragment位置
     */
    private int currentPosition;
    /**
     * 网络状态监听
     */
    private NetworkChangeReceiver networkChangeReceiver;
    /**
     * 当前播放的小节实体
     */
    private CourseCatalogSecondBean playSecondBean;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private Integer freeType;
    /**
     * 收藏状态
     */
    private boolean isCollection;
    /**
     * 分享实体
     */
    private ShareBean mShareBean;
    /**
     * 倍速实体
     */
    private List<TCMultipleBean> mMultipleBeanList;
    /**
     * 当前倍速 全局记录
     */
    private float mSpeedLevel = 1f;
    /**
     * 课程详情页 tabs
     */
    private String[] mTitles;

    /**
     * 下载 传递的本地路径
     */
    private String localpath;
    //一级分类名字 、 简介
    public static String level_name = "";
    public static String level_desc = "";
    public static String level_url = "";
    /**
     * 1专栏 否则课程
     */
    public String courseType = "1";

    /**
     * 是否是直播回放  1横屏播放 2竖屏播放
     * isLiveFull：是否全屏播放中
     */
    public static boolean isLivePlayback;
    private boolean isLiveFull;
    /**
     * 播放权限，作用于商品兑换：1-无播放权限 2-有播放权限
     */
    public static int playPermission;
    /**
     * 当前视频播放时间
     */
    private long mCurrentTime;

    /**
     * V1.6.0 记录第一小节的id，用户第一节免费播放使用
     */
    private String mPointId;

    @Override

    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCourseDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_course_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //沉浸式状态栏设置
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_333333)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> {
                    //软键盘弹出关闭的回调监听
                    if (isPopup) {
                        mGroup.setVisibility(View.GONE);
                    } else {
                        if (mConfig != Configuration.ORIENTATION_LANDSCAPE) {
                            if ("1".equals(courseType)) {
                                mGroup.setVisibility(View.GONE);
                            } else {
                                mGroup.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                })
                .setOnBarListener(barProperties -> {
                    //横竖屏切换
                    setFullScreen(this, !barProperties.isPortrait());
                })
                .init();
        videoHeight = AutoSizeUtils.dp2px(this, 210f);
        mVpDetail.addOnPageChangeListener(this);
        mSuperPlayerView.setPlayerViewCallback(this);

        Intent intent = getIntent();
        if (intent != null) {
            courseId = intent.getStringExtra(Constant.BUNDLE_COURSE_ID);
            localpath = intent.getStringExtra(Constant.BUNDLE_LOCAL_PATH);
            if (localpath != null) {
                playLocalVideo();
            }///storage/emulated/0/Android/data/com.phjt.shangxueyuan/files/txdownload/8cea73709f0ea1e039acc0752bc7a848.m3u8.sqlite
            String titleName = intent.getStringExtra(Constant.BUNDLE_COURSE_NAME);
        }
        mCourseCatalogFragment = CourseCatalogFragment.newInstance(courseId);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        getCourseDetail();

        mSuperPlayerView.setTimeReport(new SuperPlayerView.TimeReport() {
            @Override
            public void submitLookTime(long currentTime, long countTime) {
                //每隔60s进行一次时间上报
                if (mPresenter != null && playSecondBean != null) {
                    if ("1".equals(courseType)) {
                        mPresenter.reportWatchTime(playSecondBean.getCourseId(), playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, currentTime == countTime ? 1 : 0, columnId);
                    } else {
                        mPresenter.reportWatchTime(courseId, playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, currentTime == countTime ? 1 : 0, null);
                    }
                }
            }

            @Override
            public void submitEndTime(long currentTime, long countTime) {
                //视频播放完成上报
                if (mPresenter != null && playSecondBean != null) {
                    if ("1".equals(courseType)) {
                        mPresenter.reportWatchTime(playSecondBean.getCourseId(), playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, 1, columnId);
                    } else {
                        mPresenter.reportWatchTime(courseId, playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, 1, null);
                    }
                }
            }

            @Override
            public void getCurrentTime(long currentTime, long countTime) {
                //获取 当前播放的时间 和 总时间
                mCurrentTime = currentTime;
            }
        });

        initMultipleList();
    }

    /**
     * 获取课程详情
     */
    private void getCourseDetail() {
        if (!NetworkUtils.isConnected()) {
            mNoNetworkLayout.setVisibility(View.VISIBLE);
            mIvNoNetworkBack.setVisibility(View.VISIBLE);
            setBarBgColor(true);
        }
        if (mPresenter != null) {
            mPresenter.getCourseDetailAndCatalog(courseId);
            mPresenter.getVipState(true);
        }

    }

    private void initMultipleList() {
        mMultipleBeanList = new ArrayList<>();
        mMultipleBeanList.add(new TCMultipleBean(1f));
        mMultipleBeanList.add(new TCMultipleBean(1.25f));
        mMultipleBeanList.add(new TCMultipleBean(1.5f));
        mMultipleBeanList.add(new TCMultipleBean(2f));
    }

    private void setMultipleBeanList() {
        for (int i = 0; i < mMultipleBeanList.size(); i++) {
            TCMultipleBean multipleBean = mMultipleBeanList.get(i);
            multipleBean.setSelect(multipleBean.getSpeedLevel() == mSpeedLevel);
        }
    }

    @Override
    public void showCourseDetailAndCatalog(CourseDetailAndCatalogBean courseDetailAndCatalogBean, String type) {
        columnId = courseDetailAndCatalogBean.getCourseDetailBean().getId();
        columnName = courseDetailAndCatalogBean.getCourseDetailBean().getName();
        realPrice = courseDetailAndCatalogBean.getCourseDetailBean().getSellPrice();
        buyStatus = courseDetailAndCatalogBean.getCourseDetailBean().getBuyState();
        if (courseDetailAndCatalogBean.getCourseDetailBean().getPlayPermission() == 2 || TextUtils.isEmpty(realPrice)) {
            mLinearBuy.setVisibility(View.GONE);
            mLinearBuyCourse.setVisibility(View.GONE);
            mGroup.setVisibility(View.GONE);
        }
        if ("0".equals(buyStatus)) {
            mTvSellprice.setText("立即购买：¥" + realPrice);
        } else {
            mLinearBuy.setVisibility(View.GONE);
            mLinearBuyCourse.setVisibility(View.GONE);
            mGroup.setVisibility(View.GONE);
        }
        if ("1".equals(type)) {
            mGroup.setVisibility(View.GONE);
        } else {
            mLinearBuyCourse.setVisibility(View.GONE);
            mGroup.setVisibility(View.VISIBLE);
        }
        courseType = type;
        mNoNetworkLayout.setVisibility(View.GONE);
        //获取笔记传递的 视频小节id   如果传递的有时间，则定位到当前小节时间
        String pointId = getIntent().getStringExtra(Constant.BUNDLE_POINT_ID);
        long pauseWatchTime = getIntent().getLongExtra(Constant.BUNDLE_COURSE_WATCH_TIME, 0L);

        List<CourseCatalogFirstBean> catalogFirstBeans = courseDetailAndCatalogBean.getCatalogFirstBeans();
        if (catalogFirstBeans != null && !catalogFirstBeans.isEmpty()) {
            getVideoPoint(catalogFirstBeans, pointId, pauseWatchTime);
        }
        //重新赋值小节id
        if (null != playSecondBean) {
            pointId = playSecondBean.getPointId();
        }

        //课程详情赋值
        CourseDetailBean courseDetailBean = courseDetailAndCatalogBean.getCourseDetailBean();
        if (courseDetailBean != null) {
            freeType = courseDetailBean.getFreeType();
            isCollection = courseDetailBean.getCollectionStatus() == 1;
            mSuperPlayerView.mControllerWindow.showCollcetionState(isCollection);
            mSuperPlayerView.mControllerFullScreen.showCollcetionState(isCollection);
            loadCourseBg(courseDetailBean.getCoverImg(), mIvVideoHolder);
            playPermission = courseDetailBean.getPlayPermission();
            isLivePlayback = courseDetailBean.getVideoType() == 2;

            //上下架状态(0下架 1上架)
            Integer upState = courseDetailBean.getUpState();
            isShowOffLayout(upState == 0);

            int courseNum = courseDetailBean.getCourseNum();
            int noteNum = courseDetailBean.getNoteNum();
            int punchCardNum = courseDetailBean.getPunchCardNum();
            int exerciseNum = courseDetailBean.getExerciseNum();

            level_name = courseDetailBean.getName();
            level_desc = courseDetailBean.getCouDesc();
            level_url = courseDetailBean.getCoverImg();

            ArrayList<Fragment> fragments = new ArrayList<>();
            mCourseIntroduceFragment = CourseIntroduceFragment.newInstance(courseDetailBean);
            mNoteFragment = NoteFragment.newInstance(courseId, pointId, freeType, vipStates, courseDetailBean.getNoteNum(), courseType);
            fragments.add(mCourseIntroduceFragment);
            fragments.add(mCourseCatalogFragment);
            fragments.add(mNoteFragment);
            //助学或打卡数量大于0，显示助学tab
            if (courseNum > 0 || punchCardNum > 0 || exerciseNum > 0) {
                mTitles = new String[]{"介绍", "目录", "助学", "笔记"};
                fragments.add(2, DataFragment.newInstance(courseId, freeType, playPermission, courseType, punchCardNum, exerciseNum));
            } else {
                mTitles = new String[]{"介绍", "目录", "笔记"};
            }
            MyHomeAdapter adapter = new MyHomeAdapter(getSupportFragmentManager(), fragments);
            mVpDetail.setAdapter(adapter);
            mVpDetail.setOffscreenPageLimit(fragments.size());
            mTabDetail.setViewPager(mVpDetail, mTitles);

            //消息评论定位到笔记，否则选中目录
            if (getIntent().getBooleanExtra(Constant.BUNDLE_COURSE_TO_COMMENT, false)) {
                mVpDetail.setCurrentItem(mTitles.length - 1);
            } else if (getIntent().getBooleanExtra(Constant.BUNDLE_COURSE_TO_NOTES, false)) {
                mVpDetail.setCurrentItem(0);
            } else {
                mVpDetail.setCurrentItem(1);
            }

            if (getIntent().getBooleanExtra(Constant.BUNDLE_ANSWER_TO_COMMENT, false)) {
                mVpDetail.setCurrentItem(2);
            }

            //显示 助学、笔记 的数量
            if (courseNum > 0 || punchCardNum > 0 || exerciseNum > 0) {
                setTitleCount(2, courseNum);
                setTitleCount(3, noteNum);
            } else {
                setTitleCount(2, noteNum);
            }

            //课程目录赋值
            mCourseCatalogFragment.showCourseCatalogList(list);
            if (mCourseCatalogFragment.catalogAdapter != null && playSecondBean != null) {
                mCourseCatalogFragment.catalogAdapter.pointId = playSecondBean.getPointId();
            }
            mNoteFragment.getPointId(pointId);
            EventBusManager.getInstance().post(new EventBean(playPermission, null));
        }
    }

    /**
     * 显示、隐藏下架页面
     *
     * @param isShow true显示下架页面同时隐藏布局页面
     */
    public void isShowOffLayout(boolean isShow) {
        mCourseLayout.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mCourseOffLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        setBarBgColor(isShow);
    }

    @Override
    public void showLoadingDialog() {
        LoadingDialog.getInstance().show(this);
    }

    @Override
    public void dismissLoadingDialog() {
        LoadingDialog.getInstance().dismiss();
    }

    /**
     * 封面背景图加载
     */
    public void loadCourseBg(String url, ImageView imageView) {
        Glide.with(CourseDetailActivity.this).asBitmap().load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        AppImageLoader.rsBlur(CourseDetailActivity.this, resource, 10);
                        return false;
                    }
                }).into(imageView);
    }

    private ArrayList<MultiItemEntity> list;

    /**
     * 获取当前播放的小节实体
     */
    public void getVideoPoint(List<CourseCatalogFirstBean> catalogFirstBeans, String pointId, long pauseWatchTime) {
        list = new ArrayList<>();
        for (int i = 0; i < catalogFirstBeans.size(); i++) {
            CourseCatalogFirstBean firstBean = catalogFirstBeans.get(i);
            if (firstBean != null && firstBean.getCouPointVOs() != null) {
                List<CourseCatalogSecondBean> couPointVOs = firstBean.getCouPointVOs();
                for (int j = 0; j < couPointVOs.size(); j++) {
                    CourseCatalogSecondBean secondBean = couPointVOs.get(j);
                    if (secondBean != null) {
                        //获取第一小节视频的id
                        if (i == 0 && j == 0) {
                            mPointId = secondBean.getPointId();
                        }
                        secondBean.setWareId(firstBean.getCoursewareId());
                        if (TextUtils.isEmpty(pointId)) {
                            //查询接口返回的上次播放小节实体
                            Integer isLastWatch = secondBean.getIsLastWatch();
                            if (isLastWatch == 1) {
                                playSecondBean = secondBean;
                                secondBean.setSelect(true);
                            } else {
                                secondBean.setSelect(false);
                            }
                        } else {
                            //获取笔记对应的播放小节实体
                            if (TextUtils.equals(pointId, secondBean.getPointId())) {
                                playSecondBean = secondBean;
                                playSecondBean.setPointWatchDuration(pauseWatchTime);
                                secondBean.setSelect(true);
                            } else {
                                secondBean.setSelect(false);
                            }
                        }
                    }
                    firstBean.addSubItem(secondBean);
                }
                list.add(firstBean);
            }
        }
        //未观看过，默认选中第一个
        if (playSecondBean == null && list.size() > 0) {
            MultiItemEntity multiItemEntity = list.get(0);
            if (multiItemEntity instanceof CourseCatalogFirstBean) {
                CourseCatalogFirstBean firstBean = (CourseCatalogFirstBean) multiItemEntity;
                if (firstBean.getCouPointVOs() != null && !firstBean.getCouPointVOs().isEmpty()) {
                    CourseCatalogSecondBean secondBean = firstBean.getCouPointVOs().get(0);
                    playSecondBean = secondBean;
                    secondBean.setSelect(true);
                }
            }
        }
    }

    /**
     * 助学、笔记 Title赋值
     *
     * @param index        2:助学 3:笔记
     * @param commentCount 总数
     */
    public void setTitleCount(int index, int commentCount) {
        String name;
        if (mTitles.length == 4) {
            name = index == 2 ? "助学" : "笔记";
        } else {
            name = "笔记";
            index = 2;
        }
        TextView titleView = mTabDetail.getTitleView(index);
        if (commentCount == 0) {
            titleView.setText(name);
        } else if (commentCount > 99) {
            titleView.setText(String.format("%s(99+)", name));
        } else {
            titleView.setText(String.format(Locale.getDefault(), "%s(%d)", name, commentCount));
        }
    }

    @SingleClick
    @OnClick({R.id.iv_course_back, R.id.iv_video_holder, R.id.tv_get_information, R.id.tv_get_information1, R.id.linear_buy, R.id.tv_open_member, R.id.iv_play, R.id.iv_off_back,
            R.id.tv_find_more, R.id.course_off_layout, R.id.iv_no_network_back, R.id.tv_refresh, R.id.no_network_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_course_back:
                if (getIntent().getBooleanExtra("is_from_planet", false)) {
                    AppUtils.wakeUpPlanet(CourseDetailActivity.this, false);
                }
                finish();
                break;
            case R.id.iv_video_holder:
                //拦截点击事件
                break;
            case R.id.linear_buy:
                Intent intent1 = new Intent(this, OpenVipActivity.class);
                intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, realPrice);
                intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                startActivity(intent1);
                break;
            case R.id.tv_get_information1:
            case R.id.tv_get_information:
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_COLLECTION_INFO);
                break;
            case R.id.tv_open_member:
                //会员
                VipUtil.toVipPage(this);
                UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_INTEREST_MEMBER);
                break;
            case R.id.iv_play:
                //视频播放
                if (mCourseCatalogFragment != null) {
                    playCheck(playSecondBean);
                } else {
                    TipsUtil.showTips("课程目录数据为空");
                }
                break;

            //------------------- 课程失效页面 和 无网络页面---------------------
            case R.id.iv_off_back:
            case R.id.tv_find_more:
                finish();
                break;
            case R.id.course_off_layout:
                //拦截点击事件、勿删
                break;
            case R.id.iv_no_network_back:
                finish();
                break;
            case R.id.tv_refresh:
                getCourseDetail();
                break;
            case R.id.no_network_layout:
                //拦截点击事件、勿删
                break;
            default:
                break;
        }
    }

    /**
     * wifi播放是否提示（当前页提示一次）
     */
    private boolean isWifiTip = true;

    /**
     * 加载视频 网络判断
     */
    public void playCheck(CourseCatalogSecondBean secondBean) {
        playSecondBean = secondBean;
        if (!NetworkUtils.isConnected()) {
            TipsUtil.showTips("网络不可用");
            return;
        }
        if (playSecondBean == null) {
            TipsUtil.showTips("课程目录数据为空");
            return;
        }

        if (playPermission == 2 || TextUtils.equals(playSecondBean.getPointId(), mPointId)) {
            playNetVideo(playSecondBean);
        } else {

            if ("1".equals(courseType)) {
                DialogUtils.SubscribeDialog(this, new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        Intent intent1 = new Intent(CourseDetailActivity.this, OpenVipActivity.class);
                        intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                        intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                        intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                        intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, realPrice);
                        intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                        startActivity(intent1);
                    }
                });

            } else {
                if (mPresenter != null) {
                    mPresenter.getVipState(false);
                }
            }
        }
    }

    @Override
    public void showVipVideoPlay() {
        playNetVideo(playSecondBean);
    }

    @Override
    public void showVipBugDialog() {
        mIvVideoHolder.setVisibility(View.VISIBLE);
        mIvPlay.setVisibility(View.VISIBLE);
        mIvBack.setVisibility(View.VISIBLE);
        if (mSuperPlayerView != null) {
            mSuperPlayerView.onPause();
        }
        DialogUtils.showVipDialog(this);
    }

    /**
     * 播放-本地下载视频
     */
    public void playLocalVideo() {
        mIvVideoHolder.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);
        mIvBack.setVisibility(View.GONE);
        //显示loading view
        if (mSuperPlayerView.mControllerWindow != null) {
            mSuperPlayerView.mControllerWindow.showLoadingView();
        }
        //更新标题
        mSuperPlayerView.updateTitle(getIntent().getStringExtra(Constant.BUNDLE_POINT_NAME));

        //观看进度赋值
        long pointWatchDuration = 0L;
        mSuperPlayerView.setStartPlayTime(pointWatchDuration);

        SuperPlayerModel model = new SuperPlayerModel();
        model.url = localpath;
        mSuperPlayerView.playWithModel(model);

        //播放速率赋值
        if (mSuperPlayerView.mVodPlayer != null) {
            mSuperPlayerView.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    /**
     * 当前视频播放的videoId;
     */
    private String videoId;

    /**
     * 播放-网络视频
     */
    public void playNetVideo(CourseCatalogSecondBean secondBean) {
        //wifi状态，直接播放，否则提示
        if (NetworkUtils.getNetworkType() != NetworkUtils.NetworkType.NETWORK_WIFI) {
            if (isWifiTip) {
                ToastUtils.show("当前为非WIFI环境，请注意流量消耗");
                isWifiTip = false;
            }
        }
        mIvVideoHolder.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);
        mIvBack.setVisibility(View.GONE);
        //显示loading view、更新标题
        if (mSuperPlayerView.mControllerWindow != null) {
            mSuperPlayerView.mControllerWindow.showLoadingView();
            mSuperPlayerView.updateTitle(secondBean.getPointIdName());
        }

        //观看进度赋值
        Long pointWatchDuration = secondBean.getPointWatchDuration();
        mSuperPlayerView.setStartPlayTime(pointWatchDuration);


        if (isLivePlayback) {
            //直播回放，改变页面UI
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSuperPlayerView.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mSuperPlayerView.setLayoutParams(params);
            hideCommentLayout();
            mGroup.setVisibility(View.GONE);
            mSuperPlayerView.mControllerWindow.hideFullScreen();
            mSuperPlayerView.mControllerWindow.isShowIvLock = true;
            isLiveFull = true;

            if (TextUtils.equals(secondBean.getVideoId(), videoId)) {
                //播放的是同一视频，接着播放
                mSuperPlayerView.onResume();
            } else {
                SuperPlayerModel model = new SuperPlayerModel();
                model.appId = BuildConfig.TENCENT_PLAY_APPID;
                model.videoId = new SuperPlayerVideoId();
                model.videoId.fileId = secondBean.getVideoId();
                mSuperPlayerView.playWithModel(model);
            }
        } else {
            SuperPlayerModel model = new SuperPlayerModel();
            model.appId = BuildConfig.TENCENT_PLAY_APPID;
            model.videoId = new SuperPlayerVideoId();
            model.videoId.fileId = secondBean.getVideoId();
            mSuperPlayerView.playWithModel(model);
        }

        videoId = secondBean.getVideoId();

        //播放速率赋值
        if (mSuperPlayerView.mVodPlayer != null) {
            mSuperPlayerView.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    /**
     * 播放视频调用
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CourseCatalogSecondBean secondBean) {
        if (!TextUtils.isEmpty(secondBean.getPointId())) {
            localpath = "";
            playCheck(secondBean);
        } else {
            //最后一节和第一小节相同时，播放完成后判断vip状态弹框
            if (playSecondBean != null && TextUtils.equals(playSecondBean.getPointId(), mPointId)) {
                permissionCheck();
            }
        }
    }

    /**
     * 支付成功，修改vip状态 和 重新获取课程目录
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.PAY_SUCCESS && mIvMember != null) {
                getCourseDetail();
                mIvMember.setImageResource(R.drawable.member_equity);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getConnectBean(ConnectBean connectBean) {
        if (!connectBean.isConnect()) {
            TipsUtil.showTips("网络不可用");
        }
    }

    /**
     * 删除下载课程，刷新下载弹框状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDeleteEvent(DeleteDownLoadBean downLoadBean) {
        if (verticalPop != null && verticalPop.isShowing() && mCourseCatalogFragment.catalogAdapter != null) {
            List<MultiItemEntity> data = mCourseCatalogFragment.catalogAdapter.getData();
            verticalPop.refreshData(data);
        }
    }

    /**
     * WebView页面点击去学习，关闭当前页
     * 防止播放页面重复存在
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(WebToStudyBean webToStudyBean) {
        finish();
    }

    /**
     * ---------------------------------- SuperPlayerView的回调接口 开始 ----------------------------------
     */
    @Override
    public void onStartFullScreenPlay() {
        hideStatusBar();
        UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_FULL_SCREEN);
    }

    @Override
    public void onStopFullScreenPlay() {
        showStatusBar();
    }

    @Override
    public void onClickSmallReturnBtn() {
        onLiveAndSmallWindowBack();
    }

    @Override
    public void onShowMore(int type) {
        if (mPresenter != null) {
            if ("1".equals(courseType)) {
                mPresenter.getCourseShareData(columnId, type, courseType, playSecondBean.getPointId());
            } else {
                mPresenter.getCourseShareData(courseId, type, null, playSecondBean.getPointId());
            }
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_SHARE);
        }
    }

    @Override
    public void onWeChatCallBack(int statusId, String name) {
        shareData(statusId);
    }

    @Override
    public void onMultipleChange(float speedLevel) {
        this.mSpeedLevel = speedLevel;
    }

    @Override
    public void onMultiple(int type) {
        setMultipleBeanList();
        if (type == 1) {
            mSuperPlayerView.mControllerWindow.showMultipleView(mMultipleBeanList, isLivePlayback);
        } else if (type == 2) {
            mSuperPlayerView.mControllerFullScreen.showMultipleView(mMultipleBeanList);
        }
        UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_DOUBLE_SPEED);
    }

    @Override
    public void onCollection() {
        if (mPresenter != null) {
            mPresenter.doFavorite(courseId, courseType);
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION);
        }
    }

    private DownloadVerticalPop verticalPop;

    @Override
    public void onDownload(int type) {
        if (permissionCheck()) {
            return;
        }
        if (!NetworkUtils.isConnected()) {
            TipsUtil.showTips("网络不可用");
            return;
        }
        if (mCourseCatalogFragment.catalogAdapter != null) {
            if (type == 1) {
                if (isLivePlayback) {
                    DownloadLivePop livePop = new DownloadLivePop(this);
                    livePop.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    List<MultiItemEntity> data = mCourseCatalogFragment.catalogAdapter.getData();
                    livePop.showCatalogDate(data);
                } else {
                    verticalPop = new DownloadVerticalPop(this);
                    verticalPop.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    List<MultiItemEntity> data = mCourseCatalogFragment.catalogAdapter.getData();
                    verticalPop.showCatalogDate(data);
                }
            } else if (type == 2) {
                mSuperPlayerView.mControllerFullScreen.hide();
                DownloadHorizontalPop horizontalPop = new DownloadHorizontalPop(this);
                horizontalPop.setFocusable(false);
                horizontalPop.showAtLocation(mLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                horizontalPop.getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);
                horizontalPop.setFocusable(true);
                horizontalPop.update();

                List<MultiItemEntity> data = mCourseCatalogFragment.catalogAdapter.getData();
                horizontalPop.showCatalogDate(data);
            }
        }
        UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_DOWNLOAD);
    }

    /**
     * 下载 和 只有一小节课程是播放完成 的权限判断
     */
    private boolean permissionCheck() {
        if ("1".equals(courseType) && playPermission == 1) {
            DialogUtils.SubscribeDialog(this, new DialogUtils.OnCancelSureClick() {
                @Override
                public void clickSure() {
                    Intent intent1 = new Intent(CourseDetailActivity.this, OpenVipActivity.class);
                    intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                    intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                    intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                    intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, realPrice);
                    intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                    startActivity(intent1);
                }
            });
            return true;
        } else if ((vipStates == 0 || vipStates == 3) &&
                (("1".equals(courseType) && "0".equals(buyStatus)) || ("0".equals(courseType) && freeType != 1))) {
            DialogUtils.showVipDialog(this);
            return true;
        }
        return false;
    }


    @Override
    public void onPlayEnd() {
        //播放完成，自动切换下一节
        if (mCourseCatalogFragment != null && mCourseCatalogFragment.catalogAdapter != null) {
            mCourseCatalogFragment.catalogAdapter.changeNextVideo();
        }
    }

    /**
     * 是否手动暂停 true为手动
     */
    private boolean isPause;

    @Override
    public void onPlayEvent(boolean isPause) {
        this.isPause = isPause;

        if (isLivePlayback && !isLiveFull) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSuperPlayerView.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mSuperPlayerView.setLayoutParams(params);
            hideCommentLayout();
            mGroup.setVisibility(View.GONE);
            mSuperPlayerView.mControllerWindow.hideFullScreen();
            mSuperPlayerView.mControllerWindow.isShowIvLock = true;
            isLiveFull = true;
        }
    }

    private static final int FULL_SCREEN_FLAG =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;

    /**
     * ---------------------------------- SuperPlayerView的回调接口 结束 ----------------------------------
     */


    /**
     * 课程分享————获取分享内容
     *
     * @param shareBean 分享内容
     * @param type      分享弹框标识 1竖屏 2横屏
     */
    @Override
    public void showShareDialog(ShareBean shareBean, int type) {
        mShareBean = shareBean;
        if (type == 1) {
            if (isLivePlayback) {
                ShareLivePlaybackDialog liveDialog = new ShareLivePlaybackDialog(this, (statusId, name) -> shareData(statusId));
                liveDialog.setCancelable(true);
                liveDialog.setCanceledOnTouchOutside(true);
                liveDialog.show();
            } else {
                ShareToPlatformDialog shareDialog = new ShareToPlatformDialog(this, (statusId, name) -> shareData(statusId));
                shareDialog.setCancelable(true);
                shareDialog.setCanceledOnTouchOutside(true);
                shareDialog.show();
            }
        } else {
            mSuperPlayerView.mControllerFullScreen.showShareView();
        }
    }

    /**
     * 课程 分享操作
     *
     * @param statusId 分享平台标识
     */
    private void shareData(int statusId) {
        ShareUtils.shareUrlData(this, mShareBean, statusId);
    }

    /**
     * 动态、课程评论、笔记、专题留言分享————获取分享内容
     *
     * @param type    1.课程评论 2.笔记 3.专题页 4.动态
     * @param otherId 课程评论id/笔记评论id/专题页留言id/动态评论id
     * @param content 分享内容
     */
    public void getShareItemData(int type, String otherId, String content) {
        if (mPresenter != null) {
            mPresenter.getShareItemData(type, otherId, content, courseType);

            UmengUtil.umengCount(this, type == 1 ? ConstantUmeng.COURSE_COMMENT_SHARE : ConstantUmeng.COURSE_NOTE_SHARE);
        }
    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        //封装分享内容
        mShareBean = new ShareBean();
        mShareBean.setTitle(shareItemBean.getTitle());
        mShareBean.setContent(shareItemBean.getContent());
        mShareBean.setImgUrl(shareItemBean.getPhoto());
        mShareBean.setUrl(shareItemBean.getUrl());

        ShareToPlatformDialog shareDialog = new ShareToPlatformDialog(this, (statusId, name) -> shareData(statusId));
        shareDialog.setCancelable(true);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
            Fragment fragment = fragmentManager.getFragments().get(i);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void doFavoriteSuccess() {
        if (isCollection) {
            isCollection = false;
            TipsUtil.showTips("取消收藏");
        } else {
            isCollection = true;
            TipsUtil.showTips("收藏成功");
        }
        if (mSuperPlayerView != null) {
            mSuperPlayerView.mControllerWindow.showCollcetionState(isCollection);
            mSuperPlayerView.mControllerFullScreen.showCollcetionState(isCollection);
        }
        EventBusManager.getInstance().post(new EventBean(EventBean.CANCEL_COLLECTIONS, null));
    }

    @Override
    public void showVipState(VipStateBean vipStateBean) {
        Integer vipState = vipStateBean.getVipState();
        //0.普通用户；1.普通vip；2.永久vip；3.vip已过期
        if (vipState == 1 || vipState == 2) {
            mIvMember.setImageResource(R.drawable.member_equity);
        } else {
            mIvMember.setImageResource(R.drawable.course_open_member);
        }
        vipStates = vipState;
        if (null != mNoteFragment) {
            mNoteFragment.setVipState(vipState);
        }
    }

    @Override
    public void saveTimeSuccess() {
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏-1
            mConfig = Configuration.ORIENTATION_PORTRAIT;
            mSuperPlayerView.getLayoutParams().height = videoHeight;
            mSuperPlayerView.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;

            if ("1".equals(courseType)) {
                mGroup.setVisibility(View.GONE);
            } else {
                mGroup.setVisibility(View.VISIBLE);
            }
            mSuperPlayerView.mControllerWindow.show();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //横屏-2
            mConfig = Configuration.ORIENTATION_LANDSCAPE;
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mSuperPlayerView.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mSuperPlayerView.setLayoutParams(params);

            hideCommentLayout();
            mGroup.setVisibility(View.GONE);

            if (isFirst) {
                setFullScreen(this, true);
            } else {
                ImmersionBar.with(this).fullScreen(true).init();
                isFirst = true;
            }
        }
        mSuperPlayerView.dispatchConfigurationChanged(newConfig);
    }

    /**
     * 处理 vivo手机 横屏白边问题
     */
    private boolean isFirst;

    @Override
    public Resources getResources() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            AutoSizeCompat.autoConvertDensity(super.getResources(), 375f,
                    AutoSizeConfig.getInstance().getScreenWidth() < AutoSizeConfig.getInstance().getScreenHeight());
        }
        return super.getResources();
    }

    public void setFullScreen(Activity activity, boolean isfull) {
        ImmersionBar.with(activity).fullScreen(isfull).fitsSystemWindows(!isfull)
                .hideBar(isfull ? BarHide.FLAG_HIDE_BAR : BarHide.FLAG_SHOW_BAR).init();
    }

    private void setBarBgColor(boolean isWhite) {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(isWhite ? R.color.white : R.color.color_333333)
                .statusBarDarkFont(isWhite)
                .init();
    }

    /**
     * 保存观看时间
     */
    public void saveWatchTime() {
        if (mSuperPlayerView != null) {
            long currentTime = mSuperPlayerView.currentTime;
            long countTime = mSuperPlayerView.countTime;

            if (currentTime > countTime || (countTime - currentTime) < 4) {
                currentTime = countTime;
            }

            if (currentTime > 30 && mPresenter != null && playSecondBean != null) {
                if ("1".equals(courseType)) {
                    mPresenter.saveWatchTime(playSecondBean.getCourseId(), playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, currentTime == countTime ? 1 : 0, columnId);
                } else {
                    mPresenter.saveWatchTime(courseId, playSecondBean.getPointId(), playSecondBean.getWareId(), currentTime, currentTime == countTime ? 1 : 0, null);

                }
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    /**
     * 隐藏底部评论框
     */
    public void hideCommentLayout() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onLiveAndSmallWindowBack();
            //让程序进入后台但不销毁 这里写自己的逻辑代码就可以了
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 直播返回 和 播放页面小屏返回 事件
     * 直播全屏时返回到小窗口 否则 关闭播放页
     */
    private void onLiveAndSmallWindowBack() {
        if (isLivePlayback && isLiveFull) {
            mSuperPlayerView.onPause();
            mSuperPlayerView.updatePlayState(SuperPlayerConst.PLAYSTATE_PAUSE);

            mSuperPlayerView.getLayoutParams().height = videoHeight;
            mSuperPlayerView.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            if ("1".equals(courseType)) {
                mGroup.setVisibility(View.GONE);
            } else {
                mGroup.setVisibility(View.VISIBLE);
            }
            mSuperPlayerView.mControllerWindow.hideLockView();
            mSuperPlayerView.mControllerWindow.isShowIvLock = false;
            isLiveFull = false;
        } else {
            saveWatchTime();
        }
    }


    private void hideStatusBar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * ----------------------------------ViewPager滑动监听 开始 ----------------------------------
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentPosition = i;
        hideCommentLayout();
        if (0 == i) {
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_INTRODUCE);
        } else if (1 == i) {
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_CONTENTS);
        } else if (2 == i) {
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_DATUM);
        } else if (3 == i) {
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_NOTES);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * ----------------------------------ViewPager滑动监听 结束 ----------------------------------
     */


    /**
     * 获取当前播放时间： mCurrentTime
     */
    public Long getCurrentTime() {
        return mCurrentTime;
    }

    /**
     * 视频暂停 与 恢复播放 (笔记记录使用)
     * 当前播放时间： mCurrentTime
     */
    public void stopPlay() {
        if (mSuperPlayerView != null && !isLivePlayback) {
            mSuperPlayerView.onPause();
        }
    }

    public void resumePlay() {
        if (mSuperPlayerView != null && !isLivePlayback) {
            mSuperPlayerView.onResume();
        }
    }

    /**
     * 点击笔记，直接定位播放(用于笔记页面调用)
     *
     * @param pointId            小节id
     * @param pointWatchDuration 小节播放时间
     */
    public void clickNotePlay(String pointId, Long pointWatchDuration) {
        if (mCourseCatalogFragment != null && mCourseCatalogFragment.catalogAdapter != null) {
            mCourseCatalogFragment.catalogAdapter.choseChapterAndSetTime(pointId, pointWatchDuration);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView != null && !isPause && !isLivePlayback) {
            mSuperPlayerView.onResume();
        }
        if (verticalPop != null) {
            new Handler().postDelayed((Runnable) () -> {
                verticalPop.setAnimationStyle(R.style.ActionSheetDialogAnimation);
                verticalPop.update();
            }, 200);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (verticalPop != null && verticalPop.isShowing()) {
            verticalPop.setAnimationStyle(0);
            verticalPop.update();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!ActivityLifecycleCallbacksImpl.isBackground && mSuperPlayerView != null) {
            mSuperPlayerView.onPause();
        }
        if (ActivityLifecycleCallbacksImpl.isBackground) {
            UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_FULL_SCREEN_LOCK_SCREEN);
        }
    }

    @Override
    public void onDestroy() {
        if (mSuperPlayerView != null) {
            if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
                mSuperPlayerView.onPause();
            }
            mSuperPlayerView.resetPlayer();
        }
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
        super.onDestroy();
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
