package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.mengzhu.live.sdk.business.dto.push.StartBroadcastInfoDto;
import com.mengzhu.live.sdk.ui.api.MZApiDataListener;
import com.mengzhu.live.sdk.ui.api.MZApiRequest;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.mvp.IPresenter;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.net.URLEncoder;

import tv.mengzhu.core.frame.coreutils.URLParamsUtils;
import tv.mengzhu.core.wrap.netwock.Page;
import tv.mengzhu.core.wrap.user.modle.UserDto;
import tv.mengzhu.core.wrap.user.presenter.MyUserInfoPresenter;

/**
 * @author: gengyan
 * date:    2021/4/9 14:44
 * company: 普华集团
 * description: 盟主推流直播 需继承此类
 */
public abstract class MZBasePusherActivity<P extends IPresenter> extends BaseActivity<P> {

    /**
     * 推流屏幕方向 1：竖屏 2：横屏
     */
    private int screen = 2;
    /**
     * 推流分辨率 标清：500 * 1024, 高清：800 * 1024, 超清：1000 * 1024
     */
    private int bitrate = 800 * 1024;
    /**
     * 关闭美颜（默认开启）
     */
    private boolean cbbeauty = true;
    /**
     * 后置摄像头（默认前置）
     */
    private boolean cblater;
    /**
     * 静音
     */
    private boolean cbAudio;
    /**
     * 全体禁言
     */
    private boolean cbAllBanChat;
    /**
     * 填写开始倒计时长，默认为3秒
     */
    private String time;
    /**
     * 是否语音直播
     */
    private boolean isAudioPush;
    private MZApiRequest mzLiveCreateApiRequest;
    private MZApiRequest mzLiveStreamApiRequest;
    private ProgressDialog progressDialog;

    private String[] mPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mzLiveCreateApiRequest = new MZApiRequest();
        mzLiveStreamApiRequest = new MZApiRequest();
        mzLiveCreateApiRequest.createRequest(this, MZApiRequest.API_TYPE_LIVE_CREATE);
        mzLiveStreamApiRequest.createRequest(this, MZApiRequest.API_TYPE_LIVE_STREAM);
        progressDialog = new ProgressDialog(this);
    }

    /**
     * 推流直播
     *
     * @param ticketId  直播间ID
     * @param liveTk    直播间token
     * @param id        列表id
     * @param liveStyle 直播样式 0:横屏 1:竖屏
     */
    @SuppressLint("CheckResult")
    public void startPush(int channelId, String ticketId, String liveTk, String id, int liveStyle) {

        screen = liveStyle == 0 ? 2 : 1;

        new RxPermissions(this).request(mPermission).subscribe(aBoolean -> {
            if (aBoolean) {
                final String nickName = SPUtils.getInstance().getString(Constant.SP_NICK_NAME);
                final String avatar = SPUtils.getInstance().getString(Constant.SP_AVATAR);
                final String uniqueId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
                final String appId = BuildConfig.MZ_APP_ID;
                final String secretKey = BuildConfig.MZ_SECRET_KEY;
                UserDto dto = new UserDto();
                dto.setUniqueID(uniqueId);
                dto.setAppid(appId);
                dto.setAvatar(avatar);
                dto.setNickname(nickName);
                MyUserInfoPresenter.getInstance().saveUserinfo(dto);
                URLParamsUtils.setSecretKey(secretKey);

                if (TextUtils.isEmpty(ticketId) || TextUtils.isEmpty(liveTk)) {
                    TipsUtil.show("直播信息异常");
                    return;
                }
                progressDialog.show();
                mzLiveStreamApiRequest.setResultListener(new StartStream(channelId, liveTk, id, ticketId));
                mzLiveStreamApiRequest.startData(MZApiRequest.API_TYPE_LIVE_STREAM,
                        liveTk,
                        uniqueId,
                        URLEncoder.encode(nickName),
                        URLEncoder.encode(avatar), ticketId);
            } else {
                TipsUtil.show("没有权限！请到“设置-应用-权限管理-熵吾优”打开麦克风、相机、存储等权限");
            }
        });
    }

    private class StartStream implements MZApiDataListener {

        private String livetk;
        private String id;
        private String ticketId;
        private int channelId;

        public StartStream(int channelId, String livetk, String id, String ticketId) {
            this.livetk = livetk;
            this.id = id;
            this.ticketId = ticketId;
            this.channelId = channelId;
        }

        @Override
        public void dataResult(String s, Object o, Page page, int status) {
            progressDialog.dismiss();
            StartBroadcastInfoDto dto = (StartBroadcastInfoDto) o;
            Intent intent = new Intent(MZBasePusherActivity.this, MZPusherActivity.class);
            intent.putExtra("pushDto", dto);
            intent.putExtra("screen", screen);
            intent.putExtra("bitrate", bitrate);
            intent.putExtra("cbbeauty", cbbeauty);
            intent.putExtra("channelId", channelId + "");
            intent.putExtra("cblater", cblater);
            intent.putExtra("cbAudio", cbAudio);
            intent.putExtra("cbAllBanChat", cbAllBanChat);
            intent.putExtra("livetk", livetk);
            intent.putExtra("fps", "");
            intent.putExtra("time", time);
            intent.putExtra("isAudioPush", isAudioPush);
            intent.putExtra("id", id);
            intent.putExtra("ticketId", ticketId);
            startActivity(intent);
        }

        @Override
        public void errorResult(String s, int i, String s1) {
            progressDialog.dismiss();
            TipsUtil.show(s1);
        }
    }

}
