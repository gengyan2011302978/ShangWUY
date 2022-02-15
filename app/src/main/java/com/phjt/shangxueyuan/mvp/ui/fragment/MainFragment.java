package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.tools.ScreenUtils;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ActivityInfoBean;
import com.phjt.shangxueyuan.bean.AnnouncementListBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouRecommendListBean;
import com.phjt.shangxueyuan.bean.CourseRecommendListBean;
import com.phjt.shangxueyuan.bean.HomeHotRecommendListBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.PopularArticleListBean;
import com.phjt.shangxueyuan.bean.ProdInfoBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserWatchHistoryBean;
import com.phjt.shangxueyuan.bean.event.MsgEvent;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerMainComponent;
import com.phjt.shangxueyuan.mvp.contract.MainContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.MainPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.AllStudyCampActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ArticleClassifyActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.HomePagerActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.HotRecommendActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OffInvalidActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.PassagewayActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.QuestionsAndAnswersActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ShareActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageCourseAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageCourseAdapterBanner;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageCourseAdapterHot;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageCourseAdapterLive;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageCourseAdapterNew;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageInformationAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageVipCourseAdapter;
import com.phjt.shangxueyuan.utils.BannerUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.MZLiveUtils;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.GlideImageLoader;
import com.phjt.shangxueyuan.widgets.NoticeFlipView;
import com.phjt.shangxueyuan.widgets.RecycleViewDivider;
import com.phjt.shangxueyuan.widgets.banner.ImageHolderCreator;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;


/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */

