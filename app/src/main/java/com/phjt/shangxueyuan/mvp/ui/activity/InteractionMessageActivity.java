package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MesExerciseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.di.component.DaggerInteractionMessageComponent;
import com.phjt.shangxueyuan.mvp.contract.InteractionMessageContract;
import com.phjt.shangxueyuan.mvp.presenter.InteractionMessagePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseMessageAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.InteractionMessageAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.NoticeMessageAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phsxy.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :其他消息
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 13:54
 */
public class InteractionMessageActivity extends BaseActivity<InteractionMessagePresenter> implements InteractionMessageContract.View, OnRefreshLoadMoreListener {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_interaction)
    RecyclerView rvInteraction;
    @BindView(R.id.srl_list_interaction)
    SmartRefreshLayout srlListInteraction;

    private int pageNo = 1;
    private int pageSize = 10;
    private View footerView;
    private int mType;
    private CourseMessageAdapter courseAdapter;

    private InteractionMessageAdapter interactionAdapter;
    private NoticeMessageAdapter noticeAdapter;
    private String url;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerInteractionMessageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_interaction_message;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Constant.MESSAGE_TYPE, 0);
        tvCommonTitle.setText(getIntent().getStringExtra(Constant.MESSAGE_HEADER));
        srlListInteraction.setOnRefreshLoadMoreListener(this);
        footerView = LayoutInflater.from(this).inflate(R.layout.item_invitation_list_foot, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvInteraction.setLayoutManager(layoutManager);

        // type 2:课程提醒  ; 3互动提醒 ;4:活动公告
        if (mPresenter != null) {
            mPresenter.getListMessage(mType, pageNo, pageSize, true);
        }
        if (mType == 2) {
            setCourseMessage();
        } else if (mType == 3 || mType == 4) {
            interactionAdapter = new InteractionMessageAdapter(this);
            rvInteraction.setAdapter(interactionAdapter);
            interactionAdapter.addFooterView(footerView);
            interactionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @SingleClick
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    List<MessageListBean.RecordsBean> data = adapter.getData();
                    MessageListBean.RecordsBean recordsBean = data.get(position);
                    if (mType == 4) {
                        openingDetails(recordsBean);
                    } else {
                        correspondentDetails(recordsBean);
                    }
                }
            });

        }
        footerView.setVisibility(View.GONE);
    }

    /**
     * 跳转相对应的课程详情
     *
     * @param recordsBean
     */
    private void correspondentDetails(MessageListBean.RecordsBean recordsBean) {
        String webUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_DYNAMIC);
        Intent intent = new Intent(this, MyWebViewActivity.class);
        switch (recordsBean.getMessageClassify()) {
            case 5:
            case 6:
            case 12:
//            case 13:
//            case 22:
                //课程详情页
                if (TextUtils.isEmpty(recordsBean.getCourseId()) && !"0".equals(recordsBean.getCourseId()) && intent != null) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getCourseId() + "&typeId=1&type=1");
                    startActivity(intent);
                } else if (TextUtils.isEmpty(recordsBean.getOtherId()) && !"0".equals(recordsBean.getOtherId()) && intent != null) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getOtherId() + "&typeId=1&type=1");
                    startActivity(intent);
                }
                break;
            case 7:
            case 8:
            case 14:
//            case 15:
//            case 26:
                //笔记详情页
                if (!"0".equals(recordsBean.getOtherId()) && intent != null) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getOtherId() + "&typeId=2&type=1");
                    startActivity(intent);
                }
                break;
            case 9:
            case 10:
//            case 11:
            case 18:
//            case 19:
//            case 21:
                //专题页详情页
                if (!"0".equals(recordsBean.getOtherId()) && intent != null) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getOtherId() + "&typeId=3&type=1");
                    startActivity(intent);
                }
                break;
            case 16:
