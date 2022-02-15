package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerAllTopicComponent;
import com.phjt.shangxueyuan.mvp.contract.AllTopicContract;
import com.phjt.shangxueyuan.mvp.presenter.AllTopicPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyDownloadAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicHotFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicTimeFragment;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:29
 * company: 普华集团
 * description: 模版请保持更新
 */
public class AllTopicActivity extends BaseActivity<AllTopicPresenter> implements AllTopicContract.View {

    TopicHotFragment topicHotFragment;
    TopicTimeFragment topicTimeFragment;
    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.share_titile)
    ConstraintLayout shareTitile;
    @BindView(R.id.stl_topic)
    SlidingTabLayout stlTopic;
    @BindView(R.id.vp_topic)
    ViewPager vpTopic;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAllTopicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_all_topic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("全部话题");
        String[] titles = {"按热度", "按时间"};
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> fragments = new ArrayList<>();
        if (null!=getIntent().getStringExtra("local")){
            topicHotFragment = TopicHotFragment.newInstance(1);
            topicTimeFragment = TopicTimeFragment.newInstance(1);
        }else {
            topicHotFragment = TopicHotFragment.newInstance(2);
            topicTimeFragment = TopicTimeFragment.newInstance(2);
        }

        fragments.add(topicHotFragment);
        fragments.add(topicTimeFragment);
        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(fragmentManager, fragments);
        vpTopic.setAdapter(myDownloadAdapter);
        vpTopic.setCurrentItem(0);
        vpTopic.setOffscreenPageLimit(2);
        stlTopic.setViewPager(vpTopic, titles);
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked() {
        Intent i = new Intent();
        i.putExtra(Constant.PUSH_TOPICID, "");
        i.putExtra(Constant.PUSH_TOPICNAME, "");
        setResult(9,i);
        finish();
    }
}
