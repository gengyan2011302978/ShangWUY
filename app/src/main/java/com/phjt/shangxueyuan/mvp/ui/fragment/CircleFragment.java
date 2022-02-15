package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.bean.TopicMainBean;
import com.phjt.shangxueyuan.bean.event.UserInfoUpdateEvent;
import com.phjt.shangxueyuan.di.component.DaggerCircleComponent;
import com.phjt.shangxueyuan.mvp.contract.CircleContract;
import com.phjt.shangxueyuan.mvp.presenter.CirclePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.AllTopicActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.PublishDynamicActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ReleaseTopicActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.HompageTopicAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ThemeMainAdapter;
import com.phjt.shangxueyuan.utils.BannerUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.GlideImageLoader;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.fragment.MainFragment.getIdByType;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.nsl)
    NestedScrollView mNsl;
    @BindView(R.id.iv_line)
    View mIvLine;
    @BindView(R.id.customer_service_icon)
    ImageView customerServiceIcon;
    @BindView(R.id.message_icon)
    ImageView messageIcon;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    @BindView(R.id.relat_title)
    RelativeLayout relatTitle;
    @BindView(R.id.recycle_note_course)
    RecyclerView recycleNoteCourse;
    @BindView(R.id.recycle_circle_course)
    RecyclerView recycleCircleCourse;
    @BindView(R.id.srf_main)
    SmartRefreshLayout srfMain;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    public static String themeId = "0";
    public static int indexCircle = 0;

    private int pageNo = 1;
    private HompageTopicAdapter adapter;
    /**
     * 0.普通用户；1.普通vip；2.永久vip；3.vip已过期
     */
    private String vipState = "0";
    private List<ThemeMainBean.RecordsBean> list = new ArrayList<>();
    private ThemeMainAdapter themeMainAdapter;
    private View footerView;
    /**
     * 分享实体
     */
    private ShareBean mShareBean;
    private View mEmptyView;

    public static CircleFragment newInstance() {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCircleComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        themeMainAdapter = new ThemeMainAdapter(list);
        srfMain.setEnableLoadMore(false);
        srfMain.setOnRefreshLoadMoreListener(this);
        recycleNoteCourse.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycleCircleCourse.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleCircleCourse.setNestedScrollingEnabled(false);
        recycleCircleCourse.setVerticalScrollBarEnabled(true);
        recycleCircleCourse.setAdapter(themeMainAdapter);

        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        themeMainAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        footerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_invitation_list_foot, null);
        themeMainAdapter.addFooterView(footerView);
        footerView.setVisibility(View.GONE);
        if (mPresenter != null) {
            mPresenter.recommendTopics();
            mPresenter.addTopic(1);
            mPresenter.ListBanner("6");
            requestData(true);
        }
        themeMainAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ThemeMainBean.RecordsBean recordsBean = (ThemeMainBean.RecordsBean) adapter.getData().get(position);
                int vipState = recordsBean.getVipState();
                String userId = recordsBean.getUserId();

                Intent intent = null;
                // 来源 1.课程评论 2.笔记评论 3.专题留言 4.自发布动态 getThemeSource
                switch (view.getId()) {
                    case R.id.iv_like:
                    case R.id.tv_like:
                        if (mPresenter != null) {
                            String likeStatus = recordsBean.getThemeLikeState();
                            if ("1".equals(recordsBean.getThemeSource())) {
                                if ("1".equals(recordsBean.getCouLikeState())) {
                                    likeStatus = "2";
                                } else {
                                    likeStatus = "1";
                                }
                            }
                            if ("2".equals(recordsBean.getThemeSource())) {
                                likeStatus = recordsBean.getNotesLikeState();
                            }
                            if ("3".equals(recordsBean.getThemeSource())) {
                                likeStatus = recordsBean.getLeaveLikeState();
                            }
                            mPresenter.themeLike(recordsBean.getThemeSource(), recordsBean.getId() + "", position, likeStatus, recordsBean.getCommentId(), recordsBean.getNotesId(), recordsBean.getLeaveId(), recordsBean.getCouId());
                        }
                        break;
                    case R.id.tv_share:
                    case R.id.iv_share:
                        if (mPresenter != null) {
                            String otherType = recordsBean.getOtherType();
                            if ("1".equals(recordsBean.getThemeSource())) {
                                mPresenter.getShareItemData(1, recordsBean.getCommentId(), recordsBean.getContent(), 1, otherType);
                            }
                            if ("2".equals(recordsBean.getThemeSource())) {
                                mPresenter.getShareItemData(2, recordsBean.getNotesId(), recordsBean.getBackContent(), 1, otherType);
                            }
                            if ("3".equals(recordsBean.getThemeSource())) {
                                mPresenter.getShareItemData(3, recordsBean.getLeaveId(), recordsBean.getLeaveWordContent(), 1, otherType);
                            }
                            if ("4".equals(recordsBean.getThemeSource())) {
                                mPresenter.getShareItemData(4, recordsBean.getId() + "", recordsBean.getThemeName(), 1, otherType);
                            }

                        }
                        break;
                    case R.id.linear_note:
                        themeId = recordsBean.getId() + "";
                        indexCircle = position;
                        intent = new Intent(getActivity(), CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, recordsBean.getCouId());
                        intent.putExtra(Constant.BUNDLE_COURSE_TO_COMMENT, true);
                        startActivity(intent);
                        break;
                    case R.id.linear_topic:
                        themeId = recordsBean.getId() + "";
                        indexCircle = position;
                        intent = new Intent(getActivity(), TopicInfoActivity.class);
                        intent.putExtra("topicId", recordsBean.getTopicId() + "");
                        startActivity(intent);
                        break;
                    case R.id.linear_class:
                        themeId = recordsBean.getId() + "";
                        indexCircle = position;
                        intent = new Intent(getActivity(), CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, recordsBean.getCouCommentId() + "");
                        intent.putExtra(Constant.BUNDLE_COURSE_TO_NOTES, true);
                        startActivity(intent);
                        break;
                    case R.id.tv_specialTitle:
                        themeId = recordsBean.getId() + "";
                        indexCircle = position;
                        intent = new Intent(getActivity(), MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PROJECTDETAILS) + "?id=" + recordsBean.getSpecialId());
                        startActivity(intent);
                        break;
                    case R.id.relat_comment:
                    case R.id.iv_notes_reply:
                    case R.id.tv_my_notes_reply:
                        themeId = recordsBean.getId() + "";
                        indexCircle = position;
                        String webUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_DYNAMIC);
                        intent = new Intent(getActivity(), MyWebViewActivity.class);
                        if ("1".equals(recordsBean.getThemeSource())) {
                            intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getCommentId() + "&typeId=1" + "&type=1" + "&themeId=" + recordsBean.getId());
                        }
                        if ("2".equals(recordsBean.getThemeSource())) {
                            intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getNotesId() + "&typeId=2" + "&courseId=" + recordsBean.getCouId() + "&themeId=" + recordsBean.getId() + "&type=1");
                        }
                        if ("3".equals(recordsBean.getThemeSource())) {
                            intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getLeaveId() + "&typeId=3" + "&type=1" + "&themeId=" + recordsBean.getId());
                        }
                        if ("4".equals(recordsBean.getThemeSource())) {
                            intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getId() + "&typeId=4" + "&type=1");
                        }
                        startActivity(intent);
                        break;
                    case R.id.iv_mine_head_pic:
                    case R.id.tv_name:
                        VipUtil.iconAndVipClick(mContext, userId, vipState);
                        break;
                    case R.id.iv_vip:
                        VipUtil.iconAndVipClick(mContext, userId, vipState, true);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void requestData(boolean isRefresh) {
        if (mPresenter != null) {
            mPresenter.getRefreshList("", pageNo, 10, isRefresh, 2020);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.addTopic(1);
            mPresenter.recommendTopics();
            if (!"0".equals(themeId)) {
                mPresenter.getRefreshList(themeId, 1, 10, false, indexCircle);
            }
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
                    tvMainInfo.setText(String.valueOf(count));
                } else {
                    tvMainInfo.setText("99+");
                }
            } else {
                tvMainInfo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void recommendTopicsSuccess(List<TopicMainBean> list) {
        TopicMainBean topicMainBean = new TopicMainBean();
        topicMainBean.setCoverImg("");
        topicMainBean.setId(999);
        topicMainBean.setViewNum(-1);
        topicMainBean.setTopicName("创建话题");
        list.add(list.size(), topicMainBean);
        adapter = new HompageTopicAdapter(list);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == adapter.getData().size() - 1) {
                if ("0".equals(vipState)) {
                    DialogUtils.showVipBuyDialog(getActivity(), "没有创建话题的权限，请去开通会员", "您还不是会员", "去开通", new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            VipUtil.toVipPage(getContext());
                        }
                    });
                } else if ("3".equals(vipState)) {
                    DialogUtils.showVipBuyDialog(getActivity(), "暂不能创建话题，请去续费会员", "您的会员已过期", "去续费", new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            VipUtil.toVipPage(getContext());
                        }
                    });
                } else {
                    Intent intent = new Intent(getActivity(), ReleaseTopicActivity.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(getActivity(), TopicInfoActivity.class);
                intent.putExtra(Constant.BUNDLE_TOPIC_ID, list.get(position).getId() + "");
                startActivity(intent);
            }
        });
        recycleNoteCourse.setAdapter(adapter);
        adapter.setNewData(list);
    }

    @Override
    public void recommendTopicsFaile(String msg) {

    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        //封装分享内容
        mShareBean = new ShareBean();
        mShareBean.setTitle(shareItemBean.getTitle());
        mShareBean.setContent(shareItemBean.getContent());
        mShareBean.setImgUrl(shareItemBean.getPhoto());
        mShareBean.setUrl(shareItemBean.getUrl());

        ShareUtils.showSharePop(getActivity(), mShareBean);
    }

    @Override
    public void ListBannerSuccess(BaseBean<List<ListBannerBean>> bean, String type) {
        List<String> images = new ArrayList<>();
        if (bean != null && bean.data.size() > 0) {
            for (int i = 0; i < bean.data.size(); i++) {
                ListBannerBean bannerBean = bean.data.get(i);
                images.add(bannerBean.getCoverUrl());
            }
            banner.setVisibility(View.VISIBLE);
        } else {
            //设置默认图片，在GlideImageLoader显示
            images.add("");
            banner.setVisibility(View.GONE);
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader(), 1)
                .setImages(images, "")
                .setBannerAnimation(Transformer.ZoomOutSlide)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOffscreenPageLimit(0)
                .setOnBannerListener(position -> {
                    if (bean.data.size() == 0) {
                    } else {
                        BannerUtils.bannerClick(mContext, bean.data.get(position), getIdByType(9));
                        onEventMainPage("圈子顶部轮播图", UmengUtil.EVENT_CIRCLELBT_MAIN);
                    }
                })
                .start();
    }

    @Override
    public void canLoadMore() {
        if (srfMain != null) {
            srfMain.finishRefresh();
        }
        if (footerView != null) {
            footerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadRefreshDataSuccess(ThemeMainBean beans, int pageNo, boolean isRefresh, int positon, String tId) {
        if (!"0".equals(tId) && beans.getRecords().size() == 1) {
            if (themeMainAdapter.getData().size() > positon) {
                themeMainAdapter.getData().set(positon, beans.getRecords().get(0));
            }
            themeMainAdapter.notifyItemChanged(positon);
            themeId = "0";
            return;
        }
        if (pageNo < beans.getTotalPage()) {
            if (footerView != null) {
                footerView.setVisibility(View.GONE);
            }
        } else {
            if (footerView != null) {
                footerView.setVisibility(View.VISIBLE);
            }
        }
        if (beans.getRecords().size() > 0) {
            srfMain.setEnableLoadMore(pageNo < beans.getTotalPage());
            list = beans.getRecords();
            if (isRefresh) {
                themeMainAdapter.setNewData(new ArrayList<>());
                if (beans.getRecords().size() > 0) {
                    themeMainAdapter.setNewData(beans.getRecords());
                } else if (beans.getRecords().size() == 0) {
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srfMain.setEnableLoadMore(false);
                    if (themeMainAdapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srfMain.finishRefresh();
                //刷新完，回到顶部
                if (mNsl != null) {
                    mNsl.fullScroll(NestedScrollView.FOCUS_UP);
                }
            } else {
                if (beans.getRecords().size() > 0) {
                    themeMainAdapter.addData(beans.getRecords());
                } else {
                    srfMain.setEnableLoadMore(false);
                }
                srfMain.finishLoadMore();
            }
        }
    }

    @Override
    public void loadRefreshDataFailure() {
        srfMain.finishLoadMore();
        srfMain.finishRefresh();
    }

    @Override
    public void themeLikeSuccess(int position, String likeStatus) {
        int thumbsUpCount = themeMainAdapter.getData().get(position).getThemeLikeNum();
        String themeSource = themeMainAdapter.getData().get(position).getThemeSource();
        if ("1".equals(themeSource)) {
            thumbsUpCount = Integer.parseInt(themeMainAdapter.getData().get(position).getCouLikeNum());
            themeMainAdapter.getData().get(position).setCouLikeNum(String.valueOf("1".equals(likeStatus) ? thumbsUpCount + 1 : thumbsUpCount - 1));
            themeMainAdapter.getData().get(position).setCouLikeState("2".equals(likeStatus) ? "0" : "1");
        }
        if ("2".equals(themeSource)) {
            thumbsUpCount = Integer.parseInt(themeMainAdapter.getData().get(position).getNotesLikeNum());
            themeMainAdapter.getData().get(position).setNotesLikeNum(String.valueOf("0".equals(likeStatus) ? thumbsUpCount + 1 : thumbsUpCount - 1));
            themeMainAdapter.getData().get(position).setNotesLikeState("1".equals(likeStatus) ? "0" : "1");
        }
        if ("3".equals(themeSource)) {
            thumbsUpCount = Integer.parseInt(themeMainAdapter.getData().get(position).getLeaveLikeNum());
            themeMainAdapter.getData().get(position).setLeaveLikeNum(String.valueOf("0".equals(likeStatus) ? thumbsUpCount + 1 : thumbsUpCount - 1));
            themeMainAdapter.getData().get(position).setLeaveLikeState("1".equals(likeStatus) ? "0" : "1");
        }
        if ("4".equals(themeSource)) {
            themeMainAdapter.getData().get(position).setThemeLikeNum("0".equals(likeStatus) ? thumbsUpCount + 1 : thumbsUpCount - 1);
            themeMainAdapter.getData().get(position).setThemeLikeState("1".equals(likeStatus) ? "0" : "1");
        }
        themeMainAdapter.notifyItemChanged(position, R.id.tv_like);
    }

    @Override
    public void themeLikeFaile(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void addTopicSuccess(String vipstatus) {
        vipState = vipstatus;
    }

    @Override
    public void addTopicFaile(String msg) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        requestData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        requestData(true);
        if (mPresenter != null) {
            mPresenter.recommendTopics();
            mPresenter.ListBanner("6");
        }
    }

    @OnClick({R.id.customer_service_icon, R.id.message_icon, R.id.iv_release, R.id.relat_title})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.customer_service_icon:
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICKCUSTOM_SERVICE);
                break;
            case R.id.message_icon:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.iv_release:
                intent = new Intent(getActivity(), PublishDynamicActivity.class);
                startActivity(intent);
                break;
            case R.id.relat_title:
                intent = new Intent(getActivity(), AllTopicActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(UserInfoUpdateEvent userInfoUpdateEvent) {
        if (userInfoUpdateEvent != null) {
            //个人信息修改，刷新数据
            pageNo = 1;
            requestData(true);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.show(message);
    }
}
