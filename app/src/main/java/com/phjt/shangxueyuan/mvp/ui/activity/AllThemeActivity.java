package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MotifBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerAllThemeComponent;
import com.phjt.shangxueyuan.mvp.contract.AllThemeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.AllThemePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ThemeListAdapter;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.Constant.PUNCH_CARDS_COURSE_ID;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 16:23
 * company: 普华集团
 * description: 模版请保持更新
 */
public class AllThemeActivity extends BaseActivity<AllThemePresenter> implements AllThemeContract.View {

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
    @BindView(R.id.recycle_theme)
    RecyclerView recycleTheme;
    ThemeListAdapter themeListAdapter;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAllThemeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_all_theme;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("全部主题");
        icCommonRight.setBackgroundResource(R.drawable.icon_share_right);
        icCommonRight.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleTheme.setLayoutManager(layoutManager);
        themeListAdapter = new ThemeListAdapter(this);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        themeListAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        recycleTheme.setAdapter(themeListAdapter);
        if (mPresenter != null) {
            mPresenter.motifList(getIntent().getStringExtra("id"));
        }
        themeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MotifBean motifBean = (MotifBean) adapter.getData().get(position);
                Intent intent = new Intent(AllThemeActivity.this, HistoryThemeActivity.class);
                intent.putExtra("id", String.valueOf(motifBean.getId()));
                intent.putExtra("punchCardId", String.valueOf(motifBean.getPunchCardId()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void motifListSucceed(List<MotifBean> themeBean) {
        themeListAdapter.setNewData(themeBean);
    }

    @Override
    public void motifListFail() {

    }

    @OnClick({R.id.iv_common_back, R.id.ic_common_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.ic_common_right:
                CommonHttpManager.getInstance(this)
                        .obtainRetrofitService(ApiService.class)
                        .diaryShare(getIntent().getStringExtra("id"), getIntent().getStringExtra(PUNCH_CARDS_COURSE_ID), getIntent().getStringExtra("trainingId"), getIntent().getStringExtra("diaryId"))
                        .compose(RxUtils.applySchedulersWithLoading(this))
                        .subscribe(shareBaseBean -> {
                            if (shareBaseBean.isOk()) {
                                ShareUtils.showSharePop(AllThemeActivity.this, shareBaseBean.data);
                            }
                        }, throwable -> TipsUtil.showTips("获取分享内容有误"));
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
        checkNotNull(message);
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
