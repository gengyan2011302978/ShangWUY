package com.phjt.shangxueyuan.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mzmedia.fragment.PlayerFragment;
import com.mzmedia.presentation.dto.MZEvent;
import com.mzmedia.utils.MUIImmerseUtils;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.integration.AppManager;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.MZDesBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.BitmapUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * @author: gengyan
 * date:    2021/4/22 17:19
 * company: 普华集团
 * description: 盟主观看直播，需继承此类
 */
public abstract class MZBasePlayerActivity extends AppCompatActivity implements IWithoutImmersionBar, CancelAdapt, View.OnClickListener {

    public static final int REQUEST_CODE = 1000;
    public static final int RECHARGE_RESULT_CODE = 1001;
    public static final int PAY_RESULT_CODE = 1002;

    private View mLiveStateLayout;
    private TextView mTvLiveTipContent;
    private TextView mTvStartTimeTip;
    private RoundTextView mTvLivePay;
    private RoundTextView mTvLiveBack;
    private ImageView mIvBg;
    private ImageView mIvLiveQr;

    public String mTicketId;
    public String mLiveId;
    public String phone;

    /**
     * 是否可以观看直播
     */
    private boolean isCanPlay = true;
    private LiveBean mLiveBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        MUIImmerseUtils.setStatusTranslucent(getWindow(), this);
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppCompatTheme);
        setContentView(R.layout.activity_half_play);
        MUIImmerseUtils.setStatusTextColor(false, this);

        mLiveStateLayout = findViewById(R.id.live_state_layout);
        mTvLiveTipContent = findViewById(R.id.tv_live_tip_content);
        mTvLivePay = findViewById(R.id.tv_live_pay);
        mTvLiveBack = findViewById(R.id.tv_live_back);
        mIvBg = findViewById(R.id.iv_bg);
        mIvLiveQr = findViewById(R.id.iv_live_qr);
        mTvStartTimeTip = findViewById(R.id.tv_start_time_tip);
        mTvLivePay.setOnClickListener(this);
        mTvLiveBack.setOnClickListener(this);

        mTicketId = getIntent().getStringExtra(PlayerFragment.TICKET_ID);
        mLiveId = getIntent().getStringExtra(PlayerFragment.LIVE_ID);
        phone = getIntent().getStringExtra("phone");

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //获取直播状态
        getLiveState(mLiveId);

        //长按保存二维码
        mIvLiveQr.setOnLongClickListener(v -> {
            Bitmap bitmap = BitmapUtils.loadBitmapFromView(mIvLiveQr);
            if (bitmap != null) {
                boolean isSuccess = BitmapUtils.saveImageToGallery(MZBasePlayerActivity.this, bitmap);
                TipsUtil.show(isSuccess ? "保存成功" : "保存失败");
            }
            return false;
        });
    }

    /**
     * 获取直播状态后调用
     *
     * @param isCanPlay 是否可以观看直播
     */
    public abstract void doAfterGetLiveState(boolean isCanPlay);

    /**
     * 重新加载直播状态
     */
    public abstract void reloadLiveStatus();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_live_pay:
                if (mLiveBean != null) {
                    Intent intent = new Intent(this, OrderConfirmActivity.class);
                    intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, mLiveId);
                    intent.putExtra(Constant.BUNDLE_ORDER_NAME, mLiveBean.getName());
                    intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, mLiveBean.getStudyBean());
                    intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 15);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
            case R.id.tv_live_back:
                finish();
                break;
            case R.id.live_state_layout:
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(MZEvent mzEvent) {
        if (mzEvent != null) {
            int type = mzEvent.type;
            String liveId = mzEvent.liveId;
            Double integral = mzEvent.integral;
            String gifName = mzEvent.gifName;
            if (type == MZEvent.TYPE_GET_LIVE_DES) {
                getLiveDes(liveId);
            } else if (type == MZEvent.TYPE_SEND_GIF) {
                sendGif(integral, gifName, liveId);
            } else if (type == MZEvent.TYPE_LOAD_IMG_BG) {
                if (mIvBg != null) {
                    Glide.with(this).asBitmap().load(mzEvent.liveId)
                            .listener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                    AppImageLoader.rsBlur(MZBasePlayerActivity.this, resource, 15);
                                    return false;
                                }
                            }).into(mIvBg);
                }
            } else if (type == MZEvent.TYPE_SEND_MSG_SUCCESS) {
                sendMsgSuccess();
            } else if (type == MZEvent.TYPE_KICK_OUT) {
                tickOut();
            }
        }
    }

    /**
     * 获取直播状态，回放状态是，退出直播页面
     */
    @SuppressLint("CheckResult")
    private void getLiveState(String id) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .liveInfo(id)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        mLiveBean = baseBean.data;
                        //0:未直播 1:直播中 2:回放 3:断流中 4直播生成回放中
                        int status = mLiveBean.getStatus();
                        //上下架状态 0.下架（默认） 1.上架
                        int upState = mLiveBean.getUpState();
                        //0,"正常观看" 1,"您已被限制进入" 2,"该直播仅指定用户可见" 3,"该直播仅限VIP观看"
                        // 4,"该直播为付费直播,支付完成即可进入观看" 5,"无法观看,已超过允许进入的时间"
                        int userPermission = mLiveBean.getUserPermission();

                        if (mLiveStateLayout != null) {
                            if (upState == 0) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("该直播已下架");
                                isCanPlay = false;
                            } else if (status != 0 && status != 1 && status != 3) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("该直播已结束");
                                isCanPlay = false;
                            } else if (userPermission == 1) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("您已被限制进入");
                                isCanPlay = false;
                            } else if (userPermission == 2) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("该直播仅指定用户可见");
                                isCanPlay = false;
                            } else if (userPermission == 3) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("该直播仅限VIP观看");
                                isCanPlay = false;
                            } else if (userPermission == 5) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.GONE);
                                mTvLiveTipContent.setText("无法观看\n已超过允许进入的时间");
                                isCanPlay = false;
                            } else if (userPermission == 4) {
                                mLiveStateLayout.setVisibility(View.VISIBLE);
                                mTvLivePay.setVisibility(View.VISIBLE);
                                if (TextUtils.equals("0", mLiveBean.getStartMinute())) {
                                    mTvStartTimeTip.setVisibility(View.GONE);
                                } else {
                                    mTvStartTimeTip.setVisibility(View.VISIBLE);
                                    mTvStartTimeTip.setText(String.format("该直播已开播%s分钟", mLiveBean.getStartMinute()));
                                }
                                mTvLivePay.setText(String.format("支付%s学豆", String_Utils.linearNmber(mLiveBean.getStudyBean())));
                                mTvLiveTipContent.setText("该直播为付费直播\n支付完成即可进入观看");
                                isCanPlay = false;
                            } else {
                                mLiveStateLayout.setVisibility(View.GONE);
                                isCanPlay = true;
                            }
                        }
                    }
                    doAfterGetLiveState(isCanPlay);
                }, throwable -> {
                    doAfterGetLiveState(isCanPlay);
                    LogUtils.e("====================" + throwable.getMessage());
                });
    }

    /**
     * 获取直播简介
     */
    @SuppressLint("CheckResult")
    private void getLiveDes(String liveId) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getLiveDetail(liveId)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        MZDesBean mzDesBean = baseBean.data;
                        EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_LOAD_LIVE_DES, liveId,
                                mzDesBean.getIntroduction(), mzDesBean.getIntegral()));
                    } else {
                        TipsUtil.show(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("====================" + throwable.getMessage()));
    }

    /**
     * 送礼物
     */
    @SuppressLint("CheckResult")
    private void sendGif(Double integral, String gifName, String liveId) {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .sendGif(integral, gifName, liveId)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        TipsUtil.show("发送成功");
                        getLiveDes(liveId);
                        EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_SEND_GIF_SUCCESS));
                    } else if (baseBean.code == 8001) {
                        //积分不足
                        showIntegralDialog();
                    } else {
                        TipsUtil.show(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("====================" + throwable.getMessage()));
    }

    /**
     * 发送消息成功，行为奖励
     */
    @SuppressLint("CheckResult")
    private void sendMsgSuccess() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .liveCommment(mLiveId)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (!baseBean.isOk()) {
                        LogUtils.e("=======================" + baseBean.msg);
                    }
                }, throwable -> LogUtils.e("====================" + throwable.getMessage()));
    }

    /**
     * 加入黑名单，被调用
     */
    @SuppressLint("CheckResult")
    private void tickOut() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .saveUserBlackList(mLiveId)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (!baseBean.isOk()) {
                        LogUtils.e("=======================" + baseBean.msg);
                    }
                    TipsUtil.show("您被移除直播间");
                    finish();
                }, throwable -> LogUtils.e("====================" + throwable.getMessage()));
    }


    private void showIntegralDialog() {
        Activity currentActivity = AppManager.getAppManager().getCurrentActivity();
        if (currentActivity == null) {
            TipsUtil.show("学豆不足");
            return;
        }

        DialogUtils.showCancelSureDialog(currentActivity, "学豆不足",
                "学豆不足，前去获取学豆",
                "取消", "确定",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        Intent intent = new Intent(MZBasePlayerActivity.this, CurrencyRechargeActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                        EventBusManager.getInstance().post(new MZEvent(MZEvent.TYPE_CLOSE_GIF_DIALOG));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RECHARGE_RESULT_CODE:
                    getLiveDes(mLiveId);
                    break;
                case PAY_RESULT_CODE:
                    //支付成功，重新加载直播状态
                    if (mLiveStateLayout != null) {
                        mLiveStateLayout.setVisibility(View.GONE);
                        reloadLiveStatus();
                    }
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
