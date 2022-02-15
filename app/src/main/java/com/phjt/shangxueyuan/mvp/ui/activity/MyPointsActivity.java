package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gyf.immersionbar.ImmersionBar;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.TaskCurrencySecondBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerMyPointsComponent;
import com.phjt.shangxueyuan.mvp.contract.MyPointsContract;
import com.phjt.shangxueyuan.mvp.presenter.MyPointsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TaskCurrencyAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.MallHomeFragment;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.fragment.MainFragment.getIdByType;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_TITLE;
import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_WEB_URL;
import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;

/**
 * Created by Template
 *
 * @author :
 * description :我的学豆
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */
public class MyPointsActivity extends BaseActivity<MyPointsPresenter> implements MyPointsContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_exchange_now)
    TextView tvExchangeNow;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.iv_bocc_detail)
    ImageView ivBoccDetail;
    @BindView(R.id.rv_points_detail)
    RecyclerView rvPointsDetail;
    @BindView(R.id.tv_currency)
    TextView mTvCurrency;
    @BindView(R.id.tv_bocc)
    TextView mTvBocc;

    private TaskCurrencyAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyPointsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_points;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(MyPointsActivity.this).fitsSystemWindows(true)
                .statusBarColor(R.color.color_3B8CFA)
                .statusBarDarkFont(true)
                .init();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyPointsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvPointsDetail.setLayoutManager(layoutManager);
        adapter = new TaskCurrencyAdapter(this, null);
        rvPointsDetail.setAdapter(adapter);

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_points) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == TaskCurrencyAdapter.TYPE_TASK_SECOND && view.getId() == R.id.tv_points) {
                    TaskCurrencySecondBean secondBean = (TaskCurrencySecondBean) adapter.getData().get(position);
                    Integer integralType = secondBean.getIntegralType();
                    //integralType 课程评论 1   公开笔记 2   圈子动态 3   专题留言 4   被点赞 5   被置顶 6   被转发 7   实名认证奖励 30
                    // 邀请好友激活 31  邀请好友实名认证32   分享课程 49   分享直播 50   分享邀请有礼海报 51   分享圈子或话题 52
                    // 被邀请人消费 48 购买会员卡 64 付费考试 63 付费训练营 65  直播评论53 日历打卡 54 提交作业 55 观看一节视频 56
                    // 观看直播 57   获得《BOC商科》证书58      获得《新营销架构师》证书 59     获得《信息化运营官》证书60
                    if (secondBean.getFinishFlag() == 3 || secondBean.getFinishFlag() == 1) {
                        return;
                    }
                    if (integralType == 1 || integralType == 2 || integralType == 49 || integralType == 54 || integralType == 55 || integralType == 56) {
                        //尝鲜课
                        Intent intent = new Intent(MyPointsActivity.this, CourseCategoryActivity.class);
                        intent.putExtra(Constant.COURSE_TYPE_ID, SPUtils.getInstance().getString(Constant.SP_PRACTICE_ID));
                        startActivity(intent);

                    } else if (integralType == 3 || integralType == 52) {
                        // 圈子
                        EventBus.getDefault().post(new EventBean(EventBean.PUSH_CIRCLE, ""));
                        Intent intent = new Intent(this, HomePagerActivity.class);
                        startActivity(intent);

                    } else if (integralType == 4) {
                        //专题留言
                        EventBus.getDefault().post(new EventBean(EventBean.PUSH_HOME, ""));
                        Intent intent = new Intent(this, HomePagerActivity.class);
                        startActivity(intent);
                    } else if (integralType == 63) {
                        //考试系统
                        Intent intent = new Intent(this, MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "考试系统");
                        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_EXAM));
                        startActivity(intent);
                    } else if (integralType == 64) {
                        //购买会员
                        VipUtil.toVipPage(this);
                    } else if (integralType == 65) {
                        //26学习营
                        Intent intent = new Intent(this, AllStudyCampActivity.class);
                        startActivity(intent);
                    } else if (integralType == 30) {
                        //实名认证
                        Intent intent = new Intent(this, AuthenticationActivity.class);
                        startActivity(intent);
                    } else if (integralType == 31 || integralType == 32 || integralType == 51) {
                        //邀请有礼
                        if (null != mPresenter) {
                            mPresenter.inviteShareT();
                        }
                    } else if (integralType == 50 || integralType == 53 || integralType == 57) {
                        // 直播列表
                        Intent intent = new Intent(this, CourseCategoryActivity.class);
                        intent.putExtra(Constant.COURSE_TYPE_ID, getIdByType(9));
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getUserAssetsInfo();
            mPresenter.getNewTaskList();
        }
    }


    @Override
    public void showUserAssets(UserAssetsBean assetsBean) {
        mTvCurrency.setText(String_Utils.linearNmber(assetsBean.getUserStudyCoin()));
        mTvBocc.setText(String_Utils.linearNmber(assetsBean.getUserBocc()));
    }

    @Override
    public void loadTaskLisSuccess(List<TaskCurrencyFirstBean> firstBeanList) {
        ArrayList<MultiItemEntity> multiList = new ArrayList<>();
        for (int i = 0; i < firstBeanList.size(); i++) {
            TaskCurrencyFirstBean currencyFirstBean = firstBeanList.get(i);
            if (currencyFirstBean != null && currencyFirstBean.getTaskList() != null) {
                List<TaskCurrencySecondBean> secondBeanList = currencyFirstBean.getTaskList();
                for (int j = 0; j < secondBeanList.size(); j++) {
                    TaskCurrencySecondBean currencySecondBean = secondBeanList.get(j);
                    currencySecondBean.setType(currencyFirstBean.getType());
                    currencyFirstBean.addSubItem(currencySecondBean);
                }
            }
            multiList.add(currencyFirstBean);
        }
        adapter.setNewData(multiList);
        adapter.expandAll();
    }

    @Override
    public void loadInviteSharetSuccess(String data) {
        if (mPresenter != null) {
            mPresenter.inviteShare(data);
        }
    }

    @Override
    public void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data, String url) {
        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        Intent intent = new Intent(this, ShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        intent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHAREWX)
                + "?code=" + url + "&mobile=" + phoneNumber);
        startActivity(intent);
    }

    @OnClick({R.id.iv_points_detail, R.id.iv_points_ranking_list, R.id.iv_bocc_detail, R.id.iv_common_back,
            R.id.tv_common_title, R.id.tv_exchange_now, R.id.tv_currency_mall, R.id.tv_common_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_common_title:
                Intent intents = new Intent(MyPointsActivity.this, MyWebViewActivity.class);
                intents.putExtra(BUNDLE_WEB_TITLE, "积分规则");
                intents.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_INTEGRATIONRULE));
                startActivity(intents);
                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_points_detail:
                //积分明细
                Intent intent = new Intent(MyPointsActivity.this, PointsDetailsActivity.class);
                intent.putExtra(Constant.USER_INTEGRAL, mTvCurrency.getText().toString());
                intent.putExtra("detail_type", "学豆");
                startActivity(intent);
                break;
            case R.id.iv_bocc_detail:
                //BOCC明细
                Intent intentt = new Intent(MyPointsActivity.this, PointsDetailsActivity.class);
                intentt.putExtra(Constant.USER_INTEGRAL, mTvBocc.getText().toString());
                intentt.putExtra("detail_type", "bocc");
                startActivity(intentt);
                break;
            case R.id.iv_points_ranking_list:
                //积分排行榜
                Intent intent2 = new Intent(MyPointsActivity.this, RankingListsActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_exchange_now:
                //学豆兑换BOCC
                Intent exchangeIntent = new Intent(this, MyWebViewActivity.class);
                exchangeIntent.putExtra(BUNDLE_WEB_TITLE, "");
                exchangeIntent.putExtra(BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CURRENCY_EXCHANG_BOCC) + "?type=1");
                startActivity(exchangeIntent);
//                showMessage("暂未开放兑换");
                break;
            case R.id.tv_currency_mall:
                //学豆商城
                Intent mallIntent = new Intent(this, MallHomeFragment.class);
                startActivity(mallIntent);
                break;
            case R.id.tv_common_right:
                //充值学豆
                Intent rechargeIntent = new Intent(this, CurrencyRechargeActivity.class);
                startActivity(rechargeIntent);
                break;
            default:
                break;
        }
    }

    private void showQiitongDialog() {
        DialogUtils.showQiitongDialog(MyPointsActivity.this, new DialogUtils.OnClickDialogListener() {
            @Override
            public void clickCancel(boolean selected) {
                if (selected) {
                    SPUtils.getInstance().put(Constant.SP_COUNT_DOWN_DAY, TimeUtils.countDownDay());
                }
            }

            @Override
            public void clickOk(boolean selected) {
                if (selected) {
                    SPUtils.getInstance().put(Constant.SP_COUNT_DOWN_DAY, TimeUtils.countDownDay());
                    goQiitong();
                } else {
                    goQiitong();
                }
            }
        });
    }

    public void goQiitong() {
        Intent intent = new Intent(MyPointsActivity.this, QiitongWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
        startActivity(intent);
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
