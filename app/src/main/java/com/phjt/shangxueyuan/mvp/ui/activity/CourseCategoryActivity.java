package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.bean.CourseCategoryBean;
import com.phjt.shangxueyuan.bean.CourseCategoryItemBean;
import com.phjt.shangxueyuan.bean.OnDemandBean;
import com.phjt.shangxueyuan.bean.SuspendImgBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerCourseCategoryComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseCategoryContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseCategoryPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCategoryAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.AuditionFragment;
import com.phjt.shangxueyuan.utils.BannerUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.SPUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 课程类别列表
 */
public class CourseCategoryActivity extends BaseActivity<CourseCategoryPresenter> implements CourseCategoryContract.View {

    @BindView(R.id.tv_common_title)
    TextView mTvTitle;
    @BindView(R.id.stl_category)
    SlidingTabLayout mStl;
    @BindView(R.id.vp_category)
    ViewPager mVpCategory;
    @BindView(R.id.no_network_layout)
    View mNoNetworkLayout;
    @BindView(R.id.iv_right_bottom)
    ImageView mIvRightBottom;
    @BindView(R.id.iv_right_top)
    ImageView mIvRightTop;

    /**
     * 栏目一级分类id
     */
    private String couTypeId;
    /**
     * 栏目二级分类id，定位使用
     */
    private String secondId;

    /**
     * 右下角 跳转链接
     */
    private String mOnDemandUrl;
    private SuspendImgBean suspendImgBean;
    private boolean isShowSuspendIm = false;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCourseCategoryComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_course_category;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        couTypeId = intent.getStringExtra(Constant.COURSE_TYPE_ID);
        secondId = intent.getStringExtra(Constant.COURSE_TYPE_SECOND_ID);
        if (mPresenter != null) {
            mPresenter.getCourseCategory(couTypeId);
            //显示 直播回放按钮
            String liveListId = SPUtils.getInstance().getString(Constant.SP_LIVE_LIST_ID);
            if (TextUtils.equals(couTypeId, liveListId)) {
                mPresenter.getOnDemandBean();
            } else {
                mPresenter.getSuspendImg();
            }
        }

        mVpCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (0 == i) {
                    if (!isShowSuspendIm) {
                        mIvRightTop.setVisibility(View.VISIBLE);
                    }
                } else {
                    mIvRightTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void getCourseCategory(CourseCategoryBean categoryBean) {
        String categoryName = categoryBean.getName();
        mTvTitle.setText(categoryName);
        String description = categoryBean.getDescription();
        List<CourseCategoryItemBean> categoryItemList = categoryBean.getChildList();
        if (categoryItemList != null) {
            int site = 0;
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Fragment> fragments = new ArrayList<>();
            categoryItemList.add(0, new CourseCategoryItemBean(couTypeId, "全部", 1));
            for (int i = 0; i < categoryItemList.size(); i++) {
                CourseCategoryItemBean itemBean = categoryItemList.get(i);
                if (itemBean != null) {
                    String itemName = itemBean.getName();
                    if (!TextUtils.isEmpty(categoryName) && !TextUtils.isEmpty(itemName)) {
                        if (itemName.contains("全部")) {
                            if (categoryName.contains("初级课程")) {
                                description = "【初级课程】是考取BOC初级证书的必学内容，包含10个课程体系，是对考试体系九大模块基础实操的课程制动，欢迎加入学习！";
                            } else if (categoryName.contains("中级课程")) {
                                description = "";
                            } else if (categoryName.contains("高级课程")) {
                                description = "";
                            }
                        } else if (itemName.contains("主修课")) {
                            description = "【主修课】主修课涵盖企业运营九大模块的理论知识，是企业扭转乾坤的珍藏宝典。按照BOC商科证书初级资格的要求，由企业运营九大模块的基础教学大纲编撰而成！";
                        } else if (itemName.contains("辅修课")) {

                            description = "【辅修课】辅修课是企业运营九大模块的辅助课程。针对主课程学习有余力的学员，通过修读BOC商科证书同层次的其他专业课，来加固九大模块的基础知识与落地应用！";
                        } else if (itemName.contains("参考课")) {
                            description = "【参考课】参考课是企业运营九大模块的参考拓展。在学员掌握初级主修、辅修课程后，通过多层次、多角度的参考课程学习，拓展学员对于九大模块的发散思维，全面掌握BOC商科证书的教学真谛！";
                        }
                    }

                    titles.add(itemName);
                    fragments.add(AuditionFragment.newInstance(couTypeId, itemBean.getLevel(), itemBean.getId(),
                            categoryName + itemName, description));
                    //设置当前课程分类的位置
                    if (TextUtils.equals(secondId, itemBean.getId())) {
                        site = i;
                    }
                }
            }

            CourseCategoryAdapter categoryAdapter = new CourseCategoryAdapter(getSupportFragmentManager(), fragments, titles);
            mVpCategory.setAdapter(categoryAdapter);
            mStl.setViewPager(mVpCategory);
            mVpCategory.setOffscreenPageLimit(titles.size());
            mVpCategory.setCurrentItem(site);
        }
    }

    /**
     * 不显示浮窗
     */
    public void noIsShowImg() {
        isShowSuspendIm = true;
        mIvRightTop.setVisibility(View.GONE);
    }

    @Override
    public void isShowNoNetworkView(boolean isShow) {
        if (mNoNetworkLayout != null) {
            mNoNetworkLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showOnDemandInfo(OnDemandBean onDemandBean) {
        mIvRightBottom.setVisibility(View.VISIBLE);
        mIvRightTop.setVisibility(View.GONE);
        mOnDemandUrl = onDemandBean.getLiveExclusiveUrl();
        String liveExclusiveImg = onDemandBean.getLiveExclusiveImg();
        AppImageLoader.loadResUrl(liveExclusiveImg, mIvRightBottom);
    }

    @Override
    public void showSuspendImgInfo(SuspendImgBean bean) {
        suspendImgBean = bean;
        if (suspendImgBean != null && !isShowSuspendIm) {
            mIvRightBottom.setVisibility(View.GONE);
            mIvRightTop.setVisibility(View.VISIBLE);
            AppImageLoader.loadResUrl(bean.getCoverUrl(), mIvRightTop);
        }
    }

    @OnClick({R.id.iv_common_back, R.id.tv_refresh, R.id.iv_right_bottom, R.id.iv_right_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_refresh:
                if (mPresenter != null) {
                    mPresenter.getCourseCategory(couTypeId);
                }
                break;
            case R.id.iv_right_bottom:
                if (!TextUtils.isEmpty(mOnDemandUrl)) {
                    Intent intent = new Intent(this, MyWebViewActivity.class);
                    intent.putExtra(Constant.BUNDLE_WEB_URL, mOnDemandUrl);
                    startActivity(intent);
                }
                break;
            case R.id.iv_right_top:
                if (suspendImgBean != null) {
                    BannerUtils.suspendImgClick(this, suspendImgBean, null);
                }
                break;
            default:
                break;
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
