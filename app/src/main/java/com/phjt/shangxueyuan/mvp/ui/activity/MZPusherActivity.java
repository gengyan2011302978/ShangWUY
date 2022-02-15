package com.phjt.shangxueyuan.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengzhu.live.sdk.business.dto.MZOnlineUserListDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatTextDto;
import com.mengzhu.live.sdk.business.dto.play.PlayInfoDto;
import com.mengzhu.live.sdk.business.dto.push.StartBroadcastInfoDto;
import com.mzmedia.IPushClickListener;
import com.mzmedia.fragment.push.MZPlugFlowFragement;
import com.mzmedia.presentation.dto.LiveConfigDto;
import com.mzmedia.utils.MUIImmerseUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.NetworkUtils;

import io.reactivex.functions.Consumer;
import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * @author: gengyan
 * date:    2021/4/8
 * company: 普华集团
 * description: 直播推流
 */
public class MZPusherActivity extends AppCompatActivity implements IPushClickListener, IWithoutImmersionBar, CancelAdapt {

    private FragmentManager mFragmentManager;
    private MZPlugFlowFragement mzPlugFlowFragement;
    private StartBroadcastInfoDto startBroadcastInfoDto;
    private PlayInfoDto mPlayInfoDto;
    private TextView mTvCount;
    private ImageView mImClose;
    private int screen, bitrate;
    private boolean cbbeauty, cblater, cbAudio, cbAllBanChat;
    private String liveTk, ticketId, fps, time, id, channelId;
    private LiveConfigDto liveConfigDto;

    private boolean isAudioPush = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        screen = getIntent().getIntExtra("screen", 0);
        MUIImmerseUtils.setStatusTranslucent(getWindow(), this);
        switch (screen) {
            case 1:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case 2:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                break;
        }
        startBroadcastInfoDto = (StartBroadcastInfoDto) getIntent().getSerializableExtra("pushDto");
        mFragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppCompatTheme);
        setContentView(R.layout.activity_mz_pusher);

        isAudioPush = getIntent().getBooleanExtra("isAudioPush", false);
        bitrate = getIntent().getIntExtra("bitrate", 0);
        liveTk = getIntent().getStringExtra("livetk");
        ticketId = getIntent().getStringExtra("ticketId");
        channelId = getIntent().getStringExtra("channelId");
        cbbeauty = getIntent().getBooleanExtra("cbbeauty", false);
        cblater = getIntent().getBooleanExtra("cblater", false);
        cbAudio = getIntent().getBooleanExtra("cbAudio", false);
        cbAllBanChat = getIntent().getBooleanExtra("cbAllBanChat", false);
        fps = getIntent().getStringExtra("fps");
        time = getIntent().getStringExtra("time");
        id = getIntent().getStringExtra("id");
        liveConfigDto = new LiveConfigDto();
        liveConfigDto.setBitrate(bitrate);
        liveConfigDto.setCbbeauty(cbbeauty);
        liveConfigDto.setCblater(cblater);
        liveConfigDto.setCbAudio(cbAudio);
        liveConfigDto.setLive_tk(liveTk);
        liveConfigDto.setTicketId(ticketId);
        liveConfigDto.setAllBanChat(cbAllBanChat ? 0 : 1);
        if (TextUtils.isEmpty(fps)) {
            fps = "15";
        }
        liveConfigDto.setFps(Integer.parseInt(fps));
        if (TextUtils.isEmpty(time)) {
            time = "3";
        }
        liveConfigDto.setTime(Integer.parseInt(time));
        MUIImmerseUtils.setStatusTextColor(false, this);
        initView();
    }

    private void initView() {
        mTvCount = findViewById(R.id.tv_activity_broadcast_count);
        mPlayInfoDto = new PlayInfoDto();
        mPlayInfoDto.setMsg_config(startBroadcastInfoDto.getMsg_conf());
        mPlayInfoDto.setChat_config(startBroadcastInfoDto.getChat_conf());
        mPlayInfoDto.setTicket_id(startBroadcastInfoDto.getTicket_id());
        mPlayInfoDto.setChannel_id(startBroadcastInfoDto.getChannel_id());
        mPlayInfoDto.setChat_uid(startBroadcastInfoDto.getChat_conf().getChat_uid());
        mzPlugFlowFragement = MZPlugFlowFragement.newInstance(startBroadcastInfoDto.getPush_url(), startBroadcastInfoDto.getTicket_id()
                , screen, mPlayInfoDto, liveConfigDto, isAudioPush);
        mFragmentManager.beginTransaction().replace(R.id.layout_push, mzPlugFlowFragement).commitAllowingStateLoss();
        mzPlugFlowFragement.setPushClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStopLive() {
        changeLiveStatus();
    }

    @Override
    public void onChatAvatar(ChatTextDto chatTextDto) {
//        Toast.makeText(PusherActivity.this,"点击用户头像",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onALLBanChat() {
        TipsUtil.show("全体禁言成功");
    }

    @Override
    public void onBanChat() {
        TipsUtil.show("单体禁言成功");
    }

    @Override
    public void onShare(PlayInfoDto dto) {
//        Toast.makeText(PusherActivity.this,"点击分享",Toast.LENGTH_LONG).show();
        getShareData();
    }

    @Override
    public void onLiveAvatar() {
//        Toast.makeText(PusherActivity.this,"点击主播头像",Toast.LENGTH_LONG).show();
//        MyUserInfoPresenter.getInstance().getUserInfo().getAvatar(); //获取主播头像
//        MyUserInfoPresenter.getInstance().getUserInfo().getNickname();//获取主播昵称
    }

    @Override
    public void onOnlineNum(MZOnlineUserListDto onlineUserDto) {
//        Toast.makeText(PusherActivity.this,"点击在线人数",Toast.LENGTH_LONG).show();
    }

    /**
     * 切换直播状态
     */
    @SuppressLint("CheckResult")
    private void changeLiveStatus() {
        if (!NetworkUtils.isConnected()) {
            TipsUtil.showTips("网络不可用");
            return;
        }
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .updateLiveState(channelId, "2", id, ticketId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {
                        MZPusherActivity.this.finish();
                    }
                }, throwable -> {
                    LogUtils.e("=================" + throwable.getMessage());
                    finish();
                });

    }

    /**
     * 获取老师分享
     */
    @SuppressLint("CheckResult")
    private void getShareData() {
        if (!NetworkUtils.isConnected()) {
            TipsUtil.showTips("网络不可用");
            return;
        }
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getTeacherShare(id)
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {
                        ShareBean shareBean = baseBean.data;
                        if (shareBean != null) {
                            ShareUtils.showSharePop(this, shareBean);
                        } else {
                            TipsUtil.show("分享内容为空");
                        }
                    } else {
                        TipsUtil.show(baseBean.msg);
                    }
                }, throwable -> LogUtils.e("========================" + throwable.getMessage()));
    }


    @Override
    public Resources getResources() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            AutoSizeCompat.autoConvertDensity(super.getResources(), 375f,
                    AutoSizeConfig.getInstance().getScreenWidth() < AutoSizeConfig.getInstance().getScreenHeight());
        }
        return super.getResources();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
