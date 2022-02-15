package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.AppLifecyclesImpl;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.RulesConfigBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.di.component.DaggerMineComponent;
import com.phjt.shangxueyuan.mvp.contract.MineContract;
import com.phjt.shangxueyuan.mvp.presenter.MinePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.AgentActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.BasicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.DiscipleGroupActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ExchangeVoucherActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.FeedbackActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyClockInActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyCollectionActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyCoursesActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyDowloadActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyExerciseActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyNotesActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyPointsActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyTopicActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWalletActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.QiitongWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ScannerActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.SetUpActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ShareActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TeacherLiveListActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ViewRecordActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.WjjAuthenticationActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MineAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.roundImg.RoundedImageView;
import com.phsxy.utils.SPUtils;
import com.phsxy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Roy
 * date:    2020/03/27
 * company: 普华集团
 * description: 我的页面
 */
public class MineFragment extends BaseLazyLoadFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icSweep;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.iv_mine_head_pic)
    RoundedImageView ivMineHeadPic;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_become_due)
    TextView tvMineBecomeDue;
    @BindView(R.id.tv_mine_integral)
    TextView tIntegral;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.iv_open_boc)
    ImageView ivOpenBoc;
    @BindView(R.id.cl_head)
    ConstraintLayout clHead;
    @BindView(R.id.cl_share_politeness)
    ConstraintLayout constraintLayout;
    @BindView(R.id.rv_study)
    RecyclerView rvStudy;
    @BindView(R.id.rv_service)
    RecyclerView rvService;
    @BindView(R.id.iv_right_bottom)
    ImageView ivRightBottom;

    private String url = "";
    private List<MineBean> studyList;
    private List<MineBean> serviceList;
    private int mineWjj = 0;
    private int mineDiscipleGroup = 0;
    private String code = "";

    /**
     * vipState 0 :普通用户  1:vip 用户  2 :永久vip  3 vip 已过期
     */
    private int vipState;
    /**
     * v2.4.1 版本 启富通是否开启新的版本
     */
    private int mFresh = 0;
    private boolean isName;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivCommonBack.setVisibility(View.VISIBLE);
        icSweep.setVisibility(View.VISIBLE);
        ivCommonBack.setImageResource(R.drawable.customer_service_icon);
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setImageResource(R.drawable.ic_news);
        tvCommonTitle.setText("我的");

        studyList = new ArrayList<>();
        serviceList = new ArrayList<>();


    }

    @Override
    public void lazyLoadData() {
        initGridData();
        //是否显示弟子群
        if (mPresenter != null) {
            //弟子群认证状态开关
            code = "DZQ_AUTH_SWITCH";
            mPresenter.isShowDiscipleGroup(code);
            mPresenter.mShareRules("1");
        }

        initAdapter(rvStudy, studyList);
        initAdapter(rvService, serviceList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (constraintLayout != null) {
            constraintLayout.setClickable(true);
        }
        if (mPresenter != null) {
            mPresenter.getUserInfo();
            mPresenter.getUserIntegral();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                mPresenter.isShowQftPointFlag();
            }
        }
    }


    /**
     * 初始化Adapter
     */
    private void initAdapter(RecyclerView recyclerViews, List<MineBean> list) {
        recyclerViews.setNestedScrollingEnabled(false);
        recyclerViews.setVerticalScrollBarEnabled(true);
        recyclerViews.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerViews.setLayoutManager(gridLayoutManager);

        MineAdapter adapter = new MineAdapter(getActivity());
        recyclerViews.setAdapter(adapter);
        adapter.setNewData(list);
        setItemClick(adapter);
        tvMineBecomeDue.setEnabled(true);
    }

    /**
     * Adapter条目Click
     */
    private void setItemClick(MineAdapter adapter) {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            MineBean mineBean = (MineBean) adapter1.getData().get(position);
            Intent intent = null;
            if (mineBean.getName().contains("考试系统")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "考试系统");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_EXAM));
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_EXAM_SYSTEM);
            } else if (mineBean.getName().contains("我的证书")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "我的证书");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_OTHER) + "?num=3");
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_ORDER);
            } else if (mineBean.getName().contains("我的收藏")) {
                intent = new Intent(getActivity(), MyCollectionActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_COLLECTION);
            } else if (mineBean.getName().contains("学习记录")) {
                intent = new Intent(getActivity(), ViewRecordActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_STUDY);
            } else if (mineBean.getName().contains("我的下载")) {
                intent = new Intent(getActivity(), MyDowloadActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_STUDY);
            } else if (mineBean.getName().contains("我的笔记")) {
                intent = new Intent(getActivity(), MyNotesActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_NOTES);
            } else if (mineBean.getName().contains("联系班主任")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_SETTINGS_ABOUT_US);
            } else if (mineBean.getName().contains("基本信息")) {
                intent = new Intent(getActivity(), BasicInfoActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_INFORMATION);
            } else if (mineBean.getName().contains("考生信息")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "考生信息");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_EXAMINFO) + "?status=1");
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_EXAM_FORMATION);
            } else if (mineBean.getName().contains("我的订单")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "我的订单");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PHYDSTATIC_EXAMSYSTEM_OTHER) + "?num=2");
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_MY_ORDER);
            } else if (mineBean.getName().contains("邀请记录")) {
                intent = new Intent(getActivity(), MyWalletActivity.class);
            } else if (mineBean.getName().contains("挖掘机认证")) {
                if (mineWjj == 0) {
                    startActivity(new Intent(getContext(), WjjAuthenticationActivity.class));
                    UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_WJJ);
                } else {
                    ToastUtils.show("已认证");
                }
            } else if (mineBean.getName().contains("弟子群认证")) {
                if (mineDiscipleGroup == 0) {
                    startActivity(new Intent(getContext(), DiscipleGroupActivity.class));
                } else {
                    ToastUtils.show("已认证");
                }
            } else if (mineBean.getName().contains("帮助中心")) {
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "帮助中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_ISSUE));
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_HELP_CENTER);
            } else if (mineBean.getName().contains("意见反馈")) {
                startActivity(new Intent(getContext(), FeedbackActivity.class));
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_FEEDBACK);
            } else if (mineBean.getName().contains("设置")) {
                intent = new Intent(getActivity(), SetUpActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_SETTINGS);
            } else if (mineBean.getName().contains("我的话题")) {
                intent = new Intent(getActivity(), MyTopicActivity.class);
            } else if (mineBean.getName().contains("我的课程")) {
                intent = new Intent(getActivity(), MyCoursesActivity.class);
            } else if (mineBean.getName().contains("打卡")) {
                intent = new Intent(getActivity(), MyClockInActivity.class);
            } else if (mineBean.getName().contains("作业本")) {
                intent = new Intent(getActivity(), MyExerciseActivity.class);
            } else if (mineBean.getName().contains("我的通证")) {
                String strToday = TimeUtils.getStrToday();
                String strCountDownDay = SPUtils.getInstance().getString(Constant.SP_COUNT_DOWN_DAY);
                if (TextUtils.isEmpty(strCountDownDay) || strToday.equals(strCountDownDay)) {
                    showQiitongDialog();
                } else {
                    goQiitong();
                }
            } else if (mineBean.getName().contains("商城")) {
                intent = new Intent(mContext, MallHomeFragment.class);
            } else if (mineBean.getName().contains("代理商")) {
                intent = new Intent(mContext, AgentActivity.class);
            }
            if (intent != null) {
                startActivity(intent);
            }
        });
    }


    @Override
    public void loadDataSuccess(UserInfoBean beans) {
        if (beans.getIsTeacher() == 1) {
            ivRightBottom.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(beans.getNickName())) {
            String phoneNumber = beans.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            tvMineName.setText(phoneNumber);
        } else {
            tvMineName.setText(beans.getNickName());
        }

        if (!TextUtils.isEmpty(beans.getPlatform())) {
            setPlatform(beans.getPlatform());
        } else {
            mineWjj = 0;
            mineDiscipleGroup = 0;
        }
        int failImage = R.drawable.iv_mine_avatar;
        if (!TextUtils.isEmpty(beans.getPhoto())) {
            GlideUtils.load(beans.getPhoto(), ivMineHeadPic, failImage);
            SPUtils.getInstance().put(Constant.SP_AVATAR, beans.getPhoto());
        } else {
            GlideUtils.load(beans.getWxPhoto(), ivMineHeadPic, failImage);
            SPUtils.getInstance().put(Constant.SP_AVATAR, beans.getWxPhoto());
        }

        //保存用户信息
        SPUtils.getInstance().put(Constant.SP_MOBILE, beans.getMobile());
        SPUtils.getInstance().put(Constant.SP_USER_ID, beans.getId());
        SPUtils.getInstance().put(Constant.SP_NICK_NAME, tvMineName.getText().toString());

        ivVip.setImageResource(beans.getVipState() == 0 || beans.getVipState() == 3 ? R.drawable.ic_become_vip : R.drawable.ic_interests_vip);
        Drawable mVip = ContextCompat.getDrawable(mContext, R.drawable.ic_vip);
        Drawable mVipUn = ContextCompat.getDrawable(mContext, R.drawable.ic_vip_un);
        vipState = beans.getVipState();

        //0 :普通用户  1:vip 用户  2 :永久vip  3 vip 已过期
        if (beans.getVipState() == 0) {
            tvMineBecomeDue.setText("普通用户");
            tvMineBecomeDue.setEnabled(false);
        } else if (beans.getVipState() == 1) {
            tvMineBecomeDue.setCompoundDrawablesRelativeWithIntrinsicBounds(mVip, null, null, null);
            tvMineBecomeDue.setText(String.format("%s到期", beans.getVipEndTime()));
        } else if (beans.getVipState() == 2) {
            tvMineBecomeDue.setCompoundDrawablesRelativeWithIntrinsicBounds(mVip, null, null, null);
            tvMineBecomeDue.setText("终身VIP");
        } else if (beans.getVipState() == 3) {
            tvMineBecomeDue.setText("");
            tvMineBecomeDue.setCompoundDrawablesRelativeWithIntrinsicBounds(mVipUn, null, null, null);
        }
        if (SPUtils.getInstance().getInt(Constant.BUNDLE_MINE_BG, 0) > 1) {
            showGuideView();
        }
        //友盟推送——设置Alias
        String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        if (!TextUtils.isEmpty(phone)) {
            AppLifecyclesImpl.getPushAgent().setAlias(phone, Constant.PUSH_PHONE, (b, s) -> {
//                LogUtils.e("===============Mine:别名绑定" + phone + b);
            });
        }
    }


    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.ic_common_right, R.id.iv_vip, R.id.ic_common_right_sweep, R.id.cl_share_politeness, R.id.iv_open_boc,
            R.id.cl_excellent_audition, R.id.tv_mine_integral, R.id.iv_mine_head_pic, R.id.tv_mine_name, R.id.tv_mine_become_due, R.id.iv_right_bottom})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_common_back:
                //客服
                intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "客服中心");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICKCUSTOM_SERVICE);
                break;
            case R.id.iv_mine_head_pic:
            case R.id.tv_mine_name:
                //基本信息
                intent = new Intent(getActivity(), BasicInfoActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_INFORMATION);
                break;
            case R.id.tv_mine_become_due:
                if (vipState == 3) {
                    VipUtil.toVipPage(getContext());
                    UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_VIP);
                } else {
                    //基本信息
                    intent = new Intent(getActivity(), BasicInfoActivity.class);
                    UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_INFORMATION);
                }
                break;
            case R.id.ic_common_right:
                //消息
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
            case R.id.iv_vip:
                //加入vip
                VipUtil.toVipPage(getContext());
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_VIP);
                break;
            case R.id.ic_common_right_sweep:
                //扫一扫
                intent = new Intent(getActivity(), ScannerActivity.class);
                UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_SCAN);
                break;
            case R.id.cl_share_politeness:
                //邀请有礼
                if (null != mPresenter) {
                    mPresenter.inviteShareT();
                    constraintLayout.setClickable(false);
                }
                break;
            case R.id.cl_excellent_audition:
                //兑换礼券
                intent = new Intent(getActivity(), ExchangeVoucherActivity.class);
                intent.putExtra(Constant.EXCHANGE_VOUCHER_TYPE, 0);
                break;
            case R.id.tv_mine_integral:
                //我的学豆
                intent = new Intent(getActivity(), MyPointsActivity.class);
                break;
            case R.id.iv_right_bottom:
                intent = new Intent(getActivity(), TeacherLiveListActivity.class);
                break;
            case R.id.iv_open_boc:
                String strToday = TimeUtils.getStrToday();
                String strCountDownDay = SPUtils.getInstance().getString(Constant.SP_COUNT_DOWN_DAY);
                if (TextUtils.isEmpty(strCountDownDay) || strToday.equals(strCountDownDay)) {
                    showQiitongDialog();
                } else {
                    goQiitong();
                }
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void showQiitongDialog() {
        DialogUtils.showQiitongDialog(getActivity(), new DialogUtils.OnClickDialogListener() {
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
        if (mFresh == 1) {
            Intent intent = new Intent(getActivity(), QiitongWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_QIITONG_HOME));
            startActivity(intent);
        }

    }

    @Override
    public void loadTopicSuccess(BaseListBean<MyTopicBean> bean) {
        if (null != bean && vipState == 3 && bean.getRecords().size() > 0) {
            //会员过期了发布过话题也显示话题的
            addTopics();
        } else {
            removeTopics();
        }
    }

    @Override
    public void loadTopicFailed() {
        removeTopics();
    }

    @Override
    public void isShowQftPointFlagSuccess(int status, int fresh) {
        mFresh = fresh;
        if (status == 0) {
            ivOpenBoc.setVisibility(View.VISIBLE);
            addMyGeneral();
        } else {
            ivOpenBoc.setVisibility(View.GONE);
            removeMyGeneral();
        }
    }

    /**
     * 删除话题
     */
    private void removeTopics() {
        if (null != studyList && studyList.size() > 0) {
            for (int i = 0; i < studyList.size(); i++) {
                if ("我的话题".equals(studyList.get(i).getName())) {
                    studyList.remove(i);
                }
            }
            initAdapter(rvStudy, studyList);
        }
    }

    /**
     * 删除我的通证
     */
    private void removeMyGeneral() {
        if (null != serviceList && serviceList.size() > 0) {
            for (int i = 0; i < serviceList.size(); i++) {
                if ("我的通证".equals(serviceList.get(i).getName())) {
                    serviceList.remove(i);
                }
            }
            initAdapter(rvService, serviceList);
        }

    }

    /**
     * 添加我的通证
     */
    private void addMyGeneral() {
        if (null != serviceList && serviceList.size() > 0) {
            isName = false;
            for (int i = 0; i < serviceList.size(); i++) {
                if ("我的通证".equals(serviceList.get(i).getName())) {
                    isName = true;
                }
            }
            getActivity().findViewById(android.R.id.content).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isName) {
                        MineBean serviceBean = new MineBean("我的通证", mContext.getDrawable(R.drawable.ic_my_pass));
                        serviceList.add(serviceList.size(), serviceBean);
                        initAdapter(rvService, serviceList);
                    }
                }
            }, 100);


        }
    }

    /**
     * 添加话题
     */
    private void addTopics() {
        boolean isName = false;
        MineBean studyBean = new MineBean("我的话题", mContext.getDrawable(R.drawable.ic_topic));
        if (null != studyList && studyList.size() > 0) {
            for (int i = 0; i < studyList.size(); i++) {
                if ("我的话题".equals(studyList.get(i).getName())) {
                    isName = true;
                }
            }
            if (!isName) {
                studyList.add(studyList.size() - 3, studyBean);
                initAdapter(rvStudy, studyList);
            }
        }
    }


    @Override
    public void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data) {
        String mobile = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        String phoneNumber = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHAREWX)
                + "?code=" + url + "&mobile=" + phoneNumber);
        startActivity(intent);
        UmengUtil.umengCount(getActivity(), ConstantUmeng.MINE_CLICK_SHARE_MY_INVITATION);
    }

    @Override
    public void loadInviteSharetSuccess(String data) {
        url = data;
        if (mPresenter != null) {
            mPresenter.inviteShare();
        }
    }

    @Override
    public void showDiscipleGroup(String isShow) {
        if ("DZQ_AUTH_SWITCH".equals(code)) {
            if (TextUtils.equals(isShow, "open")) {
                resetServiceList();
            }
        }
    }

    @Override
    public void mShareRulesSuccess(BaseBean<List<RulesConfigBean>> rules) {
        SPUtils.getInstance().put("WITHDRAWAL_RULE", rules.data.get(0).getContent());
    }


    @Override
    public void loadUserIntegralSuccess(UserAssetsBean bean) {
        if (tIntegral != null) {
            tIntegral.setText("我的BOCC： \n" + String_Utils.linearNmber(bean.getUserBocc()));
        }
    }

    @Override
    public void loadUserIntegralFailure() {
        tIntegral.setVisibility(View.GONE);
    }

    /**
     * 设置认证
     *
     * @param platform
     */
    private void setPlatform(String platform) {
        if (platform.contains("1")) {
            mineWjj = 1;
        } else {
            mineWjj = 0;
        }
        if (platform.contains("2")) {
            mineDiscipleGroup = 1;
        } else {
            mineDiscipleGroup = 0;
        }
    }

    /**
     * 未读消息总数
     */
    public void showUnReadCount(Integer count) {
        if (tvMainInfo != null) {
            if (count != null && count > 0) {
                tvMainInfo.setVisibility(View.VISIBLE);
                if (count < 100) {
                    tvMainInfo.setText(count + "");
                } else {
                    tvMainInfo.setText("99+");
                }
            } else {
                tvMainInfo.setVisibility(View.GONE);
            }
        }
    }

    private void initGridData() {
        studyList.clear();
        MineBean studyBean0 = new MineBean(" 联系班主任 ", mContext.getDrawable(R.drawable.ic_contact_us));
        MineBean studyBean01 = new MineBean("考试系统", mContext.getDrawable(R.drawable.ic_candidate_system));
        MineBean studyBean02 = new MineBean("考生信息", mContext.getDrawable(R.drawable.ic_candidate_information));
        MineBean studyBean03 = new MineBean("基本信息", mContext.getDrawable(R.drawable.ic_basic_info));
        MineBean studyBean1 = new MineBean("我的证书", mContext.getDrawable(R.drawable.ic_my_certificate));
        MineBean studyBean2 = new MineBean("   我的收藏   ", mContext.getDrawable(R.drawable.ic_my_collection));
        MineBean studyBean3 = new MineBean("学习记录", mContext.getDrawable(R.drawable.ic_watch_record));
        MineBean studyBean4 = new MineBean("我的下载", mContext.getDrawable(R.drawable.ic_my_download));
        MineBean studyBean5 = new MineBean("我的笔记", mContext.getDrawable(R.drawable.ic_my_notes));
        MineBean studyBean7 = new MineBean("我的课程", mContext.getDrawable(R.drawable.ic_my_courses));
        MineBean studyBean8 = new MineBean("打卡", mContext.getDrawable(R.drawable.ic_clock_in));
        MineBean studyBean9 = new MineBean("作业本", mContext.getDrawable(R.drawable.ic_exercise_book));

        studyList.add(studyBean0);
        studyList.add(studyBean01);
        studyList.add(studyBean02);
        studyList.add(studyBean03);
        studyList.add(studyBean1);
        studyList.add(studyBean2);
        studyList.add(studyBean3);
        studyList.add(studyBean4);
        studyList.add(studyBean5);
        studyList.add(studyBean7);
        studyList.add(studyBean8);
        studyList.add(studyBean9);

        MineBean serviceBean = new MineBean("代理商", mContext.getDrawable(R.drawable.ic_agent));
//        MineBean serviceBean = new MineBean("商城", mContext.getDrawable(R.drawable.ic_xuecoin_mall));
        MineBean serviceBean1 = new MineBean("挖掘机认证", mContext.getDrawable(R.drawable.ic_wealth_wjj));
        MineBean serviceBean2 = new MineBean("帮助中心", mContext.getDrawable(R.drawable.ic_help_centre));
        MineBean serviceBean3 = new MineBean("意见反馈", mContext.getDrawable(R.drawable.img_feedback));
        MineBean serviceBean4 = new MineBean("我的订单", mContext.getDrawable(R.drawable.ic_my_order));
        MineBean serviceBean5 = new MineBean("邀请记录", mContext.getDrawable(R.drawable.ic_mine_wallet));
        MineBean serviceBean6 = new MineBean("设置", mContext.getDrawable(R.drawable.ic_set_up));
        serviceList.add(serviceBean);
        serviceList.add(serviceBean1);
        serviceList.add(serviceBean2);
        serviceList.add(serviceBean3);
        serviceList.add(serviceBean4);
        serviceList.add(serviceBean5);
        serviceList.add(serviceBean6);
    }

    private void resetServiceList() {
        serviceList.clear();
        MineBean serviceBean = new MineBean("代理商", mContext.getDrawable(R.drawable.ic_agent));
//        MineBean serviceBean = new MineBean("商城", mContext.getDrawable(R.drawable.ic_xuecoin_mall));
        MineBean serviceBean0 = new MineBean("挖掘机认证", mContext.getDrawable(R.drawable.ic_wealth_wjj));
        MineBean serviceBean1 = new MineBean("弟子群认证", mContext.getDrawable(R.drawable.img_wjj));
        MineBean serviceBean2 = new MineBean("帮助中心", mContext.getDrawable(R.drawable.ic_help_centre));
        MineBean serviceBean3 = new MineBean("意见反馈", mContext.getDrawable(R.drawable.img_feedback));
        MineBean serviceBean4 = new MineBean("我的订单", mContext.getDrawable(R.drawable.ic_my_order));
        MineBean serviceBean5 = new MineBean("邀请记录", mContext.getDrawable(R.drawable.ic_mine_wallet));
        MineBean serviceBean6 = new MineBean("\u3000设置\u3000", mContext.getDrawable(R.drawable.ic_set_up));
        serviceList.add(serviceBean);
        serviceList.add(serviceBean0);
        serviceList.add(serviceBean1);
        serviceList.add(serviceBean2);
        serviceList.add(serviceBean3);
        serviceList.add(serviceBean4);
        serviceList.add(serviceBean5);
        serviceList.add(serviceBean6);
        initAdapter(rvService, serviceList);
    }

    /**
     * 加载引导
     */
    public void showGuideView() {
        if (vipState == 1 || vipState == 2) {
            addTopics();
        } else if (vipState == 3 && mPresenter != null) {
            mPresenter.getTopicList();
        }
    }

    @Override
    public void setData(@Nullable Object data) {

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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
