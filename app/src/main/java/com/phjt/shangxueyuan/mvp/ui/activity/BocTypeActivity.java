package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;
import com.phjt.shangxueyuan.di.component.DaggerBocTypeComponent;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.BocTypeContract;
import com.phjt.shangxueyuan.mvp.presenter.BocTypePresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.MyProgressView;
import com.phjt.view.roundView.RoundLinearLayout;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 10/27/2020 13:53
 * company: 普华集团
 * description: BOC 初级、中级、高级 页面
 */
public class BocTypeActivity extends BaseActivity<BocTypePresenter> implements BocTypeContract.View, INotFitsImmersionBar {

    @BindView(R.id.tv_boc_title)
    TextView mTvBocTitle;
    @BindView(R.id.iv_top_bg)
    ImageView mIvTopBg;
    @BindView(R.id.iv_label)
    ImageView mIvLabel;
    @BindView(R.id.tv_des)
    TextView mTvDes;
    @BindView(R.id.iv_progress_icon)
    ImageView mIvProgressIcon;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.cl_progress)
    ConstraintLayout mClProgress;
    @BindView(R.id.mPv)
    MyProgressView mPv;
    @BindView(R.id.tv_study_in)
    RoundTextView mTvStudyIn;
    @BindView(R.id.tv_course_name)
    TextView mTvCourseName;
    @BindView(R.id.tv_continue)
    TextView mTvContinue;
    @BindView(R.id.ll_watch_continue)
    RoundLinearLayout mLlWatchContinue;

    private int[] mTopBgs = {R.drawable.boc_primary_bg, R.drawable.boc_middle_bg, R.drawable.boc_high_bg};
    private int[] mIvLabels = {R.drawable.boc_primary_label, R.drawable.boc_middle_label, R.drawable.boc_high_label};
    private int[] mClProgresses = {R.drawable.boc_primary_progress_bg, R.drawable.boc_middle_progress_bg, R.drawable.boc_high_progress_bg};
    private int[] mIvProgressIcons = {R.drawable.boc_primary_progress, R.drawable.boc_middle_progress, R.drawable.boc_high_progress};
    private int[] mStudyInColors = {R.color.color_FEBB32, R.color.color_3D98FC, R.color.color_AD7EFB};
    private int[] mCourseTypeDes = {R.string.boc_primary_des, R.string.boc_middle_des, R.string.boc_high_des};

    private int[] mProgressBgColors = {R.color.color_FFF1D6, R.color.color_D3ECFF, R.color.color_ECDEFF};
    private int[] mProgressColors = {R.color.color_FBB222, R.color.color_3B92FC, R.color.color_B57FFD};


    public static final String WATCH_CONTINUE_IN = "进入学习";
    public static final String WATCH_CONTINUE_WAIT = "敬请期待";

    /**
     * 0初、1中、2高 标识
     */
    private int type;
    /**
     * 初/中/高 课程id
     */
    private String primaryCouTypeId;
    private String middleCouTypeId;
    private String highCouTypeId;

    /**
     * 进入学习 二级id
     */
    private String secondId;
    /**
     * 继续观看id
     */
    private String latestWatchId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBocTypeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_boc_type;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        primaryCouTypeId = SPUtils.getInstance().getString(Constant.SP_PRIMARY_COURSE_ID);
        middleCouTypeId = SPUtils.getInstance().getString(Constant.SP_MIDDLE_COURSE_ID);
        highCouTypeId = SPUtils.getInstance().getString(Constant.SP_HIGH_COURSE_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCourseTypeData();
    }

    private void getCourseTypeData() {
        if (mPresenter != null) {
            mPresenter.getCourseClassifyList(type == 0 ? primaryCouTypeId : type == 1 ? middleCouTypeId : highCouTypeId);
        }
    }

    @Override
    public void loadDataSuccess(CourseClassifyBean bean) {
        if (bean != null) {
            setTypeViews();

            //有一个开放，则认为开放
            boolean isOpen = false;
            //进度
            long courseWatchDuration = 0L;
            long sumTimeLong = 0L;
            //上次观看实体
            CourseClassifyBean.LatestWatch latestWatch = null;

            List<CourseClassifyBean.ChildListBean> childList = bean.getChildList();
            if (childList != null && childList.size() > 0) {
                for (int i = 0; i < childList.size(); i++) {
                    CourseClassifyBean.ChildListBean childListBean = childList.get(i);
                    //1开放 2关闭
                    int state = childListBean.getState();
                    if (state == 1 && !isOpen) {
                        isOpen = true;
                        secondId = String.valueOf(childListBean.getId());
                    }

                    courseWatchDuration += childListBean.getTotalWatchDuration();
                    sumTimeLong += childListBean.getSumTimeLong();

                    CourseClassifyBean.LatestWatch latestWatchBean = childListBean.getLatestWatch();
                    if (latestWatchBean != null) {
                        latestWatch = latestWatchBean;
                    }
                }

                if (isOpen) {
                    mTvStudyIn.setText(WATCH_CONTINUE_IN);
                    mTvStudyIn.setTextColor(ContextCompat.getColor(this, R.color.white));
                } else {
                    mTvStudyIn.setText(WATCH_CONTINUE_WAIT);
                    mTvStudyIn.setTextColor(ContextCompat.getColor(this, R.color.color_FF8F62));
                    mTvStudyIn.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.color_F5F5F5));
                    mLlWatchContinue.setVisibility(View.INVISIBLE);
                }

                mTvProgress.setText(String.format("%s%%", TimeUtils.getStudyLook(courseWatchDuration, sumTimeLong)));
                mPv.setProgress((int) sumTimeLong, (int) courseWatchDuration, mProgressBgColors[type], mProgressColors[type]);

                if (latestWatch != null) {
                    latestWatchId = latestWatch.getId();
                    mLlWatchContinue.setVisibility(View.VISIBLE);
                    mTvCourseName.setText(String.format("上次观看：%s", latestWatch.getName()));
                } else {
                    mLlWatchContinue.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void setTypeViews() {
        mIvTopBg.setImageResource(mTopBgs[type]);
        mIvLabel.setImageResource(mIvLabels[type]);
        mClProgress.setBackgroundResource(mClProgresses[type]);
        mIvProgressIcon.setImageResource(mIvProgressIcons[type]);
        mTvStudyIn.getDelegate().setBackgroundColor(ContextCompat.getColor(this, mStudyInColors[type]));

        mTvDes.setText(getString(mCourseTypeDes[type]));
    }

    @OnClick({R.id.iv_back, R.id.iv_primary, R.id.iv_middle, R.id.iv_high, R.id.tv_study_in, R.id.ll_watch_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_primary:
                type = 0;
                getCourseTypeData();
                break;
            case R.id.iv_middle:
                type = 1;
                getCourseTypeData();
                break;
            case R.id.iv_high:
                type = 2;
                getCourseTypeData();
                break;
            case R.id.tv_study_in:
                if (WATCH_CONTINUE_IN.equals(mTvStudyIn.getText().toString())) {
                    Intent intent = new Intent(this, CourseCategoryActivity.class);
                    intent.putExtra(Constant.COURSE_TYPE_ID, type == 0 ? primaryCouTypeId : type == 1 ? middleCouTypeId : highCouTypeId);
                    intent.putExtra(Constant.COURSE_TYPE_SECOND_ID, secondId);
                    startActivity(intent);
                } else {
                    if ("vivo".equals(UmengUtil.getAppInfo(BocTypeActivity.this, Constant.UMENG_CHANNEL))) {
                        showMessage("暂未开放，敬请期待~");
                    } else {
                        showMessage("您先完成初级课程，敬请期待~");
                    }
                }
                break;
            case R.id.ll_watch_continue:
                if (!TextUtils.isEmpty(latestWatchId)) {
                    Intent intent = new Intent(this, CourseDetailActivity.class);
                    intent.putExtra(Constant.BUNDLE_COURSE_ID, latestWatchId);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        if (mTvStudyIn != null) {
            mTvStudyIn.setClickable(false);
            mLlWatchContinue.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mTvStudyIn != null) {
            mTvStudyIn.setClickable(true);
            mLlWatchContinue.setClickable(true);
        }
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
