package com.phjt.shangxueyuan.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Description:窗口工具类
 * Data：2019/5/20-19:12
 *
 * @author: Mandel
 * Log: 2019/5/20-19:12 Update:Create
 */
public class DisplayUtil {

    public static int getScreenWidth(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        if (activity == null) {
            return 0;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }


    /**
     * 将px值转换为dp值
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值，
     *
     * @param dipValue
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取手机DPI
     *
     * @param context
     * @return
     */
    public static int getPhoneDPI(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        // 屏幕密度DPI（120 / 160 / 240,小米4的是480）
        int densityDpi = metric.densityDpi;
        return densityDpi;
    }


    /**
     * @throws ，有SIM卡则调用付费SDK
     * @author CX-
     * @判断 是否含有sim卡
     **/
    private boolean readSIMCard(Activity activity) {
// 取得相关系统服务
        TelephonyManager manager = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        // 取出IMSI
        String imsi = manager.getSubscriberId();

        if (imsi == null || imsi.length() <= 0) {
            return false;
        } else {
            return true;

        }

    }

    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(Context context, int dimensionId) {
        return context.getResources().getDimensionPixelSize(dimensionId);
    }

    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
