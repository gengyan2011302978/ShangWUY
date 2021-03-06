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
 * company: ????????????
 * description: ???????????????
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
    //????????????
    public static String realPrice;
    //??????????????????
    private String buyStatus;

    /**
     * ??????????????? ??????
     */
    private int mConfig = Configuration.ORIENTATION_PORTRAIT;

    private int videoHeight;
    /**
     * 0.???????????????1.??????vip???2.??????vip???3.vip??????
     */
    private int vipStates;
    /**
     * ??????id
     */
    public static String courseId;
    /**
     * ??????id
     */
    String columnId = "";
    /**
     * ????????????
     */
    public static String columnName;

    private CourseIntroduceFragment mCourseIntroduceFragment;
    private CourseCatalogFragment mCourseCatalogFragment;
    /**
     * ?????? ???????????????
     */
    private NoteFragment mNoteFragment;
    /**
     * ??????ViewPager??????Fragment??????
     */
    private int currentPosition;
    /**
     * ??????????????????
     */
    private NetworkChangeReceiver networkChangeReceiver;
    /**
     * ???????????????????????????
     */
    private CourseCatalogSecondBean playSecondBean;
    /**
     * ?????????????????? 1.?????????2.??????
     */
    private Integer freeType;
    /**
     * ????????????
     */
    private boolean isCollection;
    /**
     * ????????????
     */
    private ShareBean mShareBean;
    /**
     * ????????????
     */
    private List<TCMultipleBean> mMultipleBeanList;
    /**
     * ???????????? ????????????
     */
    private float mSpeedLevel = 1f;
    /**
     * ??????????????? tabs
     */
    private String[] mTitles;

    /**
     * ?????? ?????????????????????
     */
    private String localpath;
    //?????????????????? ??? ??????
    public static String level_name = "";
    public static String level_desc = "";
    public static String level_url = "";
    /**
     * 1?????? ????????????
     */
    public String courseType = "1";

    /**
     * ?????????????????????  1???????????? 2????????????
     * isLiveFull????????????????????????
     */
    public static boolean isLivePlayback;
    private boolean isLiveFull;
    /**
     * ???????????????????????????????????????1-??????????????? 2-???????????????
     */
    public static int playPermission;
    /**
     * ????????????????????????
     */
    private long mCurrentTime;

    /**
     * V1.6.0 ?????????????????????id????????????????????????????????????
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
        //????????????????????????
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_333333)
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> {
                    //????????????????????????????????????
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
                    //???????????????
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
                //??????60s????????????????????????
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
                //????????????????????????
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
                //?????? ????????????????????? ??? ?????????
                mCurrentTime = currentTime;
            }
        });

        initMultipleList();
    }

    /**
     * ??????????????????
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
            mTvSellprice.setText("?????????????????" + realPrice);
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
        //????????????????????? ????????????id   ?????????????????????????????????????????????????????????
        String pointId = getIntent().getStringExtra(Constant.BUNDLE_POINT_ID);
        long pauseWatchTime = getIntent().getLongExtra(Constant.BUNDLE_COURSE_WATCH_TIME, 0L);

        List<CourseCatalogFirstBean> catalogFirstBeans = courseDetailAndCatalogBean.getCatalogFirstBeans();
        if (catalogFirstBeans != null && !catalogFirstBeans.isEmpty()) {
            getVideoPoint(catalogFirstBeans, pointId, pauseWatchTime);
        }
        //??????????????????id
        if (null != playSecondBean) {
            pointId = playSecondBean.getPointId();
        }

        //??????????????????
        CourseDetailBean courseDetailBean = courseDetailAndCatalogBean.getCourseDetailBean();
        if (courseDetailBean != null) {
            freeType = courseDetailBean.getFreeType();
            isCollection = courseDetailBean.getCollectionStatus() == 1;
            mSuperPlayerView.mControllerWindow.showCollcetionState(isCollection);
            mSuperPlayerView.mControllerFullScreen.showCollcetionState(isCollection);
            loadCourseBg(courseDetailBean.getCoverImg(), mIvVideoHolder);
            playPermission = courseDetailBean.getPlayPermission();
            isLivePlayback = courseDetailBean.getVideoType() == 2;

            //???????????????(0?????? 1??????)
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
            //???????????????????????????0???????????????tab
            if (courseNum > 0 || punchCardNum > 0 || exerciseNum > 0) {
                mTitles = new String[]{"??????", "??????", "??????", "??????"};
                fragments.add(2, DataFragment.newInstance(courseId, freeType, playPermission, courseType, punchCardNum, exerciseNum));
            } else {
                mTitles = new String[]{"??????", "??????", "??????"};
            }
            MyHomeAdapter adapter = new MyHomeAdapter(getSupportFragmentManager(), fragments);
            mVpDetail.setAdapter(adapter);
            mVpDetail.setOffscreenPageLimit(fragments.size());
            mTabDetail.setViewPager(mVpDetail, mTitles);

            //????????????????????????????????????????????????
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

            //?????? ??????????????? ?????????
            if (courseNum > 0 || punchCardNum > 0 || exerciseNum > 0) {
                setTitleCount(2, courseNum);
                setTitleCount(3, noteNum);
            } else {
                setTitleCount(2, noteNum);
            }

            //??????????????????
            mCourseCatalogFragment.showCourseCatalogList(list);
            if (mCourseCatalogFragment.catalogAdapter != null && playSecondBean != null) {
                mCourseCatalogFragment.catalogAdapter.pointId = playSecondBean.getPointId();
            }
            mNoteFragment.getPointId(pointId);
            EventBusManager.getInstance().post(new EventBean(playPermission, null));
        }
    }

    /**
     * ???????????????????????????
     *
     * @param isShow true??????????????????????????????????????????
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
     * ?????????????????????
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
     * ?????????????????????????????????
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
                        //???????????????????????????id
                        if (i == 0 && j == 0) {
                            mPointId = secondBean.getPointId();
                        }
                        secondBean.setWareId(firstBean.getCoursewareId());
                        if (TextUtils.isEmpty(pointId)) {
                            //?????????????????????????????????????????????
                            Integer isLastWatch = secondBean.getIsLastWatch();
                            if (isLastWatch == 1) {
                                playSecondBean = secondBean;
                                secondBean.setSelect(true);
                            } else {
                                secondBean.setSelect(false);
                            }
                        } else {
                            //???????????????????????????????????????
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
        //????????????????????????????????????
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
     * ??????????????? Title??????
     *
     * @param index        2:?????? 3:??????
     * @param commentCount ??????
     */
    public void setTitleCount(int index, int commentCount) {
        String name;
        if (mTitles.length == 4) {
            name = index == 2 ? "??????" : "??????";
        } else {
            name = "??????";
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
                //??????????????????
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
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "????????????");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_COLLECTION_INFO);
                break;
            case R.id.tv_open_member:
                //??????
                VipUtil.toVipPage(this);
                UmengUtil.umengCount(this, ConstantUmeng.COURSE_CLICK_COLLECTION_INTEREST_MEMBER);
                break;
            case R.id.iv_play:
                //????????????
                if (mCourseCatalogFragment != null) {
                    playCheck(playSecondBean);
                } else {
                    TipsUtil.showTips("????????????????????????");
                }
                break;

            //------------------- ?????????????????? ??? ???????????????---------------------
            case R.id.iv_off_back:
            case R.id.tv_find_more:
                finish();
                break;
            case R.id.course_off_layout:
                //???????????????????????????
                break;
            case R.id.iv_no_network_back:
                finish();
                break;
            case R.id.tv_refresh:
                getCourseDetail();
                break;
            case R.id.no_network_layout:
                //???????????????????????????
                break;
            default:
                break;
        }
    }

    /**
     * wifi?????????????????????????????????????????????
     */
    private boolean isWifiTip = true;

    /**
     * ???????????? ????????????
     */
    public void playCheck(CourseCatalogSecondBean secondBean) {
        playSecondBean = secondBean;
        if (!NetworkUtils.isConnected()) {
            TipsUtil.showTips("???????????????");
            return;
        }
        if (playSecondBean == null) {
            TipsUtil.showTips("????????????????????????");
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
     * ??????-??????????????????
     */
    public void playLocalVideo() {
        mIvVideoHolder.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);
        mIvBack.setVisibility(View.GONE);
        //??????loading view
        if (mSuperPlayerView.mControllerWindow != null) {
            mSuperPlayerView.mControllerWindow.showLoadingView();
        }
        //????????????
        mSuperPlayerView.updateTitle(getIntent().getStringExtra(Constant.BUNDLE_POINT_NAME));

        //??????????????????
        long pointWatchDuration = 0L;
        mSuperPlayerView.setStartPlayTime(pointWatchDuration);

        SuperPlayerModel model = new SuperPlayerModel();
        model.url = localpath;
        mSuperPlayerView.playWithModel(model);

        //??????????????????
        if (mSuperPlayerView.mVodPlayer != null) {
            mSuperPlayerView.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    /**
     * ?????????????????????videoId;
     */
    private String videoId;

    /**
     * ??????-????????????
     */
    public void playNetVideo(CourseCatalogSecondBean secondBean) {
        //wifi????????????????????????????????????
        if (NetworkUtils.getNetworkType() != NetworkUtils.NetworkType.NETWORK_WIFI) {
            if (isWifiTip) {
                ToastUtils.show("????????????WIFI??????????????????????????????");
                isWifiTip = false;
            }
        }
        mIvVideoHolder.setVisibility(View.GONE);
        mIvPlay.setVisibility(View.GONE);
        mIvBack.setVisibility(View.GONE);
        //??????loading view???????????????
        if (mSuperPlayerView.mControllerWindow != null) {
            mSuperPlayerView.mControllerWindow.showLoadingView();
            mSuperPlayerView.updateTitle(secondBean.getPointIdName());
        }

        //??????????????????
        Long pointWatchDuration = secondBean.getPointWatchDuration();
        mSuperPlayerView.setStartPlayTime(pointWatchDuration);


        if (isLivePlayback) {
            //???????????????????????????UI
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
                //???????????????????????????????????????
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

        //??????????????????
        if (mSuperPlayerView.mVodPlayer != null) {
            mSuperPlayerView.mVodPlayer.setRate(mSpeedLevel);
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CourseCatalogSecondBean secondBean) {
        if (!TextUtils.isEmpty(secondBean.getPointId())) {
            localpath = "";
            playCheck(secondBean);
        } else {
            //????????????????????????????????????????????????????????????vip????????????
            if (playSecondBean != null && TextUtils.equals(playSecondBean.getPointId(), mPointId)) {
                permissionCheck();
            }
        }
    }

    /**
     * ?????????????????????vip?????? ??? ????????????????????????
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
            TipsUtil.showTips("???????????????");
        }
    }

    /**
     * ?????????????????????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDeleteEvent(DeleteDownLoadBean downLoadBean) {
        if (verticalPop != null && verticalPop.isShowing() && mCourseCatalogFragment.catalogAdapter != null) {
            List<MultiItemEntity> data = mCourseCatalogFragment.catalogAdapter.getData();
            verticalPop.refreshData(data);
        }
    }

    /**
     * WebView???????????????????????????????????????
     * ??????????????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(WebToStudyBean webToStudyBean) {
        finish();
    }

    /**
     * ---------------------------------- SuperPlayerView??????????????? ?????? ----------------------------------
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
            TipsUtil.showTips("???????????????");
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
     * ?????? ??? ???????????????????????????????????? ???????????????
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
        //????????????????????????????????????
        if (mCourseCatalogFragment != null && mCourseCatalogFragment.catalogAdapter != null) {
            mCourseCatalogFragment.catalogAdapter.changeNextVideo();
        }
    }

    /**
     * ?????????????????? true?????????
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
     * ---------------------------------- SuperPlayerView??????????????? ?????? ----------------------------------
     */


    /**
     * ??????????????????????????????????????????
     *
     * @param shareBean ????????????
     * @param type      ?????????????????? 1?????? 2??????
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
     * ?????? ????????????
     *
     * @param statusId ??????????????????
     */
    private void shareData(int statusId) {
        ShareUtils.shareUrlData(this, mShareBean, statusId);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     *
     * @param type    1.???????????? 2.?????? 3.????????? 4.??????
     * @param otherId ????????????id/????????????id/???????????????id/????????????id
     * @param content ????????????
     */
    public void getShareItemData(int type, String otherId, String content) {
        if (mPresenter != null) {
            mPresenter.getShareItemData(type, otherId, content, courseType);

            UmengUtil.umengCount(this, type == 1 ? ConstantUmeng.COURSE_COMMENT_SHARE : ConstantUmeng.COURSE_NOTE_SHARE);
        }
    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        //??????????????????
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
            TipsUtil.showTips("????????????");
        } else {
            isCollection = true;
            TipsUtil.showTips("????????????");
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
        //0.???????????????1.??????vip???2.??????vip???3.vip?????????
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
            //??????-1
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

            //??????-2
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
     * ?????? vivo?????? ??????????????????
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
     * ??????????????????
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
     * ?????????????????????
     */
    public void hideCommentLayout() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onLiveAndSmallWindowBack();
            //????????????????????????????????? ??????????????????????????????????????????
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ???????????? ??? ???????????????????????? ??????
     * ????????????????????????????????? ?????? ???????????????
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
     * ----------------------------------ViewPager???????????? ?????? ----------------------------------
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
     * ----------------------------------ViewPager???????????? ?????? ----------------------------------
     */


    /**
     * ??????????????????????????? mCurrentTime
     */
    public Long getCurrentTime() {
        return mCurrentTime;
    }

    /**
     * ???????????? ??? ???????????? (??????????????????)
     * ????????????????????? mCurrentTime
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
     * ?????????????????????????????????(????????????????????????)
     *
     * @param pointId            ??????id
     * @param pointWatchDuration ??????????????????
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