//            case 17:
//            case 20:
//            case 23:
//                //帖子详情页
                if (!"0".equals(recordsBean.getOtherId()) && intent != null) {
                    intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getOtherId() + "&typeId=4&type=1");
                    startActivity(intent);
                }
                break;
            case 32:
                if (mPresenter != null && !"0".equals(recordsBean.getOtherId())) {
                    mPresenter.getMesExerciseDetail(recordsBean.getOtherId());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取消息跳转作业列表
     *
     * @param bean
     */
    @Override
    public void loadMesExerciseSuccess(MesExerciseBean bean) {
        Intent intent = new Intent(InteractionMessageActivity.this, ExerciseShowActivity.class);
        intent.putExtra(Constant.BUNDLE_EXERCISE_ID, bean.getId());
        intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, bean.getExerciseBookId());
        intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, bean.getCouId());
        intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, bean.getTrainingId());
        startActivity(intent);
    }

    /**
     * 设置课程提醒
     */
    private void setCourseMessage() {
        courseAdapter = new CourseMessageAdapter(this);
        rvInteraction.setAdapter(courseAdapter);
        courseAdapter.addFooterView(footerView);
        courseAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<MessageListBean.RecordsBean> data = adapter.getData();
            MessageListBean.RecordsBean recordsBean = data.get(position);
            openingDetails(recordsBean);
        });
        courseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<MessageListBean.RecordsBean> recordsBean = adapter.getData();
            String mCourseId = "0";
            //	1 邀请有礼 2 BOC初级课程列表 3 精品试听课程列表 4 反馈
            if (1 == recordsBean.get(position).getMouduleId()) {
                if (null != mPresenter) {
                    mPresenter.inviteCourtesy();
                }
            } else if (2 == recordsBean.get(position).getMouduleId()) {
                Intent intent = new Intent(InteractionMessageActivity.this, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRIMARY_COURSE_ID));
                startActivity(intent);
            } else if (3 == recordsBean.get(position).getMouduleId()) {
                Intent intent = new Intent(InteractionMessageActivity.this, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRACTICE_ID));
                startActivity(intent);
            } else if (4 == recordsBean.get(position).getMouduleId()) {
                startActivity(new Intent(InteractionMessageActivity.this, FeedbackActivity.class));
            } else if (29 == recordsBean.get(position).getMessageClassify() &&
                    !TextUtils.isEmpty(recordsBean.get(position).getOtherId()) && !"0".equals(recordsBean.get(position).getOtherId())) {
                Intent intent = new Intent(this, TrainingCampDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, recordsBean.get(position).getOtherId());
                startActivity(intent);
            } else if (31 == recordsBean.get(position).getMessageClassify()) {
                Intent intent = new Intent(InteractionMessageActivity.this, CourseCategoryActivity.class);
                intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_LIVE_LIST_ID));
                startActivity(intent);
            } else {
                //跳转话题
                if ("0".equals(recordsBean.get(position).getCourseId()) &&
                        !TextUtils.isEmpty(recordsBean.get(position).getTopicId()) && !"0".equals(recordsBean.get(position).getTopicId())) {
                    Intent intent = new Intent(InteractionMessageActivity.this, TopicInfoActivity.class);
                    intent.putExtra(Constant.BUNDLE_TOPIC_ID, "" + recordsBean.get(position).getTopicId());
                    startActivity(intent);
                } else {
                    //跳转课程
                    if (!TextUtils.isEmpty(recordsBean.get(position).getCourseId()) && !"0".equals(recordsBean.get(position).getCourseId())) {
                        mCourseId = recordsBean.get(position).getCourseId();
                    } else if (!TextUtils.isEmpty(recordsBean.get(position).getOtherId()) && !"0".equals(recordsBean.get(position).getOtherId())) {
                        mCourseId = recordsBean.get(position).getOtherId();
                    }
                    if (view.getId() == R.id.tv_play_back && !"0".equals(mCourseId)) {
                        Intent courseIntent = new Intent(InteractionMessageActivity.this, CourseDetailActivity.class);
                        courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, mCourseId);
                        courseIntent.putExtra(Constant.BUNDLE_COURSE_TO_COMMENT, false);
                        startActivity(courseIntent);
                    } else if (view.getId() == R.id.tv_play_back && "0".equals(mCourseId) && !TextUtils.isEmpty(recordsBean.get(position).getLink())) {
                        Intent intent = new Intent(InteractionMessageActivity.this, MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, recordsBean.get(position).getLink());
                        intent.putExtra(Constant.BUNDLE_WEB_TITLE, recordsBean.get(position).getTitle());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * 跳转到消息详情
     *
     * @param recordsBean
     */
    private void openingDetails(MessageListBean.RecordsBean recordsBean) {
        String messageClassify = String.valueOf(recordsBean.getMessageClassify());
        if (TextUtils.isEmpty(messageClassify)) {
            return;
        }
        Intent intent = new Intent(InteractionMessageActivity.this, MessageDetalActivity.class);
        intent.putExtra(Constant.MESSAGE_TYPE, mType);
        intent.putExtra(Constant.MESSAGE_ID, recordsBean.getMessageId());
        if (29 == recordsBean.getMessageClassify() && !TextUtils.isEmpty(recordsBean.getOtherId()) && !"0".equals(recordsBean.getOtherId())) {
            intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, recordsBean.getOtherId());
            intent.putExtra(Constant.BUNDLE_TRAINING_MESSAGE_TYPE, recordsBean.getMessageClassify());
        } else if ("0".equals(recordsBean.getCourseId()) && !TextUtils.isEmpty(recordsBean.getTopicId()) && !"0".equals(recordsBean.getTopicId())) {
            intent.putExtra(Constant.BUNDLE_TOPIC_ID, recordsBean.getTopicId());
        }
        startActivity(intent);

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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void getRefresh(List<MessageListBean.RecordsBean> bean) {
        if (mType == 2) {
            courseAdapter.setNewData(bean);
            courseAdapter.setEmptyView(R.layout.empty_layout, rvInteraction);
        } else if (mType == 3 || mType == 4) {
            interactionAdapter.setNewData(bean);
            interactionAdapter.setEmptyView(R.layout.empty_layout, rvInteraction);
        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void getLoadMore(List<MessageListBean.RecordsBean> bean) {
        if (mType == 2) {
            courseAdapter.addData(bean);
        } else if (mType == 3 || mType == 4) {
            interactionAdapter.addData(bean);

        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void canLoadMore() {
        if (srlListInteraction != null) {
            srlListInteraction.setEnableLoadMore(true);
        }
        if (footerView != null) {
            footerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlListInteraction != null) {
            srlListInteraction.setEnableLoadMore(false);
        }
        if (footerView != null) {
            footerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlListInteraction != null) {
            srlListInteraction.finishRefresh();
            srlListInteraction.finishLoadMore();
        }
    }

    @Override
    public void getLoadFail() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void loadInviteSharetSuccess(String data) {
        url = data;
        if (mPresenter != null) {
            mPresenter.inviteShare();
        }
    }

    @Override
    public void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data) {
        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        Intent intent = new Intent(InteractionMessageActivity.this, ShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHAREWX)
                + "?code=" + url + "&mobile=" + phoneNumber);
        startActivity(intent);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null != mPresenter) {
            pageNo += 1;
            /**
             * 消息类型（ 1 系统通知 2 课程提醒 3 互动提醒 4 活动公告）
             */
            mPresenter.getListMessage(mType, pageNo, pageSize, false);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        if (null != mPresenter) {
            pageNo = 1;
            /**
             * 消息类型（ 1 系统通知 2 课程提醒 3 互动提醒 4 活动公告）
             */
            mPresenter.getListMessage(mType, pageNo, pageSize, true);
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onClick() {
        finish();
    }

}
