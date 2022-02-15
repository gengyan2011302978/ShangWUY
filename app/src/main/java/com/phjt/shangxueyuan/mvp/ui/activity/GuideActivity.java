package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.widgets.ResizableImageView;
import com.phsxy.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: Created by shaopengfei
 * date: 2020/5/9 10:07
 * company: 普华集团
 * description: 引导页
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, INotFitsImmersionBar {

    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.iv_guide_enter)
    ImageView mTvGuideEnter;
    @BindView(R.id.rll_guide_oval)
    LinearLayout rllGuideOval;


    private int[] imgIds = {R.drawable.guide_feature_1, R.drawable.guide_feature_2,
            R.drawable.guide_feature_3, R.drawable.guide_feature_4, R.drawable.guide_feature_5};
    private List<View> mImageViews = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guide;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        for (int anImgId : imgIds) {
            ResizableImageView imageView = new ResizableImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(anImgId);
            mImageViews.add(imageView);
        }

        GuideAdapter adapter = new GuideAdapter();
        mVpGuide.setAdapter(adapter);
        mVpGuide.setOffscreenPageLimit(4);
        mVpGuide.addOnPageChangeListener(this);
        initGuideOval();
    }

    /**
     * 跳过/ 立刻体验
     */
    public void onEnterClick(View view) {
        SPUtils.getInstance().put(Constant.GO_GUIDE, "guide");
        Intent intent;
        String token = SPUtils.getInstance().getString(Constant.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, HomePagerActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (i == imgIds.length - 1) {
            mTvGuideEnter.setVisibility(View.VISIBLE);
            rllGuideOval.setVisibility(View.INVISIBLE);
        } else {
            mTvGuideEnter.setVisibility(View.INVISIBLE);
            rllGuideOval.setVisibility(View.VISIBLE);
        }
    }

    private void initGuideOval() {
        for (int j = 0; j < imgIds.length; j++) {
            View view = View.inflate(this, R.layout.item_guide, null);
            if (j == 0) {
                view.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_oval_bg_select));
            } else {
                view.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_oval_bg_unselect));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AutoSizeUtils.dp2px(this, 15), AutoSizeUtils.dp2px(this, 5));
            params.setMargins(0, 0, AutoSizeUtils.dp2px(this, 10), 0);
            rllGuideOval.addView(view, params);
        }
    }

    @Override
    public void onPageSelected(int i) {
        onGuideOvalSelect(i);
    }

    private void onGuideOvalSelect(int select) {
        for (int i2 = 0; i2 < rllGuideOval.getChildCount(); i2++) {
            View childAt = rllGuideOval.getChildAt(i2);
            if (i2 == select) {
                childAt.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_oval_bg_select));
            } else {
                childAt.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_oval_bg_unselect));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgIds.length;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mImageViews.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

    }
}
