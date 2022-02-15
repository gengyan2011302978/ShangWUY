package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.bean.MessageDetailBean;
import com.phjt.shangxueyuan.di.component.DaggerMessageDetalComponent;
import com.phjt.shangxueyuan.mvp.contract.MessageDetalContract;
import com.phjt.shangxueyuan.mvp.presenter.MessageDetalPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.Constant;


import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * Created by Template
 *
 * @author :
 * description :消息详情
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/28 09:35
 */
public class MessageDetalActivity extends BaseActivity<MessageDetalPresenter> implements MessageDetalContract.View {
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_message_commont)
    TextView tvMessageCommont;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    /**
     * type 1.系统消息 2:课程提醒  ; 3互动提醒 ;4:活动公告
     */
    private int mType;
    private int messageId;
    private String courseId = "";
    private String mLink = "";
    private String link = "";
    private String mTitle = "";
    private int messageClassify;
    private String campId = "";
    private int campMessageType;
    private String topicId = "";


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMessageDetalComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_detal;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("消息详情");
        mType = getIntent().getIntExtra(Constant.MESSAGE_TYPE, 0);
        messageId = getIntent().getIntExtra(Constant.MESSAGE_ID, 0);
        campId = getIntent().getStringExtra(Constant.BUNDLE_TRAINING_CAMP_ID);
        campMessageType = getIntent().getIntExtra(Constant.BUNDLE_TRAINING_MESSAGE_TYPE, 0);
        topicId = getIntent().getStringExtra(Constant.BUNDLE_TOPIC_ID);
        if (mPresenter != null) {
            mPresenter.getMessageDetail(messageId);
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

    @OnClick({R.id.iv_common_back, R.id.tv_url})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_url:
                if (mType == 2) {
                    if (campMessageType == 29 && !TextUtils.isEmpty(campId) && !"0".equals(campId)) {
                        Intent intent = new Intent(this, TrainingCampDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, campId);
                        startActivity(intent);
                    } else {
                        if (!TextUtils.isEmpty(topicId) && !"0".equals(topicId)) {
                            Intent intent = new Intent(MessageDetalActivity.this, TopicInfoActivity.class);
                            intent.putExtra(Constant.BUNDLE_TOPIC_ID, topicId);
                            startActivity(intent);
                        } else {
                            if (!TextUtils.isEmpty(courseId) && !"0".equals(courseId)) {
                                Intent courseIntent = new Intent(MessageDetalActivity.this, CourseDetailActivity.class);
                                courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
                                courseIntent.putExtra(Constant.BUNDLE_COURSE_TO_COMMENT, true);
                                startActivity(courseIntent);
                            } else if (TextUtils.isEmpty(courseId) && !TextUtils.isEmpty(link)) {
                                Intent intent = new Intent(MessageDetalActivity.this, MyWebViewActivity.class);
                                intent.putExtra(Constant.BUNDLE_WEB_URL, link);
                                intent.putExtra(Constant.BUNDLE_WEB_TITLE, mTitle);
                                startActivity(intent);
                            }
                        }
                    }
                } else if (messageClassify == 27) {
                    Intent intent = new Intent(this, ExchangeVoucherActivity.class);
                    intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
                    intent.putExtra(Constant.MESSAGE_IN_VOUCHER_TYPE, 1);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MessageDetalActivity.this, MyWebViewActivity.class);
                    intent.putExtra(Constant.BUNDLE_WEB_URL, mLink);
                    intent.putExtra(Constant.BUNDLE_WEB_TITLE, mTitle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void loadSuccess(MessageDetailBean bean) {
        tvMessageCommont.setText(bean.getContent());
        tvTitle.setText(bean.getTitle());
        tvTime.setText(bean.getCreateTime());
        courseId = bean.getCourseId();
        messageClassify = bean.getMessageClassify();
        link = bean.getLink();
        if (bean.getMessageClassify() == 27) {
            tvUrl.setText("查看详情");
            tvUrl.setVisibility(View.VISIBLE);
        } else {
            if (String.valueOf(bean.getLinkStatus()).isEmpty()) {
                return;
            }
            if (bean.getLinkStatus() == 0 && !TextUtils.isEmpty(bean.getLink())) {
                mLink = bean.getLink();
                mTitle = bean.getTitle();
                tvUrl.setVisibility(View.VISIBLE);
                if ("0".equals(String.valueOf(bean.getMessageClassify()))) {
                    tvUrl.setText("VIP会员权益");
                } else {
                    tvUrl.setText("链接查看");
                }
            }
        }
    }

    @Override
    public void loadFail() {

    }
}
