package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ArticleClassifyBean;
import com.phjt.shangxueyuan.di.component.DaggerArticleClassifyComponent;
import com.phjt.shangxueyuan.mvp.contract.ArticleClassifyContract;
import com.phjt.shangxueyuan.mvp.presenter.ArticleClassifyPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCategoryAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.ArticleListFragment;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.UmengUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:26
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ArticleClassifyActivity extends BaseActivity<ArticleClassifyPresenter> implements ArticleClassifyContract.View {

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
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.stl_category)
    SlidingTabLayout stlCategory;
    @BindView(R.id.vp_category)
    ViewPager vpCategory;
    private String couTypeId;
    private CourseCategoryAdapter categoryAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerArticleClassifyComponent
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
        if (mPresenter != null) {
            Objects.requireNonNull(mPresenter).getArticleClassify();
        }
        tvCommonTitle.setText("所有资讯");
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


    @Override
    public void getArticleClassifySuccess(List<ArticleClassifyBean> beans) {
        if (beans != null) {
            beans.add(0, new ArticleClassifyBean(couTypeId, "全部"));

            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Fragment> fragments = new ArrayList<>();

            for (int i = 0; i < beans.size(); i++) {
                ArticleClassifyBean itemBean = beans.get(i);
                if (itemBean != null) {
                    titles.add(itemBean.getClassifyName());
                    fragments.add(ArticleListFragment.newInstance(beans.get(i).getId()));
                }
            }

            categoryAdapter = new CourseCategoryAdapter(getSupportFragmentManager(), fragments, titles);
            vpCategory.setAdapter(categoryAdapter);
            stlCategory.setViewPager(vpCategory);
            vpCategory.setOffscreenPageLimit(beans.size());
            vpCategory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    if (titles.size() > i) {
                        UmengUtil.umengCount(ArticleClassifyActivity.this, ConstantUmeng.INFORMATION_CLICK_LABEL, "" + titles.get(i));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            default:
                break;
        }
    }
}