public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.marquee_notice)
    NoticeFlipView marqueeNotice;
    @BindView(R.id.iv_master)
    ImageView ivMaster;
    @BindView(R.id.iv_practice)
    ImageView ivPractice;
    @BindView(R.id.iv_number)
    ImageView ivNumber;
    @BindView(R.id.iv_growth)
    ImageView ivGrowth;
    @BindView(R.id.iv_answer)
    ImageView ivAnswer;
    @BindView(R.id.tv_circle)
    TextView tvCircle;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.ll_icons)
    LinearLayout llIcons;
    @BindView(R.id.ll_icons_tow)
    RelativeLayout llIconsTow;
    @BindView(R.id.iv_notice)
    TextView ivNotice;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.srf_main)
    SmartRefreshLayout srfMain;
    @BindView(R.id.canstra)
    ConstraintLayout canStra;
    @BindView(R.id.iv_jpst)
    ImageView ivJpst;
    @BindView(R.id.relat_jpst)
    RelativeLayout relatJpst;
    @BindView(R.id.iv_kztd)
    ImageView ivKztd;
    @BindView(R.id.iv_cftd)
    ImageView ivCftd;
    @BindView(R.id.relat_master)
    RelativeLayout relatMaster;
    @BindView(R.id.relat_practice)
    RelativeLayout relatPractice;
    @BindView(R.id.relat_circle)
    RelativeLayout relatCircle;
    @BindView(R.id.relat_editor_title)
    RelativeLayout relatEditorTitle;
    @BindView(R.id.relat_project)
    RelativeLayout relatProject;
    @BindView(R.id.recycle_hot_information)
    RecyclerView mRecycleHotInformation;
    @BindView(R.id.recycle_good_course)
    RecyclerView mRecycleGoodCourse;
    @BindView(R.id.recycle_editor_course)
    RecyclerView recycleEditorCourse;
    @BindView(R.id.relat_informationtitle)
    RelativeLayout mRelatInformationtitle;

    HompageInformationAdapter adapter;
    HompageCourseAdapter hompageCourseAdapter;
    HompageCourseAdapterNew hompageCourseAdapterNew;
    HompageCourseAdapterNew hompageCourseAdapterNewLabel;
    HompageCourseAdapterLive hompageCourseAdapterLive;
    @BindView(R.id.tv_passageway)
    TextView tvPassageway;

    @BindView(R.id.ll_iconst)
    LinearLayout llIconst;
    @BindView(R.id.ic_banner_center)
    Banner icBannerCenter;
    @BindView(R.id.ic_last_center)
    Banner icLastCenter;
    @BindView(R.id.ic_banner_bottom)
    Banner icBannerBottom;
    @BindView(R.id.relat_title)
    RelativeLayout relatTitle;
    @BindView(R.id.textttt)
    TextView textttt;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.iv_continue)
    ImageView ivContinue;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.textt)
    TextView textt;
    @BindView(R.id.texttt)
    TextView texttt;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.relat_coursetitle)
    RelativeLayout relatCoursetitle;
    public static List<InitIndexSiteInfoBean> initIndexSiteInfoBeanList = new ArrayList<>();

    @BindView(R.id.relat_playback)
    RelativeLayout relatPlayback;
    @BindView(R.id.recycle_playback)
    RecyclerView mRecyclePlayback;
    HompageCourseAdapterLive playbackAdapter = new HompageCourseAdapterLive(new ArrayList<>());
    @BindView(R.id.relat_business_train)
    RelativeLayout relatBusinessTrain;
    @BindView(R.id.recycle_business_train)
    RecyclerView mRecycleBusinessTrain;
    @BindView(R.id.recycle_banner)
    RecyclerView recycleBanner;
    HompageCourseAdapter businessTrainAdapter = new HompageCourseAdapter(new ArrayList<>());
    HompageCourseAdapterHot hompageCourseAdapterHot = new HompageCourseAdapterHot(new ArrayList<>());
    HompageCourseAdapterBanner hompageCourseAdapterBanner = new HompageCourseAdapterBanner(new ArrayList<>());
    HompageCourseAdapter digitalEconomyAdapter = new HompageCourseAdapter(new ArrayList<>());
    @BindView(R.id.relat_financial_culture)
    RelativeLayout relatFinancialCulture;
    @BindView(R.id.line_financial_culture)
    ImageView lineFinancialCulture;
    @BindView(R.id.recycle_financial_culture)
    RecyclerView mRecycleFinancialCulture;
    HompageVipCourseAdapter financialCultureAdapter = new HompageVipCourseAdapter(new ArrayList<>());

    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_continue_watch)
    TextView tvContinueWatch;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.ic_activity)
    ImageView icActivity;
    @BindView(R.id.iv_close_pop)
    ImageView ivClosePop;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.relat_continue)
    RelativeLayout relatContinue;
    @BindView(R.id.relat_activity)
    RelativeLayout relatActivity;
    ActivityInfoBean activityInfoBean;
    UserWatchHistoryBean userWatchHistoryBean;
    @BindView(R.id.tv_gotobuy)
    TextView tvGotobuy;
    @BindView(R.id.line_information)
    ImageView lineInformation;
    @BindView(R.id.nt_scroll)
    NestedScrollView ntScroll;
    List<CouRecommendListBean> courseRecommendListBeans;
    List<CouRecommendListBean> courseRecommendListBeansTow;
    private boolean isClose = true;
    private boolean isClosePop = true;
    private int lastScrollY;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        srfMain.setEnableLoadMore(false);
        if (mPresenter != null) {
            Objects.requireNonNull(mPresenter).ListBanner("1");
            Objects.requireNonNull(mPresenter).ListBanner("11");
            Objects.requireNonNull(mPresenter).ListBanner("12");
            Objects.requireNonNull(mPresenter).ListBanner("2");
            Objects.requireNonNull(mPresenter).getAnnouncementList();
            Objects.requireNonNull(mPresenter).getCourseRecommend();
            Objects.requireNonNull(mPresenter).getCourseCategory();
            //1免费公开课 2vip精选 3直播 4主编精选
            Objects.requireNonNull(mPresenter).getCouRecommend(1);
            Objects.requireNonNull(mPresenter).getCouRecommend(2);
            Objects.requireNonNull(mPresenter).getCouRecommend(3);
            Objects.requireNonNull(mPresenter).getCouRecommend(4);
            Objects.requireNonNull(mPresenter).getHomeHotRecommend();
            Objects.requireNonNull(mPresenter).getInitIndexSiteInfo();
            Objects.requireNonNull(mPresenter).getPopularArticle();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null != mPresenter) {
                        Objects.requireNonNull(mPresenter).ListBanner("7");
                    }
                }
            }, 1100);
        }
        srfMain.setOnRefreshLoadMoreListener(this);
        mRecycleHotInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleGoodCourse.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycleEditorCourse.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        hompageCourseAdapterNew = new HompageCourseAdapterNew(courseRecommendListBeans);
        hompageCourseAdapterNewLabel = new HompageCourseAdapterNew(courseRecommendListBeansTow);
        hompageCourseAdapterNew.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goCourseDetail(courseRecommendListBeans.get(position).getCouId() + "",
                        courseRecommendListBeans.get(position).getName(),
                        courseRecommendListBeans.get(position).getCouDesc(),
                        courseRecommendListBeans.get(position).getCoverImg());
            }
        });
        hompageCourseAdapterNewLabel.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goCourseDetail(courseRecommendListBeansTow.get(position).getCouId() + "",
                        courseRecommendListBeansTow.get(position).getName(),
                        courseRecommendListBeansTow.get(position).getCouDesc(),
                        courseRecommendListBeansTow.get(position).getCoverImg());
            }
        });
        initCourseCategoryLive(mRecyclePlayback, playbackAdapter, true);
        initCourseHotCategory(mRecycleBusinessTrain, hompageCourseAdapterHot, true);
        initCourseBannerCategory(recycleBanner, hompageCourseAdapterBanner, true);
        initCourseVipCategory(mRecycleFinancialCulture, financialCultureAdapter, false);
        HomePagerActivity.MyOnTouchListener myOnTouchListener = new HomePagerActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
                if (ev.getAction() == MotionEvent.ACTION_UP) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isClosePop && relatActivity != null) {
                                relatActivity.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 100);
                }
                return false;
            }
        };
        ((HomePagerActivity) getActivity())
                .registerMyOnTouchListener(myOnTouchListener);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            int scrollY = ntScroll.getScrollY();
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            System.out.println(lastScrollY - scrollY + "滚动监听：" + scrollY);

            if (relatContinue != null && relatActivity != null) {
                if (lastScrollY - scrollY < 0) {
                    relatContinue.setVisibility(View.GONE);
                    relatActivity.setVisibility(View.GONE);
                } else if (lastScrollY - scrollY > 0) {
                    if (isClose) {
                        relatContinue.setVisibility(View.VISIBLE);
                    }

                    relatActivity.setVisibility(View.GONE);
                }
                lastScrollY = scrollY;
            }
        }
    };


    private void initCourseCategoryLive(RecyclerView recyclerView, HompageCourseAdapterLive courseAdapter, boolean isLinearLayout) {
        if (isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new RecycleViewDivider(
                    getActivity(), LinearLayoutManager.VERTICAL,
                    ScreenUtils.dip2px(getActivity(), 10),
                    ContextCompat.getColor(getActivity(), R.color.white)));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            CouRecommendListBean liveBean = (CouRecommendListBean) adapter.getItem(position);
            if (liveBean == null) {
                return;
            }
            if (!TextUtils.isEmpty(liveBean.getCouId())) {
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, liveBean.getCouId() + "");
                startActivity(intent);
                return;
            }

            CommonHttpManager.getInstance(getActivity())
                    .obtainRetrofitService(ApiService.class)
                    .liveInfo(liveBean.getId() + "")
                    .compose(RxUtils.applySchedulers())
                    .subscribe((BaseBean<LiveBean> baseBean) -> {
                        if (baseBean.isOk()) {
                            MZLiveUtils.toLivePlay(mContext, liveBean.getTicketId(), liveBean.getLiveStyle(), liveBean.getId() + "");
                        } else {

                        }
                    }, throwable -> LogUtils.e("网络异常"));

        });
    }

    private void initCourseBannerCategory(RecyclerView recyclerView, HompageCourseAdapterBanner courseAdapter, boolean isLinearLayout) {
        if (isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new RecycleViewDivider(
                    getActivity(), LinearLayoutManager.VERTICAL,
                    ScreenUtils.dip2px(getActivity(), 10),
                    ContextCompat.getColor(getActivity(), R.color.white)));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            ListBannerBean bean = (ListBannerBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            BannerUtils.bannerClick(getActivity(), bean, getIdByType(9));
        });
    }

    private void initCourseHotCategory(RecyclerView recyclerView, HompageCourseAdapterHot courseAdapter, boolean isLinearLayout) {
        if (isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new RecycleViewDivider(
                    getActivity(), LinearLayoutManager.VERTICAL,
                    ScreenUtils.dip2px(getActivity(), 10),
                    ContextCompat.getColor(getActivity(), R.color.white)));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomeHotRecommendListBean bean = (HomeHotRecommendListBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, bean.getId() + "");
            intent.putExtra(Constant.BUNDLE_COURSE_NAME, bean.getName());
            startActivity(intent);
        });
    }

    private void initCourseVipCategory(RecyclerView recyclerView, HompageVipCourseAdapter courseAdapter, boolean isLinearLayout) {
        if (isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new RecycleViewDivider(
                    getActivity(), LinearLayoutManager.VERTICAL,
                    ScreenUtils.dip2px(getActivity(), 10),
                    ContextCompat.getColor(getActivity(), R.color.white)));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            CouRecommendListBean bean = (CouRecommendListBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            goCourseDetail(bean.getCouId(), bean.getName(), bean.getCouDesc(), bean.getCoverImg());
        });
    }

    private void initCourseCategory(RecyclerView recyclerView, HompageCourseAdapter courseAdapter, boolean isLinearLayout) {
        if (isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new RecycleViewDivider(
                    getActivity(), LinearLayoutManager.VERTICAL,
                    ScreenUtils.dip2px(getActivity(), 10),
                    ContextCompat.getColor(getActivity(), R.color.white)));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            CourseRecommendListBean bean = (CourseRecommendListBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            goCourseDetail(bean.getId(), bean.getName(), bean.getCouDesc(), bean.getCoverImg());
        });
    }

    private void goCourseDetail(String id, String name, String desc, String img) {
        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra(Constant.BUNDLE_COURSE_ID, id);
        intent.putExtra(Constant.BUNDLE_LEVEL_NAME, name);
        intent.putExtra(Constant.BUNDLE_LEVEL_DESC, desc);
        intent.putExtra(Constant.BUNDLE_LEVEL_URL, img);
        startActivity(intent);
    }

    @Override
    public void ListBannerSuccess(BaseBean<List<ListBannerBean>> bean, String type) {
        if ("11".equals(type)) {
            recycleBanner.setAdapter(hompageCourseAdapterBanner);
            hompageCourseAdapterBanner.setNewData(bean.data);
        }
        if ("12".equals(type)) {
            if (null == icLastCenter) {
                return;
            }
            List<String> images = new ArrayList<>();
            if (bean != null && bean.data.size() > 0) {
                for (int i = 0; i < bean.data.size(); i++) {
                    ListBannerBean bannerBean = bean.data.get(i);
                    images.add(bannerBean.getCoverUrl());
                }
            } else {
                //设置默认图片，在GlideImageLoader显示
                icLastCenter.setVisibility(View.GONE);
                images.add("");
            }
            icLastCenter.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImageLoader(new GlideImageLoader(), 1)
                    .setImages(images, "")
                    .setBannerAnimation(Transformer.ZoomOutSlide)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setOffscreenPageLimit(images.size())
                    .setOnBannerListener(position -> {
                        BannerUtils.bannerClick(mContext, bean.data.size() == 0 ? null : bean.data.get(position), getIdByType(9));
                        onEventMainPage("顶部轮播图", UmengUtil.EVENT_DBLBT_MAIN);
                    })
                    .start();
        }
        if ("1".equals(type)) {
            if (null == banner) {
                return;
            }
            List<String> images = new ArrayList<>();
            if (bean != null && bean.data.size() > 0) {
                for (int i = 0; i < bean.data.size(); i++) {
                    ListBannerBean bannerBean = bean.data.get(i);
                    images.add(bannerBean.getCoverUrl());
                }
            } else {
                //设置默认图片，在GlideImageLoader显示
                images.add("");
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImageLoader(new GlideImageLoader(), 1)
                    .setImages(images, "")
                    .setBannerAnimation(Transformer.ZoomOutSlide)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setOffscreenPageLimit(images.size())
                    .setOnBannerListener(position -> {
                        BannerUtils.bannerClick(mContext, bean.data.size() == 0 ? null : bean.data.get(position), getIdByType(9));
                        onEventMainPage("顶部轮播图", UmengUtil.EVENT_DBLBT_MAIN);
                    })
                    .start();
        } else if ("2".equals(type)) {
            if (null == icBannerCenter) {
                return;
            }
            List<String> images = new ArrayList<>();
            if (bean != null && bean.data.size() > 0) {
                for (int i = 0; i < bean.data.size(); i++) {
                    ListBannerBean bannerBean = bean.data.get(i);
                    images.add(bannerBean.getCoverUrl());
                }
                icBannerCenter.setVisibility(View.VISIBLE);
                ivBanner.setVisibility(View.VISIBLE);
            } else {
                //设置默认图片，在GlideImageLoader显示
                icBannerCenter.setVisibility(View.GONE);
                ivBanner.setVisibility(View.GONE);
                images.add("");
            }
            icBannerCenter.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImageLoader(new GlideImageLoader(), 1)
                    .setImages(images, "")
                    .setBannerAnimation(Transformer.ZoomOutSlide)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setOffscreenPageLimit(images.size())
                    .setOnBannerListener(position -> {
                        if (initIndexSiteInfoBeanList != null && initIndexSiteInfoBeanList.size() > 5) {
                            BannerUtils.bannerClick(mContext, bean.data.size() == 0 ? null : bean.data.get(position), getIdByType(9));
                        } else {
                            BannerUtils.bannerClick(mContext, bean.data.size() == 0 ? null : bean.data.get(position), null);
                        }
                        onEventMainPage("中部banner", UmengUtil.EVENT_ZBBANNER_MAIN);
                    })
                    .start();
        } else if ("7".equals(type)) {
            if (null == icBannerBottom) {
                return;
            }
            List<String> images = new ArrayList<>();
            if (bean != null && bean.data.size() > 0) {
                for (int i = 0; i < bean.data.size(); i++) {
                    ListBannerBean bannerBean = bean.data.get(i);
                    images.add(bannerBean.getCoverUrl());
                }
                icBannerBottom.setVisibility(View.VISIBLE);
            } else {
                //设置默认图片，在GlideImageLoader显示
                icBannerBottom.setVisibility(View.GONE);
                images.add("");
                bean.data = new ArrayList<>();
            }
            ImageHolderCreator holderCreator = new ImageHolderCreator();
            holderCreator.setOnMainBannerClick(bannerBean -> BannerUtils.bannerClick(mContext, bannerBean, getIdByType(9)));
            List<ListBannerBean> bannerList = new ArrayList<>();
            if (bean.data != null) {
                bannerList.addAll(bean.data);
            }
            int leftMargin = bannerList.size() > 1 ? 1 : 0;
            int rightMargin = bannerList.size() > 1 ?
                    AutoSizeUtils.dp2px(mContext, 20) : 0;
            icBannerBottom.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setImageLoader(new GlideImageLoader(), 1)
                    .setImages(images, "")
                    .setPageMargin(leftMargin, rightMargin, rightMargin)
                    .isAutoPlay(false)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setOffscreenPageLimit(images.size())
                    .setOnBannerListener(position -> {
                        BannerUtils.bannerClick(mContext, bean.data.size() == 0 ? null : bean.data.get(position), getIdByType(9));
                        onEventMainPage("顶部轮播图", UmengUtil.EVENT_DBLBT_MAIN);
                    })
                    .start();
//            icBannerBottom.setHolderCreator(holderCreator)
//                    .setAutoPlay(false)
//                    .setPageMargin(leftMargin, rightMargin, rightMargin)
//                    .setIndicator(new IndicatorView(getActivity()).setIndicatorRadius(2f).setIndicatorRatio(3))
//                    .setPages(bannerList);
        }

        srfMain.finishRefresh();
    }

    @Override
    public void canLoadMore() {
        if (srfMain != null) {
            srfMain.finishRefresh();
        }
    }

    @Override
    public void getAnnouncementListSuccess(List<AnnouncementListBean> bean) {
        if (null == marqueeNotice) {
            return;
        }
        if (bean != null && bean.size() > 0) {
            marqueeNotice.setViews(getActivity(), bean);
        }
        srfMain.finishRefresh();
    }

    @Override
    public void getPopularArticleListSuccess(List<PopularArticleListBean> bean) {
        if (null == mRelatInformationtitle) {
            return;
        }
        if (bean.size() == 0) {
            mRelatInformationtitle.setVisibility(View.GONE);
            lineInformation.setVisibility(View.GONE);
        } else {
            mRelatInformationtitle.setVisibility(View.VISIBLE);
            lineInformation.setVisibility(View.VISIBLE);
        }

        adapter = new HompageInformationAdapter(bean);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == 0) {
                onEventMainPage("热门资讯位置1", UmengUtil.EVENT_RMZX1_MAIN);
            }
            if (position == 1) {
                onEventMainPage("热门资讯位置2", UmengUtil.EVENT_RMZX2_MAIN);
            }
            if (position == 2) {
                onEventMainPage("热门资讯位置3", UmengUtil.EVENT_RMZX3_MAIN);
            }
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_DETAILS)
                    + "?id=" + bean.get(position).getId());
            startActivity(intent);
        });
        mRecycleHotInformation.setAdapter(adapter);
        adapter.setNewData(bean);
    }

    @Override
    public void getCourseRecommendListSuccess(List<CourseRecommendListBean> bean) {
//        if (null == relatCoursetitle) {
//            return;
//        }
//        if (bean.size() == 0) {
//            relatCoursetitle.setVisibility(View.GONE);
//            lineBg.setVisibility(View.GONE);
//        } else {
//            relatCoursetitle.setVisibility(View.VISIBLE);
//            lineBg.setVisibility(View.VISIBLE);
//        }
//        courseRecommendListBeans = bean;


    }

    @Override
    public void getCourseCategoryListSuccess(List<CourseRecommendListBean> bean) {
        if (bean == null) {
            return;
        }
        List<CourseRecommendListBean> playBack = new ArrayList<>();
        List<CourseRecommendListBean> businessTrain = new ArrayList<>();
        List<CourseRecommendListBean> digitalEconomy = new ArrayList<>();
        List<CourseRecommendListBean> financialCulture = new ArrayList<>();
        for (CourseRecommendListBean course : bean) {
            if (9 == course.getType()) {
                playBack.add(course);
            } else if (5 == course.getType()) {
                businessTrain.add(course);
            } else if (10 == course.getType()) {
                digitalEconomy.add(course);
            } else if (8 == course.getType()) {
                financialCulture.add(course);
            }
        }

        relatPlayback.setVisibility(playBack.size() == 0 ? View.GONE : View.VISIBLE);
        relatBusinessTrain.setVisibility(businessTrain.size() == 0 ? View.GONE : View.VISIBLE);

    }

    @Override
    public void getCouRecommendSuccess(List<CouRecommendListBean> bean, int type) {
        if (type == 3) {
            mRecyclePlayback.setAdapter(playbackAdapter);
            playbackAdapter.setNewData(bean);
        }
        if (type == 1) {
            mRecycleGoodCourse.setAdapter(hompageCourseAdapterNew);
            courseRecommendListBeans = bean;
            hompageCourseAdapterNew.setNewData(bean);
        }
        if (type == 4) {
            if (bean.size() == 0) {
                recycleEditorCourse.setVisibility(View.GONE);
                relatEditorTitle.setVisibility(View.GONE);
            }
            for (int i = 0; i < bean.size(); i++) {
                bean.get(i).setCouDesc("");
            }
            recycleEditorCourse.setAdapter(hompageCourseAdapterNewLabel);
            courseRecommendListBeansTow = bean;
            hompageCourseAdapterNewLabel.setNewData(bean);
        }
        if (type == 2) {
            mRecycleFinancialCulture.setAdapter(financialCultureAdapter);
            financialCultureAdapter.setNewData(bean);
        }
    }

    @Override
    public void getInitIndexSiteInfoListSuccess(List<InitIndexSiteInfoBean> bean) {
        initIndexSiteInfoBeanList = bean;

        if (initIndexSiteInfoBeanList != null) {
            if (initIndexSiteInfoBeanList.size() > 3) {
                //保存初级、中级、高级课程的id
                SPUtils.getInstance().put(Constant.SP_PRIMARY_COURSE_ID, getIdByType(5));
                SPUtils.getInstance().put(Constant.SP_MIDDLE_COURSE_ID, getIdByType(6));
                SPUtils.getInstance().put(Constant.SP_HIGH_COURSE_ID, getIdByType(7));

                //保存精品试听课程id
                String practiceId = getIdByType(2);
                SPUtils.getInstance().put(Constant.SP_PRACTICE_ID, practiceId);
            }

            if (initIndexSiteInfoBeanList.size() > 5) {
                //保存直播回放id
                String liveListId = getIdByType(9);
                SPUtils.getInstance().put(Constant.SP_LIVE_LIST_ID, liveListId);
            }
        }
    }

    @Override
    public void getHomeHotRecommendSuccess(List<HomeHotRecommendListBean> bean) {
        mRecycleBusinessTrain.setAdapter(hompageCourseAdapterHot);
        hompageCourseAdapterHot.setNewData(bean);
    }

    @Override
    public void getActivityInfoSuccess(ActivityInfoBean bean) {
        if (null == relatActivity) {
            isClosePop = false;
            return;
        }
        if (null == bean) {
            relatActivity.setVisibility(View.GONE);
            isClosePop = false;
        } else {
            activityInfoBean = bean;
            if (bean.getActivityStatus() == 1) {
                relatActivity.setVisibility(View.VISIBLE);
                isClosePop = true;
            } else {
                relatActivity.setVisibility(View.GONE);
                isClosePop = false;
            }
        }
    }

    @Override
    public void getUserWatchHistorySuccess(UserWatchHistoryBean bean) {
        if (null == relatContinue) {
            return;
        }
        if (null == bean) {
            relatContinue.setVisibility(View.GONE);
        } else if (isClose) {
            if (bean.getUpState() == 1) {
                userWatchHistoryBean = bean;
                tvComment.setText(String.format("上次观看：%s", bean.getCourseName()));
                GlideUtils.load(bean.getCouverImg(), ivLogo);
                tvContinueWatch.setText(bean.getPointName());
                ivContinue.setVisibility(View.VISIBLE);
                tvContinueWatch.setTextColor(ContextCompat.getColor(getActivity(), R.color.color999999));
                relatContinue.setVisibility(View.VISIBLE);
            } else {
                userWatchHistoryBean = bean;
                SpannableStringBuilder builder = new SpannableStringBuilder("上次观看：抱歉该课程已下架");
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(102, 102, 102));
                builder.setSpan(redSpan, 5, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                GlideUtils.load(bean.getCouverImg(), ivLogo);
                ivContinue.setVisibility(View.INVISIBLE);
                tvComment.setText(builder);
                tvContinueWatch.setText("查看更多课程>");
                tvContinueWatch.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_F650C));
                relatContinue.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void getSwyProdSuccess(ProdInfoBean bean) {
        SPUtils.getInstance().put("WXCODE", bean.getWx());
    }

    @Override
    public void loadInviteSharetSuccess(String data) {
        if (mPresenter != null) {
            mPresenter.inviteShare(data);
        }
    }

    @Override
    public void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data, String url) {
        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHAREWX) + "?code=" + url + "&mobile=" + phoneNumber);
        startActivity(intent);
        UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_SHARE_MY_INVITATION);
    }

    @Override
    public void answersStateSuccess(BaseBean data) {
        Intent intent = new Intent(getActivity(), TopicInfoActivity.class);
        intent.putExtra(Constant.BUNDLE_TOPIC_ID, String.valueOf(data.data));
        startActivity(intent);
    }

    @OnClick({R.id.relat_title, R.id.relat_coursetitle, R.id.relat_playback, R.id.relat_business_train, R.id.relat_financial_culture,
            R.id.tv_gotobuy, R.id.tv_comment, R.id.tv_continue_watch, R.id.iv_close_pop, R.id.ic_activity, R.id.iv_close, R.id.tv_passageway,
            R.id.relat_informationtitle, R.id.relat_master, R.id.relat_practice, R.id.relat_circle, R.id.relat_project, R.id.iv_master, R.id.iv_practice, R.id.iv_number, R.id.iv_growth, R.id.iv_answer, R.id.tv_circle,
            R.id.tv_project, R.id.iv_jpst, R.id.iv_cftd, R.id.iv_kztd, R.id.tv_digital_economy, R.id.tv_boc_course, R.id.tv_invitation})
    public void onViewClicked(View view) {
        Intent intent = null;
        String name = "";
        if (initIndexSiteInfoBeanList.size() == 0) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_answer:
//                if (mPresenter != null) {
//                    mPresenter.answersState();
//                }
                intent = new Intent(getActivity(), QuestionsAndAnswersActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_growth:
                intent = new Intent(getActivity(), AllStudyCampActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_master:
                name = "权威证书";
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_NAV));
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, name);
                startActivity(intent);
                onEventMainPage("权威证书", UmengUtil.EVENT_QWZS_MAIN);
                break;
            case R.id.iv_practice:
            case R.id.relat_practice:
                intent = new Intent(getActivity(), CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(2));
                startActivity(intent);
                onEventMainPage("公开课", UmengUtil.EVENT_JPST_MAIN);
                break;
            case R.id.tv_circle:
            case R.id.relat_circle:
                name = "备考宝典";
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_REFERENCE));
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, name);
                intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(2));
                startActivity(intent);
                onEventMainPage("备考宝典", UmengUtil.EVENT_BKBD_MAIN);
                break;
            case R.id.tv_project:
            case R.id.tv_gotobuy:
            case R.id.relat_project:
                name = "VIP会员";
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_VIP));
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, name);
                startActivity(intent);
                onEventMainPage("VIP会员", UmengUtil.EVENT_VIP_MAIN);
                break;
