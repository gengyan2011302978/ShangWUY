package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TopicInfoBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerTopicInfoComponent;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoContract;
import com.phjt.shangxueyuan.mvp.presenter.TopicInfoPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyDownloadAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.TopicInfoListFragment;
import com.phjt.shangxueyuan.utils.BitmapPhjtUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.shangxueyuan.widgets.popupwindow.TopicChoosePopupWindow;
import com.phjt.view.roundImg.RoundedImageView;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.NetworkUtils;
import com.phsxy.utils.SPUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 15:08
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TopicInfoActivity extends BaseActivity<TopicInfoPresenter> implements TopicInfoContract.View, INotFitsImmersionBar, TopicChoosePopupWindow.OnItemClickListener {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_topictitle)
    TextView tvTopictitle;
    @BindView(R.id.tv_topicdesc)
    TextView tvTopicdesc;
    @BindView(R.id.iv_person)
    ImageView ivPerson;
    @BindView(R.id.iv_mine_head_pic)
    RoundedImageView ivMineHeadPic;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.tv_local_one)
    TextView tvLocalOne;
    @BindView(R.id.relat_local_one)
    RelativeLayout relatLocalOne;
    @BindView(R.id.tv_local_tow)
    TextView tvLocalTow;
    @BindView(R.id.relat_local_tow)
    RelativeLayout relatLocalTow;
    @BindView(R.id.stl_topic)
    SlidingTabLayout stlTopic;
    @BindView(R.id.vp_topic)
    ViewPager vpTopic;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.iv_join)
    ImageView ivJoin;
    @BindView(R.id.iv_line)
    ImageView ivLine;
    @BindView(R.id.iv_top_bg)
    ImageView ivTopBg;
    @BindView(R.id.iv_top_bg1)
    ImageView ivTopBg1;
    @BindView(R.id.iv_no_network_back)
    ImageView ivNoNetworkBack;
    @BindView(R.id.iv_no_network)
    ImageView ivNoNetwork;
    @BindView(R.id.tv_no_network)
    TextView tvNoNetwork;
    @BindView(R.id.tv_refresh)
    RoundTextView tvRefresh;
    @BindView(R.id.no_network_layout)
    View mNoNetworkLayout;
    @BindView(R.id.tv_edit)
    TextView mTvEdit;
    @BindView(R.id.tv_share)
    TextView tvShare;
    private TopicChoosePopupWindow popupWindow;
    private String topicId;
    private String topicUserId;
    int vpIndex = 0;
    private int vipStatus;
    private Integer freezeState;
    String spUserId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
    /**
     * 话题实体
     */
    private TopicInfoBean mTopicInfoBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTopicInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_topic_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        topicId = intent.getStringExtra(Constant.BUNDLE_TOPIC_ID);

        if (!NetworkUtils.isConnected()) {
            mNoNetworkLayout.setVisibility(View.VISIBLE);
            ivNoNetworkBack.setVisibility(View.VISIBLE);
            setBarBgColor(true);
        }

        getTopicDetail();
    }

    private void getTopicDetail() {
        if (mPresenter != null) {
            mPresenter.topicDetail(topicId);
        }
    }

    private void setBarBgColor(boolean isWhite) {
        ImmersionBar.with(this).fitsSystemWindows(isWhite)
                .statusBarColor(isWhite ? R.color.white : R.color.color_50827462)
                .statusBarDarkFont(isWhite)
                .init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (data != null && data.getBooleanExtra(Constant.PUSH_TOPICID_CALLBACK, false)) {
                vpIndex = 1;
            }
            getTopicDetail();
        } else if (requestCode == 1002) {
            getTopicDetail();
        }
    }

    @Override
    public void topicDetailSucceed(TopicInfoBean topicInfoBean) {
        mTopicInfoBean = topicInfoBean;


        if (TextUtils.equals(spUserId, topicInfoBean.getUserId())) {
            //自己的话题，显示编辑按钮
//            mTvEdit.setVisibility(View.VISIBLE);
            tvShare.setBackgroundResource(R.drawable.topic_more_icon);
        } else {
            tvShare.setBackgroundResource(R.drawable.share_white);
        }
        popupWindow = new TopicChoosePopupWindow(this);
        popupWindow.setOnItemClickListener(this);
        mNoNetworkLayout.setVisibility(View.GONE);
        ivNoNetworkBack.setVisibility(View.GONE);
        topicUserId = topicInfoBean.getUserId() == null ? "" : topicInfoBean.getUserId();
        vipStatus = topicInfoBean.getVipState();
        freezeState = topicInfoBean.getFreezeState();
        setBarBgColor(false);
        String[] titles = {"热门", "最新"};
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TopicInfoListFragment.newInstance("1", topicId, topicUserId, topicInfoBean.getVipState(), topicInfoBean.getFreezeState()));
        fragments.add(TopicInfoListFragment.newInstance("2", topicId, topicUserId, topicInfoBean.getVipState(), topicInfoBean.getFreezeState()));
        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(fragmentManager, fragments);
        vpTopic.setAdapter(myDownloadAdapter);
        vpTopic.setCurrentItem(vpIndex);
        vpTopic.setOffscreenPageLimit(1);
        stlTopic.setViewPager(vpTopic, titles);
        vpTopic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                stlTopic.setCurrentTab(i);
                vpIndex = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tvTopictitle.setText(String.format("#%s", topicInfoBean.getTopicName()));
        tvTopicdesc.setText(topicInfoBean.getFocusDescribe());
        tvNumber.setText(String.format("%d条动态", topicInfoBean.getThemeNum()));
        Glide.with(this)
                .load(topicInfoBean.getTopicFocusImg())
                .error(R.drawable.banner_holder)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ivTopBg.setImageDrawable(resource);
                        Bitmap bitmap = ((BitmapDrawable) ivTopBg.getDrawable()).getBitmap();
                        ivTopBg1.setImageBitmap(BitmapPhjtUtils.fastblur(bitmap, 50));
                    }
                });
        if (topicInfoBean.getPublishType() == 1) {
            ivPerson.setVisibility(View.GONE);
            ivVip.setVisibility(View.GONE);
            ivMineHeadPic.setVisibility(View.GONE);
        } else {
            ivVip.setVisibility(View.VISIBLE);
            AppImageLoader.loadResUrl(topicInfoBean.getPhoto(), ivMineHeadPic);
            if (topicInfoBean.getVipState() == 1 || topicInfoBean.getVipState() == 2) {
                ivVip.setBackgroundResource(R.drawable.ic_vip);
            } else {
                ivVip.setBackgroundResource(R.drawable.ic_vip_un);
            }
        }
        if (topicInfoBean.getTopicDescribeVo().size() == 0) {
            relatLocalOne.setVisibility(View.GONE);
            relatLocalTow.setVisibility(View.GONE);
            ivLine.setVisibility(View.GONE);
        }
        if (topicInfoBean.getTopicDescribeVo().size() == 1) {
            relatLocalOne.setVisibility(View.VISIBLE);
            tvLocalOne.setText(String.format("戳此进入  %s", topicInfoBean.getTopicDescribeVo().get(0).getButtonName()));
            relatLocalTow.setVisibility(View.GONE);
        }
        if (topicInfoBean.getTopicDescribeVo().size() == 2) {
            relatLocalOne.setVisibility(View.VISIBLE);
            tvLocalOne.setText(String.format("戳此进入  %s", topicInfoBean.getTopicDescribeVo().get(0).getButtonName()));
            relatLocalTow.setVisibility(View.VISIBLE);
            tvLocalTow.setText(String.format("戳此进入  %s", topicInfoBean.getTopicDescribeVo().get(1).getButtonName()));
        }
        relatLocalOne.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                relatLoaclClick(topicInfoBean, 0);
            }
        });
        relatLocalTow.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                relatLoaclClick(topicInfoBean, 1);
            }
        });
    }

    @Override
    public void shareThemeSucceed(ShareBean shareBean) {
        ShareUtils.showSharePop(this, shareBean);
    }

    /**
     * 点击判断
     *
     * @param index: 0标识只有一条  1标识两条
     */
    public void relatLoaclClick(TopicInfoBean topicInfoBean, int index) {
        if (topicInfoBean != null) {
            List<TopicInfoBean.TopicDescribeVoBean> topicDescribeVo = topicInfoBean.getTopicDescribeVo();
            if (topicDescribeVo != null && topicDescribeVo.size() > index) {
                TopicInfoBean.TopicDescribeVoBean topicDescribeVoBean = topicDescribeVo.get(index);
                if (topicDescribeVoBean != null) {
                    String courseId = topicDescribeVoBean.getCourseId();
                    if (!TextUtils.isEmpty(courseId)) {
                        //跳转到课程页
                        Intent courseIntent = new Intent(TopicInfoActivity.this, CourseDetailActivity.class);
                        courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
                        startActivity(courseIntent);
                    } else {
                        //跳转到H5页面
                        Intent intent = new Intent(TopicInfoActivity.this, MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, topicDescribeVoBean.getButtonUrl());
                        startActivity(intent);
                    }
                }
            }
        }
    }

    @OnClick({R.id.tv_share, R.id.iv_common_back, R.id.relat_local_one, R.id.relat_local_tow, R.id.iv_join, R.id.tv_refresh, R.id.tv_edit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_share:
                if (TextUtils.equals(spUserId, mTopicInfoBean.getUserId())) {
                    if (popupWindow != null) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                        popupWindow.showAsDropDown(vpTopic);
                    }
                } else {
                    if (mPresenter != null) {
                        mPresenter.shareTheme(topicId);
                    }
                }

                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.relat_local_one:
                break;
            case R.id.relat_local_tow:
                break;
            case R.id.tv_refresh:
                getTopicDetail();
                break;
            case R.id.iv_join:
                if (freezeState == 1) {
                    TipsUtil.show("该话题已冻结");
                    return;
                }
                intent = new Intent(TopicInfoActivity.this, PublishDynamicActivity.class);
                intent.putExtra(Constant.PUSH_TOPICID, topicId);
                intent.putExtra(Constant.PUSH_TOPICNAME, tvTopictitle.getText().toString());
                startActivityForResult(intent, 1001);
                break;
            case R.id.tv_edit:

                break;
            default:
                break;
        }
    }

    /**
     * 支付成功，修改vip状态和图片
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null && eventBean.getType() == EventBean.PAY_SUCCESS) {
            String spUserId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
            if (mTopicInfoBean != null && TextUtils.equals(spUserId, mTopicInfoBean.getUserId())) {
                //自己的话题，修改vip状态
                vipStatus = 1;
                if (ivVip != null) {
                    ivVip.setBackgroundResource(R.drawable.ic_vip);
                }
            }
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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void onItemClick(int positon) {
        if (positon == 0) {
            if (vipStatus == 3) {
                DialogUtils.showVipOverdueDialog(this);
            } else if (vipStatus == 0) {
                DialogUtils.showVipBugDialog(this);
            } else if (mTopicInfoBean != null) {
                Intent intent = new Intent(this, ReleaseTopicActivity.class);
                intent.putExtra(Constant.BUNDLE_TOPIC_ID, topicId);
                intent.putExtra(Constant.BUNDLE_TOPIC_TITLE, mTopicInfoBean.getTopicName());
                intent.putExtra(Constant.BUNDLE_TOPIC_CONTENT, mTopicInfoBean.getFocusDescribe());
                intent.putExtra(Constant.BUNDLE_TOPIC_BG, mTopicInfoBean.getTopicFocusImg());
                startActivityForResult(intent, 1002);
            }
        } else {
            if (mPresenter != null) {
                mPresenter.shareTheme(topicId);
            }
        }
    }
}
