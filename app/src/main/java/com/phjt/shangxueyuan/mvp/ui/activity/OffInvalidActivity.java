package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/9/1
 * company: 普华集团
 * description: 失效的页面
 */
public class OffInvalidActivity extends BaseActivity {

    @BindView(R.id.tv_find_more)
    TextView mTvFindMore;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.course_off_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_off_back, R.id.tv_find_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_off_back:
            case R.id.tv_find_more:
                finish();
                break;
            default:
                break;
        }
    }
}