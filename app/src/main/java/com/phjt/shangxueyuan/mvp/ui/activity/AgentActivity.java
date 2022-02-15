package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerAgentComponent;
import com.phjt.shangxueyuan.mvp.contract.AgentContract;
import com.phjt.shangxueyuan.mvp.presenter.AgentPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.SubordinateAgentsFragment;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Roy
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:01
 * @description :代理商
 */
public class AgentActivity extends BaseActivity<AgentPresenter> implements AgentContract.View {
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.stl_agent)
    SlidingTabLayout stlAgent;
    @BindView(R.id.viewpager_agent)
    ViewPager viewpagerAgent;
    @BindView(R.id.tv_direct_invitation)
    TextView tvDirectInvitation;
    @BindView(R.id.tv_lower_agent)
    TextView tvLowerAgent;
    @BindView(R.id.cl_type)
    FrameLayout clType;
    @BindView(R.id.view_ask_questions)
    View viewAskQuestions;

    private int mPosition;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAgentComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_agent;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    /**
     * 初始化提问题 viewpager
     */
    private void initViewPager() {
        tvCommonTitle.setText("代理商");
        String[] titles = {getString(R.string.str_direct_invitation), getString(R.string.str_subordinate_agents)};
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(SubordinateAgentsFragment.newInstance(Constant.INDEX_TUTOR_ANSWER));
        fragmentList.add(SubordinateAgentsFragment.newInstance(Constant.INDEX_SELECTED_ANSWER));

        MyHomeAdapter fragmentAdapter = new MyHomeAdapter(getSupportFragmentManager(), fragmentList);
        viewpagerAgent.setAdapter(fragmentAdapter);
        viewpagerAgent.setCurrentItem(Constant.INDEX_TUTOR_ANSWER);
        viewpagerAgent.setOffscreenPageLimit(Constant.INDEX_MINE_QUESTION);
        stlAgent.setViewPager(viewpagerAgent, titles);
        stlAgent.setCurrentTab(Constant.INDEX_TUTOR_ANSWER);
        stlAgent.updateTabSelection(0);

        viewpagerAgent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPosition = i;
                if (i == 1) {
                    tvLowerAgent.setTextColor(ContextCompat.getColor(AgentActivity.this, R.color.color_white));
                    tvLowerAgent.setBackgroundResource(R.drawable.bg_ask_questions_left);
                    tvDirectInvitation.setTextColor(ContextCompat.getColor(AgentActivity.this, R.color.color_FF333333));
                    tvDirectInvitation.setBackgroundResource(R.drawable.bg_ask_questions_right);
                    viewAskQuestions.setVisibility(View.GONE);
                } else {
                    tvDirectInvitation.setTextColor(ContextCompat.getColor(AgentActivity.this, R.color.color_white));
                    tvDirectInvitation.setBackgroundResource(R.drawable.bg_ask_questions_left);
                    tvLowerAgent.setTextColor(ContextCompat.getColor(AgentActivity.this, R.color.color_FF333333));
                    tvLowerAgent.setBackgroundResource(R.drawable.bg_ask_questions_right);
                    viewAskQuestions.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_common_back, R.id.tv_direct_invitation, R.id.tv_lower_agent})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;

            case R.id.tv_direct_invitation:
                tvLowerAgent.setTextColor(ContextCompat.getColor(this, R.color.color_white));
                tvLowerAgent.setBackgroundResource(R.drawable.bg_ask_questions_left);
                tvDirectInvitation.setTextColor(ContextCompat.getColor(this, R.color.color_FF333333));
                tvDirectInvitation.setBackgroundResource(R.drawable.bg_ask_questions_right);
                viewpagerAgent.setCurrentItem(0);
                viewAskQuestions.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_lower_agent:
                tvDirectInvitation.setTextColor(ContextCompat.getColor(this, R.color.color_white));
                tvDirectInvitation.setBackgroundResource(R.drawable.bg_ask_questions_left);
                tvLowerAgent.setTextColor(ContextCompat.getColor(this, R.color.color_FF333333));
                tvLowerAgent.setBackgroundResource(R.drawable.bg_ask_questions_right);
                viewpagerAgent.setCurrentItem(1);
                viewAskQuestions.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}