//            case R.id.tv_live_playback:
//                //直播回放
//                if (initIndexSiteInfoBeanList != null && initIndexSiteInfoBeanList.size() > 5) {
//                    intent = new Intent(getActivity(), CourseCategoryActivity.class);
//                    intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(9));
//                    startActivity(intent);
//                }
//                break;
            case R.id.tv_digital_economy:
                //数字经济
                if (initIndexSiteInfoBeanList != null && initIndexSiteInfoBeanList.size() > 5) {
                    intent = new Intent(getActivity(), CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(10));
                    startActivity(intent);
                }
                break;
            case R.id.iv_number:
            case R.id.relat_financial_culture:
                //BOC课程
                intent = new Intent(mContext, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(5));
                startActivity(intent);
                break;
            case R.id.tv_boc_course:
                //BOC课程
                intent = new Intent(mContext, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(11));
                startActivity(intent);
                break;
            case R.id.tv_passageway:
                intent = new Intent(getActivity(), CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(12));
                startActivity(intent);
                onEventMainPage("中国文化", UmengUtil.EVENT_CFTD_MAIN);
                break;
            case R.id.tv_invitation:
                //邀请有礼
                if (null != mPresenter) {
                    mPresenter.inviteShareT();
                }
                break;

            case R.id.relat_master:
            case R.id.relat_title:
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_HOME));
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, name);
                startActivity(intent);
                onEventMainPage("BOC商科证书banner", UmengUtil.EVENT_BOCSKZS_MAIN);
                break;
            case R.id.iv_jpst:

                break;
            case R.id.iv_kztd:
                name = "BOC考证通道";
                intent = new Intent(getActivity(), PassagewayActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.iv_close_pop:
                isClosePop = false;
                relatActivity.setVisibility(View.GONE);
                break;
            case R.id.relat_coursetitle:
                if (initIndexSiteInfoBeanList != null && initIndexSiteInfoBeanList.size() > 0) {
                    intent = new Intent(getActivity(), CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(2));
                    startActivity(intent);
                }
                onEventMainPage("好课推荐标题钮", UmengUtil.EVENT_JPHKBT_MAIN);
                break;
            case R.id.relat_playback:
                if (playbackAdapter != null && playbackAdapter.getData().size() > 0) {
                    intent = new Intent(getActivity(), CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(9));
                    startActivity(intent);
                }
                break;
            case R.id.relat_business_train:
                intent = new Intent(getActivity(), HotRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.relat_informationtitle:
                intent = new Intent(getActivity(), ArticleClassifyActivity.class);
                startActivity(intent);
                onEventMainPage("热门资讯标题钮", UmengUtil.EVENT_HOTZXBT_MAIN);
                break;

            case R.id.iv_close:
                isClose = false;
                relatContinue.setVisibility(View.GONE);
                onEventMainPage("上次观看-关闭", UmengUtil.EVENT_SCGKGB_MAIN);
                break;
            case R.id.ic_activity:
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, TextUtils.isEmpty(activityInfoBean.getActivityUrl()) ? "" : activityInfoBean.getActivityUrl());
                startActivity(intent);
                break;
            case R.id.tv_continue_watch:
            case R.id.iv_continue:
            case R.id.tv_comment:
                if (tvContinueWatch.getText().toString().equals("查看更多课程>")) {
                    intent = new Intent(getActivity(), CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRACTICE_ID));
                    startActivity(intent);
                    return;
                }
                if (userWatchHistoryBean.getUpState() == 1) {
                    intent = new Intent(getActivity(), CourseDetailActivity.class);
                    intent.putExtra(Constant.BUNDLE_COURSE_ID, userWatchHistoryBean.getCourseId());
                } else {
                    intent = new Intent(mContext, OffInvalidActivity.class);
                }
                startActivity(intent);
                onEventMainPage("上次观看", UmengUtil.EVENT_SCGK_MAIN);
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenter) {
            Objects.requireNonNull(mPresenter).getUserWatchHistory();
            Objects.requireNonNull(mPresenter).getActivityInfo();
            Objects.requireNonNull(mPresenter).getSwyProd(BuildConfig.HOST_URL_PRODINFO);
        }
    }

    public void reLoad() {
        if (mPresenter != null) {
            Objects.requireNonNull(mPresenter).ListBanner("1");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null != mPresenter) {
                        Objects.requireNonNull(mPresenter).ListBanner("11");
                    }
                }
            }, 150);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (null != mPresenter) {
                        Objects.requireNonNull(mPresenter).ListBanner("12");
                    }
                }
            }, 300);
            Objects.requireNonNull(mPresenter).getAnnouncementList();
            Objects.requireNonNull(mPresenter).getCourseRecommend();
            Objects.requireNonNull(mPresenter).getCourseCategory();
            Objects.requireNonNull(mPresenter).getInitIndexSiteInfo();
            Objects.requireNonNull(mPresenter).getPopularArticle();
            Objects.requireNonNull(mPresenter).getUserWatchHistory();
            Objects.requireNonNull(mPresenter).getActivityInfo();
            //1免费公开课 2vip精选 3直播 4主编精选
            Objects.requireNonNull(mPresenter).getCouRecommend(1);
            Objects.requireNonNull(mPresenter).getCouRecommend(2);
            Objects.requireNonNull(mPresenter).getCouRecommend(3);
            Objects.requireNonNull(mPresenter).getCouRecommend(4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mPresenter != null) {
                        Objects.requireNonNull(mPresenter).ListBanner("2");
                    }
                }
            }, 450);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mPresenter != null) {
                        Objects.requireNonNull(mPresenter).ListBanner("7");
                    }
                }
            }, 600);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        EventBus.getDefault().post(new MsgEvent());
        reLoad();
    }

    /**
     * @param type 2：精品试听  5/6/7：初中高级课程  8：创富通道  9：直播列表 10:数字经济
     */
    public static String getIdByType(int type) {
        if (initIndexSiteInfoBeanList != null) {
            for (int i = 0; i < initIndexSiteInfoBeanList.size(); i++) {
                InitIndexSiteInfoBean initIndexSiteInfoBean = initIndexSiteInfoBeanList.get(i);
                if (initIndexSiteInfoBean != null) {
                    int indexSiteType = initIndexSiteInfoBean.getType();
                    if (type == indexSiteType) {
                        return initIndexSiteInfoBean.getId();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void setData(@Nullable Object data) {

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
