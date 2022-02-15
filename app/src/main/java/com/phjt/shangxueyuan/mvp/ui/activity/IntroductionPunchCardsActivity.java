package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.IntroductionPunchCardsBean;
import com.phjt.shangxueyuan.bean.IntroductionTopCardsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerIntroductionPunchCardsComponent;
import com.phjt.shangxueyuan.mvp.contract.IntroductionPunchCardsContract;
import com.phjt.shangxueyuan.mvp.presenter.IntroductionPunchCardsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.IntroductionPunchCardsAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :打卡介绍
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */
public class IntroductionPunchCardsActivity extends BaseActivity<IntroductionPunchCardsPresenter> implements IntroductionPunchCardsContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.tv_punch_cards_title)
    TextView tvPunchCardsTitle;
    @BindView(R.id.rv_punch_cards_partake)
    RecyclerView rvPunchCardsPartake;
    @BindView(R.id.tv_punch_cards_partake)
    TextView tvPunchCardsPartake;
    @BindView(R.id.tv_activity_time)
    TextView tvActivityTime;
    @BindView(R.id.tv_activity_details)
    TextView tvActivityDetails;
    @BindView(R.id.iv_associated_courses)
    ImageView ivAssociatedCourses;
    @BindView(R.id.tv_associated_courses_title)
    TextView tvAssociatedCoursesTitle;
    @BindView(R.id.tv_associated_courses)
    TextView tvAssociatedCourses;
    @BindView(R.id.tv_number_students_enrolleds)
    TextView tvNumberStudentsEnrolleds;
    @BindView(R.id.tv_participationn)
    TextView tvParticipationn;

    private boolean mPermissions = false;
    private IntroductionPunchCardsAdapter adapter;
    private String mId;
    private String mCourseId = "";
    private String courseName = "";
    private int punchmType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIntroductionPunchCardsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_introduction_punch_cards;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("打卡介绍");
        String participationPunchCardsId = getIntent().getStringExtra(Constant.PARTICIPATION_PUNCH_CARDSID);
        String punchCardsCourseId = getIntent().getStringExtra(Constant.PUNCH_CARDS_COURSE_ID);
        punchmType = getIntent().getIntExtra(Constant.PUNCHM_TYPE, 0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPunchCardsPartake.setLayoutManager(layoutManager);
        adapter = new IntroductionPunchCardsAdapter(this);
        rvPunchCardsPartake.setAdapter(adapter);
        if (mPresenter != null) {
            mPresenter.getIntroductionCardst(participationPunchCardsId);
            mPresenter.getHomeFocus(participationPunchCardsId, punchCardsCourseId);
        }
        rvPunchCardsPartake.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildPosition(view) != 0) {
                    outRect.left = -15;
                }
            }
        });
    }

    @OnClick({R.id.iv_common_back, R.id.tv_participationn, R.id.view_go_courses})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_participationn:
                Intent intent = new Intent(IntroductionPunchCardsActivity.this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "打卡主页");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PUNCH_CLOCK) + "?id=" + mId);
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, mCourseId);
                intent.putExtra(Constant.PARTICIPATION_PUNCH_CARDSID, mId);
                startActivity(intent);
                break;
            case R.id.view_go_courses:
                if (1 == punchmType && !TextUtils.isEmpty(mCourseId)) {
                    Intent intent2 = new Intent(this, CourseDetailActivity.class);
                    intent2.putExtra(Constant.BUNDLE_COURSE_ID, mCourseId);
                    intent2.putExtra(Constant.BUNDLE_COURSE_NAME, courseName);
                    startActivity(intent2);
                } else {
                    finish();
                    EventBus.getDefault().post(new EventBean(EventBean.CORRESPONDENT_COURSE, ""));
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void LoadSuccess(IntroductionPunchCardsBean bean) {
        if (bean != null) {
            mId = bean.getId();
            tvPunchCardsTitle.setText(bean.getPunchCardName());
            tvActivityTime.setText(String.format("%s  至  %s", bean.getPunchCardStartDate(), bean.getPunchCardEndDate()));
            CharSequence charSequence = Html.fromHtml(bean.getPunchCardDesc());
            tvActivityDetails.setText(charSequence.toString());
            if (bean.getCouLinkVos() != null && bean.getCouLinkVos().size() > 0) {
                courseName = bean.getCouLinkVos().get(0).getName();
                mCourseId = bean.getCouLinkVos().get(0).getId();
                tvAssociatedCoursesTitle.setText(bean.getCouLinkVos().get(0).getName());
                tvAssociatedCourses.setText(bean.getCouLinkVos().get(0).getCouDesc());
                tvNumberStudentsEnrolleds.setText(String.format("%s人在学", bean.getCouLinkVos().get(0).getStudyNum()));
                GlideUtils.load(bean.getCouLinkVos().get(0).getCoverImg(), ivAssociatedCourses, R.drawable.image_placeholder);
            }
        }
    }

    @Override
    public void LoadFailed() {

    }

    @Override
    public void LoadPunchCardsSuccess(IntroductionTopCardsBean bean) {
        if (bean != null) {
            if (bean.getUserVos() != null && bean.getUserVos().size() > 0) {
                List<IntroductionTopCardsBean.UserVos> mUserVos;
                if (bean.getUserVos().size() >3) {
                    mUserVos = bean.getUserVos().subList(0, 3);
                }else {
                    mUserVos=bean.getUserVos();
                }
                adapter.setNewData(mUserVos);
            }
            if (0 == bean.getPartyUser()) {
                tvPunchCardsPartake.setText("赶紧成为第一个加入的打卡成员");
            } else {
                tvPunchCardsPartake.setText("等" + bean.getPartyUser() + "人参与 " + bean.getPartyNum() + "次打卡");
            }


        }
    }

    @Override
    public void LoadPunchCardsFailed() {

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
