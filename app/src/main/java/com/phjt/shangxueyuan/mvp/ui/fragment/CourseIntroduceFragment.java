package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.CourseCommentSizeBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.bean.event.CommentEventBean;
import com.phjt.shangxueyuan.bean.event.CourseCommentStateBean;
import com.phjt.shangxueyuan.bean.event.UserInfoUpdateEvent;
import com.phjt.shangxueyuan.di.component.DaggerCourseDetailIntroduceComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseIntroduceContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseIntroducePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OpenVipActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCommentAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity.columnName;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_TYPE;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 课程介绍
 */
public class CourseIntroduceFragment extends BaseFragment<CourseIntroducePresenter> implements CourseIntroduceContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.srf_comment)
    SmartRefreshLayout mSrfComment;
    @BindView(R.id.sfr_rv)
    SmartRefreshLayout mSrfRv;
    @BindView(R.id.rv_comment)
    RecyclerView mRvComment;

    private int currentPage;
    public  final int PAGE_SIZE = 10;

    private CourseCommentAdapter commentAdapter;

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private int freeType;
    /**
     * 底部布局
     */
    public View bottomView;
    /*
     * 空页面
     */
    private View emptyView;
    /**
     * 评论总数 和 显示view
     */
    private int mCourseCommentNum;
    private TextView mTvCommentCount;
    /**
     * 复用笔记的
     */
    private NotesEditingFragment mNotesEditingFragment;

    private String courseType;
    private CourseDetailBean courseDetailBean;

    public static CourseIntroduceFragment newInstance(CourseDetailBean courseDetailBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constant.BUNDLE_COURSE_BEAN, courseDetailBean);
        CourseIntroduceFragment fragment = new CourseIntroduceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCourseDetailIntroduceComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_introduce, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RichText.initCacheDir(mContext);
        mSrfComment.setOnRefreshLoadMoreListener(this);
        mSrfRv.setOnRefreshLoadMoreListener(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseDetailBean = (CourseDetailBean) bundle.getSerializable(Constant.BUNDLE_COURSE_BEAN);
            showCourseDetail(courseDetailBean);
        }
    }

    public void showCourseDetail(CourseDetailBean courseDetailBean) {
        if (courseDetailBean != null) {
            courseId = courseDetailBean.getId();
            freeType = courseDetailBean.getFreeType();
            courseType = courseDetailBean.getCouType();
            initRv();
            initRvViews(courseDetailBean);
            refreshData();
        }
    }

    private void initRvViews(CourseDetailBean courseDetailBean) {
        TextView mTvIntroduceTitle = getView().findViewById(R.id.tv_course_introduce_title);
        TextView mTvIntroduceTeacher = getView().findViewById(R.id.tv_course_introduce_teacher);
        TextView mTvIntroduceContent = getView().findViewById(R.id.tv_course_introduce_content);
        TextView mTvWriteComment = getView().findViewById(R.id.tv_write_comment);
        mTvCommentCount = getView().findViewById(R.id.tv_course_comment_count);
        mTvIntroduceTitle.setText(courseDetailBean.getName());
        String strTeacher = "讲师：" + courseDetailBean.getLecName() + "&nbsp;<font color='#CACACA'><small>|</small></font>&nbsp;" +
                CountNumUtils.getStudyNum(courseDetailBean.getStudyNum()) + "人在学";
        if ("1".equals(courseType)){
            mTvIntroduceTeacher.setVisibility(View.GONE);
        }
        mTvIntroduceTeacher.setText(Html.fromHtml(strTeacher));
        String couDescribe = courseDetailBean.getCouDescribe();
        if (!TextUtils.isEmpty(couDescribe)) {
            RichText.from(couDescribe).into(mTvIntroduceContent);
        }
        mCourseCommentNum = courseDetailBean.getCourseCommentNum();
        setCommentCount(mCourseCommentNum);

        //写评论  先进行权限判断 （freeType == 1 || playPermission == 2可直接播放）
        int freeType = courseDetailBean.getFreeType();
        mTvWriteComment.setOnClickListener(v -> {
            if (freeType == 1 || CourseDetailActivity.playPermission == 2) {
                showEditDialog();
            } else if ("1".equals(courseType) && CourseDetailActivity.playPermission == 1) {
                DialogUtils.SubscribeDialog(getActivity(), new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        Intent intent1 = new Intent(getActivity(), OpenVipActivity.class);
                        intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                        intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                        intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                        intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, CourseDetailActivity.realPrice);
                        intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                        startActivity(intent1);
                    }
                });

            } else {
                checkVipState();
            }

            UmengUtil.umengCount(getActivity(), ConstantUmeng.COURSE_CLICK_COLLECTION_WRITE_COMMENTS);
        });

        bottomView = LayoutInflater.from(mContext).inflate(R.layout.bottom_layout, null);
        commentAdapter.setFooterView(bottomView);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        commentAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        commentAdapter.setHeaderFooterEmpty(true, false);
    }

    @VipStateCheck
    public void checkVipState() {
        showEditDialog();
    }

    /**
     * 弹框评论弹框
     */
    private void showEditDialog() {
        if (null == mNotesEditingFragment) {
            mNotesEditingFragment = new NotesEditingFragment();
        }
        if (!mNotesEditingFragment.isAdded()) {
            if (getActivity() instanceof CourseDetailActivity) {
                CourseDetailActivity detailActivity = (CourseDetailActivity) getActivity();
                detailActivity.stopPlay();
            }
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_COURSE_ID, courseId);
            bundle.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
            bundle.putBoolean(NotesEditingFragment.BUNDLE_IS_COMMENT, true);
            bundle.putInt(BUNDLE_TYPE, 0);
            mNotesEditingFragment.setArguments(bundle);
            mNotesEditingFragment.show(getChildFragmentManager(), "comment_dialog");
        }
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvComment.setLayoutManager(layoutManager);
        List<CourseCommentBean> commentBeanList = new ArrayList<>();
        commentAdapter = new CourseCommentAdapter(mContext, commentBeanList);
        mRvComment.setAdapter(commentAdapter);
        //处理RecycleView item刷新闪烁的问题
        ((SimpleItemAnimator) Objects.requireNonNull(mRvComment.getItemAnimator())).setSupportsChangeAnimations(false);

        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CourseCommentBean commentBean = (CourseCommentBean) adapter.getData().get(position);
                if (commentBean != null) {
                    int vipState = commentBean.getVipState();
                    String userId = commentBean.getUserId();
                    switch (view.getId()) {
                        case R.id.iv_like:
                        case R.id.tv_like:
                            if (mPresenter != null) {
                                mPresenter.commentZanState(commentBean, commentBean.getUserStatus() == 1 ? 2 : 1, position);
                            }
                            break;
                        case R.id.tv_share:
                            if (getActivity() instanceof CourseDetailActivity) {
                                CourseDetailActivity courseDetailActivity = (CourseDetailActivity) getActivity();
                                courseDetailActivity.getShareItemData(1, commentBean.getId(), commentBean.getContent());
                            }
                            break;
                        case R.id.tv_my_notes_reply:
                        case R.id.iv_notes_reply:
                            toCommentDetail(commentBean);
                            break;
                        case R.id.gray_view:
                            //置灰view，拦截点击事件
                            break;
                        case R.id.iv_comment_icon_item:
                        case R.id.tv_comment_phone_item:
                            VipUtil.iconAndVipClick(mContext, userId, vipState);
                            break;
                        case R.id.iv_vip_item:
                            VipUtil.iconAndVipClick(mContext, userId, vipState, true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        commentAdapter.setOnItemClickListener((adapter, view, position) -> {
            CourseCommentBean commentBean = (CourseCommentBean) adapter.getData().get(position);
            if (commentBean != null && commentBean.getAuditState() == 1) {
                //审核通过，进入详情页
                toCommentDetail(commentBean);
            }
        });
    }

    public void toCommentDetail(CourseCommentBean commentBean) {
        if (commentBean != null) {
            Intent intent = new Intent(mContext, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "评论详情");
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_DYNAMIC) +
                    "?id=" + commentBean.getId() + "&typeId=1&type=2");
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getChildFragmentManager().findFragmentByTag("comment_dialog");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 评论Title赋值
     *
     * @param commentCount 评论总数
     */
    public void setCommentCount(int commentCount) {
        if (mTvCommentCount != null) {
            if (commentCount == 0) {
                mTvCommentCount.setText("");
            } else if (commentCount > 999) {
                mTvCommentCount.setText("(999+)");
            } else {
                mTvCommentCount.setText(String.format("(%d)", commentCount));
            }
        }
    }

    /**
     * 刷新评论数据
     */
    public void refreshData() {
        if (mSrfComment != null) {
            onRefresh(mSrfComment);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            mPresenter.getCourseCommentList(courseId, courseType, currentPage, PAGE_SIZE, true);
        }
        if (bottomView != null) {
            bottomView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getCourseCommentList(courseId, courseType, currentPage, PAGE_SIZE, false);
        }
    }

    @Override
    public void showCommentListRefresh(List<CourseCommentBean> commentBeans) {
        if (commentAdapter != null) {
            commentAdapter.setNewData(commentBeans);
        }
        if (mPresenter != null) {
            mPresenter.getCourseCommentSize(courseId);
        }
    }

    @Override
    public void showCommentListLoadMore(List<CourseCommentBean> commentBeans) {
        if (commentAdapter != null) {
            commentAdapter.addData(commentBeans);
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void zanStateSuccess(CourseCommentBean commentBean, int position, int status) {
        Integer likeNum = commentBean.getLikeNum();
        if (status == 2) {
            //取消点赞
            commentBean.setUserStatus(0);
            commentBean.setLikeNum(Math.max(likeNum - 1, 0));
        } else {
            //新增点赞
            commentBean.setUserStatus(1);
            commentBean.setLikeNum(likeNum + 1);
        }
        if (commentAdapter != null) {
            commentAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void getCourseCommentSizeSuccess(CourseCommentSizeBean courseCommentSizeBean) {
        if (courseCommentSizeBean != null) {
            setCommentCount(courseCommentSizeBean.getCourse_comment_num());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CommentEventBean commentEventBean) {
        if (commentEventBean != null) {
            //新增评论，刷新评论列表
            refreshData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(CourseCommentStateBean commentStateBean) {
        if (commentStateBean != null) {
            List<CourseCommentBean> commentBeans = commentAdapter.getData();
            for (int i = 0; i < commentBeans.size(); i++) {
                CourseCommentBean courseCommentBean = commentBeans.get(i);
                if (courseCommentBean != null && TextUtils.equals(courseCommentBean.getId(), commentStateBean.getId())) {
                    courseCommentBean.setUserStatus(commentStateBean.isLikeState() ? 1 : 0);
                    courseCommentBean.setLikeNum(commentStateBean.getLikeNum());
                    courseCommentBean.setBackNum(commentStateBean.getReplyNum());

                    commentAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(UserInfoUpdateEvent userInfoUpdateEvent) {
        if (userInfoUpdateEvent != null) {
            //个人信息修改，刷新数据
            refreshData();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void canLoadMore() {
        if (mSrfRv != null) {
            mSrfRv.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrfRv != null) {
            mSrfRv.setEnableLoadMore(false);
        }
        if (bottomView != null) {
            bottomView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrfComment != null) {
            mSrfComment.finishRefresh();
        }
        if (mSrfRv != null) {
            mSrfRv.finishLoadMore();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }
}
