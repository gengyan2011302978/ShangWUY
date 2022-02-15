package com.phjt.shangxueyuan.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;

import com.mengzhu.live.sdk.ui.api.MZApiDataListener;
import com.mengzhu.live.sdk.ui.api.MZApiRequest;
import com.mzmedia.fragment.HalfPlayerFragment;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MZHalfPlayerActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MZPlayerActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import tv.mengzhu.core.frame.coreutils.URLParamsUtils;
import tv.mengzhu.core.wrap.netwock.Page;
import tv.mengzhu.core.wrap.user.modle.UserDto;
import tv.mengzhu.core.wrap.user.presenter.MyUserInfoPresenter;

/**
 * @author: gengyan
 * date:    2021/4/8 14:24
 * company: 普华集团
 * description: 盟主直播
 */
public class MZLiveUtils {

    /**
     * 进入直播
     *
     * @param context   context
     * @param ticketId  友盟直播活动id
     * @param liveStyle 直播形式 0横屏  1竖屏
     * @param liveId    后台直播id
     */
    @SuppressLint("CheckResult")
    public static void toLivePlay(Context context, String ticketId, int liveStyle, String liveId) {

        new RxPermissions((FragmentActivity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
            if (aBoolean) {
                final String uniqueId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
                final String nickName = SPUtils.getInstance().getString(Constant.SP_NICK_NAME);
                final String avatar = SPUtils.getInstance().getString(Constant.SP_AVATAR);
                final String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
                final String appId = BuildConfig.MZ_APP_ID;
                final String secretKey = BuildConfig.MZ_SECRET_KEY;
                URLParamsUtils.setSecretKey(secretKey);
                UserDto userDto = new UserDto();
                userDto.setUniqueID(uniqueId);
                userDto.setAppid(appId);
                userDto.setAvatar(avatar);
                userDto.setNickname(nickName);
                MyUserInfoPresenter.getInstance().saveUserinfo(userDto);
                MZApiRequest sdkLogin = new MZApiRequest();
                sdkLogin.createRequest(context, MZApiRequest.API_TYPE_LOGIN);
                sdkLogin.setResultListener(new MZApiDataListener() {
                    @Override
                    public void dataResult(String apiType, Object dto, Page page, int status) {
                        MyUserInfoPresenter.getInstance().getUserInfo().setToken(((UserDto) dto).getToken());
                        //传入观看用户的信息和活动id到直播间
                        Intent intent;
                        if (liveStyle == 0) {
                            intent = new Intent(context, MZHalfPlayerActivity.class);
                        } else {
                            intent = new Intent(context, MZPlayerActivity.class);
                        }

                        intent.putExtra(HalfPlayerFragment.TICKET_ID, ticketId);
                        intent.putExtra(HalfPlayerFragment.LIVE_ID, liveId);
                        intent.putExtra(HalfPlayerFragment.UNIQUE_ID, uniqueId);
                        intent.putExtra(HalfPlayerFragment.APP_ID, appId);
                        if (!TextUtils.isEmpty(ticketId)) {
                            intent.putExtra(HalfPlayerFragment.NICKNAME, nickName);
                            intent.putExtra(HalfPlayerFragment.AVATAR, avatar);
                            intent.putExtra("phone", phone);
                            context.startActivity(intent);
                        } else {
                            TipsUtil.show("直播id不能为空");
                        }
                    }

                    @Override
                    public void errorResult(String apiType, int code, String msg) {
                        TipsUtil.show(msg);
                    }
                });
                sdkLogin.startData(MZApiRequest.API_TYPE_LOGIN, uniqueId);
            } else {
                TipsUtil.show("没有权限！请到“设置-应用-权限管理-熵吾优”打开存储权限");
            }
        });
    }


    /**
     * 更加商品链接，进行页面跳转
     *
     * @param context context
     * @param url     商品链接
     */
    public static void jumpPage(Context context, String url) {

        LogUtils.e("================直播商品点击Url：" + url);
        if (TextUtils.isEmpty(url)) {
            TipsUtil.show("链接为空");
            return;
        }

        UrlUtil.UrlEntity entity = UrlUtil.parse(url);
        Map<String, String> params = entity.params;
        if (params != null) {
            String type = params.get("type");
            String id = params.get("id");
            if (TextUtils.equals(type, "vip")) {
                VipUtil.toVipPage(context);
            } else if (TextUtils.equals(type, "zhuanlan")) {
                Intent courseIntent = new Intent(context, CourseDetailActivity.class);
                courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(id));
                context.startActivity(courseIntent);
            } else if (TextUtils.equals(type, "xunlianying")) {
                Intent trainingIntent = new Intent(context, TrainingCampDetailActivity.class);
                trainingIntent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, id);
                context.startActivity(trainingIntent);
            } else {
                Intent webIntent = new Intent(context, MyWebViewActivity.class);
                webIntent.putExtra(Constant.BUNDLE_WEB_TITLE, "");
                webIntent.putExtra(Constant.BUNDLE_WEB_URL, url);
                context.startActivity(webIntent);
            }
        } else {
            Intent webIntent = new Intent(context, MyWebViewActivity.class);
            webIntent.putExtra(Constant.BUNDLE_WEB_TITLE, "");
            webIntent.putExtra(Constant.BUNDLE_WEB_URL, url);
            context.startActivity(webIntent);
        }
    }
}
