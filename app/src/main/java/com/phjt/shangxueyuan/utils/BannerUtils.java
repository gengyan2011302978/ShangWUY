package com.phjt.shangxueyuan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.SuspendImgBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseCategoryActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseClassifyActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phsxy.utils.LogUtils;

/**
 * @author: gengyan
 * date:    2020/7/23 11:10
 * company: 普华集团
 * description: Banner点击判断
 */
public class BannerUtils {

    public static void bannerClick(Context context, ListBannerBean bannerBean) {
        bannerClick(context, bannerBean, null);
    }

    @SuppressLint("CheckResult")
    public static void bannerClick(Context context, ListBannerBean bannerBean, String id) {
        //是否有跳转： 1.无(跳转到直播专区)   2.有h5   3.课程  4.专栏 5.权威证书 6.训练营
        if (null == bannerBean) {
            return;
        }
        int ifUrl = bannerBean.getIfUrl();
        if (ifUrl == 1 && !TextUtils.isEmpty(id)) {
            Intent courseCategoryIntent = new Intent(context, CourseCategoryActivity.class);
            courseCategoryIntent.putExtra(Constant.COURSE_TYPE_ID, id);
            context.startActivity(courseCategoryIntent);
        } else if (ifUrl == 2) {
            Intent intent = new Intent(context, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, bannerBean.getToUrl());
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, bannerBean.getName());
            context.startActivity(intent);
        } else if (ifUrl == 3) {
            Intent courseIntent = new Intent(context, CourseDetailActivity.class);
            courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, bannerBean.getCourseId());
            context.startActivity(courseIntent);
        } else if (ifUrl == 4) {
            Intent courseIntent = new Intent(context, CourseDetailActivity.class);
            courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, bannerBean.getColumnId());
            context.startActivity(courseIntent);
        } else if (ifUrl == 5) {
            Intent intent = new Intent(context, MyWebViewActivity.class);
            String url = ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_NAV) + "?videoId=" + bannerBean.getToUrl();
            intent.putExtra(Constant.BUNDLE_WEB_URL, url);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, bannerBean.getName());
            context.startActivity(intent);
        } else if (ifUrl == 6) {
            Intent trainingIntent = new Intent(context, TrainingCampDetailActivity.class);
            trainingIntent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, bannerBean.getTrainingId());
            context.startActivity(trainingIntent);
        } else if (ifUrl == 8) {
            CommonHttpManager.getInstance(context)
                    .obtainRetrofitService(ApiService.class)
                    .liveInfo(bannerBean.getLiveInfoId() + "")
                    .compose(RxUtils.applySchedulers())
                    .subscribe((BaseBean<LiveBean> baseBean) -> {
                        if (baseBean.isOk()) {
                            if (baseBean.data.getStatus() != 2 && baseBean.data.getStatus() != 4) {
                                MZLiveUtils.toLivePlay(context, baseBean.data.getTicketId(), baseBean.data.getLiveStyle(), baseBean.data.getId());
                            } else {
                                TipsUtil.show("直播已结束");
                            }
                        } else {
                            TipsUtil.show(baseBean.msg);
                        }
                    }, throwable -> LogUtils.e("网络异常"));
        }
    }


    public static void suspendImgClick(Context context, SuspendImgBean bannerBean) {
        suspendImgClick(context, bannerBean, null);
    }

    @SuppressLint("CheckResult")
    public static void suspendImgClick(Context context, SuspendImgBean bannerBean, String id) {
        //是否有跳转： 1.无(跳转到直播专区)   2.有h5   3.课程  4.专栏 5.权威证书 6.训练营
        int ifUrl = bannerBean.getIfUrl();
        if (ifUrl == 1 && !TextUtils.isEmpty(id)) {
            Intent courseCategoryIntent = new Intent(context, CourseCategoryActivity.class);
            courseCategoryIntent.putExtra(Constant.COURSE_TYPE_ID, id);
            context.startActivity(courseCategoryIntent);
        } else if (ifUrl == 2) {
            Intent intent = new Intent(context, MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, bannerBean.getToUrl());
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, bannerBean.getName());
            context.startActivity(intent);
        } else if (ifUrl == 3) {
            Intent courseIntent = new Intent(context, CourseDetailActivity.class);
            courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, bannerBean.getCourseId());
            context.startActivity(courseIntent);
        } else if (ifUrl == 4) {
            Intent courseIntent = new Intent(context, CourseDetailActivity.class);
            courseIntent.putExtra(Constant.BUNDLE_COURSE_ID, bannerBean.getColumnId());
            context.startActivity(courseIntent);
        } else if (ifUrl == 5) {
            Intent intent = new Intent(context, MyWebViewActivity.class);
            String url = ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_NAV) + "?videoId=" + bannerBean.getToUrl();
            intent.putExtra(Constant.BUNDLE_WEB_URL, url);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, bannerBean.getName());
            context.startActivity(intent);
        } else if (ifUrl == 6) {
            Intent trainingIntent = new Intent(context, TrainingCampDetailActivity.class);
            trainingIntent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, bannerBean.getTrainingId());
            context.startActivity(trainingIntent);
        } else if (ifUrl == 8) {
            CommonHttpManager.getInstance(context)
                    .obtainRetrofitService(ApiService.class)
                    .liveInfo(bannerBean.getLiveInfoId() + "")
                    .compose(RxUtils.applySchedulers())
                    .subscribe((BaseBean<LiveBean> baseBean) -> {
                        if (baseBean.isOk()) {
                            if (baseBean.data.getStatus() != 2 && baseBean.data.getStatus() != 4) {
                                MZLiveUtils.toLivePlay(context, baseBean.data.getTicketId(), baseBean.data.getLiveStyle(), baseBean.data.getId());
                            } else {
                                TipsUtil.show("直播已结束");
                            }
                        } else {
                            TipsUtil.show(baseBean.msg);
                        }
                    }, throwable -> LogUtils.e("网络异常"));
        }
    }
}
