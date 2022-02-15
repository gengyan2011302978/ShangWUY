package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerQuestionsAndAnswersComponent;
import com.phjt.shangxueyuan.mvp.contract.QuestionsAndAnswersContract;
import com.phjt.shangxueyuan.mvp.presenter.QuestionsAndAnswersPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.SelectedAnswersFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.TutorAnsweringQuestionsFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.ScreenMapUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.shangxueyuan.widgets.dialog.StudyScreenPopWindow;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.SPUtils;
import com.phsxy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author : Roy
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 10:56
 * @description :问答
 */
public class QuestionsAndAnswersActivity extends BaseActivity<QuestionsAndAnswersPresenter> implements QuestionsAndAnswersContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;

    @BindView(R.id.iv_screen_select)
    ImageView ivScreenData;
    @BindView(R.id.iv_screen_reply)
    ImageView ivScreenReply;

    @BindView(R.id.tv_study_screen)
    TextView tvScreen;
    @BindView(R.id.stl_answers)
    SlidingTabLayout stlAnswers;

    @BindView(R.id.stl_my_consultation)
    SlidingTabLayout stlMyConsultation;
    @BindView(R.id.view_pager_my_consultation)
    ViewPager viewPagermyconsultation;

    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_popwindow)
    View viewPopwindow;
    @BindView(R.id.answers_viewpager)
    ViewPager answersViewpager;
    @BindView(R.id.tv_ask_questions)
    TextView tvAskQuestions;
    @BindView(R.id.tv_come_consult)
    TextView tvComeConsult;
    @BindView(R.id.view_ask_questions)
    View viewAskQuestions;
    @BindView(R.id.view_acc)
    View viewAcc;


    @BindView(R.id.screen_include)
    ConstraintLayout clScreen;
    @BindView(R.id.tv_screen_reply)
    TextView tvScreenReply;
    @BindView(R.id.tv_screen_data)
    TextView tvScreenData;
    public StudyScreenPopWindow popWindow;
    private int mPosition = 0;
    private int mMyconsultationPosition = 0;

    private TutorAnsweringQuestionsFragment myConsultationFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerQuestionsAndAnswersComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_questions_and_answers;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonRight.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(getString(R.string.string_questions_answers));
        tvCommonRight.setText(getString(R.string.questions_open_column));
        Drawable mEdit = ContextCompat.getDrawable(this, R.drawable.ic_questions_edit);
        tvCommonRight.setCompoundDrawablesRelativeWithIntrinsicBounds(mEdit, null, null, null);

        tvCommonTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvScreen.setText(getString(R.string.questions_answers_screen));
        Drawable mScreen = ContextCompat.getDrawable(this, R.drawable.selector_study_screen);
        tvScreen.setCompoundDrawablesRelativeWithIntrinsicBounds(mScreen, null, null, null);
        tvScreen.setSelected(false);
        initViewPager();
        initConsultPager();

        if (TextUtils.isEmpty(SPUtils.getInstance().getString(Constant.BUNDLE_QUESTIONS_BG))) {
            DialogUtils.showOpenColumnDialog(this, new DialogUtils.OnClickDialogListener() {
                @Override
                public void clickOk() {
                    SPUtils.getInstance().put(Constant.BUNDLE_QUESTIONS_BG, Constant.BUNDLE_QUESTIONS_BG);
                }
            });
        }

    }

    /**
     * 初始化提问题 viewpager
     */
    private void initViewPager() {
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) stlAnswers.getLayoutParams();
        lp.rightMargin = AutoSizeUtils.dp2px(this, 112f);
        stlAnswers.setLayoutParams(lp);

        String[] titles = {getString(R.string.question_tutor_answer), getString(R.string.question_selected_answer), getString(R.string.question_my_answer)};
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(TutorAnsweringQuestionsFragment.newInstance(Constant.INDEX_TUTOR_ANSWER));
        fragmentList.add(SelectedAnswersFragment.newInstance(Constant.INDEX_SELECTED_ANSWER));
        fragmentList.add(SelectedAnswersFragment.newInstance(Constant.INDEX_MINE_QUESTION));
        MyHomeAdapter fragmentAdapter = new MyHomeAdapter(getSupportFragmentManager(), fragmentList);
        answersViewpager.setAdapter(fragmentAdapter);
        answersViewpager.setCurrentItem(Constant.INDEX_TUTOR_ANSWER);
        answersViewpager.setOffscreenPageLimit(Constant.INDEX_MINE_QUESTION);
        stlAnswers.setViewPager(answersViewpager, titles);
        stlAnswers.setCurrentTab(Constant.INDEX_TUTOR_ANSWER);
        stlAnswers.updateTabSelection(0);
        tvScreen.setVisibility(View.VISIBLE);
        clScreen.setVisibility(View.GONE);
        answersViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPosition = i;
                clScreen.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /**
     * 初始化来咨询 viewpager
     */
    private void initConsultPager() {
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) stlMyConsultation.getLayoutParams();
        lp.rightMargin = AutoSizeUtils.dp2px(this, 198f);
        stlMyConsultation.setLayoutParams(lp);
        myConsultationFragment = TutorAnsweringQuestionsFragment.newInstance(Constant.INDEX_MY_CONSULTATION);

        String[] titles = {getString(R.string.question_ask_questions), getString(R.string.question_my_consultation)};
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(TutorAnsweringQuestionsFragment.newInstance(Constant.INDEX_SELECTED_ANSWER));
        fragmentList.add(myConsultationFragment);

        MyHomeAdapter fragmentAdapter = new MyHomeAdapter(getSupportFragmentManager(), fragmentList);
        fragmentAdapter.notifyDataSetChanged();
        viewPagermyconsultation.setAdapter(fragmentAdapter);
        viewPagermyconsultation.setCurrentItem(Constant.INDEX_TUTOR_ANSWER);
        viewPagermyconsultation.setOffscreenPageLimit(1);
        stlMyConsultation.setViewPager(viewPagermyconsultation, titles);
        stlMyConsultation.setCurrentTab(Constant.INDEX_TUTOR_ANSWER);
        stlMyConsultation.updateTabSelection(0);

        viewPagermyconsultation.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mMyconsultationPosition = i;
                if (1 == i) {
                    tvScreen.setVisibility(View.GONE);
                    clScreen.setVisibility(View.VISIBLE);
                    viewAcc.setVisibility(View.GONE);
                } else {
                    tvScreen.setVisibility(View.VISIBLE);
                    clScreen.setVisibility(View.GONE);
                    viewAcc.setVisibility(View.VISIBLE);
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
        TipsUtil.show(message);
    }


    @SingleClick
    @OnClick({R.id.tv_common_right, R.id.iv_common_back, R.id.tv_study_screen, R.id.tv_ask_questions,
            R.id.tv_come_consult, R.id.iv_screen_reply, R.id.tv_screen_reply, R.id.iv_screen_select, R.id.tv_screen_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_common_right:
                //开通专栏
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "开通直播宣讲");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_OPEN_COLUMN) + "?id=999999");
                startActivity(intent);
                break;

            case R.id.tv_ask_questions:
                //提问题
                viewAskQuestions.setVisibility(View.VISIBLE);
                answersViewpager.setVisibility(View.VISIBLE);
                stlAnswers.setVisibility(View.VISIBLE);
                tvScreen.setVisibility(View.VISIBLE);
                stlMyConsultation.setVisibility(View.GONE);
                viewPagermyconsultation.setVisibility(View.GONE);
                clScreen.setVisibility(View.GONE);
                viewAcc.setVisibility(View.VISIBLE);

                tvAskQuestions.setTextColor(ContextCompat.getColor(this, R.color.color_white));
                tvAskQuestions.setBackgroundResource(R.drawable.bg_ask_questions_left);
                tvComeConsult.setTextColor(ContextCompat.getColor(this, R.color.color_FF333333));
                tvComeConsult.setBackgroundResource(R.drawable.bg_ask_questions_right);

                break;
            case R.id.tv_come_consult:
                //来咨询
                viewAskQuestions.setVisibility(View.GONE);
                answersViewpager.setVisibility(View.GONE);
                stlAnswers.setVisibility(View.GONE);
                stlMyConsultation.setVisibility(View.VISIBLE);
                viewPagermyconsultation.setVisibility(View.VISIBLE);

                if (1 == mMyconsultationPosition) {
                    tvScreen.setVisibility(View.GONE);
                    clScreen.setVisibility(View.VISIBLE);
                    viewAcc.setVisibility(View.GONE);
                } else {
                    tvScreen.setVisibility(View.VISIBLE);
                    clScreen.setVisibility(View.GONE);
                    viewAcc.setVisibility(View.VISIBLE);
                }
                tvComeConsult.setBackgroundResource(R.drawable.bg_ask_questions_left);

                tvComeConsult.setTextColor(ContextCompat.getColor(this, R.color.color_white));
                tvAskQuestions.setTextColor(ContextCompat.getColor(this, R.color.color_FF333333));
                tvAskQuestions.setBackgroundResource(R.drawable.bg_come_consult);
                break;
            case R.id.tv_study_screen:
                //筛选
                setScreen();
                break;
            case R.id.iv_screen_reply:
            case R.id.tv_screen_reply:
                if (myConsultationFragment != null) {
                    myConsultationFragment.setPopWindow(4);
                }
                break;
            case R.id.iv_screen_select:
            case R.id.tv_screen_data:
                //时间
                if (myConsultationFragment != null) {
                    myConsultationFragment.setPopWindow(3);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 筛选
     */

    private void setScreen() {
        if (tvScreen.isSelected()) {
            //选中，关闭筛选框
            tvScreen.setSelected(false);
            closePopWindow();
        } else {
            //未选中，打开筛选框
            if (mPresenter != null) {
                mPresenter.getRealmSelectList();
            }
        }
    }


    /**
     * 获取筛选成功
     *
     * @param beans
     */
    @Override
    public void loadDataSuccess(List<ScreenlBean> beans) {
        if (beans != null && !beans.isEmpty()) {
            closePopWindow();
            tvScreen.setTextColor(ContextCompat.getColor(this, R.color.color_FFFF650C));
            tvScreen.setSelected(true);
            popWindow = new StudyScreenPopWindow(this, beans,
                    answersViewpager.getCurrentItem() == Constant.INDEX_TUTOR_ANSWER ? ScreenMapUtils.ANSWER_ONE :
                            (answersViewpager.getCurrentItem() == Constant.INDEX_SELECTED_ANSWER ? ScreenMapUtils.ANSWER_TWO : ScreenMapUtils.ANSWER_THREE)
                    , new StudyScreenPopWindow.StudyPopInter() {
                @Override
                public void sure(String realmId) {
                    popWindowCallBack(realmId);

                }
            });
            popWindow.setTitle(getString(R.string.question_select_interest));
            popWindow.showAsDropDown(viewPopwindow, 0, 1);

            popWindow.setOnDismissListener(() -> {
                tvScreen.setSelected(false);
                tvScreen.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
            });

        }
    }

    private void closePopWindow() {
        if (popWindow != null) {
            popWindow.dismiss();
            popWindow = null;
        }
    }

    public void popWindowCallBack(String statusId) {
        if (mPosition == 0 || mMyconsultationPosition == 0) {
            EventBusManager.getInstance().post(new EventBean(EventBean.REALM_ID, statusId));
        } else if (mPosition == 1) {
            EventBusManager.getInstance().post(new EventBean(EventBean.QUESTIONS_ID, statusId));
        } else {
            EventBusManager.getInstance().post(new EventBean(EventBean.MY_ANSWERS_ID, statusId));
        }
    }

    public void selectTimeUp() {
        ivScreenData.setImageResource(R.drawable.icon_select_down);
    }

    public void selectReplyUp() {
        ivScreenReply.setImageResource(R.drawable.icon_select_down);
    }

    public void selectTimeDown() {
        ivScreenData.setImageResource(R.drawable.icon_select_up);
    }

    public void selectReplyDown() {
        ivScreenReply.setImageResource(R.drawable.icon_select_up);
    }


    public void setReplyText(String name) {
        tvScreenData.setText(name);
    }

    public void setDataText(String name) {
        tvScreenReply.setText(name);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ScreenMapUtils.getInstance().clearAnswer();
    }

}