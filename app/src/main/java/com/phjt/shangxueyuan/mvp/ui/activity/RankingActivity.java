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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.RankBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerRankingComponent;
import com.phjt.shangxueyuan.mvp.contract.RankingContract;
import com.phjt.shangxueyuan.mvp.presenter.RankingPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.RankListAdapter;
import com.phjt.view.roundImg.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/29/2021 13:57
 * company: 普华集团
 * description: 模版请保持更新
 */
public class RankingActivity extends BaseActivity<RankingPresenter> implements RankingContract.View {

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
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.iv_course)
    RoundedImageView ivCourse;
    @BindView(R.id.tv_join_number)
    TextView tvJoinNumber;
    @BindView(R.id.tv_clock_number)
    TextView tvClockNumber;
    @BindView(R.id.recycle_rank)
    RecyclerView recycleRank;
    RankListAdapter rankListAdapter;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_course_name)
    TextView tvCourseName;
    @BindView(R.id.linear_empty)
    LinearLayout linearEmpty;
    @BindView(R.id.relat_finish)
    RelativeLayout relatFinish;
    private View mEmptyView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRankingComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ranking;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("排行榜");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleRank.setLayoutManager(layoutManager);
        rankListAdapter = new RankListAdapter(this);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        rankListAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        recycleRank.setAdapter(rankListAdapter);
        if (mPresenter != null) {
            mPresenter.rankingList(getIntent().getStringExtra("id"));
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
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @Override
    public void rankingListSucceed(RankBean rankBean) {
        if (null == rankBean) {
            linearEmpty.setVisibility(View.VISIBLE);
        }
        if (rankBean.getUserVos().size()==0) {
            linearEmpty.setVisibility(View.VISIBLE);
        }
        tvJoinNumber.setText(rankBean.getPartyUser()+"人参与");
        tvClockNumber.setText(rankBean.getPartyNum()+"次打卡");
        rankListAdapter.setNewData(rankBean.getUserVos());
        if (null!=rankBean.getRankingNum()){
            tvDesc.setText("No."+rankBean.getRankingNum());
        }else {
            tvDesc.setVisibility(View.INVISIBLE);
        }
        tvName.setText(rankBean.getNickName());
        tvCourseName.setText(rankBean.getPunchCardName());
        AppImageLoader.loadResUrl(rankBean.getCoverImg(), ivCourse);
    }

    @Override
    public void rankingListFail() {
        linearEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_common_back, R.id.relat_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.relat_finish:
                finish();
                break;
        }
    }
}
