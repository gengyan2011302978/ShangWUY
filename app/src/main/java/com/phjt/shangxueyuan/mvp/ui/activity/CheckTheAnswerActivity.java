package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.bean.CheckTheAnswerBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerCheckTheAnswerComponent;
import com.phjt.shangxueyuan.mvp.contract.CheckTheAnswerContract;
import com.phjt.shangxueyuan.mvp.presenter.CheckTheAnswerPresenter;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.ViewQuestionAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 11:05
 * @description :查看问题
 */
public class CheckTheAnswerActivity extends BaseActivity<CheckTheAnswerPresenter> implements CheckTheAnswerContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_questions_label)
    TextView tvQuestionsLabel;
    @BindView(R.id.tv_questions_title)
    TextView tvQuestionsTitle;
    @BindView(R.id.tv_questioner)
    TextView tvQuestioner;
    @BindView(R.id.tv_time_bt)
    TextView tvTime;
    @BindView(R.id.tv_questions_content)
    ExpandableTextView tvQuestionsContent;
    @BindView(R.id.iv_tutor)
    RoundedImageView ivTutor;
    @BindView(R.id.tv_tutor_name)
    TextView tvTutorName;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_reply_and)
    TextView tvReplyAnd;
    @BindView(R.id.tv_reply_content)
    TextView tvReplyContent;
    @BindView(R.id.rv_selected_answers)
    RecyclerView rvSelectedAnswers;
    @BindView(R.id.rcy_released)
    RecyclerView rcyReleased;
    @BindView(R.id.cl_name)
    ConstraintLayout clName;
    @BindView(R.id.view_vo)
    View viewVo;
    @BindView(R.id.view_udge)
    View viewUdge;

    private List<String> mDatas;
    private List<String> mDatasImgList;
    private String questionId;
    private Drawable mFabulousUn, mFabulous;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCheckTheAnswerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_check_the_answer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText(getString(R.string.mine_view_problems));
        rvSelectedAnswers.setLayoutManager(new LinearLayoutManager(CheckTheAnswerActivity.this));
        rcyReleased.setNestedScrollingEnabled(false);
        rcyReleased.setVerticalScrollBarEnabled(true);
        questionId = getIntent().getStringExtra(Constant.QUESTION_ID);
        mDatas = new ArrayList<>();
        mDatasImgList = new ArrayList<>();
        if (mPresenter != null && !TextUtils.isEmpty(questionId)) {
            mPresenter.getUserQuestion(questionId);
        }

        mFabulousUn = getResources().getDrawable(R.drawable.ic_zan);
        mFabulous = getResources().getDrawable(R.drawable.ic_zan_s);
        CheckTheAnswerBean bean = new CheckTheAnswerBean();
        setData(bean);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void checkTheAnswerSuccess(CheckTheAnswerBean bean) {
        setData(bean);
    }

    @Override
    public void checkTheAnswerFail() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Singleton
    @OnClick({R.id.iv_common_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;

            default:
                break;
        }
    }


    private void setData(CheckTheAnswerBean bean) {

        tvQuestionsTitle.setText(bean.getTitle());
        tvQuestionsLabel.setText(TextUtils.isEmpty(bean.getRealmName()) ? "" : bean.getRealmName());
        tvQuestionsLabel.setVisibility(TextUtils.isEmpty(bean.getRealmName()) ? View.GONE : View.VISIBLE);
        tvTime.setText(TextUtils.isEmpty(bean.getCreateTime()) ? "" : bean.getCreateTime());
        tvQuestionsContent.setContent(TextUtils.isEmpty(bean.getContent()) ? "" : bean.getContent());
        tvQuestionsContent.setVisibility(TextUtils.isEmpty(bean.getContent()) ? View.GONE : View.VISIBLE);
        if (bean.getUserInfo() != null) {
            CheckTheAnswerBean.UserInfo userInfo = bean.getUserInfo();
            if (!TextUtils.isEmpty(userInfo.getNickName())) {
                tvQuestioner.setText(String.format(getString(R.string.questions_answers_questioner), userInfo.getNickName()));
            }
        }


        if (bean.getLecLecturer() != null) {
            CheckTheAnswerBean.LecLecturer lecLecturer = bean.getLecLecturer();
            tvTutorName.setText(lecLecturer.getName()+"老师");
            viewVo.setVisibility(!TextUtils.isEmpty(lecLecturer.getName()) ? View.VISIBLE : View.GONE);
            AppImageLoader.loadRoundImg(lecLecturer.getCoverImg(), ivTutor, R.drawable.iv_mine_avatar);
        }


        if (!TextUtils.isEmpty(bean.getQuestionImg())) {
            setReleased(bean.getQuestionImg(), "");
        }

        if (bean.getTutorAnswer() != null) {
            CheckTheAnswerBean.TutorAnswer tutorAnswer = bean.getTutorAnswer();
            tvReplyAnd.setText(TextUtils.isEmpty(tutorAnswer.getCreateTime()) ? "" : String.format(getString(R.string.questions_answers_reply_and), tutorAnswer.getCreateTime()));
            tvReplyContent.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(tutorAnswer.getContent())) {
                String content = tutorAnswer.getContent();
                CharSequence charSequence;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    charSequence = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    charSequence = Html.fromHtml(content);
                }
                tvReplyContent.setText(2 == bean.getIsReply() ? "忽略理由:" + charSequence : charSequence);
            }
        }
    }


    private void setReleased(String questionImg, String urlPre) {

        List<String> imgList = Arrays.asList(questionImg.split(","));
        if (imgList != null && imgList.size() < 5) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
            rcyReleased.setLayoutManager(layoutManager);
        } else if (imgList != null && imgList.size() > 4) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
            rcyReleased.setLayoutManager(layoutManager);
        }
        ViewQuestionAdapter mAdapter = new ViewQuestionAdapter(this);
        rcyReleased.setAdapter(mAdapter);
        mAdapter.setNewData(imgList);
        mDatasImgList.clear();
        mDatasImgList.addAll(imgList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(CheckTheAnswerActivity.this, BigPhotoActivity.class);
            intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) mDatasImgList);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, urlPre);
            ArchitectUtils.startActivity(intent);
        });

    }


}