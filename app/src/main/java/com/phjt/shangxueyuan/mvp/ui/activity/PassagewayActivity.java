package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: Created by GengYan
 * date: 04/02/2020 18:58
 * company: 普华集团
 * description: 模版请保持更新
 */
public class PassagewayActivity extends BaseActivity {

    @BindView(R.id.frame_count)
    FrameLayout frameCount;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;

    boolean isload = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_passageway;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String name = getIntent().getStringExtra("name");
//        if (TextUtils.isEmpty(name)) {
//            auditionFragment = AuditionFragment.newInstance(getIntent().getIntExtra("type", 1));
//        } else {
//            auditionFragment = AuditionFragment.newInstance(getIntent().getIntExtra("type", 1), name);
//        }
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.frame_count, auditionFragment);
//        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isload) {
            isload = false;
        }
    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
            default:
                break;
        }
    }
}
