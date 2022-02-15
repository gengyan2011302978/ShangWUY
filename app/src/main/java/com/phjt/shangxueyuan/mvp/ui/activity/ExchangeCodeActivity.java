package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.event.ExchangeCode;
import com.phjt.shangxueyuan.di.component.DaggerExchangeCodeComponent;
import com.phjt.shangxueyuan.mvp.contract.ExchangeCodeContract;
import com.phjt.shangxueyuan.mvp.presenter.ExchangeCodePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyDownloadAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.CourseExchangeFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.VipExchangeFragment;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


public class ExchangeCodeActivity extends BaseActivity<ExchangeCodePresenter> implements ExchangeCodeContract.View {

    @BindView(R.id.stl_exchange)
    SlidingTabLayout stlExchange;
    @BindView(R.id.vp_exchange)
    ViewPager vpExchange;
    CourseExchangeFragment courseExchangeFragment;
    CourseExchangeFragment trainingFragment;
    VipExchangeFragment vipExchangeFragment;
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
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.et_exchange)
    EditText etExchange;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExchangeCodeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exchange_code;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("兑换码");
        String[] titles = {"课程", "会员", "训练营"};
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> fragments = new ArrayList<>();
        courseExchangeFragment = CourseExchangeFragment.newInstance(1);
        vipExchangeFragment = VipExchangeFragment.newInstance();
        trainingFragment = CourseExchangeFragment.newInstance(3);
        fragments.add(courseExchangeFragment);
        fragments.add(vipExchangeFragment);
        fragments.add(trainingFragment);
        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(fragmentManager, fragments);
        vpExchange.setAdapter(myDownloadAdapter);
        vpExchange.setCurrentItem(0);
        vpExchange.setOffscreenPageLimit(2);
        stlExchange.setViewPager(vpExchange, titles);
        etExchange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etExchange.getText().toString().length() > 0) {
                    tvExchange.setBackgroundResource(R.drawable.bg_4c7cfb_rectangle);
                } else {
                    tvExchange.setBackgroundResource(R.drawable.bg_c2c2c2_rectangle);
                }
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
    protected void onResume() {
        super.onResume();
        EventBusManager.getInstance().post(new ExchangeCode());
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

    @OnClick({R.id.iv_common_back, R.id.tv_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_exchange:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etExchange.getWindowToken(), 0);
                if (etExchange.getText().toString().equals("")) {
                    TipsUtil.show("请输入兑换码");
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.toChange(etExchange.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void toChangeSuccess(BaseBean msg) {
        if (msg.data.equals("1")) {
            vpExchange.setCurrentItem(1);
        } else if (msg.data.equals("2")) {
            vpExchange.setCurrentItem(0);
        } else if (msg.data.equals("3")) {
            vpExchange.setCurrentItem(2);
        }

        TipsUtil.show("兑换成功");
        EventBusManager.getInstance().post(new ExchangeCode());
    }

    @Override
    public void toChangefaile(String msg) {
        TipsUtil.show(msg);
    }
}
