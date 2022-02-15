package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerPunchClockListComponent;
import com.phjt.shangxueyuan.mvp.contract.PunchClockListContract;
import com.phjt.shangxueyuan.mvp.presenter.PunchClockListPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.fragment.ParticipatingPunchFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 02/05/2021 11:30
 * company: 普华集团
 * description: 打开列表页
 */
public class PunchClockListActivity extends BaseActivity<PunchClockListPresenter> implements PunchClockListContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPunchClockListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_punch_clock_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvTitle.setText("打卡列表");
        Intent intent = getIntent();
        String courseId = intent.getStringExtra(Constant.BUNDLE_COURSE_ID);
        String courseType = intent.getStringExtra(Constant.BUNDLE_COURSE_TYPE);

        ParticipatingPunchFragment trainingCampFragment = ParticipatingPunchFragment.newInstance(0);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUNDLE_COURSE_ID, courseId);
        bundle.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
        trainingCampFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, trainingCampFragment);
        transaction.commit();
    }

    @OnClick(R.id.iv_common_back)
    public void onClick(View view) {
        finish();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.CORRESPONDENT_COURSE) {
                finish();
            }
        }
    }
}
