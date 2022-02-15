package com.phjt.shangxueyuan.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.interf.IEditTextAutomaticHide;
import com.phjt.shangxueyuan.interf.INotFitsImmersionBar;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;


/**
 * @author : austen
 * company    : 普华
 * date       : 2019/3/27 17:51
 * description: Activity 代理生命周期回调
 * 同{@link AppLifecyclesImpl}原理一样
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    /**
     * 记录当前应用是否处于前台
     */
    public int count;
    public static boolean isBackground;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String simpleName = activity.getClass().getSimpleName();
        if (activity instanceof IWithoutImmersionBar
                || simpleName.contains("LandscapeTransActivity")
                || simpleName.contains("ImageShowActivity")) {
            //doNothing
        } else if (activity instanceof INotFitsImmersionBar
                || simpleName.contains("PictureSelectorActivity")
                || simpleName.contains("PicturePreviewActivity")) {
            ImmersionBar.with(activity)
                    .statusBarDarkFont(true)
                    .init();
        } else {
            ImmersionBar.with(activity).fitsSystemWindows(true)
                    .statusBarColor(R.color.color_white)
                    .statusBarDarkFont(true)
                    .init();
        }

        //点击外部，关闭键盘
        if (activity instanceof IEditTextAutomaticHide) {
            FrameLayout contentParent =
                    activity.getWindow().getDecorView().findViewById(android.R.id.content);
            View content = contentParent.getChildAt(0);
            if (content != null) {
                SoftKeyboardUtil.recursiveFindEditAndSetOnTouchListener(content);
            }
        }

        //Push后台进行日活统计及多维度推送的必调用方法
        if (SPUtils.getInstance().getBoolean(Constant.AGREEMENT, false)) {
            PushAgent.getInstance(activity).onAppStart();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        count++;
        if (count == 1) {
            LogUtils.e("================" + "foreground");
            isBackground = false;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) {
            LogUtils.e("================" + "background");
            isBackground = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
