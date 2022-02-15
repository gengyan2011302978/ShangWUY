package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.di.component.DaggerCourseCommentComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseCommentContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseCommentPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCommentAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 11:32
 * company: 普华集团
 * description: 课程评论
 */
public class CourseCommentFragment extends BaseLazyLoadFragment<CourseCommentPresenter> implements CourseCommentContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.srf_comment)
    SmartRefreshLayout mSrfComment;
    @BindView(R.id.rv_comment)
    RecyclerView mRvComment;
    @BindView(R.id.et_comment)
    EditText mEtComment;
    @BindView(R.id.tv_comment_send)
    TextView mTvCommentSend;

    /**
     * 底部布局
     */
    public View bottomView;

    public static final String COURSE_FREE_TYPE = "course_free_type";

    private int currentPage;
    public  final int PAGE_SIZE = 10;
    /**
     * 课程id
     */
    private String courseId;
    private CourseCommentAdapter commentAdapter;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private int freeType;
    /*
     * 空页面
     */
    private View emptyView;

    public static CourseCommentFragment newInstance(String courseId, int freeType) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        args.putInt(COURSE_FREE_TYPE, freeType);
        CourseCommentFragment fragment = new CourseCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCourseCommentComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_comment, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            freeType = bundle.getInt(COURSE_FREE_TYPE);
        }
        mSrfComment.setOnRefreshLoadMoreListener(this);

        initRv();

        bottomView = LayoutInflater.from(mContext).inflate(R.layout.bottom_layout, null);
        commentAdapter.setFooterView(bottomView);
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvComment.setLayoutManager(layoutManager);
        List<CourseCommentBean> commentBeanList = new ArrayList<>();
        commentAdapter = new CourseCommentAdapter(mContext, commentBeanList);
        mRvComment.setAdapter(commentAdapter);
        //处理RecycleView item刷新闪烁的问题
        ((SimpleItemAnimator) Objects.requireNonNull(mRvComment.getItemAnimator())).setSupportsChangeAnimations(false);

        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        commentAdapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

//        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @SingleClick
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                CourseCommentBean commentBean = (CourseCommentBean) adapter.getData().get(position);
//                if (commentBean != null && mPresenter != null) {
//                    mPresenter.commentZanState(commentBean, commentBean.getUserStatus() == 1 ? 2 : 1, position);
//                }
//            }
//        });
    }

    @Override
    public void lazyLoadData() {
        if (mPresenter != null) {
            mSrfComment.autoRefresh();
        }
    }

    /**
     * 刷新数据
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
            mPresenter.getCourseCommentList(courseId, currentPage, PAGE_SIZE, true);
        }
        if (bottomView != null) {
            bottomView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getCourseCommentList(courseId, currentPage, PAGE_SIZE, false);
        }
    }

    @SingleClick
    @OnClick(R.id.tv_comment_send)
    public void onClick(View view) {
        if (freeType == 2) {
            checkVipState();
        } else {
            sendComment();
        }
    }

    @VipStateCheck
    public void checkVipState() {
        sendComment();
    }

    /**
     * 发送评论
     */
    public void sendComment() {
        String commentContent = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(commentContent)) {
            TipsUtil.showTips("请输入评论内容");
        } else {
            if (mPresenter != null) {
                mPresenter.addCommentSuccess(courseId, commentContent);
            }
        }
    }

    @Override
    public void showCommentListRefresh(List<CourseCommentBean> commentBeans) {
        commentAdapter.setNewData(commentBeans);
    }

    @Override
    public void showCommentListLoadMore(List<CourseCommentBean> commentBeans) {
        commentAdapter.addData(commentBeans);
    }

    @Override
    public void addCommentSuccess() {
        TipsUtil.showTips("评论成功");
        mEtComment.setText("");
        SoftKeyboardUtil.hideSoftKeyboard((Activity) mContext);
        refreshData();
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
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void canLoadMore() {
        if (mSrfComment != null) {
            mSrfComment.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrfComment != null) {
            mSrfComment.setEnableLoadMore(false);
        }
        if (bottomView != null) {
            bottomView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrfComment != null) {
            mSrfComment.finishRefresh();
            mSrfComment.finishLoadMore();
        }
    }

    @Override
    public void showLoading() {
        //控制网慢 重复发送问题
        if (mTvCommentSend != null) {
            mTvCommentSend.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
        if (mTvCommentSend != null) {
            mTvCommentSend.setClickable(true);
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
