package com.phjt.shangxueyuan.common;

import android.text.TextUtils;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.app.AppLifecyclesImpl;
import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;


/**
 * @author: gengyan
 * date:    2019/11/18 11:21
 * company: 普华集团
 * description: 公共业务处理
 */
public class MyBusiness {

    public static final String BUILD_FLAVOR_RELEASE = "product";

    private MyBusiness() {
    }

    /**
     * 退出登录，清空SP值
     */
    public static void loginOut() {
        cleanToken();
        cleanSp();
    }

    private static void cleanToken() {
        SPUtils.getInstance().remove(Constant.SP_TOKEN);
        SPUtils.getInstance().remove(Constant.SP_MOBILE);
    }

    public static void cleanSp() {
        String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
        if (!TextUtils.isEmpty(phone)) {
            AppLifecyclesImpl.getPushAgent().deleteAlias(phone, Constant.PUSH_PHONE, (b, s) -> {
                LogUtils.e("=================移除别名" + phone + "==b::" + b);
            });
        }
        SPUtils.getInstance().remove(Constant.SP_MOBILE);
    }


    /**
     * 判断是否是生产的正式环境版本
     */
    public static boolean isProductRelease() {
        return !BuildConfig.DEBUG && BUILD_FLAVOR_RELEASE.equals(BuildConfig.FLAVOR);
    }
}
