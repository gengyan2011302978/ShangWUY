package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;

import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.bean.event.UserInfoUpdateEvent;
import com.phjt.shangxueyuan.di.component.DaggerCurriculumNoteComponent;
import com.phjt.shangxueyuan.mvp.contract.CurriculumNoteContract;
import com.phjt.shangxueyuan.mvp.presenter.CurriculumNotePresenter;

import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyNotesAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phsxy.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :Roy
 * description :我的笔记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

public class CurriculumNoteFragment extends BaseLazyLoadFragment<CurriculumNotePresenter> implements CurriculumNoteContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.srl_my_notes)
    SmartRefreshLayout srlMyNotes;
    @BindView(R.id.rv_my_notes)
    RecyclerView rvMyNotes;

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 小节id
     */
    private String pointId;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private int freeType;
    public static final String COURSE_FREE_TYPE = "course_free_type";
    /**
     * 当前课程状态 0.我的笔记；1.所有笔记
     */
    private int mType;
    public static final String COURSE_TYPE = "course_type";
    public static final String COURSE_VIP_STATE = "course_vip_state";

    /**
     * 0.普通用户；1.普通vip；2.永久vip；3.vip已过
     */
    private int vipState;

    private int pageNo = 1;
    public final int PAGE_SIZE = 10;

    private MyNotesAdapter adapter;
    private NotesReviewDetailFragment mNotesReviewFragment;
    private FragmentManager fragmentManager;
    /**
     * 空布局
     */
    private View mEmptyView;
    private String courseType;

    public static CurriculumNoteFragment newInstance(String courseId, String pointId, int freeType, int vipState, int type,String courseType) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        args.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
        args.putString(Constant.BUNDLE_POINT_ID, pointId);
        args.putInt(COURSE_FREE_TYPE, freeType);
        args.putInt(COURSE_VIP_STATE, vipState);
        args.putInt(COURSE_TYPE, type);
        CurriculumNoteFragment fragment = new CurriculumNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCurriculumNoteComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_note, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            courseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            pointId = bundle.getString(Constant.BUNDLE_POINT_ID);
            freeType = bundle.getInt(COURSE_FREE_TYPE);
            mType = bundle.getInt(COURSE_TYPE);
            vipState = bundle.getInt(COURSE_VIP_STATE);
        }
        srlMyNotes.setOnRefreshLoadMoreListener(this);
    }

    public void getPage(int page, String pointIds) {
        mType = page;
        pointId = pointIds;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (null!=rvMyNotes){
                setDatas();
            }
        }
    }

    @Override
    public void lazyLoadData() {
        setDatas();
    }

    private void setDatas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyNotes.setLayoutManager(layoutManager);
        List<MyNotesBean> myNotesBeans = new ArrayList<>();
        adapter = new MyNotesAdapter(getActivity(), myNotesBeans, mType);
        rvMyNotes.setAdapter(adapter);

        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        requestData(true);
        fragmentManager = getChildFragmentManager();
        adapter.setOnItemClickListener((adapter, view, position) -> {
            MyNotesBean notesBean = (MyNotesBean) adapter.getData().get(position);
            if (1 == notesBean.getAuditState()) {
                if (null == mNotesReviewFragment) {
                    mNotesReviewFragment = new NotesReviewDetailFragment();
                }
                if (!mNotesReviewFragment.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(NotesReviewDetailFragment.COURSE_FREE_TYPE, freeType);
                    bundle.putInt(NotesReviewDetailFragment.COURSE_VIP_STATE, vipState);
                    bundle.putInt(NotesReviewDetailFragment.BUNDLE_ITEM_POSITION, position);
                    bundle.putSerializable(NotesReviewDetailFragment.BUNDLE_NOTES, notesBean);
                    bundle.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
                    mNotesReviewFragment.setArguments(bundle);
                    mNotesReviewFragment.show(fragmentManager, "dialog");
                    FragmentActivity activity = getActivity();
                    if (activity instanceof CourseDetailActivity) {
                        CourseDetailActivity mActivitys = (CourseDetailActivity) activity;
                        mActivitys.stopPlay();
                    }
                }
            } else {
                ToastUtils.show("笔记审核中");
            }
        });
        setItemChildClick();
    }

    private void setItemChildClick() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyNotesBean notesBean = (MyNotesBean) adapter.getData().get(position);
            FragmentActivity activity = getActivity();
            if (notesBean.getUpState() == 0) {
                TipsUtil.showTips(" 该课程已下架");
                return;
            }
            int vipState = notesBean.getVipState();
            String userId = notesBean.getUserId();
            switch (view.getId()) {
                case R.id.tv_my_notes_address:
                    if (activity instanceof CourseDetailActivity) {
                        CourseDetailActivity activitys = (CourseDetailActivity) activity;
                        activitys.clickNotePlay(String.valueOf(notesBean.getPointId()), notesBean.getCoursePauseTime());
                    }
                    break;


                case R.id.iv_like:
                case R.id.tv_like:
                    getZanData(notesBean, position);
                    break;
                case R.id.tv_editor:
                    int courseId = notesBean.getCourseId();
                    int mId = notesBean.getId();
                    long currentTime = notesBean.getCoursePauseTime();
                    Fragment fragment = getParentFragment();
                    if (fragment instanceof NoteFragment) {
                        NoteFragment noteFragment = (NoteFragment) fragment;
                        noteFragment.toEditPage(mId, courseId, currentTime, notesBean);
                    }
                    break;
                case R.id.tv_share:
                    //分享
                    getShareData(notesBean);
                    break;
                case R.id.iv_re_head_pic:
                case R.id.tv_designation:
                    VipUtil.iconAndVipClick(mContext, userId, vipState);
                    break;
                case R.id.iv_vip_item:
                    VipUtil.iconAndVipClick(mContext, userId, vipState, true);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * 点赞统一调用
     */
    public void getZanData(MyNotesBean notesBean, int position) {
        if (1 == notesBean.getAuditState()) {
            if (mPresenter != null) {
                mPresenter.thumbsUp(notesBean.getId(), notesBean.getCourseId(), position, notesBean.getLikeState());
            }
        } else {
            ToastUtils.show("笔记审核中");
        }
    }

    /**
     * 分享统一调用
     */
    public void getShareData(MyNotesBean notesBean) {
        if (getActivity() instanceof CourseDetailActivity && notesBean != null) {
            CourseDetailActivity courseDetailActivity = (CourseDetailActivity) getActivity();
            courseDetailActivity.getShareItemData(2, String.valueOf(notesBean.getId()), notesBean.getNoteContent());
        }
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
    }

    /**
     * 刷新
     */
    public void requestData(boolean isRefresh) {
        if (mPresenter != null) {
            mPresenter.getRefreshList(courseId, pointId, pageNo, PAGE_SIZE, mType, isRefresh,courseType);
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
    public void loadRefreshDataSuccess(BaseListBean<MyNotesBean> bean, int pageNo, boolean isRefresh) {
        if (null != adapter && null != rvMyNotes && null != bean) {
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
    }

    @Override
    public void loadRefreshDataFailure() {
        srlMyNotes.finishRefresh();
        srlMyNotes.finishLoadMore();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mNotesReviewFragment != null) {
            mNotesReviewFragment = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.NOTES_FOUND) {
                pageNo = 1;
                requestData(true);
            } else if (type == EventBean.NOTES_REVIEW_SUCCESSFUL) {
                pageNo = 1;
                requestData(true);
            }
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
