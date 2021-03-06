package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerNotesReviewDetailComponent;
import com.phjt.shangxueyuan.mvp.contract.NotesReviewDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.NotesReviewDetailPresenter;

import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyNotesActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OpenVipActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.RecyclerCommonAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ReleasedAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.roundImg.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity.columnName;


/**
 * Created by Template
 *
 * @author :????????????
 * description :
 * company     : Copyright (c) 2019 ???????????? All rights reserved
 * @date : 2020/06/18 13:44
 */
public class NotesReviewDetailFragment extends BaseNoteFragment<NotesReviewDetailPresenter> implements NotesReviewDetailContract.View,
        View.OnClickListener, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_ta_title)
    TextView tvTaTitle;
    @BindView(R.id.re_back_img)
    ImageView reBackImg;
    @BindView(R.id.fragment_share_recyclerView)
    RecyclerView fragmentShareRecyclerView;
    @BindView(R.id.srl_notes)
    SmartRefreshLayout srlNotes;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.rl_reply)
    RelativeLayout rlReply;
    Unbinder unbinder;

    public static final String BUNDLE_NOTES = "bundle_mynotesbea";
    public static final String COURSE_VIP_STATE = "course_vip_state";
    public static final String COURSE_FREE_TYPE = "course_free_type";

    public static final String BUNDLE_ITEM_POSITION = "bundle_item_position";
    private int mPosition;

    private Context mContext;
    private Drawable drawable;
    private RecyclerCommonAdapter adapter;
    private int backNum;
    private int pageNo = 1;
    public  final int PAGE_SIZE = 10;
    private int notesId;
    private int courseId = 0;
    private String postContent = "";
    private Dialog dialog;

    /**
     * 0.???????????????1.??????vip???2.??????vip???3.vip??????
     */
    private int vipState;

    /**
     * ?????????????????? 1.?????????2.??????
     */
    private int freeType;
    /**
     * ????????????
     */
    private TextView tvAlReplies;
    private TextView tvReplyCount;

    private int openState;
    //????????????
    private TextView mTvLike;
    private ImageView mIvLike;
    private View mEmptyView;
    private String courseType = "1";


    public static NotesReviewDetailFragment getInstance() {
        Bundle args = new Bundle();
        NotesReviewDetailFragment fragment = new NotesReviewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNotesReviewDetailComponent //Dagger ?????????????????????,??????????????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public Dialog onCreateDialog(@androidx.annotation.Nullable Bundle savedInstanceState) {
        //??????BottomSheetDialog?????????
        return new BottomSheetDialog(this.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        //??????dialog??????
        BottomSheetDialog mBottomSheetDialog = (BottomSheetDialog) getDialog();
        //???windowsd??????????????????????????????????????????????????????
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //??????diglog????????????
        FrameLayout bottomSheet = mBottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //??????????????????LayoutParams??????
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //?????????????????????????????????????????????????????????????????????
            bottomSheet.setLayoutParams(layoutParams);

            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight????????????????????????
            behavior.setPeekHeight(getPeekHeight());
            // ?????????????????????
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mBottomSheetDialog.setCanceledOnTouchOutside(false);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.layoyt_bottomsheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        setDatas();
        return view;
    }

    private void setDatas() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vipState = bundle.getInt(COURSE_VIP_STATE, 0);
            freeType = bundle.getInt(COURSE_FREE_TYPE, 0);
            mPosition = bundle.getInt(BUNDLE_ITEM_POSITION, 0);
            courseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            MyNotesBean myNotesBea = (MyNotesBean) bundle.getSerializable(BUNDLE_NOTES);
            pageNo = 1;
            openState = myNotesBea.getOpenState();
            loadData(true);
            if (1 != myNotesBea.getOpenState()) {
                srlNotes.setEnableLoadMore(false);
                rlReply.setVisibility(View.GONE);
            }
            if (0 == myNotesBea.getUpState()) {
                rlReply.setVisibility(View.GONE);
            }
        }
    }

    private void initViews() {
        fragmentShareRecyclerView.setNestedScrollingEnabled(false);
        fragmentShareRecyclerView.setVerticalScrollBarEnabled(true);
        fragmentShareRecyclerView.setHasFixedSize(true);
        srlNotes.setEnableRefresh(false);
        initAdapter();
        drawable = getResources().getDrawable(R.drawable.ic_reply_news);
        rlReply.setOnClickListener(this);
        reBackImg.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //????????????????????????
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        FragmentActivity activity = getActivity();
        if (activity instanceof CourseDetailActivity) {
            CourseDetailActivity activitys = (CourseDetailActivity) activity;
            activitys.resumePlay();
        }
    }


    /**
     * ??????Adapter
     */
    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragmentShareRecyclerView.setLayoutManager(layoutManager);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.fragment_share_recyclerview_header, null);

        TextView tvMineName = headView.findViewById(R.id.tv_mine_name);
        TextView tvContent = headView.findViewById(R.id.tv_notes_content);
        TextView tvAddress = headView.findViewById(R.id.tv_notes_address);
        TextView tvAll = headView.findViewById(R.id.tv_all_replies_es);
        TextView tvCon = headView.findViewById(R.id.tv_notes_con);
        TextView tvTime = headView.findViewById(R.id.tv_time);
        RecyclerView rcyReleased = headView.findViewById(R.id.rcy_released);
        tvAlReplies = headView.findViewById(R.id.tv_all_replies);
        RoundedImageView ivReHeadPic = headView.findViewById(R.id.iv_re_head_pic);

        mIvLike = headView.findViewById(R.id.iv_like);
        mTvLike = headView.findViewById(R.id.tv_like);
        tvReplyCount = headView.findViewById(R.id.tv_my_notes_reply);
        TextView tvShare = headView.findViewById(R.id.tv_share);
        ImageView ivReply = headView.findViewById(R.id.iv_notes_reply);

        if (getArguments() != null && null != (MyNotesBean) getArguments().getSerializable(BUNDLE_NOTES)) {
            MyNotesBean myNotesBea = (MyNotesBean) getArguments().getSerializable(BUNDLE_NOTES);
            tvMineName.setText(myNotesBea.getNickName());
            tvAddress.setText(TextUtils.isEmpty(myNotesBea.getPointName()) ? " " : "???  " + myNotesBea.getPointName());
            tvContent.setText(myNotesBea.getNoteContent());
            backNum = myNotesBea.getBackNum();
            notesId = myNotesBea.getId();
            courseId = myNotesBea.getCourseId();
            int failImage = R.drawable.iv_mine_avatar;
            GlideUtils.load(myNotesBea.getPhoto(), ivReHeadPic, failImage);
            tvTime.setText(myNotesBea.getCreatTime());
            setReplyCount(backNum);

            //?????????????????????????????????
            setZanCountAndState(myNotesBea.getNotesLikeNum(), myNotesBea.getLikeState());
            mIvLike.setOnClickListener(v -> {
                if (getParentFragment() instanceof CurriculumNoteFragment) {
                    CurriculumNoteFragment curriculumNoteFragment = (CurriculumNoteFragment) getParentFragment();
                    curriculumNoteFragment.getZanData(myNotesBea, mPosition);
                } else if (getActivity() instanceof MyNotesActivity) {
                    MyNotesActivity notesActivity = (MyNotesActivity) getActivity();
                    notesActivity.getZanData(myNotesBea, mPosition);
                }
            });
//            setReplyOnClick(tvReply);
//            setReplyOnClick(ivReply);
            tvShare.setOnClickListener(v -> {
                if (getParentFragment() instanceof CurriculumNoteFragment) {
                    CurriculumNoteFragment curriculumNoteFragment = (CurriculumNoteFragment) getParentFragment();
                    curriculumNoteFragment.getShareData(myNotesBea);
                } else if (getActivity() instanceof MyNotesActivity) {
                    MyNotesActivity notesActivity = (MyNotesActivity) getActivity();
                    notesActivity.getShareData(myNotesBea);
                }
            });

            if (!TextUtils.isEmpty(myNotesBea.getNotesImg())) {
                setReleased(rcyReleased, myNotesBea.getNotesImg());
            }
            if (1 != myNotesBea.getOpenState()) {
                tvAlReplies.setVisibility(View.GONE);
                tvAll.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
                ivReHeadPic.setVisibility(View.GONE);
                tvMineName.setVisibility(View.GONE);
                tvCon.setVisibility(View.VISIBLE);
                tvCon.setText(myNotesBea.getNoteContent());
            }
        }

        adapter = new RecyclerCommonAdapter(getActivity());
        fragmentShareRecyclerView.setAdapter(adapter);
        adapter.setHeaderView(headView);
        adapter.setHeaderFooterEmpty(true, false);
        srlNotes.setOnRefreshLoadMoreListener(this);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * ??????????????????
     */
    public void setReplyCount(int backNum) {
        if (tvAlReplies != null) {
            tvAlReplies.setText(backNum > 999 ? "(999+)" : "(" + backNum + ")");
        }
        if (tvReplyCount != null) {
            tvReplyCount.setText(backNum > 999 ? "999+" : String.valueOf(backNum));
        }
    }


    /**
     * ??????????????? ??? ??????
     */
    public void setZanCountAndState(int zanNum, int zanState) {
        if (mTvLike != null) {
            mTvLike.setText(CountNumUtils.getCountNum(zanNum));
        }
        if (mIvLike != null) {
            mIvLike.setSelected(zanState == 1);
        }
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_back_img:
                dismiss();
                break;
            case R.id.rl_reply:
                if ("1".equals(courseType) && CourseDetailActivity.playPermission == 1) {
                    DialogUtils.SubscribeDialog(getActivity(), new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            Intent intent1 = new Intent(getActivity(), OpenVipActivity.class);
                            intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, CourseDetailActivity.courseId);
                            intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                            intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                            intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, CourseDetailActivity.realPrice);
                            intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                            startActivity(intent1);
                        }
                    });

                } else if (freeType == 2 || CourseDetailActivity.playPermission == 2) {
                    if (vipState == 1 || vipState == 2 || CourseDetailActivity.playPermission == 2) {
                        initNotesDialog();
                    } else {
                        checkCourseVipState();
                    }
                } else {
                    initNotesDialog();
                }
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     */
    private void setReleased(RecyclerView rcyReleased, String notesImg) {
        List<String> mDatasImgList = new ArrayList<>();
        List<String> imgList = Arrays.asList(notesImg.split(","));
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        rcyReleased.setLayoutManager(layoutManager);
        ReleasedAdapter mAdapter = new ReleasedAdapter(mContext);
        rcyReleased.setAdapter(mAdapter);
        mAdapter.setNewData(imgList);
        mDatasImgList.addAll(imgList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) mDatasImgList);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            }

        });

    }

    @VipStateCheck
    public void checkCourseVipState() {
        initNotesDialog();
    }

    /**
     * ????????????
     */
    private void initNotesDialog() {
        dialog = DialogUtils.showCommentDialog(getActivity(), postContent, new DialogUtils.OnClickDialogListener() {
            @Override
            public void sendComment(String content) {
                postContent = content;
                if (mPresenter != null) {
                    mPresenter.replyNotess(notesId, content, courseId);
                }
            }
        });
    }

    @Override
    public void loadDetailsSuccess(BaseListBean<NotesDetailsBean> bean, boolean isRefresh) {
        if (bean != null) {
            srlNotes.setEnableLoadMore(pageNo < bean.getTotalPage());
            if (isRefresh) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setNewData(bean.getRecords());
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srlNotes.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srlNotes.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                } else {
                    srlNotes.setEnableLoadMore(false);
                }
                srlNotes.finishLoadMore();
            }
        }
    }

    @Override
    public void loadDetailsFailure(boolean refurbish) {
        srlNotes.finishRefresh();
        srlNotes.finishLoadMore();
        if (adapter != null && mEmptyView != null && refurbish) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void replyNotesSuccess() {
        rlReply.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_DADADA));
        tvReply.setVisibility(View.VISIBLE);
        postContent = "";
        tvReply.setText("?????????????????????");
        tvReply.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        pageNo = 1;
        loadData(true);
        backNum += 1;
        setReplyCount(backNum);
        EventBusManager.getInstance().post(new EventBean(EventBean.NOTES_REVIEW_SUCCESSFUL, null));
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean b) {
        if (mPresenter != null && 1 == openState) {
            mPresenter.getnotesDetails(notesId, pageNo, PAGE_SIZE, b);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.NOTES_REVIEW) {
                postContent = eventBean.getMsg();
                if (!TextUtils.isEmpty(postContent)) {
                    tvReply.setText(postContent);
                } else {
                    tvReply.setText("?????????????????????");
                }
            }
        }
    }

    /**
     * ?????????????????????
     *
     * @param view
     */
    private void setReplyOnClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freeType == 2) {
                    if (vipState == 1 || vipState == 2) {
                        initNotesDialog();
                    } else {
                        checkCourseVipState();
                    }
                } else {
                    initNotesDialog();
                }
            }
        });
    }


    /**
     * ???????????????????????????????????????????????????
     * ??????????????????????????????peekHeight
     *
     * @return height
     */
    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //????????????????????????????????????3/4
        return (int) (peekHeight - peekHeight / 9.5);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        postContent = "";
        pageNo = 1;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

}
