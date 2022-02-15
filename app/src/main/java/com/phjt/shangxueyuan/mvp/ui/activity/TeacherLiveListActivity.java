package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TeacherLiveBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerTeacherLiveListComponent;
import com.phjt.shangxueyuan.mvp.contract.TeacherLiveListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.TeacherLiveListPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.LiveListTeactherAdapter;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TeacherLiveListActivity extends MZBasePusherActivity<TeacherLiveListPresenter> implements TeacherLiveListContract.View, OnRefreshListener {

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
    @BindView(R.id.rv_live)
    RecyclerView rvLive;
    @BindView(R.id.srl_live)
    SmartRefreshLayout srlLive;
    LiveListTeactherAdapter teactherAdapter;
    private View mEmptyView;
    List<TeacherLiveBean> liveBeans = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTeacherLiveListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_teacher_live_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("我的直播列表");
        tvCommonRight.setText("退出讲师端");
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color_F650C));
        tvCommonRight.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLive.setLayoutManager(layoutManager);
        teactherAdapter = new LiveListTeactherAdapter(this, liveBeans);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        teactherAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        rvLive.setAdapter(teactherAdapter);

        teactherAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TeacherLiveBean teacherLiveBean = (TeacherLiveBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_rate_of_learning:
                    case R.id.relat_icon:
                    case R.id.tv_audition_title_item:
                    case R.id.tv_live_time:
                    case R.id.iv_people:
                    case R.id.tv_live_commpany:
                    case R.id.tv_study_people_item:
                        if (teacherLiveBean.getStatus()==0){
                            DialogUtils.showOpenLiveDialog(TeacherLiveListActivity.this, teacherLiveBean.getLiveStyle(), new DialogUtils.OnCancelSureClick() {
                                @Override
                                public void clickSure() {
                                    CommonHttpManager.getInstance(TeacherLiveListActivity.this)
                                            .obtainRetrofitService(ApiService.class)
                                            .updateLiveState(teacherLiveBean.getChannelId() + "", "1", teacherLiveBean.getId() + "", teacherLiveBean.getTicketId() + "")
                                            .compose(RxUtils.applySchedulers())
                                            .subscribe(baseBean -> {
                                                if (baseBean.isOk()) {
                                                    if (mPresenter != null) {
                                                        mPresenter.getTeacherLiveList();
                                                    }
                                                    startPush(teacherLiveBean.getChannelId(), teacherLiveBean.getTicketId() + "", teacherLiveBean.getLiveToken(), teacherLiveBean.getId(), teacherLiveBean.getLiveStyle());
                                                } else {
                                                    Toast.makeText(TeacherLiveListActivity.this, baseBean.msg, Toast.LENGTH_SHORT).show();
                                                }
                                            }, throwable -> LogUtils.e("网络异常"));
                                }
                            });
                        }else {
                            DialogUtils.showOpenLiveDialog(TeacherLiveListActivity.this, teacherLiveBean.getLiveStyle(), new DialogUtils.OnCancelSureClick() {
                                @Override
                                public void clickSure() {
                                    if (mPresenter != null) {
                                        mPresenter.getTeacherLiveList();
                                    }
                                    startPush(teacherLiveBean.getChannelId(), teacherLiveBean.getTicketId() + "", teacherLiveBean.getLiveToken(), teacherLiveBean.getId(), teacherLiveBean.getLiveStyle());
                                }
                            });
                        }

                        break;
                    default:
                        break;
                }
            }
        });
        srlLive.setOnRefreshListener(this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_common_left, R.id.iv_common_back, R.id.tv_common_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_common_left:
                finish();
                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_common_right:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getTeacherLiveList();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.getTeacherLiveList();
        }
    }

    @Override
    public void getTeacherLiveListSuccess(List<TeacherLiveBean> itemBeanList) {
        srlLive.finishRefresh();
        if (itemBeanList.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        liveBeans = itemBeanList;
        teactherAdapter.setNewData(itemBeanList);
    }
}
