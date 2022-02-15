package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerMyNotesComponent;
import com.phjt.shangxueyuan.mvp.contract.MyNotesContract;
import com.phjt.shangxueyuan.mvp.presenter.MyNotesPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyNotesAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.NotesReviewDetailFragment;

import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.sharestatistic.ShareInit;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_NOTES_EDITING;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_TYPE;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.CURRENT_TIME;

/**
 * @author: Roy
 * date:    06/01/2020 11:43
 * company: 普华集团
 * dedication: 我的笔记
 */
public class MyNotesActivity extends BaseActivity<MyNotesPresenter> implements MyNotesContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_my_notes)
    RecyclerView rvMyNotes;
    @BindView(R.id.srl_my_notes)
    SmartRefreshLayout srlMyNotes;

    private int pageNo = 1;
    public final int PAGE_SIZE = 10;

    private MyNotesAdapter adapter;
    private NotesReviewDetailFragment mNotesReviewFragment;
    private NotesEditingFragment mNotesEditingFragment;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyNotesComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_notes;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("我的笔记");
        srlMyNotes.setOnRefreshLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMyNotes.setLayoutManager(layoutManager);
        List<MyNotesBean> myNotesBeans = new ArrayList<>();
        adapter = new MyNotesAdapter(this, myNotesBeans, 2);
        rvMyNotes.setAdapter(adapter);
        adapter.setNewData(myNotesBeans);

        View footerView = LayoutInflater.from(this).inflate(R.layout.item_invitation_list_foot_underscore, null);
        adapter.addFooterView(footerView);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyNotesBean myNotesBean = (MyNotesBean) adapter.getData().get(position);
                if (1 == myNotesBean.getAuditState()) {
                    if (null == mNotesReviewFragment) {
                        mNotesReviewFragment = new NotesReviewDetailFragment();
                    }
                    if (!mNotesReviewFragment.isAdded()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(NotesReviewDetailFragment.BUNDLE_ITEM_POSITION, position);
                        bundle.putSerializable(NotesReviewDetailFragment.BUNDLE_NOTES, myNotesBean);
                        mNotesReviewFragment.setArguments(bundle);
                        mNotesReviewFragment.show(getSupportFragmentManager(), "dialog");
                    }
                } else {
                    TipsUtil.show("笔记审核中");
                }
            }
        });

        setItemChildClick();
    }

    private void setItemChildClick() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyNotesBean myNotesBean = (MyNotesBean) adapter.getData().get(position);
            if (myNotesBean.getUpState() == 0) {
                TipsUtil.showTips(" 该课程已下架");
                return;
            }
            switch (view.getId()) {
                case R.id.tv_my_notes_address:
                    if (1 == myNotesBean.getAuditState()) {
                        long pauseTime = myNotesBean.getCoursePauseTime();
                        Intent intent = new Intent(MyNotesActivity.this, CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(myNotesBean.getCourseId()));
                        intent.putExtra(Constant.BUNDLE_POINT_ID, String.valueOf(myNotesBean.getPointId()));
                        intent.putExtra(Constant.BUNDLE_COURSE_WATCH_TIME, pauseTime);
                        startActivity(intent);
                    } else {
                        TipsUtil.show("笔记审核中");
                    }
                    break;

                case R.id.iv_like:
                case R.id.tv_like:
                    getZanData(myNotesBean, position);
                    break;
                case R.id.tv_editor:
                    int courseId = myNotesBean.getCourseId();
                    int mId = myNotesBean.getId();
                    long currentTime = myNotesBean.getCoursePauseTime();
                    toEditPage(mId, courseId, currentTime, myNotesBean);
                    break;
                case R.id.tv_share:
                    //分享
                    getShareData(myNotesBean);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 点赞统一调用
     */
    public void getZanData(MyNotesBean myNotesBean, int position) {
        if (1 == myNotesBean.getAuditState()) {
            if (mPresenter != null) {
                mPresenter.thumbsUp(myNotesBean.getId(), myNotesBean.getCourseId(), position, myNotesBean.getLikeState());
            }
        } else {
            TipsUtil.show("笔记审核中");
        }
    }

    /**
     * 分享统一调用
     */
    public void getShareData(MyNotesBean notesBean) {
        if (mPresenter != null) {
            mPresenter.getShareItemData(2, String.valueOf(notesBean.getId()), notesBean.getNoteContent(), notesBean.getCouType());

            UmengUtil.umengCount(this, ConstantUmeng.COURSE_NOTE_SHARE);
        }
    }

    /**
     * 编辑笔记
     */
    public void toEditPage(int pointId, int courseId, long currentTime, MyNotesBean myNotesBean) {
        if (null == mNotesEditingFragment) {
            mNotesEditingFragment = new NotesEditingFragment();
        }
        if (!mNotesEditingFragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_POINT_ID, "" + pointId);
            bundle.putString(Constant.BUNDLE_COURSE_ID, "" + courseId);
            bundle.putLong(CURRENT_TIME, currentTime);
            bundle.putInt(BUNDLE_TYPE, 1);
            bundle.putSerializable(BUNDLE_NOTES_EDITING, myNotesBean);
            mNotesEditingFragment.setArguments(bundle);
            mNotesEditingFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            mPresenter.getNotesList(pageNo, PAGE_SIZE, isReFresh);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, data);
        if (null != mNotesEditingFragment) {
            mNotesEditingFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void thumbsUpSuccess(int position, int likeStatus) {
        int thumbsUpCount = adapter.getData().get(position).getNotesLikeNum();
        adapter.getData().get(position).setNotesLikeNum(likeStatus == 0 ? thumbsUpCount + 1 : thumbsUpCount - 1);
        adapter.getData().get(position).setLikeState(likeStatus == 1 ? 0 : 1);
        adapter.notifyItemChanged(position, R.id.tv_like);

        if (mNotesReviewFragment != null) {
            mNotesReviewFragment.setZanCountAndState(likeStatus == 0 ? thumbsUpCount + 1
                    : thumbsUpCount - 1, likeStatus == 1 ? 0 : 1);
        }
    }

    @Override
    public void loadDataSuccess(BaseListBean<MyNotesBean> bean, boolean isRefresh) {
        srlMyNotes.setEnableLoadMore(pageNo < bean.getTotalPage());
        if (isRefresh) {
            if (bean.getRecords().size() > 0) {
                adapter.setNewData(bean.getRecords());
            } else {
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srlMyNotes.finishRefresh();
        } else {
            if (bean.getRecords().size() > 0) {
                adapter.addData(bean.getRecords());
            } else {
                srlMyNotes.setEnableLoadMore(false);
            }
            srlMyNotes.finishLoadMore();
        }
    }

    @Override
    public void loadDataFailure(boolean isRefresh) {
        if (srlMyNotes != null) {
            srlMyNotes.finishRefresh();
            srlMyNotes.finishLoadMore();
        }
        if (adapter != null && mEmptyView != null && isRefresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        ShareBean shareBean = new ShareBean();
        shareBean.setTitle(shareItemBean.getTitle());
        shareBean.setContent(shareItemBean.getContent());
        shareBean.setUrl(shareItemBean.getUrl());
        shareBean.setImgUrl(shareItemBean.getPhoto());

        ShareUtils.showSharePop(this, shareBean);
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.NOTES_REVIEW_SUCCESSFUL) {
                pageNo = 1;
                loadData(true);
            } else if (type == EventBean.NOTES_FOUND) {
                //笔记发布成功，刷新数据
                if (srlMyNotes != null) {
                    srlMyNotes.autoRefresh();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null != mNotesEditingFragment) {
            if (keyCode == KeyEvent.KEYCODE_BACK && mNotesEditingFragment.isAdded()) {
                //让程序进入后台但不销毁 这里写自己的逻辑代码就可以了
                moveTaskToBack(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (srlMyNotes != null) {
            srlMyNotes.autoRefresh();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNotesReviewFragment != null) {
            mNotesReviewFragment = null;
        }
        if (mNotesEditingFragment != null) {
            mNotesEditingFragment = null;
        }
    }
}
